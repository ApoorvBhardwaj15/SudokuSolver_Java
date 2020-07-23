/**
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

package grid;

import java.io.*;
import java.util.stream.IntStream;


/**
 * Abstract class representing the general interface for a Sudoku grid.
 * Both standard and Killer Sudoku extend from this abstract class.
 */
public abstract class SudokuGrid
{
	public int[][] grid;
	public int[][] getGrid() {
		return grid;
	}
	public void setGrid(int[][] grid) {
		this.grid = grid;
	}
	public int[] getValues() {
		return values;
	}
	public void setValues(int[] values) {
		this.values = values;
	}


	public int[] values;

	public SudokuGrid() {
		this.grid = grid;
		this.values = values;
	}
	/**
	 * Load the specified file and construct an initial grid from the contents
	 * of the file.  See assignment specifications and sampleGames to see
	 * more details about the format of the input files.
	 *
	 * @param filename Filename of the file containing the intial configuration
	 *                  of the grid we will solve.
	 *
	 * @throws FileNotFoundException If filename is not found.
	 * @throws IOException If there are some IO exceptions when openning or closing
	 *                  the files.
	 */
	public abstract void initGrid(String filename)
			throws FileNotFoundException, IOException;


	/**
	 * Write out the current values in the grid to file.  This must be implemented
	 * in order for your assignment to be evaluated by our testing.
	 *
	 * @param filename Name of file to write output to.
	 *
	 * @throws FileNotFoundException If filename is not found.
	 * @throws IOException If there are some IO exceptions when openning or closing
	 *                  the files.
	 */
	public abstract void outputGrid(String filename)
			throws FileNotFoundException, IOException;


	/**
	 * Converts grid to a String representation.  Useful for displaying to
	 * output streams.
	 *
	 * @return String representation of the grid.
	 */
	public abstract String toString();


	/**
	 * Checks and validates whether the current grid satisfies the constraints
	 * of the game in question (either standard or Killer Sudoku).  Override to
	 * implement game specific checking.
	 *
	 * @return True if grid satisfies all constraints of the game in question.
	 */
	public boolean validate(SudokuGrid grid, int row, int column)
	{
		return rowConstraint(grid, row) &&
				columnConstraint(grid, column) &&
				subsectionConstraint(grid, row, column);
	} 
 	
	 private boolean subsectionConstraint(SudokuGrid grid, int row, int column) {
	        int gridSize = grid.values.length;
	        int subGridSize = (int)Math.sqrt(gridSize);
		 	boolean[] constraint = new boolean[gridSize];
	        int subGridRowStart = (row / subGridSize) * subGridSize;
	        int subGridRowEnd = subGridRowStart + subGridSize;

	        int subGridColumnStart = (column / subGridSize) * subGridSize;
	        int subGridColumnEnd = subGridColumnStart + subGridSize;

	        for (int r = subGridRowStart; r < subGridRowEnd; r++) {
	            for (int c = subGridColumnStart; c < subGridColumnEnd; c++) {
	                if (!checkConstraint(grid, r, constraint, c)) return false;
	            }
	        }
	        return true;
	    }

	    private boolean columnConstraint(SudokuGrid grid, int column) {
	        int gridSize = grid.values.length;
	    	boolean[] unique = new boolean[gridSize];
	        return IntStream.range(0, gridSize)
	          .allMatch(row -> checkConstraint(grid, row, unique, column));
	    }

	    private boolean rowConstraint(SudokuGrid grid, int row) {
	    	 int gridSize = grid.values.length;
	        boolean[] unique = new boolean[gridSize];
	        return IntStream.range(0, gridSize)
	          .allMatch(column -> checkConstraint(grid, row, unique, column));
	    }

	    private boolean checkConstraint(SudokuGrid grid, int row, boolean[] unique, int column) {
	        if (grid.grid[row][column] != 0) {
	        	int uniqueIndex = 0;
	        	for(int i =0; i< grid.values.length;i++) {
	        		if(values[i] == grid.grid[row][column]) {
	        			uniqueIndex = i;
	        			break;
	        		}
	        	}
	            if (!unique[uniqueIndex]) {
	            	unique[uniqueIndex] = true;
	            } else {
	                return false;
	            }
	        }
	        return true;
	    }
	    
	

} // end of abstract class SudokuGrid
