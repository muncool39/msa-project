-- 업체 더미 데이터 5개 삽입 (UUID id 사용)
INSERT INTO p_company (user_id, id, name, business_number, hub_id, city, district, street_name, street_number, address_detail, type, status, created_at, created_by, updated_at, updated_by, is_deleted)
VALUES
    (1,'d7fa8f56-6d67-4a13-89b1-e1d210040f5e', 'ABC 전자', '123-45-6789-001', 'd23457cd-7f75-4cd4-b150-03bb557a3f72', '서울', '강남구', '테헤란로 123', '10', '2층', 'SUPPLIER', 'PENDING',NOW(), 1, NOW(), 1, false),
    (1,'bb3e028d-f0b7-4f59-b4ab-10a1d61e0531', 'XYZ 식품', '123-45-6789-002', 'd23457cd-7f75-4cd4-b150-03bb557a3f72', '서울', '종로구', '종로 101', '11', '3층', 'SUPPLIER','PENDING',NOW(), 1, NOW(), 1, false),
    (1,'f08430f1-2c5e-4b95-bd74-d4ae8d07b4a7', 'LMN 가구', '123-45-6789-003', 'd23457cd-7f75-4cd4-b150-03bb557a3f74', '부산', '해운대구', '해운대로 456', '12', '5층','SUPPLIER', 'REJECTED',NOW(), 1, NOW(), 1, false),
    (1,'b04e2f9d-11d9-48fe-9eb2-8d8f99f17eb9', 'OPQ 의류', '123-45-6789-004', 'd23457cd-7f75-4cd4-b150-03bb557a3f75', '대구', '동성로', '동성로 789', '13', '4층', 'SUPPLIER','APPROVED',NOW(), 1, NOW(), 1, false),
    (1,'ff5d7908-02c6-42e6-8e2d-3ab2f89fc4cd', 'RST 서적', '123-45-6789-005', 'd23457cd-7f75-4cd4-b150-03bb557a3f76', '광주', '충장로', '충장로 234', '14', '6층', 'SUPPLIER','APPROVED',NOW(), 1, NOW(), 1, false);

-- 상품 더미 데이터 5개 삽입 (createdBy, updatedBy, createdAt, updatedAt 고려)
INSERT INTO p_product (id, name, stock, company_id, is_out_of_stock, created_at, created_by, updated_at, updated_by, is_deleted)
VALUES
    ('0f6c285e-b268-4b3f-83bb-02082a2ad35b', '스마트폰', 100, 'd7fa8f56-6d67-4a13-89b1-e1d210040f5e', false,NOW(), 1, NOW(), 1,false),
    ('1c625b88-dc82-4b0c-9f61-b0e545ed26b2', '노트북', 50, 'bb3e028d-f0b7-4f59-b4ab-10a1d61e0531', false,NOW(), 1, NOW(), 1,false),
    ('8b6d92e1-3a99-4657-b21d-2f4f5935d241', '냉장고', 30, 'f08430f1-2c5e-4b95-bd74-d4ae8d07b4a7', false,NOW(), 1, NOW(), 1,false),
    ('1c625b88-dc82-4b0c-9f61-b0e545ed26b1', '의류 셔츠', 200, 'b04e2f9d-11d9-48fe-9eb2-8d8f99f17eb9', false,NOW(), 1, NOW(), 1,false),
    ('d76f6fdb-ff98-4648-bff5-0eab7c12f9c0', '책', 500, 'ff5d7908-02c6-42e6-8e2d-3ab2f89fc4cd', false,NOW(), 1, NOW(), 1,false);

INSERT INTO p_hub (
    id, name, city, district, street_name, street_number, address_detail, latitude, longitude, manager_id, created_at, created_by, updated_at, updated_by, is_deleted
) VALUES
      ('d23457cd-7f75-4cd4-b150-03bb557a3f72', '서울특별시 센터', '서울특별시', '송파구', '송파대로', '55', '송파대로 55', 37.514, 127.112, 1, now(), 1,now(),1, false),
      ('d23457cd-7f75-4cd4-b150-03bb557a3f74', '부산광역시 센터', '부산광역시', '동구', '중앙대로', '206', '중앙대로 206', 35.114, 129.042, 4, now(), 1, now(),1, false),
      ('d23457cd-7f75-4cd4-b150-03bb557a3f75', '대구광역시 센터', '대구광역시', '북구', '태평로', '161', '태평로 161', 35.877, 128.594, 5, now(), 1,now(),1, false),
      ('d23457cd-7f75-4cd4-b150-03bb557a3f76', '광주광역시 센터', '광주광역시', '서구', '내방로', '111', '내방로 111', 35.150, 126.890, 7, now(), 1,now(),1, false)
