/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoncapture;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.net.URI;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rohan
 */
public class RadioAlarm {

	static Robot robot;

	static final int speakX = 1788, speakY = 1060;
	static final int muteX = 1608, muteY = 1000;
	static final int skipX = 1104, skipY = 557;
	static final int playbackBarXStart = 265, playbackBarXEnd = 1090, playbackBarY = 640;
	static final int playbackBarLength = playbackBarXEnd - playbackBarXStart;

	static {
		try {
			robot = new Robot();
		} catch (Exception ex) {
		}
	}

	public static void click() throws Exception {
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		Thread.sleep(1000);
	}

	public static void click(int x, int y) throws Exception {
		robot.mouseMove(x, y);
		click();
	}

	public static Point getMouse() {
		return MouseInfo.getPointerInfo().getLocation();
	}

	public static Color getColorUnderMouse() {
		Point p = getMouse();
		return getColorUnderPoint(p);
	}

	public static Color getColorUnderPoint(Point p) {

		return robot.getPixelColor((int) p.x, (int) p.y);
	}
	public static final boolean DEBUG_ONE = 1==11;
	public static final boolean DEBUG_TWO = 1==11;
	public static final boolean QUIT = 1==1;

	public static void debug() throws Exception {
		if (DEBUG_ONE) {

			Thread.sleep(7 * 1000);
			//System.out.println(isVideoAd());
			//System.out.println(getLength());
			Point p = getMouse();
			System.out.println(p);
			System.out.println(getColorUnderMouse());

			if (QUIT) {
				System.exit(0);
			}
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws Exception {
		debug();

		Random random = new Random();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {

					for (;;) {
						Thread.sleep(60000);

						Point p = getMouse();

						robot.mouseMove(p.x + 2 * (int) (Math.random() * 2) - 1, p.y + 2 * (int) (Math.random() * 2) - 1);
					}
				} catch (Exception ex) {
					Logger.getLogger(RadioAlarm.class.getName()).log(Level.SEVERE, null, ex);
				}
			}

		}).start();
		while (true) {
			while (true) {
				if (DEBUG_TWO || Calendar.getInstance().getTime().toString().contains("06:30:")) {
					if (Desktop.isDesktopSupported()) {
						click(speakX, speakY);
						click(muteX, muteY);
						String[] songs = new String[]{"https://www.youtube.com/watch?v=wH-by1ydBTM","https://www.youtube.com/watch?v=Y5H_h9wquW8","https://www.youtube.com/watch?v=kMsEwMnOKpc","https://www.youtube.com/watch?v=EmupctH-oAs","https://www.youtube.com/watch?v=O6e0sAMRioo","https://www.youtube.com/watch?v=XE0nOfTA3pY","https://www.youtube.com/watch?v=cjVQ36NhbMk","https://www.youtube.com/watch?v=2hvitOG7FgY", "https://www.youtube.com/watch?v=gCxUJRT-hq0", "https://www.youtube.com/watch?v=VyXEvDXqeeI", "https://www.youtube.com/watch?v=UUtIlq3KxAU", "https://www.youtube.com/watch?v=wi105P5bX-g", "https://www.youtube.com/watch?v=EFEmTsfFL5A", "https://www.youtube.com/watch?v=ei8hPkyJ0bU", "https://www.youtube.com/watch?v=u3IAybZOt-E", "https://www.youtube.com/watch?v=aCyGvGEtOwc", "https://www.youtube.com/watch?v=7YAAyUFL1GQ", "https://www.youtube.com/watch?v=jG1JY0rt2Os", "https://www.youtube.com/watch?v=NDHY1D0tKRA","https://www.youtube.com/watch?v=HS4NqNkhTOo","https://www.youtube.com/watch?v=V5msaglDU4Q",};
						Desktop.getDesktop().browse(new URI(songs[random.nextInt(songs.length)]));
						Thread.sleep(3500);

						boolean isAd = isVideoAd();
						int x = isAd ? getLength() : 0;
						if (isAd) {
							Thread.sleep(x > 30 ? 2000 : 60 * 1000);
						}
					//	System.out.println(isAd+"|"+x);
						if (isAd && isVideoAd() && x > 30) {

							click(skipX, skipY);

						}
						click(speakX, speakY);
						click(muteX, muteY);
						break;
					} else {
						System.err.println("Something VERY bad happened");
						System.exit(1);
					}
				}
			}
			Thread.sleep(60 * 2 * 1000);
			click(skipX, skipY);
		}
	}

	public static int getLength() throws Exception {
		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 200; i++) {
					robot.mouseMove(skipX + i % 2, skipY + i % 3);
					try {
						Thread.sleep(30);
					} catch (InterruptedException ex) {
						Logger.getLogger(RadioAlarm.class.getName()).log(Level.SEVERE, null, ex);
					}

				}
			}
		}).start();
		Thread.sleep(500);
		double startDist = getDistBar(playbackBarXStart);
		Thread.sleep(3000);
		double endDist = getDistBar((int) startDist);
		return (int) (3 / (((endDist) - (startDist)) / playbackBarLength));
	}

	public static int getDistBar(int seed) {
		int i = 0;
		for (i = seed; true; i++) {
			if (robot.getPixelColor(i, playbackBarY).getRed() < 255) {
				break;
			}
		}
		//System.out.println(i);
		return i;
	}

	public static boolean isVideoAd() throws InterruptedException {
		new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < 32; i++) {
										robot.mouseMove(skipX + i % 2, skipY + i % 3);
					try {
						Thread.sleep(32);
					} catch (InterruptedException ex) {
						Logger.getLogger(RadioAlarm.class.getName()).log(Level.SEVERE, null, ex);
					}

				}
			}
		}).start();
		Thread.sleep(500);
		Color c = robot.getPixelColor(playbackBarXStart, playbackBarY);

		return c.getRed() == 255 && c.getGreen() == 204 && c.getBlue() == 0;
	}
}
