package com.ultreon.bubbles.common;

import com.ultreon.commons.annotation.MethodsReturnNonnullByDefault;
import com.ultreon.commons.exceptions.SyntaxError;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.regex.Pattern;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record Identifier(@NotNull String namespace, @NotNull String path) {
    public Identifier {
        if (!Pattern.matches("[a-z][a-z0-9_.]*", namespace)) {
            throw new SyntaxError("Namespace contains illegal characters: " + namespace);
        }

        if (!Pattern.matches("[A-Z][A-Za-z0-9_/.]*((\\.[a-z0-9_]*)|)", path)) {
            throw new SyntaxError("Path contains illegal characters: " + path);
        }
    }

    public Identifier(@NotNull String name) {
        this(verify(name, 0).split(":")[0], verify(name, 1).split(":")[1]);
    }

    private static String verify(String name, int id) {
        switch (id) {
            case 0 -> Objects.requireNonNull(name, "Expected non-null string, but got a null.");
            case 1 -> {
                String[] split = name.split(":");
                if (split.length != 2) {
                    throw new SyntaxError("Resource location syntax is invalid. Expected to find 1 colon but found " + (split.length - 1) + ".");
                }
            }
        }
        return name;
    }

    public static Identifier fromString(String name) {
        return new Identifier(name);
    }

    public static void testNamespace(String namespace) {
        if (!Pattern.matches("[a-z_]*", namespace)) {
            throw new SyntaxError("Namespace contains illegal characters: " + namespace);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier that = (Identifier) o;
        return namespace.equals(that.namespace) && path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, path);
    }

    @Override
    public String toString() {
        return namespace + ":" + path;
    }

    public Identifier withNamespace(String namespace) {
        return new Identifier(namespace, path);
    }
}
