package com.ultreon.hydro.resources;

import com.ultreon.hydro.common.ResourceEntry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ResourcePackage {
    private final Map<ResourceEntry, Resource> resources;

    public ResourcePackage(Map<ResourceEntry, Resource> resources) {
        this.resources = resources;
    }

    public ResourcePackage() {
        resources = new HashMap<>();
    }

    public boolean has(ResourceEntry entry) {
        return resources.containsKey(entry);
    }

    public Set<ResourceEntry> entries() {
        return resources.keySet();
    }

    public Resource get(ResourceEntry entry) {
        return resources.get(entry);
    }
}
