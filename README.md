# ToDo Tracker
A simple to-do tracking application with an Angular frontend and a Java (Spring Boot + Hibernate) API backend, with MySQL database.

## API ([backend](/backend/README.md))
This provides the data to the frontend for the todo entries. It is a REST API (no webpages rendered) and can be used and tested via Postman or `curl`. The frontend will call the API in a similar way to Postman/`curl` to get the todos to show on the page, and to create, update, or delete todos. It interacts with the MySQL database via Hibernate & JDBC.

## Frontend ([frontend](/frontend/README.md))
The frontend provides the website for viewing, creating, updating, and deleting todo entries. This will be built with Angular, a common framework used on clients. It communicates with the API in a similar fashion to Postman/`curl`, via HTTP verbs (GET/POST/PUT/DELETE) and URLs.

## Database
ToDo Tracker uses a MySQL database hosted in the AWS RDS cloud, instead of a locally hosted database; this is similar to how you would encounter databases on a customer project. The URL and credentials for this database will be provided individually, with each user receiving their own schema on the database to use as they see fit for their copy of the application.
