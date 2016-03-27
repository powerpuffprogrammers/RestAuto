Unit Tests
This ReadMe describes the scope of our unit tests and how to run them. 

The unit tests for Host Interface were done in IntegrationTestHost.java. 
Since a unit test is testing one function of an integration test, we were 
able to write all the tests in the same file. 

To run the unit tests, first run DatabaseAController.java,
DatabaseBController.java and MessageController.java. 
Once those are running, run IntegrationTestHost.java. 

The unit tests we did were for loadTables() and seat(). 
The function loadTables() returns true on success and false on failure to load
the tables to the screen. The function seat() returns 0 on success of changing 
the status of a table from ready (‘r’) to seated (‘s’) and redrawing the screen 
to account for this update. It returns -1 if the host attempts to change the 
status to seated when a table is not ready. On success of these integration 
tests, the output is “TEST PASSED” and on failure of the test the output will
be “TEST FAILED”. 

The unit tests for Waiter Interface were done in the 
IntergrationTestWaiter.java. Since a unit test is testing one function of an 
integration test, we were able to write all the tests in the same file. 

To run the unit tests, first run DatabaseAController.java, 
DatabaseCController.java and MessageController.java. Once those are running, 
run IntegrationTestWaiter.java. 

The unit tests we did were for loadMenu() and addDishtoTicket(). The function 
loadMenu() returns true on success and false on failure to load the menu items 
on the waiter’s screen. The function addDishtoTicket() returns true if the dish 
is successfully added to the waiter’s ticket and false if the dish couldn’t be 
added to the ticket. On success of the loadMenu() function, the output is 
“TEST PASSED” and on failure of the test the output will be “TEST FAILED”.  
On success of the addDishtoTicket() function, the output is "The dish is added 
to the ticket!” and on failure of the test the output will be "Not added :(". 