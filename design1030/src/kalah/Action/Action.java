package kalah.Action;
import kalah.Player;
import java.util.Map;
public abstract class Action {
    private Player _nextPlayer;
    public Action(Player player) {
        _nextPlayer = player;
    }
    protected Player determineNextPlayer() {
        return _nextPlayer;
    }
    public abstract Player executeAction();
}
