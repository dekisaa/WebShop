INSERT INTO `webshop`.`department` (`description`, `name`) VALUES ('obuca', 'obuca');
INSERT INTO `webshop`.`department` (`description`, `name`) VALUES ('racunari', 'racunari');
INSERT INTO `webshop`.`department` (`description`, `name`) VALUES ('mobilni', 'mobilni');
INSERT INTO `webshop`.`category` (`description`, `name`, `department_id`) VALUES ('sportske', 'patike', '1');
INSERT INTO `webshop`.`category` (`description`, `name`, `department_id`) VALUES ('stikle', 'stikle', '1');
INSERT INTO `webshop`.`category` (`description`, `name`, `department_id`) VALUES ('racunari', 'gejmerski racunar', '2');
INSERT INTO `webshop`.`category` (`description`, `name`, `department_id`) VALUES ('racunari', 'poslovni racunari', '2');
INSERT INTO `webshop`.`category` (`description`, `name`, `department_id`) VALUES ('mobilni', 'samsung', '3');
INSERT INTO `webshop`.`category` (`description`, `name`, `department_id`) VALUES ('mobilni', 'iphone', '3');

INSERT INTO `webshop`.`user` (`id`, `address`, `credit_card_info`, `email`, `phone`, `username`) VALUES ('1', 'Pere Dobrinovica 5', '131-121-125-789', 'peraperic@gmail.com', '069214548', 'Peki');
INSERT INTO `webshop`.`shopping_cart` (`id`, `user_id`) VALUES ('1', '1');

INSERT INTO `webshop`.`product` (`id`, `description`, `name`, `price`, `shipping_price`, `category_id`) VALUES ('1', 'Vodootporne', 'Nike', '360', '5.0', '1');
INSERT INTO `webshop`.`product` (`id`, `description`, `name`, `price`, `shipping_price`, `category_id`) VALUES ('2', 'Plave', 'Puma', '250','3.2', '1');

INSERT INTO `webshop`.`product` (`id`, `description`, `name`, `price`, `shipping_price`, `category_id`) VALUES ('3', 'gejminglaptop', 'asus', '2000', '1.0', '3');
INSERT INTO `webshop`.`product` (`id`, `description`, `name`, `price`, `shipping_price`, `category_id`) VALUES ('4', 'gejminglaptop', 'lenovo', '3000', '2.0', '3');

INSERT INTO `webshop`.`cart_item` (`id`, `quantity`, `subtotal`, `product_id`) VALUES ('1', '3', '1005', '1');
INSERT INTO `webshop`.`cart_item` (`id`, `quantity`, `subtotal`, `product_id`) VALUES ('2', '4', '2005', '2');

