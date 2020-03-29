# CSED2Tracker


# Required Downloads

JDK Version: https://www.oracle.com/java/technologies/javase/jdk12-archive-downloads.html
mySQL Windows: https://dev.mysql.com/downloads/installer/
(basically for this just do the defaults and get everything
        HELP VIDEO: https://www.youtube.com/watch?v=BOUMR85B-V0&list=PLhs1urmduZ2-yp3zID5rMEmXDETN8xvMo&index=1
mySQL Java connector: https://dev.mysql.com/downloads/connector/j/
NOTE: CLICK ON PLATFORM INDEPENDANT AND DOWNLOAD THE ZIP

# How to link the JDK and Connector
When you ahve downloaded the java project, right click on it -> properties -> Java build path -> Libraries
Add the JDK-12.0.2 You downloaded and the SQL connecter.jar file 

# How to import the DB into your MySQL workbench!!!
For this u need to be on ur server in ur workbench. Click on server at the top, import, then follow the video imma link
https://www.youtube.com/watch?v=Jvul-wr-_Bg


# The final step. Getting the project to link with ur version of the DB
In the frame1.java file there will be a getConnection method. Basically change the details in the url, username and password so it fits ur machine. Again sort of just follow this bloke here
https://www.youtube.com/watch?v=BOUMR85B-V0
