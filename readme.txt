Project group number: g3k
Title: TraJour

Description: TraJour is an app that will be used by travelers to communicate with other travelers and keep track of their own journeys. 
This desktop application functions as a social media diary for travelers. The application consists of features that not only allow 
users to plan their journeys but also share their travels with their friends, add and modify their journeys with the help of a map, 
have their own profile page where they can add friends, make a wishlist for their future journeys. We are using JavaFX 11 as external library. 
For data persistency we are using MySQL and for the map we are using WorldMapViewfrom ControlsFX This text file is a report that conveys our 
current status, and the details of code organization, the tools we have used, and instructions for the setup of the program.

Current Status: Except the oncoming small adjustments on the graphical user interface to make it better, 
the colorway, the button alignments, and the design has been completed. FXML files are created and are linked to the related code. 
A map has been implemented and it is going to have two new features which are distance calculating between the selected countries 
and map will show the capital cities on the map. The database that has been implemented is MySQL. This database makes it possible 
for a user to register and assigns each user a key. The login, logout, register, change password functions are working properly. 
Adding friends is possible as well and the main page has a feed section. Feed is showing users friends’ journeys and ratings. 
On profile page the user has a friends list and it will soon be implementing friends’ past journeys.


Group Members and Contributions:
Mehmet Feyyaz Küçük 	-> Added functionality to post journeys for main page added new functionalities to profile page
Selim Can Güler 	-> Worked on new database queries, and map page, added new functionalities and view options to the map map
Ahmet Alperen Yılmazyıldız -> Worked on the UI design and worked on FXML files, some methods in profile page.
Arda Kurtuoğlu 		-> Worked on the UI design and fixed FXML files

Code Organization: To keep the uniqueness of the package names, the first two packages are named as "com" and "trajour". Inside trajour 
is the main packages of the program. The code is organized into 7 packages. The Main class that launches the program is inside "com.trajour"
The first package is "db" which contains the database related classes "DatabaseConnection" and "DatabaseQuery". The second package is 
"journey" and it contains model classes and interfaces related to journey which gives the functionallity to the users to share, add, post their 
journeys. The third package is "login", in the "login" package there are FXML files and controllers for these FXML files for the login and 
register processes. The fourth package is main and it contains the "MainController" and "ShareJourneyController" classes and FXML files of
these controllers. These provide the functionality to the main page. The fifth package is "map" which contains the map controller and FXML
files to provide the map page with further functionalities. The sixth package is "profile", it contains classes related to functionalities
that can be made within the profile page and the GUI of the profile. The last package is "user" which contains model classes "User" and "Friend".

Tools: 
For the IDE we used IntelliJ 2021.1 Community Edition.
We have used Java 11 (AdoptOpenJDK 11.0.10.9-hotspot) and we have  3 external libraries in our project. The first one is the JavaFX 11 library to create the desktop application. 
The second one is Controls FX 11.1.0. We used ControlsFX to integrate the map into our program and provide more modern looking GUI elements 
throughout the application. The third one is the MySQL Connector for Java (Connector/J 8.0.23 to be more precise). This is required to to 
accomplish database connectivity between the program and the database.

Setup & Run Instructions:
Firstly the program uses Java 11 and some parts of it may be broken in different versions of Java, so it is recommended that whoever wants to
run the program should use Java 11. There are 3 external libraries that are used in the program, all of them should be downloaded as ".jar" files
and should be added to the project library. This can easily be accomplished using a IDE. 
For IntelliJ, the JavaFX library must be placed inside
"Libraries" which can be found by navigating to File -> Project Structure -> Libraries. ControlsFX 11.1.0 and Connector/J must be placed inside 
"Modules" which can also be found by nagivating to File -> Project Structure -> Modules. After that run configurations must be specified, this 
can be specific to IDE but for IntelliJ, users should open Run/Debug configurations, and select Java 11 as SDK. Then these VM options should be 
added: 

--module-path
/path/to/javafx/sdk
--add-modules
javafx.controls,javafx.fxml
--add-exports=javafx.base/com.sun.javafx.event=ALL-UNNAMED

and Main class should be specified as "com.trajour.Main". For IntelliJ, further information about the setup of JavaFX applications can be found on 
https://www.jetbrains.com/help/idea/javafx.html#vm-options

The project can be recompiled and run using an IDE if the setup is successfully completed, by using "Build Project" (to recompile), "Run" or similarly named
buttons.


