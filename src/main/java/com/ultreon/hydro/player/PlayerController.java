package com.ultreon.bubbles.player;

import com.ultreon.bubbles.input.KeyInput;
import com.ultreon.bubbles.input.KeyInput.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("ClassCanBeRecord")
public class PlayerController {
    private final BasePlayer player;

    private static final Logger logger = LogManager.getLogger("Player-Controller");

    public PlayerController(BasePlayer player) {
        this.player = player;
    }

    public void tick() {
//        logger.info("PlayerController[8c724942]: " + this.player);
        if (this.player != null) {
//            logger.info("PlayerController[8c217398]: " + this.player);
            this.player.forward(KeyInput.isDown(Map.KEY_UP) || KeyInput.isDown(Map.KEY_KP_UP) || KeyInput.isDown(Map.KEY_W));
            this.player.backward(KeyInput.isDown(Map.KEY_DOWN) || KeyInput.isDown(Map.KEY_KP_DOWN) || KeyInput.isDown(Map.KEY_S));
            this.player.right(KeyInput.isDown(Map.KEY_RIGHT) || KeyInput.isDown(Map.KEY_KP_RIGHT) || KeyInput.isDown(Map.KEY_D));
            this.player.left(KeyInput.isDown(Map.KEY_LEFT) || KeyInput.isDown(Map.KEY_KP_LEFT) || KeyInput.isDown(Map.KEY_A));
        }
    }
}
