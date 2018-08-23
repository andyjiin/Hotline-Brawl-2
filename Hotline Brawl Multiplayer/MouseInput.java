/**
 * Mouse inputs using left and right click
 * @author: Andy and Hubert
 * @Date: 1/23/2017
 * */


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MouseInput extends MouseAdapter{
 double xNew,yNew;//coordinates on map of clicked place
 Handler handler;

 public MouseInput(Handler handler){
  this.handler=handler;
 }

 
 /**
  *If left or right mouse is clicked, then create bullet object and play sound
  * @param: Input of Click on mouse
  * @return: Game object bullet and playing of gun sound
  **/
 public void mousePressed(MouseEvent event) {
  for (int i =0;i<handler.object.size();i++){
   GameObject tempObject = handler.object.get(i);
   if (tempObject.getType().equals("Player")){//If temp object is player and they clicked
    xNew=event.getX();//get coordinates of place where mouse was clicked
    yNew=event.getY();//get coordinates of place where mouse was clicked
    ((Pikachu) tempObject).setFiring(true);//make fire true
    ((Pikachu) tempObject).setxNew(xNew);//set coordinates of new now
    ((Pikachu) tempObject).setyNew(yNew);
    try {//play sound of gun shot when clicked
              playSound("gun.wav");//play gun shot
          } catch (MalformedURLException ex) {
              
          } catch (LineUnavailableException ex) {
              
          } catch (UnsupportedAudioFileException ex) {
             
          } catch (IOException ex) {
              
          }
   }
  }
 }
 
 
 /**
  * Plays sound file with the name chosen
  * @param String fileName which is directory of sound file
  * @return: plays sound 
  **/
 public static void playSound(String fileName) throws MalformedURLException, LineUnavailableException, UnsupportedAudioFileException, IOException{
  File file = new File(fileName);//file object
     Clip clip = AudioSystem.getClip();

     AudioInputStream ais = AudioSystem.//get the .wav file from directory
         getAudioInputStream( file );
     clip.open(ais);//open sound
     clip.start();//play sound
 }
 
 /**
  * When mouse is released, the player's fire boolean should be false
  **/
 public void mouseReleased(MouseEvent e) {
  for (int i =0;i<handler.object.size();i++){
   GameObject tempObject = handler.object.get(i);
   if (tempObject.getType().equals("Player")){
    ((Pikachu) tempObject).setFiring(false);
   }
  }
 }
}

