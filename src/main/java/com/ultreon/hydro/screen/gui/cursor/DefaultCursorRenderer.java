package com.ultreon.bubbles.screen.gui.cursor;

import com.ultreon.bubbles.render.Renderer;

import java.awt.*;

public class DefaultCursorRenderer extends CursorRenderer {
    public DefaultCursorRenderer() {
        super("default_cursor");
    }

    @Override
    public void draw(Renderer g) {
        Polygon poly = new Polygon(new int[]{0, 10, 5, 0}, new int[]{0, 12, 12, 16}, 4);

        g.hint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.color(Color.black);
        g.polygon(poly);
        g.color(Color.white);
        g.polygonLine(poly);
        g.dispose();
    }
}
