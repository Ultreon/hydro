package com.ultreon.bubbles.event.window;

import com.ultreon.commons.lang.ICancellable;
import com.ultreon.hydro.Window;

public class WindowClosingEvent extends WindowEvent implements ICancellable {
    public WindowClosingEvent(Window window) {
        super(window);
    }
}
