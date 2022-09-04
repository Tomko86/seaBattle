package com.spb.avenue.seaBattle;

import com.spb.avenue.seaBattle.process.Game;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {

    public static void main(String[] args) {
        log.info("The application is running.");
        Game.play();
        log.trace("The application is finished.");

    }
}