package com;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;


import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class Sudoku extends JFrame
{
	public static Solution sol = new Solution();
	public static int[] labelPos = new int[2];
	public static int position = 0;
	public static Color disabledColor = new Color(237,235,236);//Color.LIGHT_GRAY;
	public static boolean validSelect = false;
	
	public static JFrame difficultyFrame;
	
	public static boolean validSolution = false;
	public static int difficulty = 25;
	
	public static boolean okayToContinue = false;
	public static final JLabel[] numberLabel = new JLabel[81];
	
	public Sudoku()
	{
		//nothing here to look at
	}
	
	public static void main(String[] args)
	{
		//Solution sol = new Solution();
		solveBoard(sol);
		
		difficultySetup();
		while (difficultyFrame.isVisible())
		{
			//do nothing but wait
		}
		Sudoku frame = new Sudoku();
		setupMenus(frame);
		setupDisplayFrame(frame);
		
		initializeNumbersToBoard(sol, difficulty);
		
		System.out.println(sol.printBoardSolution());
		
	}
	
	public static void difficultySetup()
	{	
		
		difficultyFrame = new JFrame();
		
		final JRadioButton easyButton   = new JRadioButton("Easy"  , true);
		final JRadioButton mediumButton    = new JRadioButton("Medium"   , false);
		final JRadioButton hardButton = new JRadioButton("Hard", false);

		ButtonGroup bgroup = new ButtonGroup();
		bgroup.add(easyButton);
		bgroup.add(mediumButton);
		bgroup.add(hardButton);

		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new GridLayout(4, 1));
		radioPanel.add(easyButton);
		radioPanel.add(mediumButton);
		radioPanel.add(hardButton);

		radioPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Select Difficulty"));
		
		JButton okay = new JButton("Okay");
		okay.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				String buttonSelected = "";
				if (easyButton.isSelected())
				{
					buttonSelected = "easy";
				}
				else if (mediumButton.isSelected())
				{
					buttonSelected = "medium";
				}
				else
				{
					buttonSelected = "hard";
				}
				
				setDifficulty(buttonSelected);
				difficultyFrame.dispose();
				
			}
			
		});
		
		radioPanel.add(okay);
		
		difficultyFrame.add(radioPanel);
		
		difficultyFrame.setTitle("Difficulty Selection");
		difficultyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		difficultyFrame.setSize(250,150);
		difficultyFrame.setVisible(true);
		
	}
	
	public static void setDifficulty(String btnSelected)
	{
		if (btnSelected == "easy")
		{
			difficulty = 40;
		}
		else if (btnSelected == "medium")
		{
			difficulty = 33;
		}
		else if (btnSelected == "hard")
		{
			difficulty = 25;
		}
		okayToContinue = true;
	}
	
	public static void setupMenus(Sudoku frame)
	{
		MenuBar mb = new MenuBar();
		
		Menu fileMenu = new Menu("File");
		final MenuItem miNewGame = new MenuItem("New Game");
		final MenuItem miLoadGame = new MenuItem("Load Game");
		final MenuItem miSaveGame = new MenuItem("Save Game");
		final MenuItem miDifficulty = new MenuItem("Change Difficulty");
		final MenuItem miExit = new MenuItem("Exit");
		
		miNewGame.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				handleMenu("New Game");
			} 
		});
		miLoadGame.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				handleMenu("Load Game");
			} 
		});
		miSaveGame.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				handleMenu("Save Game");
			} 
		});
		miDifficulty.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				handleMenu("Change Difficulty");
			} 
		});
		miExit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				handleMenu("Exit");
				
			} 
		});
		
		fileMenu.add(miNewGame);
		fileMenu.add(miLoadGame);
		fileMenu.add(miSaveGame);
		fileMenu.add(miDifficulty);
		fileMenu.add(miExit);
		
		
		mb.add(fileMenu);
		frame.setMenuBar(mb);
	}
	
	public static void handleMenu(String mi)
	{
		if (mi.equals("New Game"))
		{
			//System.out.println("You clicked: New Game");
			startNewGame();
		}
		else if (mi.equals("Load Game"))
		{
			System.out.println("You clicked: Load Game");
		}
		else if (mi.equals("Save Game"))
		{
			System.out.println("You clicked: Save Game");
		}
		else if (mi.equals("Change Difficulty"))
		{
			startNewDifficultyGame(); //NOT WORKING RIGHT
		}
		else if (mi.equals("Exit"))
		{
			System.exit(EXIT_ON_CLOSE);
		}
	}
	
	public static void startNewDifficultyGame()
	{
		okayToContinue = false;
		solveBoard(sol);
		resetBoard();
		difficultySetup();
		/*while (!okayToContinue)
		{
			//do nothing but wait
		}*/
		initializeNumbersToBoard(sol, difficulty);
	}
	
	public static void startNewGame()
	{
		solveBoard(sol);
		resetBoard();
		//difficultySetup();
		while (!okayToContinue)
		{
			//do nothing but wait
		}
		initializeNumbersToBoard(sol, difficulty);
	}
	
	public static void resetBoard()
	{
		String blank = "";
		for (int i =0; i< 81; i++)
		{
			numberLabel[i].setBackground(Color.WHITE);
			numberLabel[i].setText(blank);
		}
	}
	
	public static void setupDisplayFrame(Sudoku frame)
	{
		final Border border = LineBorder.createGrayLineBorder();
		
		JPanel numPanel = new JPanel();
		numPanel.setLayout(new GridLayout(9,9));
		
		MatteBorder borderTop = new MatteBorder(5,1,1,1,Color.GRAY);
		MatteBorder borderLeft = new MatteBorder(1,5,1,1,Color.GRAY);
		MatteBorder borderBottom = new MatteBorder(1,1,5,1,Color.GRAY);
		MatteBorder borderRight = new MatteBorder(1,1,1,5, Color.GRAY);
		
		MatteBorder borderTopLeft = new MatteBorder(5,5,1,1, Color.GRAY);
		MatteBorder borderTopRight = new MatteBorder(5,1,1,5, Color.GRAY);
		MatteBorder borderBottomLeft = new MatteBorder(1,5,5,1, Color.GRAY);
		MatteBorder borderBottomRight = new MatteBorder(1,1,5,5, Color.GRAY);
		
		
		for (int iter = 0; iter < numberLabel.length; iter++)
		{
			final int count = iter;
			numberLabel[iter] = new JLabel("", SwingConstants.CENTER);
			
			int[] labPos = new int[2];
			labPos = calculateLabelGridPosition(iter);
			String posName = "";
			
			if ((labPos[0] == 0) || (labPos[0] == 3) || (labPos[0] == 6))
			{
				posName = "Top";
			}
			else if ((labPos[0] == 2) || (labPos[0] == 5) || (labPos[0] == 8))
			{
				posName = "Bottom";
			}
			
			if ((labPos[1] == 0) || (labPos[1] == 3) || (labPos[1] == 6))
			{
				posName += "Left";
			}
			else if ((labPos[1] == 2) || (labPos[1] == 5) || (labPos[1] == 8))
			{
				posName+= "Right";
			}
			
			if (posName.equals("Top"))
			{
				numberLabel[iter].setBorder(borderTop);
			}
			else if (posName.equals("Left"))
			{
				numberLabel[iter].setBorder(borderLeft);
			}
			else if (posName.equals("Bottom"))
			{
				numberLabel[iter].setBorder(borderBottom);
			}
			else if (posName.equals("Right"))
			{
				numberLabel[iter].setBorder(borderRight);
			}
			else if (posName.equals("TopLeft"))
			{
				numberLabel[iter].setBorder(borderTopLeft);
			}
			else if (posName.equals("BottomLeft"))
			{
				numberLabel[iter].setBorder(borderBottomLeft);
			}
			else if (posName.equals("TopRight"))
			{
				numberLabel[iter].setBorder(borderTopRight);
			}
			else if (posName.equals("BottomRight"))
			{
				numberLabel[iter].setBorder(borderBottomRight);
			}
			else
			{
				numberLabel[iter].setBorder(border);
			}
			
			Font font = new Font("arial", Font.PLAIN, 22);
			
			numberLabel[iter].setHorizontalTextPosition(JLabel.CENTER);
			numberLabel[iter].setVerticalTextPosition(JLabel.CENTER);
			numberLabel[iter].setFont(font);
			numberLabel[iter].setOpaque(true);
			numberLabel[iter].setBackground(Color.white);
			numberLabel[iter].addMouseListener(new MouseListener()
			{

				@Override
				public void mouseClicked(MouseEvent arg0) 
				{
					resetColor(numberLabel);
					
					position = count;
					
					if (!numberLabel[count].getBackground().equals(disabledColor))
					{
						validSelect = true;
						numberLabel[count].setBackground(Color.green);
						handleLabelClick(numberLabel[count], count);
						
					}
					else
					{
						validSelect = false;
					}
					
					
				}
				
				public void resetColor(JLabel[] numberLabel)
				{
					if (numberLabel[position].getBackground().equals(Color.green))
					{
						numberLabel[position].setBackground(Color.white);
					}
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
			});

		}
		
		for (int i=0; i<81; i++)
		{
			numPanel.add(numberLabel[i]);
		}
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,9));
		
		JButton[] numButtons = new JButton[10];
		
		for (int i=0; i<numButtons.length; i++)
		{
			numButtons[i] = new JButton();
			final int textVal = i+1;
			if (i == 9)
			{
				numButtons[i].setText("");
			}
			else
			{
				numButtons[i].setText(Integer.toString(textVal));
			}
			numButtons[i].addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					handleButtonClick(textVal);
				} 
			});
			buttonPanel.add(numButtons[i]);
		}
		
		frame.setTitle("Sudoku");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(610,460);
		//frame.pack();
		frame.add(numPanel, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		
		JPanel timerScorePanel = new JPanel();
		timerScorePanel.setLayout(new GridLayout(2,1));
		JLabel timerScoreArea = new JLabel();
		timerScoreArea.setText("Timer and score area");
		JButton checkSolution = new JButton("Check solution");
		checkSolution.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				validSolution = checkSolution();	
			}
			
		});
		
		timerScorePanel.add(timerScoreArea);
		timerScorePanel.add(checkSolution, BorderLayout.SOUTH);
		
		frame.add(timerScorePanel/*new JLabel("Timer and score area")*/, BorderLayout.EAST);
		frame.setVisible(true);
	}
	
	public static void handleButtonClick(int buttonVal)
	{
		System.out.println("Button " + buttonVal + " pressed");
		if (validSelect)
		{
			if (10 != buttonVal)
			{
				insertValueIntoLabel(buttonVal);
			}
			else
			{
				insertValueIntoLabel(-1);
			}
		}
	}
	
	public static void insertValueIntoLabel(int buttonVal)
	{
		if (-1 != buttonVal)
		{
			numberLabel[position].setText(Integer.toString(buttonVal));
		}
		else
		{
			numberLabel[position].setText("");
		}
	}
	
	public static void insertValueIntoLabel(JLabel label, int value)
	{
		label.setText(Integer.toString(value));
	}
	
	public static void handleLabelClick(JLabel numLabel, int pos)
	{
		
		labelPos = calculateLabelGridPosition(pos);
		System.out.println("Clicked label #" + pos + " at grid position [" + labelPos[0] + "][" + labelPos[1] + "]");
	}
	
	public static int[] calculateLabelGridPosition(int pos)
	{
		int[] temp = new int[2];
		
		temp[0] = pos / 9;
		temp[1] = pos % 9;
		
		return temp;
	}
	
	public static void solveBoard(Solution sol)
	{
		sol.setBoardToZero();
		sol.solveBoard();
	}
	
	public static void initializeNumbersToBoard(Solution sol, int totalToReveal)
	{
		int randomNum = -1;
		int[] gridPos = new int[2];
		ArrayList<Integer> randNumList = new ArrayList<Integer>();
		Random rand = new Random();
		
		for (int i=0; i < totalToReveal; i++)
		{
			randomNum = rand.nextInt(81);
			
			while (randNumList.contains(randomNum))
			{
				randomNum = rand.nextInt(81);
			}
			
			randNumList.add(randomNum);
			
			gridPos = calculateLabelGridPosition(randomNum);
			
			insertValueIntoLabel(numberLabel[randomNum], sol.getBoardValue(gridPos[0], gridPos[1]));
			numberLabel[randomNum].enable(false);
			numberLabel[randomNum].setBackground(disabledColor);
		}
	}
	
	public static boolean checkSolution()
	{
		boolean status = false;
		int counter = 0;
		int numCorrect = 0;
		for (int row = 0; row<9; row++)
		{
			for (int col = 0; col<9; col++)
			{
				if ((!numberLabel[counter].getText().equals("")))
				{
					if (sol.getBoardValue(row, col) == Integer.parseInt(numberLabel[counter].getText()))
					{
						numberLabel[counter].enable(false);
						numberLabel[counter].setBackground(disabledColor);
						numCorrect++;
					}
					else
					{
						numberLabel[counter].setBackground(Color.red);
					}
				}
				counter++;
			}
		}
		
		if (numCorrect == 81)
		{
			JOptionPane.showMessageDialog(null, "Congratulations! you win!!");
		}
		
		return status;
	}

}
