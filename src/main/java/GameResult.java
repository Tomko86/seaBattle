import lombok.Data;

@Data
public class GameResult {

    private boolean isGameEnded;
    private Condition shotResult;
    private String coordinate;

    public GameResult(boolean isGameEnded, Condition shotResult) {
        this.isGameEnded = isGameEnded;
        this.shotResult = shotResult;
    }

    public GameResult(boolean isGameEnded, Condition shotResult, String coordinate) {
        this.isGameEnded = isGameEnded;
        this.shotResult = shotResult;
        this.coordinate = coordinate;
    }
}
