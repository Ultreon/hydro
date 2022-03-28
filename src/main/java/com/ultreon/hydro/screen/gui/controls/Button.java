package com.ultreon.bubbles.screen.gui.controls;

import com.ultreon.bubbles.render.Renderer;
import com.ultreon.bubbles.screen.gui.Interactable;
import com.ultreon.bubbles.screen.gui.style.State;
import com.ultreon.bubbles.screen.gui.style.Style;
import com.ultreon.bubbles.vector.Vec2i;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;

@Deprecated
@SuppressWarnings("unused")
public class Button extends Interactable {
    private final Style style;
    private final MouseListener mouseListener;
    private final Set<State> state;
    protected final Rectangle2D buttonRectangle;
    protected Button instance = this;

    /**
     * Constructor for button.
     *
     * @param pos   the position.
     * @param style the style.
     */
    public Button(Vec2i pos, Style style) {
        this(pos, style, 96, 24);
    }

    /**
     * Constructor for button.
     *
     * @param pos   the position.
     * @param style the style.
     */
    public Button(Vec2i pos, Style style, int width, int height) {
        this(pos, new Vec2i(width, height), style);
    }

    /**
     * Get button states.
     *
     * @return a set of states.
     */
    public Set<State> getState() {
        return state;
    }

    public Button(Vec2i pos, Vec2i size, Style style) {
        super(pos.x, pos.y, size.x, size.y);
        this.buttonRectangle = new Rectangle2D.Float(pos.x, pos.y, size.x, size.y);
        this.state = new HashSet<>();
        this.state.add(State.NORMAL);
        this.style = style;
        this.mouseListener = new MouseListener();
    }

    protected class MouseListener extends MouseAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            if (buttonRectangle.contains(e.getPoint())) state.add(State.HOVERED);
            else state.remove(State.HOVERED);
        }
    }

    @Override
    public void render(Renderer renderer) {
        if (state.contains(State.PRESSED)) {
            renderer.color(style.getBackgroundColors().getPressed());
            renderer.stroke(new BasicStroke(style.getBorderWidths().getPressed()));
//            g.fill(buttonRectangle);
            renderer.fill(new Rectangle2D.Double(0, 0, width, height));
            renderer.color(style.getBorderColors().getPressed());
            renderer.outline(new Rectangle2D.Double(0, 0, width, height));
        } else if (state.contains(State.NORMAL)) {
            renderer.color(style.getBackgroundColors().getNormal());
            renderer.stroke(new BasicStroke(style.getBorderWidths().getNormal()));
            renderer.fill(new Rectangle2D.Double(0, 0, width, height));
            renderer.color(style.getBorderColors().getNormal());
            renderer.outline(new Rectangle2D.Double(0, 0, width, height));
        }
    }

    @Override
    public void make() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public boolean isValid() {
        return false;
    }

    public MouseListener getMouseListener() {
        return mouseListener;
    }
}
