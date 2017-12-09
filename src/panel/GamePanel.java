package panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import image.ImageCollection;
import word.Word;
import globalNum.GlobalNum;
import panel.PanelManager;

//���� �׶��� �г� + ��ǲ �г� (�ܾ� �������� ������, ��� üũ ������)
public class GamePanel extends JPanel {
	private PanelManager panelManager;
	private Word word;
	public JButton startBtn; // �پ�!

	private JToolBar tb;
	private JDialogEx wordJDialog; // �ܾ� �߰� ���̾�α� â

	public JTextField textField;
	private GameGroundPanel groundPanel = new GameGroundPanel();
	private GameInputPanel inputPanel = new GameInputPanel();

	private WordMakeThread wordMakeThread; // �ܾ� ���� �ӵ� ���� ����
	private HeartCheckThread heartCheckThread;
	private Vector<WordDownThread> threadVector = new Vector<WordDownThread>();

	private int score = 0;// ����
	private int heartNum = 5;// ���
	public static int gameLevel = 1;
	int runControl = GlobalNum.STOP; // �������� ������ �̰� ���� ��Ű������ ��� �����ؾ���

	public GamePanel(Word word, PanelManager panelManager) {
		setSize(1100, 800);
		this.word = word;
		this.panelManager = panelManager;
		startBtn = panelManager.startBtn;
		setLayout(new BorderLayout());
		makeToolbar();
		add(groundPanel, BorderLayout.CENTER);
		add(inputPanel, BorderLayout.SOUTH);
		repaint();
	}

	public void startThread() {
		score = 0;
		heartNum = 5;
		runControl = GlobalNum.STOP;
		wordMakeThread = new WordMakeThread();
		heartCheckThread = new HeartCheckThread();
		wordMakeThread.start();
		heartCheckThread.start();				
	}

	public void makeToolbar() {
		tb = new JToolBar() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(ImageCollection.spaceImg, 0, 0, getWidth(), getHeight(), this);
				g.setFont(new Font("HY����L", Font.ITALIC, 18));
				g.setColor(Color.white);
				if (panelManager.getGamePanel().runControl == GlobalNum.STOP) {
					g.drawImage(ImageCollection.redImg, 780, 10, 14, 14, this);
					g.drawString("Break!", 800, 23);
				}
				if (panelManager.getGamePanel().runControl == GlobalNum.START) {
					g.drawImage(ImageCollection.blueImg, 780, 10, 14, 14, this);
					g.drawString("Running!", 800, 23);
				}
			}
		};

		// ���� ��ư
		JButton exitbtn = new JButton(ImageCollection.exitBtnOffIcon);
		exitbtn.setPressedIcon(ImageCollection.exitBtnOnIcon);
		exitbtn.setOpaque(false);
		exitbtn.setBorder(null);
		exitbtn.addActionListener(new ActionListener() {
			// �ϴ� ����� ���� �Ŀ� �� ���̵�� ������ ����
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// �ܾ� �߰� ��ư
		JButton addWordBtn = new JButton(ImageCollection.addWordBtnOffIcon);
		addWordBtn.setPressedIcon(ImageCollection.addWordBtnOnIcon);
		addWordBtn.setBorder(null);
		addWordBtn.setOpaque(false);
		addWordBtn.addActionListener(new ActionListener() {
			// �������� ���̾�α� â ����
			@Override
			public void actionPerformed(ActionEvent e) {
				wordJDialog = new JDialogEx(panelManager.getMainFrame());
				wordJDialog.setVisible(true);
				System.gc();
			}
		});

		// ó���� �����ϰ�!
		startBtn.setEnabled(false);
		startBtn.setDisabledIcon(ImageCollection.startBtnOffIcon);
		startBtn.setPressedIcon(ImageCollection.startBtnOnIcon);
		startBtn.setBorder(null);
		startBtn.setOpaque(false);

		// ���� �����Ѵٸ� ��ŸƮ�� �ٽ� ĥ�ض�!
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (panelManager.getGamePanel().runControl == GlobalNum.STOP) {
					panelManager.getGamePanel().runControl = GlobalNum.START;
					tb.repaint();
				}
			}
		});

		// �ܾ� �� �߰�
		tb.add(exitbtn);
		tb.addSeparator();
		tb.add(addWordBtn);
		tb.addSeparator();
		tb.add(startBtn);
		tb.addSeparator();

		tb.setFloatable(false); // ���� ����
		tb.setBackground(Color.LIGHT_GRAY);

		this.add(tb, BorderLayout.NORTH);

	}

	class JDialogEx extends JDialog // �ܾ� �߰� ������ �� ���̾�α� â
	{
		private JTextField text = new JTextField(20);		
		private JButton addButton = new JButton(ImageCollection.addWordBtnOffIcon);

		private JList<String> wordList;
		private JScrollPane scroll;
		private boolean run = false;
		
		// ���̾�α�â�� ������Ʈ�� ��� ���� �����Ƿ� �г��� ����� ������ ����� �ٹ�
		private JPanel mainPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(ImageCollection.spaceImg, 0, 0, getWidth(), getHeight(), this);
			}
		};

		public JDialogEx(GameFrame f) {
			super(f, "Add Word", true);
			setContentPane(mainPanel);
			mainPanel.setLayout(null);

			addButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int count = 0;// ���� �ܾ��� �� ��
					String inputTxt = text.getText();

					// ������� �ܾ��߰� ����
					if (inputTxt.equals(""))
						return;

					for (int i = 0; i < word.getSize(); i++)
						if (inputTxt.equals(word.getWord(i)))
							count++;
					if (count >= 1) // ���� �ܾ ������
						JOptionPane.showMessageDialog(null, "�Է��� �ܾ�� �ִ� �ܾ��Դϴ�.", "Error!!", JOptionPane.ERROR_MESSAGE);
					else // ������
					{
						word.inputWord(inputTxt);
						JOptionPane.showMessageDialog(null, "��ϼ���!", "���!!", JOptionPane.INFORMATION_MESSAGE);
					}

					text.setText(""); // �Է�â �ٽ� �ʱ�ȭ

					// �ܾ� �߰��� ȭ�� �ٷ� �ٲ�°� ���� ���ԤФ�
				}
			});

			text.setBounds(80, 20, 200, 30);
			addButton.setBounds(300, 20, 80, 33);

			wordList = new JList<String>(word.getWordVector());
			scroll = new JScrollPane(wordList);

			scroll.setBounds(400, 20, 100, 100);

			mainPanel.add(text);
			mainPanel.add(addButton);

			mainPanel.add(scroll);
			setSize(550, 200);
		}
	}
	class RankingJDialog extends JDialog // �ܾ� �߰� ������ �� ���̾�α� â
	{		
		private JButton addButton = new JButton(ImageCollection.normalInputName);		
		private JTextField nameField = new JTextField();
		private JLabel myScore = new JLabel("����  :  " + Integer.toString(score));
		private FileWriter fw = null;
		private BufferedWriter out = null;
		
		// ���̾�α�â�� ������Ʈ�� ��� ���� �����Ƿ� �г��� ����� ������ ����� �ٹ�
		private JPanel mainPanel = new JPanel() 
		{
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(ImageCollection.spaceImg, 0, 0, getWidth(), getHeight(), this);
			}
		};
		
		public RankingJDialog(GameFrame mainFrame) 
		{
			super(mainFrame, "Add Ranking", true);
			setContentPane(mainPanel);			
			mainPanel.setLayout(null);
			nameField.setBounds(30, 30, 200, 35);
			addButton.setRolloverIcon(ImageCollection.overInputName);
			addButton.setBounds(240, 30, 50, 35);
			myScore.setForeground(Color.white);
			myScore.setBounds(310, 30, 100, 35);
			mainPanel.add(nameField);
			mainPanel.add(addButton);
			mainPanel.add(myScore);			
			addButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String name = nameField.getText() +"&" + score;	
					
					try {
						fw = new FileWriter(new File("Rank.txt"),true);
						out = new BufferedWriter(fw);
						out.write(name + "\n");

					} catch (IOException e1) {
						e1.printStackTrace();
					} finally {
						try {
							if (out != null)
								out.close();
							if (fw != null)
								fw.close();
						} catch (IOException e2) {
							e2.printStackTrace();
						}
					}
					heartCheckThread.inputranking = false;					
					setVisible(false);
				}
			});
			setSize(430, 150);
			setLocation(mainFrame.getLocation().x + 300, mainFrame.getLocation().y + 200);
		}
	}

	class GameGroundPanel extends JPanel // �ܾ������ �г�
	{
		public GameGroundPanel() // �ʱ� �ܾ� ����� �����带 ����!
		{
			setLayout(null);
			setFocusable(false);
		}

		public void printWord() // �������� �ܾ� �����ִ� �Լ�
		{
			if (word.state) // �ܾ �ִٸ�
			{
				int x = (int) (Math.random() * (getWidth() - 200)); // ������ ��ġ
				int y = 0;
				int index = (int) (Math.random() * word.getSize()); // ������ �ε���

				JLabel labelText = new JLabel(word.getWord(index), SwingConstants.CENTER);
				JLabel labelImg = new JLabel(ImageCollection.fallingNormalStarImg);
				labelText.setForeground(Color.white);
				labelText.setLocation(x, y);
				labelText.setSize(200, 50);
				labelText.setFont(new Font("HY�ٴ�L", Font.ITALIC, 20));

				labelImg.setLocation(x, y);
				labelImg.setSize(205, 100);

				int ran = (int) (Math.random() * (20 + (gameLevel*2)));

				if (ran == 0) // �������� �Ķ��� �ܾ� ���� ���� Ȯ���� ���� ������ �ϴ� 0~5
				{
					labelImg.setIcon(ImageCollection.clearItemIcon);
					labelText.setForeground(Color.yellow);
					labelText.setFont(new Font("HY�ٴ�L", Font.ITALIC, 30));
				}
				if (ran == 1) {
					labelImg.setIcon(ImageCollection.clearItemIcon);
					labelText.setForeground(Color.orange);
					labelText.setFont(new Font("HY�ٴ�L", Font.ITALIC, 30));
				}
				if (ran == 2) {
					labelImg.setIcon(ImageCollection.heart);
					labelText.setForeground(Color.magenta);
					labelText.setFont(new Font("HY�ٴ�L", Font.ITALIC, 30));
				}

				int wordspeed = (int) (Math.random() * 30) + 15 - (int) (gameLevel * 2);
				WordDownThread wordDownThread;
				wordDownThread = new WordDownThread(labelText, labelImg, wordspeed);// ����
				// ���̶�
				// ���ǵ�
				// �Ѱ���
				threadVector.add(wordDownThread); // �������� ������ ���Ϳ� ����
				wordDownThread.start(); // �������� ������ ����

				add(labelText);
				add(labelImg);
				repaint();
			}
		}

		@Override
		public void paintComponent(Graphics g) // �Ѱ輱 ����
		{
			super.paintComponent(g);
			g.drawImage(ImageCollection.BackgroundTopImg, 0, 0, getWidth(), getHeight(), this);
			g.drawLine(0, getHeight() - 30, getWidth(), getHeight() - 30);
			g.setColor(Color.yellow);
			g.setFont(new Font("HY����L", Font.ITALIC, 25));

			g.drawString("��� : ", 20, getHeight());
			for (int i = 0; i < heartNum; i++) {
				g.drawImage(ImageCollection.lifeStarImg, 100 + (i * 20), getHeight() - 20, 20, 20, this);
			}
		}
	}

	// �ӷ��ϴ� �г�
	class GameInputPanel extends JPanel {
		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(ImageCollection.BackgroundBottomImg, 0, 0, this.getWidth(), this.getHeight(), this);
			g.setColor(Color.yellow);
			g.setFont(new Font("HY����L", Font.ITALIC, 25));
			g.drawString("���� : " + score, 20, 30);
		}

		public GameInputPanel() {
			setLayout(null);
			this.setPreferredSize(new Dimension(1100, 60));

			textField = new JTextField() {
				public void paintComponent(Graphics g) {
					g.drawImage(ImageCollection.BackgroundBottomImg, 0, 0, this);
					this.setCaretColor(Color.red); // �ؽ�Ʈ â �����̸� �Ͼ������
					super.paintComponent(g);
				}
			};			
			textField.setHorizontalAlignment(JTextField.CENTER);
			textField.setForeground(Color.white);
			textField.setOpaque(false);
			textField.setFont(new Font("HY����L", Font.ITALIC, 20));
			textField.setBounds(380, 0, 300, 40);

			add(textField);

			// Ű�����Է�
			textField.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE)// �Է��Ѱ� escŰ�̸�
					{
						if (runControl == GlobalNum.START)// �⺻1���� esc������ 2�κ�ȯ
						{
							runControl = GlobalNum.STOP;
						} else if (runControl == GlobalNum.STOP)// 2������ 1�κ�ȯ
						// �Ͻ����� ����
						{
							runControl = GlobalNum.START;
						}
						// esc�������� �ٽ� ĥ�ض�
						tb.repaint();
					}
					if (e.getKeyCode() == KeyEvent.VK_ENTER) // ���ʹ�����
					{
						JTextField textfield = (JTextField) e.getSource();
						String text = textfield.getText();
						Vector<JLabel> sameLabel = new Vector<JLabel>();
						Vector<Integer> sameLabelIndex = new Vector<Integer>();
						textfield.setText("");// �ʵ� ���
						for (int i = 0; i < threadVector.size(); i++) {
							JLabel la = threadVector.get(i).getTextLabel();
							if (text.equals(la.getText())) {
								sameLabelIndex.add(i);
								sameLabel.add(la);
							}

						}
						if (sameLabel.size() != 0)// ���̵�� ������
						{
							JLabel maxLabel = sameLabel.get(0);
							int maxLabelIndex = sameLabelIndex.get(0);
							if (sameLabel.size() > 1) // ���� �ܾ �ΰ� �̻� ������
							// ����Ʒ��ִ´ܾ� ã��
							{
								for (int i = 1; i < sameLabel.size(); i++) {
									JLabel la = sameLabel.get(i);
									if (maxLabel.getLocation().y < la.getLocation().y) {
										maxLabel = la;
										maxLabelIndex = sameLabelIndex.get(i);
									}

								}
							}

							WordDownThread maxLabelThread = threadVector.get(maxLabelIndex); // �Է¹���

							Color labelForeColor = maxLabel.getForeground();
							ItemThread itemThread = new ItemThread(maxLabelThread);
							if (labelForeColor == Color.yellow){
								itemThread.setMissileAll();
								score= score + 50 + (gameLevel*10);
							}							
							else if (labelForeColor == Color.orange) {
								itemThread.setItem(GlobalNum.TWO_SECOND_STOP);
							}
							else if (labelForeColor == Color.magenta) {
								heartNum++;
								if(heartNum>10) heartNum = 10;
							}
							itemThread.start();
							score += (100 + text.length() * 10) + (gameLevel * 50);
							groundPanel.repaint();
							repaint();
						}
					}
				}
			});
		}
	}

	class WordDownThread extends Thread {
		private JLabel labelText, labelImg;
		private int speed;
		private int UPDOWN = 1;

		void setUPDOWN(int UPDOWN) // ���ΰ��� �Ʒ��ΰ��� ���ϱ�
		{
			this.UPDOWN = UPDOWN;
		}

		public JLabel getTextLabel() {
			return labelText;
		}

		public JLabel getImgLabel() {
			return labelImg;
		}

		public int getSpeed() {
			return speed;
		}

		public WordDownThread(JLabel labelText, JLabel labelImg, int speed) {
			this.labelText = labelText;
			this.labelImg = labelImg;
			this.speed = speed;
		}

		public synchronized void run() {
			while (true) {
				try {
					while (runControl == GlobalNum.STOP) {
						Thread.sleep(10);
					}

					labelText.setLocation(labelText.getLocation().x, labelText.getLocation().y + UPDOWN);
					labelImg.setLocation(labelImg.getLocation().x, labelImg.getLocation().y + UPDOWN);

					Thread.sleep(speed);

					if (labelText.getLocation().y >= getHeight() - 190) // ��������
					// ����
					// ����������
					// ����
					{
						heartNum--;
						groundPanel.remove(labelText);
						groundPanel.remove(labelImg);
						groundPanel.repaint();
						threadVector.remove(this);
						this.interrupt(); // �ܾ �������� ��� �������� ����
					}
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}

	class WordMakeThread extends Thread {
		public void run() {
			while (true) {
				try {
					if (word.state) {
						// ���� ���Ҷ����� ���� ����
						if (score >= 1000 * gameLevel * 2.5 && gameLevel != 5) {
							textField.setEditable(false);
							gameLevel++;
							runControl = GlobalNum.STOP;
							JLabel label = new JLabel("���� " + gameLevel);
							label.setForeground(Color.yellow);
							label.setFont(new Font("HY����L", Font.ITALIC, 40));
							label.setBounds(450, 300, 200, 200);
							label.setHorizontalAlignment(SwingConstants.CENTER);
							label.setVerticalAlignment(SwingConstants.CENTER);
							groundPanel.add(label);
							groundPanel.repaint();
							try {
								int i;
								Thread.sleep(1000);
								for (i = 5; i > 0; i--) {
									label.setText(Integer.toString(i));
									Thread.sleep(1000);
									groundPanel.repaint();
								}
							} catch (InterruptedException e1) {
							}
							groundPanel.remove(label);
							textField.setEditable(true);
							runControl = GlobalNum.START;
						}
						if (gameLevel >= 5)
							gameLevel = 5;
						while (runControl == GlobalNum.STOP) {
							Thread.sleep(10);
						}

						groundPanel.printWord();

					}
					Thread.sleep(1000 - (gameLevel * 100));
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}

	class HeartCheckThread extends Thread {		
		Boolean inputranking = true;
		public void run() {
			while (true) {
				if (heartNum == 0) {
					wordMakeThread.interrupt();
					for (int i = 0; i < threadVector.size(); i++) {
						WordDownThread th = threadVector.get(i);
						JLabel imgLabel = th.getImgLabel();
						JLabel txtLabel = th.getTextLabel();
						groundPanel.remove(imgLabel);
						groundPanel.remove(txtLabel);
						th.interrupt();
					}
					threadVector.removeAllElements();
					groundPanel.removeAll();
					groundPanel.repaint();					
				
					JLabel gameOver = new JLabel("Game Over!!!");
					gameOver.setBounds(400, 200, 400, 100);					
					gameOver.setFont(new Font("HY����L", Font.BOLD, 40));
					gameOver.setForeground(Color.yellow);					
					groundPanel.add(gameOver);
					groundPanel.repaint();
					
					try {
						sleep(3000);
					} catch (InterruptedException e) {
						return;
					}
					RankingJDialog rankingJDialog =new RankingJDialog(panelManager.getMainFrame());
					rankingJDialog.setVisible(true);
					groundPanel.remove(gameOver);
					gameLevel = 1;
					score = 0;
					panelManager.setContentPane(GlobalNum.INTRO);
					while(inputranking){
						
					}					
					this.interrupt();
				}
				try {
					sleep(10);
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}

	class ItemThread extends Thread {
		private JLabel mis = new JLabel(ImageCollection.missileIcon);
		private int missileX;
		private int missileY = groundPanel.getHeight() - 80;
		private int missileSpeed = 1;
		JLabel destroyLabel;
		JLabel destroyImgLabel;
		WordDownThread destroyThread;
		private int myItem = 0;
		boolean missileAll = false;
		SoundClass sound = new SoundClass("Sounds//ǲ��.wav");		
		public ItemThread(WordDownThread downThread) {
			this.destroyLabel = downThread.getTextLabel();
			this.destroyImgLabel = downThread.getImgLabel();
			this.destroyThread = downThread;
			missileX = destroyLabel.getX();
			mis.setBounds(missileX + 30, missileY, 130, 100);
			groundPanel.add(mis);
		}

		public void setItem(int n) {
			myItem = n;
		}

		public void setMissileAll() {
			missileAll = true;
		}

		public void destroyStar() {	
			sound.play();
			mis.setLocation(missileX + 30, mis.getY() - missileSpeed);
			missileSpeed += 1;
			groundPanel.repaint();

			try {
				Thread.sleep(destroyThread.getSpeed());
			} catch (InterruptedException e1) {
				return;
			}
			if (mis.getY() <= destroyLabel.getY()) {
				mis.setIcon(ImageCollection.explosionIcon);
				groundPanel.remove(destroyImgLabel);
				groundPanel.remove(destroyLabel);
				groundPanel.repaint();
				synchronized (this) {
					threadVector.remove(destroyThread);
					destroyThread.interrupt();
				}
				try {
					if (myItem == GlobalNum.TWO_SECOND_STOP && runControl == GlobalNum.START) {
						runControl = GlobalNum.STOP;
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							return;
						}
						runControl = GlobalNum.START;
						myItem = 0;
					} else
						sleep(500);
				} catch (InterruptedException e) {
					return;
				}
				groundPanel.remove(mis);
				groundPanel.repaint();
				if (missileAll)
					destroyAllStar();
				this.interrupt();
			}
		}

		public void destroyAllStar() {
			missileAll = false;
			for (int i = 0; i < threadVector.size(); i++) {
				ItemThread missile = new ItemThread(threadVector.get(i));
				missile.start();
			}
			interrupt();
		}

		public void run() {
			while (true) {
				destroyStar();
			}
		}
	}
}