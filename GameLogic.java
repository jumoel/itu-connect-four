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
			return Winner.PLAYER1;
		}
		
		if (winner == 2) {
			return Winner.PLAYER2;
		}
		
		if (winner == -1) {
			return Winner.TIE;
		}
		
        return Winner.NOT_FINISHED;
    }


    public void insertCoin(int column, int playerId) {
		board.putIn(column, playerId);
		System.out.println(board);
    }

    public int decideNextMove() {
        return behavior.nextMove(board);
    }
}
