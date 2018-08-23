/**
 * Keyboard inputs using WASD and esc
 * @author: Andy and Hubert
 * @Date: 1/23/2017
 * */
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
 Handler handler;
 
 public KeyInput(Handler handler){
  this.handler=handler;
 }
 
 /**
  * W - up
  * A - left
  * S - down
  * D - right
  * @param: Uses the keyboard keys WASD and esc to do functions in game
  * @return: gives player velocity in direction pressed
  * */
 public void keyPressed(KeyEvent e){//keyboard inputs
  int code = e.getKeyCode();
  
  for (int i =0;i<handler.object.size();i++){
   GameObject tempObject = handler.object.get(i);
   if (tempObject.getType().equals("Player")){
    if (code == KeyEvent.VK_D){ //right
     tempObject.setVelX(5);
    } else if (code == KeyEvent.VK_A){ //left
     tempObject.setVelX(-5);
    } else if (code == KeyEvent.VK_W){ //up
     tempObject.setVelY(-5);
    } else if (code == KeyEvent.VK_S){ //down
     tempObject.setVelY(5);
    }
   }
  }
  if (code == KeyEvent.VK_ESCAPE){//If escape is pressed than game is closed
   System.exit(1);
  } 

 }
/*
 * When key is released then velocity is stopped so that player doesnt move infiniteley
 * No deceleration instant rate of change
 * */
 public void keyReleased(KeyEvent e){
  int code = e.getKeyCode();

  for (int i =0;i<handler.object.size();i++){
   GameObject tempObject = handler.object.get(i);
   if (tempObject.getType().equals("Player")){
    if (code == KeyEvent.VK_D){//stop right
     tempObject.setVelX(0);
    } else if (code == KeyEvent.VK_A){//stop left
     tempObject.setVelX(0);
    } else if (code == KeyEvent.VK_W){//stop up
     tempObject.setVelY(0);
    } else if (code == KeyEvent.VK_S){//stop down
     tempObject.setVelY(0);
    }
   }
  }
 }
}
