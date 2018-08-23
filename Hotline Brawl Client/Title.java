/**
 * This class contains the title screen panel 
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

public class Title{
  //All the buttons are rectangles NOT Jbuttons
 private boolean play = false;
 private boolean instruction = false;
 private boolean main = true;
 Rectangle playButton = new Rectangle (850,580,322,83);
 Rectangle instructionButton = new Rectangle (618,692,808,100);
 Rectangle backButton = new Rectangle(1551,875,269,182);
 Rectangle exitButton =new Rectangle(852,833,311,91); 
 JFrame frame; 
 int flag=1;
 
 public Title (int w, int h, String title) throws IOException{
  frame = new JFrame (title);
  frame.setSize(w, h);
  frame.add(new TitlePanel());
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frame.setResizable(false);
  frame.setLocationRelativeTo(null);
  frame.setVisible(true);
  //Add the mouse listener
  frame.addMouseListener(new MouseScreen());
 }

 //Getters and setters for the private varaibles 
 
 public boolean isPlay() {
  return play;
 }

 public void setPlay(boolean play) {
  this.play = play;
 }

 public boolean isInstruction() {
  return instruction;
 }

 public void setInstruction(boolean instruction) {
  this.instruction = instruction;
 }
 
 public void setMain (boolean main){
   this.main=main;
 }
 
 public boolean isMain(){
   return main;
 }

 /**
 * This class is the mouse click and extends mouse adapter
 * @author Andy Jin and Hubert Yoo
 * @date 2017-01-23
 */
 public class MouseScreen extends MouseAdapter{
  public void mousePressed(MouseEvent event){
   Point click = event.getPoint(); //Get point clicked
   System.out.println(click);
   if (flag==1){ //If the main screen is present 
     if (instructionButton.contains(click)){
       flag=2;
     } else if (exitButton.contains(click)){
       flag=3;
     } else if (playButton.contains(click)){
       setPlay(true);
       flag=4;
     }
   } else if (flag==2){ //if the user is looking at instructions screen
     if (backButton.contains(click)){
       flag=1;
     }
   }
     
   if (play){ //If user clicks play , dispose of the frame 
     frame.dispose();
   }
   
  }
  
 }
 
 /**
 * This class contains the title Panel and graphics
 * @author Andy Jin and Hubert Yoo
 * @date 2017-01-23
 */
public class TitlePanel extends JPanel {
  BufferedImage titleScreen;
  BufferedImage instructionScreen;

  public TitlePanel () throws IOException{
   titleScreen = ImageIO.read(new File("titlescreenv1.jpg"));
   instructionScreen = ImageIO.read(new File("instruction.jpg"));
  }

  public void paintComponent(Graphics g) {
   super.paintComponent(g);
   if (flag==1){ //If the title screen
     g.drawImage(titleScreen,0,0,this);
   } else if (flag==2){ //Instructions creen
     g.drawImage(instructionScreen,0,0,this);
   } else if (flag==3){ //Exit button
     System.exit(0);
   } else if (flag==4){ //Play button
     play=true;
   }
   repaint();
  }
 }

}

