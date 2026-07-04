# Hotel Booking Platform - Build & Deployment Guide

## Prerequisites Check
- ✅ Java 17 (OpenJDK Temurin-17.0.15) - Already Installed
- ❌ Maven 3.8+ - **NOT installed**, needs installation

## Maven Installation

### Option 1: Windows Package Managers
```powershell
# Using Chocolatey (if installed)
choco install maven

# Using SCOOP (if installed)  
scoop install maven
```

### Option 2: Manual Installation from Apache Maven
1. Download from: https://maven.apache.org/download.cgi
2. Extract to: `C:\apache-maven-3.9.x`
3. Add to PATH: 
   - Add `C:\apache-maven-3.9.x\bin` to System Environment Variables
4. Verify: Open new terminal and run `mvn -version`

### Option 3: Download Maven Wrapper Setup
```bash
cd "c:\Users\saisr\Projects\ProdigyInfotech_BD_Task03\users-api (4)\users-api"
# Once Maven is available, create wrapper (optional)
mvn wrapper:wrapper -Dmaven=3.9.0
```

## Build Steps (After Maven Installation)

### Step 1: Navigate to project
```bash
cd "c:\Users\saisr\Projects\ProdigyInfotech_BD_Task03\users-api (4)\users-api"
```

### Step 2: Clean and Package
```bash
mvn clean package -DskipTests
```

### Step 3: Start Application
```bash
java -jar target/users-api-1.0.0.jar
```

The application will start on port 8014 (configurable via `SERVER_PORT` environment variable)

## What Gets Built

The new JAR includes:
- ✅ 5 Exception classes for hotel booking domain
- ✅ 3 Service classes (HotelService, RoomService, BookingService)
- ✅ 3 Controller classes exposing REST endpoints
- ✅ Updated GlobalExceptionHandler with 5 new exception handlers
- ✅ Updated SecurityConfig with granular endpoint authorization
- ✅ Updated BookingRepository with date conflict query

## New API Endpoints

### Hotels
- `POST /api/hotels` - Create hotel (requires auth)
- `GET /api/hotels` - List all hotels (public)
- `GET /api/hotels/{id}` - Get hotel details (public)
- `GET /api/hotels/city/{city}` - Search by city (public)
- `GET /api/hotels/country/{country}` - Search by country (public)
- `GET /api/hotels/my-hotels` - Get current user's hotels (requires auth)
- `PUT /api/hotels/{id}` - Update hotel (requires owner/admin)
- `DELETE /api/hotels/{id}` - Delete hotel (requires owner/admin)

### Rooms
- `POST /api/rooms` - Create room (requires auth + hotel owner)
- `GET /api/rooms/{id}` - Get room details (public)
- `GET /api/rooms/hotel/{hotelId}` - List rooms in hotel (public)
- `GET /api/rooms/search?hotelId=&checkInDate=&checkOutDate=&capacity=&maxPrice=` - Search available rooms (public)
- `PUT /api/rooms/{id}` - Update room (requires auth + hotel owner)
- `DELETE /api/rooms/{id}` - Delete room (requires auth + hotel owner)

### Bookings
- `POST /api/bookings` - Create booking (requires auth)
- `GET /api/bookings/{id}` - Get booking (requires auth + owner/admin)
- `GET /api/bookings/my-bookings` - Get user's bookings (requires auth)
- `GET /api/bookings/hotel/{hotelId}` - Get hotel's bookings (requires auth + owner/admin)
- `GET /api/bookings/room/{roomId}` - Get room's bookings (requires auth)
- `PUT /api/bookings/{id}/cancel` - Cancel booking (requires auth + owner/admin)
- `GET /api/bookings/availability/check?roomId=&checkInDate=&checkOutDate=` - Check room availability (public)

## Authorization Rules

### Public Endpoints (No authentication required)
- Browse all hotels and rooms
- Check room availability
- Search rooms by criteria

### Authenticated Endpoints (Login required)
- Create/edit/delete own hotels
- Create/edit/delete rooms in own hotels
- Create bookings
- View own bookings
- Cancel own bookings

### Admin-Only Endpoints
- `/api/users/**` - User management
- Can edit/delete any hotel
- Can edit/delete any booking

## Testing the API

### 1. Register User
```bash
curl -X POST http://localhost:8014/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","age":30,"password":"SecurePass123"}'
```

### 2. Login
```bash
curl -X POST http://localhost:8014/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"SecurePass123"}'
# Returns: {"token":"eyJhbGciOiJIUzI1NiJ9..."}
```

### 3. Create Hotel (with token)
```bash
curl -X POST http://localhost:8014/api/hotels \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "name":"Grand Hotel",
    "city":"New York",
    "country":"USA",
    "address":"123 Main St",
    "description":"Luxury 5-star hotel",
    "email":"hotel@example.com",
    "phone":"555-1234",
    "starRating":5
  }'
```

### 4. Create Room
```bash
curl -X POST http://localhost:8014/api/rooms \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "hotelId":"hotel-uuid-from-step-3",
    "roomNumber":"101",
    "roomType":"DOUBLE",
    "capacity":2,
    "basePrice":150.00,
    "description":"Spacious double room",
    "amenities":"WiFi,TV,AC"
  }'
```

### 5. Search Available Rooms
```bash
curl "http://localhost:8014/api/rooms/search?hotelId=hotel-uuid&checkInDate=2025-07-15&checkOutDate=2025-07-20&capacity=2&maxPrice=200"
```

### 6. Create Booking
```bash
curl -X POST http://localhost:8014/api/bookings \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "roomId":"room-uuid-from-step-4",
    "checkInDate":"2025-07-15",
    "checkOutDate":"2025-07-20",
    "numberOfGuests":2,
    "specialRequests":"Early check-in if possible"
  }'
```

## Error Handling

The API returns appropriate HTTP status codes:
- `200` - Success
- `201` - Created
- `204` - No Content (delete successful)
- `400` - Bad Request (validation error)
- `401` - Unauthorized (missing/invalid token)
- `403` - Forbidden (insufficient permissions)
- `404` - Not Found (hotel/room/booking not found)
- `409` - Conflict (room not available)
- `500` - Internal Server Error

## Database Migrations

After rebuilding, the application will auto-run Flyway migrations:
- `V1__create_users_table.sql` - Users table (existing)
- `V2__add_password_and_role_to_users.sql` - User security fields (existing)
- `V3__create_hotels_table.sql` - Hotels table (needs creation)
- `V4__create_rooms_table.sql` - Rooms table (needs creation)
- `V5__create_bookings_table.sql` - Bookings table (needs creation)

Create migration files in: `src/main/resources/db/migration/`

## Troubleshooting

### JAR won't start
```bash
# Check if port 8014 is in use
netstat -ano | findstr :8014

# Run on different port
set SERVER_PORT=8080
java -jar target/users-api-1.0.0.jar
```

### Compilation errors
```bash
# Clean Maven cache
mvn clean
rm -r .m2/repository

# Rebuild
mvn package -DskipTests
```

### Database issues
- Check `application.properties` for database connection settings
- Ensure H2 driver is in classpath (dev profile)
- Check Flyway migration scripts are in `src/main/resources/db/migration/`
