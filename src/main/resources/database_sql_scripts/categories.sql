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

-- Child category "Apartments"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('c5e9cd9a-2b84-438f-b2fa-fb4d667f0637', '534ec9ab-448d-407f-9478-30e2f0b132ca', 'Apartments', 'Kvartiralar','Квартиры','Studio, one-bedroom, two-bedroom apartments...','Studio, bir xonali, ikki xonali kvartiralar...', 'Студии, однокомнатные, двухкомнатные квартиры...','https://listin-app-images.s3.eu-north-1.amazonaws.com/apartments-min.png');

-- Child category "Rooms"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('b30495cc-1f0a-4492-8d21-aeac3b3ea8c5', '534ec9ab-448d-407f-9478-30e2f0b132ca', 'Rooms', 'Xonalar','Комнаты','Single rooms, shared rooms, rentals...','Yakka xonalar, umumiy xonalar, ijaralar...', 'Отдельные комнаты, общие комнаты, аренда...','https://listin-app-images.s3.eu-north-1.amazonaws.com/rooms-min.png');

-- Child category "Houses, Dachas, Cottages"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('7bde739d-5842-40ee-84eb-5f74d98a4f8d', '534ec9ab-448d-407f-9478-30e2f0b132ca', 'Houses, Dachas, Cottages', 'Uylar, dachalar, kottejlar','Дома, дачи, коттеджи','Private houses, country homes, vacation cottages...','Xususiy uylar, dala hovlilar, dam olish kottejlari...', 'Частные дома, загородные дома, дачные коттеджи...','https://listin-app-images.s3.eu-north-1.amazonaws.com/houses-min.png');

-- Child category "Land Plots"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('aa695fe5-9043-4de6-8fe6-9b3020aeb07c', '534ec9ab-448d-407f-9478-30e2f0b132ca', 'Land Plots', 'Yer uchastkalari','Земельные участки','Residential, commercial, agricultural land...','Turar-joy, tijorat, qishloq xo‘jaligi yerlari...', 'Жилые, коммерческие, сельскохозяйственные земли...','https://listin-app-images.s3.eu-north-1.amazonaws.com/lands-min.png');

-- Child category "Garages and Parking Spaces"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('17da3f72-5ebc-43ee-9edc-6f00ce66a20f', '534ec9ab-448d-407f-9478-30e2f0b132ca', 'Garages and Parking Spaces', 'Garajlar va avtoturargohlar','Гаражи и машиноместа','Private garages, parking lots, covered spaces...','Xususiy garajlar, avtoturargohlar, yopiq joylar...', 'Частные гаражи, парковки, крытые стоянки...','https://listin-app-images.s3.eu-north-1.amazonaws.com/parking-min.png');

-- Child category "Commercial Real Estate"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('a44e669e-9a32-4736-9aaa-f9c5f46c03ef', '534ec9ab-448d-407f-9478-30e2f0b132ca', 'Commercial Real Estate', 'Tijorat ko‘chmas mulki','Коммерческая недвижимость','Offices, retail spaces, warehouses...','Ofislar, savdo joylari, omborxonalar...', 'Офисы, торговые площади, склады...','https://listin-app-images.s3.eu-north-1.amazonaws.com/comersial-min.png');

-- Child category "Residential Rentals"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('225853b7-67d9-4248-a5f1-96386f6d9784', '534ec9ab-448d-407f-9478-30e2f0b132ca','Residential Rentals', 'Turar-joy ijarasi', 'Аренда жилой недвижимости','Houses, apartments, and other properties available for rent.','Ijaraga beriladigan uylar, kvartiralar va boshqa turar-joylar.','Дома, квартиры и другая жилая недвижимость в аренду.','https://listin-app-images.s3.eu-north-1.amazonaws.com/residentials.png');


--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



-- Child category "Smartphones"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('11b78509-0092-44b1-a0be-f3659933dcb6', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Smartphones', 'Smartfonlar','Смартфоны','Apple, Samsung, Xiaomi...','Apple, Samsung, Xiaomi...', 'Apple, Samsung, Xiaomi...', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/smartphones.png');

-- Child category "Laptops"`
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('7f2f0f5b-abd5-4a19-95ad-f558dfa45f7d', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Laptops', 'Noutbuklar','Ноутбуки','Ultrabook, Gaming, Business...','Ultrabuk, Oʻyin noutbuklari, Biznes noutbuklari', 'Ультрабук, Игровой ноутбук, Бизнес-ноутбук', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/laptop.png');

-- Child category "Macbooks"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('e51e9a61-8f59-4390-adf4-4142f6b28645', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Macbook', 'MacBook','MacBook','Macbook Air M1, Macbook Pro M1...','Macbook Air M1, Macbook Pro M1...', 'Macbook Air M1, Macbook Pro M1...', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/macbook.png');

-- Child category "Smartwatches"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('b41e5ed9-1909-4fcb-85ca-0d209c110c5c', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Smart-Watches', 'Aqlli soatlar','Умные часы','Apple, Samsung, Huawei...','Apple, Samsung, Huawei...', 'Apple, Samsung, Huawei...', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/watches.png');

-- Child category "Tablets"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('623f9c6d-b99b-43ff-a91f-3b47941c891e', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Tablets', 'Planshetlar','Планшеты','Apple, Samsung, Huawei...','Apple, Samsung, Huawei...', 'Apple, Samsung, Huawei...', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/tablet.png');

-- Child category "Personal Computers"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('d9d0a1d3-418d-4b28-8e1d-b5fc7edc8cf2', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Personal Computer', 'Shaxsiy kompyuterlar','Персональные компьютеры','Gaming PC, Mini PC, Desktop PC...','Oʻyin kompyuterlari, Mini kompyuterlari, Stol kompyuterlari...', 'Игровой ПК, Мини-ПК, Настольный ПК', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/gaming.png');

-- Child category "Televisions"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('3e53ed81-131f-454c-91d1-f62a9f859158', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'TV', 'Televizorlar','Телевизоры','Smart TV, LED TV, OLED TV...','Aqlli TV, LED TV, OLED TV...', 'Смарт-ТВ, LED-ТВ, OLED-ТВ...', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/tv.png');

-- Child category "Gaming Consoles"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('85a7d948-ed9c-4199-a9bf-51feb805e9d9', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Consoles', 'Konsollar','Консоли','PlayStation, Xbox, Nintendo...','PlayStation, Xbox, Nintendo...', 'PlayStation, Xbox, Nintendo...', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/console.png');

-- Child category "Audio Equipment"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('4bbffb60-4832-4f24-bf74-343c2678b46e', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Audio', 'Audio','Аудио','Headphones, Speakers, Earbuds...','Quloqchinlar, Karnaylar, Simsiz quloqchinlar...', 'Наушники, Колонки, Беспроводные наушники...', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/audio.png');

-- Child category "Photography and Video"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('7eb2a701-7bc4-4156-a1e6-8e70c7e1c50d', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Photography and Video', 'Foto va video','Фото и видео','Cameras, Lenses, Tripods...','Kameralar, Obyektivlar, Shtativlar...', 'Камеры, Объективы, Штативы...', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/photo_and_video.png');

-- Child category "Home Electronics"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('d856497c-3a0a-43a8-9630-3b023f76115d', '050747ed-c6aa-409c-835c-e7cd30bc4d98', 'Home electronics', 'Uy elektr jihozlari','Бытовая техника','Refrigerators, Air Conditioners, Washers...','Muzlatgichlar, Konditsionerlar, Kir yuvish mashinalari...', 'Холодильники, Кондиционеры, Стиральные машины...', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/home_electronics.png');



--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



--Child category "Women's Clothes"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('93e23e28-e0f8-427e-aa39-81a32d447285', 'a27c8ffe-8d08-4578-bd7c-de336f4ff8d1', 'Women''s Clothes', 'Ayollar kiyimlari','Женская одежда','Dresses, Tops, Bottoms, Outerwear, Lingerie, Activewear','Ko''ylaklar, Futbolkalar, Shimlar, Ustki kiyimlar, Ichki kiyimlar, Sport kiyimlari', 'Платья, Топы, Брюки, Верхняя одежда, Нижнее бельё, Спортивная одежда', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/womens-clothes.png');

--Child category "Men's Clothes"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('a018b62e-c220-4d97-b013-5ce405c7e3b3', 'a27c8ffe-8d08-4578-bd7c-de336f4ff8d1', 'Men''s Clothes', 'Erkaklar kiyimlari','Мужская одежда','Shirts, Trousers, Jeans, Suits, Outerwear, Activewear','Ko''ylaklar, Shimlar, Jinsilar, Kostyumlar, Ustki kiyimlar, Sport kiyimlari', 'Рубашки, Брюки, Джинсы, Костюмы, Верхняя одежда, Спортивная одежда', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/mens-clothes.png');

--Child category "Kids' Clothes"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('34d7f2b8-9c6a-4f4b-a5d8-2e7b6a9d3c1f', 'a27c8ffe-8d08-4578-bd7c-de336f4ff8d1', 'Kids'' Clothes','Bolalar kiyimlari', 'Детская одежда','T-Shirts, Pants, Dresses, Jackets', 'Futbolkalar, shimlar, ko‘ylaklar, kurtkalar','Футболки, брюки, платья, куртки','https://listin-app-images.s3.eu-north-1.amazonaws.com/kids-clothes.png');

--Child category "Women's Shoes"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('67b9d2b6-da2d-4e7a-bf72-9b859e898037', 'a27c8ffe-8d08-4578-bd7c-de336f4ff8d1', 'Women''s Shoes', 'Ayollar poyabzali','Женская обувь','Heels, Flats, Sandals, Boots, Sneakers','Poshnali tuflilar, Balerinalar, Sandallar, Botinkalar, Krossovkalar', 'Туфли на каблуке, Балетки, Сандалии, Сапоги, Кроссовки', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/womens-shoes.png');

--Child category "Men's Shoes"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('d92163d5-c8da-4368-8dcd-9872d2c61e33', 'a27c8ffe-8d08-4578-bd7c-de336f4ff8d1', 'Men''s Shoes', 'Erkaklar poyabzali','Мужская обувь','Dress Shoes, Sneakers, Boots, Sandals, Loafers','Klassik tuflilar, Krossovkalar, Botinkalar, Sandallar, Loferlar', 'Классические туфли, Кроссовки, Сапоги, Сандалии, Лоферы', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/mens-shoes.png');

--Child category "Kids' Shoes"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('2e35799c-a116-4edf-9559-1285180ba9ad', 'a27c8ffe-8d08-4578-bd7c-de336f4ff8d1', 'Kids'' Shoes', 'Bolalar poyabzali', 'Детская обувь','Soft Soles, Pre-Walkers, Booties, First Walkers', 'Yumshoq taglik, yangi yuruvchilar uchun poyabzal, botinka, birinchi qadamlar uchun','Мягкая подошва, первая обувь, ботиночки, обувь для первых шагов','https://listin-app-images.s3.eu-north-1.amazonaws.com/kids-shoes.png');

