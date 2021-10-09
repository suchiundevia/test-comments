package kalah.Action;
import kalah.Action.Action;
import kalah.Player;
import kalah.SeedStorage.SeedStorage;
import kalah.SeedStorage.Store;
public class CaptureAction extends Action {
    private SeedStorage _lastStorage;
    private Store _store;
    private SeedStorage _oppositeStorage;
    public CaptureAction(Player nextPlayer, SeedStorage lastStorage, SeedStorage oppositeStorage, Store store) {
        super(nextPlayer);
        _lastStorage = lastStorage;
        _oppositeStorage = oppositeStorage;
        _store = store;
    }
    @Override
    public Player executeAction() {
        _store.capture(_lastStorage, _oppositeStorage);
        return super.determineNextPlayer();
    }
}
