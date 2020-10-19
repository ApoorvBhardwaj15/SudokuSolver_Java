package solver;
import grid.SudokuGrid;
/**
 * Backtracking solver for standard Sudoku.
 */
public class BackTrackingSolver extends StdSudokuSolver
{
    @Override
    public boolean solve(SudokuGrid grid) {
    	int size =  grid.getValues().length;
     	
        // TODO: your implementation of the backtracking solver for standard Sudoku.
    	 for (int row = 0; row < size; row++) {
             for (int column = 0; column < size; column++) {
                 if (grid.getGrid()[row][column] == 0) {
                     for (int k = grid.getValues()[0]; k <= grid.getValues()[size-1]; k++) {
                         grid.getGrid()[row][column] = k;
                         if (grid.validate(grid,row,column) && solve(grid)) {
                             return true;
                         }
                         grid.getGrid()[row][column] = 0;
                     }
                     return false;
                 }
             }
         }
         return true;
        // placeholder

    } // end of solve()

} // end of class BackTrackingSolver()
