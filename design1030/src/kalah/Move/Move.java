package kalah.Move;
import kalah.KalahException.MoveException;
import kalah.Player;
public abstract class Move {
    private Player _player;
    private boolean _shouldContinue;
    public Move(Player player, boolean shouldContinue) {
        _player = player;
        _shouldContinue = shouldContinue;
    }
    public void finishGame() {
        _shouldContinue = false;
    }
    public abstract int getHouseChoice() throws MoveException;
    public boolean checkForContinue() {
        return _shouldContinue;
    }
    public Player getPlayer() {
        return _player;
    }
}
