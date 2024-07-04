INSERT INTO prices (sku, unit_price) VALUES ('A', 50);
INSERT INTO prices (sku, unit_price) VALUES ('B', 75);
INSERT INTO prices (sku, unit_price) VALUES ('C', 25);
INSERT INTO prices (sku, unit_price) VALUES ('D', 150);
INSERT INTO prices (sku, unit_price) VALUES ('E', 200);

INSERT INTO specials (sku, n_sku, one_free, special_price, special_price_desc) VALUES ('A', '', false, 0, '');
INSERT INTO specials (sku, n_sku, one_free, special_price, special_price_desc) VALUES ('B', 'B,B', false, 125, '2 for £1.25');
INSERT INTO specials (sku, n_sku, one_free, special_price, special_price_desc) VALUES ('C', 'C,C,C', true, 0, 'Buy 3, get one free');
INSERT INTO specials (sku, n_sku, one_free, special_price, special_price_desc) VALUES ('D', 'D,E', false, 300, 'Buy D and E for £3');
INSERT INTO specials (sku, n_sku, one_free, special_price, special_price_desc) VALUES ('E', 'D,E', false, 300, 'Buy D and E for £3');