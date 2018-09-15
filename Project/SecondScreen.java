

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

//Upon entering the submit button on first window, this class is invoked and a table
//displaying grouping member names(depending on the number of users entered in the previous window)
//and their corresponding scores are displayed( depending on whether the user has selected "yes" or "no"
//for previouslyEntered scores).
//The user can update the scores and submit, upon which the third window is invoked showing normalized
//scores
public class SecondScreen extends JFrame implements ActionListener{
	
	//The total number of columns in the table
	private final int NUM_OF_COLUMNS = 4;
	
	//Variable to check number of members in the group, by default it is set to 2
	private int numberOfMembers = 2;
	
	//JTable object, upon which the data is populated
	private JTable table;
	
	//Variable used to check if the user has previously entered any scores, by default it is set to true
	private boolean previouslyEntered = true;
	
	//Constructor called when this class is instantiated
	//Parameters : numOfMembers - The number of members the user has selected
	//			   alreadyEntered - boolean flag telling whether the user has any previously entered
	//								scores
	public SecondScreen(int numOfMembers,boolean alreadyEntered)
    {
		//Setting members of class
		numberOfMembers = numOfMembers;
		previouslyEntered = alreadyEntered;
		
		//New table is created here using the TableModel class
		table = new JTable(new TableModel());        
	
		//Setting up columns in drop down format
        setUpColumns(table);
        
        //Setting up table parameters
        setUpTableParameters(table);
        
        //Adding scrollpane to the frame
        JScrollPane scrollPane = new JScrollPane( table );
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
    
        //Setting button at the bottom of table
        JButton submit = new JButton("Submit");
        submit.setSize(30,30);
		JPanel btnPnl = new JPanel(new BorderLayout());
		JPanel bottombtnPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel errorLabel = new JLabel();
		errorLabel.setForeground(Color.RED);
		bottombtnPnl.add(submit);
		btnPnl.add(bottombtnPnl, BorderLayout.CENTER);
		btnPnl.add(errorLabel, BorderLayout.SOUTH);

		//Setting frame layout,size and visibility
		this.add(table.getTableHeader(), BorderLayout.NORTH);
		this.add(table, BorderLayout.CENTER);
		this.add(btnPnl, BorderLayout.SOUTH);
       
        this.setTitle("Group Members Evaluation");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        
        //Width is set depending on the number of members
        //the table window size fits according to the number of members
        int width = 210;
        width+=30*(numOfMembers-2);
        this.setSize(1500,width);
        this.setVisible(true);
        
        //Listening to the submit button
        submit.addActionListener(this);
       
    }

	//Upon pressing the submit button this overriden method is called
	@Override
    public void actionPerformed(ActionEvent e) {
		
		//LinkedHashMap is created where key is the member name and value corresponds 
		//to a list of integers corresponding to professionalism, meeting participation and
		// Work evaluation
	    Map<String,List<Integer>> scoresMap = new LinkedHashMap<String,List<Integer>>();
	    
	    //Flag to check if the user has entered all the scores in the table
	    boolean errorFlag = false;
	    
	    //Populating the scoresMap collection
	    for(int i = 0 ; i < numberOfMembers ; i++){
	    	String name = table.getModel().getValueAt(i,0).toString();
	    	List<Integer> scores = new ArrayList<Integer>();
	    	for(int j = 1 ; j < NUM_OF_COLUMNS ; j++){
	    		Object value = table.getModel().getValueAt(i,j);
	    		//If value at a cell is null, it means that the user has not entered any value at that
	    		//cell but was trying to submit.
	    		if(value==null){
	    			errorFlag = true;
	    			break;
	    		}
	    		int intVal = Integer.parseInt(value.toString());
	    		scores.add(intVal);
	    	}
	    	//Checking for error case
	    	if(errorFlag){
	    		break;
	    	}
	    	scoresMap.put(name,scores);
	    }
	  //Checking for error case, if the user didn't enter any value in the cell, and tried to submit
	  // a error pop-up will be shown
	    if(errorFlag){
	    	JOptionPane.showMessageDialog(null,"Error in submission,please enter all the values");
	    }
	    //The user entered all the values correctly
	    else{
	    	//Invoking the third screen to show the normalized scores
	         ThirdScreen thirdScreen = new ThirdScreen(scoresMap);
	        //Disposing the current window
	         this.dispose();
	    }
    }
	
	//Method to return table object - used for testing
	public JTable getTableObject(){
		return table;
	}
	
	//Setting up table parameters
	//Parameters : table - Current table object
	public void setUpTableParameters(JTable table){
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
		table.setRowHeight(30);
        table.setFont(new Font("Serif", Font.BOLD, 25));
        table.getTableHeader().setFont(new Font("Serif", Font.BOLD, 25));
	}
	
	//Setting up drop down box in all the columns and setting the values at center
	//Parameters : table - Current table object
	public void setUpColumns(JTable table){
		
		//Setting the column values at center
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for(int i = 0 ; i < NUM_OF_COLUMNS ; i++){
        	  table.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
        }
		
        //Adding drop down box for each column in the table
		for(int i = 1 ; i < NUM_OF_COLUMNS ; i++){
			TableColumn sportColumn = table.getColumnModel().getColumn(i);
	    	JComboBox comboBox = new JComboBox();
	        DefaultComboBoxModel model = new DefaultComboBoxModel();
	        model.addElement("0");
	        model.addElement("1");
	        model.addElement("2");
	        model.addElement("3");
	        model.addElement("4");
	        model.addElement("5");
	        comboBox.setAlignmentX(CENTER_ALIGNMENT);
	       
	        comboBox.setModel(model);
	        ((JLabel)comboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
	        comboBox.setFont(new Font("Serif", Font.BOLD, 25));
	        sportColumn.setCellEditor(new DefaultCellEditor(comboBox));
	        
	        model = new DefaultComboBoxModel();
	        model.addElement("0");
	        model.addElement("1");
	        model.addElement("2");
	        model.addElement("3");
	        model.addElement("4");
	        model.addElement("5");
	        
	        ComboBoxTableCellRenderer renderer
	                        = new ComboBoxTableCellRenderer();
	        renderer.setModel(model);
	        sportColumn.setCellRenderer(renderer);
	       	}
	}
	
	//Need this class to show drop down icon by default
	public class ComboBoxTableCellRenderer extends JComboBox implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setSelectedItem(value);
            return this;
        }

    }
     
	//Table is constructed on this model
	//Table data is populated here depending on the number of members
	 class TableModel extends AbstractTableModel {
		 
		 //Pre-defined group member names
		 String[] defaultNames = {"John","Rick","Lora","Rachel","Mike",
					"Michell","Victor"};
		 
		 //Pre-defined table columns
		 String[] columns = new String[] {
		            "Group Members", "Professionalism(0-5)","Meeting Participation(0-5)","Work Evaluation(0-5)"
		        };
		 
		 //Table data is stored here
		 Object[][] tableData;
 
        public TableModel(){
        	
        	//Populating the data in tableData
        	tableData = new Object[numberOfMembers][NUM_OF_COLUMNS];
			for(int i = 0 ; i < numberOfMembers ; i++){
				tableData[i][0] = defaultNames[i];
				if(previouslyEntered == true){
					for(int j = 1 ; j < NUM_OF_COLUMNS ; j++){
						//Initially the values are initialized randomly
						Random rand = new Random();
					    int randomNum = rand.nextInt(6);
						tableData[i][j] = Integer.toString(randomNum);
					}
				}
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
           //The first cell should be uneditable, because the member names are predefined and fixed
            if (col == 0) {
                return false;
            } 
            //The scores can be updated by the user
            else {
                return true;
            }
        
        }
	    
        //Set the value at particular row and column to the object value
        //Parameters : value : the value to be set
        //			   row : row index
        //			   col : column index
        public void setValueAt(Object value, int row, int col) {
            tableData[row][col] = value;
        }

	    }
}

