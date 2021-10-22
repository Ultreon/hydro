package com.ultreon.hydro.screen.gui.view;

import com.ultreon.hydro.render.RenderSystem;
import com.ultreon.hydro.screen.gui.Widget;

public abstract class View extends Widget {
    public RenderSystem containerGraphics;

    public View(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
}
