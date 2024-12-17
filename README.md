<div align="center">

  # 물류 관리 및 배송 시스템을 위한 MSA 기반 플랫폼
  
  ### 팀 인피니티 엔진
</div>

## 🗨️ About Project
MSA 기반 B2B 물류 관리 및 배송 시스템입니다. <br>
대한민국 지역마다 하나씩 허브가 있으며(총 17개 허브), 시스템에 등록된 업체는 하나의 허브에 반드시 등록이 됩니다. 허브를 통해 재고 관리와 배송 처리가 가능합니다. <br>
<p>비즈니스는 다음과 같은 흐름을 가집니다. <br>
부산의 A업체가 서울의 B업체 물품 요청 -> B업체가 소속된 서울 허브에서 배송 출발 -> N개의 경유지를 거쳐 부산 허브에 도착 -> 부산 허브에서 A업체로 업체 배송 시작 -> 배송완료 </p>


### 프로젝트 기간
- `2024.12.05 ~ 2024.12.18`

## 🤝 Member
| <img width="160px" alt="시원" src="https://github.com/muncool39.png"> | <img width="160px" alt="재희" src="https://github.com/jhbreeze.png"> | <img width="160px" alt="남규" src="https://github.com/Namgyu11.png"> | <img width="160px" alt="유진" src="https://github.com/Hujin0322.png"> |
|:-------:|:----------:|:----------:|:---:|
| [문시원](https://github.com/muncool39) | [안재희](https://github.com/jhbreeze) |  [하남규](https://github.com/Namgyu11)   | [홍유진](https://github.com/Hujin0322) |
| `팀장` `유저` `허브` `허브경로` |  `주문` `배송` `배송경로` |  `배송자` `알림` `AI` | `업체` `상품` |


## 🌐 Architecture
![최종인프라](https://github.com/user-attachments/assets/936a4029-9ab4-4111-aa42-f19bfe51fb9e)

## 🖼 ERD
![논리ERD](https://github.com/user-attachments/assets/2a6cade8-700c-495f-8a0f-5b57f3f62f8d)

## 📃 API Docs
⛓[API 명세서 링크](https://www.notion.so/teamsparta/API-14b2dc3ef514813d8ea2c363f6cb3c5e)

## 🛠️ Skills
- Java17 
- Spring Boot 3.3.x
- **데이터베이스** PostgreSQL, Redis, Spring Data JPA
- **빌드** Gradle
- **인프라** Spring Cloud Eureka, Spring Cloud Gateway, Spring Cloud OpenFeign
- **버전 관리** Git, GitHub

## 🎠 Run
### 프로젝트 실행

### 회원 가입 및 로그인: /auth
- 요청 예시
```Json
{
  "username": "company12",
  "password": "imsiPassword12!",
  "email": "email@gmail.com",
  "slackId": "1234567",
  "role": "COMPANY_MANAGER"
}
```
- 요청 예시
```Json
{
  "username": "company12",
  "password": "imsiPassword12!"
}
```

### 허브 및 허브 경로 생성: /hubs, /hub-routes
- 요청 예시
```Json
{
  "name" : "부산광역시 센터",
  "city" : "부산",
  "district": "동구",
  "streetName": "중앙대로",
  "streetNumber": "206",
  "addressDetail": "1층",
  "latitude": 35.117605126596,
  "longitude": 129.045060216345,
  "managerId": "1"
}
```
- 요청 예시
```Json
{
  "sourceHubId" : "a811fdf3-4337-47ec-9e2a-6aaf519ca3e1",
  "destinationHubId" : "f789cefd-3574-42bc-b797-b4ffbf79371c"
}
```


### 🏭 업체 생성 (Company) "/companies"
>업체 생성 시 허브 ID를 확인하고, 관련된 city 값을 불러옵니다. 이후 userId(=managerId)의 존재 여부와 역할을 확인합니다. 허브 관리자는 본인 소속 허브 ID로 업체를 생성할 수 있으며, 마스터는 업체 상태를 '승인'으로 변경해야만 업체가 생성됩니다. 삭제는 is_delete 필드를 통해 논리적으로 처리됩니다.
>
>권한 관리는 아래와 같습니다.
>  - 공통: 조회 및 검색 가능
>  - 마스터: 모든 권한
>  - 허브 관리자: 소속 허브의 업체만 관리 가능
>  - 업체 관리자: 본인 담당 업체만 수정 가능
    
- 요청 예시 (마스터/ 허브 관리자의 경우 hubId 입력X)
```json
  {
  "userId": "매니저 ID",
  "name": "ㅅㅅ전자",
  "businessNumber": "3948573806",
  "hubId": "d23457cd-7f75-4cd4-b150-03bb557a3f72",
  "address": {
    "district": "강남구",
    "streetName": "테헤란로",
    "streetNumber": "129",
    "addressDetail": "5층"
  },
  "type": "SUPPLIER"
}
```

#### 🛒 상품 생성 "/companies/products"
>상품 생성 시, 업체의 존재 여부, 승인 여부, 허브 ID의 유효성을 확인합니다. is_out_of_stock 필드를 통해 재고량에 따른 품절 여부를 관리하고, is_delete 필드를 통해 논리적 삭제를 처리합니다. 업체가 삭제될 경우 소속 상품들도 함께 삭제됩니다. 
>
>권한 관리는 아래와 같습니다.
>  - 공통: 조회 및 검색 가능
>  - 마스터: 모든 권한
>  - 허브 관리자: 소속 허브의 상품만 관리 가능
>  - 업체 관리자: 본인 업체의 상품만 생성 및 수정 가능 (삭제 제외)

- 요청 예시
```json
  {
    "companyId": "d7fa8f56-6d67-4a13-89b1-e1d210040f5e",
    "name": "냉장고",
    "stock": 500
  }
```
  

### 주문 생성 및 배송 생성 POST /orders
1. 주문할 상품정보(상품아이디, 상품이름, 수량), 배송지정보, 요구사항, 수령업체id, 수령자 이름을 담아서 요청을 보냅니다.
	```json
	{
	"itemId": "0f6c285e-b268-4b3f-83bb-02082a2ad35b",
	"itemName": "스마트폰",
	"quantity": 2,
	"description": null,
	"receiverCompanyId" : "f08430f1-2c5e-4b95-bd74-d4ae8d07b4a7",
	"receiverName": "부산",
	"city": "서울",
	"district": "종로구",
	"streetName": "경희궁",
	"streetNum": "35",
	"detail": "D타워 3층"
	}
	```
 2. 상품 서비스 호출을 통해 재고 감소 처리를 합니다.
 3. 주문이 생성(상태: ORDER_REQUEST)되며 서버 내부적으로 배송 생성 서비스 호출이 진행됩니다. <br> 생성된 주문 id, 수령업체 id, 출발허브 id, 최종 도착허브 id, 배송지정보, 수령자이름, 수령자슬랙id를 담아서 요청을 보냅니다.
	```json
	{
	"orderId": "b03d26c0-890a-4af4-956c-28a22d465736",
	"receiverCompanyId": "f08430f1-2c5e-4b95-bd74-d4ae8d07b4a7",
	"departureHubId": "123e4567-e89b-12d3-a456-426614174001",
	"destinationHubId": "123e4567-e89b-12d3-a456-426614174004",
	"address": {
	"city": "부산",
	"district": "수영구",
	"streetname": "광안로",
	"streetnum": "35",
	"detail": "3층"
	},
	"receiverSlackId": "부산아이",
	"receiverName": "부산아이"
	}
	```
 4. 배송 서비스에서 허브 서비스를 호출하여 배송 경로를 생성합니다.
 5. 생성된 배송 경로를 바탕으로 배송담당자 서비스를 호출하여 배송담당자를 할당 받습니다.
 6. 생성된 경로와 배송담당자를 매칭하여 배송과 배송 경로를 성공적으로 기록합니다.
 7. 주문이 생성 완료됩니다. (상태 : ORDERED)


## 🔥 Trouble-Shooting
❗️[QueryDSL Q클래스 생성 오류](https://github.com/muncool39/msa-project/wiki/%ED%8A%B8%EB%9F%AC%EB%B8%94%EC%8A%88%ED%8C%85:-QueryDSL-Q%ED%81%B4%EB%9E%98%EC%8A%A4-%EC%83%9D%EC%84%B1-%EC%98%A4%EB%A5%98)
<br> 
❗️[@PathVariable에서 이름 생략하니까 파라미터 인식이 잘 안돼요 😥](https://github.com/muncool39/msa-project/wiki/%5BTrouble-Shooting%5D-@PathVariable%EC%97%90%EC%84%9C-%EC%9D%B4%EB%A6%84-%EC%83%9D%EB%9E%B5%ED%95%98%EB%8B%88%EA%B9%8C-%ED%8C%8C%EB%9D%BC%EB%AF%B8%ED%84%B0-%EC%9D%B8%EC%8B%9D%EC%9D%B4-%EC%9E%98-%EC%95%88%EB%8F%BC%EC%9A%94-%F0%9F%98%A5)
<br>
