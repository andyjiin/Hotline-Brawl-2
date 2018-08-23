import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Floor extends GameObject{
 private int width = 32;
 private int height =32;
 Handler handler;
 
 private int blocktype;//type of wall
 
 Texture tex = Game.getInstance();
 
 
 public Floor(float x, float y, int blocktype,Handler handler, String type) {
  super(x, y, type);
  this.blocktype = blocktype;
  this.handler=handler;
 }
 public void tick(LinkedList<GameObject> object) {
  x+=velX;
  y+=velY;
  
 }
 
 public void render(Graphics g) {
  if(blocktype == 1){
   g.drawImage(tex.block[1], (int)x, (int)y, null);
  }
  if(blocktype == 2){
   g.drawImage(tex.block[1], (int)x, (int)y, null);
  }
  if(blocktype == 3){
   g.drawImage(tex.block[1], (int)x, (int)y, null);
  }
 }

 public Rectangle getBounds() {
  return new Rectangle((int)x,(int)y,width,height);
 }
}
