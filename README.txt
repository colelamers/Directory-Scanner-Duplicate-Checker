/********************/
/********************/
/***Cole Lamers******/
/***File Scanner-****/
/***Duplicate File***/
/******Checker*******/
/********************/
/********************/

7/13/2020
Since the program currently is not set up as an executable JAR File, it will need to be run in an IDE. This was created in Eclipse 2020-06 and will be explained as if you are using Eclipse.

8/1/2020
The program should be working in its entirety and all functionality visually present should be in working order.

This is also assumed to be ran on Windows 10 (Version 1903).

/***To Launch the Program in Eclipse***/
-Launch Eclipse
-In a new workspace or in the current workspace, import the entire folder and it's contents within the .ZIP file named "ColeLamers_JavaFileScannerDupeChecker_Alpha."
-The program currently will not run yet, you need to import the java libraries and adjust the run configuration.
-Take the JavaFX SDK folder out, place it in a directory where it can be accessed by Eclipse. 

-To import the JavaFX SDK library, right click on the project folder
-Click "Build Path", then "Add Libraries"
-Click on "User Library"
-In the Add Library screen, click the button "User Libraries..."
-Under the Preferences (Filtered) screen, click "New"
-While still having the folder highlighted, click "Add External JARs"
-Find the path of the JavaFX SDK library you took out of the .ZIP file
-In the path, go to ...\javafx-sdk-14.0.1\lib\
-Select all the .JAR files and click "Open"
-Click "Apply and Close"
-Under the "Add Library" screen, check the library you just created and click "Finish"

-Now update the Run Configuration
-Go to the "Run" button and hit the arrow adjacent to it
-In the Run Configurations Pane, click on the "Arguments" tab
-In the VM Arguments, add this line here (YOU WILL HAVE TO MODIFY THE PATH TO THE EXACT PATH WHERE YOU STORED THE JAVAFX SDK):
	--module-path "C:\Program Files\Java\javafx-sdk-14.0.1\lib" --add-modules javafx.controls,javafx.fxml
-Click "Apply"
-The program should now be able to run at this point so you can click "Run"