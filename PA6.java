
/* PA6.java
 * @author Eric Schulze
 * @version 1.0 12/1/16
 *
 * Traveling Salesperson Problem V2
 *
 */

import java.util.*;
import java.lang.*;
import java.io.*;

public class PA6{

	//Attributes
		static int CITIES; //Number of total cities
		static String COST_FILE;
		static int bestCost = Integer.MAX_VALUE;
        static ArrayList<Integer> bestPath = new ArrayList<>();
		static int[][] cost = new int[CITIES][CITIES];
        static Stack<Integer> pathStack =new Stack();
        static ArrayList<Integer> visitedCities = new ArrayList<>();
        static boolean minFlag;

	//Constructors

	//Methods

	/*MAIN METHOD*/
	public static void main(String[] args){
		CITIES = Integer.parseInt(args[0]);
		COST_FILE = args[1];
		cost = readCostFromFile();
        
		ArrayList<Integer> originalRemainingList = new ArrayList<>();
		for(int i = 1; i < CITIES; i++){
			originalRemainingList.add(i - 1, i);
		}//end for
        
        long start = System.nanoTime();
		shortestPath();
        long end = System.nanoTime();
        
        bestCost = computeCost(visitedCities);
        
        System.out.println("Best Cost: " + bestCost);
        System.out.println("Best Travel Path:");
        System.out.println(visitedCities);
        System.out.println("Execution Time: " + (end - start) * Math.pow(10,-9) + " sec");
        
		System.out.println("Done");
		
	}

/************************************************************************/

	/*public int[][] readCostFromFile() reads in the cost data from the instructor provided .txt file
 	 *
	 * @returns 2-D int array that holds the cost travel when traveling from city to city
	 *
	 * requires: CITIES and COST_FILE not be empty
	 * ensures: 2D array with the cost from the txt file is returned
	 */
	public static int[][] readCostFromFile(){
		int value, i, j;
		int[][] cost = new int[CITIES][CITIES];
		File f = new File(COST_FILE);

		try{
			Scanner input = new Scanner(f);
		
			for(i = 0; (i < CITIES) && input.hasNext(); i++){
				for(j = i; (j < CITIES) && input.hasNext(); j++){
					if(i == j)
						cost[i][j] = 0;
					else{
						value = input.nextInt();
						cost[i][j] = value;
						cost[j][i] = value;
					} // end if
				} // end for
			} // end for
			input.close();
		} //end try

		catch(IOException e){
			System.out.println("Unable to read file");
		} //end catch
		return cost;
	} // readCostFromFile

/************************************************************************/

	/*public static int computeCost(ArrayList<Integer> tour) brings an ArrayList as a parameter and uses
     *          the cost matrix to calcualte the cost of the entire trip
	 * @returns int that represents the cost of a completed trip
	 *
	 * requires: CITIES and cost[][] not be empty
	 * ensures: int value of the entire trip is calculated and returned
	 */
	public static int computeCost(ArrayList<Integer> tour){
		int totalCost = 0;

		for(int i = 0; i < tour.size() - 1; i++){
			totalCost += cost[tour.get(i)][tour.get(i + 1)];
		} // end for
		
		if(tour.size() == CITIES)
			totalCost += cost[tour.get(tour.size() - 1)][tour.get(0)];
		//System.out.println(totalCost);
		return totalCost;

	} // computeCost


/************************************************************************/

	/*public static void shortestPath() populates the visitedCities ArrayList with all the cities, with the
 	 *              each city being the closest city to the previous city.
     *
	 * @returns none
	 *
	 * requires: CITIES and cost[][] not to be empty
	 * ensures: visitedCities holds the minimum spanning tree of the cities
	 */
	public static void shortestPath(){
        minFlag = false;
        visitedCities.add(0,0);
        System.out.print("Start: " + visitedCities.get(0) + " ");
        pathStack.push(0);
        int closestCity = 0;
        int currentCity;
        int min;
        
        
        while(!pathStack.empty()){
            currentCity = pathStack.peek();
            min = Integer.MAX_VALUE;
            
            for(int i = 1; i < CITIES; i++){
                if(cost[currentCity][i] != 0 && !visitedCities.contains(i)){
                    if(cost[currentCity][i] < min){
                        min = cost[currentCity][i];
                        closestCity = i;
                        minFlag = true;
                    }//end if
                } //end if
            } //end for
            
            pathStack.pop();
            
            if(minFlag){
                visitedCities.add(closestCity);
                pathStack.push(closestCity);
                System.out.print(closestCity + " ");
                minFlag = false;
            }
            
        }
        System.out.println();
	}//recDFS

	
}