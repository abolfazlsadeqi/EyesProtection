package io.applications.timer;

import javazoom.jl.player.Player;

public class App {
	
	private final static String TIME_FLAG = "-t";//TIME FLAG ARGUMENT
	private static int timeToDelay = 10 * 60 * 1000;//TIME FOR DELAY DEFAULT 10 MINUTES (10 * 60 * 1000)
	
	private final static int NUMBER_OF_PLAYED_SOUNDS_AFTER_DELAY = 10;
	private final static int DELAY_TIME_FOR_EACH_SOUND = 1000;
	
	private App(String[] args) {
		try {
			//IF WE HAVE A TIME ARGUMENT USE IT FOR TIME-TO-DELAY
			for(int i=0; i<args.length; i++) {
				if(args[i].equalsIgnoreCase(TIME_FLAG)) {//IF WE HAVE THE TIME ARGUMENT
					if(args.length > i+1) {//IF WE HAVE ANOTHER ONE INDEX
						timeToDelay = Integer.parseInt(args[i+1]);//GET THE NEXT INDEX AS TIME TO DELAY
					}
				}
			}
			
			while(true) {
				Thread.sleep(timeToDelay);//WAIT UNTIL DELAY TIME TO SHOW THE NOTIFICATION
				
				Runtime runtime = Runtime.getRuntime();//EXECUTE THE SHOW NOTIFICATION COMMAND
				runtime.exec("notify-send this-time-to-take-a-rest-to-your-eyes");
				
				for(int i=0; i<NUMBER_OF_PLAYED_SOUNDS_AFTER_DELAY; i++) {//WAIT FOR NUMBER_OF_PLAYED_SOUNDS_AFTER_DELAY * DELAY_TIME_FOR_EACH_SOUND
					Thread.sleep(DELAY_TIME_FOR_EACH_SOUND);//WAIT FOR A TIME THEN PLAY
					//START PLAYING
					Player player = new Player(getClass().getResourceAsStream("1.mp3"));
					player.play();
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new App(args);
	}
}
