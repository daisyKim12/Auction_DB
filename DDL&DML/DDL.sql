-- drop table
DROP TABLE IF EXISTS Billing;
DROP TABLE IF EXISTS Biding;
DROP TABLE IF EXISTS Items;
DROP TABLE IF EXISTS Users;


-- Create a table to store user data
CREATE TABLE Users (
    UserID VARCHAR(100) NOT NULL,
    Password VARCHAR(100) NOT NULL,
    IsAdmin BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (UserID)
);

-- Create a table to store item data with automatically generated ItemID
CREATE TABLE Items (
    ItemID SERIAL,
    Category VARCHAR(100) NOT NULL check ( Category in (
        'ELECTRONICS',
		'BOOKS',
		'HOME',
		'CLOTHING',
		'SPORTINGGOODS',
		'OTHERS'
        )),
    Description TEXT NOT NULL,
    Condition VARCHAR(100) NOT NULL check ( Condition in (
        'NEW',
		'LIKE_NEW',
		'GOOD',
		'ACCEPTABLE'
        )),
    SellerID VARCHAR(100) NOT NULL,
    BuyItNowPrice DECIMAL(10, 2) DEFAULT 0.00 CHECK (BuyItNowPrice > 0),
    DatePosted TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    Sold BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (ItemID),
    FOREIGN KEY (SellerID) REFERENCES Users(UserID)
);


-- Create a table to store bid data
CREATE TABLE Biding (
    ItemID INT,
    BidPrice DECIMAL(10, 2) NOT NULL check ( BidPrice > 0),
    BidderID VARCHAR(100) NOT NULL,
    DatePurchase TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (ItemID, BidderID),
    FOREIGN KEY (ItemID) REFERENCES Items(ItemID),
    FOREIGN KEY (BidderID) REFERENCES Users(UserID)
);

CREATE TABLE Billing (
    ItemID int,
    BuyerID varchar(100) not null,
    SellerID varchar(100) not null,
    DateSold timestamp,
    BuyerNeedsToPay decimal(10, 2) not null check (BuyerNeedsToPay > 0),
    SellerCommission decimal(10, 2),
    SellerNeedsToEarn decimal(10, 2),
    FOREIGN KEY (ItemID) REFERENCES Items(ItemID),
    FOREIGN KEY (ItemID, BuyerID) REFERENCES Biding(ItemID, BidderID),
    PRIMARY KEY (ItemID, BuyerID, SellerID)
);

-- update sold trigger
CREATE OR REPLACE FUNCTION update_item_sold()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.BidPrice >= (
        SELECT BuyItNowPrice FROM Items WHERE ItemID = NEW.ItemID
    ) THEN
        UPDATE Items SET Sold = TRUE WHERE ItemID = NEW.ItemID;
        INSERT INTO Billing (ItemID, BuyerID, SellerID, DateSold, BuyerNeedsToPay)
        SELECT NEW.ItemID, NEW.bidderid, i.SellerID, Now(), NEW.bidprice
        FROM items as i
        WHERE i.ItemID = NEW.ItemID;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_item_sold_trigger
AFTER INSERT ON Biding
FOR EACH ROW
EXECUTE PROCEDURE update_item_sold();

-- auto calculate payment
CREATE OR REPLACE FUNCTION update_billing() RETURNS TRIGGER AS $$
BEGIN
    NEW.SellerCommission = FLOOR(NEW.BuyerNeedsToPay * 0.10);
    NEW.SellerNeedsToEarn = NEW.BuyerNeedsToPay - NEW.SellerCommission;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_billing_trigger
BEFORE INSERT ON Billing
FOR EACH ROW
EXECUTE PROCEDURE update_billing();