package com.ultreon.bubbles.screen;

import com.ultreon.hydro.Game;
import com.ultreon.bubbles.input.KeyInput;
import com.ultreon.bubbles.render.Renderer;
import com.ultreon.bubbles.screen.gui.Container;
import com.ultreon.bubbles.screen.gui.Interactable;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("unused")
public abstract class Screen extends Container {
    private Interactable focusedInteractable;
    private int focusIndex = 0;

    public Screen() {
        super(0, 0, Game.getInstance().getWidth(), Game.getInstance().getHeight());
    }

    private boolean eventsActive;
    protected final Game game = Game.getInstance();

    public final void resize(int width, int height) {
        this.onResize(width, height);
        this.width = width;
        this.height = height;
    }

    protected void onResize(int width, int height) {

    }

    public void init(int width, int height) {
        this.init();
    }

    /**
     * Show Scene
     *
     * @author Qboi
     */
    public abstract void init();

    /**
     * Hide Scene
     * Hide scene, unbind events.
     *
     * @param to the next scene to go.
     * @return true to cancel change screen.
     * @author Qboi
     */
    public boolean onClose(Screen to) {
        return true;
    }

    @Override
    public void make() {
        eventsActive = true;
    }

    @Override
    public void destroy() {
        eventsActive = false;
    }

    @Override
    public boolean isValid() {
        return eventsActive;
    }

    public void onMouseExit() {
        if (this.hoveredInteractable != null) {
            this.hoveredInteractable.onMouseLeave();
            this.hoveredInteractable = null;
        }
    }

    @Override
    public void onKeyPress(int keyCode, char character) {
        if (keyCode == KeyInput.Map.KEY_TAB) {
            this.focusIndex++;
            onChildFocusChanged();
            return;
        }

        if (this.focusedInteractable != null) this.focusedInteractable.onKeyPress(keyCode, character);
    }

    @Override
    public void onKeyRelease(int keyCode, char character) {
        if (keyCode == KeyInput.Map.KEY_TAB) return;

        if (this.focusedInteractable != null) this.focusedInteractable.onKeyRelease(keyCode, character);
    }

    @Override
    public void onKeyType(int keyCode, char character) {
        if (keyCode == KeyInput.Map.KEY_TAB) return;

        if (this.focusedInteractable != null) this.focusedInteractable.onKeyType(keyCode, character);
    }

    public void onChildFocusChanged() {
        CopyOnWriteArrayList<Interactable> clone = new CopyOnWriteArrayList<>(children);
        if (this.focusIndex >= clone.size()) {
            this.focusIndex = 0;
        }

        this.focusedInteractable = clone.get(this.focusIndex);
    }

    public Interactable getFocusedWidget() {
        return focusedInteractable;
    }

    @Deprecated
    public void tick() {

    }

    public abstract void render(Game game, Renderer gg);

    @Override
    public final <T extends Interactable> T add(T widget) {
        this.children.add(widget);
        return widget;
    }

    public void renderGUI(Game game, Renderer gg) {
        for (Interactable interactable : this.children) {
            interactable.render(gg);
        }
    }

    public Cursor getDefaultCursor() {
        return Game.getInstance().getDefaultCursor();
    }

    public boolean doesPauseGame() {
        return true;
    }
}
