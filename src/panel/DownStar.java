package panel;

import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DownStar 
{
	private Vector<DownThread> downThreadVector = new Vector<DownThread>();
	private JPanel myPanel;
	MakeThread myMakeThread = new MakeThread();

	public DownStar(JPanel myPanel) 
	{
		this.myPanel = myPanel;
	}

	public void finish() 
	{
		myMakeThread.interrupt();
		for (int i = 0; i < downThreadVector.size(); i++) 
		{
			DownThread th = downThreadVector.get(i);
			th.remove();
			th.interrupt();
		}
		myPanel.repaint();
	}

	public void start() {
		myMakeThread.start();
	}

	class MakeThread extends Thread {
		public void run() {
			while (true) {
				int randomStar = (int) (Math.random() * 4) + 1;
				int starX = (int) (Math.random() * myPanel.getWidth());
				ImageIcon icon = new ImageIcon("Images//새 폴더//" + randomStar + ".png");

				JLabel showLabel = new JLabel(icon);
				showLabel.setBounds(starX, 40, 25, 25);
				DownThread downThread = new DownThread(showLabel);
				downThreadVector.add(downThread);
				downThread.start();
				myPanel.add(showLabel);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					return;
				}

			}
		}
	}

	class DownThread extends Thread {
		private JLabel ImgLabel;
		private int lr;
		private int howToMove;
		private int count = 0;

		public DownThread(JLabel ImgLabel) {
			this.ImgLabel = ImgLabel;
			lr = (int) (Math.random() * 2);
			howToMove = 1;
		}

		public void remove() {
			myPanel.remove(ImgLabel);
		}

		public void run() {
			while (true) {
				if (lr == 0) // 오른쪽
				{
					if (count % 4 == 0)
						ImgLabel.setLocation(ImgLabel.getX() + howToMove, ImgLabel.getY() + 1);
					else
						ImgLabel.setLocation(ImgLabel.getX(), ImgLabel.getY() + 1);
				} else {
					if (count % 4 == 0)
						ImgLabel.setLocation(ImgLabel.getX() - howToMove, ImgLabel.getY() + 1);
					else
						ImgLabel.setLocation(ImgLabel.getX(), ImgLabel.getY() + 1);
				}
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					return;
				}
				if (ImgLabel.getY() > myPanel.getHeight() - 30) {
					myPanel.remove(ImgLabel);
					myPanel.repaint();
					interrupt();
				}
				count++;
			}
		}
	}
}
