<div align="center">

  # Team 인피니티엔진
  
  ### 물류 관리 및 배송 시스템을 위한 MSA 기반 플랫폼
</div>

## 🗨️ About Project
프로젝트 설명

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

## 🛠️ Skills
- Java17 
- Spring Boot 3.3.x
- **데이터베이스** PostgreSQL, Redis, Spring Data JPA
- **빌드** Gradle
- **인프라** Spring Cloud Eureka, Spring Cloud Gateway, Spring Cloud OpenFeign
- **버전 관리** Git, GitHub

## 🎠 Run
### 프로젝트 실행

### 회원 가입 및 로그인

### 허브 생성

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
  "name": "업체이름",
  "businessNumber": "사업자번호(10)",
  "hubId": "d23457cd-7f75-4cd4-b150-03bb557a3f72",
  "address": {
    "district": "강남구",
    "streetName": "테헤란로",
    "streetNumber": "129",
    "addressDetail": "5층"
  },
  "type": "SUPPLIER/RECEIVER"
}
```

#### 🧺 상품 생성 "/companies/products"
>상품 생성 시, 업체의 존재 여부, 승인 여부, 허브 ID의 유효성을 확인합니다. is_out_of_stock 필드를 통해 재고량에 따른 품절 여부를 관리하고, is_delete 필드를 추가하여 논리적 삭제를 처리합니다.

>권한 관리는 아래와 같습니다.
>  - 공통: 조회 및 검색 가능
>  - 마스터: 모든 권한
>  - 허브 관리자: 소속 허브의 상품만 관리 가능
>  - 업체 관리자: 본인 업체의 상품만 생성 및 수정 가능 (삭제 제외)

- 요청 예시
```json
  {
    "companyId": "d7fa8f56-6d67-4a13-89b1-e1d210040f5e",
    "name": "상품 이름",
    "stock": 500
  }
```
  

### 주문 생성

### 배송 생성


## 🔥 Trouble-Shooting
[트러블슈팅: QueryDSL Q클래스 생성 오류](https://github.com/muncool39/msa-project/wiki/%ED%8A%B8%EB%9F%AC%EB%B8%94%EC%8A%88%ED%8C%85:-QueryDSL-Q%ED%81%B4%EB%9E%98%EC%8A%A4-%EC%83%9D%EC%84%B1-%EC%98%A4%EB%A5%98)
