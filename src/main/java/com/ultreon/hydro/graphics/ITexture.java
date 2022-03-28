package com.ultreon.bubbles.graphics;

import com.ultreon.bubbles.render.Renderer;

public interface ITexture {
    void render(Renderer gg);

    int width();

    int height();
}
