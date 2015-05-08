package com.old;

public class Grid {

	public Solution[][] grid = new Solution[3][3];
	
	public Grid()
	{
		
	}
	
	public void setSingleGrid(Solution single, int row, int col)
	{
		grid[row][col] = single;
	}
	
	public Solution getSingleGrid(int row, int col)
	{
		return grid[row][col];
	}
	public Solution[][] getCompleteGrid()
	{
		return grid;
	}
	
	public void setToZero()
	{
		Solution temp = new Solution();
		for (int a =0; a <2; a++)
		{
			for (int b=0; b <2; b++)
			{
				grid[a][b] = temp.setSolutionToZero();
			}
		}
	}
	
}
