package com.ultreon.hydro;

import java.awt.*;

public class Hydro {
    private static final Hydro instance = new Hydro();
    private final GraphicsEnvironment environment;

    private Hydro() {
        this.environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    }

    public static Hydro get() {
        return instance;
    }

    public GraphicsEnvironment getEnvironment() {
        return environment;
    }

    public boolean registerFont(Font font) {
        return environment.registerFont(font);
    }
}
