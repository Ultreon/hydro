package com.ultreon.bubbles.common;

import java.util.Objects;

@SuppressWarnings("ConstantConditions")
public abstract class Registrable implements IRegistrable {
    private Identifier registryName = null;

    public void setRegistryName(String namespace, String name) {
        this.registryName = new Identifier(namespace, name);
    }

    public boolean isTempRegistryName() {
        return this.registryName.namespace() == null;
    }

    public void updateRegistryName(String namespace) {
        if (this.registryName.namespace() == null) {
            this.registryName = this.registryName.withNamespace(namespace);
        }
    }

    public Identifier getRegistryName() {
        return registryName;
    }

    public void setRegistryName(String name) {
        this.registryName = new Identifier(null, name);
    }

    public void setRegistryName(Identifier name) {
        this.registryName = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Registrable that = (Registrable) o;
        return Objects.equals(this.registryName, that.registryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.registryName);
    }
}
