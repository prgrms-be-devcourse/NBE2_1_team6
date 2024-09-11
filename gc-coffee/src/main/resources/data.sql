-- 초기 PRODUCT 데이터 10개 삽입.
INSERT IGNORE INTO products (product_id, product_name, category, price, description) VALUES (1, 'PRODUCT1', 'COFFEE_BEAN_PACKAGE', 1000, 'PRODUCT_DESCRIPTION_1');
INSERT IGNORE INTO products (product_id, product_name, category, price, description) VALUES (2, 'PRODUCT2', 'COFFEE_BEAN_PACKAGE', 1000, 'PRODUCT_DESCRIPTION_2');
INSERT IGNORE INTO products (product_id, product_name, category, price, description) VALUES (3, 'PRODUCT3', 'COFFEE_BEAN_PACKAGE', 1000, 'PRODUCT_DESCRIPTION_3');
INSERT IGNORE INTO products (product_id, product_name, category, price, description) VALUES (4, 'PRODUCT4', 'COFFEE_BEAN_PACKAGE', 1000, 'PRODUCT_DESCRIPTION_4');
INSERT IGNORE INTO products (product_id, product_name, category, price, description) VALUES (5, 'PRODUCT5', 'COFFEE_BEAN_PACKAGE', 1000, 'PRODUCT_DESCRIPTION_5');
INSERT IGNORE INTO products (product_id, product_name, category, price, description) VALUES (6, 'PRODUCT6', 'COFFEE_BEAN_PACKAGE', 1000, 'PRODUCT_DESCRIPTION_6');
INSERT IGNORE INTO products (product_id, product_name, category, price, description) VALUES (7, 'PRODUCT7', 'COFFEE_BEAN_PACKAGE', 1000, 'PRODUCT_DESCRIPTION_7');
INSERT IGNORE INTO products (product_id, product_name, category, price, description) VALUES (8, 'PRODUCT8', 'COFFEE_BEAN_PACKAGE', 1000, 'PRODUCT_DESCRIPTION_8');
INSERT IGNORE INTO products (product_id, product_name, category, price, description) VALUES (9, 'PRODUCT9', 'COFFEE_BEAN_PACKAGE', 1000, 'PRODUCT_DESCRIPTION_9');
INSERT IGNORE INTO products (product_id, product_name, category, price, description) VALUES (10, 'PRODUCT10', 'COFFEE_BEAN_PACKAGE', 1000, 'PRODUCT_DESCRIPTION_10');

-- 초기 ORDER 데이터 5개 삽입.
INSERT IGNORE INTO orders (order_id, email, address, order_status, post_code, create_at) VALUES (1, 'username1@email.com', 'ADDRESS_1', 'NOT_DELIVERY', '123456', NOW());
INSERT IGNORE INTO orders (order_id, email, address, order_status, post_code, create_at) VALUES (2, 'username2@email.com', 'ADDRESS_2', 'NOT_DELIVERY', '234567', NOW());
INSERT IGNORE INTO orders (order_id, email, address, order_status, post_code, create_at) VALUES (3, 'username3@email.com', 'ADDRESS_3', 'NOT_DELIVERY', '345678', NOW());
INSERT IGNORE INTO orders (order_id, email, address, order_status, post_code, create_at) VALUES (4, 'username4@email.com', 'ADDRESS_4', 'NOT_DELIVERY', '456789', NOW());
INSERT IGNORE INTO orders (order_id, email, address, order_status, post_code, create_at) VALUES (5, 'username5@email.com', 'ADDRESS_5', 'NOT_DELIVERY', '567890', NOW());

-- 초기 ORDER_ITEM 데이터 10개 삽입.

INSERT IGNORE INTO order_items (order_item_id, order_id, product_id, category, price, quantity) VALUES (1, 1, 1, 'COFFEE_BEAN_PACKAGE', 1000, 10);
INSERT IGNORE INTO order_items (order_item_id, order_id, product_id, category, price, quantity) VALUES (2, 1, 2, 'COFFEE_BEAN_PACKAGE', 1000, 10);
INSERT IGNORE INTO order_items (order_item_id, order_id, product_id, category, price, quantity) VALUES (3, 2, 3, 'COFFEE_BEAN_PACKAGE', 1000, 10);
INSERT IGNORE INTO order_items (order_item_id, order_id, product_id, category, price, quantity) VALUES (4, 2, 4, 'COFFEE_BEAN_PACKAGE', 1000, 10);
INSERT IGNORE INTO order_items (order_item_id, order_id, product_id, category, price, quantity) VALUES (5, 3, 5, 'COFFEE_BEAN_PACKAGE', 1000, 10);
INSERT IGNORE INTO order_items (order_item_id, order_id, product_id, category, price, quantity) VALUES (6, 3, 6, 'COFFEE_BEAN_PACKAGE', 1000, 10);
INSERT IGNORE INTO order_items (order_item_id, order_id, product_id, category, price, quantity) VALUES (7, 4, 7, 'COFFEE_BEAN_PACKAGE', 1000, 10);
INSERT IGNORE INTO order_items (order_item_id, order_id, product_id, category, price, quantity) VALUES (8, 4, 8, 'COFFEE_BEAN_PACKAGE', 1000, 10);
INSERT IGNORE INTO order_items (order_item_id, order_id, product_id, category, price, quantity) VALUES (9, 5, 9, 'COFFEE_BEAN_PACKAGE', 1000, 10);
INSERT IGNORE INTO order_items (order_item_id, order_id, product_id, category, price, quantity) VALUES (10, 5, 10, 'COFFEE_BEAN_PACKAGE', 1000, 10);