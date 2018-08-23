/*
 * This class contains the end screen 
 * @author Andy Jin and Hubert Yoo
 * @date 2017-01-23
 */

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class End{
  private boolean main = true;
  //Buttons
  Rectangle backButton = new Rectangle(1551,875,269,182);
  Rectangle exitButton =new Rectangle(852,833,311,91); 
  JFrame frame; 
  int flag=1;
  String winner;
  
  /*
 * This method is the constructor to the end screen
 * @param: frame information and the winner stored as the string 
 * @return: void
 */
  public End (int w, int h, String title, String winner) throws IOException{
    //Set up the frame 
    frame = new JFrame (title);
    frame.setSize(w, h);
    frame.add(new EndPanel());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.addMouseListener(new MouseScreen());
    this.winner=winner;
    //Set the flag based on the winner
    if (winner.equals("Player")){
      flag=3;
    } else if (winner.equals("Enemy")){
      flag=4;
    }
  }
  
  /*
 * This method is setter to main
 * @param: boolean
 * @return: void
 */
  public void setMain (boolean main){
    this.main=main;
  }
  
  /*
 * This method is getter to main
 * @param: void
 * @return: boolean
 */
  public boolean isMain(){
    return main;
  }
  
  /**
   * This class extends mouseAdapter and gets the information of mouseclicks
   * @author Hubert Yoo and Andy Jin 
   * @date 2017-01-23
   */
  public class MouseScreen extends MouseAdapter{
    public void mousePressed(MouseEvent event){
      //Get point clicked
      Point click = event.getPoint();
      System.out.println(click);
      //Exit 
      if (exitButton.contains(click)){
        flag=1;
      } else if (backButton.contains(click)){
        flag=2;
      } 
      
    } 
  }
  
  /**
   * This class contains information and extends JPanel
   * @author Hubert Yoo and Andy Jin 
   * @date 2017-01-23
   */
  public class EndPanel extends JPanel {
    BufferedImage winScreen;
    BufferedImage loseScreen;
    
    public EndPanel () throws IOException{
      winScreen = ImageIO.read(new File("gameover.jpg"));
      loseScreen = ImageIO.read(new File("youlose.jpg"));
    }
    
    /*
     * This method is the paint component
     * @param: graphics g
     * @return: void
     */
    public void paintComponent(Graphics g){
      System.out.println(flag);
      super.paintComponent(g);
      if (flag ==3){
        g.drawImage(winScreen,0,0,this);//Display win screen
        
        if (flag==1){ //If exit is pressed
          System.exit(0);
        } else if (flag==2){
        }
        
      } else if (flag==4){ //Display lose screen 
        g.drawImage(loseScreen,0,0,this);
        if (flag==1){
          System.exit(0);
        } else if (flag==2){
        }
      }

      repaint();
    }
  }

}
