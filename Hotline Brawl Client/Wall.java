/*
 * GameObject called Wall which interacts with player. Player cannot walk into wall so that player is contained within an area. Bullets that hit wall are deleted so that they do not travel forever
 * @param starting coordinates
 * @return GameObject called Wall
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Wall extends GameObject{//extends GameObject
 private int width = 64;//The dimensions of single wall
 private int height =64;
 Handler handler;
 
 private int blocktype;//type of wall
 
 Texture tex = Game.getInstance();
 
 
 public Wall(float x, float y, int blocktype,Handler handler, String type) {//extends game object and also has a block type and handler
  super(x, y, type);
  this.blocktype = blocktype;
  this.handler=handler;
 }

 
 public void tick(LinkedList<GameObject> object) {//whenever ticked, adds distance travelled horizontally into x and y
  x+=velX;
  y+=velY;
  
 }
 
 public void render(Graphics g) {//draws brick wall at coordinates called
  if(blocktype == 0){
   g.drawImage(tex.block[0], (int)x, (int)y, null);
  }
  
 }

 public Rectangle getBounds() {//makes new rectangle box used to check collisions
  return new Rectangle((int)x,(int)y,width,height);
 }

}
