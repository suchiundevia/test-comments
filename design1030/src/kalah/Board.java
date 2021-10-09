package kalah;
import kalah.Action.Action;
import kalah.KalahException.InvalidInputException;
import kalah.KalahException.MoveException;
import kalah.KalahException.StorageNotFoundException;
import kalah.Move.Move;
import kalah.SeedStorage.SeedStorage;
import kalah.SeedStorage.House;
import kalah.SeedStorage.Store;
import kalah.View.BoardView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class Board {
    private final int START_ID = 1;
    private int _numPlayerHouses;
    private List<Player> _players;
    private Map<Player, SeedStorage> _firstHouseMap = new HashMap<>();
    private Map<Player, Store> _storeMap = new HashMap<>();
    private BoardView _view;
    public Board(int initialSeeds, int numPlayerHouses, List<Player> playerList, BoardView view) throws StorageNotFoundException {
        _players = playerList;
        _numPlayerHouses = numPlayerHouses;
        _view = view;
        for (Player player : playerList) {
            Store store = new Store(player);
            House house = new House(player, store, initialSeeds, numPlayerHouses, START_ID);
            _storeMap.put(player, store);
            _firstHouseMap.put(player, house);
        }
        connectStoreToHouse();
        updateView();
    }
    private void connectStoreToHouse() {
        for (Player player : _storeMap.keySet()) {
            Store store = _storeMap.get(player);
            SeedStorage opponentHouse = _firstHouseMap.get(player.getOpponent());
            store.connectToHouse(opponentHouse);
        }
    }
    public Player executeMove(Move move) throws InvalidInputException, StorageNotFoundException, MoveException {
        Player currentPlayer = move.getPlayer();
        int targetHouse = move.getHouseChoice();
        SeedStorage lastSeedStorage = _firstHouseMap.get(currentPlayer).reap(targetHouse);
        Store currentPlayerStore = _storeMap.get(currentPlayer);
        Action action = lastSeedStorage.createAction(_numPlayerHouses, currentPlayer, currentPlayerStore, _players, _firstHouseMap);
        currentPlayer = action.executeAction();
        updateView();
        return currentPlayer;
    }
    public boolean checkGameEndStatus(Player player) throws StorageNotFoundException {
        SeedStorage firstHouse = _firstHouseMap.get(player);
        if (firstHouse.checkEmptyPlayerHouses()) {
            return true;
        }
        return false;
    }
    public void displayEmptyHousePrompt() throws StorageNotFoundException {
        _view.displayEmptyHousePrompt();
        updateView();
    }
    public void displayGameOver() throws StorageNotFoundException {
        _view.showGameOver();
        updateView();
    }
    public void gameFinish() throws StorageNotFoundException {
        BoardAccess accessObject = new BoardAccess(generateHouseMap(), generateStoreMap());
        _view.showScore(_players, accessObject);
    }
    private void updateView() throws StorageNotFoundException {
        BoardAccess accessObject = new BoardAccess(generateHouseMap(), generateStoreMap());
        _view.update(accessObject, _players);
    }
    private Map<Player, Map<Integer,Integer>> generateHouseMap() throws StorageNotFoundException {
        Map<Player, Map<Integer,Integer>> idHouseMap = new HashMap<>();
        for(Player player : _players) {
            Map<Integer, Integer> houseMap = new HashMap<>();
            SeedStorage firstHouse = _firstHouseMap.get(player);
            for (int i = 1; i <= _numPlayerHouses;i++) {
                houseMap.put(i, firstHouse.getSeedStorageOfId(i).getSeeds());
            }
            idHouseMap.put(player, houseMap);
        }
        return idHouseMap;
    }
    private Map<Player, Integer> generateStoreMap() {
        Map<Player, Integer> storeSeedsMap = new HashMap<>();
        for (Player player : _players) {
            storeSeedsMap.put(player, _storeMap.get(player).getSeeds());
        }
        return storeSeedsMap;
    }
}
