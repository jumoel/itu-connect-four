package c4utility.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import c4utility.C4Board;

public class C4BoardTest {

	private C4Board empty;
	private C4Board full;

	private static int WIDTH = 7;
	private static int HEIGHT = 6;
	
	@Before
	public void setUp() {
		empty = new C4Board(WIDTH, HEIGHT);
		full = new C4Board(WIDTH, HEIGHT);
		
		for (int column = 0; column < WIDTH; column++) {
			for (int row = 0; row < HEIGHT; row++) {
				full.putIn(column, 1);
			}
		}
	}

	@Test
	public void testCanPutInColumns() {
		assertTrue(!empty.isFull());
		assertTrue(full.isFull());
		
		for (int column = 0; column < WIDTH; column++) {
			assertTrue("Should be able to insert into Column " + column + " in empty board", empty.canPutInColumn(column));
			
			assertFalse("Should not be able to insert into Column " + column + " in full board", full.canPutInColumn(column));
		}
	}
	
	@Test
	public void testPutIn() {
		assertTrue(!empty.isFull());
		
		for (int column = 0; column < WIDTH; column++) {
			int height = -1;
			
			for (int row = 0; row < HEIGHT; row++) {
				height = empty.putIn(column, 1);
;				assertTrue("Could not insert into Column " + column + " in empty board. Row " + row + " should be free.", height == row);
			}
			
			height = empty.putIn(column, 1);
			assertTrue("Could insert into Column " + column + " where it should have been full", height == -1);
		}
	}
	
	@Test
	public void testVictoryHorizontal() {
		int[][] data;
		
		for (int column = 0; column < WIDTH - 3; column++) {
			for (int row = 0; row < HEIGHT; row++) {
				
				data = new int[WIDTH][HEIGHT];
				data[column][row] = data[column + 1][row] = data[column + 2][row] = data[column + 3][row] = 1;
				C4Board b1 = new C4Board(data);
				
				assertTrue(b1.toString(), b1.getWinner() == 1);
				
				data = new int[WIDTH][HEIGHT];
				data[column][row] = data[column + 1][row] = data[column + 2][row] = data[column + 3][row] = 2;
				C4Board b2 = new C4Board(data);
				
				assertTrue(b2.toString(), b2.getWinner() == 2);
			}
		}
	}
	
	@Test
	public void testVictoryVertical() {
		int[][] data;
		
		for (int row = 0; row < HEIGHT - 3; row++) {
			for (int column = 0; column < WIDTH; column++) {

				data = new int[WIDTH][HEIGHT];
				data[column][row] = data[column][row + 1] = data[column][row + 2] = data[column][row + 3] = 1;
				C4Board b1 = new C4Board(data);
				
				assertTrue(b1.toString(), b1.getWinner() == 1);
				
				data = new int[WIDTH][HEIGHT];
				data[column][row] = data[column][row + 1] = data[column][row + 2] = data[column][row + 3] = 2;
				C4Board b2 = new C4Board(data);
				
				assertTrue(b2.toString(), b2.getWinner() == 2);
			}
		}
	}
	
	@Test
	public void testVictoryDiagonal() {
		int[][] data;
		
		for (int column = 0; column < WIDTH - 3; column ++) {
			for (int row = 0; row < HEIGHT - 3; row++) {

				data = new int[WIDTH][HEIGHT];
				data[column][row] = data[column + 1][row + 1] = data[column + 2][row + 2] = data[column + 3][row + 3] = 1;
				C4Board b1 = new C4Board(data);
				
				assertTrue(b1.toString(), b1.getWinner() == 1);

				data = new int[WIDTH][HEIGHT];
				data[column][row] = data[column + 1][row + 1] = data[column + 2][row + 2] = data[column + 3][row + 3] = 2;
				C4Board b2 = new C4Board(data);
				
				assertTrue(b2.toString(), b2.getWinner() == 2);
			}
			
			for (int row = HEIGHT - 1; row >= 3; row--) {
				data = new int[WIDTH][HEIGHT];
				data[column][row] = data[column + 1][row - 1] = data[column + 2][row - 2] = data[column + 3][row - 3] = 1;
				C4Board b1 = new C4Board(data);
				
				assertTrue(b1.toString(), b1.getWinner() == 1);

				data = new int[WIDTH][HEIGHT];
				data[column][row] = data[column + 1][row - 1] = data[column + 2][row - 2] = data[column + 3][row - 3] = 2;
				C4Board b2 = new C4Board(data);
				
				assertTrue(b2.toString(), b2.getWinner() == 2);
			}
		}
	}
}
