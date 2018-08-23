/**
 * This class contains information on all enemies within the game 
 * @author Andy Jin and Hubert Yoo
 * @date 2017-01-23
 */
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Enemy extends GameObject{

 private int width = 64;
 private int height = 64;
 Handler handler;
 private boolean firing = false; 
 private double xNew,yNew;
 private int kills=0; 
 private int health = 100;
 private boolean alive = true; 
 private int gunType=1;
 private boolean win = false;
 //The facing variables look at the direction the character should be facing 
 private int facingX = 1;
 private int facingY = 1;
 //Animation varaibles
 private Animation playerWalkRight, playerWalkLeft, playerWalkUp, playerWalkDown;
 private Animation subWalkRight, subWalkLeft, subWalkUp, subWalkDown;
 //New texture
 Texture tex = Game.getInstance();
 
  /**
  * This method is constructor to Enemy  
  * @param x/y coordinates, Handler and type of enemy
  * @return 
  */
 public Enemy(float x, float y,Handler handler, String type) {
  super(x, y, type);
  this.handler=handler;

  //Submachine gun animations
   subWalkRight = new Animation(8, tex.player_sub[6], tex.player_sub[7]);
  subWalkLeft = new Animation(8, tex.player_sub[4], tex.player_sub[5]);
  subWalkUp = new Animation(8, tex.player_sub[2], tex.player_sub[3]);
  subWalkDown = new Animation(8, tex.player_sub[0], tex.player_sub[1]);
  //Machinegun Animations 
  playerWalkRight = new Animation(8, tex.player[6], tex.player[7]);
  playerWalkLeft = new Animation(8, tex.player[4], tex.player[5]);
  playerWalkUp = new Animation(8, tex.player[2], tex.player[3]);
  playerWalkDown = new Animation(8, tex.player[0], tex.player[1]);
  
 }

 /**
  * This method updates the enemy class  
  * @param linked list of GameObjects
  * @return void
  */
 public void tick(LinkedList<GameObject> object) {
    if (health<0){ //If health is less than 0, don't allow player to move
     velX=0;
     velY=0;
   }
   //Move the enemy 
   x+=velX;
   y+=velY;
   //Direction the enemy is facing depends on their velocity direction 
   if(velX < 0){
     facingX = 1;
   }
   else{
     facingX = -1;
   }
  
  if(velY < 0){
   facingY = 1;
  }
  else{
   facingY = -1;
  }
  //If the first gun is applied
 if (gunType == 1){
    subWalkRight.runAnimation();
    subWalkLeft.runAnimation();
    subWalkUp.runAnimation();
    subWalkDown.runAnimation();
  } else if(gunType == 2){ //If second gun is chosen 
    playerWalkRight.runAnimation();
    playerWalkLeft.runAnimation();
    playerWalkUp.runAnimation();
    playerWalkDown.runAnimation();
  } else { //If another gun type, then the user has won 
    setWin(true);
  }
  
  collision(object); //Collisions 
  
  setGunType(kills+1);
  if (gunType==3){ //Player wins after 2 kills
   setWin(true);
  }
 }
 
 /**
  * This method checks collisions with enemies
  * @param linked list of GameObjects
  * @return void 
  */
 private void collision(LinkedList <GameObject> object){
   for (int i=0;i<handler.object.size();i++){
     GameObject tempObject = handler.object.get(i); //set temporary variable 
     if (tempObject.getType().equals("Bullet")){ //If the enemy collides with a bullet 
       if (getBounds().intersects(tempObject.getBounds())){ 
         if (((Bullet)tempObject).isHitPlayer() && ((Bullet)tempObject).getId() instanceof Pikachu && alive==true){
           health = health - ((Bullet)tempObject).getDamage(); //Subtract the health from the enemy
           ((Bullet)tempObject).setRemove(true); 
           if (health <=0){ //If health is less than 0, then the enemy is dead
             setAlive(false);
             ((Pikachu)((Bullet)tempObject).getId()).setKills(((Pikachu)((Bullet)tempObject).getId()).getKills()+1); //Add the kills 
           }
         }
       }
     }
   }
 }
 
  
 /**
  * This method contains all graphical related aspects of Enemy
  * @param graphics g
  * @return void
  */
  public void render(Graphics g) {
    if(alive){ //If the enemy is alive 
     if(velX != 0){ //If the enemy is moving 
       if(facingX == 1){ //Direction the enemy is facing 
         if(gunType == 1){ //Type of gun 
           subWalkLeft.drawAnimation(g, (int)x, (int)y, 75,64);
         }
         else if(gunType == 2){ 
           playerWalkLeft.drawAnimation(g, (int)x, (int)y, 75,64);
         }
       }
       else{
         if(gunType == 1){
           subWalkRight.drawAnimation(g, (int)x, (int)y, 75,64);
         }
         else if(gunType == 2){
           playerWalkRight.drawAnimation(g, (int)x, (int)y, 75,64);
         }
       }
     }
     else if(velY != 0){ // If the enemy is not moving in the Y direction 
       if(facingY == 1){ //Direction of facing 
         if(gunType == 1){
           subWalkUp.drawAnimation(g, (int)x, (int)y, 75,64);
         }
         else if(gunType == 2){
           playerWalkUp.drawAnimation(g, (int)x, (int)y, 75,64);
         }
       }
       else{
         if(gunType == 1){
           subWalkDown.drawAnimation(g, (int)x, (int)y, 75,64);
         }
         else if(gunType == 2){
           playerWalkDown.drawAnimation(g, (int)x, (int)y, 75,64);
         }
       }
     }
     else{ //Standard drawings when nothing is pressed
       if(gunType == 1){
         g.drawImage(tex.player_sub[0],(int)x,(int)y,null);
       }
       else if(gunType == 2){
         g.drawImage(tex.player[0],(int)x,(int)y,null);
       }
     }
   } else { //If player is not alive, draw in the tombstone 
      g.drawImage(tex.tombstone[0],(int)x,(int)y,null);
   }
 }

  /**
  * This method gets the lower bounds of the Enemy
  * @param void 
  * @return Rectangle
  */
 public Rectangle getBounds() {
  return new Rectangle((int)x+(width/2)-((width/2)/2)+12,(int)y+(height/2),width/2-15,height/2-17);
 }
 
 /**
  * This method gets the lower bounds of the Enemy
  * @param void 
  * @return Rectangle
  */
 public Rectangle getBoundsTop() {
  return new Rectangle((int)x+(width/2)-((width/2)/2)+12,(int)y+15,width/2-15,height/2-17);
 }
 
 /**
  * This method gets the lower bounds of the Enemy
  * @param void 
  * @return Rectangle
  */
 public Rectangle getBoundsRight() {
  return new Rectangle((int)x+width-17,(int)y+17,3,height-36);
 }
 
 /**
  * This method gets the lower bounds of the Enemy
  * @param void 
  * @return Rectangle
  */
 public Rectangle getBoundsLeft() {
  return new Rectangle((int)x+23,(int)y+17,3,height-36);
 }

 /**
  * This method is the getter to Firing 
  * @param void 
  * @return boolean 
  */
 public boolean isFiring() {
  return firing;
 }
 
 /**
  * This method is the setter to firing 
  * @param boolean
  * @return void 
  */
 public void setFiring(boolean firing) {
  this.firing = firing;
 }

 /**
  * This method is the getter to xNew
  * @param void 
  * @return double
  */
 public double getxNew() {
  return xNew;
 }

 /**
  * This method is the setter to xNew
  * @param double
  * @return void 
  */
 public void setxNew(double xNew) {
  this.xNew = xNew;
 }

 /**
  * This method is the getter to yNew
  * @param void 
  * @return double
  */
 public double getyNew() {
  return yNew;
 }

 /**
  * This method is the setter to YNew
  * @param double
  * @return void 
  */
 public void setyNew(double yNew) {
  this.yNew = yNew;
 }

 /**
  * This method is the getter to kills
  * @param void 
  * @return int
  */
 public int getKills() {
  return kills;
 }

 /**
  * This method is the setter to kills
  * @param int
  * @return void 
  */
 public void setKills(int kills) {
  this.kills = kills;
 }

 /**
  * This method is the getter to health 
  * @param void 
  * @return int
  */
 public int getHealth() {
  return health;
 }

 /**
  * This method is the setter to health
  * @param int
  * @return void 
  */
 public void setHealth(int health) {
  this.health = health;
 }

 /**
  * This method is the getter to alive
  * @param void 
  * @return boolean
  */
 public boolean isAlive() {
  return alive;
 }

 /**
  * This method is the setter to alive
  * @param boolean
  * @return void 
  */
 public void setAlive(boolean alive) {
  this.alive = alive;
 }

 /**
  * This method is the getter to gunType
  * @param void 
  * @return int
  */
 public int getGunType() {
  return gunType;
 }

 /**
  * This method is the setter to gunType
  * @param int
  * @return void 
  */
 public void setGunType(int gunType) {
  this.gunType = gunType;
 }

 /**
  * This method is the getter to win
  * @param void 
  * @return boolean
  */
 public boolean isWin() {
  return win;
 }

 /**
  * This method is the setter to win
  * @param boolean
  * @return void 
  */
 public void setWin(boolean win) {
  this.win = win;
 }
 
 /**
  * This method is the getter to facingX
  * @param void 
  * @return int
  */
 public int getFacingX(){
   return facingX;
 }
 
 /**
  * This method is the setter to facingX
  * @param int
  * @return void 
  */
 public void setFacingX(int facingX){
   this.facingX=facingX;
 }
 
 /**
  * This method is the getter to facingY
  * @param void 
  * @return int
  */
 public int getFacingY(){
   return facingY;
 }
 
 /**
  * This method is the setter to facingY
  * @param int
  * @return void 
  */
 public void setFacingY(int facingY){
   this.facingY=facingY;
 }
}


