import java.awt.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class ConectedClient {
public static ArrayList<ConectedClient> conectedClients= new ArrayList<ConectedClient>();

public String name;
public ArrayList<Files> files = new ArrayList<Files>();

public ConectedClient( String name, ArrayList<Files> files) {
	this.name = name;
	this.files = files;
}
public static void update() {
	conectedClients.clear();
	for(Entry<String, ArrayList<PeerFiles>> h : Game.peerFilesHashMap.entrySet()) {
	    String key = h.getKey();
	    ArrayList<PeerFiles> value = h.getValue();
	    ArrayList<Files> f = new ArrayList<Files>();
	    for (int i=0;i<value.size();i++) {
	    	String element = value.get(i).path;
	    	String[] array = element.split("/");
	    	element = array[array.length-1];
	    	f.add(new Files(value.get(i).hash, element));
	    }
	    
	    ConectedClient cc = new ConectedClient(key,f);
	    conectedClients.add(cc);
	    
	}
}
public static void init() {
	/*
	ArrayList<String> files1 = new ArrayList<String>();
	files1.add("foto1.png");
	files1.add("video1.mp4");
	files1.add("sound1.wav");
	ConectedClient cc1= new ConectedClient( "Guilherme", files1);
	
	ArrayList<String> files2 = new ArrayList<String>();
	files2.add("imagem.png");
	files2.add("filme.mp4");
	files2.add("musica.wav");
	ConectedClient cc2= new ConectedClient( "Daniel", files2);
	
	conectedClients.add(cc1);
	conectedClients.add(cc2);
	*/
	ArrayList<PeerFiles> files1 = new ArrayList<PeerFiles>();
	files1.add(new PeerFiles("aherhreaheraherhaerhaer","C:/nlana/n�ntr�nsrt/video.mp4"));
	files1.add(new PeerFiles("aherhreaheraherhaer","C:/nlana/n�ntr�nsrt/video1.mp4"));
	files1.add(new PeerFiles("aherhreaerhaerhaer","C:/nlana/n�ntr�nsrt/sound1.wav"));
	files1.add(new PeerFiles("aherhreaherherhheraherhaerhaer","C:/nlana/n�ntr�nsrt/souaaa.wav"));
	Game.peerFilesHashMap.put("deconto", files1);
	ArrayList<PeerFiles> files2 = new ArrayList<PeerFiles>();
	files2.add(new PeerFiles("aherhreaheraherhaerhaer","C:/nlana/n�ntr�nsrt/gravura.png"));
	files2.add(new PeerFiles("aherhreaheraherhaerhaer","C:/nlana/n�ntr�nsrt/filme.mp4"));
	files2.add(new PeerFiles("aherhreaheraherhaerhaer","C:/nlana/n�ntr�nsrt/vi.mp4"));
	files2.add(new PeerFiles("aherhreaheraherhaerhaer","C:/nlana/n�ntr�nsrt/vi2.mp4"));
	files2.add(new PeerFiles("aherhreaheraherhaerhaer","C:/nlana/n�ntr�nsrt/vi3.mp4"));
	files2.add(new PeerFiles("aherhreaheraherhaerhaer","C:/nlana/n�ntr�nsrt/vi4.mp4"));
	Game.peerFilesHashMap.put("daniel", files2);
	ArrayList<PeerFiles> files3 = new ArrayList<PeerFiles>();
	files3.add(new PeerFiles("51181982342349151","C:/nlana/n�ntr�nsrt/photo.png"));
	files3.add(new PeerFiles("511819813242349151","C:/nlana/n�ntr�nsrt/clip.mp4"));
	files3.add(new PeerFiles("5118198124239151","C:/nlana/n�ntr�nsrt/som.wav"));
	Game.peerFilesHashMap.put("chris", files3);
}

}
