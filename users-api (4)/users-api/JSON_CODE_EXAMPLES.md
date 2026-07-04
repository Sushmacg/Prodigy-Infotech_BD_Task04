# 📝 COMPLETE JSON CODE EXAMPLES FOR TESTING

**API Base URL:** `http://localhost:8014/api`

---

## 🔐 STEP 1: REGISTER USER (POST)

**Endpoint:** `POST http://localhost:8014/api/auth/register`

**Request Body (JSON):**
```json
{
  "name": "Postman User",
  "email": "postman23@example.com",
  "password": "Password123",
  "age": 25
}
```

**Headers:**
```
Content-Type: application/json
```

**Expected Response (Status 200):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI2N2I4ZDk4ZC04YjQ2LTQxY2QtODdmMS1mYjQ4ZGY4ZjI4YjMiLCJpYXQiOjE2ODk2NTk1NDF9.2xV_7qE...",
  "message": "User registered successfully"
}
```

**⚠️ SAVE THIS TOKEN** - You'll use it for all future requests!
Set environment variable: `TOKEN = <copied_token_here>`

---

## 🔑 STEP 2: LOGIN USER (POST)

**Endpoint:** `POST http://localhost:8014/api/auth/login`

**Request Body (JSON):**
```json
{
  "email": "postman23@example.com",
  "password": "Password123"
}
```

**Headers:**
```
Content-Type: application/json
```

**Expected Response (Status 200):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "message": "Login successful"
}
```

---

## 🏨 STEP 3: CREATE HOTEL (POST)

**Endpoint:** `POST http://localhost:8014/api/hotels`

**Request Headers:**
```
Content-Type: application/json
Authorization: Bearer {{TOKEN}}
```

**Request Body (JSON):**
```json
{
  "name": "Grand Plaza Hotel",
  "city": "New York",
  "country": "USA",
  "address": "123 Main Street, Manhattan, NY 10001",
  "description": "Luxury 5-star hotel in the heart of Manhattan with world-class amenities",
  "email": "info@grandplaza.com",
  "phone": "+1-555-0100",
  "starRating": 5.0
}
```

**Expected Response (Status 201):**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Grand Plaza Hotel",
  "city": "New York",
  "country": "USA",
  "address": "123 Main Street, Manhattan, NY 10001",
  "description": "Luxury 5-star hotel in the heart of Manhattan with world-class amenities",
  "email": "info@grandplaza.com",
  "phone": "+1-555-0100",
  "starRating": 5.0,
  "ownerId": "67b8d98d-8b46-41cd-87f1-fb48df8f28b3",
  "createdAt": "2026-07-04T15:59:05",
  "updatedAt": "2026-07-04T15:59:05"
}
```

**⚠️ SAVE THIS HOTEL ID** - You'll need it for creating rooms!
Set environment variable: `HOTEL_ID = <copied_id_here>`

---

## 🛏️ STEP 4: CREATE ROOM (POST)

**Endpoint:** `POST http://localhost:8014/api/rooms`

**Request Headers:**
```
Content-Type: application/json
Authorization: Bearer {{TOKEN}}
```

**Request Body (JSON):**
```json
{
  "hotelId": "550e8400-e29b-41d4-a716-446655440000",
  "roomNumber": "101",
  "roomType": "DOUBLE",
  "capacity": 2,
  "basePrice": 150.00,
  "description": "Double room with city view and premium amenities",
  "amenities": "WiFi,AC,TV,Minibar,Safe,Bathrobe,Slippers"
}
```

**Note:** Replace `hotelId` with your actual `{{HOTEL_ID}}`

**Expected Response (Status 201):**
```json
{
  "id": "660e8400-e29b-41d4-a716-446655440111",
  "hotelId": "550e8400-e29b-41d4-a716-446655440000",
  "roomNumber": "101",
  "roomType": "DOUBLE",
  "capacity": 2,
  "basePrice": 150.00,
  "isAvailable": true,
  "description": "Double room with city view and premium amenities",
  "amenities": "WiFi,AC,TV,Minibar,Safe,Bathrobe,Slippers",
  "createdAt": "2026-07-04T16:00:15",
  "updatedAt": "2026-07-04T16:00:15"
}
```

**⚠️ SAVE THIS ROOM ID** - You'll need it for bookings!
Set environment variable: `ROOM_ID = <copied_id_here>`

---

## 🔍 STEP 5: SEARCH AVAILABLE ROOMS (GET)

**Endpoint:** `GET http://localhost:8014/api/rooms/search?hotelId=550e8400-e29b-41d4-a716-446655440000&checkInDate=2026-07-10&checkOutDate=2026-07-15&capacity=2&maxPrice=200`

**Request Headers:**
```
(No auth needed - Public endpoint)
```

**Query Parameters:**
```
hotelId = 550e8400-e29b-41d4-a716-446655440000
checkInDate = 2026-07-10
checkOutDate = 2026-07-15
capacity = 2
maxPrice = 200
```

**Alternative - Using Environment Variables:**
```
GET {{BASE_URL}}/rooms/search?hotelId={{HOTEL_ID}}&checkInDate=2026-07-10&checkOutDate=2026-07-15&capacity=2&maxPrice=200
```

**Expected Response (Status 200):**
```json
[
  {
    "id": "660e8400-e29b-41d4-a716-446655440111",
    "hotelId": "550e8400-e29b-41d4-a716-446655440000",
    "roomNumber": "101",
    "roomType": "DOUBLE",
    "capacity": 2,
    "basePrice": 150.00,
    "isAvailable": true,
    "description": "Double room with city view and premium amenities",
    "amenities": "WiFi,AC,TV,Minibar,Safe,Bathrobe,Slippers"
  },
  {
    "id": "770e8400-e29b-41d4-a716-446655440222",
    "hotelId": "550e8400-e29b-41d4-a716-446655440000",
    "roomNumber": "102",
    "roomType": "DOUBLE",
    "capacity": 2,
    "basePrice": 175.00,
    "isAvailable": true,
    "description": "Deluxe double room with balcony",
    "amenities": "WiFi,AC,TV,Minibar,Safe,Balcony,Jacuzzi"
  }
]
```

---

## 📅 STEP 6: CREATE BOOKING (POST)

**Endpoint:** `POST http://localhost:8014/api/bookings`

**Request Headers:**
```
Content-Type: application/json
Authorization: Bearer {{TOKEN}}
```

**Request Body (JSON):**
```json
{
  "roomId": "660e8400-e29b-41d4-a716-446655440111",
  "checkInDate": "2026-07-10",
  "checkOutDate": "2026-07-15",
  "numberOfGuests": 2,
  "specialRequests": "Late checkout preferred, high floor preferred"
}
```

**Note:** 
- `roomId`: Replace with your `{{ROOM_ID}}`
- `checkOutDate` must be AFTER `checkInDate`
- Date format: YYYY-MM-DD

**Expected Response (Status 201):**
```json
{
  "id": "880e8400-e29b-41d4-a716-446655440333",
  "userId": "67b8d98d-8b46-41cd-87f1-fb48df8f28b3",
  "roomId": "660e8400-e29b-41d4-a716-446655440111",
  "hotelId": "550e8400-e29b-41d4-a716-446655440000",
  "checkInDate": "2026-07-10",
  "checkOutDate": "2026-07-15",
  "numberOfGuests": 2,
  "totalPrice": 750.00,
  "status": "CONFIRMED",
  "specialRequests": "Late checkout preferred, high floor preferred",
  "createdAt": "2026-07-04T16:02:30",
  "updatedAt": "2026-07-04T16:02:30",
  "cancelledAt": null
}
```

**Formula:** `totalPrice = basePrice × (checkOutDate - checkInDate).days`
- Example: 150 × (5 nights) = 750.00

**⚠️ SAVE THIS BOOKING ID**
Set environment variable: `BOOKING_ID = <copied_id_here>`

---

## ✅ STEP 7: CHECK ROOM AVAILABILITY (GET)

**Endpoint:** `GET http://localhost:8014/api/bookings/availability/check?roomId=660e8400-e29b-41d4-a716-446655440111&checkInDate=2026-07-10&checkOutDate=2026-07-15`

**Request Headers:**
```
(No auth needed - Public endpoint)
```

**Query Parameters:**
```
roomId = 660e8400-e29b-41d4-a716-446655440111
checkInDate = 2026-07-10
checkOutDate = 2026-07-15
```

**Alternative - Using Environment Variables:**
```
GET {{BASE_URL}}/bookings/availability/check?roomId={{ROOM_ID}}&checkInDate=2026-07-10&checkOutDate=2026-07-15
```

**Expected Response (Status 200):**
```json
false
```

**Note:** Returns `false` because the room is already booked for those dates

**If room is available, returns:**
```json
true
```

---

## 📋 STEP 8: GET ALL HOTELS (GET)

**Endpoint:** `GET http://localhost:8014/api/hotels`

**Request Headers:**
```
(No auth needed - Public endpoint)
```

**Expected Response (Status 200):**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "name": "Grand Plaza Hotel",
    "city": "New York",
    "country": "USA",
    "address": "123 Main Street, Manhattan, NY 10001",
    "description": "Luxury 5-star hotel in the heart of Manhattan with world-class amenities",
    "email": "info@grandplaza.com",
    "phone": "+1-555-0100",
    "starRating": 5.0,
    "ownerId": "67b8d98d-8b46-41cd-87f1-fb48df8f28b3"
  },
  {
    "id": "990e8400-e29b-41d4-a716-446655440444",
    "name": "Sunset Hotel",
    "city": "Los Angeles",
    "country": "USA",
    "address": "456 Sunset Boulevard, LA",
    "description": "Beautiful 4-star beachfront hotel",
    "email": "info@sunset.com",
    "phone": "+1-555-0200",
    "starRating": 4.0,
    "ownerId": "78c9e09e-9c57-52de-98g2-gc59eg9g39c4"
  }
]
```

---

## 🏨 STEP 9: GET SINGLE HOTEL (GET)

**Endpoint:** `GET http://localhost:8014/api/hotels/550e8400-e29b-41d4-a716-446655440000`

**Alternative - Using Environment Variable:**
```
GET {{BASE_URL}}/hotels/{{HOTEL_ID}}
```

**Request Headers:**
```
(No auth needed - Public endpoint)
```

**Expected Response (Status 200):**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Grand Plaza Hotel",
  "city": "New York",
  "country": "USA",
  "address": "123 Main Street, Manhattan, NY 10001",
  "description": "Luxury 5-star hotel in the heart of Manhattan with world-class amenities",
  "email": "info@grandplaza.com",
  "phone": "+1-555-0100",
  "starRating": 5.0,
  "ownerId": "67b8d98d-8b46-41cd-87f1-fb48df8f28b3",
  "createdAt": "2026-07-04T15:59:05",
  "updatedAt": "2026-07-04T15:59:05"
}
```

---

## 🔎 STEP 10: SEARCH HOTELS BY CITY (GET)

**Endpoint:** `GET http://localhost:8014/api/hotels/city/New York`

**Alternative:**
```
GET http://localhost:8014/api/hotels/city/Los Angeles
```

**Request Headers:**
```
(No auth needed - Public endpoint)
```

**Expected Response (Status 200):**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "name": "Grand Plaza Hotel",
    "city": "New York",
    "country": "USA",
    "address": "123 Main Street, Manhattan, NY 10001",
    "description": "Luxury 5-star hotel in the heart of Manhattan with world-class amenities",
    "email": "info@grandplaza.com",
    "phone": "+1-555-0100",
    "starRating": 5.0,
    "ownerId": "67b8d98d-8b46-41cd-87f1-fb48df8f28b3"
  }
]
```

---

## 🌍 STEP 11: SEARCH HOTELS BY COUNTRY (GET)

**Endpoint:** `GET http://localhost:8014/api/hotels/country/USA`

**Request Headers:**
```
(No auth needed - Public endpoint)
```

**Expected Response (Status 200):**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "name": "Grand Plaza Hotel",
    "city": "New York",
    "country": "USA",
    "starRating": 5.0
  },
  {
    "id": "990e8400-e29b-41d4-a716-446655440444",
    "name": "Sunset Hotel",
    "city": "Los Angeles",
    "country": "USA",
    "starRating": 4.0
  }
]
```

---

## 🏷️ STEP 12: UPDATE HOTEL (PUT)

**Endpoint:** `PUT http://localhost:8014/api/hotels/550e8400-e29b-41d4-a716-446655440000`

**Alternative - Using Environment Variable:**
```
PUT {{BASE_URL}}/hotels/{{HOTEL_ID}}
```

**Request Headers:**
```
Content-Type: application/json
Authorization: Bearer {{TOKEN}}
```

**Request Body (JSON):**
```json
{
  "name": "Grand Plaza Hotel - Premium Edition",
  "description": "Updated luxury 5-star hotel with new amenities",
  "starRating": 4.8,
  "email": "contact@grandplaza.com"
}
```

**Note:** You only need to provide fields you want to update. Other fields remain unchanged.

**Expected Response (Status 200):**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Grand Plaza Hotel - Premium Edition",
  "city": "New York",
  "country": "USA",
  "address": "123 Main Street, Manhattan, NY 10001",
  "description": "Updated luxury 5-star hotel with new amenities",
  "email": "contact@grandplaza.com",
  "phone": "+1-555-0100",
  "starRating": 4.8,
  "ownerId": "67b8d98d-8b46-41cd-87f1-fb48df8f28b3",
  "createdAt": "2026-07-04T15:59:05",
  "updatedAt": "2026-07-04T16:05:30"
}
```

---

## 🛏️ STEP 13: UPDATE ROOM (PUT)

**Endpoint:** `PUT http://localhost:8014/api/rooms/660e8400-e29b-41d4-a716-446655440111`

**Alternative - Using Environment Variable:**
```
PUT {{BASE_URL}}/rooms/{{ROOM_ID}}
```

**Request Headers:**
```
Content-Type: application/json
Authorization: Bearer {{TOKEN}}
```

**Request Body (JSON):**
```json
{
  "roomNumber": "101A",
  "capacity": 3,
  "basePrice": 175.00,
  "description": "Updated deluxe double room with city view",
  "amenities": "WiFi,AC,TV,Minibar,Safe,Bathrobe,Slippers,Balcony,Jacuzzi"
}
```

**Expected Response (Status 200):**
```json
{
  "id": "660e8400-e29b-41d4-a716-446655440111",
  "hotelId": "550e8400-e29b-41d4-a716-446655440000",
  "roomNumber": "101A",
  "roomType": "DOUBLE",
  "capacity": 3,
  "basePrice": 175.00,
  "isAvailable": true,
  "description": "Updated deluxe double room with city view",
  "amenities": "WiFi,AC,TV,Minibar,Safe,Bathrobe,Slippers,Balcony,Jacuzzi",
  "createdAt": "2026-07-04T16:00:15",
  "updatedAt": "2026-07-04T16:06:45"
}
```

---

## ❌ STEP 14: CANCEL BOOKING (PUT)

**Endpoint:** `PUT http://localhost:8014/api/bookings/880e8400-e29b-41d4-a716-446655440333/cancel`

**Alternative - Using Environment Variable:**
```
PUT {{BASE_URL}}/bookings/{{BOOKING_ID}}/cancel
```

**Request Headers:**
```
Content-Type: application/json
Authorization: Bearer {{TOKEN}}
```

**Request Body (JSON):**
```json
{}
```

**Note:** Empty JSON object is sufficient

**Expected Response (Status 200):**
```json
{
  "id": "880e8400-e29b-41d4-a716-446655440333",
  "userId": "67b8d98d-8b46-41cd-87f1-fb48df8f28b3",
  "roomId": "660e8400-e29b-41d4-a716-446655440111",
  "hotelId": "550e8400-e29b-41d4-a716-446655440000",
  "checkInDate": "2026-07-10",
  "checkOutDate": "2026-07-15",
  "numberOfGuests": 2,
  "totalPrice": 750.00,
  "status": "CANCELLED",
  "specialRequests": "Late checkout preferred, high floor preferred",
  "createdAt": "2026-07-04T16:02:30",
  "updatedAt": "2026-07-04T16:07:00",
  "cancelledAt": "2026-07-04T16:07:00"
}
```

**Note:** Status changed from `CONFIRMED` to `CANCELLED`, and `cancelledAt` timestamp is set

---

## 📝 STEP 15: GET MY BOOKINGS (GET)

**Endpoint:** `GET http://localhost:8014/api/bookings/my-bookings`

**Alternative - Using Environment Variable:**
```
GET {{BASE_URL}}/bookings/my-bookings
```

**Request Headers:**
```
Authorization: Bearer {{TOKEN}}
```

**Expected Response (Status 200):**
```json
[
  {
    "id": "880e8400-e29b-41d4-a716-446655440333",
    "userId": "67b8d98d-8b46-41cd-87f1-fb48df8f28b3",
    "roomId": "660e8400-e29b-41d4-a716-446655440111",
    "hotelId": "550e8400-e29b-41d4-a716-446655440000",
    "checkInDate": "2026-07-10",
    "checkOutDate": "2026-07-15",
    "numberOfGuests": 2,
    "totalPrice": 750.00,
    "status": "CANCELLED",
    "specialRequests": "Late checkout preferred, high floor preferred",
    "createdAt": "2026-07-04T16:02:30",
    "updatedAt": "2026-07-04T16:07:00",
    "cancelledAt": "2026-07-04T16:07:00"
  }
]
```

---

## 🗑️ STEP 16: DELETE ROOM (DELETE)

**Endpoint:** `DELETE http://localhost:8014/api/rooms/660e8400-e29b-41d4-a716-446655440111`

**Alternative - Using Environment Variable:**
```
DELETE {{BASE_URL}}/rooms/{{ROOM_ID}}
```

**Request Headers:**
```
Authorization: Bearer {{TOKEN}}
```

**Request Body:**
```
(No body needed)
```

**Expected Response (Status 204):**
```
(Empty - No content)
```

**Verification:** Try GET the room → Should return 404 Not Found

---

## 🗑️ STEP 17: DELETE HOTEL (DELETE)

**Endpoint:** `DELETE http://localhost:8014/api/hotels/550e8400-e29b-41d4-a716-446655440000`

**Alternative - Using Environment Variable:**
```
DELETE {{BASE_URL}}/hotels/{{HOTEL_ID}}
```

**Request Headers:**
```
Authorization: Bearer {{TOKEN}}
```

**Request Body:**
```
(No body needed)
```

**Expected Response (Status 204):**
```
(Empty - No content)
```

**Verification:** Try GET the hotel → Should return 404 Not Found

---

## 📊 COMPLETE TESTING SEQUENCE TABLE

| Step | Method | Endpoint | Body | Status | Save As |
|------|--------|----------|------|--------|---------|
| 1 | POST | `/auth/register` | ✅ | 200 | TOKEN |
| 2 | POST | `/auth/login` | ✅ | 200 | TOKEN |
| 3 | POST | `/hotels` | ✅ | 201 | HOTEL_ID |
| 4 | POST | `/rooms` | ✅ | 201 | ROOM_ID |
| 5 | GET | `/rooms/search?...` | ❌ | 200 | - |
| 6 | POST | `/bookings` | ✅ | 201 | BOOKING_ID |
| 7 | GET | `/bookings/availability/check?...` | ❌ | 200 | - |
| 8 | GET | `/hotels` | ❌ | 200 | - |
| 9 | GET | `/hotels/{{id}}` | ❌ | 200 | - |
| 10 | GET | `/hotels/city/...` | ❌ | 200 | - |
| 11 | GET | `/hotels/country/...` | ❌ | 200 | - |
| 12 | PUT | `/hotels/{{id}}` | ✅ | 200 | - |
| 13 | PUT | `/rooms/{{id}}` | ✅ | 200 | - |
| 14 | PUT | `/bookings/{{id}}/cancel` | ✅ | 200 | - |
| 15 | GET | `/bookings/my-bookings` | ❌ | 200 | - |
| 16 | DELETE | `/rooms/{{id}}` | ❌ | 204 | - |
| 17 | DELETE | `/hotels/{{id}}` | ❌ | 204 | - |

---

## 🔧 POSTMAN SETUP FOR EACH STEP

### How to add each request in Postman:

**Step 1 - 3 - 4 - 6 (POST with body):**
1. Click **+** tab → Create new request
2. Select method: **POST**
3. Paste URL
4. Click **Body** → select **raw** → select **JSON**
5. Paste the JSON code from above
6. Click **Headers** → Add `Content-Type: application/json`
7. (For steps 3, 4, 6) Add `Authorization: Bearer {{TOKEN}}`
8. Click **Send**

**Step 2 - 5 - 7 - 8 - 9 - 10 - 11 - 15 (GET):**
1. Click **+** tab → Create new request
2. Select method: **GET**
3. Paste URL
4. Click **Headers** if auth needed → Add `Authorization: Bearer {{TOKEN}}`
5. Click **Send**

**Step 12 - 13 - 14 (PUT with body):**
1. Click **+** tab → Create new request
2. Select method: **PUT**
3. Paste URL
4. Click **Body** → select **raw** → select **JSON**
5. Paste the JSON code
6. Click **Headers** → Add both headers
7. Click **Send**

**Step 16 - 17 (DELETE):**
1. Click **+** tab → Create new request
2. Select method: **DELETE**
3. Paste URL
4. Click **Headers** → Add `Authorization: Bearer {{TOKEN}}`
5. Click **Send**

---

## ✅ VERIFICATION CHECKLIST

After each request, verify:

- [ ] Step 1: Token received (not empty)
- [ ] Step 3: Hotel ID received
- [ ] Step 4: Room ID received
- [ ] Step 6: Booking ID received with correct totalPrice
- [ ] Step 7: Returns `false` (room booked)
- [ ] Step 12: Hotel name updated
- [ ] Step 13: Room capacity updated
- [ ] Step 14: Booking status = CANCELLED
- [ ] Step 16: Returns 204 (room deleted)
- [ ] Step 17: Returns 204 (hotel deleted)

---

## 🚀 QUICK COPY-PASTE GUIDE

**Register User:**
```json
{
  "name": "Postman User",
  "email": "postman23@example.com",
  "password": "Password123",
  "age": 25
}
```

**Create Hotel:**
```json
{
  "name": "Grand Plaza Hotel",
  "city": "New York",
  "country": "USA",
  "address": "123 Main Street, Manhattan, NY 10001",
  "description": "Luxury 5-star hotel in the heart of Manhattan",
  "email": "info@grandplaza.com",
  "phone": "+1-555-0100",
  "starRating": 5.0
}
```

**Create Room:**
```json
{
  "hotelId": "{{HOTEL_ID}}",
  "roomNumber": "101",
  "roomType": "DOUBLE",
  "capacity": 2,
  "basePrice": 150.00,
  "description": "Double room with city view",
  "amenities": "WiFi,AC,TV,Minibar,Safe"
}
```

**Create Booking:**
```json
{
  "roomId": "{{ROOM_ID}}",
  "checkInDate": "2026-07-10",
  "checkOutDate": "2026-07-15",
  "numberOfGuests": 2,
  "specialRequests": "Late checkout preferred"
}
```

**Update Hotel:**
```json
{
  "name": "Grand Plaza Hotel - Premium Edition",
  "starRating": 4.8
}
```

**Update Room:**
```json
{
  "capacity": 3,
  "basePrice": 175.00
}
```

**Cancel Booking:**
```json
{}
```

---

## 🎯 API RESPONSE CODES GUIDE

| Code | Meaning | Example |
|------|---------|---------|
| **200** | ✅ Success | GET, PUT returns data |
| **201** | ✅ Created | POST returns new resource |
| **204** | ✅ Deleted | DELETE succeeds (no body) |
| **400** | ❌ Bad Request | Invalid JSON or missing fields |
| **401** | ❌ Unauthorized | Missing TOKEN header |
| **403** | ❌ Forbidden | Not the hotel/room owner |
| **404** | ❌ Not Found | Hotel/Room/Booking doesn't exist |
| **409** | ❌ Conflict | Room booked for those dates |

---

**Base URL:** `http://localhost:8014/api`

**Ready?** Copy the JSON codes above and start testing in Postman! 🎉
