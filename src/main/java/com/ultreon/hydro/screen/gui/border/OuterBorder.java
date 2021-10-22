package com.ultreon.hydro.screen.gui.border;

import com.ultreon.hydro.graphics.Insets;
import com.ultreon.hydro.render.RenderSystem;

import java.awt.*;

@SuppressWarnings("unused")
public class OuterBorder extends Border {
    public OuterBorder(com.ultreon.hydro.graphics.Insets insets) {
        super(insets);
    }

    public OuterBorder(int top, int left, int bottom, int right) {
        super(top, left, bottom, right);
    }


    /**
     * Paints a border.
     *
     * @param renderSystem the graphics.
     * @param x            the x-positon.
     * @param y            the y-position.
     * @param width        the width.
     * @param height       the height.
     */
    @Override
    public void paintBorder(RenderSystem renderSystem, int x, int y, int width, int height) {
        // Get insets
        Insets insets = getBorderInsets();

        // Save old paint.
        Paint oldPaint = renderSystem.getPaint();

        // Set paint.
        renderSystem.paint(getPaint());

        // Draw rectangles around the component, but do not draw
        // in the component area itself.
        renderSystem.rect(x - insets.left, y - insets.top, width + insets.left + insets.right, insets.top);
        renderSystem.rect(x - insets.left, y, insets.left, height);
        renderSystem.rect(x + width, y, insets.right, height);
        renderSystem.rect(x - insets.left, y + height, width + insets.left + insets.right, insets.bottom);

        // Set backup paint.
        renderSystem.paint(oldPaint);
    }
}
