INSERT INTO p_delivery(id, order_id, status, departure_hub_id, destination_hub_id, city, district, streetname,
                       streetnum, detail, receiver_name, receiver_slack_id, deliver_id)
VALUES
    ('edf673b3-e9f9-4129-8355-881ece978de0', 'edf673b3-e9f9-4129-8355-881ece978de0', 'HUB_WAITING',
        'edf673b3-e9f9-4129-8355-881ece978de0', 'edf673b3-e9f9-4129-8355-881ece978de0', '서울', '마포구', '홍대입구로', '30',
        '1층', '수령자이름', 'slackId', 1),
    ('e6c3f5c7-acfc-4d1a-ba94-6e0dbb7912c6', '9cc4f593-1ca4-47f1-a9a4-2a4a255690d8', 'HUB_WAITING',
        'edf673b3-e9f9-4129-8355-881ece978de0',  'edf673b3-e9f9-4129-8355-881ece978de0', '서울', '마포구', '홍대입구로', '30',
        '1층', '수령자이름', 'slackId', 2),
    ('09c19306-5618-4deb-8ed0-09040c2e32f5', '3d1ebd4e-5c47-4871-937f-658d9e7f7607', 'HUB_WAITING',
        'edf673b3-e9f9-4129-8355-881ece978de0', 'edf673b3-e9f9-4129-8355-881ece978de0', '서울', '마포구', '홍대입구로', '30',
        '1층', '수령자이름', 'slackId', 3);
