# FoodDelivery-backend_2
Java springBoot, Apache Kafka and Websocket for food-delivery project backend

# Food Delivery Project
The project aims to simulate the whole process of food delivered from the time when customer makes the order, then the restaurant confirms and cooks the order, the order is finally delivery by the courier to the customer's destination. The project focuses on the order delivery tracking in real-time, so all customer, restaurant, courier will be informed of the order's status whenever it's status get updated by other entities. The project includes three separate mobile apps for couriers, customers, and restaurants.  

The Food delivery project includes 5 repositories and was developed by Quan Doan and Hajri Mohamed.
- Backend-V2 repository: Hajri Mohamed
- Courier mobile: Hajri Mohamed
- Customer mobile: Quan Doan 
- Restaurant mobile: Quan Doan
- Backend repository: Quan Doan
# Features 
- Login and register user
- update user's profile and password
### for the restaurant
- the user can register a new restaurant and update the restaurant
- the user can add or update dishes in the menu of the restaurant
- the restaurant can accept or decline orders whenever they are sent to his/her restaurant from the customer
- the restaurant confirms the order as being ready for pick-up after finishing the order cooking
- the restaurant can track the order status within the stages when the courier picked up the order and drop off the order at customer home.
- the restaurant can view list of in-progress and completed orders.
### for the customer
- the app get user's current location whenever they sign in or sign up into the app
- user gets a list of restaurant based on the user's current location.
- users can change the location on mobile phone to get the new list of restaurants
- users can make a new order or re-order from their previous orders. The orders of the customer are only allowed to be made within 20 km from the restaurant to the customer location.
- users can track the real-time order status and location on map whenever the order's restaurant and courier update it.
- users can view list or their previous orders
### for the courier
- the app can access the current location of the courier, the couriers can view their real-time locaiton on the app's map whenever they are commuting.
- the user user can turn courier's status to online/offline
- when the courier is online, the courier will received a new order automatically if the order is close to the courier, if there is no active order, the courier will wait till having a new order.
- the courier has a right to accept or decline the order
- the courier cannot pick up the order if the courier is 200 meters far away from the restaurant.
- the courier cannot drop off the order for the customer if the courier is 200 meters far away from the restaurant
- the courier update the order's location in real-time during the delivery for the customer from the time of order pickup till the time of order drop off


# Technologies for the project
- SpringBoot, Spring Security, Maven
- WebSocket
- Apache Kafka
- Docker
- PostgresSQL
- React-Native
- Typescript
- Tailwind CSS

# Technologies for this repository
- SpringBoot, Spring Security, Maven
- WebSocket
- Docker
- PostgresSQL

