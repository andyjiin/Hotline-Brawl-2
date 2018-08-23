/**
 * This class contains all the gameObjects in the game. Everything in the game extends this
 * @author Andy Jin and Hubert Yoo
 * @date 2017-01-23
 */

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

public abstract class GameObject {
 
 protected float x;
 protected float y;
 protected float  velX =0;
 protected float velY =0;
 private String type;
 
 public GameObject(float x, float y, String type){
  this.x=x;
  this.y=y;
  this.type=type;
 }
 
 public abstract void tick (LinkedList<GameObject> object);
 public abstract void render (Graphics g);
 public abstract Rectangle getBounds(); 
 
 //Getters and setters for all private variables 
 
 public float getX(){
  return x;
 }
 
 public float getY(){
  return y;
 }
 
 public void setX(float x){
  this.x=x;
 }
 
 public void setY(float y){
  this.y=y;
 }
 
 public float getVelX(){
  return velX;
 }
 
 public float getVelY(){
  return velY;
 }
 
 public void setVelX(float velX){
  this.velX=velX;
 }
 
 public void setVelY(float velY){
  this.velY=velY;
 }
 
 public String getType(){
  return type;
 }
}
