INSERT INTO prices (sku, unit_price) VALUES ('A', 50);
INSERT INTO prices (sku, unit_price) VALUES ('B', 75);
INSERT INTO prices (sku, unit_price) VALUES ('C', 25);
INSERT INTO prices (sku, unit_price) VALUES ('D', 150);
INSERT INTO prices (sku, unit_price) VALUES ('E', 200);

INSERT INTO specials (sku, n_sku, special_price, special_price_desc, promotion) VALUES ('A', 'AAA', 130, '3 for £1.30', 1);
INSERT INTO specials (sku, n_sku, special_price, special_price_desc, promotion) VALUES ('B', 'BB', 125, '2 for £1.25', 1);
INSERT INTO specials (sku, n_sku, special_price, special_price_desc, promotion) VALUES ('C', 'CCC', 0, 'Buy 3, get one free', 2);
INSERT INTO specials (sku, n_sku, special_price, special_price_desc, promotion) VALUES ('D', 'DE', 300, 'Buy D and E for £3', 3);
INSERT INTO specials (sku, n_sku, special_price, special_price_desc, promotion) VALUES ('E', 'DE', 300, 'Buy D and E for £3', 3);