-- Main category "Auto"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('5d211862-168e-41ad-af27-a9ad461169f6', null, 'Auto', 'Avtomobil', 'Авто', '', '', '','https://listin-app-images.s3.eu-north-1.amazonaws.com/cars.png');

-- Main category "Electronics"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('050747ed-c6aa-409c-835c-e7cd30bc4d98', null, 'Electronics','Elektronika','Электроника','','', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/electronics.png');

-- Main category "Real Estate"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('534ec9ab-448d-407f-9478-30e2f0b132ca', null, 'Real Estate', 'Koʻchmas mulk','Недвижимость','','', '','https://listin-app-images.s3.eu-north-1.amazonaws.com/home_estate.png');

-- Main category "Clothes and shoes"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('a27c8ffe-8d08-4578-bd7c-de336f4ff8d1', null, 'Clothes and Shoes', 'Kiyim-kechak va poyabzal','Одежда и обувь','','', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/clothes.png');

-- Main category "Flowers and gifts"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('180b0b2d-4956-420e-8f44-8542e17f883b', null, 'Flowers and Gifts', 'Gullar va sovgʻalar','Цветы и подарки','','', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/gifts.png');

-- Main category "Beauty and Health"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('e7a956ed-dc77-461e-9f29-a0a49bf35826', null, 'Beauty and Health', 'Goʻzallik va salomatlik','Красота и здоровье','','', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/beauty_health.png');

-- Main category "Animals"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('16198ad8-3e13-4ef6-bf8b-9d295eab2967', null, 'Animals', 'Hayvonlar','Животные','','', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/animals.png');

-- Main category "For Home and Garden"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('37a2eb83-8373-4ee1-9034-6fd60ed6e5b2', null, 'For Home and Garden', 'Uy va bogʻ uchun','Для дома и сада','','', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/garden.png');

-- Main category "Luxurious Accessories"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('d24a6a1a-09ee-4e6b-9de6-6c50d8cb9c9b', null, 'Luxurious accessories', 'Hashamatli aksessuarlar','Роскошные аксессуары','','', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/accessories.png');


--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


-- Child category "Cars"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('36e2c837-320a-4ac0-9aa5-e37d0be1889f', '5d211862-168e-41ad-af27-a9ad461169f6', 'Cars', 'Avtomobillar', 'Легковые автомобили', 'Sedans, SUVs, Hatchbacks...', 'Sedanlar, SUVlar, Xetchbeklar...', 'Седаны, внедорожники, хэтчбеки...','https://listin-app-images.s3.eu-north-1.amazonaws.com/cars_car1-min.png');

-- Child category Motorcycles
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('c0011135-e8ff-4c1e-aab5-6c6a47451ab4', '5d211862-168e-41ad-af27-a9ad461169f6', 'Motorcycles', 'Mototsikllar', 'Мотоциклы', 'Sport Bikes, Cruisers, Dirt Bikes...', 'Sport velosipedlari, kreyserlar, yoʻl tanlamas velosipedlar...', 'Спортивные велосипеды, круизеры, мотоциклы для бездорожья...','https://listin-app-images.s3.eu-north-1.amazonaws.com/cars_motorcycles1-min.png');

-- Child category Commercial Vehicles
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('071bb3ee-850e-463d-a140-1ea2f8b0e4fa', '5d211862-168e-41ad-af27-a9ad461169f6', 'Commercial Vehicles', 'Tijorat transport vositalari', 'Коммерческий транспорт', 'Trucks, vans, buses...', 'Yuk mashinalari, furgonlar, avtobuslar...', 'Грузовики, фургоны, автобусы...','https://listin-app-images.s3.eu-north-1.amazonaws.com/cars_commercial1-min.png');

-- Child category Watercraft
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('8b9698c6-3b77-47cf-8ad1-3c5845e11797', '5d211862-168e-41ad-af27-a9ad461169f6', 'Watercraft', 'Suv transportlari', 'Водный транспорт', 'Boats, Jet Skis, Yachts...', 'Qayiqlar, Jet Skis, Yaxtalar ...', 'Лодки, водные мотоциклы, яхты...','https://listin-app-images.s3.eu-north-1.amazonaws.com/cars_watercrafts-min.png');

-- Child category Special Vehicles
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('501174fa-8f50-42f7-b2ca-3046c3dc9ae3', '5d211862-168e-41ad-af27-a9ad461169f6', 'Special Vehicles', 'Maxsus transport vositalari', 'Специальные транспортные средства', 'RVs & Motorhomes, Trailers, Campers...', 'RVs va avtoulovlar, treylerlar, lagerlar...', 'Дома на колесах и автодома, прицепы, кемперы...','https://listin-app-images.s3.eu-north-1.amazonaws.com/cars_special1-min.png');

-- Child category Agricultural & Construction Vehicles
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('ed82f3b9-93af-4574-8039-35f73f49ed10', '5d211862-168e-41ad-af27-a9ad461169f6', 'Agricultural & Construction Vehicles', 'Qishloq xoʻjaligi va qurilish mashinalari', 'Сельскохозяйственная и строительная техника', 'Tractors, Excavators, Forklifts...', 'Traktorlar, ekskavatorlar, yuk koʻtaruvchilar...', 'Тракторы, Экскаваторы, Вилочные погрузчики','https://listin-app-images.s3.eu-north-1.amazonaws.com/cars_agriculture1-min.png');

-- Child category Electric Vehicles
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('30ed6de6-3a89-4f57-8166-d9f6a29fd81b', '5d211862-168e-41ad-af27-a9ad461169f6', 'Electric Vehicles', 'Elektr transport vositalari', 'Электромобили', 'Electric Cars, Electric Motorcycles, Electric Scooters & Bikes...', 'Elektr avtomobillari, elektr mototsikllari, elektr skuterlari va velosipedlari...', 'Электромобили, Электрические мотоциклы, Электрические скутеры и велосипеды...','https://listin-app-images.s3.eu-north-1.amazonaws.com/cars_electric1-min.png');

-- Child category Vehicle Parts & Accessories
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('a6efc38e-a559-484c-b4f5-dc95839dfffd', '5d211862-168e-41ad-af27-a9ad461169f6', 'Vehicle Parts & Accessories', 'Avtomobil ehtiyot qismlari va aksessuarlari', 'Запчасти и аксессуары для автомобилей', 'Car Parts, Motorcycle Parts, Boat Parts...', 'Avtomobil ehtiyot qismlari, mototsikl qismlari, qayiq qismlari...', 'Автозапчасти, запчасти для мотоциклов, запчасти для лодок...','https://listin-app-images.s3.eu-north-1.amazonaws.com/cars_rental1-min.png');

-- Child category Vehicle Rentals
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('ce0b2ef9-34b2-4ae6-9c9a-d052a3a3fe6a', '5d211862-168e-41ad-af27-a9ad461169f6', 'Vehicle Rentals', 'Avtomobil ijarasi', 'Прокат автомобилей', 'Car Rentals, Motorcycle Rentals, Truck Rentals...', 'Avtomobil ijarasi, mototsikl ijarasi, yuk mashinalari ijarasi...', 'Прокат автомобилей, Прокат мотоциклов, Прокат грузовиков...','https://listin-app-images.s3.eu-north-1.amazonaws.com/cars_rentals1-min.png');


--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


-- Child category "Smartphones"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('11b78509-0092-44b1-a0be-f3659933dcb6', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Smartphones', 'Smartfonlar','Смартфоны','Apple, Samsung, Xiaomi...','Apple, Samsung, Xiaomi...', 'Apple, Samsung, Xiaomi...', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/smartphones.png');

-- Child category "Laptops"`
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('7f2f0f5b-abd5-4a19-95ad-f558dfa45f7d', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Laptops', 'Noutbuklar','Ноутбуки','Ultrabook, Gaming, Business...','Ultrabuk, Oʻyin noutbuklari, Biznes noutbuklari', 'Ультрабук, Игровой ноутбук, Бизнес-ноутбук', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/laptop.png');

-- Grandchild categories todo-> deprecated and assigned removable
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('ef6c05d2-459e-4cf0-859c-86d371a972ee', '7f2f0f5b-abd5-4a19-95ad-f558dfa45f7d', 'Ultrabook', 'Ultrabuklar','Ультрабуки',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('20da85e9-03e9-49e9-93b2-e7a297263ac4', '7f2f0f5b-abd5-4a19-95ad-f558dfa45f7d', 'Gaming', 'Oʻyin noutbuklari','Игровые ноутбуки',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('0db1cd23-0675-4f90-bbff-25af08f79db4', '7f2f0f5b-abd5-4a19-95ad-f558dfa45f7d', 'Business', 'Biznes noutbuklari','Бизнес-ноутбуки',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');

-- Child category "Macbooks"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('e51e9a61-8f59-4390-adf4-4142f6b28645', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Macbook', 'MacBook','MacBook','Macbook Air M1, Macbook Pro M1...','Macbook Air M1, Macbook Pro M1...', 'Macbook Air M1, Macbook Pro M1...', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/macbook.png');

-- Grandchild categories todo -> category name Air, Pro;  todo-> deprecate, image_urld and assigned removable
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('9783661f-6fc4-47ca-b5ec-c235d85bc80e', 'e51e9a61-8f59-4390-adf4-4142f6b28645', 'Macbook Air', 'MacBook Air','MacBook Air',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('995335e1-9c19-4571-8dfe-fc32e1296ede', 'e51e9a61-8f59-4390-adf4-4142f6b28645', 'Macbook Pro', 'MacBook Pro','MacBook Pro',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');

-- Child category "Smartwatches"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('b41e5ed9-1909-4fcb-85ca-0d209c110c5c', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Smart-Watches', 'Aqlli soatlar','Умные часы','Apple, Samsung, Huawei...','Apple, Samsung, Huawei...', 'Apple, Samsung, Huawei...', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/watches.png');

-- Child category "Tablets"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('623f9c6d-b99b-43ff-a91f-3b47941c891e', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Tablets', 'Planshetlar','Планшеты','Apple, Samsung, Huawei...','Apple, Samsung, Huawei...', 'Apple, Samsung, Huawei...', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/tablet.png');

-- Child category "Personal Computers"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('d9d0a1d3-418d-4b28-8e1d-b5fc7edc8cf2', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Personal Computer', 'Shaxsiy kompyuterlar','Персональные компьютеры','Gaming PC, Mini PC, Desktop PC...','Oʻyin kompyuterlari, Mini kompyuterlari, Stol kompyuterlari...', 'Игровой ПК, Мини-ПК, Настольный ПК', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/gaming.png');

-- Grandchild categories todo-> deprecated and assigned removable
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('73926a14-8457-4636-a586-2c17e52a076d', 'd9d0a1d3-418d-4b28-8e1d-b5fc7edc8cf2', 'Desktop PC', 'Stol kompyuterlari','Настольные ПК',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('15ef9f69-ec3a-4377-a75b-ca89fb30b527', 'd9d0a1d3-418d-4b28-8e1d-b5fc7edc8cf2', 'All-in-One', 'Monoblok kompyuter','Моноблок',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('0b259ec5-8f98-4da5-8a51-75a14585b888', 'd9d0a1d3-418d-4b28-8e1d-b5fc7edc8cf2', 'Mini PC', 'Mini kompyuterlar','Мини-ПК',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('e7d47ac9-f5c1-4891-8cc8-297169250f18', 'd9d0a1d3-418d-4b28-8e1d-b5fc7edc8cf2', 'Gaming PC', 'Oʻyin kompyuterlari','Игровые ПК',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');

-- Child category "Televisions"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('3e53ed81-131f-454c-91d1-f62a9f859158', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'TV', 'Televizorlar','Телевизоры','Smart TV, LED TV, OLED TV...','Aqlli TV, LED TV, OLED TV...', 'Смарт-ТВ, LED-ТВ, OLED-ТВ...', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/tv.png');

-- Grandchild categories todo-> deprecated and assigned removable
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('21fd2aa4-6dab-4612-b22c-dbe012ee629f', '3e53ed81-131f-454c-91d1-f62a9f859158', 'Smart TV', 'Aqlli televizorlar','Смарт-ТВ',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('a43dcfa2-ce71-4d59-806f-e1446513ff7f', '3e53ed81-131f-454c-91d1-f62a9f859158', 'LED TV', 'LED televizorlar','LED телевизоры',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('5ef2a779-6043-4d00-bcd9-e365cb0714bc', '3e53ed81-131f-454c-91d1-f62a9f859158', 'OLED TV', 'OLED televizorlar','OLED телевизоры',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('2106c333-682b-4b97-8f64-4e35c0b3bc74', '3e53ed81-131f-454c-91d1-f62a9f859158', 'QLED TV', 'QLED televizorlar','QLED телевизоры',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');

-- Child category "Gaming Consoles"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('85a7d948-ed9c-4199-a9bf-51feb805e9d9', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Consoles', 'Konsollar','Консоли','PlayStation, Xbox, Nintendo...','PlayStation, Xbox, Nintendo...', 'PlayStation, Xbox, Nintendo...', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/console.png');

-- Child category "Audio Equipment"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('4bbffb60-4832-4f24-bf74-343c2678b46e', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Audio', 'Audio','Аудио','Headphones, Speakers, Earbuds...','Quloqchinlar, Karnaylar, Simsiz quloqchinlar...', 'Наушники, Колонки, Беспроводные наушники...', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/audio.png');

-- Grandchild categories todo-> deprecated and assigned removable
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('1160e6cf-f631-4a2a-ab46-03dd4c77f8c2', '4bbffb60-4832-4f24-bf74-343c2678b46e', 'Headphones', 'Quloqchinlar','Наушники',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('b304b54f-94a9-4060-9e34-dbac24c25a86', '4bbffb60-4832-4f24-bf74-343c2678b46e', 'Speakers', 'Karnaylar','Колонки',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('c0f79268-78e3-470a-804a-78a6ec16be4a', '4bbffb60-4832-4f24-bf74-343c2678b46e', 'Earbuds', 'Quloqchinlar (Earbuds)','Беспроводные наушники',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');

-- Child category "Photography and Video"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('7eb2a701-7bc4-4156-a1e6-8e70c7e1c50d', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Photography and Video', 'Foto va video','Фото и видео','Cameras, Lenses, Tripods...','Kameralar, Obyektivlar, Shtativlar...', 'Камеры, Объективы, Штативы...', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/photo_and_video.png');

-- Grandchild categories todo-> deprecated and assigned removable
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('ef4b8b50-8f5a-42cf-bb9c-3509f104d6b4', '7eb2a701-7bc4-4156-a1e6-8e70c7e1c50d', 'Cameras', 'Kameralar','Камеры',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('c1147a4a-0e05-4204-bd8e-6b0e8cf8e79a', '7eb2a701-7bc4-4156-a1e6-8e70c7e1c50d', 'Lenses', 'Obyektivlar','Объективы',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('bd244528-5144-4281-a232-ca5a3d4a2417', '7eb2a701-7bc4-4156-a1e6-8e70c7e1c50d', 'Tripods', 'Shtativlar','Штативы',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('197a6e34-aabc-4c9b-966a-1fa64a45858d', '7eb2a701-7bc4-4156-a1e6-8e70c7e1c50d', 'Gimbals', 'Gimbal stabilizatorlar','Стабилизаторы','', '', null,'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('a088f071-a963-48b6-8f48-448cbc8dd1d6', '7eb2a701-7bc4-4156-a1e6-8e70c7e1c50d', 'Lightning Equipment', 'Yoritish uskunalari','Осветительное оборудование',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');

-- Child category "Home Electronics"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('d856497c-3a0a-43a8-9630-3b023f76115d', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Home electronics', 'Uy elektr jihozlari','Бытовая техника','Refrigerators, Air Conditioners, Washers...','Muzlatgichlar, Konditsionerlar, Kir yuvish mashinalari...', 'Холодильники, Кондиционеры, Стиральные машины...', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/home_electronics.png');

-- Grandchild categories todo-> deprecated and assigned removable
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('36691dd5-ee11-48d6-9874-c03323de0791', 'd856497c-3a0a-43a8-9630-3b023f76115d', 'Refrigerators', 'Muzlatgichlar','Холодильники',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('a68b92dd-6519-4965-b191-9f9a12aa532d', 'd856497c-3a0a-43a8-9630-3b023f76115d', 'Ari Conditioners', 'Konditsionerlar','Кондиционеры',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('6a34861d-a053-40ac-af59-712acabb8b26', 'd856497c-3a0a-43a8-9630-3b023f76115d', 'Washers', 'Kir yuvish mashinalari','Стиральные машины',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('68b0c4d5-3ecf-4a5a-bde6-c68205dfd5cd', 'd856497c-3a0a-43a8-9630-3b023f76115d', 'Dryers', 'Quritish mashinalari','Сушильные машины',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('f0dc1e98-d5dc-42d4-a2e2-38569906289d', 'd856497c-3a0a-43a8-9630-3b023f76115d', 'Microwaves', 'Mikroto‘lqinli pechlar','Микроволновые печи',null,'', '', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/');
