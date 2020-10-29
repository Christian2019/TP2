import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class All_Images {
	public static Spritesheet donwloadSS = new Spritesheet("/donwload.png");
	public static BufferedImage donwload=donwloadSS.gettFullSprite();
	public static Spritesheet filesSS = new Spritesheet("/files.png");
	public static BufferedImage files=filesSS.gettFullSprite();
	
	
	
}
