
package panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import word.Word;
import image.ImageCollection;
import globalNum.GlobalNum;
import panel.PanelManager;

public class GameFrame extends JFrame
{
	private PanelManager panelManager;
	private Word word = new Word();
	private JButton startBtn;	
	private boolean soundControl = false;
	private SoundClass backSound = new SoundClass("Sounds//back.wav");
	public GameFrame()
	{		
		backSound.play();
		soundControl = true;
		setSize(1100, 800);
		makeJMenu();
		panelManager = new PanelManager(this, word);
		startBtn = panelManager.startBtn;	
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		setIconImage(ImageCollection.titleImg);	//title 이미지		
		setTitle("별똥별 게임");

		start();
		
		setVisible(true);
	}
	
	public void makeJMenu()//매뉴만들기
	{
		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);
		
		JMenu file = new JMenu("File");
		JMenuItem readItem = new JMenuItem("Read");
		JMenuItem exitItem = new JMenuItem("Exit");
		JMenuItem soundItem = new JMenuItem("Sound");
		
		readItem.setIcon(ImageCollection.fileRead);
		exitItem.setIcon(ImageCollection.fileExit);
		soundItem.setIcon(ImageCollection.soundOnItem);
		
		readItem.setIconTextGap(5);
		exitItem.setIconTextGap(5);
		
		file.add(readItem);		
		file.add(exitItem);
		file.add(soundItem);
		
		mb.add(file);
		
		readItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser chooser = new JFileChooser();//파일 찾기
				FileNameExtensionFilter filter = new FileNameExtensionFilter("txt","txt");
				chooser.setFileFilter(filter);
				int ret = chooser.showOpenDialog(panelManager.getMainFrame());
				if(ret == JFileChooser.APPROVE_OPTION)
				{
					String filePath = chooser.getSelectedFile().getPath();
					word.makeWord(filePath);
					startBtn.setEnabled(true);
				}
			}			
		});
		
		exitItem.addActionListener(new ActionListener()
		{			
			@Override
			public void actionPerformed(ActionEvent e) 
			{				
				System.exit(0);
			}
		});
		soundItem.addActionListener(new ActionListener()
		{			
			@Override
			public void actionPerformed(ActionEvent e) 
			{				
				if(soundControl==true){
					backSound.stop();
					soundControl = false;
					soundItem.setIcon(ImageCollection.soundOffItem);
				}
				else{
					backSound.play();
					soundControl = true;
					soundItem.setIcon(ImageCollection.soundOnItem);
				}
			}
		});
	}
	
	public void start()
	{
		panelManager.setContentPane(GlobalNum.INTRO);
	}
}
