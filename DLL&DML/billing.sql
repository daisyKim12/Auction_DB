DELETE FROM Billing;

INSERT INTO public.billing (itemid, buyerid, sellerid, datesold, buyerneedstopay, sellercommission, sellerneedstoearn) VALUES (2, 'test1', 'a', '2023-12-01 01:00:00.000000', 100.00, 10.00, 90.00);
INSERT INTO public.billing (itemid, buyerid, sellerid, datesold, buyerneedstopay, sellercommission, sellerneedstoearn) VALUES (3, 'test1', 'a', '2023-12-12 01:00:00.000000', 12.00, 1.00, 11.00);
INSERT INTO public.billing (itemid, buyerid, sellerid, datesold, buyerneedstopay, sellercommission, sellerneedstoearn) VALUES (5, 'mike', 'b', '2022-12-12 00:00:00.000000', 3.00, 0.00, 3.00);
INSERT INTO public.billing (itemid, buyerid, sellerid, datesold, buyerneedstopay, sellercommission, sellerneedstoearn) VALUES (7, 'michael', 'test2', '2023-10-31 02:33:27.695724', 200.00, 20.00, 180.00);
