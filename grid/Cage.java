package grid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import grid.*;
import grid.KillerSudokuGrid.Cell;

public class Cage {
	
	public int id;
	private int sum;
	private List<Cell> cellList;
	public List<Stack<Integer>> permutatedSolutions;
	public Cage(int id,int sum, List<Cell> cellList,List<Stack<Integer>> permutatedSolutions ){
		this.id = id;
		this.sum = sum;
		this.cellList = cellList;
		this.permutatedSolutions = new ArrayList<Stack<Integer>>();
		permutePossibleSolutions(permutatedSolutions);
	}
	
	public int getSum()  {
		return sum;
	}

	public List<Cell> getCells() {
		return cellList;
	}

	public Cell[] getCellsAsArray() {
		int numCells = this.cellList.size();
		Cell[] cells = new Cell[numCells];
		for(int i = 0; i < numCells; i++) {
			cells[i] = this.cellList.get(i);
		}
		return cells;
	}

	public void setSolutions(List<Stack<Integer>> ps){
		// Permutate solutions, populates Cage.permutatedSolutions
		permutePossibleSolutions(ps);
		// Set possible solutions for Cells in this Cage
		for (Stack<Integer> stack : ps) {
			while (!stack.empty()) {
				Integer k = stack.pop();
				for (Cell cell : cellList) {
					cell.setValue(k);
				}
			}
		}
	}

	public ArrayList<ArrayList<Integer>> getPermutatedSolutions(){
		ArrayList<ArrayList<Integer>> solutionsClone = new ArrayList<ArrayList<Integer>>();
		for (Stack<Integer> s : permutatedSolutions) {
			ArrayList<Integer> solution = new ArrayList<Integer>();
			for (Integer i : s) {
				solution.add(i);
			}
			solutionsClone.add(solution);
		}
		return solutionsClone;
	}

	private void permutePossibleSolutions(List<Stack<Integer>> ps) {
		for (int i = 0; i < ps.size(); i++) {
			permuteStack(ps.get(i), 0);
		}
	}

	/**
	 * Recursively searches for unique permutations of a given solution
	 * Stack and adds to permuatedSolutions for this Cage
	 *
	 * @param possibleSolution a possible solution for the Cage that will be permutated
	 * @param fromIndex starting index, updated with each recursive call
	 */
	private void permuteStack(Stack<Integer> possibleSolution, int fromIndex){
		for(int i = fromIndex; i < possibleSolution.size(); i++){
			Collections.swap(possibleSolution, i, fromIndex);
			permuteStack(possibleSolution, fromIndex + 1);
			Collections.swap(possibleSolution, fromIndex, i);
		}
		if (fromIndex == possibleSolution.size() - 1){
			// copy stack to retain correct order
			Stack<Integer> ps = new Stack<Integer>();
			for (Integer i : possibleSolution) {
				ps.add(i);
			}
			this.permutatedSolutions.add(ps);
		}
	}


}
