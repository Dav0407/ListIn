-- Main category "Electronics"
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('050747ed-c6aa-409c-835c-e7cd30bc4d98', null, 'Electronics', '');

-- Child category "Smartphones"
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('11b78509-0092-44b1-a0be-f3659933dcb6', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Smartphones', 'Apple, Samsung, Xiaomi...,');

-- Child category "Laptops"
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('7f2f0f5b-abd5-4a19-95ad-f558dfa45f7d', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Laptops', 'Ultrabook, Gaming, Business...');

-- Grandchild categories
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('ef6c05d2-459e-4cf0-859c-86d371a972ee', '7f2f0f5b-abd5-4a19-95ad-f558dfa45f7d', 'Ultrabook', null);
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('20da85e9-03e9-49e9-93b2-e7a297263ac4', '7f2f0f5b-abd5-4a19-95ad-f558dfa45f7d', 'Gaming', null);
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('0db1cd23-0675-4f90-bbff-25af08f79db4', '7f2f0f5b-abd5-4a19-95ad-f558dfa45f7d', 'Business', null);

-- Child category "Macbooks"
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('e51e9a61-8f59-4390-adf4-4142f6b28645', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Macbooks', 'Macbook Air M1, Macbook Pro M1...');

-- Grandchild categories todo -> category name Air, Pro
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('9783661f-6fc4-47ca-b5ec-c235d85bc80e', 'e51e9a61-8f59-4390-adf4-4142f6b28645', 'Macbook Air', null);
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('995335e1-9c19-4571-8dfe-fc32e1296ede', 'e51e9a61-8f59-4390-adf4-4142f6b28645', 'Macbook Pro', null);

-- Child category "Smartwatches"
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('b41e5ed9-1909-4fcb-85ca-0d209c110c5c', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Smartwatches', 'Apple, Samsung, Huawei...');

-- Child category "Tablets"
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('623f9c6d-b99b-43ff-a91f-3b47941c891e', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Tablets', 'Apple, Samsung, Huawei...');

-- Child category "Personal Computers"
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('d9d0a1d3-418d-4b28-8e1d-b5fc7edc8cf2', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Personal Computers', 'Gaming PC, Mini PC, Desktop PC...');

-- Grandchild categories
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('73926a14-8457-4636-a586-2c17e52a076d', 'd9d0a1d3-418d-4b28-8e1d-b5fc7edc8cf2', 'Desktop PC', null);
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('15ef9f69-ec3a-4377-a75b-ca89fb30b527', 'd9d0a1d3-418d-4b28-8e1d-b5fc7edc8cf2', 'All-in-One', null);
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('0b259ec5-8f98-4da5-8a51-75a14585b888', 'd9d0a1d3-418d-4b28-8e1d-b5fc7edc8cf2', 'Mini PC', null);
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('e7d47ac9-f5c1-4891-8cc8-297169250f18', 'd9d0a1d3-418d-4b28-8e1d-b5fc7edc8cf2', 'Gaming PC', null);

-- Child category "Televisions"
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('3e53ed81-131f-454c-91d1-f62a9f859158', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Televisions', 'Smart TV, LED TV, OLED TV...');

-- Grandchild categories
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('21fd2aa4-6dab-4612-b22c-dbe012ee629f', '3e53ed81-131f-454c-91d1-f62a9f859158', 'Smart TV', null);
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('a43dcfa2-ce71-4d59-806f-e1446513ff7f', '3e53ed81-131f-454c-91d1-f62a9f859158', 'LED TV', null);
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('5ef2a779-6043-4d00-bcd9-e365cb0714bc', '3e53ed81-131f-454c-91d1-f62a9f859158', 'OLED TV', null);
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('2106c333-682b-4b97-8f64-4e35c0b3bc74', '3e53ed81-131f-454c-91d1-f62a9f859158', 'QLED TV', null);

-- Child category "Gaming Consoles"
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('85a7d948-ed9c-4199-a9bf-51feb805e9d9', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Gaming Consoles', 'PlayStation, Xbox, Nintendo...');

-- Child category "Audio Equipment"
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('4bbffb60-4832-4f24-bf74-343c2678b46e', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Audio Equipment', 'Headphones, Speakers, Earbuds...');

-- Grandchild categories
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('1160e6cf-f631-4a2a-ab46-03dd4c77f8c2', '4bbffb60-4832-4f24-bf74-343c2678b46e', 'Headphones', null);
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('b304b54f-94a9-4060-9e34-dbac24c25a86', '4bbffb60-4832-4f24-bf74-343c2678b46e', 'Speakers', null);
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('c0f79268-78e3-470a-804a-78a6ec16be4a', '4bbffb60-4832-4f24-bf74-343c2678b46e', 'Earbuds', null);

-- Child category "Photography and Video"
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('7eb2a701-7bc4-4156-a1e6-8e70c7e1c50d', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Photography and Video', 'Cameras, Lenses, Tripods...');

-- Grandchild categories
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('ef4b8b50-8f5a-42cf-bb9c-3509f104d6b4', '7eb2a701-7bc4-4156-a1e6-8e70c7e1c50d', 'Cameras', null);
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('c1147a4a-0e05-4204-bd8e-6b0e8cf8e79a', '7eb2a701-7bc4-4156-a1e6-8e70c7e1c50d', 'Lenses', null);
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('bd244528-5144-4281-a232-ca5a3d4a2417', '7eb2a701-7bc4-4156-a1e6-8e70c7e1c50d', 'Tripods', null);
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('197a6e34-aabc-4c9b-966a-1fa64a45858d', '7eb2a701-7bc4-4156-a1e6-8e70c7e1c50d', 'Gimbals', null);
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('a088f071-a963-48b6-8f48-448cbc8dd1d6', '7eb2a701-7bc4-4156-a1e6-8e70c7e1c50d', 'Lightning Equipment', null);

-- Child category "Home Electronics"
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('d856497c-3a0a-43a8-9630-3b023f76115d', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Home Electronics', 'Refrigerators, Air Conditioners, Washers...');

-- Grandchild categories
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('36691dd5-ee11-48d6-9874-c03323de0791', 'd856497c-3a0a-43a8-9630-3b023f76115d', 'Refrigerators', null);
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('a68b92dd-6519-4965-b191-9f9a12aa532d', 'd856497c-3a0a-43a8-9630-3b023f76115d', 'Ari Conditioners', null);
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('6a34861d-a053-40ac-af59-712acabb8b26', 'd856497c-3a0a-43a8-9630-3b023f76115d', 'Washers', null);
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('68b0c4d5-3ecf-4a5a-bde6-c68205dfd5cd', 'd856497c-3a0a-43a8-9630-3b023f76115d', 'Dryers', null);
INSERT INTO categories (category_id, parent_id, category_name, description) VALUES ('68b0c4d5-3ecf-4a5a-bde6-c68205dfd5cd', 'd856497c-3a0a-43a8-9630-3b023f76115d', 'Microwaves', null);









/*
f77fd8e5-7f9c-429a-bc8f-16bb3ba0a117
b2766ba5-8945-4992-a5ec-a31c35b7aec6
319edb06-30df-448d-a388-60faa4774224
a44f4470-2fb9-4375-9407-66cd10335fdd
27c8bf98-b7de-483f-86f8-9ee09d07cfa7
b5c5d1b2-d895-4f54-abce-feceaf0c7fad
ad514f8f-6493-49eb-95f5-71c00e680029
4e897814-0d0f-4ae8-afb3-93be6aae8832*/


