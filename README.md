# :::Interview Test::: 
# The CRUD APIs and Spring Boot 3.0 Security with JWT Implementation



## Features
* User registration and login for get JWT token to authentication
* We have 5 api:
  * /api/customers           - get all customers
  * /api/customers/{id}    - get specific customer by id
  * /api/customers      - create customer
  * /api/customers/{id}    - update customer details by id
  * /api/customers/{id}         - delete customer by id
* Unit test


## Technologies
* Java 17
* Spring Boot
* Spring Security
* JSON Web Tokens (JWT)
* Maven
 
## How to getting started
To build and run the project, follow these steps:

* Clone the repository: `git clone https://github.com/ChinnawatPOK/CRUD-Security-Spring-boot-project.git`
* Build the project: mvn clean install
* Run the project: mvn spring-boot:run
-> The application will be available at http://localhost:8080.

# Please follow the 7 steps to call each APIs ^_^
> 1. You need to register with cUrl below:
```
curl --location 'localhost:8080/api/auth/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstname": "Chinnawat",
    "lastname": "Test",
    "email": "test_1998@hotmail.com",
    "password": "12345"
}'
```

![img.png](img.png)

>2. After success register you can Login (Login with email & password from step1 !!) with cUrl below:
```curl --location 'localhost:8080/api/auth/authenticate' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "test_1998@hotmail.com",
    "password": "12345"
}'
```
![img_1.png](img_1.png)

>3. Call api create new customer
* ** Don't forget take access_token first (from step2 response add to header Baerer xxx).
```curl --location 'localhost:8080/api/customers' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0XzE5OThAaG90bWFpbC5jb20iLCJpYXQiOjE3MTg2OTk1NTAsImV4cCI6MTcxODc4NTk1MH0.yw60XUkpMSqrfzuXabhgWWfxOtpoTK39bqX0L3xZUT8' \
--data '{
    "full_name": "Chinnawat",
    "last_name": "Kaewchim",
    "age": 25,
    "phone": "08111111111"
}'
```
![img_2.png](img_2.png)

> 4. Call api get all customer
* ** This api will show list of customer in H2 database (table: customer)
* ** Don't forget take access_token first (from step2 response add to header Baerer xxx).
```
curl --location 'localhost:8080/api/customers' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0XzE5OThAaG90bWFpbC5jb20iLCJpYXQiOjE3MTg2OTk1NTAsImV4cCI6MTcxODc4NTk1MH0.yw60XUkpMSqrfzuXabhgWWfxOtpoTK39bqX0L3xZUT8'
```
![img_3.png](img_3.png)

> 5. Call api get customer by id
* ** This api will show customer follow as id that send in path params
* ** Don't forget take access_token first (from step2 response add to header Baerer xxx).
```curl --location 'localhost:8080/api/customers/1' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0XzE5OThAaG90bWFpbC5jb20iLCJpYXQiOjE3MTg2OTk1NTAsImV4cCI6MTcxODc4NTk1MH0.yw60XUkpMSqrfzuXabhgWWfxOtpoTK39bqX0L3xZUT8'
```
![img_4.png](img_4.png)
* *But if you send id that doesn't exist in the database service will response Data not found as below picture:
![img_5.png](img_5.png)

> 6. Call api update customer
* ** This api will update customer id that send as path params with request body
* ** Don't forget take access_token first (from step2 response add to header Baerer xxx).
```
curl --location --request PUT 'localhost:8080/api/customers/1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0XzE5OThAaG90bWFpbC5jb20iLCJpYXQiOjE3MTg2OTk1NTAsImV4cCI6MTcxODc4NTk1MH0.yw60XUkpMSqrfzuXabhgWWfxOtpoTK39bqX0L3xZUT8' \
--data '{
    "full_name": "Mana",
    "last_name": "Pakdee",
    "age": 44,
    "phone": "0999999999"
}'
```
![img_6.png](img_6.png)
After update you can try to get customer id 1 again, The data was updated.
![img_7.png](img_7.png)

> 7. Call api delete customer
* ** This api will remove customer id that send as path params in the database
* ** Don't forget take access_token first (from step2 response add to header Baerer xxx).
```
curl --location --request DELETE 'localhost:8080/api/customers/1' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0XzE5OThAaG90bWFpbC5jb20iLCJpYXQiOjE3MTg2OTk1NTAsImV4cCI6MTcxODc4NTk1MH0.yw60XUkpMSqrfzuXabhgWWfxOtpoTK39bqX0L3xZUT8'
```
![img_8.png](img_8.png)
After that tou can try to call api get customer id 1 again, The service return data not found because customerId 1 was deleted from above step.
![img_9.png](img_9.png)

# ! If you don't add Bearer token in each /api/customer/xxx. You will got 403 Forbidden (Because I allow spring security for these APIs.).
![img_10.png](img_10.png)


