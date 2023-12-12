This is a simple exercise, a simple order manager. You should develop an API where users can create and manage orders. Items can be ordered and orders are automatically fulfilled as soon as the item stock allows it.

Specification

The system should be able to provide the following features:

- create, read, update and delete and list all entities;

- when an order is created, it should try to satisfy it with the current stock.;

- when a stock movement is created, the system should try to attribute it to an order that isn't complete;

- when an order is complete, send a notification by email to the user that created it;

- trace the list of stock movements that were used to complete the order, and vice-versa;

- show current completion of each order;

- Write a log file with: orders completed, stock movements, email sent and errors.

Entities

- Item

            > name

- StockMovement

            > creationDate

            > Item

            > quantity

- Order

            > creationDate

            > Item

            > quantity

            > User (who created the order)

- User

            > name

            > email

Requirements:

- The API should make by java 8 with Spring Boot + Spring JPA or Jave EE + Hibernate, PostgreSQL, GIT, log4j (or other);

- You should provide instructions on how to run the project and how to call the routes;

