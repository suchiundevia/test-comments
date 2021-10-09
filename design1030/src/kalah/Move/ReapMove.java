package kalah.Move;
import kalah.Move.Move;
import kalah.Player;
public class ReapMove extends Move {
    private int _targetHouse;
    public ReapMove(Player player, int houseToReap) {
        super(player, true);
        _targetHouse = houseToReap;
    }
    public int getHouseChoice() {
        return _targetHouse;
    }
}
