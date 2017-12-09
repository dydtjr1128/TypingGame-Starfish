package image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Image;

public abstract class ImageCollection 
{
	static public ImageIcon offStartImg = new ImageIcon("Images\\off시작.png");
	
	static public ImageIcon titleIcon = new ImageIcon("Images//별.png");
	static public Image titleImg = titleIcon.getImage();

	static public ImageIcon spaceIcon = new ImageIcon("Images//우주.jpg");
	static public Image spaceImg = spaceIcon.getImage();
	
	static public ImageIcon redIcon = new ImageIcon("Images//빨간불.png");
	static public Image redImg = redIcon.getImage();		
	
	static public ImageIcon blueIcon = new ImageIcon("Images//초록불.png");
	static public Image blueImg = blueIcon.getImage();
	
	static public ImageIcon exitBtnOffIcon = new ImageIcon("Images\\off종료.png");		
	static public ImageIcon exitBtnOnIcon = new ImageIcon("Images\\on종료.png");
	
	static public ImageIcon addWordBtnOffIcon = new ImageIcon("Images\\off단어추가.png");
	static public ImageIcon addWordBtnOnIcon = new ImageIcon("Images//on단어추가.png");
	
	static public ImageIcon startBtnOffIcon = new ImageIcon("Images//Disabled시작.png");
	static public ImageIcon startBtnOnIcon = new ImageIcon("Images//on시작.png");
	
	static public ImageIcon fallingNormalStarImg = new ImageIcon("Images//별.png");
	
	static public ImageIcon lifeStarIcon  = new ImageIcon("Images\\lifeStar.png");
	static public Image lifeStarImg = lifeStarIcon.getImage();	
	
	static public ImageIcon BackgroundTopIcon = new ImageIcon("Images//위.jpg");
	static public Image BackgroundTopImg = BackgroundTopIcon.getImage();
	
	static public ImageIcon clearItemIcon = new ImageIcon("Images//clearItem.png");
	
	static public ImageIcon BackgroundBottomIcon = new ImageIcon("Images//아래.jpg");
	static public Image BackgroundBottomImg = BackgroundBottomIcon.getImage();

	static public ImageIcon missileIcon = new ImageIcon("Images//미사일.png");
	static public Image missileImg = missileIcon.getImage();
	
	static public ImageIcon explosionIcon = new ImageIcon("Images//폭팔.png");
	static public Image explosionImg = explosionIcon.getImage();
	
	//인트로 별
	static public ImageIcon star1 = new ImageIcon("Images//새 폴더//1.png");
	static public ImageIcon star2 = new ImageIcon("Images//새 폴더//2.png");
	static public ImageIcon star3 = new ImageIcon("Images//새 폴더//3.png");
	static public ImageIcon star4 = new ImageIcon("Images//새 폴더//4.png");
	
	static public ImageIcon normalGameStart = new ImageIcon("Images//메인버튼//normalGameStart.png");
	static public ImageIcon normalLogin = new ImageIcon("Images//메인버튼//normalLogin.png");
	static public ImageIcon normalLevelChoice = new ImageIcon("Images//메인버튼//normalLevelChoice.png");
	static public ImageIcon normalGameExplanation = new ImageIcon("Images//메인버튼//normalGameExplanation.png");
	static public ImageIcon normalRanking = new ImageIcon("Images//메인버튼//normalRanking.png");
	static public ImageIcon normalExit = new ImageIcon("Images//메인버튼//normalExit.png");

	static public ImageIcon overGameStart = new ImageIcon("Images//메인버튼//overGameStart.png");
	static public ImageIcon overLogin = new ImageIcon("Images//메인버튼//overLogin.png");
	static public ImageIcon overLevelChoice = new ImageIcon("Images//메인버튼//overLevelChoice.png");
	static public ImageIcon overGameExplanation = new ImageIcon("Images//메인버튼//overGameExplanation.png");
	static public ImageIcon overRanking = new ImageIcon("Images//메인버튼//overRanking.png");
	static public ImageIcon overExit = new ImageIcon("Images//메인버튼//overExit.png");

	static public ImageIcon normalBackspace = new ImageIcon("Images//normalBackspace.png");
	static public ImageIcon overBackspace = new ImageIcon("Images//overBackspace.png");
	
	//파일 아이콘은 Icon
	static public Icon fileExit = new ImageIcon("Images//fileExit.png");
	static public Icon fileRead = new ImageIcon("Images//fileRead.png");

	static public ImageIcon normalLevel1 = new ImageIcon("Images\\레벨버튼\\1levelNormal.png");
	static public ImageIcon normalLevel2 = new ImageIcon("Images\\레벨버튼\\2levelNormal.png");
	static public ImageIcon normalLevel3 = new ImageIcon("Images\\레벨버튼\\3levelNormal.png");
	static public ImageIcon normalLevel4 = new ImageIcon("Images\\레벨버튼\\4levelNormal.png");
	static public ImageIcon normalLevel5 = new ImageIcon("Images\\레벨버튼\\5levelNormal.png");

	static public ImageIcon overLevel1 = new ImageIcon("Images\\레벨버튼\\1levelOver.png");
	static public ImageIcon overLevel2 = new ImageIcon("Images\\레벨버튼\\2levelOver.png");
	static public ImageIcon overLevel3 = new ImageIcon("Images\\레벨버튼\\3levelOver.png");
	static public ImageIcon overLevel4 = new ImageIcon("Images\\레벨버튼\\4levelOver.png");
	static public ImageIcon overLevel5 = new ImageIcon("Images\\레벨버튼\\5levelOver.png");

	static public ImageIcon arrowIcon = new ImageIcon("Images//arrow.png");
	static public Image arrowImg = arrowIcon.getImage();
	
	static public ImageIcon normalSoloPlay = new ImageIcon("Images\\normalSoloPlay.png");
	static public ImageIcon normalWithPlay = new ImageIcon("Images\\normalWithPlay.png");
	
	static public ImageIcon overSoloPlay = new ImageIcon("Images\\overSoloPlay.png");
	static public ImageIcon overWithPlay = new ImageIcon("Images\\overWithPlay.png");
	static public ImageIcon heart = new ImageIcon("Images\\lover.png");
	
	static public ImageIcon normalInputName = new ImageIcon("Images\\normalInputName.png");
	static public ImageIcon overInputName = new ImageIcon("Images\\overInputName.png");
	
	static public ImageIcon soundOnItem = new ImageIcon("Images\\soundOnItem.png");
	static public ImageIcon soundOffItem = new ImageIcon("Images\\soundOffItem.png");
}
