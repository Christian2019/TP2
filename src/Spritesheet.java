
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {
public BufferedImage spritesheet;
public Spritesheet (String path) {
	
try {
	spritesheet= ImageIO.read(getClass().getResource(path));
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}

public BufferedImage getSprite(int x, int y, int width, int height) {
	return spritesheet.getSubimage(x, y, width, height);
}
public BufferedImage gettFullSprite() {
//	return spritesheet.getSubimage(0, 0, spritesheet.getWidth(), spritesheet.getHeight());
	return spritesheet;
}
}
