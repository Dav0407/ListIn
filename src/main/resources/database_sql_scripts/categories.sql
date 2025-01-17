-- Main category "Auto"
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('5d211862-168e-41ad-af27-a9ad461169f6', null, 'Auto', '','https://listin-app-images.s3.eu-north-1.amazonaws.com/cars.png');

-- Main category "Electronics"
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('050747ed-c6aa-409c-835c-e7cd30bc4d98', null, 'Electronics', '','https://listin-app-images.s3.eu-north-1.amazonaws.com/electronics.png');

-- Main category "Real Estate"
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('534ec9ab-448d-407f-9478-30e2f0b132ca', null, 'Real Estate', '','https://listin-app-images.s3.eu-north-1.amazonaws.com/home_estate.png');

-- Main category "Clothes and shoes"
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('a27c8ffe-8d08-4578-bd7c-de336f4ff8d1', null, 'Clothes and Shoes', '','https://listin-app-images.s3.eu-north-1.amazonaws.com/clothes.png');

-- Main category "Flowers and gifts"
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('180b0b2d-4956-420e-8f44-8542e17f883b', null, 'Flowers and Gifts', '','https://listin-app-images.s3.eu-north-1.amazonaws.com/gifts.png');

-- Main category "Beauty and Health"
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('e7a956ed-dc77-461e-9f29-a0a49bf35826', null, 'Beauty and Health', '','https://listin-app-images.s3.eu-north-1.amazonaws.com/beauty_health.png');

-- Main category "Animals"
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('16198ad8-3e13-4ef6-bf8b-9d295eab2967', null, 'Animals', '','https://listin-app-images.s3.eu-north-1.amazonaws.com/animals.png');

-- Main category "For Home and Garden"
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('37a2eb83-8373-4ee1-9034-6fd60ed6e5b2', null, 'For Home and Garden', '','https://listin-app-images.s3.eu-north-1.amazonaws.com/garden.png');

-- Main category "Luxurious Accessories"
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('d24a6a1a-09ee-4e6b-9de6-6c50d8cb9c9b', null, 'Luxurious accessories', '','https://listin-app-images.s3.eu-north-1.amazonaws.com/accessories.png');



-- Child category "Smartphones"
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('11b78509-0092-44b1-a0be-f3659933dcb6', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Smartphones', 'Apple, Samsung, Xiaomi...,','https://listin-app-images.s3.eu-north-1.amazonaws.com/smartphones.png');

-- Child category "Laptops"
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('7f2f0f5b-abd5-4a19-95ad-f558dfa45f7d', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Laptops', 'Ultrabook, Gaming, Business...','https://listin-app-images.s3.eu-north-1.amazonaws.com/laptop.png');

-- Grandchild categories todo-> deprecated and assigned removable
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('ef6c05d2-459e-4cf0-859c-86d371a972ee', '7f2f0f5b-abd5-4a19-95ad-f558dfa45f7d', 'Ultrabook', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('20da85e9-03e9-49e9-93b2-e7a297263ac4', '7f2f0f5b-abd5-4a19-95ad-f558dfa45f7d', 'Gaming', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('0db1cd23-0675-4f90-bbff-25af08f79db4', '7f2f0f5b-abd5-4a19-95ad-f558dfa45f7d', 'Business', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');

-- Child category "Macbooks"
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('e51e9a61-8f59-4390-adf4-4142f6b28645', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Macbook', 'Macbook Air M1, Macbook Pro M1...','https://listin-app-images.s3.eu-north-1.amazonaws.com/macbook.png');

-- Grandchild categories todo -> category name Air, Pro;  todo-> deprecate, image_urld and assigned removable
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('9783661f-6fc4-47ca-b5ec-c235d85bc80e', 'e51e9a61-8f59-4390-adf4-4142f6b28645', 'Macbook Air', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('995335e1-9c19-4571-8dfe-fc32e1296ede', 'e51e9a61-8f59-4390-adf4-4142f6b28645', 'Macbook Pro', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');

-- Child category "Smartwatches"
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('b41e5ed9-1909-4fcb-85ca-0d209c110c5c', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Smart-Watches', 'Apple, Samsung, Huawei...','https://listin-app-images.s3.eu-north-1.amazonaws.com/watches.png');

-- Child category "Tablets"
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('623f9c6d-b99b-43ff-a91f-3b47941c891e', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Tablets', 'Apple, Samsung, Huawei...','https://listin-app-images.s3.eu-north-1.amazonaws.com/tablet.png');

-- Child category "Personal Computers"
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('d9d0a1d3-418d-4b28-8e1d-b5fc7edc8cf2', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Personal Computer', 'Gaming PC, Mini PC, Desktop PC...','https://listin-app-images.s3.eu-north-1.amazonaws.com/gaming.png');

-- Grandchild categories todo-> deprecated and assigned removable
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('73926a14-8457-4636-a586-2c17e52a076d', 'd9d0a1d3-418d-4b28-8e1d-b5fc7edc8cf2', 'Desktop PC', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('15ef9f69-ec3a-4377-a75b-ca89fb30b527', 'd9d0a1d3-418d-4b28-8e1d-b5fc7edc8cf2', 'All-in-One', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('0b259ec5-8f98-4da5-8a51-75a14585b888', 'd9d0a1d3-418d-4b28-8e1d-b5fc7edc8cf2', 'Mini PC', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('e7d47ac9-f5c1-4891-8cc8-297169250f18', 'd9d0a1d3-418d-4b28-8e1d-b5fc7edc8cf2', 'Gaming PC', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');

-- Child category "Televisions"
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('3e53ed81-131f-454c-91d1-f62a9f859158', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'TV', 'Smart TV, LED TV, OLED TV...','https://listin-app-images.s3.eu-north-1.amazonaws.com/tv.png');

-- Grandchild categories todo-> deprecated and assigned removable
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('21fd2aa4-6dab-4612-b22c-dbe012ee629f', '3e53ed81-131f-454c-91d1-f62a9f859158', 'Smart TV', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('a43dcfa2-ce71-4d59-806f-e1446513ff7f', '3e53ed81-131f-454c-91d1-f62a9f859158', 'LED TV', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('5ef2a779-6043-4d00-bcd9-e365cb0714bc', '3e53ed81-131f-454c-91d1-f62a9f859158', 'OLED TV', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('2106c333-682b-4b97-8f64-4e35c0b3bc74', '3e53ed81-131f-454c-91d1-f62a9f859158', 'QLED TV', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');

-- Child category "Gaming Consoles"
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('85a7d948-ed9c-4199-a9bf-51feb805e9d9', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Consoles', 'PlayStation, Xbox, Nintendo...','https://listin-app-images.s3.eu-north-1.amazonaws.com/console.png');

-- Child category "Audio Equipment"
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('4bbffb60-4832-4f24-bf74-343c2678b46e', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Audio', 'Headphones, Speakers, Earbuds...','https://listin-app-images.s3.eu-north-1.amazonaws.com/audio.png');

-- Grandchild categories todo-> deprecated and assigned removable
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('1160e6cf-f631-4a2a-ab46-03dd4c77f8c2', '4bbffb60-4832-4f24-bf74-343c2678b46e', 'Headphones', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('b304b54f-94a9-4060-9e34-dbac24c25a86', '4bbffb60-4832-4f24-bf74-343c2678b46e', 'Speakers', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('c0f79268-78e3-470a-804a-78a6ec16be4a', '4bbffb60-4832-4f24-bf74-343c2678b46e', 'Earbuds', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');

-- Child category "Photography and Video"
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('7eb2a701-7bc4-4156-a1e6-8e70c7e1c50d', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Photography and Video', 'Cameras, Lenses, Tripods...','https://listin-app-images.s3.eu-north-1.amazonaws.com/photo_and_video.png');

-- Grandchild categories todo-> deprecated and assigned removable
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('ef4b8b50-8f5a-42cf-bb9c-3509f104d6b4', '7eb2a701-7bc4-4156-a1e6-8e70c7e1c50d', 'Cameras', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('c1147a4a-0e05-4204-bd8e-6b0e8cf8e79a', '7eb2a701-7bc4-4156-a1e6-8e70c7e1c50d', 'Lenses', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('bd244528-5144-4281-a232-ca5a3d4a2417', '7eb2a701-7bc4-4156-a1e6-8e70c7e1c50d', 'Tripods', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('197a6e34-aabc-4c9b-966a-1fa64a45858d', '7eb2a701-7bc4-4156-a1e6-8e70c7e1c50d', 'Gimbals', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('a088f071-a963-48b6-8f48-448cbc8dd1d6', '7eb2a701-7bc4-4156-a1e6-8e70c7e1c50d', 'Lightning Equipment', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');

-- Child category "Home Electronics"
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('d856497c-3a0a-43a8-9630-3b023f76115d', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Home Electronics', 'Refrigerators, Air Conditioners, Washers...','https://listin-app-images.s3.eu-north-1.amazonaws.com/home_electronics.png');

-- Grandchild categories todo-> deprecated and assigned removable
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('36691dd5-ee11-48d6-9874-c03323de0791', 'd856497c-3a0a-43a8-9630-3b023f76115d', 'Refrigerators', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('a68b92dd-6519-4965-b191-9f9a12aa532d', 'd856497c-3a0a-43a8-9630-3b023f76115d', 'Ari Conditioners', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('6a34861d-a053-40ac-af59-712acabb8b26', 'd856497c-3a0a-43a8-9630-3b023f76115d', 'Washers', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('68b0c4d5-3ecf-4a5a-bde6-c68205dfd5cd', 'd856497c-3a0a-43a8-9630-3b023f76115d', 'Dryers', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, description, image_url) VALUES ('f0dc1e98-d5dc-42d4-a2e2-38569906289d', 'd856497c-3a0a-43a8-9630-3b023f76115d', 'Microwaves', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
