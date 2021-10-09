package kalah.Move;
import kalah.KalahException.InvalidInputException;
import kalah.Player;
public class MoveFactory {
    public Move makeMove(String move, Player player, int numHouses) throws InvalidInputException {
        try {
            if("Q".equalsIgnoreCase(move)) {
                return new QuitMove(player);
            } else {
                int selectedHouseId = Integer.parseInt(move);
                if (selectedHouseId < 1 || selectedHouseId > numHouses) {
                    throw new InvalidInputException("Could not find specified house number");
                } else {
                    return new ReapMove(player, selectedHouseId);
                }
            }
        } catch (NumberFormatException e){
            throw new InvalidInputException("Invalid command. Please enter a valid house number or 'q' to quit .");
        }
    }
}
