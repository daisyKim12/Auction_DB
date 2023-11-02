# Auction_DB
This progect, I designed, implemented, documented, and tested a database system for an online auction site.   
DB: PostgresDB
Auction Program: JAVA

## Main functionality

**Login and Signin**   
![](x/Screenshot%202023-11-02%20at%209.14.04%20PM.png)   

**Main menu**
When successfully logged in user can sell and buy items, and Check his or her activities.
- sell item: sell item by category, condition, price, etc..
- status of your item list: show the item user tries to sell so far.
- buy item: user can search for a item and if exist he or she can make a bidding.
- check your biddind: show user bidding
- check your account: show user sold item and purchased item finally calculate the money.
   
![](x/Screenshot%202023-11-02%20at%209.18.50%20PM.png)

**Admin menu**
If a logged in by admin id, there are more information to access. 
- Print Sold Items per Category
- Print Account Balance for Seller
- Print Seller Ranking
- Print Buyer Ranking   

## DB Implementatin
![](x/Screenshot%202023-11-02%20at%209.26.45%20PM.png)

## File explanation
- auction_project_report: details about Auction.java source code.
- DDL&DML: contains DDl code to create the Auction DB relationship, and DML code to insert dummy data
- Auction.java: source code for Auction program

## How to Run

1. Enter postgres in your local PC or server   
```
psql {database_name}
```
2. Make the table by running DDl   
```
i\ ./DDL&DML/ddl.sql
```
3. Insert dummy data by running DML   
```
i\ ./DDL&DML/data.sql
```
4. exit postgres and compile the source code   
```
exit
javac Auction.java
```
5. Run Auction program. You must pass database name and password as a arguement.   
```
java -cp .:./postgresql-42.6.0.jar Auction {db name} {password}
```
