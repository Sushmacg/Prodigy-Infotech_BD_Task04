# 🔗 HTTP METHODS TESTING GUIDE - Complete Reference

**Base URL:** `http://localhost:8014/api`

---

## 📮 POST REQUESTS (Create/Submit Data)

### What is POST?
- **Purpose:** CREATE new resources
- **Expected Status:** `201 Created`
- **Sends Data:** In request body (JSON)
- **Response:** New resource with ID

---

### 1️⃣ POST - Register User

**Full URL:** `http://localhost:8014/api/auth/register`

**How to Test in Postman:**
1. Select method: **POST**
2. Paste URL: `http://localhost:8014/api/auth/register`
3. Click **Body** tab
4. Select **raw** → **JSON**
5. Paste this JSON:
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "age": 30,
  "password": "SecurePass123!"
}
```
6. Click **Send**
7. ✅ Expected: Status **200** + token in response

**Save Token:** Copy the token from response → Set environment variable `TOKEN`

---

### 2️⃣ POST - Login User

**Full URL:** `http://localhost:8014/api/auth/login`

**How to Test in Postman:**
1. Select method: **POST**
2. Paste URL: `http://localhost:8014/api/auth/login`
3. Click **Body** → **raw** → **JSON**
4. Paste:
```json
{
  "email": "john@example.com",
  "password": "SecurePass123!"
}
```
5. Click **Send**
6. ✅ Expected: Status **200** + new token

---

### 3️⃣ POST - Create Hotel

**Full URL:** `http://localhost:8014/api/hotels`

**How to Test in Postman:**
1. Select method: **POST**
2. Paste URL: `http://localhost:8014/api/hotels`
3. Click **Headers** tab
4. Add header:
   - Key: `Authorization`
   - Value: `Bearer {{TOKEN}}`
5. Click **Body** → **raw** → **JSON**
6. Paste:
```json
{
  "name": "Grand Plaza Hotel",
  "city": "New York",
  "country": "USA",
  "address": "123 Main Street, NY",
  "description": "Luxury 5-star hotel",
  "email": "info@grandplaza.com",
  "phone": "+1-555-0100",
  "starRating": 5.0
}
```
7. Click **Send**
8. ✅ Expected: Status **201** + hotel object with ID

**Save Hotel ID:** Copy ID from response → Set `HOTEL_ID` environment variable

---

### 4️⃣ POST - Create Room

**Full URL:** `http://localhost:8014/api/rooms`

**How to Test in Postman:**
1. Select method: **POST**
2. Paste URL: `http://localhost:8014/api/rooms`
3. Click **Headers** → Add authorization header
4. Click **Body** → **raw** → **JSON**
5. Paste:
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
6. Click **Send**
7. ✅ Expected: Status **201** + room object with ID

**Save Room ID:** Copy ID from response → Set `ROOM_ID` environment variable

---

### 5️⃣ POST - Create Booking

**Full URL:** `http://localhost:8014/api/bookings`

**How to Test in Postman:**
1. Select method: **POST**
2. Paste URL: `http://localhost:8014/api/bookings`
3. Click **Headers** → Add authorization header
4. Click **Body** → **raw** → **JSON**
5. Paste:
```json
{
  "roomId": "{{ROOM_ID}}",
  "checkInDate": "2026-07-10",
  "checkOutDate": "2026-07-15",
  "numberOfGuests": 2,
  "specialRequests": "Late checkout preferred"
}
```
6. Click **Send**
7. ✅ Expected: Status **201** + booking with calculated total price

**Save Booking ID:** Copy ID from response → Set `BOOKING_ID` environment variable

---

## 📥 GET REQUESTS (Retrieve Data)

### What is GET?
- **Purpose:** READ/RETRIEVE data
- **Expected Status:** `200 OK`
- **Sends Data:** In URL (query parameters)
- **Response:** Requested resource(s)
- **No body needed**

---

### 1️⃣ GET - Get All Hotels (Public)

**Full URL:** `http://localhost:8014/api/hotels`

**How to Test in Postman:**
1. Select method: **GET**
2. Paste URL: `http://localhost:8014/api/hotels`
3. **NO headers needed** (public endpoint)
4. Click **Send**
5. ✅ Expected: Status **200** + array of hotels

---

### 2️⃣ GET - Get Single Hotel (Public)

**Full URL:** `http://localhost:8014/api/hotels/{{HOTEL_ID}}`

**How to Test in Postman:**
1. Select method: **GET**
2. Paste URL: `http://localhost:8014/api/hotels/{{HOTEL_ID}}`
3. Click **Send**
4. ✅ Expected: Status **200** + single hotel object

---

### 3️⃣ GET - Search Hotels by City (Public)

**Full URL:** `http://localhost:8014/api/hotels/city/New York`

**How to Test in Postman:**
1. Select method: **GET**
2. Paste URL: `http://localhost:8014/api/hotels/city/New York`
3. Click **Send**
4. ✅ Expected: Status **200** + array of hotels in that city

---

### 4️⃣ GET - Search Hotels by Country (Public)

**Full URL:** `http://localhost:8014/api/hotels/country/USA`

**How to Test in Postman:**
1. Select method: **GET**
2. Paste URL: `http://localhost:8014/api/hotels/country/USA`
3. Click **Send**
4. ✅ Expected: Status **200** + array of hotels in that country

---

### 5️⃣ GET - Get My Hotels (Authenticated)

**Full URL:** `http://localhost:8014/api/hotels/my-hotels`

**How to Test in Postman:**
1. Select method: **GET**
2. Paste URL: `http://localhost:8014/api/hotels/my-hotels`
3. Click **Headers** → Add authorization header
4. Click **Send**
5. ✅ Expected: Status **200** + your created hotels

---

### 6️⃣ GET - Get Single Room (Public)

**Full URL:** `http://localhost:8014/api/rooms/{{ROOM_ID}}`

**How to Test in Postman:**
1. Select method: **GET**
2. Paste URL: `http://localhost:8014/api/rooms/{{ROOM_ID}}`
3. Click **Send**
4. ✅ Expected: Status **200** + room object

---

### 7️⃣ GET - Get Rooms by Hotel (Public)

**Full URL:** `http://localhost:8014/api/rooms/hotel/{{HOTEL_ID}}`

**How to Test in Postman:**
1. Select method: **GET**
2. Paste URL: `http://localhost:8014/api/rooms/hotel/{{HOTEL_ID}}`
3. Click **Send**
4. ✅ Expected: Status **200** + array of rooms in that hotel

---

### 8️⃣ GET - Search Available Rooms (Public)

**Full URL:** `http://localhost:8014/api/rooms/search?hotelId={{HOTEL_ID}}&checkInDate=2026-07-10&checkOutDate=2026-07-15&capacity=2&maxPrice=200`

**How to Test in Postman:**
1. Select method: **GET**
2. Paste URL: `http://localhost:8014/api/rooms/search?hotelId={{HOTEL_ID}}&checkInDate=2026-07-10&checkOutDate=2026-07-15&capacity=2&maxPrice=200`
3. Click **Send**
4. ✅ Expected: Status **200** + array of available rooms matching criteria

**Query Parameters:**
- `hotelId` = {{HOTEL_ID}} (required)
- `checkInDate` = YYYY-MM-DD format (required)
- `checkOutDate` = YYYY-MM-DD format (required)
- `capacity` = number of guests (optional)
- `maxPrice` = maximum price per night (optional)

---

### 9️⃣ GET - Get Single Booking (Authenticated)

**Full URL:** `http://localhost:8014/api/bookings/{{BOOKING_ID}}`

**How to Test in Postman:**
1. Select method: **GET**
2. Paste URL: `http://localhost:8014/api/bookings/{{BOOKING_ID}}`
3. Click **Headers** → Add authorization header
4. Click **Send**
5. ✅ Expected: Status **200** + booking details

---

### 🔟 GET - Get My Bookings (Authenticated)

**Full URL:** `http://localhost:8014/api/bookings/my-bookings`

**How to Test in Postman:**
1. Select method: **GET**
2. Paste URL: `http://localhost:8014/api/bookings/my-bookings`
3. Click **Headers** → Add authorization header
4. Click **Send**
5. ✅ Expected: Status **200** + array of your bookings

---

### 1️⃣1️⃣ GET - Get Hotel's Bookings (Authenticated)

**Full URL:** `http://localhost:8014/api/bookings/hotel/{{HOTEL_ID}}`

**How to Test in Postman:**
1. Select method: **GET**
2. Paste URL: `http://localhost:8014/api/bookings/hotel/{{HOTEL_ID}}`
3. Click **Headers** → Add authorization header
4. Click **Send**
5. ✅ Expected: Status **200** + array of bookings for that hotel

---

### 1️⃣2️⃣ GET - Get Room's Bookings (Authenticated)

**Full URL:** `http://localhost:8014/api/bookings/room/{{ROOM_ID}}`

**How to Test in Postman:**
1. Select method: **GET**
2. Paste URL: `http://localhost:8014/api/bookings/room/{{ROOM_ID}}`
3. Click **Headers** → Add authorization header
4. Click **Send**
5. ✅ Expected: Status **200** + array of bookings for that room

---

### 1️⃣3️⃣ GET - Check Room Availability (Public)

**Full URL:** `http://localhost:8014/api/bookings/availability/check?roomId={{ROOM_ID}}&checkInDate=2026-07-10&checkOutDate=2026-07-15`

**How to Test in Postman:**
1. Select method: **GET**
2. Paste URL: `http://localhost:8014/api/bookings/availability/check?roomId={{ROOM_ID}}&checkInDate=2026-07-10&checkOutDate=2026-07-15`
3. Click **Send**
4. ✅ Expected: Status **200** + boolean (true=available, false=booked)

---

## ✏️ PUT REQUESTS (Update Data)

### What is PUT?
- **Purpose:** UPDATE existing resources
- **Expected Status:** `200 OK`
- **Sends Data:** In request body (JSON)
- **Response:** Updated resource

---

### 1️⃣ PUT - Update Hotel

**Full URL:** `http://localhost:8014/api/hotels/{{HOTEL_ID}}`

**How to Test in Postman:**
1. Select method: **PUT**
2. Paste URL: `http://localhost:8014/api/hotels/{{HOTEL_ID}}`
3. Click **Headers** → Add authorization header
4. Click **Body** → **raw** → **JSON**
5. Paste (update any fields):
```json
{
  "name": "Grand Plaza Hotel - Premium",
  "description": "Updated luxury hotel",
  "starRating": 4.8
}
```
6. Click **Send**
7. ✅ Expected: Status **200** + updated hotel object

---

### 2️⃣ PUT - Update Room

**Full URL:** `http://localhost:8014/api/rooms/{{ROOM_ID}}`

**How to Test in Postman:**
1. Select method: **PUT**
2. Paste URL: `http://localhost:8014/api/rooms/{{ROOM_ID}}`
3. Click **Headers** → Add authorization header
4. Click **Body** → **raw** → **JSON**
5. Paste:
```json
{
  "roomNumber": "102",
  "capacity": 3,
  "basePrice": 175.00,
  "description": "Updated deluxe room"
}
```
6. Click **Send**
7. ✅ Expected: Status **200** + updated room object

---

### 3️⃣ PUT - Cancel Booking

**Full URL:** `http://localhost:8014/api/bookings/{{BOOKING_ID}}/cancel`

**How to Test in Postman:**
1. Select method: **PUT**
2. Paste URL: `http://localhost:8014/api/bookings/{{BOOKING_ID}}/cancel`
3. Click **Headers** → Add authorization header
4. Click **Body** → **raw** → **JSON**
5. Paste:
```json
{}
```
6. Click **Send**
7. ✅ Expected: Status **200** + booking with status changed to "CANCELLED"

---

## 🗑️ DELETE REQUESTS (Remove Data)

### What is DELETE?
- **Purpose:** REMOVE/DELETE resources
- **Expected Status:** `204 No Content`
- **Sends Data:** None (just URL)
- **Response:** Empty (just status code)

---

### 1️⃣ DELETE - Delete Hotel

**Full URL:** `http://localhost:8014/api/hotels/{{HOTEL_ID}}`

**How to Test in Postman:**
1. Select method: **DELETE**
2. Paste URL: `http://localhost:8014/api/hotels/{{HOTEL_ID}}`
3. Click **Headers** → Add authorization header
4. **NO body needed**
5. Click **Send**
6. ✅ Expected: Status **204 No Content** (empty response)

---

### 2️⃣ DELETE - Delete Room

**Full URL:** `http://localhost:8014/api/rooms/{{ROOM_ID}}`

**How to Test in Postman:**
1. Select method: **DELETE**
2. Paste URL: `http://localhost:8014/api/rooms/{{ROOM_ID}}`
3. Click **Headers** → Add authorization header
4. **NO body needed**
5. Click **Send**
6. ✅ Expected: Status **204 No Content**

---

## 📊 QUICK REFERENCE TABLE

| HTTP Method | Action | Status Code | Example URL |
|-------------|--------|------------|-------------|
| **POST** | Create | 201 | `http://localhost:8014/api/auth/register` |
| **POST** | Create | 201 | `http://localhost:8014/api/hotels` |
| **POST** | Create | 201 | `http://localhost:8014/api/rooms` |
| **POST** | Create | 201 | `http://localhost:8014/api/bookings` |
| **GET** | Read | 200 | `http://localhost:8014/api/hotels` |
| **GET** | Read | 200 | `http://localhost:8014/api/hotels/{{id}}` |
| **GET** | Search | 200 | `http://localhost:8014/api/rooms/search?...` |
| **GET** | Check | 200 | `http://localhost:8014/api/bookings/availability/check?...` |
| **PUT** | Update | 200 | `http://localhost:8014/api/hotels/{{id}}` |
| **PUT** | Update | 200 | `http://localhost:8014/api/rooms/{{id}}` |
| **PUT** | Action | 200 | `http://localhost:8014/api/bookings/{{id}}/cancel` |
| **DELETE** | Remove | 204 | `http://localhost:8014/api/hotels/{{id}}` |
| **DELETE** | Remove | 204 | `http://localhost:8014/api/rooms/{{id}}` |

---

## 🎯 STEP-BY-STEP TESTING WORKFLOW

### Phase 1: Authentication (GET TOKENS)
1. ✅ POST `/auth/register` → Save TOKEN
2. ✅ GET `/hotels` (verify public access)

### Phase 2: Create Resources
3. ✅ POST `/hotels` (using TOKEN) → Save HOTEL_ID
4. ✅ POST `/rooms` (using TOKEN, HOTEL_ID) → Save ROOM_ID

### Phase 3: Search & Check
5. ✅ GET `/rooms/search` (public)
6. ✅ GET `/bookings/availability/check` (public)

### Phase 4: Create Booking
7. ✅ POST `/bookings` (using TOKEN, ROOM_ID) → Save BOOKING_ID

### Phase 5: Updates
8. ✅ PUT `/hotels/{{HOTEL_ID}}` (using TOKEN)
9. ✅ PUT `/rooms/{{ROOM_ID}}` (using TOKEN)

### Phase 6: Verify & Cancel
10. ✅ GET `/bookings/{{BOOKING_ID}}` (verify booking exists)
11. ✅ PUT `/bookings/{{BOOKING_ID}}/cancel` (using TOKEN)

### Phase 7: Cleanup
12. ✅ DELETE `/rooms/{{ROOM_ID}}` (using TOKEN)
13. ✅ DELETE `/hotels/{{HOTEL_ID}}` (using TOKEN)

---

## 🔑 AUTHORIZATION HEADER FORMAT

For all authenticated requests (POST/PUT/GET with auth, DELETE):

```
Header Name: Authorization
Header Value: Bearer {{TOKEN}}
```

**Example in Postman:**
```
Key: Authorization
Value: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

---

## ❌ ERROR RESPONSES

| Status | Meaning | Check |
|--------|---------|-------|
| **400** | Bad Request | Invalid JSON or missing fields |
| **401** | Unauthorized | Missing or invalid TOKEN |
| **403** | Forbidden | Not the hotel/room owner |
| **404** | Not Found | Hotel/Room/Booking doesn't exist |
| **409** | Conflict | Room not available for dates |
| **500** | Server Error | Application error |

---

## ✅ SUCCESS RESPONSES

| Status | Meaning | Use |
|--------|---------|-----|
| **200** | OK | GET/PUT successful |
| **201** | Created | POST successful (new resource) |
| **204** | No Content | DELETE successful |

---

## 💾 ENVIRONMENT VARIABLES TO SAVE

As you test, save these to Postman environment:

```
TOKEN = <from register/login response>
HOTEL_ID = <from create hotel response>
ROOM_ID = <from create room response>
BOOKING_ID = <from create booking response>
BASE_URL = http://localhost:8014/api
```

Then use in URLs like:
- `{{BASE_URL}}/hotels/{{HOTEL_ID}}`
- `{{BASE_URL}}/rooms/{{ROOM_ID}}`
- etc.

---

## 🧪 TESTING CHECKLIST

**POST Requests:**
- [ ] Register returns 200 + token
- [ ] Create hotel returns 201 + ID
- [ ] Create room returns 201 + ID
- [ ] Create booking returns 201 + ID

**GET Requests:**
- [ ] All hotels returns 200
- [ ] Single hotel returns 200
- [ ] Hotels by city returns 200
- [ ] Hotels by country returns 200
- [ ] My hotels returns 200 (with auth)
- [ ] All rooms returns 200
- [ ] Single room returns 200
- [ ] Rooms by hotel returns 200
- [ ] Search rooms returns 200
- [ ] Bookings returns 200
- [ ] My bookings returns 200
- [ ] Availability check returns 200

**PUT Requests:**
- [ ] Update hotel returns 200
- [ ] Update room returns 200
- [ ] Cancel booking returns 200

**DELETE Requests:**
- [ ] Delete room returns 204
- [ ] Delete hotel returns 204

---

## 🚀 QUICK COMMANDS FOR CURL

If you prefer command line, use these CURL commands:

### POST - Register
```bash
curl -X POST http://localhost:8014/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"John","email":"john@test.com","age":30,"password":"Pass123!"}'
```

### GET - All Hotels
```bash
curl -X GET http://localhost:8014/api/hotels
```

### GET - Single Hotel
```bash
curl -X GET http://localhost:8014/api/hotels/{{HOTEL_ID}}
```

### PUT - Update Hotel
```bash
curl -X PUT http://localhost:8014/api/hotels/{{HOTEL_ID}} \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {{TOKEN}}" \
  -d '{"name":"Updated Name","starRating":4.5}'
```

### DELETE - Delete Hotel
```bash
curl -X DELETE http://localhost:8014/api/hotels/{{HOTEL_ID}} \
  -H "Authorization: Bearer {{TOKEN}}"
```

---

## 📱 POSTMAN TIPS

1. **Use Base URL Variable:**
   - Set `BASE_URL` = `http://localhost:8014/api`
   - Use in requests: `{{BASE_URL}}/hotels`

2. **Auto-Save IDs:**
   - In "Tests" tab, add:
   ```javascript
   pm.environment.set("HOTEL_ID", pm.response.json().id);
   ```

3. **View Response Pretty:**
   - Click "Pretty" button in response panel

4. **Check Request Details:**
   - Click "Code" button to see generated code

5. **Save Collection:**
   - Right-click collection → "Save as"

---

**API Base URL:** `http://localhost:8014/api`

**All Tests Ready?** 🎉 **Start Testing in Postman!**
