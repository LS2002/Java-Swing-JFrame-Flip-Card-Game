import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;

public class FlipCardGame{

	static int[] cardNum, cardNumResult;
	static int clickX , clickY , memory, cardNum1, cardNum2;
	static JFrame myWindow;
	static JPanel cardPanel;
	static JLabel card;
	static Image[] cardImage = new Image[6];
	static ImageIcon cardBack, cardFront;
	static int lastPosition = 0;
	static JLabel timerLabel;
	static Timer timer;
	static boolean timerStarted = false;

	public static void main (String[] args){

		JFrame myWindow = new JFrame("Alice in Wonderland");
		myWindow.setSize(1200,675);
		myWindow.setResizable(false);  
		myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		myWindow.setLocation(dim.width/2-myWindow.getSize().width/2, dim.height/2-myWindow.getSize().height/2);
		
		myWindow.setContentPane(new JLabel(new ImageIcon("images/tile background2.jpg")));
		myWindow.setLayout(null);
		myWindow.setResizable(false);
		
		timerLabel = new JLabel("Time: 0s");
		timerLabel.setLocation(100, 500);
		timerLabel.setSize(250, 60);
		
		timer = new Timer();

		cardPanel = new JPanel();
		cardPanel.setLayout(new GridLayout(3,4));
		cardPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
		cardPanel.addMouseListener(new mouseListener());
		cardPanel.setOpaque(false);
		cardPanel.setLocation(300, 0);
		cardPanel.setSize(600, 600);

		myWindow.add(cardPanel);
		myWindow.add(timerLabel);

		myWindow.setVisible(true);
				
		cardNum = new int[12];
		cardNumResult = new int[12];
		int randomCardId=0;
		for(int i=0;i<12;i++){
			//put current image in random position 1
			randomCardId = new Random().nextInt(6)+1;
			while (countOccurrance(cardNum, randomCardId)>=2){
				randomCardId = new Random().nextInt(6)+1;
			}
			cardNum[i] = randomCardId;
		}
		
		for(int i=0;i<12;i++){
			drawImage(cardPanel,0);
		}
	}
	
	static int countOccurrance(int[] card, int theCard){
		int occurrance = 0;
		for(int i=0;i<card.length;i++){
			if(card[i]==theCard){
				occurrance++;
			}
		}
		return occurrance;
	}
	
	static void drawImage(JPanel panel, int cardId){
		JLabel img = new JLabel(new ImageIcon("images/card "+cardId+".jpg"));
		img.setHorizontalAlignment(JLabel.CENTER);
		panel.add(img);
		panel.revalidate();
	}

	static class mouseListener implements MouseListener{
		public void mousePressed(MouseEvent e) {}

		public void mouseReleased(MouseEvent e) {}

		public void mouseEntered(MouseEvent e) {}

		public void mouseExited(MouseEvent e) {}
		
		public void mouseClicked(MouseEvent e){
			int clickedCardPosition = e.getX()/150 + e.getY()/200 *4;//get the position of the clicked card, start from 0

			if (!timerStarted){
				timerStarted=true;
				timer.scheduleAtFixedRate(new TimerTask(){
					int counter = 0;
					@Override
					public void run() {
						timerLabel.setText("Time: "+ (counter++) +"s");
					}
					
				}, 0,1000);
			}
			
			cardPanel.removeAll();
						
			for(int i=0;i<12;i++){
				if (cardNumResult[i]==1){
					drawImage(cardPanel,cardNum[i]);
				}else{
					if(i==clickedCardPosition){
	
						if (cardNum[lastPosition] == cardNum[clickedCardPosition] && lastPosition!=clickedCardPosition){
							cardNumResult[clickedCardPosition]=1;//save matched card into result array
							cardNumResult[lastPosition]=1;
						}
						drawImage(cardPanel,cardNum[clickedCardPosition]);
						
					}else{
							drawImage(cardPanel,0);
					}
				}
			}
			
			int counterResult = 0;
			for(int i=0;i<12;i++){
				counterResult+=cardNumResult[i];
			}
			if (counterResult==12)
				timer.cancel();
						
			lastPosition = clickedCardPosition;
		}
	}
}
