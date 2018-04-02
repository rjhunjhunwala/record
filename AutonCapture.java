/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autoncapture;

import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rohan
 */
public class AutonCapture {
//not a thing...

	public static final AtomicInteger x = new AtomicInteger(0), y = new AtomicInteger(0);

	static String[] autonRoutes = new String[]{
"gjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjfjfjfjfjfjfjfjfjejejejejejejejejejfjfjgjhjikjkkkkklklklkmkmkmkmknknknkokokpkqkqkrksksktktktkukukukukvkvkvkwkwkwkwkwkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkxkwkvkuktkrkqkqkpkoknknjmjljljkjjjjjhjhjfjfjejejejejejejejejfjgjgjgkhkikkklkokrksktkvkwkxkzkzkAkAkBkBkBkBkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAkAlznxowqvsususustststsssrrqrprnrkrgqdqcqbqbqbqbqbqbpbpbococndnemfmhljlkkmknkqjsjtjvjvjwjxkykzkAlAmAnApzqxrvrustsrroqnqnpmololnlnlmlmlmllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllklkljlilhlhlhlglglglglfkfkfkfkfkfkfjgjhjiikimioirisjtjukvmvnwowpxqxqxqxqyqyqzqzqApApApAoAoAnznzmymxmxmvlvlultlslqlplplomomnmnnmnmnlnlnlnknknjnjninhmhmgmglglglglgkgkgkgkgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgjgj:1q01CB6I/8WE"
	};
	public static final int time = 15;
	public static final int samples = 50;
	public static final int period = 1000 / samples;
	public Motor[] motors = new Motor[]{new YMotor(), new XMotor()};

	//dummy fire piston
	void firePiston(final String directions) {
	//	System.out.println(directions);
			int piston = BASE_64_ALPHABET.indexOf(directions.charAt(0));
				int a = BASE_64_ALPHABET.indexOf(directions.charAt(1)) * 64
												+ BASE_64_ALPHABET.indexOf(directions.charAt(2));
				final double t = (double) time * 1000.0 * (double) a / 4096.0;
			//	System.out.println(t);
				new Thread(new Runnable() {
			public void run() {

		
		
				try {
					Thread.sleep((long) t);
				} catch (Exception ex) {

				}
				//just print what was typed instead of firing a piston
				System.out.println(BASE_64_ALPHABET.charAt(piston));
			}
		}).start();

	}

	//dummy motor class override at competition
	public static class Motor {

		public Motor() {

		}

		public double getSpeed() {

			return 0.0;
		}

		public void setSpeed(double inSpeed) {
		}
	}

	//Examples, used to simulate the mouse as a motor

	public static class YMotor extends Motor {

		public double getSpeed() {
			return RadioAlarm.getMouse().getY() / 2048.0;
		}
		public void setSpeed(double inSpeed){
			y.set((int) (inSpeed*2048.0));
		}
	}

	public static class XMotor extends Motor {

		public double getSpeed() {
			return RadioAlarm.getMouse().getX()/4096.0;
		}
		public void setSpeed(double inSpeed){
			x.set((int) (inSpeed*4096.0));
		}
	}
	
	public static final String BASE_64_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890+/";

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws Exception {
	new AutonCapture().playBack(autonRoutes[0]);
		Thread.sleep(2500);
		//System.out.println("Capture Started");
		AutonCapture a = new AutonCapture();
		//a.startCapture();
	}
	String out = "";

	public void playBack(final String recorded) {
final String motorString = recorded.substring(0,recorded.indexOf(":"));
final String pistonString = recorded.substring(recorded.indexOf(":")+1);
for(int i = 0;i<pistonString.length();i+=3){
	firePiston(pistonString.substring(i,i+3));
}
new Thread(new Runnable(){
	public void run(){
		int i = time*1000;
		while(i>0){
		//System.out.println(x+"|"+y);
		RadioAlarm.robot.mouseMove(x.get(), y.get());
			try{
				Thread.sleep(1);
			}catch(Exception ex){}
			i--;
		}
	}
}).start();
new Thread(new Runnable(){
	public void run(){
		
		for(int i = 0;i<motorString.length();i+=motors.length){
			for(int j = 0;j<motors.length;j++){
				motors[j].setSpeed(BASE_64_ALPHABET.indexOf(motorString.charAt(i+j))/64.0);
			}
			try {
				Thread.sleep(period);
			} catch (InterruptedException ex) {
				Logger.getLogger(AutonCapture.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}).start();
	}

	public void startCapture() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				class myTask extends TimerTask {

					@Override
					public void run() {
						for (Motor m : motors) {
							int speed = (int) (m.getSpeed() * .99999 * 64);
							out += (BASE_64_ALPHABET.charAt(speed));
						}
					}

				}
				Timer timer = new Timer();
				timer.scheduleAtFixedRate(new myTask(), 0, period);
				try {
					Thread.sleep(time * 1000);
				} catch (InterruptedException ex) {
					Logger.getLogger(AutonCapture.class.getName()).log(Level.SEVERE, null, ex);
				}
				timer.cancel();
				timer.purge();

			}

		}).start();

		new Thread(new Runnable() {
			public void run() {
				long start = System.currentTimeMillis();
				String s = "";
				long a;
				char c;
				while (true) {
					try {
						c = (char) System.in.read();
						a = System.currentTimeMillis() - start;
						double t = a / (time * 1000.0);
						int b = (int) (t * 4096);
						if (t > 1) {
							break;
						} else if (c != '\n') {
							s += c;
							s += BASE_64_ALPHABET.charAt(b >> 6);
							s += BASE_64_ALPHABET.charAt(b & 63);
						}
					} catch (IOException ex) {
						Logger.getLogger(AutonCapture.class.getName()).log(Level.SEVERE, null, ex);
					}

					try {
						Thread.sleep(100);
					} catch (InterruptedException ex) {
						Logger.getLogger(AutonCapture.class.getName()).log(Level.SEVERE, null, ex);
					}

				}
				out += (":" + s);

				System.out.println(out);
			}
		}
		).start();

	}
}
