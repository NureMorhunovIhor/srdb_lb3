# TaxiService Database Schema

This project involves the creation of a database for a taxi service. Below are the SQL commands to create the necessary tables, procedure and functions for this database.

### Tables

```sql
USE TaxiService
GO

CREATE TABLE Passenger (
  Passenger_id int PRIMARY KEY IDENTITY(1, 1) NOT NULL,
  First_name nvarchar(40),
  Last_name nvarchar(50),
  Phone_number nvarchar(15) UNIQUE,
  Birth_date date,
  Gender nvarchar(8),
  Email nvarchar(50) UNIQUE NOT NULL
);

CREATE TABLE Driver (
  Driver_id int PRIMARY KEY IDENTITY(1, 1) NOT NULL,
  First_name nvarchar(40) NOT NULL,
  Last_name nvarchar(50) NOT NULL,
  Middle_name nvarchar(50) NOT NULL,
  Phone_number nvarchar(15) UNIQUE NOT NULL,
  City nvarchar(30) NOT NULL,
  Rating float CHECK((Rating >= 0 AND Rating <= 5) OR Rating IS NULL),
  Driving_expirience date NOT NULL,
  Registration_date date NOT NULL,
  Email nvarchar(50) UNIQUE,
  Birth_date date NOT NULL
);

CREATE TABLE Orders (
  Orders_id int PRIMARY KEY IDENTITY(1, 1) NOT NULL,
  Adress_from nvarchar(100) NOT NULL,
  Adress_to nvarchar(100) NOT NULL,
  Creation_datetime datetime DEFAULT CURRENT_TIMESTAMP,
  Price float NOT NULL CHECK(Price > 0),
  Order_state nvarchar(40) NOT NULL,
  Luggage_weight float CHECK(Luggage_weight > 0 OR Luggage_weight IS NULL),
  Preferred_datetime datetime DEFAULT CURRENT_TIMESTAMP,
  Passenger_id int REFERENCES Passenger(Passenger_id) ON DELETE SET NULL ON UPDATE CASCADE,
  Driver_id int REFERENCES Driver(Driver_id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE Review (
  Review_id int PRIMARY KEY IDENTITY(1, 1) NOT NULL,
  Rating float NOT NULL CHECK(Rating >= 0 AND Rating <= 5),
  Comment nvarchar(200),
  Creation_datetime datetime DEFAULT CURRENT_TIMESTAMP,
  Order_id int UNIQUE REFERENCES Orders(Orders_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Car_category (
  Car_category_id int PRIMARY KEY IDENTITY(1, 1) NOT NULL,
  Category_name nvarchar(15) NOT NULL,
  Max_passengers_number int NOT NULL,
  Kilometer_price float NOT NULL
);

CREATE TABLE Car (
  Car_number nvarchar(8) PRIMARY KEY NOT NULL,
  Model nvarchar(50) NOT NULL,
  Color nvarchar(50) NOT NULL,
  Production_year int NOT NULL,
  Driver_id int UNIQUE REFERENCES Driver(Driver_id) ON DELETE CASCADE ON UPDATE CASCADE,
  Car_category_id int REFERENCES Car_category(Car_category_id) ON DELETE SET NULL ON UPDATE CASCADE
);
```

### Scalar Function

```tsql
CREATE FUNCTION GetDriversCountByCity (@City nvarchar(30))
RETURNS int
AS
BEGIN
  DECLARE @DriverCount int;

  SELECT @DriverCount = COUNT(*)
  FROM Driver
  WHERE City LIKE '%' + @City + '%';

  RETURN @DriverCount;
END;
```
