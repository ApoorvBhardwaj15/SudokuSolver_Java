package grid;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class StdSudokuGrid extends SudokuGrid
{	
    public StdSudokuGrid() {
        super();

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
    		String splitRegex = " ";
    		String splitRegexIndex = ","
 
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
    			for(;(inputLine=inputBufferedReader.readLine())!=null;) {
    				String[] lineSplit = inputLine.split(splitRegex);

    				String Index = String.valueOf(lineSplit[0]);
    				String[] rowCol = Index.split(splitRegexIndex);
    				int row = Integer.valueOf(rowCol[0]);
    				int col = Integer.valueOf(rowCol[1]);
    				
    				int value = Integer.valueOf(lineSplit[1]);
    				
    				grid[row][col] = value;
			
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
        // TODO
    	int size = values.length;
    	String s ="";
    	for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if(column != size-1) {
                	//System.out.print(grid[row][column] + ",");
                	s+=grid[row][column]+",";
                }
                else {
                	//System.out.print(grid[row][column]);
                	s+=grid[row][column]+"\n";
                }
            	
            }
            //System.out.println();
        }
        // placeholder
        return s;
    } // end of toString()

} // end of class StdSudokuGrid
