package com.ultreon.bubbles.event.input;

import com.ultreon.hydro.Game;
import com.ultreon.bubbles.core.input.KeyboardInput;
import com.ultreon.bubbles.event.Event;
import com.ultreon.bubbles.event.type.KeyEventType;
import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * Keyboard Event
 * This event is used for handling keyboard input.
 *
 * @see KeyboardEvent
 * @see KeyboardInput
 * @see KeyEvent
 * @see KeyEventType
 */
public class KeyboardEvent extends Event {
    private final int extendedKeyCode;
    private final int keyCode;
    private final char keyChar;
    private final int modifiers;
    private final int keyLocation;
    private final long when;

    private final Game main;
    private final KeyboardInput controller;
    private final KeyEvent parentEvent;
    private final KeyEventType type;
    private final HashMap<Integer, Boolean> pressed = new HashMap<>();

    /**
     * Keyboard event, called from a specific scene.
     *
     * @param main       The {@link Game} instance.
     * @param controller The {@link KeyboardInput} instance.
     * @param event      The {@link KeyEvent} instance.
     * @param type       One of the {@link KeyEventType} constants.
     */
    public KeyboardEvent(Game main, @NotNull KeyboardInput controller, KeyEvent event, KeyEventType type) {
        this.main = main;
        this.type = type;
        this.controller = controller;
        this.parentEvent = event;
        this.keyCode = event.getKeyCode();
        this.extendedKeyCode = event.getExtendedKeyCode();
        this.keyChar = event.getKeyChar();
        this.modifiers = event.getModifiersEx();
        this.keyLocation = event.getKeyLocation();
        this.when = event.getWhen();
    }

    /**
     * Returns the Main instance used in the event.
     *
     * @return The Main instance.
     */
    public Game getMain() {
        return main;
    }

    /**
     * Returns the KeyboardController instance used in the event.
     *
     * @return The KeyboardController instance.
     */
    public KeyboardInput getController() {
        return controller;
    }

    public KeyEvent getParentEvent() {
        return parentEvent;
    }

    public KeyEventType getType() {
        return type;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public int getExtendedKeyCode() {
        return extendedKeyCode;
    }

    public char getKeyChar() {
        return keyChar;
    }

    public int getModifiers() {
        return modifiers;
    }

    public int getKeyLocation() {
        return keyLocation;
    }

    public long getWhen() {
        return when;
    }

    public HashMap<Integer, Boolean> getPressed() {
        return pressed;
    }
}
