package com.ultreon.bubbles.core.input;

import com.ultreon.hydro.Game;
import com.ultreon.bubbles.event.bus.GameEvents;
import com.ultreon.bubbles.event.input.KeyboardEvent;
import com.ultreon.bubbles.event.type.KeyEventType;
import com.ultreon.bubbles.screen.Screen;
import com.ultreon.bubbles.screen.ScreenManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @see MouseInput
 * @see java.awt.event.KeyAdapter
 */
public abstract class KeyboardInput extends KeyAdapter {
    private final Set<Integer> keysDown = new CopyOnWriteArraySet<>();
    private final Game game;

    private static final Logger logger = LogManager.getLogger("Game-Input");

    public KeyboardInput() {
        logger.info("test");
        logger.info("test");
        this.game = Game.getInstance();
    }

    public final boolean isKeyDown(int keyCode) {
        return keysDown.contains(keyCode);
    }

    @Override
    public final void keyPressed(KeyEvent e) {
        if (GameEvents.get() != null) {
            if (isKeyDown(e.getKeyCode())) {
                GameEvents.get().publish(new KeyboardEvent(Game.getInstance(), this, e, KeyEventType.HOLD));
            } else {
                GameEvents.get().publish(new KeyboardEvent(Game.getInstance(), this, e, KeyEventType.PRESS));
                this.keysDown.add(e.getKeyCode());
            }
        }

//        logger.info("KeyInput: PRESS (" + e.getKeyCode() + ", " + e.getKeyChar() + ")");

        ScreenManager screenManager = this.game.getScreenManager();
        if (screenManager != null) {
            Screen currentScreen = screenManager.getCurrentScreen();
            if (currentScreen != null) {
                currentScreen.onKeyPress(e.getKeyCode(), e.getKeyChar());
            }
        }
    }

    @Override
    public final void keyReleased(KeyEvent e) {
        this.keysDown.remove(e.getKeyCode());
        if (GameEvents.get() != null) {
            GameEvents.get().publish(new KeyboardEvent(Game.getInstance(), this, e, KeyEventType.RELEASE));
        }

//        logger.info("KeyInput: RELEASE (" + e.getKeyCode() + ", " + e.getKeyChar() + ")");

        ScreenManager screenManager = this.game.getScreenManager();
        if (screenManager != null) {
            Screen currentScreen = screenManager.getCurrentScreen();
            if (currentScreen != null) {
                currentScreen.onKeyRelease(e.getKeyCode(), e.getKeyChar());
            }
        }
    }

    @Override
    public final void keyTyped(KeyEvent e) {
        if (GameEvents.get() != null) {
            GameEvents.get().publish(new KeyboardEvent(Game.getInstance(), this, e, KeyEventType.TYPE));
        }

//        logger.info("KeyInput: TYPE (" + e.getKeyCode() + ", " + e.getKeyChar() + ")");

        ScreenManager screenManager = this.game.getScreenManager();
        if (screenManager != null) {
            Screen currentScreen = screenManager.getCurrentScreen();
            if (currentScreen != null) {
                currentScreen.onKeyType(e.getKeyCode(), e.getKeyChar());
            }
        }
    }
}
