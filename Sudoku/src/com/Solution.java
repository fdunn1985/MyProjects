package com;

import java.util.ArrayList;
import java.util.Random;

import com.Solution;

public class Solution 
{
	int[][] boardSolution = new int[9][9];
	
	public Solution()
	{
		
	}
	
	public int[][] smallSquareSolution()
	{
		int[][] temp = new int[3][3]; 
		
		
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
				temp[countA][countB] = randomNum;
				numbersPicked[posCount] = randomNum;
				posCount++;
			}
		}
		
		return temp;
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
	
	public void createBoard(int[][] upperLeft, int[][] centerMid, int[][] bottomRight)
	{
		setBoardToZero();
		
		for (int r = 0; r<3; r++)
		{
			for (int c=0; c<3; c++)
			{
				boardSolution[r][c] = upperLeft[r][c];
				//System.out.println("Placing " + upperLeft[r][c] + " in space [" + r + "][" + c +"]");
			}
		}
		
		for (int r = 3; r<6; r++)
		{
			for (int c=3; c<6; c++)
			{
				boardSolution[r][c] = centerMid[r-3][c-3];
				//System.out.println("Placing " + centerMid[r-3][c-3] + " in space [" + r + "][" + c +"]");
			}
		}
		
		for (int r = 6; r<9; r++)
		{
			for (int c=6; c<9; c++)
			{
				boardSolution[r][c] = bottomRight[r-6][c-6];
				//System.out.println("Placing " + bottomRight[r-6][c-6] + " in space [" + r + "][" + c +"]");
			}
		}
	}
	
	public int[] createRowBackup(int row)
	{
		int[] temp = new int[9];
		for (int c = 0; c < 9; c++)
		{
			temp[c] = boardSolution[row][c];
		}
		return temp;
	}
	
	public void restoreOriginalRow(int row, int[] rowBackup)
	{
		for (int c = 0; c < 9; c++)
		{
			boardSolution[row][c] = rowBackup[c];
		}
	}
	
	public void solveBoard()
	{	
		ArrayList<Integer> indexNums = new ArrayList<Integer>();
		
		for (int r = 0; r < 9; r++)
		{
			int[] rowBackup = new int[9];
			rowBackup = createRowBackup(r);
			
			int attemptCounter = 0;
			
			for (int c = 0; c < 9; c++)
			{
				if (0 == boardSolution[r][c])
				{
					indexNums = resetNumArray();
					
					indexNums = checkSquare(indexNums, r, c);
					indexNums = checkRow(indexNums, r, c);
					indexNums = checkCol(indexNums, r, c);
					if (!indexNums.isEmpty())
					{
						int numberPicked = pickRandomNum(indexNums);
						boardSolution[r][c] = numberPicked;
					}
					else
					{
						if (attemptCounter == 10)
						{
							attemptCounter = 0;
							setBoardToZero();
							r=0; 
							c=-1;
						}
						else
						{
							c = -1;
							restoreOriginalRow(r, rowBackup);
							attemptCounter++;
							//System.out.println("Row " + r + " attempt Number: " + attemptCounter);
						}
						
					}
				}
			}
		}	
	}
	
	public ArrayList<Integer> resetNumArray()
	{
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		numbers.add(1);
		numbers.add(2);
		numbers.add(3);
		numbers.add(4);
		numbers.add(5);
		numbers.add(6);
		numbers.add(7);
		numbers.add(8);
		numbers.add(9);
		
		return numbers;
	}
	
	public int pickRandomNum(ArrayList<Integer> nums)
	{
		Random rand = new Random();
		int numberValue;
		if (nums.size() == 1)
		{
			numberValue = nums.get(0);
		}
		else
		{
		int randomNumIndex = 0;
		randomNumIndex = rand.nextInt(nums.size());
		numberValue = nums.get(randomNumIndex);
		}
		return numberValue;
	}
	
	public ArrayList<Integer> checkRow(ArrayList<Integer> numbers, int row, int col)
	{
		ArrayList<Integer> temp = numbers;
		
		for (int r = 0; r < 9; r++)
		{
			if (0 != boardSolution[r][col])
			{
				if (temp.contains(boardSolution[r][col]))
				{
					int index = temp.indexOf(boardSolution[r][col]);
					temp.remove(index);
				}
			}
		}
		return temp;
	}
	
	public ArrayList<Integer> checkCol(ArrayList<Integer> numbers, int row, int col)
	{
		ArrayList<Integer> temp = numbers;
		
		for (int c = 0; c < 9; c++)
		{
			if (0 != boardSolution[row][c])
			{
				if (temp.contains(boardSolution[row][c]))
				{
					int index = temp.indexOf(boardSolution[row][c]);
					temp.remove(index);
				}
			}
		}
		return temp;
	}
	
	public ArrayList<Integer> checkSquare(ArrayList<Integer> numbers, int row, int col)
	{
		ArrayList<Integer> temp = numbers;
		int minRow, maxRow, minCol, maxCol;
		ArrayList<Integer> numsToRemove = new ArrayList<Integer>();
		
		if (row < 3)
		{
			minRow = 0;
			maxRow = 3;
		}
		else if (row < 6)
		{
			minRow = 3;
			maxRow = 6;
		}
		else
		{
			minRow = 6;
			maxRow = 9;
		}
		
		if (col < 3)
		{
			minCol = 0;
			maxCol = 3;
		}
		else if (col < 6)
		{
			minCol = 3;
			maxCol = 6;
		}
		else
		{
			minCol = 6;
			maxCol = 9;
		}
		
		for (int r = minRow; r < maxRow; r++)
		{
			for (int c = minCol; c < maxCol; c++)
			{
				if (!((r== row) && (c==col) && (boardSolution[r][c] == 0)))
				{
					numsToRemove.add(boardSolution[r][c]);
				}
			}
		}
		
		for (int i = 0; i<numsToRemove.size(); i++)
		{
			if (temp.contains(numsToRemove.get(i)))
			{
				int index = temp.indexOf(numsToRemove.get(i));
				temp.remove(index);
			}
		}
		
		return temp;
	}
	
	public String printSmallSolution(int[][] singleSolution)
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
	
	public String printBoardSolution()
	{
		String output = "";
		
		for (int countA =  0; countA<9; countA++)
		{
			for (int countB = 0; countB <9; countB++)
			{
				output += boardSolution[countA][countB] + " ";
			}
			output += "\n";
		}
		return output;
	}
	
	public void setBoardToZero()
	{
		for (int a =0; a <9; a++)
		{
			for (int b=0; b <9; b++)
			{
				boardSolution[a][b] = 0;
			}
		}
	}
	
	public int getBoardValue(int row, int col)
	{
		return boardSolution[row][col];
	}

}
