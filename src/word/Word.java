package word;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.Vector;

public class Word
{
	private Vector<String> word = new Vector<String>();	
	public static boolean state = false;
	
	public void makeWord(String path) 	//���Ϳ� �ܾ �߰�
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
			state=true;	//�ܾ �߰� ���·� �ٲ�
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
	public void inputWord(String text)	//��� �߰�
	{
		word.addElement(text);
	}	
}