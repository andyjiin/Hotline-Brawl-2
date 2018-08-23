
 /** Class helps pull image from directory and put into BufferedImage variable
 * @author Andy Jin and Hubert Yoo
 * @date 2017-01-23
 */
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {

 
 private BufferedImage image;//new BufferedImage
 
 public BufferedImage loadImage(String path){
  try {//put inside try/catch in case of failed directory
   image = ImageIO.read(getClass().getResource(path));//finds the image from path
  } catch (IOException e) {
   e.printStackTrace();
  }
  return image;//returns finished BufferedImage
 }
}