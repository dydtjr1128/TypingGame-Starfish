
package panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import globalNum.GlobalNum;
import image.ImageCollection;
import panel.PanelManager;

public class ExplanationPanel extends JPanel
{
	private PanelManager panelManager;
	private DownStar downStar;

	private JButton backspaceBtn = new JButton(ImageCollection.normalBackspace);
	
	public ExplanationPanel(PanelManager panelManager)
	{
		this.panelManager = panelManager;
		setSize(1100, 800);
		setLayout(null);

		//버튼 이미지 넣는 방법
		backspaceBtn.setBounds(830, 540, 64, 64);
		backspaceBtn.setMargin(new Insets(0,0,0,0));		
		backspaceBtn.setOpaque(false);
		backspaceBtn.setBorderPainted(false);
		backspaceBtn.setBorder(BorderFactory.createEmptyBorder());
		backspaceBtn.setFocusPainted(false);
		backspaceBtn.setContentAreaFilled(false);
		backspaceBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				downStar.finish();
				new SoundClass("Sounds//클릭.wav").play();
				panelManager.setContentPane(GlobalNum.INTRO);
			}
		});
		
		backspaceBtn.setRolloverIcon(ImageCollection.overBackspace);
		
		add(backspaceBtn);
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
		g.setFont(new Font("HY궁서B",Font.ITALIC, 20));
		
		g.drawString("   # 게임 설명 #", 150, 150);
		g.drawString("- 레벨을 선택합니다. 레벨을 선택하지 않을 시 1레벨부터 게임을 시작합니다.", 150, 180);
		g.drawString("- 게임 시작창으로 들어가 파일 창을 이용해 단어를 로드합니다.", 150, 210);
		g.drawString("- 단어 추가 버튼을 이용해 떨어지는 단어를 추가합니다.", 150, 240);
		g.drawString("- 게임시작 버튼을 눌러 게임을 시작해 떨어지는 단어를 타이핑합니다.", 150, 270);
		g.drawString("- 게임은 5레벨까지 있으며 점수를 이용해 랭크가 높아지면 이기는 게임입니다.", 150, 300);
		
		g.drawString("   # 아이템 설명 #", 150, 380);
		g.drawString("- 노랑색 글자를 타이핑 시 모든 단어들을 클리어합니다.", 150, 410);
		g.drawString("- 주황색 글자를 타이핑 시 모든 단어들을 2초 멈춥니다.", 150, 440);
		g.drawString("- 보라색 글자를 타이핑 시 라이프를 하나 늘려줍니다.", 150, 470);
		
		g.drawString("   # 제작자 코멘트 #", 150, 550);
		g.drawString("- 김준희, 최용석 화이팅! 컴공 화이팅!!! 황기태 교수님 사랑합니다.", 150, 580);
		
		g.drawRect(100, 100, getWidth()-200, getHeight()-200);
	}
}
