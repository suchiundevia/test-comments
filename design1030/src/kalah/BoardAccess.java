package kalah;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class BoardAccess {
    private Map<Player, Map<Integer, Integer>> _playerToHouseSeedMap;
    private Map<Player, Integer> _storeSeedMap;
    public BoardAccess(Map<Player, Map<Integer, Integer>> playerToHouseSeedMap, Map<Player, Integer> storeSeedMap) {
        _playerToHouseSeedMap = playerToHouseSeedMap;
        _storeSeedMap = storeSeedMap;
    }
    public int getPlayerScore(Player player){
        int score = 0;
        Map<Integer, Integer> houseSeedMap = _playerToHouseSeedMap.get(player);
        for (Integer id : houseSeedMap.keySet()) {
            score += houseSeedMap.get(id);
        }
        score += _storeSeedMap.get(player);
        return score;
    }
    public boolean isTie() {
        int maxScore = 0;
        for (Player player : _playerToHouseSeedMap.keySet()) {
            int score = getPlayerScore(player);
            if (maxScore < score) {
                maxScore = score;
            } else if (maxScore == score) {
                return true;
            }
        }
        return false;
    }
    public Player getWinner() {
        int maxScore = 0;
        Player winner = _playerToHouseSeedMap.keySet().iterator().next();
        for (Player player : _playerToHouseSeedMap.keySet()) {
            int score = getPlayerScore(player);
            if (maxScore < score) {
                maxScore = score;
                winner = player;
            }
        }
        return winner;
    }
    public List<Integer> getHousesSeeds(Player player) {
        List<Integer> houseSeeds = new ArrayList<>();
        Map<Integer, Integer> houseSeedMap = _playerToHouseSeedMap.get(player);
        for (Integer id : houseSeedMap.keySet()) {
            houseSeeds.add(houseSeedMap.get(id));
        }
        return houseSeeds;
    }
    public int getStoreSeeds(Player player) {
        return _storeSeedMap.get(player);
    }
}
