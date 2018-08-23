/*
 * This is the camera that shoots the view of player in the map. Moves the camera around in accordance to player movement. Always centered at pikachu
 * @author Andy Jin and Hubert Yoo
 * @date 2017-01-23
 */
public class Camera{
 private float x, y;//coordinates of the camera
 
 public Camera (float x, float y){
  this.setX(x);
  this.setY(y);
 }

 public void tick(GameObject player){
  x = -player.getX() + 380;//changes the coordinates of camera's x with center at pikachu
  y = -player.getY() + 250;//changes the coordinates of camera's y with center at pikachu
 }
 
 public float getX() {
  return x;
 }

 public void setX(float x) {
  this.x = x;
 }

 public float getY() {
  return y;
 }

 public void setY(float y) {
  this.y = y;
 }
 
}
