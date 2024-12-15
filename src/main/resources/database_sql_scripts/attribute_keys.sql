--Smartphones attributes
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('bd4dbd51-8e3a-4725-93cd-61179b328245','string', 'Select Brand', 'Select Model', 'oneSelectable', 'oneSelectable', 'Smartphone Brand');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('677d0449-8b79-43ca-b7ce-7d347ad4c685','string','Select Storage', 'null', 'oneSelectable', 'null', 'Smartphone Storage');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('e0d20756-42e7-44db-aaf1-73a3dfbd24ea','string','Select Ram', 'null', 'oneSelectable', 'null', 'Smartphone RAM');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('438712ca-30b8-47ce-bd14-b08142f31d24','string','Select Color', 'null', 'colorSelectable', 'null', 'Smartphone Color');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('81728a91-0557-4e5f-829c-02fee2ef6c7c','string','Additional accessories', 'null', 'multiSelectable', 'null', 'Accessories');

--Laptops attributes
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('6f7fdb2d-1f30-49b7-8c69-83df6269b5c9','string','Select Type', 'null', 'oneSelectable', 'null', 'Laptop Type');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('c0a2e2ce-7fb3-4306-944d-b6495ec73227','string','Select Brand', 'Select Model', 'oneSelectable', 'oneSelectable',  'Laptop Brand');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('b2635cd6-4100-4eea-9e7f-9d010166d7f2','string','Select  Processor Brands', 'null', 'oneSelectable', 'null', 'Laptop Processor Brands');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('c601d5f6-7a14-4f07-8960-3d2b7deed5d2','string','Select Storage Types', 'null', 'multiSelectable', 'null', 'Laptop Storage Types'); --todo -> ssd=numeric, hdd=numeric
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('5be6a6e1-4499-442c-886a-c439fec6e9a4','string','Select GPU', 'null', 'oneSelectable', 'null', 'GPU');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('66287880-efbd-4143-9ac5-8017d9f0fa55','integer','Select Screen Size', 'null', 'oneSelectable', 'null', 'Laptop Screen Size');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('7e26c4a0-8b45-4739-be81-0f93c4322bac','string','Select Ram', 'null', 'oneSelectable', 'null', 'Laptop RAM');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('2b7ceac5-d765-40f2-9282-76832d245726','string','Select Storage', 'null', 'oneSelectable', 'null', 'Laptop Storage');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('f43b1235-4e23-4011-9bbe-88d820c6892c','string','Select Color', 'null', 'colorSelectable', 'null', 'Laptop Color');

--Macbooks attributes
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('f0658257-cfc1-4e10-96ad-0af8fe7360f3','string','Select Model', 'null', 'oneSelectable', 'null', 'Macbook Model');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('a5f79e5f-dc21-4e76-94b9-c7cd6775c00e','string','Select Storage', 'null', 'oneSelectable', 'null', 'Macbook Storage');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('365324d3-1fe1-4d05-ab2f-63fa904339b9','string','Select Ram', 'null', 'oneSelectable', 'null', 'Macbook RAM');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('cc85b860-f078-41a3-a1f6-d2d7c35b3448','string','Select Battery Capacity', 'oneSelectable', 'null', '', 'Macbook Battery Capacity');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('a6612018-a8a3-4e46-bae7-8fd1f588beb8','string','Select Color', 'null', 'colorSelectable', 'null', 'Macbook Color');

--SmartWatches attributes
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('e420017f-3b54-4236-a839-361b489825bd','string', 'Select Brand', 'Select Model', 'oneSelectable', 'oneSelectable', 'Smartwatch Brand');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('67e28662-8b28-4dd2-bd63-26df677ed5f4','string','Select Color', 'null', 'colorSelectable', 'null', 'Smartwatch Color');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('34c2eb5b-7130-450a-9f2c-fee714199a4f','string','Additional accessories', 'null', 'multiSelectable', 'null', 'Smartwatch Accessories');

--Tablet attributes
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('973884b5-63b5-4ad0-9ca6-7de2f8a40ab3','string','Select Brand', 'Select Model', 'oneSelectable', 'oneSelectable', 'Tablet Brand');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('a57f28a9-1e23-42f4-b1d8-63f35c164cd3','string','Select Storage', 'null', 'oneSelectable', 'null', 'Tablet Storage');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('da05c6e8-126d-451c-b10a-d2da8ec5eadf','string','Select Screen Size', 'null', 'oneSelectable', 'null', 'Tablet Screen Size');

--PC attributes
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('3d48a0e2-bf51-4d2c-a9fe-121a91aad8bf','string','Select Type', 'null', 'oneSelectable', 'null', 'PC Type');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('1697540a-f8d1-4fd8-bc91-5bdd19697988','string','Select Brand', 'Select Model', 'oneSelectable', 'oneSelectable', 'PC Brand');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('059fc660-4d24-43c2-8d98-a0848e6ac7b3','string','Select Processor Brand', 'null', 'oneSelectable', 'null', 'PC CPU Brand');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('90ccba65-cb4e-414d-acca-d85327484cde','string','Select Storage Type', 'null', 'multiSelectable', 'null', 'PC Storage Type'); --todo -> ssd=numeric, hdd=numeric
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('cef16c16-812d-4572-b80b-292cbf8d7d0c','string','Select Storage', 'null', 'oneSelectable', 'null', 'PC Storage');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('346a79f0-7fa9-45b3-99d5-9df99794baf1','string','Select RAM', 'null', 'oneSelectable', 'null', 'PC RAM');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('8511663a-9738-465e-a2d1-9ba8b65e460b','boolean','Includes Monitor', 'null', 'oneSelectable', 'null', 'PC Monitor');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('d90ef61b-85a9-4ce8-bdfd-fde412efea83','string','Additional Accessories', 'null', 'multiSelectable', 'null', 'PC Additional Accessories');

--TV attributes
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('9d74a5b4-f7c1-46a9-9860-b25c72c081e5','string','Select Type', 'null', 'oneSelectable', 'null', 'TV Type');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('868dc3ff-ae14-48e6-ab01-08bb6cbe497b','string','Select Brand', 'null', 'oneSelectable', 'null', 'TV Brand');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('509df700-a0be-4c47-8e2c-2053401d7ef5','string','Display Resolution', 'null', 'oneSelectable', 'null', 'TV Resolution');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('7b882258-de90-48fa-a5ce-5f5be45fa2ba','string','Screen Size', 'null', 'oneSelectable', 'null', 'TV diagonal');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('09ce8b9a-48c2-45a0-b748-6fdbd3cc465e','boolean','Smart TV', 'null', 'oneSelectable', 'null', 'TV is Smart');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('19a9b2e0-bd16-4728-b3ff-878a79019227','boolean','HDR Support', 'null', 'oneSelectable', 'null', 'TV has HDR');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('b88bc920-add8-495a-b789-dad1d846d655','boolean','Wall Mount Compatible', 'null', 'oneSelectable', 'null', 'TV is Wall Mount Compatible');

--Gaming Consoles attributes
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('76662cfc-2a41-4a6a-9289-5e2a905ae052','string','Select Brand', 'Select Model', 'oneSelectable', 'oneSelectable', 'Console Brand');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('ec5011f4-3dee-4455-8dcf-4eeb57118f30','string','Select Storage', 'null', 'oneSelectable', 'null', 'Console Storage');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('d9cb2699-29e1-469c-8f6e-356ef25caa62','boolean','VR Support', 'null', 'oneSelectable', 'null', 'Console has VR support');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('7425a352-5548-49f8-991d-bf44a25cfbed','boolean','Game Bundles', 'null', 'oneSelectable', 'null', 'Console Game Bundles');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('bbec554b-851e-4af4-9a53-6ac7fab090ea','string','Controller Type', 'null', 'oneSelectable', 'null', 'Console Controller Type');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('be322821-332e-4579-9ae9-1dc04e3e93b8','string','Select Color', 'null', 'colorSelectable', 'null', 'Console Color');

--Audio Equipment attributes
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('e1ad8d80-ff32-4669-87aa-aef571a538c8','string','Select Type', 'null', 'oneSelectable', 'null', 'Audio Equipment Type');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('577c04ee-3df2-4792-b9d5-6e6c0fcb29d8','string','Connectivity Types', 'null', 'multiSelectable', 'null', 'Audio Equipment Connectivity Types');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('4f7f1247-c988-4adb-bec4-d293d3816f4c','string','Select Color', 'null', 'colorSelectable', 'null', 'Audio Equipment color');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('76ddb474-1150-4eaf-8b1d-3e5d9dee2e35','string','Noise Cancellation', 'null', 'oneSelectable', 'null', 'Audio Equipment Noise Cancellation');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('78054b5e-3ccf-4028-84e4-3caf5e55052a','string','Water Resistance', 'null', 'oneSelectable', 'null', 'Audio Equipment Water Resistance Standard');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('0b131921-9b8c-416a-a8ab-51c4b185abf8','boolean','Microphone', 'null', 'oneSelectable', 'null', 'Audio Equipment has Microphone');

--Photo and Video attributes
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('a24bcfea-bce5-48a8-8b7a-fcd0cfacf97c','string','Select Equipment Type', 'null', 'oneSelectable', 'null', 'Photo/Video Equipment Type');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('4b10e786-7060-4f47-8978-7ed1409816ac','string','Weight', 'null', 'oneSelectable', 'null', 'Photo/Video Equipment Weight');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('d4f7f031-88b7-4a4e-aa9b-1e53c68b9b70','string','Megapixels', 'null', 'oneSelectable', 'null', 'Camera Megapixels');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('d2b8185b-0cad-4e19-b6a5-0e1deda15d71','string','Video Resolution', 'null', 'oneSelectable', 'null', 'Camera Video Resolution');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('6deabe92-a76c-4a26-bc43-ae04a9d42c8a','string','Sensor Type', 'null', 'oneSelectable', 'null', 'Photo/Video equipment Sensor Type');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('ecd6cf34-e80b-40f9-a5ba-f88d8dc70ded','string','Stabilization', 'null', 'oneSelectable', 'null', 'Camera Stabilization Type');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('4f46dd1c-ba8e-4e5c-9c51-3e47df87abf1','string','ISO Range', 'null', 'oneSelectable', 'null', 'Camera ISO Range');

--Home Electronics attributes
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('1fb4b553-a8d8-4563-9352-5a8889c2a13f','string','Select Type', 'Select Brand', 'oneSelectable', 'oneSelectable', 'Home Electronics Type'); --todo -> Refrigerator, AC, ...
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('046fc206-f7f5-4c7d-aabf-5550666b6b99','string','Select Color', 'null', 'colorSelectable', 'null', 'Home Electronics Color');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('a1fa350a-a2a9-420c-a75e-dda7917e317b','string','Control Type', 'null', 'oneSelectable', 'null', 'Home Electronics Control Type');
INSERT INTO attribute_keys (attribute_id, data_type, helper_text, sub_helper_text, widget_type, sub_widget_type, name) VALUES ('3fc333c3-096d-4762-a60e-e0a2368ec8c8','string','Warranty Period', 'null', 'oneSelectable', 'null', 'Home Electronics Warranty period');



