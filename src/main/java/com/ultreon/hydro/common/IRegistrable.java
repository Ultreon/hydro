package com.ultreon.hydro.common;

public interface IRegistrable {
    Identifier getRegistryName();

    void setRegistryName(Identifier rl);

    void updateRegistryName(String namespace);

    boolean isTempRegistryName();
}
