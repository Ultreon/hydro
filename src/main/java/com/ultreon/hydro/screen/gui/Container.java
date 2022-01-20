package com.ultreon.hydro.screen.gui;

import com.ultreon.hydro.render.Renderer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class Container extends Interactable {
    protected final List<Interactable> children = new ArrayList<>();
    protected Interactable hoveredInteractable;
    private static final Logger logger = LogManager.getLogger("Widget-Containment");

    public Container(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void render(Renderer renderer) {
        Renderer containment = renderer.subInstance(this.x, this.y, this.width, this.height);
        renderChildren(containment);
    }

    protected void renderChildren(Renderer renderer) {
        for (Interactable child : this.children) {
            child.render(renderer);
        }
    }

    public <T extends Interactable> T add(T child) {
        this.children.add(child);
        return child;
    }

    public void remove(Interactable child) {
        this.children.remove(child);
    }

    @Nullable
    public Interactable getWidgetAt(int x, int y) {
//        logger.info("Container[c7f17d76]: CHILDREN" + this.children);
        for (Interactable child : this.children) {
//            logger.info("Container[b610e134]: X(" + x + ") : Y(" + y + ") : CONTAINS(" + child.getX() + "," + child.getY() + "," + child.isWithinBounds(x, y) + ")");
            if (child.isWithinBounds(x, y)) return child;
        }
        return null;
    }

    @Override
    public void onMouseClick(int x, int y, int button, int count) {
        logger.info("Container[aeb7abd0]: MOUSE CLICK EVENT RECEIVED (" + x + ", " + y + ", " + button + ", " + count + ")");
        Interactable interactable = getWidgetAt(x, y);
        if (interactable != null) interactable.onMouseClick(x, y, button, count);
    }

    @Override
    public void onMousePress(int x, int y, int button) {
        Interactable interactable = getWidgetAt(x, y);
        if (interactable != null) interactable.onMousePress(x, y, button);
    }

    @Override
    public void onMouseRelease(int x, int y, int button) {
        Interactable interactable = getWidgetAt(x, y);
        if (interactable != null) interactable.onMouseRelease(x, y, button);
    }

    @Override
    public void onMouseMove(int x, int y) {
        boolean widgetChanged = false;
        if (this.hoveredInteractable != null && !this.hoveredInteractable.isWithinBounds(x, y)) {
            this.hoveredInteractable.onMouseLeave();
        }

        Interactable interactableAt = this.getWidgetAt(x, y);
        if (interactableAt != this.hoveredInteractable) widgetChanged = true;
        this.hoveredInteractable = interactableAt;

        if (this.hoveredInteractable != null) {
            this.hoveredInteractable.onMouseMove(x, y);

            if (widgetChanged) {
                this.hoveredInteractable.onMouseEnter(x, y);
            }
        }
    }

    @Override
    public void onMouseDrag(int x, int y, int button) {
        Interactable interactable = getWidgetAt(x, y);
        if (interactable != null) interactable.onMouseDrag(x, y, button);
    }

    @Override
    public void onMouseLeave() {
        if (this.hoveredInteractable != null) {
            this.hoveredInteractable.onMouseLeave();
            this.hoveredInteractable = null;
        }
    }

    @Override
    public void onMouseWheel(int x, int y, double rotation, int amount, int units) {
        Interactable interactable = getWidgetAt(x, y);
        if (interactable != null) interactable.onMouseWheel(x, y, rotation, amount, units);
    }

    public Interactable getHoveredWidget() {
        return hoveredInteractable;
    }
}
