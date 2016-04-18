Run - ReadMe
No special run files were used in the application.

Setting up the IDE: NOTE: Must be using jdk 1.8 or greater.
Step 1) Open Java IDE (such as eclipse).
Step 2) Start a new project.
Step 3) Copy all folders from code->java-> into yourNewProjectFolder->src
Step 4) Import Gson jar into your working project. (Gson jar can be found in code->gson-2.3.1.jar)

Setting up the databases:
If you are running the database, message controller, and application processes all on the same machine skip to step 4.
Step 1) Open domainNames.txt and portNumbers.txt (they can be found in the configuration folder)
Step 2) Change the domainNames file so that instead of localhost it now has your computer's IP Address.
Step 3) **OPTIONAL** Change the portNumbers file if you would like to select the port numbers the processes will use.

Step 4) Run databaseAController.java, databaseBController.java, databaseCController.java
Step 5) Run messageController.java
Step 6) Run TabletApp.java for each employee you would like to log in.
	Running TabletApp.java should open up the login screen. 
	On each tablet, only the TabletApp.java file will be needed to run the SWE application.

Step 7) Logging in as an employee:
Authentication is done by a unique employee ID.
In order to demo our application at this time, we have hardcoded some login IDs for your convenience. 
0->waiter
1->manager
2->chef
3->host
4->waiter