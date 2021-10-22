package com.ultreon.hydro.registry;

import com.ultreon.hydro.common.IRegistryEntry;
import com.ultreon.hydro.common.RegistryEntry;
import com.ultreon.hydro.common.ResourceEntry;
import com.ultreon.hydro.event.SubscribeEvent;
import com.ultreon.hydro.event.bus.GameEvents;
import com.ultreon.hydro.event.registry.RegistryEvent;
import com.ultreon.hydro.registry.object.RegistryObject;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

public class DeferredRegister<T extends IRegistryEntry> {
    private final String modId;
    private final Registry<T> registry;
    private final ArrayList<HashMap.Entry<ResourceEntry, Supplier<T>>> objects = new ArrayList<>();

    protected DeferredRegister(String modId, Registry<T> registry) {
        this.modId = modId;
        this.registry = registry;
    }

    public static <T extends RegistryEntry> DeferredRegister<T> create(String modId, Registry<T> registry) {
        return new DeferredRegister<>(modId, registry);
    }

    public <C extends T> RegistryObject<C> register(String key, Supplier<C> supplier) {
        ResourceEntry rl = new ResourceEntry(modId, key);

        objects.add(new HashMap.SimpleEntry<>(rl, supplier::get));

        return new RegistryObject<C>(registry, supplier, rl);
    }

    public void register(GameEvents events) {
        LogManager.getLogger("Registration").info("Mod " + modId + " subscribes for register events of type: " + registry.getType().getName());
        events.subscribe(this);
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onRegister(RegistryEvent.Register<T> event) {
        if (!event.getRegistry().getType().equals(registry.getType())) {
            return;
        }

        LogManager.getLogger("Registration").info("Mod " + modId + " registration for: " + registry.getType().getName());

        for (HashMap.Entry<ResourceEntry, Supplier<T>> entry : objects) {
            T object = entry.getValue().get();
            ResourceEntry rl = entry.getKey();

            if (!event.getRegistry().getType().isAssignableFrom(object.getClass())) {
                throw new IllegalArgumentException("Got invalid type in deferred register: " + object.getClass() + " expected assignable to " + event.getRegistry().getType());
            }

            event.getRegistry().register(rl, object);
            object.setRegistryName(rl);
        }
    }
}
