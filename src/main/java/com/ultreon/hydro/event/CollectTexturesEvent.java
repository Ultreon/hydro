package com.ultreon.bubbles.event;

import com.ultreon.bubbles.render.TextureCollection;

public class CollectTexturesEvent extends Event {
    private final TextureCollection textureCollection;

    public CollectTexturesEvent(TextureCollection textureCollection) {
        this.textureCollection = textureCollection;
    }

    public TextureCollection getTextureCollection() {
        return textureCollection;
    }
}
