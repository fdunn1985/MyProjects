using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace WordCountAndSort
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void analyzeBtn_Click(object sender, EventArgs e)
        {
            StartAnalysis(textBox.Text);
        }

        public void StartAnalysis(string text)
        {
            Dictionary<string, int> wordDictionary = new Dictionary<string, int>();

            //remove punctuation
            text = RemovePunctuation(text);
            text.ToLower();

            //alphabetize unique words
            string[] textArr = text.Split(' ');
            Array.Sort<string>(textArr);

            //parse text (and count unique words)
            wordDictionary = ParseAndCountUniqueWords(textArr);
            
            //Display Info
            DisplayCountInfo(wordDictionary, textArr);
        }

        public string RemovePunctuation(string text)
        {
            return Regex.Replace(text, @"[^\w\s\-]", string.Empty);
        }

        private Dictionary<string, int> ParseAndCountUniqueWords(string[] textArr)
        {
            Dictionary<string, int> retDictionary = new Dictionary<string ,int>();

            foreach (string word in textArr)
            {
                if (!retDictionary.ContainsKey(word))
                {
                    retDictionary.Add(word, 1);
                }
                else
                {
                    retDictionary[word] = retDictionary[word]+1;
                }
            }
            return retDictionary;
        }

        public void DisplayCountInfo(Dictionary<string, int> wordDictionary, string[] textArr)
        {
            List<string> lines = new List<string>();
            string wordCount = "" + textArr.Count();
            string uniqueCount = "" + wordDictionary.Count();

            lines.Add("Total Words: \t\t" + wordCount);
            lines.Add("Total Unique Words: \t" + uniqueCount);
            lines.Add(" ");

            foreach (var word in wordDictionary)
            {
                string key = word.Key;
                int value = word.Value;

                if (key.Length < 9) //Formatting purposes only
                    lines.Add(key + "\t\t" + value);
                else
                    lines.Add(key + "\t" + value);
            }

            resultsListBox.DataSource = lines;
        }
    }
}
