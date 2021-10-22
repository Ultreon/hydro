package com.ultreon.hydro.render;

import java.awt.*;
import java.awt.geom.AffineTransform;

class RenderState {
    private final AffineTransform transform;
    private final Color clearColor;
    private final Color color;
    private final Paint paint;
    private final Font font;
    private final Shape clip;
    private final Font fallbackFont;
    private final RenderSystem renderSystem;
    private final Composite composite;
    private final RenderingHints hints;
    private final Stroke stroke;
    private double translationX;
    private double translationY;

    RenderState(RenderSystem renderSystem) {
        this.renderSystem = renderSystem;
        this.clearColor = this.renderSystem.gg.getBackground();
        this.transform = this.renderSystem.gg.getTransform();
        this.color = this.renderSystem.gg.getColor();
        this.paint = this.renderSystem.gg.getPaint();
        this.font = this.renderSystem.gg.getFont();
        this.clip = this.renderSystem.gg.getClip();
        this.fallbackFont = this.renderSystem.fallbackFont;
        this.composite = this.renderSystem.gg.getComposite();
        this.hints = this.renderSystem.gg.getRenderingHints();
        this.stroke = this.renderSystem.gg.getStroke();
    }

    void revert() {
        this.renderSystem.translate(-translationX, -translationY);
        this.renderSystem.setTransform(transform);
        this.renderSystem.clearColor(clearColor);
        this.renderSystem.color(color);
        this.renderSystem.paint(paint);
        this.renderSystem.font(font);
        this.renderSystem.simpleClip(clip);
        this.renderSystem.fallbackFont(fallbackFont);
        this.renderSystem.composite(composite);
        this.renderSystem.hints(hints);
        this.renderSystem.stroke(stroke);
    }
}
