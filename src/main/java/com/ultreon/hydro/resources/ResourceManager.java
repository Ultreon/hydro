package com.ultreon.hydro.resources;

import com.ultreon.commons.exceptions.DuplicateElementException;
import com.ultreon.commons.function.ThrowingSupplier;
import com.ultreon.hydro.Game;
import com.ultreon.hydro.common.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResourceManager {
    private final Map<File, PathMapping<byte[]>> mapping = new ConcurrentHashMap<>();
    private final Map<Identifier, byte[]> assets = new ConcurrentHashMap<>();
    private final List<ResourcePackage> resourcePackages = new ArrayList<>();
    private final Logger logger = LogManager.getLogger("Resource-Manager");

    public byte[] getResource(String path) {
        List<byte[]> collect = mapping.values().stream().map((mapping) -> mapping.get(path)).collect(Collectors.toList());
        if (collect.size() == 0) {
            return null;
        }
        return collect.get(collect.size() - 1);
    }

    public InputStream openResourceStream(String path) {
        byte[] resource = getResource(path);
        return resource == null ? null : new ByteArrayInputStream(resource);
    }

    public InputStream openResourceStream(Identifier entry) {
        @Nullable Resource resource = getResource(entry);
        return resource == null ? null : resource.openStream();
    }

    @Nullable
    public Resource getResource(Identifier entry) {
        for (ResourcePackage resourcePackage : resourcePackages) {
            if (resourcePackage.has(entry)) {
                return resourcePackage.get(entry);
            }
        }

        logger.warn("Unknown resource: " + entry);

        return null;
    }

    public void dump() {
        for (ResourcePackage resourcePackage : resourcePackages) {
            resourcePackage.dump();
        }
    }

    public void importResources(File file) {
        if (!file.exists()) {
            Game.getInstance().crash(new IOException("Resources file doesn't exists: " + file.getAbsolutePath()));
        }

        if (file.isFile()) {
            importFileResourcePackage(file);
        } else if (file.isDirectory()) {
            importDirResourcePackage(file);
        }
    }

    @SuppressWarnings("unused")
    private void importDirResourcePackage(File file) {
        // Check if it's a directory.
        assert file.isDirectory();

        try {
            // Prepare (entry -> resource) mappings/
            Map<Identifier, Resource> map = new HashMap<>();

            // Get assets directory.
            File assets = new File(file, "Assets");

            // Check if assets directory exists.
            if (assets.exists()) {
                // List files in assets dir.
                File[] files = assets.listFiles();

                // Loop listed files.
                for (File assetsPackage : files != null ? files : new File[0]) {
                    // Get assets-package namespace from the name of the listed file (that's a dir).
                    String namespace = assetsPackage.getName();

                    // Walk assets package.
                    Stream<Path> walk = Files.walk(assetsPackage.toPath());
                    for (Path assetPath : walk.toList()) {
                        // Convert to file object.
                        File asset = assetPath.toFile();

                        // Check if it's a file, if not we will walk to the next file / folder in the Files.walk(...) list.
                        if (!asset.isFile()) {
                            continue;
                        }

                        // Create resource with file input stream.
                        ThrowingSupplier<InputStream, IOException> sup = () -> new FileInputStream(asset);
                        Resource resource = new Resource(sup);

                        // Continue to next file / folder if asset path is the same path as the assets package.
                        if (assetPath.toFile().equals(assetsPackage)) {
                            continue;
                        }

                        // Calculate resource path.
                        Path relativize = assetsPackage.toPath().relativize(assetPath);
                        String s = relativize.toString().replaceAll("\\\\", "/");

                        // Create resource entry/
                        Identifier entry = new Identifier(namespace, s);

                        // MEME
                        boolean b = Person.MY_SELF.kill(Person.MY_SELF) == Emotion.LOL;

                        // Add resource mapping for (entry -> resource).
                        map.put(entry, resource);
                    }
                }

                resourcePackages.add(new ResourcePackage(map));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void importFileResourcePackage(File file) {
        // Check if it's a file.
        assert file.isFile();

        // Check for .jar files.
        if (file.getName().endsWith(".jar")) {
            try {
                // Prepare (entry -> resource) mappings.
                Map<Identifier, Resource> map = new HashMap<>();

                // Create jar file instance from file.
                JarFile jarFile = new JarFile(file);

                // Get jar entries, and convert it into an iterable to use in for(...) loops
                Enumeration<JarEntry> var0 = jarFile.entries();
                Iterable<JarEntry> entries = () -> Objects.requireNonNull(var0.asIterator());

                // Loop jar entries.
                for (JarEntry jarEntry : entries) {
                    // Get name of the jar entry.
                    String name = jarEntry.getName();
                    logger.error(name);

                    // Check if it isn't a directory, because we want a file.
                    if (!jarEntry.isDirectory()) {
                        String[] splitPath = name.split("/", 3);

                        if (splitPath.length >= 3) {
                            String assetsDirectoryName = splitPath[0];
                            if (assetsDirectoryName.equals("assets")) {
                                // Get namespace and path from split path
                                String namespace = splitPath[1];
                                String path = splitPath[2];

                                // Resource
                                ThrowingSupplier<InputStream, IOException> sup = () -> jarFile.getInputStream(jarEntry);
                                Resource resource = new Resource(sup);

                                // Entry
                                Identifier entry = new Identifier(namespace, path);

                                // Add (entry -> resource) mapping.
                                map.put(entry, resource);
                            }
                        }
                    }
                }

                resourcePackages.add(new ResourcePackage(map));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Deprecated
    public void loadResources(File file, JarFile jarFile) {
        Enumeration<JarEntry> entries = jarFile.entries();

        if (mapping.containsKey(file)) {
            throw new DuplicateElementException("File resources already loaded: " + file);
        }

        mapping.put(file, new PathMapping<>());

        @SuppressWarnings("NullableProblems")
        Iterable<JarEntry> jarEntries = entries::asIterator;

        List<Exception> exceptions = new ArrayList<>();
        for (JarEntry entry : jarEntries) {
            byte[] bytes;
            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(jarFile.getInputStream(entry));
                bytes = bufferedInputStream.readAllBytes();
            } catch (IOException e) {
                exceptions.add(e);
                continue;
            }

            Pattern compile = Pattern.compile("(?:/|)(assets)/([a-z]*)/([a-zA-Z0-9_/]+)");
            Matcher matcher = compile.matcher(entry.getName());
            String type = matcher.group(1);
            String namespace = matcher.group(2);
            String path = matcher.group(3);

            if (Objects.equals(type, "assets")) {
                Identifier res = new Identifier(namespace, path);
                assets.put(res, bytes);
            }

            mapping.get(file).map(entry.getName(), bytes);
        }

        if (!exceptions.isEmpty()) {
            RuntimeException e = new RuntimeException("Exception" + (exceptions.size() == 1 ? "" : "s") +
                    " while reading jar-file (see suppression" + (exceptions.size() == 1 ? "" : "s") + ": " + file.getAbsolutePath());
            for (Exception e1 : exceptions) {
                e.addSuppressed(e1);
            }

            throw e;
        }
    }

    public byte[] getAsset(Identifier identifier) {
        return assets.get(identifier);
    }

    public InputStream getAssetAsStream(Identifier identifier) {
        return new ByteArrayInputStream(getAsset(identifier));
    }

    private static class PathMapping<T> {
        private final Map<String, T> mapping = new ConcurrentHashMap<>();

        public PathMapping() {

        }

        public T get(String path) {
            return mapping.get(path);
        }

        public void map(String path, T type) {
            mapping.put(path, type);
        }

        public List<Map.Entry<String, T>> entries() {
            return new ArrayList<>(mapping.entrySet());
        }
    }
}
