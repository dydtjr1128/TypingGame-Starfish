
package panel;

import java.awt.*;
import javax.swing.*;

import word.Word;
import globalNum.GlobalNum;
import image.ImageCollection;

/* 게임 내 모든 패널을 관리한다. 화면 전환과 패널 관리가 가능하다. */
public class PanelManager 
{
	public static GameFrame main;	//메인 프래임!
	
	private IntroPanel introPanel;		//인트로 패널
	private RankPanel rankPanel;		//랭크 패널
	private ExplanationPanel explanationPanel;	//설명 패널
	private GamePanel gamePanel;	//게임 시작 패널
//	private LoginPanel loginPanel;		//로그인 창 패널
//	private SignupPanel signupPanel;	//회원가입 창 패널
//	private PasuePanel pasuePanel;	//1:1 채팅창 구현 ㄱㄱ!

	//각 패널에 필요한 믈래스나 변수
	private Word word;
	public JButton startBtn = new JButton(ImageCollection.offStartImg);	//다씀!
	
	public PanelManager(GameFrame main, Word word) 
	{
		this.main = main;
		this.word = word;
		
		introPanel = new IntroPanel(this);	
		explanationPanel = new ExplanationPanel(this);
		gamePanel = new GamePanel(word, this);
	}

	//화면을 전환
	public static void setContentPane(Container panel) 
	{
		main.setContentPane(panel);
		// 코드의 유연성 증가
	}

	//정수를 이용 전환
	public void setContentPane(int panel)
	{
		// 기존코드 가독성 증가
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

	//메인 프레임 반환
	public GameFrame getMainFrame()
	{
		return main;
	}
	
	//현재 패널 반환
	public Container getNowPanel()
	{
		return main.getContentPane();
	}

	//각 패널을 반환
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