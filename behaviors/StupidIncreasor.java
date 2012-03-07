package behaviors;

import c4utility.*;

public class StupidIncreasor implements IBehavior {
	
	private int nextMove;
	
	public StupidIncreasor() {
		this.nextMove = 3;
	}
	
	public int nextMove(C4Board board) {
        int thisMove = nextMove;
		
		while (board.columnIsFull(thisMove)) { thisMove = (thisMove + 1) % board.getWidth(); }
		
		nextMove = (thisMove + 1) % board.getWidth();
		
		return thisMove;
	}
}