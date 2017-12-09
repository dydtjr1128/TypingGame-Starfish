
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

		//��ư �̹��� �ִ� ���
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
				new SoundClass("Sounds//Ŭ��.wav").play();
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
		g.setFont(new Font("HY�ü�B",Font.ITALIC, 20));
		
		g.drawString("   # ���� ���� #", 150, 150);
		g.drawString("- ������ �����մϴ�. ������ �������� ���� �� 1�������� ������ �����մϴ�.", 150, 180);
		g.drawString("- ���� ����â���� �� ���� â�� �̿��� �ܾ �ε��մϴ�.", 150, 210);
		g.drawString("- �ܾ� �߰� ��ư�� �̿��� �������� �ܾ �߰��մϴ�.", 150, 240);
		g.drawString("- ���ӽ��� ��ư�� ���� ������ ������ �������� �ܾ Ÿ�����մϴ�.", 150, 270);
		g.drawString("- ������ 5�������� ������ ������ �̿��� ��ũ�� �������� �̱�� �����Դϴ�.", 150, 300);
		
		g.drawString("   # ������ ���� #", 150, 380);
		g.drawString("- ����� ���ڸ� Ÿ���� �� ��� �ܾ���� Ŭ�����մϴ�.", 150, 410);
		g.drawString("- ��Ȳ�� ���ڸ� Ÿ���� �� ��� �ܾ���� 2�� ����ϴ�.", 150, 440);
		g.drawString("- ����� ���ڸ� Ÿ���� �� �������� �ϳ� �÷��ݴϴ�.", 150, 470);
		
		g.drawString("   # ������ �ڸ�Ʈ #", 150, 550);
		g.drawString("- ������, �ֿ뼮 ȭ����! �İ� ȭ����!!! Ȳ���� ������ ����մϴ�.", 150, 580);
		
		g.drawRect(100, 100, getWidth()-200, getHeight()-200);
	}
}
