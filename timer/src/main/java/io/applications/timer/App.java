package io.applications.timer;

import javax.swing.JOptionPane;

import javazoom.jl.player.Player;

/**
 * this the only class which include whole the codes:)
 * @author abolfazlsadeqi2001
 */
public class App {
	private final static String WORKING_TIME_FLAG = "-t";
	private static int timeToDelay = 5 * 60 * 1000;/** 5 minutes = 5(minute) * 60(second per minute) * 1000(milliseconds per each second */

	private final static String NUMBER_OF_PARTS_FOR_REST_FLAG = "-n";
	private static int numberOfPartsOfRestFlag = 10;

	private final static String REST_DEALY_FOR_EACH_PART_FLAG = "-d";
	private static int delayForEachPartOfRestTime = 1000;

	private static final String SHOW_DIALOG_FLAG = "--dialogly";
	private static boolean isShowDialog = false;

	private static final String OS_NAME = System.getProperty("os.name");
	private static final String LINUX_OSS_SAME_NAME = "inux";
	
	private static final String SOUND_NAME = "1.mp3";
	
	private static final String VERSION_FLAG = "--version";
	private static final String VERSION = "1.2.1";

	/**
	 * the constructor start by reading the arguments then start the program
	 * @param args
	 */
	private App(String[] args) {
		try {
			/**
			 * configure the arguments 
			 */
			for (int i = 0; i < args.length; i++) {
				/** 
				 * after this using equals instead of 
				 * equalsIgnoreCase method which include more performance
				 */
				String currentArg = args[i].toLowerCase();
				/** setup the working time */
				if (currentArg.equals(WORKING_TIME_FLAG)) {/** IF WE HAVE THE TIME ARGUMENT */
					if (args.length > i + 1) {/** IF WE HAVE ANOTHER ONE INDEX */
						timeToDelay = Integer.parseInt(args[i + 1]) * 1000;/** GET THE NEXT INDEX AS TIME TO DELAY PER SECOND */
					}
				}

				/** setup the number of rest times */
				if (currentArg.equals(NUMBER_OF_PARTS_FOR_REST_FLAG)) {
					if (args.length > i + 1) {
						numberOfPartsOfRestFlag = Integer.parseInt(args[i + 1]);
					}
				}

				/** setup the delay for each part of rest time */
				if (currentArg.equals(REST_DEALY_FOR_EACH_PART_FLAG)) {
					if (args.length > i + 1) {
						delayForEachPartOfRestTime = Integer.parseInt(args[i + 1]) * 1000;
					}
				}

				/** setup to show dialog frame */
				if (currentArg.equals(SHOW_DIALOG_FLAG)) {
					isShowDialog = true;/** using dialog instead of notification */
				}
				
				/** setup version flag */
				if(currentArg.equals(VERSION_FLAG)) {
					System.out.println(VERSION);
					System.exit(0);
				}
			}

			/** 
			 * if the current operating system isn't a linux using dialog anyway (how ever that the user 
			 * doesn't use the SHOW_DIALOG_FLAG)
			 */
			if (!OS_NAME.contains(LINUX_OSS_SAME_NAME)) {
				isShowDialog = true;
			}

			while (true) {
				Thread.sleep(timeToDelay);/** wait while the working time finish */

				if (isShowDialog) {/** show a dialog */
					JOptionPane.showMessageDialog(null, "this time to take a rest to your eyes");
				} else {
					Runtime runtime = Runtime.getRuntime();/** execute the show notification command */
					runtime.exec("notify-send this-time-to-take-a-rest-to-your-eyes");
				}

				/**
				 * wait for NUMBER_OF_PLAYED_SOUNDS_AFTER_DELAY * DELAY_TIME_FOR_EACH_SOUND
				 */
				for (int i = 0; i < numberOfPartsOfRestFlag; i++) {
					/**
					 * wait for a part of resting time
					 */
					Thread.sleep(delayForEachPartOfRestTime);
					/**
					 * start playing a click sound
					 */
					Player player = new Player(getClass().getResourceAsStream(SOUND_NAME));
					player.play();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new App(args);
	}
}
