package com.ultreon.bubbles.event.window;

import com.ultreon.hydro.Window;
import com.ultreon.bubbles.event.Event;

public class WindowEvent extends Event {
    private final Window window;

    public WindowEvent(Window window) {
        this.window = window;
    }

    public Window getWindow() {
        return window;
    }
}
