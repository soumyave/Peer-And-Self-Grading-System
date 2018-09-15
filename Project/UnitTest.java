

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JTable;

import org.junit.Test;

public class UnitTest {
	
	//Testing the normalization method for zero members
	@Test
	public void testNormalizationForZero(){
		try{
			ThirdScreen testObj = new ThirdScreen();
			Map<String,List<Integer>> scoresMap = new HashMap<String,List<Integer>>();
			Map<String,Double> normalizedScores = testObj.getNormalizedScores(scoresMap);
			//The size of normalizedScores should be zero since there are no members in the table
			assertEquals(0,normalizedScores.size());
		}
		catch(Exception e){
			System.out.println("Out of bounds exception");
		}
	}
	
	//Testing the normalization method for two members
	@Test
	public void testNormalizationForTwo(){
		
		try{
			ThirdScreen testObj = new ThirdScreen();
			
			//Initializing scoresMap collection where key is a member name and values are
			// a list of integers where the values correspond values in each row
			Map<String,List<Integer>> scoresMap = new HashMap<String,List<Integer>>();
			String[] names = {"Piyush","Soumya"};
			int totalSum = 0;
			List<Integer> rowTotal = new ArrayList<Integer>();
			for(int i = 0 ; i < names.length ; i++){
				List<Integer> myList = new ArrayList<Integer>();
				int rowSum = 0;
				for(int j = 0 ; j < 3 ; j++)
				{
					Random rand = new Random();
				    int randomNum = rand.nextInt(6);
					myList.add(randomNum);
					rowSum+=randomNum;
				}
				totalSum+=rowSum;
				rowTotal.add(rowSum);
				scoresMap.put(names[i],myList);
			}
			
			//Evaluating normalized scores locally
			List<Double> localNormalizedScores = new ArrayList<Double>();
			for(int row : rowTotal){
				localNormalizedScores.add((double)row/totalSum);

			}
			
			//fetching the normalized scores from getNormalizedScores() method
			Map<String,Double> normalizedScores = testObj.getNormalizedScores(scoresMap);	
			Double normalizedSum = 0.0;
			
			//Comparing the local normalized scores and the normalized scores returned from the method
			for(int i = 0 ; i < names.length ; i++){
				System.out.println(localNormalizedScores.get(i));
				assertEquals(localNormalizedScores.get(i),normalizedScores.get(names[i]),0.0001);
				normalizedSum+=normalizedScores.get(names[i]);
			}
			
			//Making sure the sum of normalized scores is equal to 1.0
			assertEquals(1.0,normalizedSum,0.0001);
		}
		catch(Exception e){
			System.out.println("Out of bounds exception");
		}
	}
	
	//Testing the normalization method for seven members
	@Test
	public void testNormalizationForSeven(){
		try{
			ThirdScreen testObj = new ThirdScreen();
			
			//Initializing scoresMap collection where key is a member name and values are
			// a list of integers where the values correspond values in each row
			Map<String,List<Integer>> scoresMap = new HashMap<String,List<Integer>>();
			String[] names = {"Piyush","Soumya","Chaitanya","Apoorv","Shaoming",
								"Amy","Matthew"};
			int totalSum = 0;
			List<Integer> rowTotal = new ArrayList<Integer>();
			for(int i = 0 ; i < names.length ; i++){
				List<Integer> myList = new ArrayList<Integer>();
				int rowSum = 0;
				for(int j = 0 ; j < 3 ; j++)
				{
					Random rand = new Random();
				    int randomNum = rand.nextInt(6);
					myList.add(randomNum);
					rowSum+=randomNum;
				}
				totalSum+=rowSum;
				rowTotal.add(rowSum);
				scoresMap.put(names[i],myList);
			}
			
			//Evaluating normalized scores locally
			List<Double> localNormalizedScores = new ArrayList<Double>();
			for(int row : rowTotal){
				localNormalizedScores.add((double)row/totalSum);

			}
			
			//fetching the normalized scores from getNormalizedScores() method
			Map<String,Double> normalizedScores = testObj.getNormalizedScores(scoresMap);
			
			Double normalizedSum = 0.0;
			//Comparing the local normalized scores and the normalized scores returned from the method
			for(int i = 0 ; i < names.length ; i++){
				System.out.println(localNormalizedScores.get(i));
				assertEquals(localNormalizedScores.get(i),normalizedScores.get(names[i]),0.0001);
				normalizedSum+=normalizedScores.get(names[i]);
			}
			//Making sure the sum of normalized scores is equal to 1.0
			assertEquals(1.0,normalizedSum,0.0001);
		}
		catch(Exception e){
			System.out.println("Out of bounds exception");
		}
	}
	
	//Testing whether the user has not entered any value in all the cells
	//If value is not entered, value should be null
	@Test
	public void testInvalidScoreSubmission(){
		//Initializing the second screen with false i.e. previously entered is false
		//So initially all the default values are empty
		SecondScreen obj = new SecondScreen(4,false);
		JTable table = obj.getTableObject();
		
		for(int i = 0 ; i < 4 ; i++){
			for(int j = 1 ; j < 4 ; j++){
				//Checking if each value in the cell is equal to null
				assertNull(table.getValueAt(i,j));
			}
		}
	}
	//Testing if the normalized scores is handling negative scores i.e. it should throw an error
	@Test
	public void testNegativeScoreNormalization(){
		Throwable caught = null;
		try{
			ThirdScreen testObj = new ThirdScreen();
			
			//Initializing scoresMap collection where key is a member name and values are
			// a list of integers where the values correspond values in each row
			Map<String,List<Integer>> scoresMap = new HashMap<String,List<Integer>>();
			String[] names = {"Piyush","Soumya"};
			int totalSum = 0;
			List<Integer> rowTotal = new ArrayList<Integer>();
			for(int i = 0 ; i < names.length ; i++){
				List<Integer> myList = new ArrayList<Integer>();
				int rowSum = 0;
				for(int j = 0 ; j < 3 ; j++)
				{
					Random rand = new Random();
				    int randomNum = rand.nextInt(6);
					myList.add(randomNum);
					rowSum+=randomNum;
				}
				//Setting negative score
				myList.set(0,-1);
				totalSum+=rowSum;
				rowTotal.add(rowSum);
				scoresMap.put(names[i],myList);
			}
			//fetching the normalized scores from getNormalizedScores() method
			Map<String,Double> normalizedScores = testObj.getNormalizedScores(scoresMap);	
		}
		catch(Exception e){
			caught = e;
		}
		//Checking if the exception is caught or not
		assertNotNull(caught);
	}
	
	//Testing whether the number of members is not within the valid range in the normalized method
	///throws an exception if the number of members is invalid
	@Test
	public void testInValidMemberCount(){
		Throwable caught = null;
		try{
			ThirdScreen testObj = new ThirdScreen();
			
			//Initializing scoresMap collection where key is a member name and values are
			// a list of integers where the values correspond values in each row
			Map<String,List<Integer>> scoresMap = new HashMap<String,List<Integer>>();
			//adding only one member, it should throw an error
			String[] names = {"Piyush"};
			int totalSum = 0;
			List<Integer> rowTotal = new ArrayList<Integer>();
			for(int i = 0 ; i < names.length ; i++){
				List<Integer> myList = new ArrayList<Integer>();
				int rowSum = 0;
				for(int j = 0 ; j < 3 ; j++)
				{
					Random rand = new Random();
				    int randomNum = rand.nextInt(6);
					myList.add(randomNum);
					rowSum+=randomNum;
				}
				totalSum+=rowSum;
				rowTotal.add(rowSum);
				scoresMap.put(names[i],myList);
			}
			//fetching the normalized scores from getNormalizedScores() method
			Map<String,Double> normalizedScores = testObj.getNormalizedScores(scoresMap);	
		}
		catch(Exception e){
			caught = e;
		}
		//Checking if the exception is caught or not
		assertNotNull(caught);
	}
	
	//Testing whether the user has entered values in all the cells
	//If value is entered, it should not be null
	@Test
	public void testValidScoreSubmission(){
		
		//Initializing the second screen with true i.e. previously entered is true
		//So initially all the cells are initialized with some random values
		SecondScreen obj = new SecondScreen(4,true);
		JTable table = obj.getTableObject();
		
		for(int i = 0 ; i < 4 ; i++){
			for(int j = 1 ; j < 4 ; j++){
				//Checking if each value in the cell is not equal to null
				assertNotNull(table.getValueAt(i,j));
			}
		}
	}
}
