package com.ultreon.bubbles.event.registry;

import com.ultreon.bubbles.common.IRegistrable;
import com.ultreon.bubbles.event.Event;
import com.ultreon.bubbles.registry.Registry;

import java.util.Objects;

public class RegistryEvent {
    public static class Register<T extends IRegistrable> extends Event {
        private final Registry<T> registry;

        public Register(Registry<T> registry) {
            super();
            this.registry = registry;
        }

        public Registry<T> getRegistry() {
            return registry;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Register<?> register = (Register<?>) o;
            return Objects.equals(getRegistry().getType(), register.getRegistry().getType());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getRegistry().getType());
        }
    }

    public static class Dump extends Event {
        public Dump() {
            super();
        }
    }
}
