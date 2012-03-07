public class C4Board {
	private int width;
	private int height;
	
	private static final int WINDISCS = 4;
	
	private int[][] board;
	
	public C4Board(int width, int height){
		this.width = width;
		this.height = height;
		
		this.board = new int[width][height];
	}
	
	public boolean canPutInColumn(int column) {
		return !columnIsFull(column);
	}
	
	public void putIn(int column, int playerId) {
		if (!canPutInColumn(column)) {
			System.out.println("Aww :(\n");
			return;
		}
		
		for (int row = 0; row < height; row++) {
			if (this.board[column][row] == 0) {
				this.board[column][row] = playerId;
				return;
			}
		}
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
			return -1;
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
			for (int row = height - 4; row < height; row++) {
				int tile = board[column][row];
				if (tile != 0 && tile == board[column + 1][row - 1] && tile == board[column + 2][row - 2] && tile == board[column + 3][row - 3]) {
					return tile;
				}
			}
		}
		
		return 0;
	}
	
	public String toString() {
		String boardtext = "";
		
		for (int row = height - 1; row > 0; row--) {
			for (int column = 0; column < width; column++) {
				int tile = board[column][row];
				
				boardtext += " " + tile;
			}
			
			boardtext += "\n";
		}
		
		return boardtext;
	}
}