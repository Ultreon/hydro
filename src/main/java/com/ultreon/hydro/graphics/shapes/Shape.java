package com.ultreon.bubbles.graphics.shapes;

public interface Shape {
    boolean doIntersect(Shape shape) throws UnsupportedOperationException;
}