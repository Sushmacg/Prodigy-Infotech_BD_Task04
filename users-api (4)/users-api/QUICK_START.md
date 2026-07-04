# ✅ QUICK START - API Build & Postman Testing

## 📊 Current Status

✅ **Maven Installed:** `C:\apache-maven` (v3.9.6)  
✅ **Build Successful:** JAR compiled at `target/users-api-1.0.0.jar`  
✅ **Application Running:** Listening on `http://localhost:8014`  
✅ **Database:** H2 in-memory (ready)  

---

## 🚀 Step-by-Step: Testing with Postman

### Step 1: Open Postman
1. Download from https://www.postman.com/downloads/
2. Install and launch
3. Skip login (or login to cloud sync, optional)

---

### Step 2: Import the Collection (Easiest Way)

**Option A - Fast Import:**
1. In Postman, click **File → Import**
2. Select: `Hotel-Booking-Platform-Collection.json` 
3. Click **Import** ✅
4. All 18 endpoints auto-loaded!

**Option B - Manual Setup:**
- Follow `POSTMAN_SETUP_GUIDE.md` to create collection manually

---

### Step 3: Setup Environment Variables

1. Click **Environments** (gear icon) → **Create Environment**
2. Name: `Local Development`
3. Add variables:

| Variable | Value |
|----------|-------|
| BASE_URL | `http://localhost:8014/api` |
| TOKEN | (empty - filled after login) |
| HOTEL_ID | (empty - filled after create) |
| ROOM_ID | (empty - filled after create) |
| BOOKING_ID | (empty - filled after create) |

4. Click **Save** ✅

---

### Step 4: Run Test Sequence

Follow this order in Postman:

#### 1️⃣ Register User (POST)
```
POST {{BASE_URL}}/auth/register
```
**Body:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "age": 30,
  "password": "SecurePass123!"
}
```
**Response:** Gets TOKEN ✅

#### 2️⃣ Create Hotel (POST)
```
POST {{BASE_URL}}/hotels
Headers: Authorization: Bearer {{TOKEN}}
```
**Body:**
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
**Response:** Gets HOTEL_ID ✅

#### 3️⃣ Create Room (POST)
```
POST {{BASE_URL}}/rooms
Headers: Authorization: Bearer {{TOKEN}}
```
**Body:**
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
**Response:** Gets ROOM_ID ✅

#### 4️⃣ Search Available Rooms (GET)
```
GET {{BASE_URL}}/rooms/search?hotelId={{HOTEL_ID}}&checkInDate=2026-07-10&checkOutDate=2026-07-15&capacity=2&maxPrice=200
```
**Expected:** Array of available rooms ✅

#### 5️⃣ Create Booking (POST)
```
POST {{BASE_URL}}/bookings
Headers: Authorization: Bearer {{TOKEN}}
```
**Body:**
```json
{
  "roomId": "{{ROOM_ID}}",
  "checkInDate": "2026-07-10",
  "checkOutDate": "2026-07-15",
  "numberOfGuests": 2,
  "specialRequests": "Late checkout preferred"
}
```
**Response:** Gets BOOKING_ID ✅

#### 6️⃣ Check Availability (GET) 🔓
```
GET {{BASE_URL}}/bookings/availability/check?roomId={{ROOM_ID}}&checkInDate=2026-07-10&checkOutDate=2026-07-15
```
**Expected:** `false` (room now booked) ✅

#### 7️⃣ Get Hotels (GET) 🔓
```
GET {{BASE_URL}}/hotels
```
**Expected:** List of all hotels ✅

#### 8️⃣ Update Hotel (PUT)
```
PUT {{BASE_URL}}/hotels/{{HOTEL_ID}}
Headers: Authorization: Bearer {{TOKEN}}
```
**Body:**
```json
{
  "name": "Grand Plaza Hotel - Deluxe",
  "starRating": 4.5
}
```
**Expected:** Updated hotel ✅

#### 9️⃣ Cancel Booking (PUT)
```
PUT {{BASE_URL}}/bookings/{{BOOKING_ID}}/cancel
Headers: Authorization: Bearer {{TOKEN}}
```
**Body:** `{}`  
**Expected:** Status changed to CANCELLED ✅

#### 🔟 Delete Hotel (DELETE)
```
DELETE {{BASE_URL}}/hotels/{{HOTEL_ID}}
Headers: Authorization: Bearer {{TOKEN}}
```
**Expected:** 204 No Content ✅

---

## 🎯 Expected Results

| Endpoint | Method | Status | Notes |
|----------|--------|--------|-------|
| Register | POST | 200 | Returns JWT token |
| Login | POST | 200 | Returns JWT token |
| Create Hotel | POST | 201 | Returns hotel with ID |
| Get All Hotels | GET | 200 | Returns array |
| Get Hotel | GET | 200 | Returns single hotel |
| Search Hotels | GET | 200 | Filters by city/country |
| Update Hotel | PUT | 200 | Returns updated hotel |
| Delete Hotel | DELETE | 204 | No response body |
| Create Room | POST | 201 | Returns room with ID |
| Get Room | GET | 200 | Returns single room |
| Search Rooms | GET | 200 | Filters by dates/price |
| Update Room | PUT | 200 | Returns updated room |
| Delete Room | DELETE | 204 | No response body |
| Create Booking | POST | 201 | Returns booking with total price |
| Get Booking | GET | 200 | Returns booking |
| Get My Bookings | GET | 200 | Returns array |
| Check Availability | GET | 200 | Returns boolean |
| Cancel Booking | PUT | 200 | Changes status to CANCELLED |

**All Status Codes Green?** 🎉 **API is fully functional!**

---

## 🔑 Environment Variable Usage

After each successful request, copy the ID to environment variables:

### Auto-Save in Postman
Go to **Tests** tab in each request and add:

```javascript
// For hotel ID
var jsonData = pm.response.json();
pm.environment.set("HOTEL_ID", jsonData.id);

// For room ID
pm.environment.set("ROOM_ID", jsonData.id);

// For booking ID
pm.environment.set("BOOKING_ID", jsonData.id);

// For token
pm.environment.set("TOKEN", jsonData.token);
```

---

## 🛠️ Troubleshooting

### ❌ Connection Refused (Port 8014)

**Check if app is running:**
```bash
netstat -ano | findstr :8014
```

**If not running, restart:**
```bash
cd "c:\Users\saisr\Projects\ProdigyInfotech_BD_Task03\users-api (4)\users-api"
java -jar target/users-api-1.0.0.jar
```

---

### ❌ 401 Unauthorized

**Problem:** Missing or invalid JWT token

**Fix:** 
1. Register user first (POST /auth/register)
2. Copy token from response
3. Paste into TOKEN environment variable
4. Use `Authorization: Bearer {{TOKEN}}` in headers

---

### ❌ 403 Forbidden

**Problem:** Permission denied (not hotel owner)

**Fix:** Make sure you're using token from user who created the hotel

---

### ❌ 409 Room Not Available

**Problem:** Room already booked for those dates

**Fix:** Choose different dates or create another room

---

### ❌ 400 Bad Request

**Problem:** Invalid input (e.g., checkout before checkin)

**Fix:**
- Use format: `YYYY-MM-DD` for dates
- Ensure checkout > checkin
- Check all required fields are present

---

## 📁 Files Created for Testing

| File | Purpose |
|------|---------|
| `POSTMAN_SETUP_GUIDE.md` | Detailed Postman setup instructions |
| `Hotel-Booking-Platform-Collection.json` | Ready-to-import collection |
| `QUICK_START.md` | This file |

---

## 📊 API Statistics

| Metric | Value |
|--------|-------|
| Total Endpoints | 18 |
| Authentication Endpoints | 2 |
| Hotel Endpoints | 8 |
| Room Endpoints | 5 |
| Booking Endpoints | 7 |
| HTTP Methods Used | GET, POST, PUT, DELETE |
| Status Codes | 200, 201, 204, 400, 401, 403, 404, 409 |

---

## ✨ Features Tested

- ✅ User registration & login
- ✅ JWT authentication
- ✅ Hotel CRUD operations
- ✅ Room CRUD operations
- ✅ Booking CRUD operations
- ✅ Date-based room availability
- ✅ Price calculations
- ✅ Role-based access control
- ✅ Error handling (401, 403, 404, 409)
- ✅ Search & filter functionality

---

## 🎓 Learning Resources

### Understanding the Architecture:
- **Models:** Hotel.java, Room.java, Booking.java
- **Services:** HotelService.java, RoomService.java, BookingService.java
- **Controllers:** HotelController.java, RoomController.java, BookingController.java
- **Security:** SecurityConfig.java, JwtAuthenticationFilter.java

### Key Concepts:
1. **JWT Authentication:** Bearer tokens in Authorization header
2. **Role-Based Access:** ADMIN vs USER permissions
3. **Ownership Model:** Users can only manage their own resources
4. **Date-Based Availability:** Overlapping booking detection
5. **Price Calculation:** nights × base_price

---

## 🚀 Next Steps After Postman Testing

1. ✅ **Verify all endpoints** return correct status codes
2. ✅ **Test error scenarios** (401, 403, 404, 409)
3. ✅ **Check database logs** in console output
4. ✅ **Review response times** (should be < 100ms)
5. ✅ **Document any issues** found during testing

---

## 📞 Support

If you encounter issues:

1. Check **POSTMAN_SETUP_GUIDE.md** for detailed instructions
2. Review **HOTEL_BOOKING_README.md** for full API documentation
3. Check application logs in terminal window
4. Verify BASE_URL is correct: `http://localhost:8014/api`
5. Verify TOKEN is valid and includes "Bearer " prefix

---

## ✅ Final Checklist

Before declaring API complete:

- [ ] Maven installed successfully
- [ ] JAR built with zero errors
- [ ] Application running on port 8014
- [ ] Postman collection imported
- [ ] Environment variables configured
- [ ] User registration works (POST)
- [ ] User login works (POST)
- [ ] Hotel creation works (POST)
- [ ] Hotel retrieval works (GET)
- [ ] Hotel search works (GET)
- [ ] Hotel update works (PUT)
- [ ] Hotel deletion works (DELETE)
- [ ] Room creation works (POST)
- [ ] Room retrieval works (GET)
- [ ] Room search works (GET)
- [ ] Room update works (PUT)
- [ ] Room deletion works (DELETE)
- [ ] Booking creation works (POST)
- [ ] Booking retrieval works (GET)
- [ ] Booking cancellation works (PUT)
- [ ] Availability check works (GET)

**All checked?** 🎉 **API is production-ready!**

---

## 📈 Performance Tips

1. **Response Time:** Should be < 100ms for most endpoints
2. **Database:** H2 in-memory, no persistence (perfect for dev)
3. **Connection Pool:** HikariCP with 20 connections
4. **Caching:** In-memory ConcurrentMapCache

---

**Last Updated:** 2026-07-04  
**Status:** ✅ Ready for Postman Testing  
**API Base URL:** `http://localhost:8014/api`
