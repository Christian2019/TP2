
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

public class Proportions {

	public static int X_Total = Game.WIDTH * Game.SCALE;
	public static int Y_Total = Game.HEIGHT * Game.SCALE;

	public static int percentages(int val, double porc) {
		X_Total = Game.WIDTH * Game.SCALE;
		Y_Total = Game.HEIGHT * Game.SCALE;

		double d = porc / 100;

		double d2 = d * val;

		int magic = (int) d2;

		return magic;
	}


	

}
