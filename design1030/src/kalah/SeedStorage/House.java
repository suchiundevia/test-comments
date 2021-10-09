package kalah.SeedStorage;
import kalah.*;
import kalah.Action.Action;
import kalah.Action.CaptureAction;
import kalah.Action.SwitchPlayerAction;
import kalah.KalahException.InvalidInputException;
import kalah.KalahException.StorageNotFoundException;
import java.util.List;
import java.util.Map;
public class House extends SeedStorage {
    private int _id;
    private final int ID_INCREMENT = 1;
    public House(Player player, Store store, int initialSeeds, int numPlayerHouses, int id) {
        super(initialSeeds, player);
        _id = id;
        SeedStorage nextStorage;
        if (id==numPlayerHouses) {
            nextStorage = store;
        } else {
            nextStorage = new House(player, store, initialSeeds, numPlayerHouses, id+ID_INCREMENT);
        }
        super.setNext(nextStorage);
    }
    @Override
    public boolean checkEmptyPlayerHouses() {
        if(!_seeds.isEmpty()) {
            return false;
        }
        return _next.checkEmptyPlayerHouses();
    }
    @Override
    public Action createAction(
            int numPlayerHouses, Player currentPlayer, Store currentPlayerStore,
            List<Player> players, Map<Player, SeedStorage> firstStorageMap) throws StorageNotFoundException {
        Player nextPlayer = players.get((players.indexOf(currentPlayer)+1)%players.size());
        int oppositeId = numPlayerHouses - _id + 1;
        SeedStorage oppositeStorage = firstStorageMap.get(_owner.getOpponent()).getSeedStorageOfId(oppositeId);
        if(this.compareOwners(currentPlayer) && _seeds.hasOneLeft()) {
            if (!oppositeStorage.isEmpty()) {
                return new CaptureAction(nextPlayer, this, oppositeStorage, currentPlayerStore);
            }
        }
        return new SwitchPlayerAction(nextPlayer);
    }
    @Override
    public SeedStorage getSeedStorageOfId(int id) throws StorageNotFoundException {
        if (_id == id) {
            return this;
        } else {
            return _next.getSeedStorageOfId(id);
        }
    }
    @Override
    public SeedStorage reap(int targetHouseId) throws InvalidInputException {
        if (_id == targetHouseId) {
            if (_seeds.isEmpty()) {
                throw new InvalidInputException("Selected house has no seeds left!");
            }
            int seedsToSow = _seeds.collectSeeds();
            SeedStorage lastSeedStorage = _next.sow(seedsToSow, _owner);
            return lastSeedStorage;
        } else {
            return _next.reap(targetHouseId);
        }
    }
}