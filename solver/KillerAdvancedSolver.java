/*
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

package solver;
import java.util.List;
import java.util.Stack;

import grid.SudokuGrid;
import grid.KillerSudokuGrid.Cell;


/**
 * Your advanced solver for Killer Sudoku.
 */
public class KillerAdvancedSolver extends KillerSudokuSolver
{
	// TODO: Add attributes as needed.

	public KillerAdvancedSolver() {
		// TODO: any initialisation you want to implement.
	} // end of KillerAdvancedSolver()


	@Override
	public boolean solve(SudokuGrid Grid) {
		// TODO: your implementation of your advanced solver for Killer Sudoku.
		int size =  Grid.getValues().length;




		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				if (Grid.getGrid()[row][column] == 0) {
					String cellId = row +"" +column; 
					int cageId = grid.KillerSudokuGrid.getCellCageMapping().get(cellId);
					//System.out.println(row +","+column );
					List<Cell> cellList =  grid.KillerSudokuGrid.cageList.get(cageId-1).getCells();
					List<Stack<Integer>> permutatedSolutions  = grid.KillerSudokuGrid.cageList.get(cageId-1).permutatedSolutions;
					
					for( int sol = 0 ; sol < permutatedSolutions.size() ; sol++) {
						Stack<Integer> cloned = (Stack<Integer>) permutatedSolutions.get(sol).clone();
						for(int i =0 ; i < cellList.size(); i++) {
							Grid.getGrid()[cellList.get(i).row][cellList.get(i).col] = cloned.pop();
						}
						
						if(validate(Grid,cellList) && solve(Grid)) {
							return true;
						}
						
						for(int i =0 ; i < cellList.size(); i++) {
							Grid.getGrid()[cellList.get(i).row][cellList.get(i).col] = 0;
						}
						
					}
					return false;
				}
			}
		}
		return true;
					
	} // end of solve()
	
	private boolean validate(SudokuGrid Grid,List<Cell> cellList) {
		// TODO Auto-generated method stub
		boolean[] check = new boolean[cellList.size()];
		for(int i =0 ; i < cellList.size(); i++) {
			check[i] = Grid.validate(Grid, cellList.get(i).row, cellList.get(i).col);
		}
		
		for(boolean checkEach : check) {
			if(!checkEach)
				return false;
		}
		return true;
	}




} // end of class KillerAdvancedSolver
