-- 업체 더미 데이터
-- hub_id: d23457cd-7f75-4cd4-b150-03bb557a3f72, city: 서울
INSERT INTO p_company (user_id, id, name, business_number, hub_id, city, district, street_name, street_number, address_detail, type, status, created_at, created_by, updated_at, updated_by, is_deleted)
VALUES
    (8, 'd7fa8f56-6d67-4a13-89b1-e1d210040f5e', 'ABC 전자', '1234567890', 'd23457cd-7f75-4cd4-b150-03bb557a3f72', '서울', '강남구', '테헤란로 123', '10', '2층', 'SUPPLIER', 'PENDING', NOW(), 1, NOW(), 1, false),
    (8, 'bb3e028d-f0b7-4f59-b4ab-10a1d61e0531', 'XYZ 식품', '2345678901', 'd23457cd-7f75-4cd4-b150-03bb557a3f72', '서울', '종로구', '종로 101', '11', '3층', 'SUPPLIER', 'PENDING', NOW(), 1, NOW(), 1, false),
    (9, 'a1f08430-2c5e-4b95-bd74-d4ae8d07b4a1', 'DEF 의약품', '3456789012', 'd23457cd-7f75-4cd4-b150-03bb557a3f72', '서울', '서초구', '서초대로 123', '12', '4층', 'SUPPLIER', 'REJECTED', NOW(), 1, NOW(), 1, false),
    (9, 'f17b2e8c-11d9-48fe-9eb2-8d8f99f17eb9', 'GHI 가전', '4567890123', 'd23457cd-7f75-4cd4-b150-03bb557a3f72', '서울', '강동구', '천호대로 456', '13', '5층', 'SUPPLIER', 'APPROVED', NOW(), 1, NOW(), 1, false);

-- hub_id: d23457cd-7f75-4cd4-b150-03bb557a3f74, city: 부산
INSERT INTO p_company (user_id, id, name, business_number, hub_id, city, district, street_name, street_number, address_detail, type, status, created_at, created_by, updated_at, updated_by, is_deleted)
VALUES
    (10, 'f08430f1-2c5e-4b95-bd74-d4ae8d07b4a7', 'LMN 가구', '1234567890', 'd23457cd-7f75-4cd4-b150-03bb557a3f74', '부산', '해운대구', '해운대로 456', '12', '5층', 'SUPPLIER', 'REJECTED', NOW(), 1, NOW(), 1, false),
    (10, 'b65f2c99-7dfe-477e-b47f-604a13cb9279', 'OPQ 의류', '2345678901', 'd23457cd-7f75-4cd4-b150-03bb557a3f74', '부산', '사상구', '사상로 789', '13', '6층', 'SUPPLIER', 'APPROVED', NOW(), 1, NOW(), 1, false),
    (11, 'd9145b13-9c8a-4b6f-9d0e-cb7b07b756de', 'RST 서적', '3456789012', 'd23457cd-7f75-4cd4-b150-03bb557a3f74', '부산', '동래구', '온천로 234', '14', '7층', 'SUPPLIER', 'PENDING', NOW(), 1, NOW(), 1, false),
    (11, 'c5e99882-6b4f-49d5-b99e-30e17bb4b393', 'UVW 카메라', '4567890123', 'd23457cd-7f75-4cd4-b150-03bb557a3f74', '부산', '진구', '부산진로 678', '15', '8층', 'SUPPLIER', 'PENDING', NOW(), 1, NOW(), 1, false);

-- hub_id: d23457cd-7f75-4cd4-b150-03bb557a3f75, city: 대구
INSERT INTO p_company (user_id, id, name, business_number, hub_id, city, district, street_name, street_number, address_detail, type, status, created_at, created_by, updated_at, updated_by, is_deleted)
VALUES
    (12, 'b04e2f9d-11d9-48fe-9eb2-8d8f99f17eb9', 'OPQ 의류', '1234567890', 'd23457cd-7f75-4cd4-b150-03bb557a3f75', '대구', '동성로', '동성로 789', '13', '4층', 'SUPPLIER', 'APPROVED', NOW(), 1, NOW(), 1, false),
    (12, 'd5c8d706-d7fb-47d2-a568-bc7639fa7773', 'XYZ 자동차', '2345678901', 'd23457cd-7f75-4cd4-b150-03bb557a3f75', '대구', '북구', '침산로 456', '14', '5층', 'SUPPLIER', 'PENDING', NOW(), 1, NOW(), 1, false),
    (14, 'fa7b2f31-b2c5-46b2-b5a6-5e39b4f6f803', 'RST 기계', '3456789012', 'd23457cd-7f75-4cd4-b150-03bb557a3f75', '대구', '수성구', '수성로 123', '15', '6층', 'SUPPLIER', 'PENDING', NOW(), 1, NOW(), 1, false),
    (14, 'c7d6c784-09ad-40a3-b408-3f2de8c8bbd0', 'LMN 전자', '4567890123', 'd23457cd-7f75-4cd4-b150-03bb557a3f75', '대구', '달서구', '달구벌대로 789', '16', '7층', 'SUPPLIER', 'REJECTED', NOW(), 1, NOW(), 1, false);

-- hub_id: d23457cd-7f75-4cd4-b150-03bb557a3f76, city: 광주
INSERT INTO p_company (user_id, id, name, business_number, hub_id, city, district, street_name, street_number, address_detail, type, status, created_at, created_by, updated_at, updated_by, is_deleted)
VALUES
    (15, 'ff5d7908-02c6-42e6-8e2d-3ab2f89fc4cd', 'RST 서적', '1234567890', 'd23457cd-7f75-4cd4-b150-03bb557a3f76', '광주', '충장로', '충장로 234', '14', '6층', 'SUPPLIER', 'APPROVED', NOW(), 1, NOW(), 1, false),
    (15, 'b52c4b9e-e836-420b-9f89-4de7fe7132c0', 'XYZ 컴퓨터', '2345678901', 'd23457cd-7f75-4cd4-b150-03bb557a3f76', '광주', '서구', '서광로 123', '15', '7층', 'SUPPLIER', 'PENDING', NOW(), 1, NOW(), 1, false),
    (16, 'f69b69a7-e3c7-4339-b49f-d412b60677be', 'ABC 패션', '3456789012', 'd23457cd-7f75-4cd4-b150-03bb557a3f76', '광주', '북구', '북구로 456', '16', '8층', 'SUPPLIER', 'REJECTED', NOW(), 1, NOW(), 1, false),
    (16, 'da9814b0-c6a6-42c1-8703-77db8556cf3a', 'LMN 인테리어', '4567890123', 'd23457cd-7f75-4cd4-b150-03bb557a3f76', '광주', '광산구', '광산로 789', '17', '9층', 'SUPPLIER', 'APPROVED', NOW(), 1, NOW(), 1, false);

-- 상품 더미 데이터 20개 삽입 (한 업체 당 상품 4개)
INSERT INTO p_product (id, name, stock, company_id, hub_id, is_out_of_stock, created_at, created_by, updated_at, updated_by, is_deleted)
VALUES
    ('0f6c285e-b268-4b3f-83bb-02082a2ad35b', '스마트폰 A', 100, 'd7fa8f56-6d67-4a13-89b1-e1d210040f5e','d23457cd-7f75-4cd4-b150-03bb557a3f72', false,NOW(), 1, NOW(), 1,false),
    ('1c625b88-dc82-4b0c-9f61-b0e545ed26b2', '노트북 A', 50, 'd7fa8f56-6d67-4a13-89b1-e1d210040f5e', 'd23457cd-7f75-4cd4-b150-03bb557a3f72', false,NOW(), 1, NOW(), 1,false),
    ('8b6d92e1-3a99-4657-b21d-2f4f5935d241', '냉장고 A', 30, 'd7fa8f56-6d67-4a13-89b1-e1d210040f5e', 'd23457cd-7f75-4cd4-b150-03bb557a3f72', false,NOW(), 1, NOW(), 1,false),
    ('1c625b88-dc82-4b0c-9f61-b0e545ed26b1', '의류 셔츠 A', 200, 'd7fa8f56-6d67-4a13-89b1-e1d210040f5e', 'd23457cd-7f75-4cd4-b150-03bb557a3f72', false,NOW(), 1, NOW(), 1,false),
    ('d76f6fdb-ff98-4648-bff5-0eab7c12f9c0', '책 A', 500, 'd7fa8f56-6d67-4a13-89b1-e1d210040f5e', 'd23457cd-7f75-4cd4-b150-03bb557a3f72', false,NOW(), 1, NOW(), 1,false),
    ('0f6c285e-b268-4b3f-83bb-02082a2ad35c', '스마트폰 B', 150, 'bb3e028d-f0b7-4f59-b4ab-10a1d61e0531','d23457cd-7f75-4cd4-b150-03bb557a3f72', false,NOW(), 1, NOW(), 1,false),
    ('1c625b88-dc82-4b0c-9f61-b0e545ed26b3', '노트북 B', 75, 'bb3e028d-f0b7-4f59-b4ab-10a1d61e0531', 'd23457cd-7f75-4cd4-b150-03bb557a3f72', false,NOW(), 1, NOW(), 1,false),
    ('8b6d92e1-3a99-4657-b21d-2f4f5935d242', '냉장고 B', 40, 'bb3e028d-f0b7-4f59-b4ab-10a1d61e0531', 'd23457cd-7f75-4cd4-b150-03bb557a3f72', false,NOW(), 1, NOW(), 1,false),
    ('1c625b88-dc82-4b0c-9f61-b0e545ed26b4', '의류 셔츠 B', 250, 'bb3e028d-f0b7-4f59-b4ab-10a1d61e0531', 'd23457cd-7f75-4cd4-b150-03bb557a3f72', false,NOW(), 1, NOW(), 1,false),
    ('d76f6fdb-ff98-4648-bff5-0eab7c12f9c1', '책 B', 450, 'bb3e028d-f0b7-4f59-b4ab-10a1d61e0531', 'd23457cd-7f75-4cd4-b150-03bb557a3f72', false,NOW(), 1, NOW(), 1,false),
    ('0f6c285e-b268-4b3f-83bb-02082a2ad35d', '스마트폰 C', 120, 'f08430f1-2c5e-4b95-bd74-d4ae8d07b4a7','d23457cd-7f75-4cd4-b150-03bb557a3f74', false,NOW(), 1, NOW(), 1,false),
    ('1c625b88-dc82-4b0c-9f61-b0e545ed26b5', '노트북 C', 60, 'f08430f1-2c5e-4b95-bd74-d4ae8d07b4a7', 'd23457cd-7f75-4cd4-b150-03bb557a3f74', false,NOW(), 1, NOW(), 1,false),
    ('8b6d92e1-3a99-4657-b21d-2f4f5935d243', '냉장고 C', 35, 'f08430f1-2c5e-4b95-bd74-d4ae8d07b4a7', 'd23457cd-7f75-4cd4-b150-03bb557a3f74', false,NOW(), 1, NOW(), 1,false),
    ('1c625b88-dc82-4b0c-9f61-b0e545ed26b6', '의류 셔츠 C', 300, 'f08430f1-2c5e-4b95-bd74-d4ae8d07b4a7', 'd23457cd-7f75-4cd4-b150-03bb557a3f74', false,NOW(), 1, NOW(), 1,false),
    ('d76f6fdb-ff98-4648-bff5-0eab7c12f9c2', '책 C', 550, 'f08430f1-2c5e-4b95-bd74-d4ae8d07b4a7', 'd23457cd-7f75-4cd4-b150-03bb557a3f74', false,NOW(), 1, NOW(), 1,false),
    ('0f6c285e-b268-4b3f-83bb-02082a2ad35e', '스마트폰 D', 110, 'b04e2f9d-11d9-48fe-9eb2-8d8f99f17eb9','d23457cd-7f75-4cd4-b150-03bb557a3f75', false,NOW(), 1, NOW(), 1,false),
    ('1c625b88-dc82-4b0c-9f61-b0e545ed26b7', '노트북 D', 55, 'b04e2f9d-11d9-48fe-9eb2-8d8f99f17eb9', 'd23457cd-7f75-4cd4-b150-03bb557a3f75', false,NOW(), 1, NOW(), 1,false),
    ('8b6d92e1-3a99-4657-b21d-2f4f5935d244', '냉장고 D', 25, 'b04e2f9d-11d9-48fe-9eb2-8d8f99f17eb9', 'd23457cd-7f75-4cd4-b150-03bb557a3f75', false,NOW(), 1, NOW(), 1,false),
    ('1c625b88-dc82-4b0c-9f61-b0e545ed26b8', '의류 셔츠 D', 150, 'b04e2f9d-11d9-48fe-9eb2-8d8f99f17eb9', 'd23457cd-7f75-4cd4-b150-03bb557a3f75', false,NOW(), 1, NOW(), 1,false),
    ('d76f6fdb-ff98-4648-bff5-0eab7c12f9c3', '책 D', 450, 'b04e2f9d-11d9-48fe-9eb2-8d8f99f17eb9', 'd23457cd-7f75-4cd4-b150-03bb557a3f75', false,NOW(), 1, NOW(), 1,false);

INSERT INTO p_hub (
    id, name, city, district, street_name, street_number, address_detail, latitude, longitude, manager_id, created_at, created_by, updated_at, updated_by, is_deleted
) VALUES
      ('d23457cd-7f75-4cd4-b150-03bb557a3f72', '서울특별시 센터', '서울특별시', '송파구', '송파대로', '55', '송파대로 55', 37.514, 127.112, 1, now(), 1,now(),1, false),
      ('d23457cd-7f75-4cd4-b150-03bb557a3f74', '부산광역시 센터', '부산광역시', '동구', '중앙대로', '206', '중앙대로 206', 35.114, 129.042, 4, now(), 1, now(),1, false),
      ('d23457cd-7f75-4cd4-b150-03bb557a3f75', '대구광역시 센터', '대구광역시', '북구', '태평로', '161', '태평로 161', 35.877, 128.594, 5, now(), 1,now(),1, false),
      ('d23457cd-7f75-4cd4-b150-03bb557a3f76', '광주광역시 센터', '광주광역시', '서구', '내방로', '111', '내방로 111', 35.150, 126.890, 7, now(), 1,now(),1, false)
