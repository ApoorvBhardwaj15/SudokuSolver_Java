package grid;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import grid.KillerSudokuGrid.Cell;

public class KillerSudokuGrid extends SudokuGrid
{
    // TODO: Add your own attributes
	public static HashMap <Integer,ArrayList<Cell>> cages = new HashMap <Integer,ArrayList<Cell>>();
	public static HashMap <String,Integer> cellCageMapping = new HashMap <String,Integer>();
	public static HashMap <Integer,Set<Integer>> cageSpecificValues = new HashMap <Integer,Set<Integer>>();
	public static ArrayList<Cage> cageList = new ArrayList<Cage>();
	
	
	 public static HashMap<Integer, Set<Integer>> getCageSpecificValues() {
		return cageSpecificValues;
	}

	boolean[][] dp;
    	public static HashMap<Integer, ArrayList<Cell>> getCages() {
		return cages;
	}
    
	public static HashMap<String, Integer> getCellCageMapping() {
		return cellCageMapping;
	}

	public KillerSudokuGrid() {
        super();
        this.cages = cages;
        this.cellCageMapping = cellCageMapping;
        this.cageSpecificValues = cageSpecificValues;
    } // end of KillerSudokuGrid()

    @Override
    public void initGrid(String filename)
        throws FileNotFoundException, IOException
    {
        // TODO
    	BufferedReader inputBufferedReader = null;
		FileReader fr = null;
		String inputLine = "";
		String inputLine1 = "";
		String inputLine2 = "";
		String inputLine3 = "";
		String splitRegex = " ";
		String splitRegexIndex = ",";
		String splitRegexSingle = "";

		try {
			fr = new FileReader(filename);
			inputBufferedReader = new BufferedReader(fr);
			
			inputLine1 = inputBufferedReader.readLine();
			int size = Integer.valueOf(inputLine1);
			grid = new int[size][size];
			values = new int[size];
			
			inputLine2 = inputBufferedReader.readLine();
			String [] splitInputLine2 = inputLine2.split(splitRegex);
			for(int i =0 ; i < size; i++) {
				values[i] = Integer.valueOf(splitInputLine2[i]);
			}
			
			inputLine3 = inputBufferedReader.readLine();
			String [] splitInputLine3 = inputLine3.split(splitRegexSingle);
			//splitInputLine3[0]
 			
			int j =1;
			for(;(inputLine=inputBufferedReader.readLine())!=null;) {
				String[] lineSplit = inputLine.split(splitRegex);
				int sum = Integer.valueOf(lineSplit[0]);
				ArrayList<Cell> cellList = new ArrayList<Cell>();
				int cardinality = 0;
				for(int i = 1 ; i <lineSplit.length ; i++) {			
					String rowColCell = String.valueOf(lineSplit[i]);
					String[] rowCol = rowColCell.split(splitRegexIndex);
					int row = Integer.valueOf(rowCol[0]);
					int col = Integer.valueOf(rowCol[1]);
					Cell cell = new Cell(rowCol[0]+rowCol[1],row, col,0,sum, j);
					cellList.add(cell);
					cellCageMapping.put(rowCol[0]+rowCol[1], j);
					cardinality++;
				}
				cages.put(j, cellList);
				List<Stack<Integer>> possibleSolutions = new ArrayList<Stack<Integer>>();			
				getAllSubsets(values,values.length,sum,j,cardinality,possibleSolutions);
				Cage cage = new Cage(j,sum,cellList,possibleSolutions);
				cageList.add(cage);			
				j++;
			}
			
	
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException in reading input file");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException in reading line from input file");
			e.printStackTrace(); 			
		} finally {
			if(inputBufferedReader != null){
				try {
					inputBufferedReader.close();
				} catch (IOException e) {
					System.out.println("IOException in closing inputBufferedReader");
					e.printStackTrace();					
				}
			}
			try{
				fr.close();
			}
			catch(Exception e){
				e.printStackTrace();
				
			}
		}		
	
    } // end of initBoard()

    @Override
    public void outputGrid(String filename)
        throws FileNotFoundException, IOException
    {
        // TODO
    	try { 	      
            PrintWriter outWriter = new PrintWriter(new FileWriter(filename), true);           
            String s = toString();
            // process the operations
            outWriter.println(s);
        }
        catch (FileNotFoundException ex) {
            System.err.println("One of the specified files not found.");
        }
        catch(IOException ex) {
            System.err.println(ex.getMessage());
        } 
    } // end of outputBoard()


    @Override
    public String toString() {
    	int size = values.length;
    	String s ="";
    	for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if(column != size-1) {
                	//System.out.print(grid[row][column] + ",");
                	s+=grid[row][column]+",";
                }
                else {                	
                	s+=grid[row][column]+"\n";
                }
            	
            }         
        }
        return s;
    } // end of toString()
    
    public void getAllSubsetsRec(int arr[], int i, int sum, 
    		ArrayList<Integer> p,int cageId, int cardinality,List<Stack<Integer>> possibleSolutions) 
	{ 
		// If we reached end and sum is non-zero. We print 
		// p[] only if arr[0] is equal to sun OR dp[0][sum] 
		// is true. 
		if (i == 0 && sum != 0 && dp[0][sum]) 
		{ 
				p.add(arr[i]); 
			//System.out.println(p + " error");
			if(p.size()==cardinality) {
				//cageSpecificValues.get(cageId).addAll(p); 
				Stack<Integer> st = new Stack<Integer>();
				st.addAll(p);
				possibleSolutions.add(st);
				//System.out.println(p + " first case");
			}
			//System.out.println(cageSpecificValues.get(34) +" second last cage ");
			p.clear(); 
			return; 
		} 
	
		// If sum becomes 0 
		if (i == 0 && sum == 0) 
		{ 
			if(p.size()==cardinality) {
				//cageSpecificValues.get(cageId).addAll(p); 
				Stack<Integer> st = new Stack<Integer>();
				st.addAll(p);
				possibleSolutions.add(st);
				//System.out.println(p + " second case");
			}
				
			p.clear(); 
			return; 
		} 
	
		// If given sum can be achieved after ignoring 
		// current element. 
		if (dp[i-1][sum]) 
		{ 
			// Create a new vector to store path 
			ArrayList<Integer> b = new ArrayList<>();  
			b.addAll(p); 
			//System.out.println(b + " PATH");
			getAllSubsetsRec(arr, i-1, sum, b,cageId,cardinality,possibleSolutions); 
		} 
	
		// If given sum can be achieved after considering 
		// current element. 
		if (sum >= arr[i] && dp[i-1][sum-arr[i]]) 
		{ 
			p.add(arr[i]); 
			getAllSubsetsRec(arr, i-1, sum-arr[i], p,cageId,cardinality,possibleSolutions); 
		} 
	} 
    
    public void getAllSubsets(int arr[], int n, int sum,int cageId, int cardinality, List<Stack<Integer>> possibleSolutions) 
	{ 
		if (n == 0 || sum < 0) 
		return; 
	
		// Sum 0 can always be achieved with 0 elements  
		dp = new boolean[n][sum + 1]; 
		for (int i=0; i<n; ++i) 
		{ 
			dp[i][0] = true; 
		} 
	
		// Sum arr[0] can be achieved with single element 
		if (arr[0] <= sum) 
		dp[0][arr[0]] = true; 
	
		// Fill rest of the entries in dp[][] 
		for (int i = 1; i < n; ++i) 
			for (int j = 0; j < sum + 1; ++j) 
				dp[i][j] = (arr[i] <= j) ? (dp[i-1][j] || 
										dp[i-1][j-arr[i]]) 
										: dp[i - 1][j]; 
		if (dp[n-1][sum] == false) 
		{ 
			System.out.println("There are no subsets with" + 
												" sum "+ sum); 
			return; 
		} 
	
		// Now recursively traverse dp[][] to find all 
		// paths from dp[n-1][sum] 
		ArrayList<Integer> p = new ArrayList<>();  
		//cageSpecificValues.put(cageId, p);
		getAllSubsetsRec(arr, n-1, sum, p,cageId,cardinality, possibleSolutions); 
	}
    
    public class Cell{
    	public String label;
    	public int row;
    	public int col;
		public int value;
		public int sum;
		public int cageId;
		
    	public Cell(String label, int row,int col,int value, int sum, int cageId) {
    		this.label = label;
    		this.value = value;
    		this.sum = sum;
    		this.row = row;
    		this.col = col;
    		this.cageId = cageId;
    	}
    	
    	public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		public int getSum() {
			return sum;
		}
		public void setSum(int sum) {
			this.sum = sum;
		}
    }
   
} // end of class KillerSudokuGrid
