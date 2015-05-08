package com.old;

import java.util.Random;

public class Solution {
	
	int[][] singleSol = new int[3][3];
	Grid[][] grid = new Grid[3][3];
	
	public Solution()
	{
		
	}
	
	public void initializeBoard()
	{
		/*grid[0][0].setGrid(singleSolution());
		grid[1][1].setGrid(singleSolution());
		grid[2][2].setGrid(singleSolution());
		
		grid[0][1].setToZero();
		grid[0][2].setToZero();
		grid[1][0].setToZero();
		grid[1][2].setToZero();
		grid[2][0].setToZero();
		grid[2][1].setToZero();
		*/
		
		
	}
	
	/*public String printGrid()
	{
		
	}*/
	
	public int[][] singleSolution()
	{
		
		
		int randomNum = 0;
		
		int[] numbersPicked = new int[9];
		
		Random rand = new Random();
		int posCount = 0;
		
		for (int countA =  0; countA<3; countA++)
		{
			for (int countB = 0; countB <3; countB++)
			{
				randomNum = rand.nextInt(9)  +1;
				while (checkExistance(numbersPicked, randomNum))
				{
					randomNum = rand.nextInt(9)  +1;
				}
				singleSol[countA][countB] = randomNum;
				numbersPicked[posCount] = randomNum;
				posCount++;
			}
		}
		
		return singleSol;
	}
	
	public boolean checkExistance(int[] list, int num)
	{
		boolean exists = false;
		for (int item : list)
		{
			if (item == num)
			{
				exists = true;
			}
		}
		
		return exists;
	}
	
	public String printSingleSolution(int[][] singleSolution)
	{
		String output = "";
		
		for (int countA =  0; countA<3; countA++)
		{
			for (int countB = 0; countB <3; countB++)
			{
				output += singleSolution[countA][countB] + " ";
			}
			output += "\n";
		}
		
		return output;
	}
	
	public Solution setSolutionToZero()
	{
		Solution sol = new Solution();
		int[][] temp = new int[3][3];
		for (int a =0; a <2; a++)
		{
			for (int b=0; b <2; b++)
			{
				temp[a][b] = 0;
			}
		}
		return sol;
	}

}
