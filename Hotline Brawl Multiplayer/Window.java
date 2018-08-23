/**
 * This class contains the main window in which the game runs 
 * @author Andy Jin and Hubert Yoo
 * @date 2017-01-23
 */
import java.awt.Dimension;
import javax.swing.JFrame;

public class Window {
 Window (int w, int h, String title,Game game){
   //Get the dimensions 
  game.setPreferredSize(new Dimension(w,h));
  game.setMaximumSize(new Dimension(w,h));
  game.setMinimumSize(new Dimension(w,h));
  
  JFrame frame = new JFrame(title);
  //Add game to the frame
  frame.add(game);
  frame.pack();
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frame.setResizable(false);
  frame.setLocationRelativeTo(null);
  frame.setVisible(true);
  game.start(); //Start the game 
  
  
 }
 
 
}

