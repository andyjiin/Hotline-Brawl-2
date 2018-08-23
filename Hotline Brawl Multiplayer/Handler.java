/**
 * This class contains the handler of the game 
 * @author Andy Jin and Hubert Yoo
 * @date 2017-01-23
 */
import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
 private GameObject tempObject;
 public LinkedList<GameObject> object = new LinkedList<GameObject>(); //Create linked list of all the game objects
 
 /**
  * this method updates the linked list 
  * @param void
  * return void
  */
 public void tick(){
  for (int i =0;i<object.size();i++){
   tempObject=object.get(i);
   tempObject.tick(object); 
  }
 }
 
  /**
  * this method updates graphics
  * @param Graphics g
  * return void
  */
 public void render(Graphics g){
  for (int i =0;i<object.size();i++){
   tempObject=object.get(i);
   tempObject.render(g); 
  }
 }
 
  /**
  * this method adds object to gameObject list 
  * @param GameObject
  * return void
  */
 public void addObject (GameObject object){
  this.object.add(object);
 }
 
  /**
  * this method removes item from linked list 
  * @param GameObject
  * return void
  */
 public void removeObject (GameObject object){
  this.object.remove(object);
 }
 
}
