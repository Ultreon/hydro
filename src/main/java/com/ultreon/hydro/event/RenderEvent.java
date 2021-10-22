package com.ultreon.hydro.event;

import com.ultreon.hydro.render.RenderSystem;

public abstract class RenderEvent extends Event {
    private final RenderSystem graphics;

    public RenderEvent(RenderSystem graphics) {
        this.graphics = graphics;
    }

    public RenderSystem graphics() {
        return graphics;
    }

}
