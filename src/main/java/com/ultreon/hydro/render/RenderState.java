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
    private final Renderer renderer;
    private final Composite composite;
    private final RenderingHints hints;
    private final Stroke stroke;
    private double translationX;
    private double translationY;

    RenderState(Renderer renderer) {
        this.renderer = renderer;
        this.clearColor = this.renderer.gg.getBackground();
        this.transform = this.renderer.gg.getTransform();
        this.color = this.renderer.gg.getColor();
        this.paint = this.renderer.gg.getPaint();
        this.font = this.renderer.gg.getFont();
        this.clip = this.renderer.gg.getClip();
        this.fallbackFont = this.renderer.fallbackFont;
        this.composite = this.renderer.gg.getComposite();
        this.hints = this.renderer.gg.getRenderingHints();
        this.stroke = this.renderer.gg.getStroke();
    }

    void revert() {
        this.renderer.translate(-translationX, -translationY);
        this.renderer.setTransform(transform);
        this.renderer.clearColor(clearColor);
        this.renderer.color(color);
        this.renderer.paint(paint);
        this.renderer.font(font);
        this.renderer.simpleClip(clip);
        this.renderer.fallbackFont(fallbackFont);
        this.renderer.composite(composite);
        this.renderer.hints(hints);
        this.renderer.stroke(stroke);
    }
}
