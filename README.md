# train-spring-boot
# High level Requirements

I want to board a train from London to Paris. The train ticket will cost $5.
Create an API where you can submit a purchase for a ticket. Details included in the receipt are:
	a) From, To, User , price paid.
	(i) User should include first and last name, email address
  (ii) The user is allocated a seat in the train. 
		Assume the train has only 2 sections, section A and section B.
An API that shows the details of the receipt for the user
An API that lets you view the users and seat they are allocated by the requested section
An API to remove a user from the train
An API to modify a user's seat

# Database design
The API's are built with in memory H2 database which gets created and initialized on startup. Refer to schema.sql & data.sql under /resources folder for the details of the table design. Following are the tables created.
 - train : table to list all the available trains
 - section : table to configure the sections/compartments in the train - assumed to be 'A' & 'B'
 - fare : table to configure fare for each train and section
 - ticket : table to register the sales of a ticket through a Ticker receipt. Contains basic passenger details , train, fare, seat details
 - seat : table to register the seats allocated to passengers on sale of a ticket
# API design
Following API's are created
 - /api/train/ticket [POST] - API to create a Ticket and seat. Allocates available seat for the requested section
 - /api/train/ticket/{id} [GET] - API to retrieve a ticket with allocated seat
 - /api/train/ticket/{id} [DELETE] - API to cancel a ticket. seat table record will be deleted, whereas ticket will be marked as cancelled
 - /api/train/ticket/{id} [PUT] - API to change seat number against a ticket
 - /api/train/section-booking - API to retrieve all seats booked in a section on a train
# Test covered
Refer to attached RestAssuredIntegrationTests.html for some sample integration tests done on the API. It is a integration test report can be viewed in browser (created using a separate project using Rest ASsured/ TestnG /Extend reports)
A few unit tests have been written for the APIs
