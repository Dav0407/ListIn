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



--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



--Child category "Furniture"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('0a69208f-cfbd-4f0d-91cd-9ab881754bd5', '37a2eb83-8373-4ee1-9034-6fd60ed6e5b2', 'Furniture', 'Mebel', 'Мебель','Sofas, Armchairs, Cabinets...', 'Divanlar, Armchairlar, Shkaflar...','Диваны, Кресла, Шкафы...','https://listin-app-images.s3.eu-north-1.amazonaws.com/garden-furniture.png');

--Child category "Decor & Interior"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('d44dca07-782e-4bd1-834f-d65679fc1dd1', '37a2eb83-8373-4ee1-9034-6fd60ed6e5b2', 'Decor & Interior', 'Dekor va Ichki', 'Декор и Интерьер','Chandeliers, Paintings, Posters...', 'Lyustralar, Rasmlar, Plakatlar...', 'Люстры, Картины, Постеры...','https://listin-app-images.s3.eu-north-1.amazonaws.com/garden-decor.png');

--Child category "Garden, Outdoor & Tools"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('5d24781c-2982-4b76-a5d7-dd38da237873', '37a2eb83-8373-4ee1-9034-6fd60ed6e5b2', 'Garden, Outdoor & Tools', 'Bogʻ, Ochiq havo va Asboblar', 'Сад, Открытый воздух и Инструменты','Garden Tables, Plants, Flower Pots...', 'Bog‘ stollari, O‘simliklari, Gul qozonlari...', 'Садовые столы, растения, Горшки для цветов...','https://listin-app-images.s3.eu-north-1.amazonaws.com/garden-garden.png');

--Child category "Household Appliances"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('1763002e-fc2b-48b0-a064-d4f601599745', '37a2eb83-8373-4ee1-9034-6fd60ed6e5b2', 'Household Appliances', 'Maishiy Texnika', 'Бытовая Техника','Refrigerators, Cookers, Ovens...', 'Muzlatgichlar, Pishirish plitalari, Pechlar...', 'Холодильники'', Плиты, Духовки...', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/garden-household.png');

--Child category "Tools & Hardware"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('38b08c9f-9f8b-4cbe-8f87-f9eee616771b', '37a2eb83-8373-4ee1-9034-6fd60ed6e5b2', 'Tools & Hardware', 'Asboblar va Jihozlar', 'Инструменты и Оборудование','Drills, Screwdrivers, Sanders...', 'Borchilar, Vidalar, Sanderlar...', 'Дрели, Отвёртки, Шлифовальные машины...','https://listin-app-images.s3.eu-north-1.amazonaws.com/garden-tools.png');

--Child category "Doors, Windows & Finishes"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('ce6053c5-b1a2-4cce-8245-ea6d6e1821a7', '37a2eb83-8373-4ee1-9034-6fd60ed6e5b2', 'Doors, Windows & Finishes', 'Eshiklar, Derazalar va Yoqimsizlar', 'Двери, Окна и Отделка','Windows, Door Handles, Sealants...', 'Derazalar, Eshik tutqichlari, Yopishtiruvchilar...', 'Окна, Ручки дверей, Уплотнители...','https://listin-app-images.s3.eu-north-1.amazonaws.com/garden-windows.png');

--Child category "Cleaning & Maintenance"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('a4fad764-769e-4d9f-9bdb-94612c3bd9a4', '37a2eb83-8373-4ee1-9034-6fd60ed6e5b2', 'Cleaning & Maintenance', 'Tozalash va Xizmat koʻrsatish', 'Чистка и Уход','Detergents, Brooms, Dustpans...', 'Yuvish vositalari, Metlalar, Changqopqichlar...', 'Моющее средство, Метлы, Совки...','https://listin-app-images.s3.eu-north-1.amazonaws.com/garden-maintenance.png');

--Child category "Organization & Storage"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('95550d53-2fcc-44e1-9e86-d7418010a77e', '37a2eb83-8373-4ee1-9034-6fd60ed6e5b2', 'Organization & Storage', 'Tashkil qilish va Saqlash', 'Организация и Хранение','Shelves, Storage Boxes, Closet Organizers...', 'Raflar, Saqlash qutilari, Kiyim tokchasi tashkilotchilari...', 'Полки, Коробки для хранения, Органайзеры для шкафов...', 'https://listin-app-images.s3.eu-north-1.amazonaws.com/garden-storage.jpg');



--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



--Child category "Cosmetics"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('ffbfba77-d321-49e9-950f-abad7421f363', 'e7a956ed-dc77-461e-9f29-a0a49bf35826', 'Cosmetics', 'Kosmetika', 'Косметика','Foundation, Concealer, Powder...', 'Tonal krem, Yashiruvchi krem, Pudra...', 'Тональный крем, Консилер, Пудра...','https://listin-app-images.s3.eu-north-1.amazonaws.com/health-cosmetics.png');

--Child category "Skin Care"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('351cf2e8-e302-44bd-af63-248f84170bb1', 'e7a956ed-dc77-461e-9f29-a0a49bf35826', 'Skin Care', 'Teri parvarishi', 'Уход за кожей','Face Cleansers, Face Creams, Eye Creams...', 'Yuz tozalagichlar, Yuz kremlari, Ko‘z kremlari...', 'Очищающие средства для лица, Кремы для лица, Кремы для глаз...','https://listin-app-images.s3.eu-north-1.amazonaws.com/health-skin_care.png');

--Child category "Hair Care"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('54988502-0b93-4aaa-ae0c-aa0e4cbfc626', 'e7a956ed-dc77-461e-9f29-a0a49bf35826', 'Hair Care', 'Soch parvarishi', 'Уход за волосами','Shampoos, Conditioners, Hair Oils...', 'Shampunlar, Konditsionerlar, Soch yog‘lari...', 'Шампуни, Кондиционеры, Масла для волос...','https://listin-app-images.s3.eu-north-1.amazonaws.com/health-hair_care.png');

--Child category "Personal Care"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('4d90aef7-c386-43c8-b4a2-3d25ef521c8f', 'e7a956ed-dc77-461e-9f29-a0a49bf35826', 'Personal Care', 'Shaxsiy parvarish', 'Личная гигиена','Body Lotions, Toothpaste, Toothbrushes...', 'Tana losonlari, Tish pastasi, Tish cho‘tkalari...', 'Лосьоны для тела, Зубная паста, Зубные щетки...','https://listin-app-images.s3.eu-north-1.amazonaws.com/health-personal_care.png');

--Child category "Fitness & Health"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('46f06372-12f2-43df-af9c-2df790af030a', 'e7a956ed-dc77-461e-9f29-a0a49bf35826', 'Fitness & Health', 'Fitness va sog‘liq', 'Фитнес и здоровье','Dumbbells, Thermometers, Protein Powders...', 'Dumbbellar, Termometrlar, Protein kukunlari...', 'Гантели, Термометры, Протеиновые порошки...','https://listin-app-images.s3.eu-north-1.amazonaws.com/health-fitness.png');

--Child category "Natural & Organic Products"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('bd35fd2d-a13e-47a6-95b2-8b82a1a85b29', 'e7a956ed-dc77-461e-9f29-a0a49bf35826', 'Natural & Organic Products', 'Tabiiy va organik mahsulotlar', 'Натуральные и органические продукты','Organic Face Creams, Detox Teas, Herbal Capsules...', 'Organik yuz kremlari, Detoks choylari, O‘simlik kapsulalari...', 'Органические кремы для лица, Детокс чаи, Травяные капсулы...','https://listin-app-images.s3.eu-north-1.amazonaws.com/health-natoural.png');

--Child category "Spa & Relaxation"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('15da1929-9d9d-4430-8360-d1b71ceb8eb3', 'e7a956ed-dc77-461e-9f29-a0a49bf35826', 'Spa & Relaxation', 'Spa va dam olish', 'Спа и расслабление','Essential Oils, Diffusers, Scented Candles...', 'Efir moylari, Difuzorlar, Hidli chiroqlar...', 'Эфирные масла, Диффузоры, Ароматные свечи...','https://listin-app-images.s3.eu-north-1.amazonaws.com/health-relaxation.png');


--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



--Child category "Handbags and Wallets"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('e427c633-e3d7-4f8a-be4f-ce01c9c87b1a', 'd24a6a1a-09ee-4e6b-9de6-6c50d8cb9c9b', 'Handbags and Wallets', 'Sumkalar va hamyonlar', 'Сумки и кошельки','', '','','');

--Child category "Eyewear"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('8fb5813f-c079-41ed-84f8-10b0b6cfce1d', 'd24a6a1a-09ee-4e6b-9de6-6c50d8cb9c9b', 'Eyewear', 'Ko‘zoynaklar', 'Очки','', '','','');

--Child category "Watches"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('c00e4be5-6b24-4c3f-a62b-c71cd697083b', 'd24a6a1a-09ee-4e6b-9de6-6c50d8cb9c9b', 'Watches', 'Soatlar', 'Часы','', '','','');

--Child category "Fine Jewelry"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('8471acec-c683-4b6e-9976-1523a329878c', 'd24a6a1a-09ee-4e6b-9de6-6c50d8cb9c9b', 'Fine Jewelry', 'Nozik zargarlik buyumlari', 'Ювелирные изделия','', '','','');

--Child category "Scarves and Shawls"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('f784ff5f-4d21-4b27-b4d8-275ec903e1f2', 'd24a6a1a-09ee-4e6b-9de6-6c50d8cb9c9b', 'Scarves and Shawls', 'Sharf va shaldirlar', 'Шарфы и платки','', '','','');

--Child category "Hats and Caps"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('14acaaa8-861f-43b1-b249-9a992e8fb4a4', 'd24a6a1a-09ee-4e6b-9de6-6c50d8cb9c9b', 'Hats and Caps', 'Shapkalar va kepkalar', 'Шляпы и кепки','', '','','');

--Child category "Gloves and Belts"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('2671d2c0-6bcc-4f3e-9c1d-7a7ba55ca42b', 'd24a6a1a-09ee-4e6b-9de6-6c50d8cb9c9b', 'Gloves and Belts', 'Qo‘lqoplar va kamarlar', 'Перчатки и ремни','', '','','');

--Child category "Home Accessories"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('9f616b91-4866-4f22-b894-47d85cbb57a3', 'd24a6a1a-09ee-4e6b-9de6-6c50d8cb9c9b', 'Home Accessories', 'Uy aksessuarlar', 'Аксессуары для дома','', '','','');

--Child category "Business Accessories"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('dff120dd-06f2-4a6b-b2f4-f80ce9bde510', 'd24a6a1a-09ee-4e6b-9de6-6c50d8cb9c9b', 'Business Accessories', 'Biznes aksessuarlar', 'Аксессуары для бизнеса','', '','','');

--Child category "Special Occasion Accessories"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('f6a74a33-9e23-452b-b2f5-b9499b4692c5', 'd24a6a1a-09ee-4e6b-9de6-6c50d8cb9c9b', 'Special Occasion Accessories', 'Maxsus tadbir aksessuarlar', 'Аксессуары для особых случаев','', '','','');

--Child category "Luggage and Travel"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('847c6503-8395-428d-b453-7cef6ced4b09', 'd24a6a1a-09ee-4e6b-9de6-6c50d8cb9c9b', 'Luggage and Travel', 'Yo‘l chelaklari va sayohat', 'Багаж и путешествия','', '','','');


--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



--Child category "Fresh Flowers"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('be5f9ff1-25d2-4099-8699-5fc8c8389a89', '180b0b2d-4956-420e-8f44-8542e17f883b', 'Fresh Flowers', 'Yangi gullar', 'Свежие цветы','', '','','');

--Child category "Flower Bouquets"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('5db84b99-5da6-4475-a7d0-e546dc126b83', '180b0b2d-4956-420e-8f44-8542e17f883b', 'Flower Bouquets', 'Gul guldastalari', 'Букеты цветов','', '','','');

--Child category "Artificial Flowers"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('656c93ac-2b91-4f44-89cc-807d17b3834d', '180b0b2d-4956-420e-8f44-8542e17f883b', 'Artificial Flowers', 'Sunʻiy gullar', 'Искусственные цветы','', '','','');

--Child category "Flower Arrangements"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('51d43b08-cd3d-4fb0-b6ec-e91a26cff88b', '180b0b2d-4956-420e-8f44-8542e17f883b', 'Flower Arrangements', 'Gul bezaklari', 'Цветочные композиции','', '','','');

--Child category "Gift Sets"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('58f80859-d0fa-48cd-ac45-2cd8b09ba4e4', '180b0b2d-4956-420e-8f44-8542e17f883b', 'Gift Sets', 'Sovg‘a to‘plamlari', 'Подарочные наборы','', '','','');

--Child category "Indoor Plants"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('14b9f73f-6074-43b0-a8b3-415e2521f0e3', '180b0b2d-4956-420e-8f44-8542e17f883b', 'Indoor Plants', 'Ichki o‘simliklar', 'Комнатные растения','', '','','');

--Child category "Outdoor Plants"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('6d7e90d6-8e52-4d5c-82c0-35e4db3e9006', '180b0b2d-4956-420e-8f44-8542e17f883b', 'Outdoor Plants', 'Tashqi o‘simliklar', 'Уличные растения','', '','','');

--Child category "Potted Plants"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('8eac01d7-b47a-462d-a83a-def4bc6a9fdf', '180b0b2d-4956-420e-8f44-8542e17f883b', 'Potted Plants', 'Gul tuvaklari', 'Растения в горшках','', '','','');

--Child category "Garden Plants"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('fa5819b4-4d31-47a7-af6f-e9ff1668cf2f', '180b0b2d-4956-420e-8f44-8542e17f883b', 'Garden Plants', 'Bog‘ o‘simliklari', 'Садовые растения','', '','','');

--Child category "Plush Toys"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('7db20eed-535a-4c2e-930e-39e4bee256a3', '180b0b2d-4956-420e-8f44-8542e17f883b', 'Plush Toys', 'Yumshoq o‘yinchoqlar', 'Мягкие игрушки','', '','','');

--Child category "Toy Cars and Vehicles"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('705c6b44-9b4b-4a5a-9aa2-7d11c1370935', '180b0b2d-4956-420e-8f44-8542e17f883b', 'Toy Cars and Vehicles', 'O‘yinchoq mashinalar va transport vositalari', 'Игрушечные машинки и транспорт','', '','','');

--Child category "Creative Toys"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('41d39552-5252-431e-bd19-0808d13b492d', '180b0b2d-4956-420e-8f44-8542e17f883b', 'Creative Toys', 'Ijodiy o‘yinchoqlar', 'Творческие игрушки','', '','','');

--Child category "Baby Toys"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('30e1ecd3-d3e6-4329-b969-f175eb9cf254', '180b0b2d-4956-420e-8f44-8542e17f883b', 'Baby Toys', 'Chaqaloq o‘yinchoqlari', 'Детские игрушки','', '','','');

--Child category "Educational Toys"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('099f3060-d6fd-4c30-b831-8fbbe7354269', '180b0b2d-4956-420e-8f44-8542e17f883b', 'Educational Toys', 'Ta’limiy o‘yinchoqlar', 'Образовательные игрушки','', '','','');


--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



--Child category "Dogs"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('20414607-6ede-43be-a935-fef2323aeeec', '16198ad8-3e13-4ef6-bf8b-9d295eab2967', 'Dogs', 'Itlar', 'Собаки','', '','','');

--Child category "Cats"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('ab72f660-f1a3-4f73-b9ee-8f850aeed59f', '16198ad8-3e13-4ef6-bf8b-9d295eab2967', 'Cats', 'Mushuklar', 'Кошки','', '','','');

--Child category "Birds"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('d395deed-f761-4fd1-9a4f-b243cba346a3', '16198ad8-3e13-4ef6-bf8b-9d295eab2967', 'Birds', 'Qushlar', 'Птицы','', '','','');

--Child category "Horses"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('b4f57a0a-5761-4e92-98ce-cd362fafae71', '16198ad8-3e13-4ef6-bf8b-9d295eab2967', 'Horses', 'Otlar', 'Лошади','', '','','');

--Child category "Reptiles"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('54741423-5718-48ec-941a-a0f909b51c5c', '16198ad8-3e13-4ef6-bf8b-9d295eab2967', 'Reptiles', 'Reptiliya hayvonlari', 'Пресмыкающиеся','', '','','');

--Child category "Farm Animals"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('c7f3d0fe-70e3-428b-bf1c-02659530d45d', '16198ad8-3e13-4ef6-bf8b-9d295eab2967', 'Farm Animals', 'Ferma hayvonlari', 'Сельскохозяйственные животные','', '','','');

--Child category "Pet Accessories"
INSERT INTO categories (category_id, parent_id, category_name, category_name_uz, category_name_ru, description, description_uz, description_ru, image_url) VALUES ('a397264b-0ae6-4ab8-b64e-29578b7565ed', '16198ad8-3e13-4ef6-bf8b-9d295eab2967', 'Pet Accessories', 'Uy hayvonlari aksessuarlar', 'Аксессуары для домашних животных','', '','','');

