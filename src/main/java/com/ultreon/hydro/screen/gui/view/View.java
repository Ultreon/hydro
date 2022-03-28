package com.ultreon.bubbles.screen.gui.view;

import com.ultreon.bubbles.render.Renderer;
import com.ultreon.bubbles.screen.gui.Interactable;

public abstract class View extends Interactable {
    public Renderer containerGraphics;

    public View(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
}
