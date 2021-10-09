package kalah;
import com.qualitascorpus.testsupport.IO;
import com.qualitascorpus.testsupport.MockIO;
import kalah.KalahException.StorageNotFoundException;
import kalah.View.BoardView;
import kalah.View.ConsoleView;
public class Kalah {
	public static final int INITIAL_SEEDS = 4;
	public static final int HOUSE_PER_PLAYER = 6;
	public static final int NUM_PLAYERS = 2;
	public static void main(String[] args) {
		new Kalah().play(new MockIO());
	}
	public void play(IO io) {
		BoardView boardView = new ConsoleView(io);
		Game game = null;
		try {
			game = new Game(INITIAL_SEEDS, HOUSE_PER_PLAYER, NUM_PLAYERS, boardView);
			game.play();
		} catch (StorageNotFoundException e) {
			e.printStackTrace();
		}
	}
}
