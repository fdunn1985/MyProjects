package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class WallPuzzle {

	public static final int WALL_LENGTH_3 = 3;
	public static final int WALL_LENGTH_4 = 4;
	
	public static ArrayList<ArrayList<Integer>> baseArrList = new ArrayList<ArrayList<Integer>>();
	public static ArrayList<ArrayList<Integer>> basePermList = new ArrayList<ArrayList<Integer>>();
	
	public static void main(String[] args)
	{
		//get input specs
		boolean askAgain = true;
		do 
		{
			BufferedReader brLength = new BufferedReader(new InputStreamReader(System.in));
			BufferedReader brHeight = new BufferedReader(new InputStreamReader(System.in));
			try 
			{
				System.out.print("Enter a wall length(type 0 to quit): ");
				String inputLength = brLength.readLine();
				System.out.print("Enter a wall height(type 0 to quit): ");
				String inputHeight = brHeight.readLine();
				
				if (inputLength.equals("0") || inputHeight.equals("0"))
				{
					System.out.println("program is exiting...");
					askAgain = false;
				}
				else if (Integer.parseInt(inputLength) < 0 )
				{
					System.err.println("You cannot use negative numbers.  Please try again.");
				}
				else if (Integer.parseInt(inputHeight) < 1)
				{
					System.err.println("wall height must be at least 1");
				}
				else if (inputLength.matches("[a-zA-Z]+") || inputHeight.matches("[a-zA-Z]+"))
				{
					System.err.println("Your value can only contain letters.  Please try again");
				}
				else
				{
					long startTime = System.currentTimeMillis();
					System.out.println("Unique walls that can be built is: " + calculateValue(Integer.parseInt(inputLength), Integer.parseInt(inputHeight)));
					basePermList.clear();
					long stopTime = System.currentTimeMillis();
					System.out.println("Total time was: " + (stopTime-startTime) + "ms");
				}
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			baseArrList.clear();
			
		} while(askAgain);
	}

	public static long calculateValue(int length, int height)
	{
		long retVal = 0;
		long variationCount = 0;
		
		ArrayList<Integer> comboArrList = new ArrayList<Integer>();
		//calculate possible values that add up to wall length
		recursiveSum(length, comboArrList, 0);
		
		//determine permutations of values that add up to wall length
		for (ArrayList<Integer> item : baseArrList)
		{			
			int[] valArray = new int[item.size()];
			for (int i=0; i<valArray.length; i++)
			{
				valArray[i] = item.get(i);
			}
			variationCount += getVariationCountSingle(height, item);
		}
		
		if (height > 1)
		{
			if (basePermList.size() > 1)
			{
				variationCount = getVariationCountByHeight(height, basePermList);
			}
		}

		
		retVal = variationCount;
		return retVal;
	}
	
	public static void recursiveSum(int length, ArrayList<Integer> comboArrList, int numOfThrees)
	{
		int sum = 0;
		
		boolean isEqual = false;
		boolean isOver = false;
		
		int counter = 0;
		while (!(isEqual || isOver))
		{
			if (counter < numOfThrees)
			{
				comboArrList.add(3);
				sum += WALL_LENGTH_3;
			}
			else
			{
				comboArrList.add(4);
				sum += WALL_LENGTH_4;
			}
			
			if (sum == length)
			{
				isEqual = true;
				baseArrList.add((ArrayList<Integer>) comboArrList.clone());
			}
			else if (sum > length)
			{
				isOver = true;
			}
			counter++;
		}
		
		boolean allThrees = true;
		for (int val : comboArrList)
		{
			if (val == 4)
			{
				allThrees = false;
				break;
			}
		}
		
		if (!allThrees)
		{
			comboArrList.clear();
			recursiveSum(length, comboArrList, ++numOfThrees);
		}
	}
	
	public static long getVariationCountSingle(int height, ArrayList<Integer> arrList)
	{
		long retVal = 0;
		if ((arrList.contains(3) && !arrList.contains(4)) || (arrList.contains(4) && !arrList.contains(3)))
		{
			if (height == 1)
				retVal = 1;
			basePermList.add(arrList);
		}
		else
		{
			ArrayList<ArrayList<Integer>> permutationList = new ArrayList<ArrayList<Integer>>();
			permutationList.add(new ArrayList<Integer>());
			
			for (int i=0; i<arrList.size(); i++)
			{
				ArrayList<ArrayList<Integer>> current = new ArrayList<ArrayList<Integer>>();
				for (ArrayList<Integer> l : permutationList)
				{
					for (int j=0; j < l.size() + 1; j++)
					{
						l.add(j, arrList.get(i));
						
						ArrayList<Integer> temp = new ArrayList<Integer>(l);
						if (!current.contains(temp))
						{
							current.add(temp);
						}
						l.remove(j);
					}
				}
				
				permutationList = new ArrayList<ArrayList<Integer>>(current);
			}
			
			retVal = permutationList.size();
			basePermList.addAll(permutationList);
		}
		return retVal;
	}
	
	public static long getVariationCountByHeight(int height, ArrayList<ArrayList<Integer>> bigList)
	{
		long retVal =0;
		
		ArrayList<ArrayList<Integer>> correctSolutionsByPos = new ArrayList<ArrayList<Integer>>();
		
		ArrayList<Integer> posList = new ArrayList<Integer>();
		for (int initPos = 0; initPos < bigList.size(); initPos++)
		{
			for (int movingPos = 0; movingPos < bigList.size(); movingPos++)
			{
				if (process(bigList, new int[] {initPos, movingPos}))
				{
					posList.add(movingPos);
				}
			}
			correctSolutionsByPos.add((ArrayList<Integer>) posList.clone());
			posList.clear();
		}
		
		for (int iter = 0; iter < bigList.size(); iter++)
		{
			retVal += getSumOfChildren(correctSolutionsByPos, correctSolutionsByPos.get(iter), height, 2);
		}
		
		return retVal;
	}
	
	public static long getSumOfChildren(ArrayList<ArrayList<Integer>> correctSolutionsByPosList, ArrayList<Integer> posList, int height, int level)
	{
		long retVal = 0;
		if (level == height)
		{
			retVal += posList.size();
		}
		else
		{
			for (int i=0; i<posList.size(); i++)
			{
				retVal += getSumOfChildren(correctSolutionsByPosList, correctSolutionsByPosList.get(posList.get(i)), height, level+1);
			}
		}
		
		return retVal;
	}
	
	public static boolean process(ArrayList<ArrayList<Integer>> list, int[] posArray)
	{
		boolean isValid = false;
		boolean sumsValid = false;
		int[] sum = new int[posArray.length];
		int minVal = list.get(posArray[0]).size();
		
		for (int pos : posArray)
		{
			if (minVal > list.get(pos).size())
				minVal = list.get(pos).size();
		}
		
		for (int iter = 0; iter < minVal; iter++)
		{
			if (iter != 0 && !sumsValid)
			{
				break;
			}
			else if (iter == (minVal - 1))
			{
				isValid = true;
			}
			else
			{
				ArrayList<ArrayList<Integer>> sumArrayList = new ArrayList<ArrayList<Integer>>();
				for (int i=0; i<sum.length; i++)
				{
					sumArrayList.add(list.get(posArray[i]));
				}
				sumsValid = checkSumArrayLists(sumArrayList);
			}
		}
		
		return isValid;
	}
	
	public static boolean checkSumArrayLists(ArrayList<ArrayList<Integer>> sumArrayList)
	{
		boolean isValid = true;
		
		for (int i=1; i<sumArrayList.size(); i++)
		{
			int sum1 = 0;
			int sum2 = 0;
			for (int j=0; j < sumArrayList.get(i-1).size()-1; j++)
			{
				sum1 += sumArrayList.get(i-1).get(j);
				sum2 = 0;
				for (int k=0; k < sumArrayList.get(i).size() -1; k++)
				{
					sum2 += sumArrayList.get(i).get(k);
					if (sum1 == sum2)
					{
						isValid = false;
						break;
					}
					else if (sum2 > sum1)
					{
						break;
					}
				}
				if (!isValid)
				{
					break;
				}
			}
			if (!isValid)
			{
				break;
			}
		}
		
		return isValid;
	}
	
	
	
	
	
	
	
}
