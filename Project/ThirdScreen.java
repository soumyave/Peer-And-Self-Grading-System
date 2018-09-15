import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import javax.swing.JLabel;
import javax.swing.JTable;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;


//Upon entering the submit button on the second window, this class is invoked and a table
//displaying grouping member names and their corresponding normalized scores is shown.
//The normalization for the group members is performed in this class
public class ThirdScreen extends JFrame {

	public ThirdScreen(){
		System.out.println("Default Constructor called\n");
	}
	
	//The third screen object is instantiated(in the second window) using this constructor
	//Parameters : scoresMap : key is member name , value is a list of integers corresponding
	//						   to members professionalism,meeting participation, work evaluation
	//						   scores
	public ThirdScreen(Map<String,List<Integer>> scoresMap){
		//Fetching the normalized scores
		try{
			Map<String,Double> normalizedScores = getNormalizedScores(scoresMap);
			displayResults(normalizedScores);
		}
		catch(Exception e){
			System.out.println("Out of bounds exception");
		}
		
	}
	
	//This method calculates the normalized score for each member
	// The normalized score is calculated using the below equation
	//	Normalized score = (total Row Score)/(Sum of Scores in all rows)
	//Parameters : scoresMap : key is member name , value is a list of integers corresponding
	//						   to members professionalism,meeting participation, work evaluation
	//						   scores
	//Return : normalized score in the form of map, key is member name , value is normalized score
	public Map<String,Double> getNormalizedScores(Map<String,List<Integer>> scoresMap) throws Exception
	{
		
		int totalScore = 0;
		
		//Map where the normalized scores are stored
		Map<String,Double> normalizedScores = new LinkedHashMap<String,Double>(); 
		List<Integer> rowTotal = new ArrayList<Integer>();
		
		//Checking whether the number of members is within valid range
		if(scoresMap.size()<2 || scoresMap.size()>7){
			System.out.println("Invalid members");
			throw new IndexOutOfBoundsException("Invalid members");
		}
		
		//Evaluating row total and total of all rows
		for(Map.Entry<String,List<Integer>> val: scoresMap.entrySet()){
			int rowScore = 0;
    		for(int score : val.getValue()){
    			if(score<0){
    				throw new IndexOutOfBoundsException("Score is negative");
    			}
    			rowScore+=score;
    		}
    		rowTotal.add(rowScore);
    		totalScore+=rowScore;
    	}
		
		int ind = 0;
		//finding normalized scores
		for(Map.Entry<String,List<Integer>> val: scoresMap.entrySet()){
			normalizedScores.put(val.getKey(),(double)rowTotal.get(ind)/totalScore);
			ind+=1;
    	}
		return normalizedScores;
	}
	
	//This method displays the final normalized scores of each member in table format
	//Parameters : normalizedScores : key is member name , value is normalized score of member
	public void displayResults(Map<String,Double> normalizedScores){
	        
	        //create table with normalized scores
	        JTable table = new JTable(new TableModel(normalizedScores));
	        
	        //Setting table parameters
	        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
	        table.setFillsViewportHeight(true);
			table.setRowHeight(30);
	        table.setFont(new Font("Serif", Font.BOLD, 25));
	        table.getTableHeader().setFont(new Font("Serif", Font.BOLD, 25));
	        
	        //Setting the cell values at center
	        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
	        
	        for(int j = 0 ; j < 2 ; j++){
	        	  table.getColumnModel().getColumn(j).setCellRenderer( centerRenderer );
	        }
	        
	        //Setting frame layout,size and visibility
	    	this.add(table.getTableHeader(), BorderLayout.NORTH);
			this.add(table, BorderLayout.CENTER);
	        this.setTitle("Normalized scores of Group Members");
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	        int width = 180;
	        width+=30*(normalizedScores.size()-2);
	        this.setSize(1500,width);
	        this.setVisible(true);
		
	}
	
	//Table is constructed on this model
	//Table data is populated here depending on the number of members
	class TableModel extends AbstractTableModel {
		//Table data is stored in this variable
		Object[][] tableData;
		
		//Column names of the table
		String[] columns = new String[] {
	            "Group Members", "Normalized Scores"
	        };
		//Constructor is called with normalizedScores where key is member name and value is normalized
		//score
       public TableModel(Map<String,Double> normalizedScores){
    	int numberOfMembers = normalizedScores.size();
       	tableData = new Object[numberOfMembers][2];
	         
        int i = 0;
        
        //Rounding up to 4 decimals
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        for(Map.Entry<String,Double> val : normalizedScores.entrySet()){
        	tableData[i][0] = val.getKey();
        	tableData[i][1] = df.format(val.getValue());
        	i+=1;
        } 
       	
       }
       
     //Get Table Column length
       public int getColumnCount() {
           return columns.length;
       }
	   
     //Get Table row count
       public int getRowCount() {
           return tableData.length;
       }

     //Get table column name depending on the index
       //Parameters : col - index of the column
       public String getColumnName(int col) {
           return columns[col];
       }

       //Get cell value at a particular row and column
       public Object getValueAt(int row, int col) {
           return tableData[row][col];
       }

       //Method to check which cells are editable
       //Parameters : row : row index
       //			   col : column index
       public boolean isCellEditable(int row, int col) {
    	   //all the cells are uneditable
          return false;
       }
	     
	}
}
