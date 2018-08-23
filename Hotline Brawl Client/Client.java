/*
 * This class sets the client and readies for networking 
 * @author Andy Jin and Hubert Yoo
 * @date 2017-01-23
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
  Handler handler;
  Socket mySocket; //socket for connection
  BufferedReader input;
  PrintWriter output;
  String x,y;
  String xEnemy, yEnemy;
  String firing;
  String velX,velY;
  String velXEnemy,velYEnemy;
  String facingX,facingY;
  String facingXEnemy, facingYEnemy;
  String xBulletNew="";
  String yBulletNew="";
  String bulletType="";
  String xBulletEnemy="";
  String yBulletEnemy="";
  String xBulletNewEnemy="";
  String yBulletNewEnemy="";
  String bulletTypeEnemy="";
  boolean enemyFiring;
  
  /**
   * This method is the constructor to client and passes in the handler varaible 
   * @param: Handler 
   * @return: void 
   */
  public Client (Handler handler){
    this.handler=handler;
  }
  
  /**
   * This method transmits information the server
   * @param: void 
   * @return: void
   */
  public void transmit() {
    for (int i=0;i<handler.object.size();i++){
      GameObject tempObject = handler.object.get(i); //Create temporary variable 
      if (tempObject.getType().equals("Player")){ //If the player is detected
        //Set varaibles to the String to be passed
        x = Float.toString(((Pikachu)tempObject).getX());
        y = Float.toString(((Pikachu)tempObject).getY());
        firing=Boolean.toString(((Pikachu)tempObject).isFiring());
        facingX=Integer.toString(((Pikachu)tempObject).getFacingX());
        facingY=Integer.toString(((Pikachu)tempObject).getFacingY());
        velX=Float.toString(((Pikachu)tempObject).getVelX());
        velY=Float.toString(((Pikachu)tempObject).getVelY());
      } else if (tempObject.getType().equals("Bullet")){
        if (((Bullet)tempObject).getId() instanceof Pikachu){ //If the bullet comes from pikachu
          //Set bullet varaibles 
          xBulletNew = Double.toString ((((Bullet)tempObject).getXNew())); 
          yBulletNew = Double.toString ((((Bullet)tempObject).getYNew())); 
          bulletType=Integer.toString(((Bullet)tempObject).getGunType());
       }
     }
  }
  
    //accept a message from the server and output to console
    
    try {   //Read the information passed through the server
      xEnemy = input.readLine();
      yEnemy = input.readLine();
      enemyFiring=Boolean.parseBoolean(input.readLine());
      xBulletNewEnemy = input.readLine();
      yBulletNewEnemy = input.readLine();
      bulletTypeEnemy = input.readLine();
      facingXEnemy = input.readLine();
      facingYEnemy = input.readLine();
      velXEnemy=input.readLine();
      velYEnemy=input.readLine();
    }catch (IOException e) { 
      System.out.println("Failed to receive msg from the server");
      e.printStackTrace();
    }
    
    for (int i=0;i<handler.object.size();i++){ 
      GameObject tempObject = handler.object.get(i); //Run through the handler 
      if (tempObject.getType().equals("Enemy")){
        //If enemy set the varaibles from the server as enemy variables
        ((Enemy)tempObject).setX((Float.valueOf(xEnemy)));
        ((Enemy)tempObject).setY((Float.valueOf(yEnemy)));
        ((Enemy)tempObject).setFacingX(Integer.parseInt(facingXEnemy));
        ((Enemy)tempObject).setFacingY(Integer.parseInt(facingYEnemy));
        ((Enemy)tempObject).setVelX(Float.valueOf(velXEnemy));
        ((Enemy)tempObject).setVelY(Float.valueOf(velYEnemy));
       ((Enemy)tempObject).setFiring(enemyFiring);
       if (enemyFiring){ //If the enemy is firing, add the bullet variables
         ((Enemy)tempObject).setxNew(Double.parseDouble(xBulletNewEnemy));
         ((Enemy)tempObject).setyNew(Double.parseDouble(yBulletNewEnemy));
         ((Enemy)tempObject).setGunType(Integer.parseInt(bulletTypeEnemy));
       }
     }
   }
   
   
    // transmit a message to the server
    output.println(x);
    output.flush();
    output.println(y);
    output.flush();
    output.println(firing);
    output.flush();
    output.println(xBulletNew);
    output.flush();
    output.println(yBulletNew);
    output.flush();
    output.println(bulletType);
    output.flush();
    output.println(facingX);
    output.flush();
    output.println(facingY);
    output.flush();
    output.println(velX);
    output.flush();
    output.println(velY);
    output.flush();
    

 }
 
  /**
   * This method closes the client 
   * @param: void 
   * @return: void
   */
 public void close(){
   try {
     input.close();
     output.close();
     mySocket.close();
   }catch (Exception e) { 
     System.out.println("Failed to close socket");
   }
 }
 
 /**
   * This method starts the client 
   * @param: void
   * @return: void
   */
 public void go(){
  System.out.println("Attempting to make connection...");

  try{
   mySocket = new Socket ("127.0.0.1",5000);
   InputStreamReader stream = new InputStreamReader(mySocket.getInputStream());
   input = new BufferedReader(stream); //buffered reader
   output = new PrintWriter(mySocket.getOutputStream());

  } catch (IOException e){
   System.out.println("Connection to Server Failed");
   e.printStackTrace();
  }

  System.out.println("Connection made.");

  // transmit a message to the server
  transmit();
  
 
 }
 
}


