
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Game extends Canvas implements Runnable, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	// Janela
	public static JFrame frame;
	public static final int WIDTH = 192;
	public static final int HEIGHT = 108;

	public final static double FPS = 60;
	public static int SCALE = 1;

	// Thread1
	private static boolean isRunning;
	public static Thread thread;

	// Render
	public static BufferedImage image;

	public static Random rand;

	public static UI ui;
	static Game game;
	public static int frames;

	public static BufferStrategy bs;
	
	public static HashMap<String, ArrayList<PeerFiles>> peerFilesHashMap = new HashMap<>();

//state 0= Server state 1= Client
	public static int state =  0;

	public Game() {
		Game.rand = new Random();
		addMouseListener(this);
		addMouseMotionListener(this);
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();

		ui = new UI();
		image = new BufferedImage(WIDTH * SCALE, HEIGHT * SCALE, BufferedImage.TYPE_INT_RGB);

	}

	public void initFrame() {
		frame = new JFrame("TP2");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void autoScale() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		int x = WIDTH * SCALE;
		int y = HEIGHT * SCALE;
		while (x < width && y < height) {
			SCALE++;
			x = WIDTH * SCALE;
			y = HEIGHT * SCALE;
		}
		SCALE -= 3;
	}

	public static void main(String[] args) throws InterruptedException, MalformedURLException {

		autoScale();

		Sound.Music.loop();
		// Exemplo
		ConectedClient.init();

		game = new Game();
		game.start();
	}

	public synchronized void start() throws InterruptedException {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	public static synchronized void stop() {

		isRunning = false;
		try {
			thread.join();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	int max_tick_frames=60*2;
	int tick_frames=max_tick_frames;
	
	
	public void tick() {
		
		if (tick_frames==this.max_tick_frames) {
		tick_frames=0;
		ConectedClient.update();
		ui.tick();
		return;
		}
		tick_frames++;
	}

	public void render() {

		Graphics g;

		bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		/*
		 * g = image.createGraphics(); g.dispose();
		 */
		g = Game.bs.getDrawGraphics();

		g.setColor(Color.black);
		g.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);

		Game.ui.render(g);

		bs.show();

	}

	@Override
	public void run() {

		long lastTime = System.nanoTime();
		double amountOfTicks = FPS;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		frames = 0;
		double timer = System.currentTimeMillis();
		while (isRunning) {

			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			if (delta >= 1) {

				tick();

				render();

				frames++;
				delta--;
			}
			if (System.currentTimeMillis() - timer >= 1000) {

				System.out.println("FPS: " + frames);

				frames = 0;
				timer += 1000;
			}
		}

		stop();

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		UI.x = arg0.getX();
		UI.y = arg0.getY();
		UI.mousePressed = true;

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
