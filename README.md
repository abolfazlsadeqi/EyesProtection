# Eyes protection
It is just a simple software that inform you each 10 minutes that this time is to take the rest of your eyes

**you can change the working time by the following syntax(default : 600 (10 min))

	syntax:
		java -jar timer.jar -t timerPerSeconds
	example:
		java -jar timer.jar -t 600  

**you can change the number of rest parts by the following syntax (default: 10)

	syntax:
		java -jar timer.jar -n numberOfParts
	example:
		java -jar timer.jar -n 10

**you can change the during of rest time for each part (defalut : 1sec)

	syntax:
		java -jar timer.jar -d delay
	example:
		java -jar timer.jar -d 1
		
**you can using the dialog instead notification by using the --dialogly (this option just for linux users and others can't have notification there fore this software using dialog automatically)

**linux and windows users can use it for ever without run the commands!,just write java -jar --generate (once) ,afterward you can see it is work when you are restart your computer without run the command.In addition after write the java -jar --generate you don't need the timer.jar you can delete it.
