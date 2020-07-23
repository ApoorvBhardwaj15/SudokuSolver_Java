/*
 * @author Jeffrey Chan & Minyi Li, RMIT 2020
 */

package solver;

import java.util.ArrayList;
import java.util.Set;

import grid.KillerSudokuGrid.Cell;
import grid.SudokuGrid;
import grid.*;

/**
 * Backtracking solver for Killer Sudoku.
 */
public class KillerBackTrackingSolver extends KillerSudokuSolver
{
	// TODO: Add attributes as needed.

	public KillerBackTrackingSolver() {
		// TODO: any initialisation you want to implement.
	} // end of KillerBackTrackingSolver()


	@Override
	public boolean solve(SudokuGrid Grid) {
		// TODO: your implementation of a backtracking solver for Killer Sudoku.
		int size =  Grid.getValues().length;
		
		
		
		
		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				if (Grid.getGrid()[row][column] == 0) {
					//System.out.println(row +","+column );
					String cellId = row +"" +column; 
				//	System.out.println(valueSet.contains(1)+"@@@");
					for (int k = Grid.getValues()[0]; k <= Grid.getValues()[size-1]; k++) {
					//	if(!valueSet.contains(k))
						//	continue;
						Grid.getGrid()[row][column] = k;
						insertInCage(row,column,k); 
						if (killerValidate(Grid,row,column) && solve(Grid)) {
							return true;
						}
						deleteFromCage(row,column,0);
						Grid.getGrid()[row][column] = 0;
						
					}
					
					return false;
				}
			}
		}
		return true;
		// placeholder
	} // end of solve()
	
	 
	private void deleteFromCage(int row, int col, int k) {
		// TODO Auto-generated method stub
		String cellId = String.valueOf(row)+String.valueOf(col);
		int cageId = grid.KillerSudokuGrid.getCellCageMapping().get(cellId);
		ArrayList<Cell> cageList = grid.KillerSudokuGrid.getCages().get(cageId);
		
		for(int i = 0 ; i < cageList.size() ; i ++) {
			if(cageList.get(i).label.compareTo(cellId)==0) {
				cageList.get(i).value=k;
			}
		}
		
	}


	private void insertInCage(int row, int col, int k) {
		// TODO Auto-generated method stub
		String cellId = String.valueOf(row)+String.valueOf(col);
		int cageId = grid.KillerSudokuGrid.getCellCageMapping().get(cellId);
		ArrayList<Cell> cageList = grid.KillerSudokuGrid.cages.get(cageId);
		//System.out.println(cellId +"#");
		for(int i = 0 ; i < cageList.size() ; i ++) {
			if(cageList.get(i).label.compareTo(cellId)==0) {
				cageList.get(i).value=k;
			}
		}
	}


	public boolean killerValidate(SudokuGrid grid, int row, int column) {
		
		if(grid.validate( grid,  row,  column) && cageConstraint(row, column)){
			return true;
		}
		
		return false;
	}


	private boolean cageConstraint(int row, int col) {
		// TODO Auto-generated method stub
		String cellId = String.valueOf(row)+String.valueOf(col);
		int cageId = grid.KillerSudokuGrid.getCellCageMapping().get(cellId);
		ArrayList<Cell> cageList = grid.KillerSudokuGrid.getCages().get(cageId);
		
		if(containsZero(cageList) >= 1) {
			return checkPartialSum(cageList);
		}
		else {
			return checkTotalSum(cageList);
		}
	}


	private boolean checkTotalSum(ArrayList<Cell> cageList) {
		// TODO Auto-generated method stub
		int sum=0;
		for(int i = 0 ; i < cageList.size() ; i ++) {
			sum += cageList.get(i).value;
		}
		if(sum==cageList.get(0).sum)
			return true;
		return false;
	}


	private boolean checkPartialSum(ArrayList<Cell> cageList) {
		// TODO Auto-generated method stub
		int sum=0;
		for(int i = 0 ; i < cageList.size() ; i ++) {
			sum += cageList.get(i).value;
		}
		if(sum < cageList.get(0).sum)
			return true;
		return false;
	}


	private int containsZero(ArrayList<Cell> cageList) {
		// TODO Auto-generated method stub
		int counter = 0;
		for(int i = 0 ; i < cageList.size() ; i ++) {
			if(cageList.get(i).value==0)
				counter++;
		}
		//System.out.println(counter);
		
		return counter;
	}

} // end of class KillerBackTrackingSolver()
