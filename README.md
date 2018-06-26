# Spring Food Delivery App

This is a food delivery application using Micro-services architecture and Spring Cloud.

## Operating Requirements
- **Docker** Provides docker images for application build up.
- **MongoDB** Provides application database.
- **Eureka** Micro-services registration and discovery.
- **RabbitMQ** Decouple micro-services.
- **WebSocket** Sends message to UI.
- **Hal Browser** Quick repository exploration.
- **Lombok** Fast build getter/setter and constructors implementation.
- **Hystrix** Enables automated batching of requests into a single HystrixCommand instance execution.


## Functions

- User can search a restaurant based on restaurant name.
- User can order food by choosing different menu item, quantity and add a note about his/her diet restrictions and etc.
- User can also fills in the delivery address.
- After user places an order, the order should contain food items user ordered, quantity, price and order time.
- User then needs to pay for his/her order by providing credit card number, expiration date, and security code.
- After payment is made successfully, it should return payment ID, timestamp and then the order is considered as completed so the user can see the estimated delivery time.

FoodOrderingSystemDesign
This spring cloud application is built on mic-services. In this application consists of six modules.

# Ready to Begin

## Step 1 - Create a directory folder and download files from github

Use the following cmd in terminal to create the requests files. Here use examples "test".
```
mkdir test
cd test
git clone https://github.com/zzhou9/food-delivery-app.git

```

## Step 2 - Create fat jar

Use the terminal before to enter the following cmd.

```
cd food-delivery-app
mvn clean install
```

## Step 3 - Use Docker to build images

Use the terminal before to enter the following cmd.

```
 docker-compose up
```
## Step 4 - Running Infrastructure Servers

Run the following codes. Each line in a new terminal.
```
sh ./start-eureka.sh
sh ./hystrix.sh
```
Wait couple seconds, then we have already started Eureka and Hystrix.

## Step 5 - Start Mic-services

Run the following codes. Each line in a new terminal.
```
sh ./restaurant-service.sh
sh ./order-service.sh
sh ./payment-distribution.sh
sh ./payment-service.sh
sh ./order-complete-updater.sh
```
Wait couple seconds, then we have already started all the mic-services we need.

### Step 6 - Open Browser
Open any browser and paste the following codes. Each line in a new tab.
```
http://localhost:7979
http://localhost:15672
http://localhost:8761
http://localhost:8001/browser/index.html
```
- First URL is for Hystrix.
- Second URL is for RabbitMQ.
- Third URL is for Eureka.
- Fourth URL is HAL Browser. 8001 could be changed to any service port at any time.

In the Hystrix tab, copy and paste the following URL into the search bar. We start to monitor payment service.
```
http://localhost:8004/hystrix.stream
```

### Step 7 - Upload data & Perform test

Open Postman, copy and paste to send those following test cases.

| URL |  Http Method     | File Name     |
| :------------- | :------------- | :------------- | 
| localhost:8001/api/restaurants/bulk/menuItems       | POST       | Menus.json    | 
| localhost:8002/api/restaurants/1/orders      | POST       | OrderTestCase.json  |
| localhost:8003/api/payments       | POST       | PaymentTestCase.json      |

The first POST, we just upload some restaurants and menus information.

The second POST, we simulate an order has been made. You will see the bottom part of Postman has feedback, which include total price etc.

The third POST, we simulate an payment has been submitted. You can see the data flowing in RabbitMQ in 'Queues" tab under payments section.
  
Meanwhile, you can check Hystrix window, the data flowing.

All those POSTs methods are repeatable, you can send as many times as you want. 

### Step 8 - Finally but not Least

In the order-complete-update terminal, we shut down this service by pressing Ctrl + C. Send some payments again, meanwhile, take a look at Hystrix window, you will notice the error rate goes to 100%. And the circuit status is open.

