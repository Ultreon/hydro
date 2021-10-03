package com.ultreon.hydro.event;

import com.ultreon.hydro.Game;

@Deprecated
public class PauseTickEvent extends Event {
    private final Game main;

    public PauseTickEvent(Game main) {
        this.main = main;
    }

    public Game getMain() {
        return main;
    }

    /**
     * Get the current tick speed. (TPS)
     *
     * @return always 0.05d (20th of a second).
     * @see Game#getTps
     * @deprecated Is since bubble blaster 1.0 Build 0 always 0.05d and therefore not needed.
     */
    @Deprecated
    public double getDeltaTime() {
        return 0.05; // Is always a 20th of a second.
    }

    public double getTps() {
        return Game.getInstance().getTps();
    }
}
