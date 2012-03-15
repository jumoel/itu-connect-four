import behaviors.*;
import c4utility.*;

public class GameLogic implements IGameLogic {
    private int width = 0;
    private int height = 0;
    private int playerId;
	
	private c4utility.C4Board board;
	private IBehavior behavior;
	
    public GameLogic() {		
		behavior = new MiniMax(playerId);
	}
	
    public void initializeGame(int width, int height, int playerId) {
        this.width = width;
        this.height = height;
        this.playerId = playerId;
		
		board = new c4utility.C4Board(width, height);
    }
	
    public Winner gameFinished() {
        int winner = board.getWinner();
		
		if (winner == 1) {
			System.out.println(board);
			return Winner.PLAYER1;
		}
		
		if (winner == 2) {
			System.out.println(board);
			return Winner.PLAYER2;
		}
		
		if (winner == board.TIE) {
			System.out.println(board);
			return Winner.TIE;
		}
		
        return Winner.NOT_FINISHED;
    }


    public void insertCoin(int column, int playerId) {
		board.putIn(column, playerId);
    }

    public int decideNextMove() {
        return behavior.nextMove(board);
    }
}
