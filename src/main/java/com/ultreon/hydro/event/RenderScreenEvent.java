package com.ultreon.bubbles.event;

import com.ultreon.bubbles.render.Renderer;
import com.ultreon.bubbles.screen.Screen;

public abstract class RenderScreenEvent extends RenderEvent {
    private final Screen screen;

    public RenderScreenEvent(Screen screen, Renderer graphics) {
        super(graphics);
        this.screen = screen;
    }

    public Screen getScreen() {
        return screen;
    }

    public static class Before extends RenderScreenEvent {
        public Before(Screen screen, Renderer graphics) {
            super(screen, graphics);
        }
    }

    public static class After extends RenderScreenEvent {
        public After(Screen screen, Renderer graphics) {
            super(screen, graphics);
        }
    }
}
