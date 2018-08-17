# ZendeskTest

This project contains the coding assignment for Zendesk. The tests have been written using following tools and frameworks:

1. Eclipse IDE
2. TestNG testing framework
3. Rest Assured for REST API framework
4. Maven for dependencies

To run the tests:

Assumptions: You already have Eclipse IDE with TestNG and Maven integration (m2e) plugins installed in it.

1. Un-zip the project file I have emailed.
2. Import the project as a Maven -> Existing Maven project in Eclipse selecting the un-zipped project folder as root directory.
3. Once the project is imported, right-click the testng.xml file in the project folder, select Run As -> TestNG Suite

It will execute below four tests from the suite

1. Verify you are able to create a ticket
2. Verify you are able to add a comment to the ticket
3. Verify you are able to list all tickets for your Zendesk
4. Verify you are able to delete a ticket
 

You should see something like below in the console:

===============================================
ZendeskTest
Total tests run: 4, Failures: 0, Skips: 0
===============================================
