package c4utility;

public class C4Board {
	private int width;
	private int height;
	
	public static int TIE = 0;
	public static int NOWINNER = -1;
	
	private static final int WINDISCS = 4;
	
	private int[][] board;
	
	public C4Board(int width, int height){
		this.width = width;
		this.height = height;
		
		this.board = new int[width][height];
	}
	
	public C4Board(int[][] data) {
		this.width = data.length;
		this.height = data[0].length;
		this.board = data;
	}
	
	public int[][] getData() { return board; }
	
	public int getWidth() { return width; }
	
	public int getHeight() { return height; }
	
	public boolean canPutInColumn(int column) {
		return !columnIsFull(column);
	}
	
	public int putIn(int column, int playerId) {
		if (!canPutInColumn(column)) {
			return -1;
		}
		
		for (int row = 0; row < height; row++) {
			if (this.board[column][row] == 0) {
				this.board[column][row] = playerId;
				return row;
			}
		}
		
		return -1;
	}
	
	public boolean columnIsEmpty(int column) {
		return board[column][0] == 0;
	}
	
	public boolean columnIsFull(int column) {
		return board[column][height - 1] != 0;
	}
	
	public boolean isFull() {
		for (int column = 0; column < width; column++) {
			if (!columnIsFull(column)) {
				return false;
			}
		}
		
		return true;
	}
	
	public int getWinner() {
		if (isFull()) {
			return TIE;
		}
		
		// Vertical
		for (int column = 0; column < width; column++) {
			for (int row = 0; row < height - 3; row++) {
				int tile = board[column][row];
				if (tile != 0 && tile == board[column][row + 1] && tile == board[column][row + 2] && tile == board[column][row + 3]) {
					return tile;
				}
				
			}
		}
		
		// Horizontal
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width - 3; column++) {
				int tile = board[column][row];
				if (tile != 0 && tile == board[column + 1][row] && tile == board[column + 2][row] && tile == board[column + 3][row]) {
					return tile;
				}
			}
		}
		
		for (int column = 0; column < width - 3; column++) {
			// Increasing slope
			for (int row = 0; row < height - 3; row++) {
				int tile = board[column][row];
				if (tile != 0 && tile == board[column + 1][row + 1] && tile == board[column + 2][row + 2] && tile == board[column + 3][row + 3]) {
					return tile;
				}
			}
			
			// Decreasing slope
			for (int row = height - 3; row < height; row++) {
				int tile = board[column][row];
				if (tile != 0 && tile == board[column + 1][row - 1] && tile == board[column + 2][row - 2] && tile == board[column + 3][row - 3]) {
					return tile;
				}
			}
		}
		
		return NOWINNER;
	}
	
	public String toString() {
		String boardtext = "";
		
		for (int row = height - 1; row >= 0; row--) {
			for (int column = 0; column < width; column++) {
				int tile = board[column][row];
				
				boardtext += " " + tile;
			}
			
			boardtext += "\n";
		}
		
		return boardtext;
	}
}