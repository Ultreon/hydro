package com.ultreon.hydro.screen.gui.view;

import com.ultreon.hydro.render.Renderer;
import com.ultreon.hydro.screen.gui.Interactable;

public abstract class View extends Interactable {
    public Renderer containerGraphics;

    public View(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
}
