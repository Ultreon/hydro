package com.ultreon.hydro.registry.object;

import com.ultreon.hydro.common.IRegistrable;
import com.ultreon.hydro.common.Identifier;
import com.ultreon.hydro.common.Registrable;
import com.ultreon.hydro.registry.Registry;

import java.util.function.Supplier;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Registered<B extends IRegistrable> {
    private final Registry registry;
    private final Supplier<B> supplier;
    private final Identifier identifier;

    public <T extends B> Registered(Registry<?> registry, Supplier<B> supplier, Identifier identifier) {
        this.registry = registry;
        this.supplier = supplier;
        this.identifier = identifier;
    }

    public void register() {
        registry.register(identifier, (Registrable) ((Supplier) supplier).get());
    }

    @SuppressWarnings("unchecked")
    public B get() {
        return (B) registry.get(identifier);
    }
}
