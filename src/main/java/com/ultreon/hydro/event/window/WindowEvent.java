package com.ultreon.hydro.event.window;

import com.ultreon.hydro.Window;
import com.ultreon.hydro.event.Event;

public class WindowEvent extends Event {
    private final Window window;

    public WindowEvent(Window window) {
        this.window = window;
    }

    public Window getWindow() {
        return window;
    }
}
