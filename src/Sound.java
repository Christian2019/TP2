

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Sound {
	// Controle do Som
	public static boolean volume_Pressed_Positive;
	public static boolean volume_Pressed_Negative;
	
	public static Clips Music = load("/sound.wav", 1);
	

	public static class Clips {
		public Clip[] clips;
		public int p;
		private int count;
		private int framePosition;
		public String name;
	

		public Clips(byte[] buffer, int count ,String name)
				throws LineUnavailableException, IOException, UnsupportedAudioFileException {
			if (buffer == null)
				return;
			this.name=name;
			clips = new Clip[count];
			this.count = count;
			this.framePosition = 0;
			for (int i = 0; i < count; i++) {
				clips[i] = AudioSystem.getClip();
				clips[i].open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(buffer)));
			}
			volumePercent=1;
			updateVolume();
		}

		// Volume
		public double volumePercent = 1;

		public void updateVolume() {

			if (volume_Pressed_Positive) {
				volume_Pressed_Positive = false;
				if (volumePercent < 1) {
					volumePercent = volumePercent + 0.01;
				}
			} else if (volume_Pressed_Negative) {
				volume_Pressed_Negative = false;
				if (volumePercent > 0)
					volumePercent = volumePercent - 0.01;
			}

			Clip clip = clips[p];

			// Get the gain control from clip

			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

			// set the gain (between 0.0 and 1.0)

			float dB = (float) (Math.log(volumePercent) / Math.log(10.0) * 20.0);
			gainControl.setValue(dB);
			if (volumePercent < 0) {
				volumePercent = 0;
			} else if (volumePercent > 100) {
				volumePercent = 100;
			}
		//	System.out.println("Volume: " + volumePercent * 100);

		}

		public void play() {
			if (clips == null)
				return;
			clips[p].stop();
			clips[p].setFramePosition(0);
			clips[p].start();
			p++;
			if (p == count)
				p = 0;
		}

		public void pause() {
			if (clips == null)
				return;
			clips[p].stop();
			framePosition = clips[p].getFramePosition();

		}

		public void stop() {
			if (clips == null)
				return;
			clips[p].stop();
			clips[p].setFramePosition(0);

		}

		public void resume() {
			if (clips == null)
				return;
			clips[p].stop();
			clips[p].setFramePosition(framePosition);
			clips[p].start();

		}

		public void loop() {
			if (clips == null)
				return;
			clips[p].loop(300);
		}

	}

	public static Clips load(String name, int count) {
		try {
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
			DataInputStream dis = new DataInputStream(Sound.class.getResourceAsStream(name));

			byte[] buffer = new byte[1024];
			int read = 0;
			while ((read = dis.read(buffer)) >= 0) {
				baos.write(buffer, 0, read);
			}
			dis.close();
			byte[] data = baos.toByteArray();
			baos.close();
			return new Clips(data, count,name);
		} catch (Exception e) {
			try {
				return new Clips(null, 0,null);
			} catch (Exception ee) {
				return null;
			}
		}
	}



}
