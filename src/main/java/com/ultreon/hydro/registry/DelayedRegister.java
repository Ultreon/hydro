package com.ultreon.bubbles.registry;

import com.ultreon.bubbles.common.IRegistrable;
import com.ultreon.bubbles.common.Identifier;
import com.ultreon.bubbles.common.Registrable;
import com.ultreon.bubbles.event.SubscribeEvent;
import com.ultreon.bubbles.event.bus.GameEvents;
import com.ultreon.bubbles.event.registry.RegistryEvent;
import com.ultreon.bubbles.registry.object.Registered;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

public class DelayedRegister<T extends IRegistrable> {
    private final String modId;
    private final Registry<T> registry;
    private final ArrayList<HashMap.Entry<Identifier, Supplier<T>>> objects = new ArrayList<>();

    protected DelayedRegister(String modId, Registry<T> registry) {
        this.modId = modId;
        this.registry = registry;
    }

    public static <T extends Registrable> DelayedRegister<T> create(String modId, Registry<T> registry) {
        return new DelayedRegister<>(modId, registry);
    }

    public <C extends T> Registered<C> register(String key, Supplier<C> supplier) {
        Identifier rl = new Identifier(modId, key);

        objects.add(new HashMap.SimpleEntry<>(rl, supplier::get));

        return new Registered<C>(registry, supplier, rl);
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

        for (HashMap.Entry<Identifier, Supplier<T>> entry : objects) {
            T object = entry.getValue().get();
            Identifier rl = entry.getKey();

            if (!event.getRegistry().getType().isAssignableFrom(object.getClass())) {
                throw new IllegalArgumentException("Got invalid type in deferred register: " + object.getClass() + " expected assignable to " + event.getRegistry().getType());
            }

            event.getRegistry().register(rl, object);
            object.setRegistryName(rl);
        }
    }
}
