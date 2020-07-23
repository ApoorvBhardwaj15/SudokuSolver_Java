package grid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import grid.*;
import grid.KillerSudokuGrid.Cell;

/**
 * COURSE: CECS-551 AI
 * PROFESSOR: Todd Ebert
 * PROJECT: Killer Sudoku Solver
 *
 * A Cage is the grouping of cells in a Killer Sudoku puzzle denoted by a dotted
 * line or by individual colours.
 *
 * Cages contain 2 to 9 cells and a value that the cells in the cage should sum to.
 *
 * @author Kristin Peterson
 * @author Ariel Katz
 *
 */
public class Cage {
	
	public int id;
	private int sum;
	private List<Cell> cellList;
	public List<Stack<Integer>> permutatedSolutions;

	/**
	 * Cage constructor, takes an input string and
	 * constructs Cage object
	 *
	 * Info format:
	 * x,y,x,y,...,g
	 *
	 * where:
	 * x == row
	 * y == col
	 * g == Cage sum goal
	 *
	 * @param info Cage info from raw text file
	 */
	public Cage(int id,int sum, List<Cell> cellList,List<Stack<Integer>> permutatedSolutions ){
		this.id = id;
		this.sum = sum;
		this.cellList = cellList;
		this.permutatedSolutions = new ArrayList<Stack<Integer>>();
		permutePossibleSolutions(permutatedSolutions);
	}

	/**
	 * Returns the sum goal for this cage
	 *
	 * @return the sum goal for this cage
	 */
	public int getSum()  {
		return sum;
	}

	/**
	 * Returns a list of cells that comprise this cage
	 *
	 * @return a list of cells that the cage is comprised of
	 */
	public List<Cell> getCells() {
		return cellList;
	}

	/**
	 * Return the list of cells as an array of cells
	 *
	 * @return the list of cells as an array of cells
	 */
	public Cell[] getCellsAsArray() {
		int numCells = this.cellList.size();
		Cell[] cells = new Cell[numCells];
		for(int i = 0; i < numCells; i++) {
			cells[i] = this.cellList.get(i);
		}
		return cells;
	}

	/**
	 * Sets the possible solutions for Cells in this Cage
	 * and permutates possible Cage solutions
	 *
	 * @param ps possible solutions for Cells in this Cage
	 */
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

	/**
	 * Returns a copy of the list of all permuated possible solutions for this Cage
	 *
	 * @return permutated list of all possible solutions for this Cage
	 */
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

	/**
	 * Finds all unique permutations of each solution Stack
	 *
	 * @param ps set of possible solutions that will be permutated
	 */
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

	/**
	 * Builds a string representation of the cage, which identifies
	 * the cage's cells (x & y value) and the sum goal
	 *
	 * @return a string representation of the cage
	 */
	public String toString() {
		return "";
	}

	/**
	 * A string representation of the cage
	 * [cage-id] + [cage-cells] + [sum-goal] + [list of permutated possible solutions]
	 *
	 * @return a string representation of the cage
	 */
	public String toStringWithSolutions() {
		return "";
	}

}