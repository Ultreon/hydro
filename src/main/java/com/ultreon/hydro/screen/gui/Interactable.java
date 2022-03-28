package com.ultreon.bubbles.screen.gui;

import com.ultreon.bubbles.render.Renderer;
import com.ultreon.bubbles.vector.Vec2i;

import java.util.Objects;

@SuppressWarnings("unused")
public abstract class Interactable implements GuiElement {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    private final long hash;

    public Interactable(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.hash = System.nanoTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interactable that = (Interactable) o;
        return hash == that.hash;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash);
    }

    public abstract void render(Renderer renderer);

    public void onMouseClick(int x, int y, int button, int count) {

    }

    public void onMousePress(int x, int y, int button) {

    }

    public void onMouseRelease(int x, int y, int button) {

    }

    public void onMouseMove(int x, int y) {

    }

    public void onMouseDrag(int x, int y, int button) {

    }

    public void onMouseLeave() {

    }

    public void onMouseEnter(int x, int y) {

    }

    public void onMouseWheel(int x, int y, double rotation, int amount, int units) {

    }

    public void onKeyPress(int keyCode, char character) {

    }

    public void onKeyRelease(int keyCode, char character) {

    }

    public void onKeyType(int keyCode, char character) {

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        if (width < 0) {
            throw new IllegalArgumentException("Width should be positive.");
        }
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if (width < 0) {
            throw new IllegalArgumentException("Height should be positive.");
        }
        this.height = height;
    }

    public Vec2i getPos() {
        return new Vec2i(x, y);
    }

    public void setPos(int x, int y) {
        setX(x);
        setY(y);
    }

    public Vec2i getSize() {
        return new Vec2i(width, height);
    }

    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public void setBounds(int x, int y, int width, int height) {
        setPos(x, y);
        setSize(width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isWithinBounds(int x, int y) {
        return x >= this.x && y >= this.y && x <= this.x + width && y <= this.y + height;
    }

    public boolean isWithinBounds(Vec2i pos) {
        return pos.x >= this.x && pos.y >= this.y && pos.x <= this.x + width && pos.y <= this.y + height;
    }
}
