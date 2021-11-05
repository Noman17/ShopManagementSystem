Use ShopManagementSystem;
CREATE TABLE Admin(
AdminID int IDENTITY(10001,1) NOT NULL PRIMARY KEY,
FirstName varchar (50) NOT NULL,
LastName varchar (50) NOT NULL,
AdminAddress varchar (50) NOT NULL,
City varchar (50) NOT NULL,
Country varchar (50) NOT NULL,
Password varchar (50) NOT NULL
);
INSERT INTO Admin
VALUES ('Noman', 'Ahmed', '39/7 K. B. Saha Road, Amlapara', 'Narayangonj', 'BD', '180104108'),
       ('Mehedi', 'Hasan', 'Suger Mill', 'Natore', 'BD', '180104114')
select * from Admin
select FirstName + ' ' + LastName from Admin

CREATE TABLE Seller(
SellerID int IDENTITY(100001,1) NOT NULL PRIMARY KEY,
SellerName varchar (50) NOT NULL,
Email varchar (50) NOT NULL,
PhoneNo varchar (50) NOT NULL,
Gender varchar (50) NOT NULL,
BirthDate date NOT NULL,
Address varchar (50) NOT NULL,
City varchar (50) NOT NULL,
Country varchar (50) NOT NULL,
Password varchar (50) NOT NULL
);
select * from Seller

CREATE TABLE Message(
MessageID int IDENTITY(1,1) NOT NULL PRIMARY KEY,
SellerID int NOT NULL FOREIGN KEY REFERENCES Seller(SellerID),
Topic varchar(250)
);
select * from Message order by MessageID DESC
alter table Message
alter column Topic varchar(Max)
alter table Message
alter column Topic text

SELECT s.SellerID, s.SellerName, s.Email, s.PhoneNo, s.Gender, s.BirthDate, s.Address, s.City, s.Country, COUNT(o.OrderID) AS Total_Orders 
FROM Seller AS s LEFT JOIN Orders AS o 
ON s.SellerID = o.SellerID
GROUP BY s.SellerID, s.SellerName, s.Email, s.PhoneNo, s.Gender, s.BirthDate, s.Address, s.City, s.Country 
HAVING SellerName = 'Hasib'

SELECT s.SellerID, s.SellerName, s.Email, s.PhoneNo, s.Address, s.Password, COUNT(o.OrderID) AS Total_Orders 
FROM Seller AS s LEFT JOIN Orders AS o
ON s.SellerID = o.SellerID 
GROUP BY s.SellerID, s.SellerName, s.Email, s.PhoneNo, s.Address, s.Password 
ORDER BY s.SellerID

SELECT s.SellerID, s.SellerName, s.Email, s.PhoneNo, s.Address, s.Password, COUNT(o.OrderID) AS Total_Orders 
FROM Seller AS s LEFT JOIN Orders AS o 
ON s.SellerID = o.SellerID 
GROUP BY s.SellerID, s.SellerName, s.Email, s.PhoneNo, s.Address, s.Password 
HAVING s.Address LIKE '%amla%' 
ORDER BY s.Address, s.SellerID

CREATE TABLE Categories(
C_ID int IDENTITY (11001,1) NOT NULL PRIMARY KEY,
C_Name varchar (50) NOT NULL UNIQUE,
Description varchar (100)
);
select * from Categories

CREATE TABLE Products(
P_ID int IDENTITY (12001,1) NOT NULL PRIMARY KEY,
P_Name varchar (50) NOT NULL,
P_C_ID int NOT NULL FOREIGN KEY REFERENCES Categories (C_ID) on delete cascade on update cascade,
P_Price float NOT NULL,
P_Quantity int NOT NULL,
Items_Sold int
);
select * from Products
alter table Products alter column P_Price float NOT NULL
alter table Products add Items_Sold int

INSERT INTO Products
VALUES ('Pepsi', '11001', '35', '200')

SELECT TOP(5) P_ID, P_Name, ISNULL(Items_Sold, 0) AS Items_Sold FROM Products ORDER BY Items_Sold DESC

SELECT P_ID, P_Name, P_Price, ISNULL(Items_Sold, 0) AS Items_Sold, P_Price * ISNULL(Items_Sold, 0) AS Revenue, 
RANK() OVER (ORDER BY P_Price * Items_Sold DESC) AS Revenue_Rank FROM Products

SELECT TOP(5) s.SellerID, s.SellerName, o.Total_Orders, od.Total_Pro
FROM Seller s
LEFT JOIN (SELECT SellerID, COUNT(*) AS Total_Orders FROM Orders GROUP BY SellerID
) o ON o.SellerID = s.SellerID
LEFT JOIN (SELECT SellerID, SUM(Quantity) AS Total_Pro FROM OrderDetail GROUP BY SellerID
) od ON od.SellerID = s.SellerID
WHERE s.SellerID IN (SELECT SellerID FROM Orders WHERE oDate >= DATEADD(day, -30, GETDATE()) and oDate <= GETDATE())
ORDER BY o.Total_Orders DESC

SELECT TOP(5) cus.Cus_ID, cus.Cus_PhoneNo, cus.Cus_LastPurchaseDate, ROUND(cus.Cus_Point, 2) AS Point, COUNT(o.OrderID) AS Total_Orders 
FROM Customer AS cus LEFT JOIN Orders AS o 
ON cus.Cus_ID = o.CustomerID 
GROUP BY cus.Cus_ID, cus.Cus_PhoneNo, cus.Cus_LastPurchaseDate, cus.Cus_Point 
HAVING cus.Cus_ID IN (SELECT CustomerID FROM Orders WHERE oDate >= DATEADD(day, -30, GETDATE()) and oDate <= GETDATE())
ORDER BY Point DESC, Total_Orders 

CREATE TABLE Customer(
Cus_ID int IDENTITY (20001,1) NOT NULL PRIMARY KEY,
Cus_PhoneNo varchar (50) NOT NULL UNIQUE,
Cus_Point float,
Cus_LastPurchaseDate varchar (50)
);
select * from Customer
alter table Customer add Cus_LastPurchaseDate varchar (50)
alter table Customer alter column Cus_Point float
exec sp_rename 'Customer.Cus_LastPurchaseDate', 'Cus_LastOrderDate', 'Column';

INSERT INTO Customer
VALUES ('01812345678', '2021-03-22', '0.00', '0.00'),
	   ('01612345678', '2021-03-22', '0.00', '0.00')
Select * from Customer

SELECT cus.Cus_ID, cus.Cus_PhoneNo, cus.Cus_LastPurchaseDate, cus.Cus_Point, COUNT(o.OrderID) AS Total_Orders 
FROM Customer AS cus LEFT JOIN Orders AS o 
ON cus.Cus_ID = o.CustomerID 
GROUP BY cus.Cus_ID, cus.Cus_PhoneNo, cus.Cus_LastPurchaseDate, cus.Cus_Point
ORDER BY cus.Cus_ID
       
select * from Categories
select * from Products
SELECT pro.P_ID, pro.P_Name, pro.P_Quantity, pro.P_Price, cat.C_ID, cat.C_Name 
FROM Products AS pro INNER JOIN Categories AS cat
ON pro.P_C_ID = cat.C_ID

select * from Categories
select * from Products
SELECT cat.C_ID, cat.C_Name, pro.P_ID, pro.P_Name, pro.P_Price, pro.P_Quantity 
FROM Categories AS cat INNER JOIN Products AS pro 
ON cat.C_ID = pro.P_C_ID
ORDER BY cat.C_ID, pro.P_ID

SELECT cat.C_ID, cat.C_Name, pro.P_ID, pro.P_Name, ROUND (pro.P_Price, 2), pro.P_Quantity 
FROM Categories AS cat INNER JOIN Products AS pro 
ON cat.C_ID = pro.P_C_ID 
WHERE pro.P_Price IN (SELECT pro.P_Price FROM Products WHERE pro.P_Price < 100)
ORDER BY pro.P_Price

CREATE TABLE Orders(
OrderID int IDENTITY(1,1) NOT NULL PRIMARY KEY,
CustomerID int NOT NULL FOREIGN KEY REFERENCES Customer(Cus_ID),
SellerID int NOT NULL FOREIGN KEY REFERENCES Seller(SellerID),
OrderDate varchar(50) NOT NULL,
TotalAmount float NOT NULL,
AmountPaid float NOT NULL,
AmountRtd float NOT NULL,
oDate date
);
select * from Orders
alter table Orders alter column Cus_Point int
alter table Orders add oDate date

CREATE TABLE OrderDetail(
OrderDetailID int IDENTITY(1,1) NOT NULL PRIMARY KEY,
OrderID int NOT NULL FOREIGN KEY REFERENCES Orders(OrderID) on delete no action on update no action,
ProductID int NOT NULL FOREIGN KEY REFERENCES Products(P_ID) on delete no action on update no action,
Quantity int NOT NULL,
Price float NOT NULL,
SellerID int NOT NULL FOREIGN KEY REFERENCES Seller(SellerID)
);
select * from OrderDetail
select * from Orders
