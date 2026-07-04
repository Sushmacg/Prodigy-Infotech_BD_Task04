# Postman Setup Guide - Hotel Booking Platform API

## Quick Start

**Base URL:** `http://localhost:8014/api`

**Application Status:** ✅ Running on port 8014

---

## Step 1: Download and Install Postman

1. Go to https://www.postman.com/downloads/
2. Download Postman for Windows
3. Install and open it
4. Create a free account or skip login

---

## Step 2: Create a New Collection

1. Click **"Collections"** on the left sidebar
2. Click **"+"** to create a new collection
3. Name it: **"Hotel Booking Platform"**
4. Click **Create**

---

## Step 3: Setup Environment Variables

1. Click the **"Environments"** tab (gear icon) on the left
2. Click **"Create Environment"**
3. Name it: **"Local Development"**
4. Add these variables:

```
BASE_URL: http://localhost:8014/api
TOKEN: (Leave empty, will be filled after login)
USER_ID: (Leave empty, will be filled after login)
HOTEL_ID: (Leave empty, will be filled after hotel creation)
ROOM_ID: (Leave empty, will be filled after room creation)
BOOKING_ID: (Leave empty, will be filled after booking creation)
```

5. Click **Save**
6. Select this environment from the dropdown in the top-right

---

## Step 4: Test Endpoints

### A. USER REGISTRATION (POST)

**Request:**
```
POST {{BASE_URL}}/auth/register
```

**Headers:**
```
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "age": 30,
  "password": "SecurePass123!"
}
```

**Expected Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

✅ **Copy the token and paste it in the environment variable `TOKEN`**

---

### B. USER LOGIN (POST)

**Request:**
```
POST {{BASE_URL}}/auth/login
```

**Headers:**
```
Content-Type: application/json
```

**Body (JSON):**
```json
{
  "email": "john@example.com",
  "password": "SecurePass123!"
}
```

**Expected Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

## Step 5: Hotel Endpoints Testing

### 1. CREATE HOTEL (POST) ✨

**Request:**
```
POST {{BASE_URL}}/hotels
```

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {{TOKEN}}
```

**Body (JSON):**
```json
{
  "name": "Grand Plaza Hotel",
  "city": "New York",
  "country": "USA",
  "address": "123 Main Street, NY",
  "description": "Luxury 5-star hotel in Manhattan",
  "email": "info@grandplaza.com",
  "phone": "+1-555-0100",
  "starRating": 5.0
}
```

**Expected Response (201 Created):**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "ownerId": "user-uuid",
  "name": "Grand Plaza Hotel",
  "city": "New York",
  "country": "USA",
  "address": "123 Main Street, NY",
  "description": "Luxury 5-star hotel in Manhattan",
  "email": "info@grandplaza.com",
  "phone": "+1-555-0100",
  "starRating": 5.0,
  "createdAt": "2026-07-04T15:30:00",
  "updatedAt": "2026-07-04T15:30:00"
}
```

✅ **Copy the `id` from response and save to environment variable `HOTEL_ID`**

**How to save response to environment:**
1. Open **Tests** tab
2. Add script:
```javascript
var jsonData = pm.response.json();
pm.environment.set("HOTEL_ID", jsonData.id);
```
3. Click Send - it will automatically save the ID

---

### 2. GET ALL HOTELS (GET) 🔓 (No Auth Needed)

**Request:**
```
GET {{BASE_URL}}/hotels
```

**Headers:**
```
Content-Type: application/json
```

**Expected Response (200 OK):**
```json
[
  {
    "id": "hotel-uuid",
    "name": "Grand Plaza Hotel",
    "city": "New York",
    ...
  },
  ...
]
```

---

### 3. GET HOTEL BY ID (GET) 🔓 (No Auth Needed)

**Request:**
```
GET {{BASE_URL}}/hotels/{{HOTEL_ID}}
```

**Expected Response (200 OK):**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Grand Plaza Hotel",
  ...
}
```

---

### 4. SEARCH HOTELS BY CITY (GET) 🔓

**Request:**
```
GET {{BASE_URL}}/hotels/city/New%20York
```

**Expected Response (200 OK):**
```json
[
  {
    "id": "hotel-uuid",
    "name": "Grand Plaza Hotel",
    "city": "New York",
    ...
  }
]
```

---

### 5. SEARCH HOTELS BY COUNTRY (GET) 🔓

**Request:**
```
GET {{BASE_URL}}/hotels/country/USA
```

**Expected Response (200 OK):**
```json
[
  { hotel object }, ...
]
```

---

### 6. GET MY HOTELS (GET) 🔐 (Auth Required)

**Request:**
```
GET {{BASE_URL}}/hotels/my-hotels
```

**Headers:**
```
Authorization: Bearer {{TOKEN}}
```

**Expected Response (200 OK):**
```json
[
  { hotel object created by this user }
]
```

---

### 7. UPDATE HOTEL (PUT) 🔐

**Request:**
```
PUT {{BASE_URL}}/hotels/{{HOTEL_ID}}
```

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {{TOKEN}}
```

**Body (JSON):**
```json
{
  "name": "Grand Plaza Hotel - Deluxe",
  "starRating": 4.5
}
```

**Expected Response (200 OK):**
```json
{
  "id": "hotel-uuid",
  "name": "Grand Plaza Hotel - Deluxe",
  "starRating": 4.5,
  ...
}
```

---

### 8. DELETE HOTEL (DELETE) 🔐

**Request:**
```
DELETE {{BASE_URL}}/hotels/{{HOTEL_ID}}
```

**Headers:**
```
Authorization: Bearer {{TOKEN}}
```

**Expected Response (204 No Content):**
```
(Empty response)
```

---

## Step 6: Room Endpoints Testing

### 1. CREATE ROOM (POST) 🔐

**Request:**
```
POST {{BASE_URL}}/rooms
```

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {{TOKEN}}
```

**Body (JSON):**
```json
{
  "hotelId": "{{HOTEL_ID}}",
  "roomNumber": "101",
  "roomType": "DOUBLE",
  "capacity": 2,
  "basePrice": 150.00,
  "description": "Spacious double room with city view",
  "amenities": "WiFi,AC,TV,Minibar,Safe"
}
```

**Expected Response (201 Created):**
```json
{
  "id": "room-uuid",
  "hotelId": "hotel-uuid",
  "roomNumber": "101",
  "roomType": "DOUBLE",
  "capacity": 2,
  "basePrice": 150.00,
  "isAvailable": true,
  "description": "Spacious double room with city view",
  "amenities": "WiFi,AC,TV,Minibar,Safe",
  "createdAt": "2026-07-04T15:35:00",
  "updatedAt": "2026-07-04T15:35:00"
}
```

✅ **Save the room `id` to environment variable `ROOM_ID`**

**Tests tab script:**
```javascript
var jsonData = pm.response.json();
pm.environment.set("ROOM_ID", jsonData.id);
```

---

### 2. GET ROOM BY ID (GET) 🔓

**Request:**
```
GET {{BASE_URL}}/rooms/{{ROOM_ID}}
```

**Expected Response (200 OK):**
```json
{
  "id": "room-uuid",
  "hotelId": "hotel-uuid",
  "roomNumber": "101",
  ...
}
```

---

### 3. LIST ROOMS BY HOTEL (GET) 🔓

**Request:**
```
GET {{BASE_URL}}/rooms/hotel/{{HOTEL_ID}}
```

**Expected Response (200 OK):**
```json
[
  { room object }, ...
]
```

---

### 4. SEARCH AVAILABLE ROOMS (GET) 🔓

**Request:**
```
GET {{BASE_URL}}/rooms/search?hotelId={{HOTEL_ID}}&checkInDate=2026-07-10&checkOutDate=2026-07-15&capacity=2&maxPrice=200
```

**Query Parameters:**
- `hotelId`: Hotel UUID (required)
- `checkInDate`: YYYY-MM-DD format (required)
- `checkOutDate`: YYYY-MM-DD format (required)
- `capacity`: Minimum capacity (optional)
- `maxPrice`: Maximum price per night (optional)

**Expected Response (200 OK):**
```json
[
  {
    "id": "room-uuid",
    "roomNumber": "101",
    "roomType": "DOUBLE",
    "capacity": 2,
    "basePrice": 150.00,
    ...
  }
]
```

---

### 5. UPDATE ROOM (PUT) 🔐

**Request:**
```
PUT {{BASE_URL}}/rooms/{{ROOM_ID}}
```

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {{TOKEN}}
```

**Body (JSON):**
```json
{
  "roomNumber": "101",
  "roomType": "DOUBLE",
  "capacity": 2,
  "basePrice": 175.00,
  "description": "Updated description"
}
```

**Expected Response (200 OK):**
```json
{
  "id": "room-uuid",
  "basePrice": 175.00,
  ...
}
```

---

### 6. DELETE ROOM (DELETE) 🔐

**Request:**
```
DELETE {{BASE_URL}}/rooms/{{ROOM_ID}}
```

**Headers:**
```
Authorization: Bearer {{TOKEN}}
```

**Expected Response (204 No Content):**

---

## Step 7: Booking Endpoints Testing

### 1. CREATE BOOKING (POST) 🔐

**Request:**
```
POST {{BASE_URL}}/bookings
```

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {{TOKEN}}
```

**Body (JSON):**
```json
{
  "roomId": "{{ROOM_ID}}",
  "checkInDate": "2026-07-10",
  "checkOutDate": "2026-07-15",
  "numberOfGuests": 2,
  "specialRequests": "Late checkout preferred"
}
```

**Expected Response (201 Created):**
```json
{
  "id": "booking-uuid",
  "userId": "user-uuid",
  "roomId": "room-uuid",
  "hotelId": "hotel-uuid",
  "checkInDate": "2026-07-10",
  "checkOutDate": "2026-07-15",
  "numberOfGuests": 2,
  "totalPrice": 750.00,
  "status": "CONFIRMED",
  "specialRequests": "Late checkout preferred",
  "createdAt": "2026-07-04T15:40:00",
  "updatedAt": "2026-07-04T15:40:00",
  "cancelledAt": null
}
```

✅ **Save booking `id` to environment variable `BOOKING_ID`**

---

### 2. GET BOOKING BY ID (GET) 🔐

**Request:**
```
GET {{BASE_URL}}/bookings/{{BOOKING_ID}}
```

**Headers:**
```
Authorization: Bearer {{TOKEN}}
```

**Expected Response (200 OK):**
```json
{
  "id": "booking-uuid",
  "userId": "user-uuid",
  ...
}
```

---

### 3. GET MY BOOKINGS (GET) 🔐

**Request:**
```
GET {{BASE_URL}}/bookings/my-bookings
```

**Headers:**
```
Authorization: Bearer {{TOKEN}}
```

**Expected Response (200 OK):**
```json
[
  { booking object created by this user }
]
```

---

### 4. GET HOTEL BOOKINGS (GET) 🔐

**Request:**
```
GET {{BASE_URL}}/bookings/hotel/{{HOTEL_ID}}
```

**Headers:**
```
Authorization: Bearer {{TOKEN}}
```

**Expected Response (200 OK):**
```json
[
  { booking object for this hotel }
]
```

---

### 5. GET ROOM BOOKINGS (GET) 🔐

**Request:**
```
GET {{BASE_URL}}/bookings/room/{{ROOM_ID}}
```

**Headers:**
```
Authorization: Bearer {{TOKEN}}
```

**Expected Response (200 OK):**
```json
[
  { booking object for this room }
]
```

---

### 6. CHECK AVAILABILITY (GET) 🔓

**Request:**
```
GET {{BASE_URL}}/bookings/availability/check?roomId={{ROOM_ID}}&checkInDate=2026-07-10&checkOutDate=2026-07-15
```

**Expected Response (200 OK):**
```json
false
```
(false because we just booked those dates)

---

### 7. CANCEL BOOKING (PUT) 🔐

**Request:**
```
PUT {{BASE_URL}}/bookings/{{BOOKING_ID}}/cancel
```

**Headers:**
```
Content-Type: application/json
Authorization: Bearer {{TOKEN}}
```

**Body (JSON):**
```json
{}
```

**Expected Response (200 OK):**
```json
{
  "id": "booking-uuid",
  "status": "CANCELLED",
  "cancelledAt": "2026-07-04T15:45:00",
  ...
}
```

---

## Complete Test Sequence

Follow this order for best results:

1. ✅ **POST /auth/register** - Create user & get token
2. ✅ **POST /hotels** - Create hotel & save hotel ID
3. ✅ **POST /rooms** - Create room & save room ID
4. ✅ **GET /rooms/search** - Search available rooms
5. ✅ **POST /bookings** - Create booking & save booking ID
6. ✅ **GET /bookings/availability/check** - Check availability
7. ✅ **GET /hotels** - View all hotels
8. ✅ **GET /hotels/{id}** - View specific hotel
9. ✅ **GET /rooms/{id}** - View specific room
10. ✅ **GET /bookings/my-bookings** - View my bookings
11. ✅ **PUT /bookings/{id}/cancel** - Cancel booking
12. ✅ **PUT /hotels/{id}** - Update hotel
13. ✅ **DELETE /hotels/{id}** - Delete hotel

---

## Error Codes & Solutions

| Status | Error | Solution |
|--------|-------|----------|
| **401** | Unauthorized | Missing or invalid JWT token in Authorization header |
| **403** | Forbidden | You don't own this resource (only owner/admin can edit) |
| **404** | Not Found | Resource doesn't exist (hotel/room/booking not found) |
| **409** | Conflict | Room not available for requested dates |
| **400** | Bad Request | Invalid input (check date format, capacity, prices) |

**Example 401 Error:**
```json
{
  "status": 401,
  "message": "Unauthorized",
  "description": "Invalid JWT token"
}
```

**Solution:** Make sure Authorization header has format:
```
Authorization: Bearer <your_token_here>
```

---

## Tips for Postman Testing

### 1. Save Response Values Automatically
Add this to the **Tests** tab after each request:

```javascript
// Save hotel ID
var jsonData = pm.response.json();
pm.environment.set("HOTEL_ID", jsonData.id);
```

### 2. Format JSON Response
After getting a response, click **"Pretty"** button to format JSON nicely.

### 3. Check Status Code
Look for green checkmark on status code. Expect:
- **201** for POST (create)
- **200** for GET (read) & PUT (update)
- **204** for DELETE

### 4. Common Issues & Fixes

**Problem:** Getting 404 for hotel
- **Fix:** Make sure HOTEL_ID environment variable is set correctly

**Problem:** Getting 403 Forbidden
- **Fix:** Verify you're using the correct TOKEN (from the same user who created the resource)

**Problem:** Getting 409 Room not available
- **Fix:** Try different dates (book in future) or create another room

**Problem:** Getting 400 Invalid date
- **Fix:** Use format YYYY-MM-DD and ensure checkout > check-in

---

## Advanced: Import Collection

If you want to use a pre-made collection:

1. **Option A - Manual:** Follow all endpoints above one by one
2. **Option B - Import:** Create a collection JSON file with all endpoints

**To create collection file:** Save this as `hotel-booking-collection.json`:

```json
{
  "info": {
    "name": "Hotel Booking Platform",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Auth",
      "item": [
        {
          "name": "Register",
          "request": {
            "method": "POST",
            "url": "{{BASE_URL}}/auth/register",
            "header": ["Content-Type: application/json"],
            "body": {
              "mode": "raw",
              "raw": "{\"name\":\"John Doe\",\"email\":\"john@example.com\",\"age\":30,\"password\":\"SecurePass123!\"}"
            }
          }
        }
      ]
    }
  ]
}
```

Then in Postman:
1. Click **"Collections"**
2. Click **"Import"**
3. Select the JSON file
4. Click **Import**

---

## Verification Checklist

After testing all endpoints, verify:

- ✅ User registration works (201)
- ✅ User login returns token (200)
- ✅ Create hotel works (201)
- ✅ Get all hotels works (200)
- ✅ Search hotels works (200)
- ✅ Create room works (201)
- ✅ Search available rooms works (200)
- ✅ Create booking works (201)
- ✅ Check availability works (200)
- ✅ Update hotel works (200)
- ✅ Update room works (200)
- ✅ Cancel booking works (200)
- ✅ Delete room works (204)
- ✅ Delete hotel works (204)

**All tests passing?** 🎉 Your API is fully functional!

---

## Troubleshooting

### Application Not Running?

```bash
# Check if on port 8014
netstat -ano | findstr :8014

# If nothing shows, restart the application:
cd "c:\Users\saisr\Projects\ProdigyInfotech_BD_Task03\users-api (4)\users-api"
java -jar target/users-api-1.0.0.jar
```

### Getting Connection Refused?

**Error:** `Connection refused` or `Failed to connect to localhost:8014`

**Solutions:**
1. Check if app is running: `netstat -ano | findstr :8014`
2. Restart the application
3. Check firewall isn't blocking port 8014
4. Try a different port: `set SERVER_PORT=8080` then restart

### Database Issues?

The app uses H2 in-memory database, so:
- Data resets when app restarts
- No need to install MySQL for testing
- Perfect for development

---

## What's Next?

After verifying all endpoints in Postman:

1. ✅ Run the Python test suite: `python test_api.py`
2. ✅ Check database: H2 console available at `/h2-console` (optional)
3. ✅ Review logs: Check console output for any warnings
4. ✅ Deploy to production (optional): Configure MySQL in `application-prod.properties`

---

**Status: ✅ Ready for Postman Testing!**

**API Base URL:** `http://localhost:8014/api`  
**Java Version:** 17  
**Spring Boot Version:** 3.3.0  
**Database:** H2 (in-memory)
