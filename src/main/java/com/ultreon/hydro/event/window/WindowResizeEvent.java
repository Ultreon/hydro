package com.ultreon.hydro.event.window;

import com.ultreon.hydro.Window;
import com.ultreon.hydro.vector.Vec2f;
import com.ultreon.hydro.vector.Vec2i;

public class WindowResizeEvent extends WindowEvent {
    private final Vec2i oldSize;
    private final Vec2f newSize;

    public WindowResizeEvent(Window window, Vec2i oldSize, Vec2f newSize) {
        super(window);
        this.oldSize = oldSize;
        this.newSize = newSize;
    }

    public Vec2i getOldSize() {
        return oldSize;
    }

    public Vec2f getNewSize() {
        return newSize;
    }
}
