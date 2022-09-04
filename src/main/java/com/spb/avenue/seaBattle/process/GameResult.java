package com.spb.avenue.seaBattle.process;

import lombok.Data;

@Data
public class GameResult {
    private boolean isGameEnded;
    private ShortResult shotResult;

    public GameResult(boolean isGameEnded, ShortResult shotResult) {
        this.isGameEnded = isGameEnded;
        this.shotResult = shotResult;
    }
}
