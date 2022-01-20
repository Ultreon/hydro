package com.ultreon.hydro;

import com.ultreon.commons.crash.CrashLog;

@SuppressWarnings("ClassCanBeRecord")
class GameExceptions implements Thread.UncaughtExceptionHandler {
    private final Game game;

    GameExceptions(Game game) {
        this.game = game;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        CrashLog crashLog = new CrashLog("Uncaught exception", throwable);
        game.crash(crashLog.createCrash());
    }
}
