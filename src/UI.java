import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class UI {
	public static boolean mousePressed;
	public static double x = 0;
	public static double y = 0;
	Font font = new Font("Arial", Font.BOLD, Proportions.percentages(Proportions.Y_Total, 4));
	Font font2 = new Font("Arial", Font.BOLD, Proportions.percentages(Proportions.Y_Total, 3));
	Font font3 = new Font("Arial", Font.BOLD, Proportions.percentages(Proportions.Y_Total, 2));
	public static ArrayList<Rectangle> server_page1_buttonsFiles_Area = new ArrayList<Rectangle>();
//	public static ArrayList<Rectangle> client_page1_buttonsDownload_Area = new ArrayList<Rectangle>();
	int page = 1;
	ConectedClient selectedCC;
	Rectangle cliked=null;
	public Rectangle server_page2_button_back = new Rectangle(Proportions.percentages(Proportions.X_Total, 80),
			Proportions.percentages(Proportions.Y_Total, 10), Proportions.percentages(Proportions.X_Total, 6),
			Proportions.percentages(Proportions.X_Total, 3));;

	public void tick() {
		if (Game.state==0) {
			server_page1_buttonsFiles();
		}else {
			client_page1_buttonsDonwload();
		}
		if (mousePressed) {
			mousePressed = false;
			if (Game.state == 0) {
				if (page == 1) {
					Rectangle b = server_insideButtonPage1();
					if (b != null) {
						selectedCC = ConectedClient.conectedClients.get(server_page1_buttonsFiles_Area.indexOf(b));
						page = 2;
						return;
					}

				} else if (page == 2) {
					if (server_insideButtonPage2()) {
						page = 1;
						return;
					}
				}

			}else {
				client_insideButtonDonwload();
				
			}

		}
	}
	
	private void client_insideButtonDonwload() {
		for (int i = 0; i < ConectedClient.conectedClients.size(); i++) {
			for (int j=0;j<ConectedClient.conectedClients.get(i).files.size();j++) {
				Rectangle r = ConectedClient.conectedClients.get(i).files.get(j).rectangle;
				if (x > r.x && x < (r.width + r.x) && y > r.y && y < (r.height + r.y)) {
					ConectedClient.conectedClients.get(i).files.get(j).donwload=true;
					return;
				}
			}
			

		}
		
	}

	private boolean server_insideButtonPage2() {

		if (x > server_page2_button_back.x && x < (server_page2_button_back.width + server_page2_button_back.x)
				&& y > server_page2_button_back.y
				&& y < (server_page2_button_back.height + server_page2_button_back.y)) {
			return true;
		}
		return false;
	}

	private Rectangle server_insideButtonPage1() {
		for (int i = 0; i < server_page1_buttonsFiles_Area.size(); i++) {
			Rectangle r = server_page1_buttonsFiles_Area.get(i);
			if (x > r.x && x < (r.width + r.x) && y > r.y && y < (r.height + r.y)) {
				return r;
			}

		}

		return null;
	}

	public void render(Graphics g) {
		if (Game.state == 0) {
			if (page == 1) {
				server_Page1_Render(g);
			} else if (page == 2) {
				server_Page2_Render(g);
				server_page2_buttonBack(g);
			}

		} else if (Game.state == 1) {
			clientRender(g);
			client_page1_buttonsDownload(g);
		}
	}

	private void client_page1_buttonsDonwload() {

		
		int upset = 0;
		int rightset =0;
		for (int i = 0; i < ConectedClient.conectedClients.size(); i++) {
			upset=0;
			ConectedClient cc = ConectedClient.conectedClients.get(i);
			for (int j = 0; j < cc.files.size(); j++) {
				Rectangle r = new Rectangle(Proportions.percentages(Proportions.X_Total, 19)+rightset,
						Proportions.percentages(Proportions.Y_Total, 11) + upset,
						Proportions.percentages(Proportions.X_Total, 6.6),
						Proportions.percentages(Proportions.Y_Total, 2));
				upset += Proportions.percentages(Proportions.Y_Total, 5);
				ConectedClient.conectedClients.get(i).files.get(j).rectangle=r;
			}
			rightset+=Proportions.percentages(Proportions.X_Total, 25);
		}

	}
	void server_page1_buttonsFiles() {
		server_page1_buttonsFiles_Area.clear();
		int upset = Proportions.percentages(Proportions.Y_Total, 5);
		for (int i = 0; i < ConectedClient.conectedClients.size(); i++) {
			Rectangle r = new Rectangle(Proportions.percentages(Proportions.X_Total, 55),
					Proportions.percentages(Proportions.Y_Total, 13) + upset,
					Proportions.percentages(Proportions.X_Total, 4), Proportions.percentages(Proportions.X_Total, 2));
			upset += Proportions.percentages(Proportions.Y_Total, 5);
			server_page1_buttonsFiles_Area.add(r);
		}
	}

	private void clientRender(Graphics g) {
		g.setColor(Color.green);
		g.setFont(this.font);
		g.drawString("Client FILES:", Proportions.percentages(Proportions.X_Total, 2),
				Proportions.percentages(Proportions.Y_Total, 4));
		g.setFont(this.font2);
		
		int upset = 0;
		int rightset =0;
		for (int i = 0; i < ConectedClient.conectedClients.size(); i++) {
			upset = Proportions.percentages(Proportions.Y_Total, 5);
			ConectedClient cc = ConectedClient.conectedClients.get(i);
			g.drawString("From: "+cc.name, Proportions.percentages(Proportions.X_Total, 2)+rightset,
					Proportions.percentages(Proportions.Y_Total, 8));
			for (int j = 0; j < cc.files.size(); j++) {
				String file = cc.files.get(j).name;
				g.drawString(file, Proportions.percentages(Proportions.X_Total, 2)+rightset,
						Proportions.percentages(Proportions.Y_Total, 8) + upset);
				upset += Proportions.percentages(Proportions.Y_Total, 5);
			}
			rightset+=Proportions.percentages(Proportions.X_Total, 25);
		}
	}
	
	void client_page1_buttonsDownload(Graphics g) {
		for (int i=0;i<ConectedClient.conectedClients.size();i++) {
			for (int j=0;j<ConectedClient.conectedClients.get(i).files.size();j++) {
				if (ConectedClient.conectedClients.get(i).files.get(j).donwload) {
					g.setColor(Color.GREEN);
					g.setFont(font3);
					g.drawString("Downloaded", ConectedClient.conectedClients.get(i).files.get(j).rectangle.x,
							ConectedClient.conectedClients.get(i).files.get(j).rectangle.y+Proportions.percentages(Proportions.Y_Total, 1.6));
				}else {
					g.drawImage(All_Images.donwload, ConectedClient.conectedClients.get(i).files.get(j).rectangle.x,
							ConectedClient.conectedClients.get(i).files.get(j).rectangle.y,
							ConectedClient.conectedClients.get(i).files.get(j).rectangle.width/2,
							ConectedClient.conectedClients.get(i).files.get(j).rectangle.height, null);
				}
			}	
		}
		
	}

	void server_page2_buttonBack(Graphics g) {
		g.setColor(Color.green);
		g.drawRect(server_page2_button_back.x, server_page2_button_back.y, server_page2_button_back.width,
				server_page2_button_back.height);
		g.setFont(font2);
		g.drawString("Back", server_page2_button_back.x + Proportions.percentages(Proportions.X_Total, 1),
				server_page2_button_back.y + Proportions.percentages(Proportions.Y_Total, 3.5));
	}

	

	private void server_Page1_Render(Graphics g) {
		g.setColor(Color.green);
		g.setFont(font);
		g.drawString("CONECTED CLIENTS:", Proportions.percentages(Proportions.X_Total, 5),
				Proportions.percentages(Proportions.Y_Total, 5));
		g.setFont(font2);
	
		g.drawString("NAME:", Proportions.percentages(Proportions.X_Total, 5),
				Proportions.percentages(Proportions.Y_Total, 15));
		g.drawString("FILES:", Proportions.percentages(Proportions.X_Total, 55),
				Proportions.percentages(Proportions.Y_Total, 15));
		int upset = Proportions.percentages(Proportions.Y_Total, 5);
		for (int i = 0; i < ConectedClient.conectedClients.size(); i++) {
			g.setColor(Color.green);
			ConectedClient cc = ConectedClient.conectedClients.get(i);
			
			g.drawString(cc.name, Proportions.percentages(Proportions.X_Total, 5),
					Proportions.percentages(Proportions.Y_Total, 15) + upset);
			/*
			g.setColor(Color.RED);
			g.fillRect(server_page1_buttonsFiles_Area.get(i).x, server_page1_buttonsFiles_Area.get(i).y,
					server_page1_buttonsFiles_Area.get(i).width, server_page1_buttonsFiles_Area.get(i).height);
			*/
			g.drawImage(All_Images.files, server_page1_buttonsFiles_Area.get(i).x, server_page1_buttonsFiles_Area.get(i).y,
					server_page1_buttonsFiles_Area.get(i).width, server_page1_buttonsFiles_Area.get(i).height, null);

			upset += Proportions.percentages(Proportions.Y_Total, 5);
		}

	}

	private void server_Page2_Render(Graphics g) {
		g.setColor(Color.green);
		g.setFont(font);
		g.drawString("FILES FROM: " + this.selectedCC.name, Proportions.percentages(Proportions.X_Total, 10),
				Proportions.percentages(Proportions.Y_Total, 5));
		g.setFont(font2);
		
		
		g.drawString("FILES:", Proportions.percentages(Proportions.X_Total, 10),
				Proportions.percentages(Proportions.Y_Total, 10));
		int upset = Proportions.percentages(Proportions.Y_Total, 5);
		for (int i = 0; i < this.selectedCC.files.size(); i++) {
			g.drawString(selectedCC.files.get(i).name, Proportions.percentages(Proportions.X_Total, 10),
					Proportions.percentages(Proportions.Y_Total, 10) + upset);
			upset += Proportions.percentages(Proportions.Y_Total, 5);
		}
	}
}
