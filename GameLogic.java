
public class GameLogic implements IGameLogic {
    private int width = 0;
    private int height = 0;
    private int playerId;
	
	private C4Board board;
	
	private int nextMove;
    
    public GameLogic() {
		nextMove = 0;
	}
	
    public void initializeGame(int width, int height, int playerId) {
        this.width = width;
        this.height = height;
        this.playerId = playerId;
		
		board = new C4Board(width, height);
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
        int thisMove = nextMove;
		
		nextMove = (nextMove + 1) % width;
		
		return thisMove;
    }

}
