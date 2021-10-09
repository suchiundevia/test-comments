package kalah.Move;
import kalah.KalahException.MoveException;
import kalah.Move.Move;
import kalah.Player;
public class QuitMove extends Move {
    public QuitMove(Player player) {
        super(player, false);
    }
    @Override
    public int getHouseChoice() throws MoveException {
        throw new MoveException("Quit move doesn't have a house");
    }
}
