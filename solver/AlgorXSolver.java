package solver;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import grid.SudokuGrid;

public class AlgorXSolver extends StdSudokuSolver
{
	@Override
	public boolean solve(SudokuGrid grid) {

		int size = grid.values.length;
		boolean solve;
		int [][] BG = createBinaryGrid(grid,size);
		Set<Integer> resultSet = new HashSet<Integer>();
		BG = loadPartialSolution(grid,BG,size,resultSet);
		solve =recursiveSolve(BG,resultSet);
		for(int n : resultSet) {
			int[] check = bGIndexToRowColNum(n,grid.grid.length);
				grid.grid[check[0]-1][check[1]-1] = grid.values[check[2]-1];
	
		}
		return solve;
	} // end of solve()

	private boolean recursiveSolve(int[][] bG, Set<Integer> resultSet) {
		int colMinIndex = getColIndexWithMinimumOnes(bG);
		if(bG.length == 1)
			return true;
		if(colMinIndex == -1)
			return false;
		for(int row = 1 ; row <bG.length ; row ++) {
			if(bG[row][colMinIndex] == 1) {
				
				resultSet.add(bG[row][0]);
				int [][] temp = processSelectedRow(bG,row);
				if(!recursiveSolve(temp,resultSet)) {
					resultSet.remove(bG[row][0]);
					continue;
				}
				else
					return true;
			}
		}
		return false;
	}

	private int getColIndexWithMinimumOnes(int[][] bG) {
		TreeMap<Integer,Integer> numOfOnes = new TreeMap<Integer,Integer>();
		numOfOnes = calcNumOfOnes(numOfOnes,bG);
		if(numOfOnes.containsKey(0))
			return -1;
		if(numOfOnes.size()==0)
			return 0;
		return numOfOnes.firstEntry().getValue();
	}

	private int[][] processSelectedRow(int[][] bG, int rowIndex){
		int[][] temp;
		Set<Integer> rowsToRemove = new HashSet<Integer>();
		Set<Integer> colsToRemove = new HashSet<Integer>();
		coverBGRow(rowsToRemove,colsToRemove,bG,rowIndex);
		temp = deleteRowsCols(bG,rowsToRemove,colsToRemove);
		return temp;
	}

	private TreeMap<Integer,Integer> calcNumOfOnes(TreeMap<Integer,Integer> numOfOnes,int[][] bG) {
		for(int col =1 ; col < bG[0].length ; col++) {
			int counter = 0;
			for(int row=1 ; row< bG.length; row++) {
				if(bG[row][col] == 1) {
					counter++;
				}
			}
			numOfOnes.put(counter, col);
		}
		return numOfOnes;
	}

	private int[][] loadPartialSolution(SudokuGrid grid, int[][] bG, int size, Set<Integer> resultSet) {

		int[][] temp;
		Set<Integer> rowsToRemove = new HashSet<Integer>();
		Set<Integer> colsToRemove = new HashSet<Integer>();
		for(int i = 0 ; i < size ; i++) {
			for(int j =0 ; j < size ; j++) {
				if(grid.grid[i][j] != 0) {
					int BGRow = i*size*size + j*size + indexOf(grid,grid.grid[i][j]);
					for(int index=1;index<bG.length;index++) {
						if(bG[index][0] == BGRow) {
							resultSet.add(BGRow);
							coverBGRow(rowsToRemove,colsToRemove,bG,index);
							break;
						}
					}
				}
			}
		}	
		temp = deleteRowsCols(bG,rowsToRemove,colsToRemove);
		return temp;
		// TODO Auto-generated method stub
	}

	private void coverBGRow(Set<Integer> rowsToRemove,Set<Integer> colsToRemove,int[][] bG, int index) {
		for(int j=1; j< bG[0].length;j++) {
			if(bG[index][j]==1) {
				for(int i =1; i< bG.length;i++) {
					if(bG[i][j]==1 &&  i!=index) {
						rowsToRemove.add(i);				
					}
				}
				colsToRemove.add(j);				
			}
		}
		rowsToRemove.add(index);
	}

	private int[][] deleteRowsCols(int[][] bG, Set<Integer> rowsToRemove, Set<Integer> colsToRemove) {
		// TODO Auto-generated method stub
		int numOfRows = rowsToRemove.size();
		int numOfCols = colsToRemove.size();

		int [][] temp = new int[bG.length-numOfRows][bG[0].length - numOfCols];
		int p = 0;
		for(int row = 0 ; row < bG.length ; row++) {
			if(rowsToRemove.contains(row)) {
				continue;
			}
			int q=0;
			for(int col =0 ; col < bG[0].length ; col++) {
				if(colsToRemove.contains(col))
					continue;
				temp[p][q] = bG[row][col];
				++q;
			}
			++p;
		}
		return temp;
	}


	private int indexOf(SudokuGrid grid,int value) {
		for(int i=0;i< grid.values.length; i++) {
			if(grid.values[i]==value) {
				return i+1;
			}
		}
		return -1;
	}

	private int[][] createBinaryGrid(SudokuGrid grid, int size) {
		// TODO Auto-generated method stub
		int[][] binaryGrid = new int[size*size*size + 1][4*size*size + 1];
		for(int j = 0 ; j < 4*size*size + 1 ; j++ ) {
			binaryGrid[0][j] = j;
		}
		for(int i = 0 ; i < size*size*size + 1 ; i++ ) {
			binaryGrid[i][0] = i;
			if(i != 0) {
				int[] index = bGIndexToRowColNum(i,size);
				setRowValues(binaryGrid,index,size,i);
			}
		}
		return binaryGrid;
	}

	private void setRowValues(int[][] binaryGrid,int[] index, int size, int i) {
		// TODO Auto-generated method stub
		int cellConstraint = (index[0]-1)*size + index[1];
		int rowConstraint = size*size + (index[0]-1)*size + index[2];
		int colConstraint = 2*size*size + (index[1]-1)*size + index[2];
		int subGridSize = (int)Math.sqrt(size);
		int block;
		if(index[0] % Math.sqrt(size) != 0) {
			if(index[1] % Math.sqrt(size) != 0) {
				block = (index[0]/subGridSize)*subGridSize +  index[1]/subGridSize + 1;
			}
			else {
				block = (index[0]/subGridSize)*subGridSize +  index[1]/subGridSize;
			}

		}
		else {
			if(index[1] % Math.sqrt(size) != 0) {
				block = ((index[0]/subGridSize)-1)*subGridSize + index[1]/subGridSize + 1;
			}
			else {
				block = ((index[0]/subGridSize)-1)*subGridSize + index[1]/subGridSize;
			}
		}

		int blockConstraint = 3*size*size + (block-1)*size + index[2];
		binaryGrid[i][cellConstraint] = 1;
		binaryGrid[i][rowConstraint] = 1;
		binaryGrid[i][colConstraint] = 1;
		binaryGrid[i][blockConstraint] = 1;
	}


	public int[] bGIndexToRowColNum(int rowIndex, int size) {
		int[] indexToFill= new int[3];
		int position;
		int row;
		int numberIndex = rowIndex % size;
		if(numberIndex ==0) {
			numberIndex = size;
		}

		if( rowIndex % size != 0) {
			position = rowIndex/size + 1;
		}
		else {
			position = rowIndex/size;
		}

		if(position % size != 0) {
			row = position/size + 1;
		}
		else {
			row = position/size;
		}

		int col = position % size;
		if(col == 0) {
			col = size;
		}
		indexToFill[0]=row;
		indexToFill[1]=col;
		indexToFill[2]= numberIndex;


		return indexToFill;
	}
} // end of class AlgorXSolver
