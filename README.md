# Getting Started

### Tech:
- Java/Kotlin
- Spring boot
- Flyway 
- Gradle
- H2 in memory database for simplicity

### Design
- Entities
  - `Reservation`: Holds the properties of each trip's reservation by customer including spots
  - `Reservation-Spot`: Spots associated to each reservation
  - `Trip`: Holds the properties of trips
  - `Spot`: Holds the properties of trips' spots
  - `Customer`: Holds user/customer data

The Design is quite simple, each customer tries to book a trip with a number of spots. The application tries to reserve random spots if there are any available spots for the trip. 

There is an atomic counter which holds the number of available spots for each trip. The counter decrement and increment in case of reservation and cancellation.

PESSIMISTIC_LOCK is applied at database level in order to avoid double booking in case of concurrent access to the same spots.

### API

- Create Reservation: 

**Note**: Passing customerId in the payload for simplicity due to lack of authorization.
```
  POST /reservation
  payload:
  {
    "numberOfSpots" :5,
    "customerId" : "16a2ae47-6b8e-4c70-8439-710eab527ea3",
    "tripId" : "1b4c80b2-ffc6-45bb-bf00-29afc74fc29d"
  }
```

- Cancel Reservation
```
POST /reservation/{reservationId}/cancel
```

- Admin API to get the list of reservation
```
GET /admin/reservation?customerId=16a2ae47-6b8e-4c70-8439-710eab527ea3&page=0&size=10

Response:
[
    {
        "reservationId": "f5de2f36-4e8a-4949-b872-51c1741d2f60",
        "bookingId": "de46fc1e-317f-4a63-ae9b-64099f309131",
        "customerId": "16a2ae47-6b8e-4c70-8439-710eab527ea3",
        "status": "CONFIRMED",
        "spotIds": [
            {
                "spotId": "f413da57-348f-483f-9afb-951981395f48",
                "status": "CONFIRMED"
            },
            {
                "spotId": "97483871-0710-4638-94df-fd9072e2e1da",
                "status": "CONFIRMED"
            }
        ]
    }
]

```
### Run the APP

**Note**: Restarting the app will remove all the existing data.

The following command will run the application server listening to port 8080

```
./gradlew bootRun
```



