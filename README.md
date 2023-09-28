## About

This is a simple order manager implementation that utilizes the 
following technologies:

* Java 8
* Spring Boot
* Spring Data
* Logback
* PostgreSQL
* Flyway

---

## Getting Started
to build this project, simple run:

### in Linux:
> ./gradlew build && docker-composer up -d

to run it inside a docker container or run 

> ./run-app.sh

to run jar directly.

### in Windows:
> ./gradlew build; docker composer up -d

to run it inside a docker container or run

> ./run-app.bat

to run jar directly.

**In both cases** remember to configure `application.properties` as needed.

---

## Creating and Supplying Orders

### Creating orders
To create an order, first of all we will need a user, and an item to be linked
to the order. To create the order, just send a POST request to /api/order with the
following JSON

```json
{
  "userId": 1,
  "itemSku": "PA001",
  "quantity": 10
}
``` 

in case of success, the following result will be shown

```json
{
    "uuid": "4b2a66e4-86d5-43f7-8c11-cf895b5aa54b",
    "item": {
        "id": 1,
        "name": "Product A",
        "sku": "PA001"
    },
    "user": {
        "id": 1
    },
    "quantity": 10,
    "supplied": 0,
    "status": "PENDING",
    "creationDate": "2023-09-26T11:49:43.046+00:00",
    "stockMovements": null
}
```

If there's enough stock to supply the order, it will be immediately COMPLETED. Otherwise, 
it will be queued for supply after a stock movement is created.

### Creating Stock Movements

A stock movement adds a specific amount of an item to the stock. Send a POST request to
/api/stockMovement with this JSON:

```json
{
    "itemSku": "PA001",
    "quantity": 20
}
```

As we previously created an order that was queued to be supplied, in case of success, the
following JSON will be returned

```json
{
    "id": 1,
    "item": {
        "id": 1,
        "name": "Product A",
        "sku": "PA001"
    },
    "quantity": 10,
    "creationDate": "2023-09-26T12:13:46.440+00:00"
}
```

As can you observe, the quantity that went to the stock is only 10, as the previous created order
was 10.

Afterward, if we check the order completion endpoint, doing a GET request to /api/order/{id}/completion
we will obtain the info about order completion

``
/api/order/4b2a66e4-86d5-43f7-8c11-cf895b5aa54b/completion
``
will result
> Order is complete.

### Creating users
There are few users already created. Just check it at /api/users. 
You can change an existing user ID, or create a new one by sendind a 
<code>POST</code> request to `/api/user` containing the following JSON:

```json
 {
   "name": "USER_NAME",
   "email": "USER_EMAIL"
 }
```

in case of success, the response should be the same as 
```json
{
  "id": "USER_ID",
  "name": "USER_NAME",
  "email": "USER_EMAIL"
}
```

### Creating Items
As users do, there are already few items created, so you can use an existing item,
or create a new one. The same creation process will be used to item creation, just send a POST request to 
/api/item with the following JSON

```json
{
  "name": "ITEM_NAME",
  "sku": "ITEM_SKU_CODE"
}
```

---
## Routes

To explore available API routes, access the Swagger-generated API documentation at: 

> http://server_ip:port/api/api-docs

Replace server_ip and port with the appropriate values for your setup.
