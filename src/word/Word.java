package word;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.Vector;

public class Word
{
	private Vector<String> word = new Vector<String>();	
	public static boolean state = false;
	
	public void makeWord(String path) 	//벡터에 단어를 추가
	{
		if (word.size() == 0) 
		{
			File select = new File(path);
			FileReader in = null;
			BufferedReader reader;
			String text = null;

			try 
			{
				in = new FileReader(select);
				reader = new BufferedReader(in);
				while ((text = reader.readLine()) != null) 
				{
					word.add(text);
				}
			} catch (Exception e1) 
			{
				
			}
			state=true;	//단어를 추가 상태로 바꿈
		}
	}
	
	public Vector<String> getWordVector()
	{
		return word;
	}	
	public int getSize()
	{
		return word.size();
	}
	public String getWord(int index) 
	{
		return word.get(index);
	}
	public void inputWord(String text)	//요소 추가
	{
		word.addElement(text);
	}	
}