package com.ultreon.hydro.event;

import com.ultreon.hydro.render.RenderSystem;

public abstract class RenderGameEvent extends RenderEvent {
    public RenderGameEvent(RenderSystem graphics) {
        super(graphics);
    }

    public static class Before extends RenderGameEvent {
        public Before(RenderSystem graphics) {
            super(graphics);
        }
    }

    public static class After extends RenderGameEvent {
        public After(RenderSystem graphics) {
            super(graphics);
        }
    }
}
