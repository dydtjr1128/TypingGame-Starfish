
package panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

import javax.swing.*;

import globalNum.GlobalNum;
import image.ImageCollection;
import panel.PanelManager;

public class RankPanel extends JPanel
{
	private PanelManager panelManager;
	private DownStar downStar;
	private JButton backspaceBtn = new JButton(ImageCollection.normalBackspace);
	
	private FileReader fr = null;
	private BufferedReader in = null;
	private Vector<Person> rankVector = new Vector<Person>();
	private JLabel arrowLabelImg = new JLabel(ImageCollection.arrowIcon);	
	private JLabel[] label;
	private int addNum = 0;
	
	public RankPanel(PanelManager panelManager)
	{
		this.panelManager = panelManager;
		
		setSize(1100, 800);
		setLayout(null);
		fileRead();
		if(rankVector.size() <10){
			label = new JLabel[rankVector.size()];
		}
		else
			label = new JLabel[10];
		//버튼 이미지 넣는 방법
		backspaceBtn.setBounds(830, 540, 64, 64);
		backspaceBtn.setMargin(new Insets(0,0,0,0));		
		backspaceBtn.setOpaque(false);
		backspaceBtn.setBorderPainted(false);
		backspaceBtn.setBorder(BorderFactory.createEmptyBorder());
		backspaceBtn.setFocusPainted(false);
		backspaceBtn.setContentAreaFilled(false);
		backspaceBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				downStar.finish();
				new SoundClass("Sounds//클릭.wav").play();
				panelManager.setContentPane(GlobalNum.INTRO);
			}
		});
		
		backspaceBtn.setRolloverIcon(ImageCollection.overBackspace);
		
		arrowLabelImg.setBounds(350,220, 50, 20);
		add(arrowLabelImg);
		add(backspaceBtn);
				
		int x = 400;
		int y = 220;
		
		for(int i=0; i<label.length; i++)
		{
			Person person = rankVector.get(i);
			label[i] = new JLabel((i+1) + "등 : " + person.getName() + "  " + person.getScore() + "점");
		}
		
		for(int i=0; i<label.length; i++)
		{
			label[i].setFont(new Font("HY궁서B",Font.ITALIC, 20));
			label[i].setForeground(Color.yellow);
			label[i].setBounds(x, y, 300, 20);
			add(label[i]);
			y += 30;
		}
		
		addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyPressed(KeyEvent e) 
			{	
				new SoundClass("Sounds//클릭.wav").play();
				int code = e.getKeyCode();
				switch(code)
				{
				case KeyEvent.VK_UP:
					if(arrowLabelImg.getY() > 220)
						arrowLabelImg.setLocation(arrowLabelImg.getX(), arrowLabelImg.getY()-30);
					if(arrowLabelImg.getY() == 220)
					{
						
						if(addNum > 0)
						{
							addNum--;
						
							for(int i=0+addNum; i<10+addNum; i++)
							{
								Person person = rankVector.get(i);
								label[i-addNum].setText((i+1) + "등 : " + person.getName() + "  " + person.getScore() + "점");
							}
						}
					}
					break;
				case KeyEvent.VK_DOWN:
					if(arrowLabelImg.getY() < 490)
						arrowLabelImg.setLocation(arrowLabelImg.getX(), arrowLabelImg.getY()+30);
					if(arrowLabelImg.getY() == 490)
					{
						if(10 + addNum < rankVector.size())
						{
							addNum++;
							for(int i=0; i<10 && i<rankVector.size(); i++)
							{
								Person person = rankVector.get(i+addNum);
								label[i].setText((i+1+addNum) + "등 : " + person.getName() + "  " + person.getScore() + "점");
							}
						}
					}
					break;
				}
				repaint();
			}
		});
	}
	public void fileRead()
	{
		String val;
		Person person = null;
		try {
			fr = new FileReader("Rank.txt");
			in = new BufferedReader(fr);
			
			while((val = in.readLine()) != null)
			{
				if(!val.equals("")){
					String[] splitString = val.split("&");
					String name = splitString[0];
					int score = Integer.parseInt(splitString[1]);
					
					person = new Person(score, name);
				}
					rankVector.add(person);
			}
		} catch (IOException e) 
		{		
			e.printStackTrace();
		} finally{
			try {
				if(in != null)
					in.close();
				if(fr != null)
					fr.close();
					
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		if(rankVector.size()>1)
		{
			int j;
			for(int i=1; i<rankVector.size(); i++)
			{
				Person key = rankVector.get(i);
				for(j=i-1;j>=0 && key.getScore()<rankVector.get(j).getScore(); j--){
					rankVector.set(j+1, rankVector.get(j));
				}
				rankVector.set(j+1, key);
			}
			Collections.reverse(rankVector);
		}
	}
	
	public void downStarStart()
	{
		downStar = new DownStar(this);
		downStar.start();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(ImageCollection.spaceImg, 0, 0, getWidth(), getHeight(), this);
		g.setColor(Color.YELLOW);
		g.drawRect(100, 100, getWidth()-200, getHeight()-200);
	}
	class Person{
		private int score;
		private String name;
		public Person(int score, String name) {
			this.score = score;
			this.name = name;
		}
		public String getName(){
			return name;
		}
		public int getScore(){
			return score;
		}
	}
}
