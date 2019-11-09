package io.applications.timer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.swing.JOptionPane;

import javazoom.jl.player.Player;

public class App {

	private final static String WORKING_TIME_FLAG = "-t";// TIME FLAG ARGUMENT
	private static int timeToDelay = 10 * 60 * 1000;// TIME FOR DELAY DEFAULT 10 MINUTES (10 * 60 * 1000)

	private final static String NUMBER_OF_PARTS_FOR_REST_FLAG = "-n";
	private static int numberOfPartsOfRestFlag = 10;

	private final static String REST_DEALY_FOR_EACH_PART_FLAG = "-d";
	private static int delayForEachPartOfRestTime = 1000;

	private static final String SHOW_DIALOG_FLAG = "--dialogly";
	private static boolean isShowDialog = false;

	private static final String OS_NAME = System.getProperty("os.name");
	private static final String LINUX_OSS_SAME_NAME = "inux";
	
	private static final String SOUND_NAME = "1.mp3";

	private static final String GENERATE_FLAG = "--generate";
	private static String argumetnsToRunAutomatically = "";
	private static final String SHELL_SCRIPT_FILE_PATH = "/etc/profile.d/timer.sh";
	private static final String JAR_FILE_NAME = "timer.jar";
	private static final String DESTINATION_JAR_FILE_PATH = "/etc/"+JAR_FILE_NAME;
	private static final String CMD_TO_RUN_TIMER = "java -jar /etc/timer.jar ";
	
	private static final String VERSION_FLAG = "--version";
	private static final String VERSION = "1.2.1";

	private App(String[] args) {
		try {
			// CONFIGURE THE ARGUMENTS
			for (int i = 0; i < args.length; i++) {
				String currentArg = args[i].toLowerCase();//AFTERWARD USING EQUALS INSTEAD OF EQUALSIGNORECASE
				// SETUP THE WORKING TIME FLAG
				if (currentArg.equals(WORKING_TIME_FLAG)) {// IF WE HAVE THE TIME ARGUMENT
					if (args.length > i + 1) {// IF WE HAVE ANOTHER ONE INDEX
						timeToDelay = Integer.parseInt(args[i + 1]) * 1000;// GET THE NEXT INDEX AS TIME TO DELAY # PER
																			// SECOND
					}
				}

				// SETUP THE NUMBER OF PART OF REST FLAG
				if (currentArg.equals(NUMBER_OF_PARTS_FOR_REST_FLAG)) {
					if (args.length > i + 1) {
						numberOfPartsOfRestFlag = Integer.parseInt(args[i + 1]);
					}
				}

				// SETUP THE DELAY FOR EACH PART OF REST TIME
				if (currentArg.equals(REST_DEALY_FOR_EACH_PART_FLAG)) {
					if (args.length > i + 1) {
						delayForEachPartOfRestTime = Integer.parseInt(args[i + 1]) * 1000;
					}
				}

				// SETUP TO SHOW DIALOG FRAME
				if (currentArg.equals(SHOW_DIALOG_FLAG)) {
					isShowDialog = true;// WHEN USE THIS FLAG USING THE DIALOG TO INFROM STARTING
				}

				// GENERATE FLAG
				if (currentArg.equals(GENERATE_FLAG)) {
					if (args.length > i + 1) {// IF THE USER DEFINED THE ARGUMENTS FOR RUN
						String nextArg = args[i + 1].toLowerCase();// OPTIMIZING INSTEAD OF USING EQUALSIGNORECASE USING EQUALS
						if (!nextArg.equals(GENERATE_FLAG)) {// IF NEXT ARGUMENT ISN'T GENERATE FLAG (OTHERWISE IT IS A BUG)
							if (!nextArg.equals(WORKING_TIME_FLAG)) {//IF NEXT ARGUMENT "JUST" A WORKING TIME FLAG DON'T USE IT AS ARGUMENT
								if (!nextArg.equals(NUMBER_OF_PARTS_FOR_REST_FLAG)) {//LIKE ABOVE
									if (!nextArg.equals(REST_DEALY_FOR_EACH_PART_FLAG)) {//LIKE ABOVE
										argumetnsToRunAutomatically = nextArg;//USE IT AS ARGUMENTS TO RUN
									}
								}
							}
						}
					}
					generate();
					System.exit(0);//CLOSE THE PROGRAM
				}
				
				//VERSION FLAG
				if(currentArg.equals(VERSION_FLAG)) {
					System.out.println(VERSION);
					System.exit(0);
				}
			}

			if (!OS_NAME.contains(LINUX_OSS_SAME_NAME)) {// IF IT ISN'T A LINUX USING DIALOG ANYWAY
				isShowDialog = true;
			}

			while (true) {
				Thread.sleep(timeToDelay);// WAIT UNTIL DELAY TIME TO SHOW THE NOTIFICATION

				if (isShowDialog) {
					JOptionPane.showMessageDialog(null, "this time to take a rest to your eyes");
				} else {
					Runtime runtime = Runtime.getRuntime();// EXECUTE THE SHOW NOTIFICATION COMMAND
					runtime.exec("notify-send this-time-to-take-a-rest-to-your-eyes");
				}

				for (int i = 0; i < numberOfPartsOfRestFlag; i++) {// WAIT FOR NUMBER_OF_PLAYED_SOUNDS_AFTER_DELAY *
																	// DELAY_TIME_FOR_EACH_SOUND
					Thread.sleep(delayForEachPartOfRestTime);// WAIT FOR A TIME THEN PLAY
					// START PLAYING
					Player player = new Player(getClass().getResourceAsStream(SOUND_NAME));
					player.play();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//MAKE A .SH FILE AND COPY JAR FILE INTO /ETC FOLDER OF LINUX
	private void generate() {
		try {
			//GENERATE .SH FILE
			String content = CMD_TO_RUN_TIMER+argumetnsToRunAutomatically;
			FileOutputStream fos = new FileOutputStream(SHELL_SCRIPT_FILE_PATH);
			fos.write(content.getBytes());
			fos.close();
			//COPY THE TIMER.JAR INTO /ETC/TIMER.JAR
			//READ
			InputStream timerIn = getClass().getResourceAsStream(JAR_FILE_NAME);
			byte[] bytesOfJarFile = new byte[timerIn.available()];
			timerIn.read(bytesOfJarFile);
			timerIn.close();
			//WRITE
			fos = new FileOutputStream(DESTINATION_JAR_FILE_PATH);
			fos.write(bytesOfJarFile);
			fos.close();
			//SHOW THE SUCCESSFUL MESSAGE
			System.out.println("finished!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new App(args);
	}
}
