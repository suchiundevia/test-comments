package kalah.View;
import java.util.List;
import java.util.Map;
import com.qualitascorpus.testsupport.IO;
import kalah.BoardAccess;
import kalah.KalahException.InvalidInputException;
import kalah.Player;
import kalah.SeedStorage.SeedStorage;
import kalah.SeedStorage.Store;
import kalah.View.BoardView;
public class ConsoleView implements BoardView {
    private IO _io;
    public ConsoleView(IO io) {
        _io = io;
    }
    @Override
    public void update(BoardAccess accessObject, List<Player> players) {
        Player player1 = players.get(0);
        Player player2 = players.get(1);
        _io.println(createBorder());
        _io.println(createPlayer2Board(player2, accessObject));
        _io.println("|    |-------+-------+-------+-------+-------+-------|    |");
        _io.println(createPlayer1Board(player1, accessObject));
        _io.println(createBorder());
    }
    @Override
    public void showGameOver() {
        _io.println("Game over");
    }
    @Override
    public void displayEmptyHousePrompt() {
        _io.println("House is empty. Move again.");
    }
    @Override
    public void showScore(List<Player> players, BoardAccess accessObject) {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            _io.println("\tplayer "+player.toString()+":"+accessObject.getPlayerScore(player));
        }
        if (accessObject.isTie()) {
            _io.println("A tie!");
        } else {
            _io.println("Player "+accessObject.getWinner().toString() + " wins!");
        }
    }
    @Override
    public String promptInput(String playerId) {
        String promptString = String.format("Player P%s's turn - Specify house number or 'q' to quit: ", playerId);
        return _io.readFromKeyboard(promptString);
    }
    @Override
    public void displayInvalidInput(InvalidInputException e) {
        _io.print(e.getMessage());
    }
    private String createPlayer2Board(Player player, BoardAccess accessObject) {
        StringBuilder sb = new StringBuilder();
        sb.append("| P"+player.toString()+" |");
        List<Integer> houseSeeds = accessObject.getHousesSeeds(player);
        for (int i = houseSeeds.size()-1; i>=0; i--) {
            sb.append(String.format(" %s[%2s] |", i+1, houseSeeds.get(i)));
        }
        sb.append(String.format(" %2s |", accessObject.getStoreSeeds(player.getOpponent())));
        return sb.toString();
    }
    private String createPlayer1Board(Player player, BoardAccess accessObject) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("| %2s |", accessObject.getStoreSeeds(player.getOpponent())));
        List<Integer> houseSeeds = accessObject.getHousesSeeds(player);
        for (int i = 0; i<houseSeeds.size(); i++) {
            sb.append(String.format(" %s[%2s] |", i+1, houseSeeds.get(i)));
        }
        sb.append(" P"+player.toString()+" |");
        return sb.toString();
    }
    private String createBorder() {
        return "+----+-------+-------+-------+-------+-------+-------+----+";
    }
}
