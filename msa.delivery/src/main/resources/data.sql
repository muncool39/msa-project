INSERT INTO p_delivery(id, order_id, status, departure_hub_id, destination_hub_id, city, district, streetname,
                       streetnum, detail, receiver_name, receiver_slack_id, deliver_id, is_deleted)
VALUES
    ('edf673b3-e9f9-4129-8355-881ece978de0', 'edf673b3-e9f9-4129-8355-881ece978de0', 'HUB_WAITING',
        'edf673b3-e9f9-4129-8355-881ece978de0', 'edf673b3-e9f9-4129-8355-881ece978de0', '서울', '마포구', '홍대입구로', '30',
        '1층', '수령자이름', 'slackId', 1, false),
    ('e6c3f5c7-acfc-4d1a-ba94-6e0dbb7912c6', '9cc4f593-1ca4-47f1-a9a4-2a4a255690d8', 'HUB_WAITING',
        'b87dde77-00c7-4d33-a2b9-4470a8672d09',  'edf673b3-e9f9-4129-8355-881ece978de0', '서울', '마포구', '홍대입구로', '30',
        '1층', '수령자이름', 'slackId', 2, false),
    ('09c19306-5618-4deb-8ed0-09040c2e32f5', '3d1ebd4e-5c47-4871-937f-658d9e7f7607', 'HUB_WAITING',
        'b436cd24-0bb0-485b-b333-14cc533d901a', 'edf673b3-e9f9-4129-8355-881ece978de0', '서울', '마포구', '홍대입구로', '30',
        '1층', '수령자이름', 'slackId', 3, false);

INSERT INTO p_delivery_route_history(id, delivery_id, sequence, deliver_id, departure_hub_id, destination_hub_id,
                                     estimated_distance, estimated_time, actual_distance, actual_time, status)
VALUES
    ('92a483e6-a6f5-4fcc-88dc-69f8c8030171', 'edf673b3-e9f9-4129-8355-881ece978de0', 1, 100 ,
  'edf673b3-e9f9-4129-8355-881ece978de0', 'edf673b3-e9f9-4129-8355-881ece978de0', 24.5, 240, 30.1, 280, 'HUB_WAITING'),
('697ebb25-ad5d-4fef-868f-5aa9c2c510ed', 'edf673b3-e9f9-4129-8355-881ece978de0', 2, 101 ,
    'edf673b3-e9f9-4129-8355-881ece978de0', 'edf673b3-e9f9-4129-8355-881ece978de0', 24.5, 240, 30.1, 280, 'HUB_WAITING'),
('b2999667-78db-41e5-92e5-483890a41e24', 'edf673b3-e9f9-4129-8355-881ece978de0', 3, 102 ,
    'edf673b3-e9f9-4129-8355-881ece978de0', 'edf673b3-e9f9-4129-8355-881ece978de0', 24.5, 240, 30.1, 280, 'HUB_WAITING');