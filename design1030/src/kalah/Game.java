package kalah;
import com.qualitascorpus.testsupport.IO;
import kalah.KalahException.InvalidInputException;
import kalah.KalahException.MoveException;
import kalah.KalahException.StorageNotFoundException;
import kalah.Move.Move;
import kalah.View.BoardView;
import java.util.ArrayList;
import java.util.List;
public class Game {
    private Board _board;
    private List<Player> _playerList;
    private int _initialSeeds;
    private int _numPlayerHouses;
    private BoardView _boardView;
    public Game(int initialSeeds, int numPlayerHouses, int numPlayers, BoardView boardView) throws StorageNotFoundException {
        createPlayers(numPlayers, numPlayerHouses);
        setOpponents();
        _initialSeeds = initialSeeds;
        _numPlayerHouses = numPlayerHouses;
        _boardView = boardView;
        _board = new Board(_initialSeeds, _numPlayerHouses, _playerList, _boardView);
    }
    private void createPlayers(int numPlayers, int numPlayerHouses) {
        _playerList = new ArrayList<>();
        for(int i = 1; i <= numPlayers; i++) {
            _playerList.add(new Player(i, numPlayerHouses));
        }
    }
    private void setOpponents() {
        for (int i = 0; i < _playerList.size(); i++) {
            int opponentIndex = (i + 1) % _playerList.size();
            Player opponent = _playerList.get(opponentIndex);
            Player player = _playerList.get(i);
            player.setOpponent(opponent);
        }
    }
    public void play() throws StorageNotFoundException {
        Player currentPlayer = _playerList.get(0);
        boolean gameCompleted = false;
        Move nextMove = currentPlayer.play(_boardView);
        while (nextMove.checkForContinue()) {
            try {
                currentPlayer = _board.executeMove(nextMove);
                if (_board.checkGameEndStatus(currentPlayer)) {
                    gameCompleted = true;
                    nextMove.finishGame();
                } else {
                    nextMove = currentPlayer.play(_boardView);
                }
            } catch (InvalidInputException e){
                _board.displayEmptyHousePrompt();
                nextMove = currentPlayer.play(_boardView);
            } catch (MoveException e) {
                _board = new Board(_initialSeeds, _numPlayerHouses, _playerList, _boardView);
            }
        }
        _board.displayGameOver();
        if (gameCompleted) {
            _board.gameFinish();
        }
    }
}