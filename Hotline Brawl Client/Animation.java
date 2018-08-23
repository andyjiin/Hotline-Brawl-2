
/*
 * Creates the animation for the player's feet moving back and forwards when in motion. Switches feet from left to right like stop motion film
 * @author Andy Jin and Hubert Yoo
 * @date 2017-01-23
 */
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Animation {

 
 private int speed;//speed of which frames switch up
 private int frames;//determines what images are in frames
 private int index = 0;
 private int count = 0;//counts which frame is next
 
 private BufferedImage[] images;
 private BufferedImage currentImg;
 
 public Animation(int speed, BufferedImage... args){
  this.speed = speed;
  images = new BufferedImage[args.length];
  for(int i = 0;i < args.length;i++){
   images[i] = args[i];//setting first image to image in argument
  }
  frames = args.length;
 }
 
 
 public void runAnimation(){//this switches between frames like stop motion film
  index++;
  if(index > speed){
   index = 0;
   nextFrame();
  }
  
 }
 
 private void nextFrame(){//goes to next frame
  for(int i = 0;i < frames;i++){
   if(count == i){
    currentImg = images[i];//sees what the current is and adds one
   }
  }
  
  count++;
  
  if(count > frames){//makes it so when at last frame it goes to last frame
   count = 0;
  }
 }
 
 public void drawAnimation(Graphics g,int x, int y){//places animation at the coordinates given
  g.drawImage(currentImg, x, y, null);
 }
 
 
 public void drawAnimation(Graphics g,int x, int y, int scaleX, int scaleY){//scaled version of drawAnimation for pictures that are smaller or larger
  g.drawImage(currentImg, x, y, scaleX, scaleY, null);
 }
}
