/*
 * This class reads .png files which hold sprites in a sheet and converts them into sub sections which can be stored into an array
 * @author Andy Jin and Hubert Yoo
 * @date 2017-01-23
 */
import java.awt.image.BufferedImage;

public class SpriteSheet {

 private BufferedImage image;//holds end image
 
 public SpriteSheet(BufferedImage image){
  this.image = image;
  
 }
 
 /*
  * @param the coordinates of the sub imaged wanted with first image being 1,1 and second to right being 2,1. The width and height of said images is also needed to subtract from
  * the product in order to get the bottom left corner of subimage
  * @return BufferedImage that is only the portion wanted from whole SpriteSheet
  */
 public BufferedImage grabImage(int col,int row, int width, int height){//coordinates of the sub image that is wanted and the width and length
  BufferedImage img = image.getSubimage((col * width) - width, (row * height) - height, width, height);//calculates total distance from 0,0 the subimage is and subtracts the width and height for starting position 
  return img;
 }
}
