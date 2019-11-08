package io.applications.timer;

import javax.swing.JOptionPane;

import javazoom.jl.player.Player;

public class App {
	
	private final static String WORKING_TIME_FLAG = "-t";//TIME FLAG ARGUMENT
	private static int timeToDelay = 10 * 60 * 1000;//TIME FOR DELAY DEFAULT 10 MINUTES (10 * 60 * 1000)
	
	private final static String NUMBER_OF_PARTS_FOR_REST_FLAG = "-n";
	private static int numberOfPartsOfRestFlag = 10;
	
	private final static String REST_DEALY_FOR_EACH_PART_FLAG = "-d";
	private static int delayForEachPartOfRestTime = 1000;
	
	private static final String SHOW_DIALOG_FLAG = "--dialogly";
	private static boolean isShowDialog = false;
	
	private static final String OS_NAME = System.getProperty("os.name");
	
	private App(String[] args) {
		try {
			//CONFIGURE THE ARGUMENTS
			for(int i=0; i<args.length; i++) {
				//SETUP THE WORKING TIME FLAG
				if(args[i].equalsIgnoreCase(WORKING_TIME_FLAG)) {//IF WE HAVE THE TIME ARGUMENT
					if(args.length > i+1) {//IF WE HAVE ANOTHER ONE INDEX
						timeToDelay = Integer.parseInt(args[i+1]) * 1000;//GET THE NEXT INDEX AS TIME TO DELAY # PER SECOND
					}
				}
				
				//SETUP THE NUMBER OF PART OF REST FLAG
				if(args[i].equalsIgnoreCase(NUMBER_OF_PARTS_FOR_REST_FLAG)) {
					if(args.length > i+1) {
						numberOfPartsOfRestFlag = Integer.parseInt(args[i+1]);
					}
				}
				
				//SETUP THE DELAY FOR EACH PART OF REST TIME
				if(args[i].equalsIgnoreCase(REST_DEALY_FOR_EACH_PART_FLAG)) {
					if(args.length > i+1) {
						delayForEachPartOfRestTime = Integer.parseInt(args[i+1]) * 1000;
					}
				}
				
				//SETUP TO SHOW DIALOG FRAME
				if(args[i].equalsIgnoreCase(SHOW_DIALOG_FLAG)) {
					isShowDialog = true;//WHEN USE THIS FLAG USING THE DIALOG TO INFROM STARTING
				}
			}
			
			if(!OS_NAME.contains("inux")) {//IF IT ISN'T A LINUX
				isShowDialog = true;
			}
			
			while(true) {
				Thread.sleep(timeToDelay);//WAIT UNTIL DELAY TIME TO SHOW THE NOTIFICATION
				
				if(isShowDialog) {
					JOptionPane.showMessageDialog(null, "this time to take a rest to your eyes");
				}else {
					Runtime runtime = Runtime.getRuntime();//EXECUTE THE SHOW NOTIFICATION COMMAND
					runtime.exec("notify-send this-time-to-take-a-rest-to-your-eyes");
				}
				
				for(int i=0; i<numberOfPartsOfRestFlag; i++) {//WAIT FOR NUMBER_OF_PLAYED_SOUNDS_AFTER_DELAY * DELAY_TIME_FOR_EACH_SOUND
					Thread.sleep(delayForEachPartOfRestTime);//WAIT FOR A TIME THEN PLAY
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
