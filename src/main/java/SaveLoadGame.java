import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class SaveLoadGame {

    private final ObjectMapper mapper = new ObjectMapper();

    public void saveGame(Player[] player) {
        try {
            String serialized = mapper.writeValueAsString(player);
            Files.writeString(Paths.get("C:\\Java\\seaBattle\\src\\main\\resources\\save.json"), serialized);
        } catch (IOException e) {
            log.error("Failed to save game", e);
        }
    }

    public Player[] loadGame() {
        try {
            String serialized = Files.readString(Paths.get("C:\\Java\\seaBattle\\src\\main\\resources\\save.json"));
            return mapper.readValue(serialized, Player[].class);
        } catch (IOException e) {
            log.error("Failed to load game!", e);
            return null;
        }
    }
}
