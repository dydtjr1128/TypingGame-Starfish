
package panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.sound.midi.Soundbank;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.swing.*;

import globalNum.GlobalNum;
import image.ImageCollection;
import panel.PanelManager;

public class IntroPanel extends JPanel 
{
	private PanelManager panelManager;
	
	private JButton gameStartBtn = new JButton(ImageCollection.normalGameStart);
	private JButton gameExplanationBtn = new JButton(ImageCollection.normalGameExplanation);
	private JButton gameLevelChoiceBtn = new JButton(ImageCollection.normalLevelChoice);
	private JButton gameRankBtn = new JButton(ImageCollection.normalRanking);
	private JButton gameExitBtn = new JButton(ImageCollection.normalExit);	
	
	
	private LevelChoiceDialog levelChoiceDialog;	
	private DownStar downStar;
	

	public IntroPanel(PanelManager panelManager) 
	{
		this.panelManager = panelManager;
		setSize(1100, 800);
		setLayout(null);	
		gameStartBtn.setBounds(440, 275, 210, 45);		
		gameExplanationBtn.setBounds(440, 340, 210, 45);
		gameLevelChoiceBtn.setBounds(440, 405, 210, 45);
		gameRankBtn.setBounds(440, 470, 210, 45);
		gameExitBtn.setBounds(440, 535, 210, 45);

		gameStartBtn.setRolloverIcon(ImageCollection.overGameStart);		
		gameExplanationBtn.setRolloverIcon(ImageCollection.overGameExplanation);
		gameLevelChoiceBtn.setRolloverIcon(ImageCollection.overLevelChoice);
		gameRankBtn.setRolloverIcon(ImageCollection.overRanking);
		gameExitBtn.setRolloverIcon(ImageCollection.overExit);
		
		gameStartBtn.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				downStar.finish();
				new SoundClass("Sounds//클릭.wav").play();
				panelManager.setContentPane(GlobalNum.GAME);
			}
		});
		gameExplanationBtn.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				downStar.finish();
				new SoundClass("Sounds//클릭.wav").play();
				panelManager.setContentPane(GlobalNum.EXPLANATION);
			}
		});
		gameLevelChoiceBtn.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) {				
				levelChoiceDialog = new LevelChoiceDialog(panelManager.getMainFrame());
				new SoundClass("Sounds//클릭.wav").play();
				levelChoiceDialog.setVisible(true);
			}
		});
		gameRankBtn.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				downStar.finish();
				new SoundClass("Sounds//클릭.wav").play();
				panelManager.setContentPane(GlobalNum.RANKING);
			}
		});
		gameExitBtn.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) {			
				System.exit(0);
			}
		});
		gameStartBtn.addMouseListener(new MyMousListener());
		gameExplanationBtn.addMouseListener(new MyMousListener());
		gameLevelChoiceBtn.addMouseListener(new MyMousListener());
		gameRankBtn.addMouseListener(new MyMousListener());
		gameExitBtn.addMouseListener(new MyMousListener());

		add(gameStartBtn);
		add(gameExplanationBtn);
		add(gameLevelChoiceBtn);
		add(gameRankBtn);
		add(gameExitBtn);		
	}
	class MyMousListener extends MouseAdapter{
		@Override
		public void mouseEntered(MouseEvent e) {
			new SoundClass("Sounds//클릭.wav").play();
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

		g.setFont(new Font("궁서", Font.ITALIC, 50));
		g.setColor(Color.YELLOW);
		g.drawString("Defend The Earth", 310, 200);

		g.setColor(Color.white);
		g.setFont(new Font("Arial Black", Font.ITALIC, 15));
		g.drawString("Produced by Kim Jun Hee, Choi Yong Seok", 700, 730);
	}
	
	
	public class LevelChoiceDialog extends JDialog 
	{
		JButton levelBtn[] = new JButton[5];
		JPanel myPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(ImageCollection.spaceImg, 0, 0, getWidth(), getHeight(), this);
			}
		};
		
		public LevelChoiceDialog(JFrame mainFrame) {
			super(mainFrame, "Level Choice", true);
			setContentPane(myPanel);
			myPanel.setLayout(null);
			levelBtn[0] = new JButton(ImageCollection.normalLevel1);
			levelBtn[1] = new JButton(ImageCollection.normalLevel2);
			levelBtn[2] = new JButton(ImageCollection.normalLevel3);
			levelBtn[3] = new JButton(ImageCollection.normalLevel4);
			levelBtn[4] = new JButton(ImageCollection.normalLevel5);
			for (int i = 0; i < levelBtn.length; i++)
			{
				levelBtn[i].setSize(200, 45);
				levelBtn[i].setLocation(43, 20 + (i * 55));
				levelBtn[i].setName("level" + (i + 1));
				myPanel.add(levelBtn[i]);
				levelBtn[i].addActionListener(new MyActionListener());
				levelBtn[i].addMouseListener(new MyMousListener());
			}
			levelBtn[0].setRolloverIcon(ImageCollection.overLevel1);
			levelBtn[1].setRolloverIcon(ImageCollection.overLevel2);
			levelBtn[2].setRolloverIcon(ImageCollection.overLevel3);
			levelBtn[3].setRolloverIcon(ImageCollection.overLevel4);
			levelBtn[4].setRolloverIcon(ImageCollection.overLevel5);
			setLocation(mainFrame.getLocation().x + 400, mainFrame.getLocation().y + 200);
			setSize(300, 350);
		}
		class MyActionListener implements ActionListener 
		{
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton) e.getSource();
				String myLevel = btn.getName();
				if (myLevel.equals("level1")) {
					GamePanel.gameLevel = 1;
				} else if (myLevel.equals("level2")) {
					GamePanel.gameLevel = 2;
				} else if (myLevel.equals("level3")) {
					GamePanel.gameLevel = 3;
				} else if (myLevel.equals("level4")) {
					GamePanel.gameLevel = 4;
				} else if (myLevel.equals("level5")) {
					GamePanel.gameLevel = 5;
				}
				new SoundClass("Sounds//클릭.wav").play();
				levelChoiceDialog.setVisible(false);
			}
		}
	}	
}