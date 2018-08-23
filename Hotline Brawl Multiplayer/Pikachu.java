/*
 * Class of player's character. Named pikachu because main character is pikachu. He moves around shooting guns and collides with walls. He has a set speed and can move in
 *  horizontal and vertical directions. He has a health and will die if hit with enough bullets. Extends GameObject and so has a type and coordinates
 * @param His coordinates on the map and must be defined as "Player" type because he is exteded from GameObject. Also has ammo class which keeps track of his ammo
 * @return GameObject which interacts with walls and other players and is controlled by the player
 */

//imports
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Pikachu extends GameObject{//extends game object like bullets and walls
 private int width = 64;//his hitbox's width
 private int height = 64;//his hitbox's height
 Handler handler;
 private boolean firing = false; //on or off boolean counting if he is firing or not
 private double xNew,yNew; //coordinates of where the mouse is clicked. used for calculated trejectory of bullets
 private int kills=0; //number of enemies killed
 private int health = 100;//his health. decreases when hit by other player's bullets and goes back to 100 at respawn
 private boolean alive = true; //boolean if he is alive or not. switches off if health drops bellow zero
 private int gunType=1;//tells what gun he has. it starts off at submachine gun which is type 1
 private boolean win = false;//win condition boolean
 private int ammo;//int for number of ammo held

 //determines what direction he is facing 
 private int facingX = 1;
 //1 = right
 //-1 = left
 private int facingY = 1;
 //1 = up
 //-1 = down

 //makes texture object
 Texture tex = Game.getInstance();


 //Animations for submachine gun and machinegun when pikachu is walking
 private Animation playerWalkRight, playerWalkLeft, playerWalkUp, playerWalkDown;
 private Animation subWalkRight, subWalkLeft, subWalkUp, subWalkDown;
 //private Animation musketWalkRight, musketWalkLeft, musketWalkUp, musketWalkDown; // this was left out, but was possible third weapon type



 public Pikachu(float x, float y,Handler handler, String type,int ammo) {//object pikachu
  super(x, y, type);//from GameObject
  this.handler=handler;
  this.ammo=ammo;//his ammo count

  subWalkRight = new Animation(8, tex.player_sub[6], tex.player_sub[7]);//switches between different images in array tex.player_sub for moving in the right direction
  subWalkLeft = new Animation(8, tex.player_sub[4], tex.player_sub[5]);//switches between different images in array tex.player_sub for moving in the left direction
  subWalkUp = new Animation(8, tex.player_sub[2], tex.player_sub[3]);//switches between different images in array tex.player_sub for moving in the up direction
  subWalkDown = new Animation(8, tex.player_sub[0], tex.player_sub[1]);//switches between different images in array tex.player_sub for moving in the down direction

  playerWalkRight = new Animation(8, tex.player[6], tex.player[7]);//switches between different images in array tex.player for moving in the right direction
  playerWalkLeft = new Animation(8, tex.player[4], tex.player[5]);//switches between different images in array tex.player for moving in the left direction
  playerWalkUp = new Animation(8, tex.player[2], tex.player[3]);//switches between different images in array tex.player for moving in the up direction
  playerWalkDown = new Animation(8, tex.player[0], tex.player[1]);//switches between different images in array tex.player for moving in the down direction

  //musketWalkRight = new Animation(8, tex.player_musket[6], tex.player_musket[7]); 
  //musketWalkLeft = new Animation(8, tex.player_musket[4], tex.player_musket[5]); 
  //musketWalkUp = new Animation(8, tex.player_musket[2], tex.player_musket[2]); 
  //musketWalkDown = new Animation(8, tex.player_musket[0], tex.player_musket[1]); 
 }

 public void tick(LinkedList<GameObject> object) {//for every tick in the game, the pikachu class checks these things
  if (health<1){//if he is alive or not. if he is dead then he cannot move
   velX=0;
   velY=0;

  }

  x+=velX;//updates his postion with his velocity
  y+=velY;

  if(velX < 0){//if his x velocity is negative then we know he is going left
   facingX = 1;
  }
  else{
   facingX = -1;//if his x velocity is positive then we know he is going right
  }

  if(velY < 0){//if his y velocity is negative then we know he is going down
   facingY = 1;
  }
  else{//if his y velocity is positive then we know he is going up
   facingY = -1;
  }


  //depending on what gun he has we run that gun's animations
  if (gunType == 1){
   subWalkRight.runAnimation();
   subWalkLeft.runAnimation();
   subWalkUp.runAnimation();
   subWalkDown.runAnimation();
   //System.out.println("sub");
  }
  else if(gunType == 2){
   playerWalkRight.runAnimation();
   playerWalkLeft.runAnimation();
   playerWalkUp.runAnimation();
   playerWalkDown.runAnimation();
   //System.out.println("MACHINE");
  } else {//if his gun isnt an smg or machine gun then we know he has won
   setWin(true);
  }

  collision(object);//checks if he is colliding with anything

  setGunType(kills+1);//if he kills someone then he moves to the next gun

  if (kills==2){//checks whether player has won or not
   setWin(true);
  }

 }

 private void collision(LinkedList <GameObject> object){//player collision check
  for (int i=0;i<handler.object.size();i++){//finds the player inside the linkedlist containing all game objects
   GameObject tempObject = handler.object.get(i);
   if (tempObject.getType().equals("Wall")){//if the object in the list is a wall
    if (getBounds().intersects(tempObject.getBounds())){//and the player is colliding with that wall
     velY=0;//then he cannot move in that direction any furthur
     y = tempObject.getY() -45;//player is then teleported back a bit to compensate for sticking into the wall on collision
    } else if (getBoundsTop().intersects(tempObject.getBounds())){
     velY=0;
     y = tempObject.getY() + 50;
    } else if (getBoundsRight().intersects(tempObject.getBounds())){
     x = tempObject.getX() - 50;
     velX=0;
    } else if (getBoundsLeft().intersects(tempObject.getBounds())){
     x = tempObject.getX() + 41;
     velX=0;
    }
   } else if (tempObject.getType().equals("Bullet")){//if the object in the list is a bullet
    if (getBounds().intersects(tempObject.getBounds())){//and the player is hit be said bullet
     if (((Bullet)tempObject).isHitPlayer() && ((Bullet)tempObject).getId() instanceof Enemy && alive==true){//and the bullet came from an enemy
      health = health - ((Bullet)tempObject).getDamage();//decrease player's health
      ((Bullet)tempObject).setRemove(true);//remove the bullet so it doesnt pass through
      System.out.println(health);
      if (health <=0){//if the bullet dropped health bellow zero, then make player dead and give the enemy a kill
       setAlive(false);
       ((Enemy)((Bullet)tempObject).getId()).setKills(((Enemy)((Bullet)tempObject).getId()).getKills()+1);
      }
     }
    }
   }
  }
 }



 /*
  * renders all pikachu related images and is called from game.java whenever render is used
  * @param
  * @return
  */
 public void render(Graphics g) {//projects main character onto screen
  if(alive){//if pikachu is alive then he will move/shoot and will need said images
   if(velX != 0){//if he is moving in x plae(left or right)
    if(facingX == 1){//and is facing left
     if(gunType == 1){//and has submachine gun
      subWalkLeft.drawAnimation(g, (int)x, (int)y, 75,64);//draw animation of pikachu looking left holding submachine gun
     }
     else if(gunType == 2){//if machine gun
      playerWalkLeft.drawAnimation(g, (int)x, (int)y, 75,64);//draw animation of pikachu looking left holding machine gun
     }

    }
    else{//if not facing left then he must be facing right
     if(gunType == 1){
      subWalkRight.drawAnimation(g, (int)x, (int)y, 75,64);//draw animation of pikachu looking right holding submachine gun
     }
     else if(gunType == 2){
      playerWalkRight.drawAnimation(g, (int)x, (int)y, 75,64);//draw animation of pikachu looking right holding machine gun
     }

    }
   }
   else if(velY != 0){//if he is moving in y plae(up or down)
    if(facingY == 1){//and is looking up 
     if(gunType == 1){
      subWalkUp.drawAnimation(g, (int)x, (int)y, 75,64);//draw submachinegun pikachu moving up
     }
     else if(gunType == 2){
      playerWalkUp.drawAnimation(g, (int)x, (int)y, 75,64);//moving down
     }

    }
    else{//looking down
     if(gunType == 1){
      subWalkDown.drawAnimation(g, (int)x, (int)y, 75,64);
     }
     else if(gunType == 2){
      playerWalkDown.drawAnimation(g, (int)x, (int)y, 75,64);
     }

    }
   }
   else{//if he is not moving draw stationary pikachu depending on gun type

    if(gunType == 1){//submachine gun
     g.drawImage(tex.player_sub[0],(int)x,(int)y,null);//draws non-animated pkachu holding sub machinegun looking at camera
    }
    else if(gunType == 2){//machinegun
     g.drawImage(tex.player[0],(int)x,(int)y,null);//draws non-animated pkachu holding machinegun looking at camera
    }

   }
  } else {//if he is not alive, then draw tombstone showing death
   g.drawImage(tex.tombstone[0],(int)x,(int)y,null);
  }


  for(int i = 0;i < health/10;i++){//draw hearts depending on how much health he has. Uses coordinates of camera, which changes with player so that hearts move with player as well
   g.drawImage(tex.heart[0],(int)this.getX() - 350 + (i*17),(int)this.getY() + 200,null);

  }
  for(int j = 0;j < ammo;j++){//draw ammo depending on how much ammo he has. Uses coordinates of camera, which changes with player so that hearts move with player as well
   g.drawImage(tex.bullet[0],(int)this.getX() - 350 + (j*6),(int)this.getY() + 225,null);

  }

  if(gunType == 1){//draws submachine gun under ammo if he has sub machinegun
   g.drawImage(tex.gun[0], (int)this.getX() - 350,(int)this.getY() + 260,null);
  }
  else if(gunType == 2){//draws machine gun under ammo if he has sub machinegun
   g.drawImage(tex.gun[1], (int)this.getX() - 350,(int)this.getY() + 260,null);
  }


 }

 //Getters and Setters for all variables

 public Rectangle getBounds() {
  return new Rectangle((int)x+(width/2)-((width/2)/2)+12,(int)y+(height/2),width/2-15,height/2-17);
 }

 public Rectangle getBoundsTop() {
  return new Rectangle((int)x+(width/2)-((width/2)/2)+12,(int)y+15,width/2-15,height/2-17);
 }

 public Rectangle getBoundsRight() {
  return new Rectangle((int)x+width-17,(int)y+17,3,height-36);
 }

 public Rectangle getBoundsLeft() {
  return new Rectangle((int)x+23,(int)y+17,3,height-36);
 }

 public boolean isFiring() {
  return firing;
 }

 public void setFiring(boolean firing) {
  this.firing = firing;
 }

 public double getxNew() {
  return xNew;
 }

 public void setxNew(double xNew) {
  this.xNew = xNew;
 }

 public double getyNew() {
  return yNew;
 }

 public void setyNew(double yNew) {
  this.yNew = yNew;
 }

 public int getKills() {
  return kills;
 }

 public void setKills(int kills) {
  this.kills = kills;
 }

 public int getHealth() {
  return health;
 }

 public void setHealth(int health) {
  this.health = health;
 }

 public boolean isAlive() {
  return alive;
 }

 public void setAlive(boolean alive) {
  this.alive = alive;
 }

 public int getGunType() {
  return gunType;
 }

 public void setGunType(int gunType) {
  this.gunType = gunType;
 }

 public boolean isWin() {
  return win;
 }

 public void setWin(boolean win) {
  this.win = win;
 }

 public int getFacingX(){
  return facingX;
 }

 public void setFacingX(int facingX){
  this.facingX=facingX;
 }

 public int getFacingY(){
  return facingY;
 }

 public void setFacingY(int facingY){
  this.facingY=facingY;
 }
 public int getAmmo(){
  return this.ammo;
 }

 public void setAmmo(int ammo){
  this.ammo=ammo;
 }
}

