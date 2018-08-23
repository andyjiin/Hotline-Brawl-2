/*
 * Texture class holds BufferedImage arrays of all textures in the game. This includes walls, main character, guns, ammo, health and enemies
 * @param SpriteSheet object that holds the sprite_sheets in png format
 * @return BufferedImage array that split the SpriteSheets into an array from one big png into many smaller ones
 */
import java.awt.image.BufferedImage;

public class Texture{

 
//SpriteSheet objects 
 SpriteSheet bs, ps, eb, hs, ts, bl,gs, ps2;
 
 //BufferedImage of SpriteSheets
 private BufferedImage block_sheet = null;
 private BufferedImage player_machinegun_sheet = null;//gun1
 private BufferedImage player_submachinegun_sheet = null;//gun2
 
 private BufferedImage enemy_bot_sheet = null;
 private BufferedImage heart_sheet = null;//hearth counter
 private BufferedImage tombstone_sheet = null;//tombstone at death
 private BufferedImage bullet_sheet = null;//bullet counter
 private BufferedImage gun_sheet = null;//gun switchup
 
 
 //BufferedImage Arrays
 public BufferedImage[] block = new BufferedImage[2];//texture for the walls and border
 
 public BufferedImage[] player = new BufferedImage[8];//textures for all possible movements of pikachu with machinegun in hand
 public BufferedImage[] player_sub = new BufferedImage[8];//textures for all possible movements of pikachu with sub machinegun in had
 
 public BufferedImage[] enemy_bot = new BufferedImage[1];//Texture for NPC bot 
 
 public BufferedImage[] heart = new BufferedImage[2];//Half and full hearts for heart counter
 
 public BufferedImage[] tombstone = new BufferedImage[1];//Tombstone for death
 
 public BufferedImage[] bullet = new BufferedImage[1]; //Bullet for ammo counter
 
 public BufferedImage[] gun = new BufferedImage[2];//The gun images for gun counter
 
 
 public Texture(){ 
  BufferedImageLoader loader = new BufferedImageLoader();//object from BufferedImageLoader class
  
  try{//tries to pull the images from the folder for each sheet
   block_sheet = loader.loadImage("/block_sheet.png");
   enemy_bot_sheet = loader.loadImage("/enemy_bot_front.png");
   heart_sheet = loader.loadImage("/heart_sheet.png");
   tombstone_sheet = loader.loadImage("/tombstone.png");
   bullet_sheet = loader.loadImage("/bullet.png");
   gun_sheet = loader.loadImage("/gun_sheet.png");
   player_machinegun_sheet = loader.loadImage("/sprite_sheet.png");
   player_submachinegun_sheet = loader.loadImage("/sprite_sheet_submachine.png");
  }catch(Exception e){
   e.printStackTrace();
  }
  
  
  bs = new SpriteSheet(block_sheet);//creates SpriteSheet object from the BufferedImage for block_sheet(wall)
  eb = new SpriteSheet(enemy_bot_sheet);
  hs = new SpriteSheet(heart_sheet);
  ts = new SpriteSheet(tombstone_sheet);
  bl = new SpriteSheet(bullet_sheet);
  gs = new SpriteSheet(gun_sheet);
  ps = new SpriteSheet(player_machinegun_sheet);
  ps2 = new SpriteSheet(player_submachinegun_sheet);
  getTextures();
  
 }

 /*
  * Method converts SpriteSheet into BufferedImage array
  * @param SpriteSheet object which has png inside of all sprites for specific array
  * @return BufferedImage array with all sprites for specific character/concept needed
  */
 private void getTextures(){
  block[0] = bs.grabImage(1, 1, 64, 64);//brick wall
  block[1] = bs.grabImage(2 , 1, 64, 64);//pink floor
  
  player[0] = ps.grabImage(1, 1, 75, 64);//down pikachu left foot
  player[1] = ps.grabImage(2, 1, 75, 64);//down pikachu right foot
  player[2] = ps.grabImage(3, 1, 75, 64);//up
  player[3] = ps.grabImage(4, 1, 75, 64);//up
  player[4] = ps.grabImage(5, 1, 75, 64);//left
  player[5] = ps.grabImage(6, 1, 75, 64);//left
  player[6] = ps.grabImage(7, 1, 75, 64);//right 
  player[7] = ps.grabImage(8, 1, 75, 64);//right
  
  player_sub[0] = ps2.grabImage(1, 1, 75, 64);//down 
  player_sub[1] = ps2.grabImage(2, 1, 75, 64);//down
  player_sub[2] = ps2.grabImage(3, 1, 75, 64);//up
  player_sub[3] = ps2.grabImage(4, 1, 75, 64);//up
  player_sub[4] = ps2.grabImage(5, 1, 75, 64);//left
  player_sub[5] = ps2.grabImage(6, 1, 75, 64);//left
  player_sub[6] = ps2.grabImage(7, 1, 75, 64);//right
  player_sub[7] = ps2.grabImage(8, 1, 75, 64);//right
  
  enemy_bot[0] = eb.grabImage(1, 1, 28, 64);//enemy bot front
  
  heart[0] = hs.grabImage(1, 1, 16, 16);//heart full
  heart[1] = hs.grabImage(2, 1, 16, 16);//heart half
  
  tombstone[0] = ts.grabImage(1, 1, 48, 58);//tombstone
  
  bullet[0] = bl.grabImage(1, 1, 6, 21);//bullet
  
  gun[0] = gs.grabImage(1, 1, 94, 22);//submachinegun
  gun[1] = gs.grabImage(2, 1, 94, 22);//machinegun
 }
}
