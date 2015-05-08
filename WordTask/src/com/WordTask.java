package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class WordTask {
	
	public static void main(String[] args)
	{
		boolean askAgain = true;
		
		do 
		{
			System.out.print("Enter a word(type 0 to quit): ");
	
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try 
			{
				String s = br.readLine();
				if (s.equals("0"))
				{
					System.out.println("program is exiting...");
					askAgain = false;
				}
				else if (s.length() >= 20)
				{
					System.err.println("Your word has too many characters.  Please try again.");
				}
				else if (!s.matches("[a-zA-Z]+"))
				{
					System.err.println("Your word can only contain letters.  Please try again");
				}
				else
				{
					System.out.println("Calculated value is: " + CalculateValue(s.toLowerCase()));
				}
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
		} while(askAgain);
	}
	
	public static long CalculateValue(String input)
	{
		long returnVal = 1;
		int count = 1;
		Map<Character, Integer> charMap = new HashMap<Character, Integer>();
		
		for (int iter = input.length() - 1; iter >= 0; iter--)
		{
			char mapChar = input.charAt(iter);
			
			int letterCount = 1;
			if (charMap.containsKey(mapChar))
			{
				letterCount = charMap.get(mapChar) + 1;
			}
			charMap.put(mapChar, letterCount);

			for (Map.Entry<Character, Integer> mapIter : charMap.entrySet())
			{
				if (mapChar > mapIter.getKey())
				{
					returnVal += count * mapIter.getValue() / letterCount;
				}
			}
			
			count *= ((input.length() - iter) / letterCount);
			
		}
		
		return returnVal;
	}
	
	
	
	
	
	
}
