
package panel;

import java.awt.*;
import javax.swing.*;

import word.Word;
import globalNum.GlobalNum;
import image.ImageCollection;

/* ���� �� ��� �г��� �����Ѵ�. ȭ�� ��ȯ�� �г� ������ �����ϴ�. */
public class PanelManager 
{
	public static GameFrame main;	//���� ������!
	
	private IntroPanel introPanel;		//��Ʈ�� �г�
	private RankPanel rankPanel;		//��ũ �г�
	private ExplanationPanel explanationPanel;	//���� �г�
	private GamePanel gamePanel;	//���� ���� �г�
//	private LoginPanel loginPanel;		//�α��� â �г�
//	private SignupPanel signupPanel;	//ȸ������ â �г�
//	private PasuePanel pasuePanel;	//1:1 ä��â ���� ����!

	//�� �гο� �ʿ��� �ɷ����� ����
	private Word word;
	public JButton startBtn = new JButton(ImageCollection.offStartImg);	//�پ�!
	
	public PanelManager(GameFrame main, Word word) 
	{
		this.main = main;
		this.word = word;
		
		introPanel = new IntroPanel(this);	
		explanationPanel = new ExplanationPanel(this);
		gamePanel = new GamePanel(word, this);
	}

	//ȭ���� ��ȯ
	public static void setContentPane(Container panel) 
	{
		main.setContentPane(panel);
		// �ڵ��� ������ ����
	}

	//������ �̿� ��ȯ
	public void setContentPane(int panel)
	{
		// �����ڵ� ������ ����
		main.getContentPane().revalidate();
		switch (panel) 
		{			
		case GlobalNum.INTRO:			
			main.setContentPane(introPanel);	
			introPanel.downStarStart();
			break;
		case GlobalNum.RANKING:
			rankPanel = new RankPanel(this);
			main.setContentPane(rankPanel);
			rankPanel.requestFocus();
			rankPanel.downStarStart();
			break;
		case GlobalNum.EXPLANATION:
			main.setContentPane(explanationPanel);
			explanationPanel.downStarStart();
			break;
		case GlobalNum.GAME:					
			main.setContentPane(gamePanel);
			gamePanel.textField.requestFocus();
			gamePanel.startThread();
			break;
		}
	}

	//���� ������ ��ȯ
	public GameFrame getMainFrame()
	{
		return main;
	}
	
	//���� �г� ��ȯ
	public Container getNowPanel()
	{
		return main.getContentPane();
	}

	//�� �г��� ��ȯ
	public IntroPanel getIntroPanel() 
	{
		return introPanel;
	}
	public RankPanel getRankingPanel() 
	{
		return rankPanel;
	}
	public ExplanationPanel getExplanationPanel()
	{
		return explanationPanel;
	}
	public GamePanel getGamePanel() 
	{
		return gamePanel;
	}
}