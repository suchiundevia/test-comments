package kalah;
import com.qualitascorpus.testsupport.IO;
import kalah.KalahException.InvalidInputException;
import kalah.Move.Move;
import kalah.Move.MoveFactory;
import kalah.View.BoardView;
public class Player {
    private final int _id;
    private Player _opponent;
    private int _numHousesOwned;
    private MoveFactory _moveFactory;
    public Player (int id, int numPlayerHouses) {
        _id = id;
        _numHousesOwned = numPlayerHouses;
        _moveFactory = new MoveFactory();
    }
    public Player getOpponent() {
        return _opponent;
    }
    public void setOpponent(Player player) {
        _opponent = player;
    }
    public Move play(BoardView view) {
        String input = view.promptInput(toString());
        try {
            return _moveFactory.makeMove(input, this, _numHousesOwned);
        } catch (InvalidInputException e) {
            view.displayInvalidInput(e);
            return play(view);
        }
    }
    @Override
    public String toString() {
        return  ""+_id;
    }
}