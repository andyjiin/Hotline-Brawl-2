import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game extends Canvas implements Runnable {
  private boolean running = false;
 private Thread thread;
 int submachineCount = 30;
 int submachineCountEnemy = 30;
 Long firingDelaySubmachine=(long) 100;
 Long reloadDelaySubmachine =(long) 8000;
 int machineCount = 100;
 int machineCountEnemy=30;
 Long firingDelayMachine=(long) 200;
 Long reloadDelayMachine =(long) 10000;
 int rifleCount = 6;
 int rifleCountEnemy = 6;
 Long firingDelayRifle=(long) 1000;
 Long reloadDelayRifle =(long) 15000;
 int musketCount = 1;
 int musketCountEnemy = 1;
 Long firingDelayMusket=(long) 1000;
 Long reloadDelayMusket =(long) 20000;
 int bowCount = 5;
 int bowCountEnemy = 5;
 Long firingDelayBow=(long) 2000;
 Long reloadDelayBow =(long) 20000;
 Long firingDelayRock=(long) 500;
 Long firingTimer;
 Long reloadTimer;
Long respawnDelay = (long) 10000;
 Long respawnTimer; 
 //Objects
 Handler handler;
 Camera cam;
 static Texture tex;
 Server server;
 boolean gameover=false;
 
  String win = "noone";
  
  public String getWin(){
   return win;
 }
  
 public void setWin(String win){
   this.win=win;
 }

 public static int WIDTH, HEIGHT;

 private BufferedImage level = null, floor = null;
 
 private int ammoCount = submachineCount;

 public synchronized void start(){
  if (running){
   return;
  }

  running = true;
  thread = new Thread (this);
  thread.start();

 }

 private void init(){
   WIDTH = getWidth();
   HEIGHT = getHeight();
   
   tex = new Texture();
   
   
   BufferedImageLoader loader = new BufferedImageLoader();
   level = loader.loadImage("/level.png");
   floor = loader.loadImage("/bkg_floor_orange.png");
   handler=new Handler();
   cam = new Camera(0,0);
   
   LoadImageLevel(level);

  this.addKeyListener(new KeyInput(handler));
  this.addMouseListener(new MouseInput(handler));
  firingTimer=System.nanoTime();
  reloadTimer=System.nanoTime();
  respawnTimer=System.nanoTime();
  server = new Server(handler);
  server.go();

 }

 public void run(){
  init();
  this.requestFocus();
  long lastTime = System.nanoTime();
  double amountOfTicks = 60.0;
  double ns = 1000000000 / amountOfTicks;
  double delta = 0;
  long timer = System.currentTimeMillis();
  int updates = 0;
  int frames = 0;
  while(running){
   long now = System.nanoTime();
   delta += (now - lastTime) / ns;
   lastTime = now;
   while(delta >= 1){
    tick();
    updates++;
    delta--;
   }
   render();
   frames++;

   if(System.currentTimeMillis() - timer > 1000){
    timer += 1000;
    System.out.println("FPS: " + frames + " TICKS: " + updates);
    frames = 0;
    updates = 0;
   }
  }
 }

   private void tick(){
  handler.tick();
  if (gameover==false){
    if (win.equals("Player")){ //Check win at the beginnning of every loop
      System.out.println("Player wins");
      gameover=true;
      try{
        new End(1920,1080,"Welcome","Player");
      } catch (Exception e){
      }
    } else if (win.equals("Enemy")){
      gameover=true;
      System.out.println("Enemy wins");
      try{
        new End(1920,1080,"Welcome","Enemy");
      } catch (Exception e){
      }
    }
  }
  //Run through the handler 
  for (int i = 0; i<handler.object.size();i++){
   GameObject tempObject = handler.object.get(i);
   if (tempObject.getType().equals("Player")){ //If the GameObject is player
     if (((Pikachu) tempObject).isWin()){
       win="Player"; //Set win as player
     }
    if ((boolean) ((Pikachu) tempObject).isFiring()){ //If the player is firing 
     long elapsed = (System.nanoTime()-firingTimer)/1000000; //Get elapsed time 
     if (((Pikachu)tempObject).getGunType()==1){ //Gun type one 
      if (elapsed > firingDelaySubmachine && submachineCount >0){ //If the time elapsed is greater than the delay and there is ammo
        //Add the bullet to the handler 
       handler.addObject(new Bullet (tempObject.getX()+33,tempObject.getY()+33,((Pikachu) tempObject).getxNew(),((Pikachu) tempObject).getyNew(),20,10,400,handler,"Bullet",1,tempObject));
       firingTimer = System.nanoTime(); //Reset timer 
       submachineCount--; //Substract from ammo
       ammoCount =  submachineCount;
      }
      if (submachineCount <= 0){ //If out of ammo
       long elapsed2 = (System.nanoTime()-reloadTimer)/1000000;
       ((Pikachu) tempObject).setFiring(false); //Set firing as false so that no bullets are fired
       if (elapsed2>reloadDelaySubmachine){ //Reload delay
        System.out.println("reloading...");
        reloadTimer = System.nanoTime();
        submachineCount=30; //Refill ammo
       }
      }
     } else if (((Pikachu)tempObject).getGunType()==2){ //Similar steps as above except for machine gun
      if (elapsed > firingDelayMachine && machineCount >0){
       handler.addObject(new Bullet (tempObject.getX()+33,tempObject.getY()+33,((Pikachu) tempObject).getxNew(),((Pikachu) tempObject).getyNew(),10,5,500,handler,"Bullet",2,tempObject));
       firingTimer = System.nanoTime();
       machineCount--;
       ammoCount= machineCount;
      }
      if (machineCount <= 0){
       long elapsed2 = (System.nanoTime()-reloadTimer)/1000000;
       System.out.println(machineCount);
       ((Pikachu) tempObject).setFiring(false);
       if (elapsed2>reloadDelayMachine){
        System.out.println("reloading...");
        reloadTimer = System.nanoTime();
        machineCount=100;
       }
      }
     }
    } else if(((Pikachu)tempObject).isAlive()==false){ //If pikachu  is dead, then set a respawn timer 
      long elapsed = (System.nanoTime()-respawnTimer)/1000000;
      if (elapsed > respawnDelay){ 
        System.out.println("RESPAWNING...");
        ((Pikachu)tempObject).setHealth(100); //Set health back at 100 and then set alive as true
        ((Pikachu)tempObject).setAlive(true);
        respawnTimer = System.nanoTime(); //reset spawn timer 
      }
    }
    ((Pikachu)tempObject).setAmmo(ammoCount);  
    cam.tick(tempObject); //Update camera
   } else if (tempObject.getType().equals("Bullet")){
     if (((Bullet)tempObject).isRemove()){ //If the bullet singals to be removes, then remove from the handler 
     handler.removeObject(tempObject);
    }
   } else if (tempObject.getType().equals("Enemy")){
     if (((Enemy) tempObject).isWin()){ //Check for win
       win="Enemy";
     }
    if (((Enemy)tempObject).isAlive()==false){ //If the enemy is not alive 
      long elapsed = (System.nanoTime()-respawnTimer)/1000000; //Respawn timer 
      if (elapsed > respawnDelay){
        System.out.println("RESPAWNING...");
        ((Enemy)tempObject).setHealth(100); //Set health back at 100 and alive as true
        ((Enemy)tempObject).setAlive(true);
        respawnTimer = System.nanoTime();
      }
    }
     if ((boolean) ((Enemy) tempObject).isFiring()){ //If the enemy is firing 
       long elapsed = (System.nanoTime()-firingTimer)/1000000; 
       if (((Enemy)tempObject).getGunType()==1){ //Similar steps as above for pikachu except this time it is for enemy 
         if (elapsed > firingDelaySubmachine && submachineCountEnemy >0){
           handler.addObject(new Bullet (tempObject.getX()+33,tempObject.getY()+33,((Enemy) tempObject).getxNew(),((Enemy) tempObject).getyNew(),20,10,400,handler,"Bullet",1,tempObject));
           firingTimer = System.nanoTime();
           submachineCountEnemy--;
         }
         if (submachineCount <= 0){
           long elapsed2 = (System.nanoTime()-reloadTimer)/1000000;
           System.out.println(submachineCountEnemy);
           ((Enemy) tempObject).setFiring(false);
           if (elapsed2>reloadDelaySubmachine){
             System.out.println("reloading...");
             reloadTimer = System.nanoTime();
             submachineCountEnemy=30;
           }
         }
       } else if (((Enemy)tempObject).getGunType()==2){  //Similar steps as above for pikachu except this time it is for enemy 
         if (elapsed > firingDelayMachine && machineCountEnemy >0){
           handler.addObject(new Bullet (tempObject.getX()+33,tempObject.getY()+33,((Enemy) tempObject).getxNew(),((Enemy) tempObject).getyNew(),10,5,500,handler,"Bullet",2,tempObject));
           firingTimer = System.nanoTime();
           machineCountEnemy--;
         }
         if (machineCount <= 0){
           long elapsed2 = (System.nanoTime()-reloadTimer)/1000000;
           System.out.println(machineCountEnemy);
           ((Enemy) tempObject).setFiring(false);
           if (elapsed2>reloadDelayMachine){
             System.out.println("reloading...");
             reloadTimer = System.nanoTime();
             machineCountEnemy=100;
           }
         }
       }  
     }
    }
   } 
  }
 

 
 

 private void render(){
  BufferStrategy bs = this.getBufferStrategy();
  if (bs == null){
   this.createBufferStrategy(3);
   return;
  }
  server.transmit();
  Graphics g = bs.getDrawGraphics();
  Graphics2D g2d = (Graphics2D) g;
  /////////////////////////////////////

  ///draw here//0

  g.setColor(Color.black);
  g.fillRect(0, 0, getWidth(), getHeight());  



  g2d.translate(cam.getX(), cam.getY());//begin of camera
  for(int x = 0;x < floor.getWidth() * 8;x+= floor.getWidth()){
   for(int y = 0;y < floor.getHeight() * 8;y+= floor.getHeight()){
    g.drawImage(floor, x, y, this);
   }

  }
  handler.render(g);

  g2d.translate(-cam.getX(), -cam.getY());//end of camera (all objects inside are rendered)
  ////////////////////////////
  g.dispose();
  bs.show();
 }


 private void LoadImageLevel(BufferedImage image){
  int w = image.getWidth();
  int h = image.getHeight();

  System.out.println("width, height: " + w +", " + h);

  for(int x = 0; x < h;x++){
   for(int y = 0;y < w;y++){
    int pixel = image.getRGB(x, y);
    int red = (pixel >> 16) & 0xff;//idk how tf this works
    int green = (pixel >> 8) & 0xff;
    int blue = (pixel) & 0xff;
    
    
    if(red == 255 && green == 255 && blue == 255){//if white pixel make wall
     handler.addObject(new Wall(x*64,y*64,0,handler,"Wall"));
    }
    if(red == 0 && green == 0 && blue == 255){//if blue pixel make players
     handler.addObject(new Pikachu(x*64,y*64,handler,"Player",ammoCount));
    }
    if(red == 0 && green == 128 && blue == 0){//if green pixel make bot/enemy
     handler.addObject(new Enemy(x*64,y*64,handler,"Enemy"));
    }
   }
  }
 }

 public static Texture getInstance(){
  return tex;
 }
 
 
 public static void main (String [] args) throws IOException{
   Title screen = new Title(1920,1080,"Welcome");
   
   while (screen.isPlay()==false){
   }
   if (screen.isPlay()){
     Game game = new Game();
     new Window (800,600,"Hotline Brawl 2",game);
     System.out.println(game.getWin());
     if (game.getWin().equals("Player")){
       System.out.println("Player wins");
     } else if (game.getWin().equals("Enemy")){
       System.out.println("Enemy wins");
     }
   }
   
 }
}


