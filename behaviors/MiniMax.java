package behaviors;

import c4utility.*;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class MiniMax implements IBehavior {
	private static int TIE_VALUE = 10;
	private static int MAX_DEPTH = 5;
	
	private enum Direction { HORIZONTAL, VERTICAL, DIAGONAL_UP, DIAGONAL_DOWN }
	
	private class Action {
		public int action;
		public int value;
		
		public Action(int action, int value) {
			this.action = action;
			this.value = value;
		}
		
		public String toString() {
			return "<A: " + action + ", V: " + value + ">";
		}
	}
	
	private class ActionList extends ArrayList<Action> {
		public ActionList() {
			super();
		}
		
		public Action getMax() {
			Action max = new Action(-1, Integer.MIN_VALUE);
			
			for (Action a : this) {
				if (a.value > max.value) {
					max.value = a.value;
					max.action = a.action;
				}
			}
			
			return max;
		}
		
		public Action getMin() {
			Action min = new Action(-1, Integer.MAX_VALUE);
			
			for (Action a : this) {
				if (a.value < min.value) {
					min.value = a.value;
					min.action = a.action;
				}
			}
			
			return min;
		}
	}
	
	private class MoveList extends ArrayList<Integer> {
		public MoveList() {
			super();
		}
	}
	
	private int playerId;
	
	public MiniMax(int playerId) {
		this.playerId = playerId;
	}
	
	public int nextMove(C4Board board) {
		return minimaxDecision(board).action;
	}
	
	
	private Action minimaxDecision(C4Board board) {
		MoveList validMoves = findMoves(board);
		ActionList actions = new ActionList();
		
		for(int move : validMoves) {
			actions.add(new Action(move, minimaxValue(board, move, otherPlayerId(playerId), MAX_DEPTH)));
		}
		
		System.out.println(actions);
		
		return actions.getMax();
	}
	/*
	private int heurestic2(C4Board board, int playerId) {
		int[][] data = copy(board.getData());
		
		int heuresticvalue = 0;
		
		for (Direction d : Direction.values()) {
			int f_start = 0, f_end = 0;
			int s_start = 0, s_end = 0;
			
			int col_diff = 0;
			int row_diff = 0;
			
			switch (d) {
			case HORIZONTAL:
				f_start = 0;
				f_end = board.getHeight();
				s_start = 0;
				s_end = board.getWidth() - 3;
				col_diff = 1;
				row_diff = 0;
				break;
			case VERTICAL:
				f_start = 0;
				f_end = board.getWidth();
				s_start = 0;
				s_end = board.getHeight() - 3;
				col_diff = 0;
				row_diff = 1;
				break;
			case DIAGONAL_UP:
				f_start = 0;
				f_end = board.getWidth() - 3;
				s_start = 0;
				s_end = board.getHeight() - 3;
				col_diff = 1;
				row_diff = 1;
				break;
			default: // DIAGONAL_DOWN
				f_start = 0;
				f_end = board.getWidth() - 3;
				s_start = 3;
				s_end = board.getHeight();
				col_diff = 1;
				row_diff = -1;
				break;
			}
			
			// TODO: Initialize start and end variables properly
			
			for (int f_iter = f_start; f_iter < f_end; f_iter++) {
				for (int s_iter = s_start; s_iter < s_end; s_iter++) {
					int in_a_row = 0;
					int max = 0;
					int startc = -1;
					int startr = -1;
					
					for (int offset = 0; offset < 4; offset++) {
						// TODO: determine value based on direction
						int colval = 0;
						int rowval = 0;
						int value = data[colval][rowval];
						
						if (value == playerId) {
							in_a_row += 1;
							
							if (in_a_row > max) {
								max = in_a_row;
								
								startc = colval - max*col_diff;
								startr = rowval - max*row_diff;
							}
						}
					}
				}
			}
		}
		
		return 0;
	}
	*/
	private int heurestic(C4Board board, int playerId) {
		int[][] data = copy(board.getData());
		
		int heuresticvalue = 0;
		
		// Check horizontal
		for (int row = 0; row < board.getHeight(); row++) {
			for (int column = 0; column < board.getWidth() - 3; column++) {
				int in_a_row = 0;
				int max = 0;
				int startc = -1;
				int startr = -1;
				
				for (int offset = 0; offset < 4; offset++) {
					int value = data[column + offset][row];
					
					if (value == playerId)
					{
						in_a_row += 1;
						if (in_a_row > max) {
							max = in_a_row;
							
							startc = column + offset - (max - 1);
							startr = row;
						}
					}
					else
					{
						in_a_row = 0;
					}
				}
				
				boolean isopen = false;
				
				// Calcuate, based on startr and startc, whether the sequence is open or not
				// An open sequence is a sequence, that has the possibility to be a sequence of four in a row
				
				for (int offset = 0; offset <= 4 - max; offset++) {
					if (startc - offset + 3 >= board.getWidth()) { continue; }
					if (startc - offset < 0) { break; }

					boolean seq = true;
					for (int i = 0; i < 4; i++) {
						int v = data[startc - offset + i][row]; 
						seq = seq && (v == 0 || v == playerId);
						
						if (!seq) { break; }
					}
					
					if (seq) { isopen = true; break; }
				}
			
				// Only add values for open sequences or sequences of four
				if (isopen || max == 4) {
					heuresticvalue += deltaheurestic(max);
				}
			}
		}

		// Check vertical
		for (int column = 0; column < board.getWidth(); column++) {
			for (int row = 0; row < board.getHeight() - 3; row++) {
				int in_a_row = 0;
				int max = 0;
				int startc = -1;
				int startr = -1;
				
				for (int offset = 0; offset < 4; offset++) {
					int value = data[column][row + offset];
					
					if (value == playerId)
					{
						in_a_row += 1;
						if (in_a_row > max) {
							max = in_a_row;
							
							startc = column;
							startr = row + offset - (max - 1);
						}
					}
					else
					{
						in_a_row = 0;
					}
				}

				
				boolean isopen = false;
				
				for (int offset = 0; offset <= 4 - max; offset++) {
					if (startr - offset + 3 >= board.getHeight()) { continue; }
					if (startr - offset < 0) { break; }

					boolean seq = true;
					for (int i = 0; i < 4; i++) {
						int v = data[column][startr - offset + i]; 
						seq = seq && (v == 0 || v == playerId);
						
						if (!seq) { break; }
					}
					
					if (seq) { isopen = true; break; }
				}
			
				// Only add values for open sequences or sequences of four
				if (isopen || max == 4) {
					heuresticvalue += deltaheurestic(max);
				}
			}
		}
		
		// Check diagonal
		for (int column = 0; column < board.getWidth() - 3; column++) {
			for (int row = 0; row < board.getHeight() - 3; row++) {
				int in_a_row = 0;
				int max = 0;
				int startc = -1;
				int startr = -1;
				
				for (int offset = 0; offset < 4; offset++) {
					int value = data[column + offset][row + offset];
					
					if (value == playerId)
					{
						in_a_row += 1;
						if (in_a_row > max) {
							max = in_a_row;
							
							startc = column + offset - (max - 1);
							startr = row + offset - (max - 1);
						}
					}
					else
					{
						in_a_row = 0;
					}
				}
				
				heuresticvalue += deltaheurestic(max);
			}
			
			for (int row = 3; row < board.getHeight(); row++) {
				int in_a_row = 0;
				int max = 0;
				int startc = -1;
				int startr = -1;
				
				for (int offset = 0; offset < 4; offset++) {
					int value = data[column + offset][row - offset];
					
					if (value == playerId)
					{
						in_a_row += 1;
						if (in_a_row > max) {
							max = in_a_row;
							
							startc = column + offset - (max - 1);
							startr = row - offset + (max - 1);
						}
					}
					else
					{
						in_a_row = 0;
					}
				}
				
				heuresticvalue += deltaheurestic(max);
			}
		}
		
		return heuresticvalue;
	}
	
	private int deltaheurestic(int max) {
		int h = (int)Math.pow(max, max);
		
		if (max == 4) { h = (int)Math.pow(h, max); }
		
		return h;
	}
	
	private int minimaxValue(C4Board board, int move, int currentPlayer, int depth) {
		C4Board newstate = new C4Board(copy(board.getData()));
		newstate.putIn(move, currentPlayer);
		
		int gamestate = newstate.getWinner();

		int opponent = otherPlayerId(currentPlayer);
		
		if (gamestate == currentPlayer) {
			return Integer.MAX_VALUE;
		}
		else if (gamestate == opponent) {
			return Integer.MIN_VALUE;
		}
		else if (gamestate == C4Board.TIE) {
			return TIE_VALUE;
		}
		
		if (depth == 0) {
			return heurestic(newstate, currentPlayer);
		}
		
		int value = Integer.MAX_VALUE;
		
		MoveList opponentMoves = findMoves(newstate);
		for (int opponentmove : opponentMoves) {
			value = Math.min(value, -minimaxValue(newstate, opponentmove, opponent, depth - 1));
		}
		
		return value;
	}
	
	
	/////// UTILITY FUNCTIONS

	private MoveList findMoves(C4Board board) {
		MoveList ml = new MoveList();
		
		for (int column = 0; column < board.getWidth(); column++) {
			if (board.canPutInColumn(column)) {
				ml.add(column);
			}
		}
		
		return ml;
	}
	
	private int otherPlayerId(int thisPlayerId) {
		if (thisPlayerId == 1) {
			return 2;
		}
		else {
			return 1;
		}
	}
	
	private int[][] copy(int[][] source) {
		int[][] destination = new int[source.length][];
		
		for (int i = 0; i < source.length; i++) {
			destination[i] = new int[source[i].length];
			
			for (int j = 0; j < source[i].length; j++) {
				destination[i][j] = source[i][j];
			}
		}
		
		return destination;
	}
	
	//////// END UTILITY FUNCTIONS
}