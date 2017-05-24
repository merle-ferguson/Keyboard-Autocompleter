# **Welcome to the Keyboard Autocompleter**

This application is used to capture keyboard input and offer suggestions on how a word is likely to be completed.

## To build it:

I used Eclipse, but you could easily use the command line by following the instructions below. Make sure that you have an updated version of the JDK (Java Development Kit) on your machine.

Open your command line and navigate to my project's src directory, which contains the java files and manifest file.

If you are running windows, type PATH=C:\Program Files\Java\[jdk version]\bin.
Fill in [jdk version] with your Java Development Kit version.

All operating systems run the following:

javac *.java

jar cvfm Keyboard_Autocompleter.jar manifest.txt *.class

## To run it:

In the command line type:

java -jar Keyboard_Autocompleter.jar

From there, you will have instructions on how to use it.
Please note, -T must be prepended to any training input.

For example:
-T The third thing that I need to tell you is that this thing does not think thoroughly.

Thank you for using my application :D
