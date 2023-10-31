delete from billing;
delete from biding;
delete from items;
delete from users;

INSERT INTO public.users (userid, password, isadmin) VALUES ('test1', '1234', false);
INSERT INTO public.users (userid, password, isadmin) VALUES ('a', '1234', false);
INSERT INTO public.users (userid, password, isadmin) VALUES ('b', '1234', true);
INSERT INTO public.users (userid, password, isadmin) VALUES ('mike', '1234', true);
INSERT INTO public.users (userid, password, isadmin) VALUES ('daisy12', '1234', false);
INSERT INTO public.users (userid, password, isadmin) VALUES ('fin', '1234', false);
INSERT INTO public.users (userid, password, isadmin) VALUES ('test2', '1234', false);
INSERT INTO public.users (userid, password, isadmin) VALUES ('test3', '1234', false);
INSERT INTO public.users (userid, password, isadmin) VALUES ('michael', '1234', false);
INSERT INTO public.users (userid, password, isadmin) VALUES ('michael12', '1234', false);

INSERT INTO public.items (itemid, category, description, condition, sellerid, buyitnowprice, dateposted, sold) VALUES (1, 'ELECTRONICS', 'lg gram', 'NEW', 'test1', 1000.00, '2023-01-01 12:00:00.000000', true);
INSERT INTO public.items (itemid, category, description, condition, sellerid, buyitnowprice, dateposted, sold) VALUES (2, 'BOOKS', 'harry potter', 'NEW', 'a', 100.00, '2023-12-01 01:00:00.000000', true);
INSERT INTO public.items (itemid, category, description, condition, sellerid, buyitnowprice, dateposted, sold) VALUES (3, 'CLOTHING', 'top', 'LIKE_NEW', 'a', 10.00, '2023-12-12 01:00:00.000000', true);
INSERT INTO public.items (itemid, category, description, condition, sellerid, buyitnowprice, dateposted, sold) VALUES (7, 'ELECTRONICS', 'iphone 12', 'ACCEPTABLE', 'test2', 200.00, '2023-11-11 00:00:00.000000', true);
INSERT INTO public.items (itemid, category, description, condition, sellerid, buyitnowprice, dateposted, sold) VALUES (4, 'ELECTRONICS', 'bose headphone', 'GOOD', 'test2', 100.00, '2023-11-01 12:00:00.000000', true);
INSERT INTO public.items (itemid, category, description, condition, sellerid, buyitnowprice, dateposted, sold) VALUES (8, 'HOME', 'towel', 'NEW', 'test2', 10.00, '2023-11-30 17:16:04.978122', true);
INSERT INTO public.items (itemid, category, description, condition, sellerid, buyitnowprice, dateposted, sold) VALUES (22, 'BOOKS', 'python', 'NEW', 'a', 5.00, '2023-11-01 01:00:00.000000', true);
INSERT INTO public.items (itemid, category, description, condition, sellerid, buyitnowprice, dateposted, sold) VALUES (5, 'BOOKS', 'mujin journey', 'NEW', 'b', 12.00, '2022-12-12 00:00:00.000000', true);
INSERT INTO public.items (itemid, category, description, condition, sellerid, buyitnowprice, dateposted, sold) VALUES (20, 'ELECTRONICS', 'iphone14', 'NEW', 'test3', 400.00, '2023-11-01 12:22:00.000000', false);
INSERT INTO public.items (itemid, category, description, condition, sellerid, buyitnowprice, dateposted, sold) VALUES (10, 'CLOTHING', 'White shirts', 'NEW', 'michael', 20.00, '2023-12-01 00:00:00.000000', false);
INSERT INTO public.items (itemid, category, description, condition, sellerid, buyitnowprice, dateposted, sold) VALUES (11, 'SPORTINGGOODS', 'nike pants', 'GOOD', 'michael', 20.00, '2023-12-01 00:00:00.000000', false);
INSERT INTO public.items (itemid, category, description, condition, sellerid, buyitnowprice, dateposted, sold) VALUES (12, 'SPORTINGGOODS', 'air jordan', 'ACCEPTABLE', 'michael', 60.00, '2023-12-01 00:00:00.000000', false);
INSERT INTO public.items (itemid, category, description, condition, sellerid, buyitnowprice, dateposted, sold) VALUES (9, 'CLOTHING', 'blue jeans', 'GOOD', 'michael', 10.00, '2023-12-30 17:19:01.455637', false);
INSERT INTO public.items (itemid, category, description, condition, sellerid, buyitnowprice, dateposted, sold) VALUES (16, 'ELECTRONICS', 'galaxy4', 'NEW', 'test3', 100.00, '2023-12-12 00:00:00.000000', false);
INSERT INTO public.items (itemid, category, description, condition, sellerid, buyitnowprice, dateposted, sold) VALUES (18, 'ELECTRONICS', 'galaxy10', 'LIKE_NEW', 'test3', 200.00, '2023-11-02 00:00:00.000000', false);
INSERT INTO public.items (itemid, category, description, condition, sellerid, buyitnowprice, dateposted, sold) VALUES (14, 'HOME', 'plant', 'NEW', 'test2', 4.00, '2023-12-12 00:00:00.000000', false);

INSERT INTO public.biding (itemid, bidprice, bidderid, datepurchase) VALUES (2, 100.00, 'test1', '2023-10-28 17:18:37.434242');
INSERT INTO public.biding (itemid, bidprice, bidderid, datepurchase) VALUES (3, 12.00, 'test1', '2023-10-28 20:26:19.946730');
INSERT INTO public.biding (itemid, bidprice, bidderid, datepurchase) VALUES (5, 2.00, 'b', '2022-10-22 20:38:44.000000');
INSERT INTO public.biding (itemid, bidprice, bidderid, datepurchase) VALUES (5, 3.00, 'mike', '2022-11-12 20:20:00.000000');
INSERT INTO public.biding (itemid, bidprice, bidderid, datepurchase) VALUES (4, 90.00, 'michael', '2023-10-31 02:33:01.520717');
INSERT INTO public.biding (itemid, bidprice, bidderid, datepurchase) VALUES (7, 200.00, 'michael', '2023-10-31 02:33:27.695724');
INSERT INTO public.biding (itemid, bidprice, bidderid, datepurchase) VALUES (4, 100.00, 'a', '2023-10-31 03:26:48.051611');
INSERT INTO public.biding (itemid, bidprice, bidderid, datepurchase) VALUES (8, 10.00, 'a', '2023-10-31 03:34:00.360691');
INSERT INTO public.biding (itemid, bidprice, bidderid, datepurchase) VALUES (9, 5.00, 'a', '2023-11-01 00:10:01.074776');
INSERT INTO public.biding (itemid, bidprice, bidderid, datepurchase) VALUES (20, 200.00, 'daisy12', '2023-11-01 00:19:59.239645');
INSERT INTO public.biding (itemid, bidprice, bidderid, datepurchase) VALUES (11, 10.00, 'daisy12', '2023-11-01 00:20:30.476680');
INSERT INTO public.biding (itemid, bidprice, bidderid, datepurchase) VALUES (20, 180.00, 'michael', '2023-11-01 00:21:20.518936');
INSERT INTO public.biding (itemid, bidprice, bidderid, datepurchase) VALUES (16, 50.00, 'michael', '2023-11-01 00:49:13.865624');

delete from billing;

INSERT INTO public.billing (itemid, buyerid, sellerid, datesold, buyerneedstopay, sellercommission, sellerneedstoearn) VALUES (2, 'test1', 'a', '2023-12-01 01:00:00.000000', 100.00, 10.00, 90.00);
INSERT INTO public.billing (itemid, buyerid, sellerid, datesold, buyerneedstopay, sellercommission, sellerneedstoearn) VALUES (3, 'test1', 'a', '2023-12-12 01:00:00.000000', 12.00, 1.00, 11.00);
INSERT INTO public.billing (itemid, buyerid, sellerid, datesold, buyerneedstopay, sellercommission, sellerneedstoearn) VALUES (5, 'mike', 'b', '2022-12-12 00:00:00.000000', 3.00, 0.00, 3.00);
INSERT INTO public.billing (itemid, buyerid, sellerid, datesold, buyerneedstopay, sellercommission, sellerneedstoearn) VALUES (7, 'michael', 'test2', '2023-10-31 02:33:27.695724', 200.00, 20.00, 180.00);
INSERT INTO public.billing (itemid, buyerid, sellerid, datesold, buyerneedstopay, sellercommission, sellerneedstoearn) VALUES (4, 'a', 'test2', '2023-10-31 03:26:48.051611', 100.00, 10.00, 90.00);
INSERT INTO public.billing (itemid, buyerid, sellerid, datesold, buyerneedstopay, sellercommission, sellerneedstoearn) VALUES (8, 'a', 'test2', '2023-10-31 03:34:00.360691', 10.00, 1.00, 9.00);
