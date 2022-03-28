package com.ultreon.bubbles.event;

import com.ultreon.bubbles.render.Renderer;

public abstract class RenderEvent extends Event {
    private final Renderer graphics;

    public RenderEvent(Renderer graphics) {
        this.graphics = graphics;
    }

    public Renderer graphics() {
        return graphics;
    }

}
