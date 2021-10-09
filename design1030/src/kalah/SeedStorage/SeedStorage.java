package kalah.SeedStorage;
import kalah.*;
import kalah.Action.Action;
import kalah.KalahException.InvalidInputException;
import kalah.KalahException.StorageNotFoundException;
import java.util.List;
import java.util.Map;
public abstract class SeedStorage {
    protected SeedCollection _seeds;
    protected SeedStorage _next;
    protected Player _owner;
    public SeedStorage(int initialSeeds, Player owner) {
        _seeds = new SeedCollection(initialSeeds);
        _owner = owner;
    }
    public void setNext(SeedStorage seedStorage) {
        _next = seedStorage;
    }
    public boolean isEmpty() {
        return _seeds.isEmpty();
    }
    public int harvestSeeds() {
        return _seeds.collectSeeds();
    }
    public int getSeeds() {
        return _seeds.getSeeds();
    }
    public SeedStorage sow(int seedsToSow, Player sourcePlayer) {
        int remainingSeeds = _seeds.sowSeed(seedsToSow);
        if (remainingSeeds == 0) {
            return this;
        } else {
            return _next.sow(remainingSeeds, sourcePlayer);
        }
    }
    protected boolean compareOwners(Player player) {
        return _owner.equals(player);
    }
    public abstract boolean checkEmptyPlayerHouses();
    public abstract Action createAction(
            int numPlayerHouses, Player currentPlayer, Store currentPlayerStore,
            List<Player> players, Map<Player, SeedStorage> firstStorageMap) throws StorageNotFoundException;
    public abstract SeedStorage getSeedStorageOfId(int id) throws StorageNotFoundException;
    public abstract SeedStorage reap(int targetHouseId) throws InvalidInputException;
}
