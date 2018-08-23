/**
 * This class contains information on all projectiles within the game 
 * @author Andy Jin and Hubert Yoo
 * @date 2017-01-23
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Bullet extends GameObject{
 Handler handler; 
 double xNew, yNew; //Contains information of where the bullet is going to travel
 float xOrignal,yOrignal;
 double angle;
 int speed;
 //Size
 int width = 5;
 int height = 5; 
 private boolean remove = false; 
 private int gunType; //The type of bullet being fired
 private boolean hitPlayer = false;
 private int damage; 
 private GameObject id; //Where the bullet orignated from 
 private int range;
 
 /**
  * This method is the constructor to the Bullet class
  * @param coordinates of origin, the new coordinates, the speed, damage and range of bullet as well as the Handler 
  * @return void
  */
 public Bullet(float x, float y, double xNew, double yNew, int speed, int damage, int range, Handler handler, String type, int gunType,GameObject id) {
  super(x, y, type);
  this.xOrignal=x;
  this.yOrignal=y;
  this.yNew=yNew;
  this.xNew=xNew;
  this.handler=handler;
  this.speed=speed;
  this.gunType=gunType;
  this.setId(id);
  this.setDamage(damage);
  this.setRange(range);
  //Calculate the angle to which the bullet travels 
  angle = Math.atan2(yNew-300, xNew-400); 
  //Multiply the velocities by speed 
  velX = (float) (Math.cos(angle)*speed);
  velY = (float) (Math.sin(angle)*speed);
 }
 
  /**
  * This method contains all information relating to collisions with bullets
  * @param a linked list of all game objects
  * @return void
  */
 private void collision (LinkedList <GameObject> object){
   for (int i=0;i<handler.object.size();i++){
     GameObject tempObject = handler.object.get(i); //Set a temporary variable 
     if (tempObject.getType().equals("Wall")){ // If colliding with walls
       if (getBounds().intersects(tempObject.getBounds())){
         setRemove(true); //Remove the bullet
       }
     } else if (tempObject.getType().equals("Enemy")){ //If colliding with enemy
       if (id instanceof Pikachu){ //If bullet comes from pikachu
         if (getBounds().intersects(tempObject.getBounds())){
           setHitPlayer(true);
         }
       }
     }else if (tempObject.getType().equals("Player")){ //If colliding with player 
       if (id instanceof Enemy){ //If bullet comes from enemy
         if (getBounds().intersects(tempObject.getBounds())){
           setHitPlayer(true);
         }
       }
     }
   }
 }
 
  /**
  * This method constantly refreshes and updates important information within the bullet class 
  * @param linked list of game objects
  * @return void
  */
 public void tick(LinkedList<GameObject> object) {
   //Add the velocities to make it look like movement 
   x+=velX;
   y+=velY;
   //Find the distance travelled to indicate range 
   double distance = Math.sqrt(Math.pow((x-xOrignal), 2) + Math.pow((y-yOrignal), 2));
   if (distance>range){
     setRemove(true);
   }
  collision(object); //Collisions
 }
 
  /**
  * This method contains all graphical components of the Bullet class
  * @param graphics g
  * @return void
  */
 public void render(Graphics g) {
  Graphics2D g2d = (Graphics2D)g;
  g.setColor(Color.black);
  //bullet specifications for each bullet
  if (gunType==1){
  g.fillOval((int)x, (int)y, width, height);
  } else if (gunType==2){
   g.fillOval((int)x, (int)y, width, height);
  } else if (gunType==3){
    g.fillOval((int)x, (int)y, width, height);
  }
 }

  /**
  * This method gets the bounds of the bullet for collision 
  * @param void 
  * @return rectangle 
  */
 public Rectangle getBounds() {
  return new Rectangle ((int)x,(int)y,width,height);
 }
 
  /**
  * This method checks if remove is true
  * @param void 
  * @return boolean 
  */
 public boolean isRemove() {
  return remove;
 }
 
  /**
  * This method sets remove 
  * @param boolean 
  * @return void
  */
 public void setRemove(boolean remove) {
  this.remove = remove;
 }

  /**
  * This method checks if player is hit  
  * @param void
  * @return boolean
  */
 public boolean isHitPlayer() {
  return hitPlayer;
 }

  /**
  * This method sets the playerHit 
  * @param boolean 
  * @return void
  */
 public void setHitPlayer(boolean hitPlayer) {
  this.hitPlayer = hitPlayer;
 }

  /**
  * This method gets the damage of bullet  
  * @param void
  * @return int  
  */
 public int getDamage() {
  return damage;
 }

  /**
  * This method sets the damage of a bullet 
  * @param int 
  * @return void
  */
 public void setDamage(int damage) {
  this.damage = damage;
 }

  /**
  * This method gets the objectId
  * @param void 
  * @return GameObject
  */
 public GameObject getId() {
  return id;
 }

  /**
  * This method sets the object id 
  * @param GameObject
  * @return void
  */
 public void setId(GameObject id) {
  this.id = id;
 }
 
  /**
  * This method gets the new x position
  * @param void 
  * @return double
  */
 public double getXNew(){
   return xNew;
 }
 
  /**
  * This method sets the new x position 
  * @param double 
  * @return void
  */
 public void setXNew(double xNew){
   this.xNew=xNew;
 }
 
  /**
  * This method gets the new y position 
  * @param void 
  * @return double
  */
 public double getYNew(){
   return yNew;
 }
 
  /**
  * This method sets the new Y position 
  * @param double 
  * @return void 
  */
 public void setYNew(double yNew){
   this.yNew=yNew;
 }
 
  /**
  * This method gets the gunType
  * @param void
  * @return int 
  */
public int getGunType(){
   return gunType;
 }
 
 /**
  * This method sets the gunType
  * @param int 
  * @return void
  */
 public void setGunType(int type){
   gunType=type;
 }
 
  /**
  * This method gets the range of bullet
  * @param void
  * @return int
  */
 public int getRange(){
   return range;
 }
 
  /**
  * This method sets the range of bullet
  * @param int 
  * @return void
  */
 public void setRange(int range){
   this.range=range;
 }
}

