package com.ultreon.bubbles.event.screen;

import com.ultreon.bubbles.screen.Screen;

public abstract class ScreenEvent {
    private final Screen screen;

    public ScreenEvent(Screen screen) {
        this.screen = screen;
    }

    public Screen getScreen() {
        return screen;
    }
}
