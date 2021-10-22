package com.ultreon.hydro.graphics;

import com.ultreon.hydro.render.RenderSystem;

public interface ITexture {
    void render(RenderSystem gg);

    int width();

    int height();
}
