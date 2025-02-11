<div align="center">

  # 물류 관리 및 배송 시스템을 위한 MSA 기반 플랫폼
</div>
<div align="right">
	팀 인피니티 엔진
</div>

## 🗨️ About Project
SpringCloud 환경의 MSA 기반 B2B 물류 관리 및 배송 시스템을 구현한 프로젝트입니다. <br>
대한민국 지역마다 하나씩 허브가 있으며, 시스템에 등록된 업체는 하나의 허브에 반드시 등록이 됩니다. 허브를 통해 재고 관리와 배송 처리가 가능합니다. <br>

비즈니스는 다음과 같은 흐름을 가집니다. 

<p align="center"><img src="https://github.com/user-attachments/assets/d9c9f3b1-803f-4946-9ec8-5a17e730e2e1" width="900"/></p>

- **예시**: 부산의 A업체가 서울의 B업체 물품 요청 -> B업체가 소속된 서울 허브에서 배송 출발 -> N개의 경유지를 거쳐 부산 허브에 도착 -> 부산 허브에서 A업체로 업체 배송 시작 -> 배송완료

### 프로젝트 기간
- `2024.12.05 ~ 2024.12.18`

## 🤝 Member
| <img width="160px" alt="시원" src="https://github.com/muncool39.png"> | <img width="160px" alt="재희" src="https://github.com/jhbreeze.png"> | <img width="160px" alt="남규" src="https://github.com/Namgyu11.png"> | <img width="160px" alt="유진" src="https://github.com/Hujin0322.png"> |
|:-------:|:----------:|:----------:|:---:|
| [문시원](https://github.com/muncool39) | [안재희](https://github.com/jhbreeze) |  [하남규](https://github.com/Namgyu11)   | [홍유진](https://github.com/Hujin0322) |
| `팀장` `유저` `허브` `허브경로` |  `주문` `배송` `배송경로` |  `배송자` `알림` `AI` | `업체` `상품` |

<br>

## 🌐 Architecture
![최종인프라](https://github.com/user-attachments/assets/936a4029-9ab4-4111-aa42-f19bfe51fb9e)
<br>

## 🖼 ERD
![논리ERD](https://github.com/user-attachments/assets/2a6cade8-700c-495f-8a0f-5b57f3f62f8d)
<br>

## 📃 API Docs
⛓[API 명세서 링크](https://www.notion.so/teamsparta/API-14b2dc3ef514813d8ea2c363f6cb3c5e)
<br>

## 🛠️ Skills
- Java17 
- Spring Boot 3.3.x
- **데이터베이스** PostgreSQL, Redis, Spring Data JPA
- **빌드** Gradle
- **인프라** Spring Cloud Eureka, Spring Cloud Gateway, Spring Cloud OpenFeign
- **버전 관리** Git, GitHub


## 🔥 Trouble-Shooting
❗️[QueryDSL Q클래스 생성 오류](https://github.com/muncool39/msa-project/wiki/%ED%8A%B8%EB%9F%AC%EB%B8%94%EC%8A%88%ED%8C%85:-QueryDSL-Q%ED%81%B4%EB%9E%98%EC%8A%A4-%EC%83%9D%EC%84%B1-%EC%98%A4%EB%A5%98)
<br> 
❗️[@PathVariable에서 이름 생략하니까 파라미터 인식이 잘 안돼요 😥](https://github.com/muncool39/msa-project/wiki/%5BTrouble-Shooting%5D-@PathVariable%EC%97%90%EC%84%9C-%EC%9D%B4%EB%A6%84-%EC%83%9D%EB%9E%B5%ED%95%98%EB%8B%88%EA%B9%8C-%ED%8C%8C%EB%9D%BC%EB%AF%B8%ED%84%B0-%EC%9D%B8%EC%8B%9D%EC%9D%B4-%EC%9E%98-%EC%95%88%EB%8F%BC%EC%9A%94-%F0%9F%98%A5)
<br>
<br>

## 📃 실행 스크립트
<details>
<summary>👥 <strong>회원 가입 및 로그인: POST /auth</strong></summary>
<ul>
<li>요청 예시<pre><code class="lang-Json">{
  <span class="hljs-attr">"username"</span>: <span class="hljs-string">"company12"</span>,
  <span class="hljs-attr">"password"</span>: <span class="hljs-string">"imsiPassword12!"</span>,
  <span class="hljs-attr">"email"</span>: <span class="hljs-string">"email@gmail.com"</span>,
  <span class="hljs-attr">"slackId"</span>: <span class="hljs-string">"1234567"</span>,
  <span class="hljs-attr">"role"</span>: <span class="hljs-string">"COMPANY_MANAGER"</span>
}
</code></pre>
</li>
<li>요청 예시<pre><code class="lang-Json">{
  <span class="hljs-attr">"username"</span>: <span class="hljs-string">"company12"</span>,
  <span class="hljs-attr">"password"</span>: <span class="hljs-string">"imsiPassword12!"</span>
}
</code></pre>
</li>
</ul>
</details>

<details>
<summary>🏢 <strong>허브 및 허브 경로 생성: POST /hubs, /hub-routes</strong></summary>

<ul>
<li>요청 예시<pre><code class="lang-Json">{
  <span class="hljs-attr">"name"</span> : <span class="hljs-string">"부산광역시 센터"</span>,
  <span class="hljs-attr">"city"</span> : <span class="hljs-string">"부산"</span>,
  <span class="hljs-attr">"district"</span>: <span class="hljs-string">"동구"</span>,
  <span class="hljs-attr">"streetName"</span>: <span class="hljs-string">"중앙대로"</span>,
  <span class="hljs-attr">"streetNumber"</span>: <span class="hljs-string">"206"</span>,
  <span class="hljs-attr">"addressDetail"</span>: <span class="hljs-string">"1층"</span>,
  <span class="hljs-attr">"latitude"</span>: <span class="hljs-number">35.117605126596</span>,
  <span class="hljs-attr">"longitude"</span>: <span class="hljs-number">129.045060216345</span>,
  <span class="hljs-attr">"managerId"</span>: <span class="hljs-string">"1"</span>
}
</code></pre>
</li>
<li>요청 예시<pre><code class="lang-Json">{
  <span class="hljs-attr">"sourceHubId"</span> : <span class="hljs-string">"a811fdf3-4337-47ec-9e2a-6aaf519ca3e1"</span>,
  <span class="hljs-attr">"destinationHubId"</span> : <span class="hljs-string">"f789cefd-3574-42bc-b797-b4ffbf79371c"</span>
}
</code></pre>
</li>
</ul>

</details>

<details>
<summary>🏬 <strong> 업체 생성: POST /companies</strong></summary>
<blockquote>
<p>업체 생성 시 허브 ID를 확인하고, 관련된 city 값을 불러옵니다. 이후 userId(=managerId)의 존재 여부와 역할을 확인합니다. 허브 관리자는 본인 소속 허브 ID로 업체를 생성할 수 있으며, 마스터는 업체 상태를 &#39;승인&#39;으로 변경해야만 업체가 생성됩니다. 삭제는 is_delete 필드를 통해 논리적으로 처리됩니다.</p>
<p>권한 관리는 아래와 같습니다.</p>
<ul>
<li>공통: 조회 및 검색 가능</li>
<li>마스터: 모든 권한</li>
<li>허브 관리자: 소속 허브의 업체만 관리 가능</li>
<li>업체 관리자: 본인 담당 업체만 수정 가능</li>
</ul>
</blockquote>
<ul>
<li>요청 예시 (마스터/ 허브 관리자의 경우 hubId 입력X)<pre><code class="lang-json">{
  <span class="hljs-attr">"userId"</span>: <span class="hljs-string">"매니저 ID"</span>,
  <span class="hljs-attr">"name"</span>: <span class="hljs-string">"튼튼전자"</span>,
  <span class="hljs-attr">"businessNumber"</span>: <span class="hljs-string">"3948573806"</span>,
  <span class="hljs-attr">"hubId"</span>: <span class="hljs-string">"d23457cd-7f75-4cd4-b150-03bb557a3f72"</span>,
  <span class="hljs-attr">"address"</span>: {
    <span class="hljs-attr">"district"</span>: <span class="hljs-string">"강남구"</span>,
    <span class="hljs-attr">"streetName"</span>: <span class="hljs-string">"테헤란로"</span>,
    <span class="hljs-attr">"streetNumber"</span>: <span class="hljs-string">"129"</span>,
    <span class="hljs-attr">"addressDetail"</span>: <span class="hljs-string">"5층"</span>
  },
  <span class="hljs-attr">"type"</span>: <span class="hljs-string">"SUPPLIER"</span>
}
</code></pre>
</li>
</ul>
</details>

<details>
<summary>🛍<strong> 상품 생성: POST /companies/products</strong></summary>

<blockquote>
<p>상품 생성 시, 업체의 존재 여부, 승인 여부, 허브 ID의 유효성을 확인합니다. is_out_of_stock 필드를 통해 재고량에 따른 품절 여부를 관리하고, is_delete 필드를 통해 논리적 삭제를 처리합니다. 업체가 삭제될 경우 소속 상품들도 함께 삭제됩니다. </p>
<p>권한 관리는 아래와 같습니다.</p>
<ul>
<li>공통: 조회 및 검색 가능</li>
<li>마스터: 모든 권한</li>
<li>허브 관리자: 소속 허브의 상품만 관리 가능</li>
<li>업체 관리자: 본인 업체의 상품만 생성 및 수정 가능 (삭제 제외)</li>
</ul>
</blockquote>
<ul>
<li>요청 예시<pre><code class="lang-json">{
  <span class="hljs-attr">"companyId"</span>: <span class="hljs-string">"d7fa8f56-6d67-4a13-89b1-e1d210040f5e"</span>,
  <span class="hljs-attr">"name"</span>: <span class="hljs-string">"냉장고"</span>,
  <span class="hljs-attr">"stock"</span>: <span class="hljs-number">500</span>
}
</code></pre>
</li>
</ul>

</details>
  
<details>
<summary>🛒 <strong>주문 생성 및 배송 생성: POST /orders</strong></summary>

<ol>
<li>주문할 상품정보(상품아이디, 상품이름, 수량), 배송지정보, 요구사항, 수령업체id, 수령자 이름을 담아서 요청을 보냅니다.<pre><code class="lang-json"> {
 <span class="hljs-attr">"itemId"</span>: <span class="hljs-string">"0f6c285e-b268-4b3f-83bb-02082a2ad35b"</span>,
 <span class="hljs-attr">"itemName"</span>: <span class="hljs-string">"스마트폰"</span>,
 <span class="hljs-attr">"quantity"</span>: <span class="hljs-number">2</span>,
 <span class="hljs-attr">"description"</span>: <span class="hljs-literal">null</span>,
 <span class="hljs-attr">"receiverCompanyId"</span> : <span class="hljs-string">"f08430f1-2c5e-4b95-bd74-d4ae8d07b4a7"</span>,
 <span class="hljs-attr">"receiverName"</span>: <span class="hljs-string">"부산"</span>,
 <span class="hljs-attr">"city"</span>: <span class="hljs-string">"서울"</span>,
 <span class="hljs-attr">"district"</span>: <span class="hljs-string">"종로구"</span>,
 <span class="hljs-attr">"streetName"</span>: <span class="hljs-string">"경희궁"</span>,
 <span class="hljs-attr">"streetNum"</span>: <span class="hljs-string">"35"</span>,
 <span class="hljs-attr">"detail"</span>: <span class="hljs-string">"D타워 3층"</span>
 }
</code></pre>
<li>상품 서비스 호출을 통해 재고 감소 처리를 합니다.</li>
<li>주문이 생성(상태: ORDER_REQUEST)되며 서버 내부적으로 배송 생성 서비스 호출이 진행됩니다. <br> 생성된 주문 id, 수령업체 id, 출발허브 id, 최종 도착허브 id, 배송지정보, 수령자이름, 수령자슬랙id를 담아서 요청을 보냅니다.<pre><code class="lang-json">{
  <span class="hljs-attr">"orderId"</span>: <span class="hljs-string">"b03d26c0-890a-4af4-956c-28a22d465736"</span>,
  <span class="hljs-attr">"receiverCompanyId"</span>: <span class="hljs-string">"f08430f1-2c5e-4b95-bd74-d4ae8d07b4a7"</span>,
  <span class="hljs-attr">"departureHubId"</span>: <span class="hljs-string">"123e4567-e89b-12d3-a456-426614174001"</span>,
  <span class="hljs-attr">"destinationHubId"</span>: <span class="hljs-string">"123e4567-e89b-12d3-a456-426614174004"</span>,
  <span class="hljs-attr">"address"</span>: {
    <span class="hljs-attr">"city"</span>: <span class="hljs-string">"부산"</span>,
    <span class="hljs-attr">"district"</span>: <span class="hljs-string">"수영구"</span>,
    <span class="hljs-attr">"streetname"</span>: <span class="hljs-string">"광안로"</span>,
    <span class="hljs-attr">"streetnum"</span>: <span class="hljs-string">"35"</span>,
    <span class="hljs-attr">"detail"</span>: <span class="hljs-string">"3층"</span>
  },
  <span class="hljs-attr">"receiverSlackId"</span>: <span class="hljs-string">"부산아이"</span>,
  <span class="hljs-attr">"receiverName"</span>: <span class="hljs-string">"부산아이"</span>
}
</code></pre>
</li>
<li>배송 서비스에서 허브 서비스를 호출하여 배송 경로를 생성합니다.</li>
<li>생성된 배송 경로를 바탕으로 배송담당자 서비스를 호출하여 배송담당자를 할당 받습니다.</li>
<li>생성된 경로와 배송담당자를 매칭하여 배송과 배송 경로를 성공적으로 기록합니다.</li>
<li>주문이 생성 완료됩니다. (상태 : ORDERED)</li>
</li>
</ol>

</details>

<details>
<summary>👩‍💻 <strong>AI 요청 생성: POST /ai-requests</strong></summary>

<blockquote>
<p><strong>요청 시 AI에 데이터를 전달하여 분석된 최종 발송 시한 정보를 도출합니다.</strong>  </p>
<p>AI는 입력된 요청 정보를 기반으로 최종 발송 시한을 반환하며, 요청 데이터는 기록됩니다.  </p>
<p><strong>권한 관리</strong>  </p>
<ul>
<li><code>MASTER</code> 권한을 가진 사용자만 접근 가능  </li>
</ul>
</blockquote>
<h4 id="-"><strong>요청 예시</strong></h4>
<pre><code class="lang-json">{  
  <span class="hljs-attr">"requestData"</span>: <span class="hljs-string">"상품 및 수량 정보: 냉장고 20대, 주문 요청 사항: 1월 15일 오후 2시까지 도착, 발송지: 인천 허브 센터, 경유지: 대전 허브 센터, 도착지: 서울 강남구 테헤란로 101 3층"</span>  
}
</code></pre>
<h4 id="-"><strong>응답 예시</strong></h4>
<pre><code class="lang-json">{  
  <span class="hljs-attr">"aiRequestId"</span>: <span class="hljs-string">"c55e3bc2-9a3f-4a19-89f2-823c2f0b40f1"</span>,  
  <span class="hljs-attr">"requestData"</span>: <span class="hljs-string">"상품 및 수량 정보: 냉장고 20대, 주문 요청 사항: 1월 15일 오후 2시까지 도착, 발송지: 인천 허브 센터, 경유지: 대전 허브 센터, 도착지: 서울 강남구 테헤란로 101 3층"</span>,  
  <span class="hljs-attr">"answer"</span>: <span class="hljs-string">"최종 발송 시한은 1월 13일 오전 9시입니다."</span>  
}
</code></pre>

</details>

<details>
<summary>🔔 <strong>Slack 알림 생성: POST /slack-notifications</strong></summary>

<blockquote>
<p><strong>Slack API를 통해 알림 메시지를 생성하고 담당자에게 알립니다.</strong>  </p>
<p>생성된 알림 데이터는 저장되며 이후 수정, 삭제, 조회가 가능합니다.  </p>
<p><strong>권한 관리</strong>  </p>
<ul>
<li>공통: 조회 및 검색 가능  </li>
<li><code>MASTER</code>: 모든 알림 관리  </li>
<li><code>HUB_MANAGER</code>: 소속 허브의 알림만 관리  </li>
</ul>
</blockquote>
<h4 id="-"><strong>요청 예시</strong></h4>
<pre><code class="lang-json">{  
  <span class="hljs-attr">"slackRecipientId"</span>: <span class="hljs-string">"slack-user-123"</span>,  
  <span class="hljs-attr">"message"</span>: <span class="hljs-string">"주문 번호: 12345\n상품 정보: 냉장고 20대\n발송지: 인천 허브 센터\n도착지: 서울 강남구 테헤란로 101 3층\n최종 발송 시한은 1월 13일 오전 9시입니다."</span>  
}
</code></pre>
<h4 id="-"><strong>응답 예시</strong></h4>
<pre><code class="lang-json">{  
  <span class="hljs-attr">"id"</span>: <span class="hljs-string">"f1a13d45-3c4e-4568-bd4b-59e47a2e112f"</span>,  
  <span class="hljs-attr">"slackRecipientId"</span>: <span class="hljs-string">"slack-user-123"</span>,  
  <span class="hljs-attr">"message"</span>: <span class="hljs-string">"주문 번호: 12345\n상품 정보: 냉장고 20대\n발송지: 인천 허브 센터\n도착지: 서울 강남구 테헤란로 101 3층\n최종 발송 시한은 1월 13일 오전 9시입니다."</span>,  
  <span class="hljs-attr">"sentAt"</span>: <span class="hljs-string">"2024-12-18T10:30:15"</span>  
}
</code></pre>

</details>

<details>
<summary>🏍 <strong>배송담당자 생성: POST /shippers</strong></summary>

<blockquote>
<p><strong>배송 담당자 정보를 생성합니다.</strong>  </p>
<p>생성 시 <code>hubId</code>를 기반으로 역할과 소속 허브를 검증하고, 배송 담당자만 생성할 수 있습니다.  </p>
<p><strong>권한 관리</strong>  </p>
<ul>
<li><code>MASTER</code>: 모든 허브의 배송담당자 생성 및 수정 가능  </li>
<li><code>HUB_MANAGER</code>: 본인 소속 허브의 배송담당자만 관리 가능  </li>
</ul>
</blockquote>
<h4 id="-"><strong>요청 예시</strong></h4>
<pre><code class="lang-json">{  
  <span class="hljs-attr">"hubId"</span>: <span class="hljs-string">"456e89f3-7d2c-4abc-9546-5f3e92d2b2b1"</span>,  
  <span class="hljs-attr">"deliveryOrder"</span>: <span class="hljs-number">1</span>  
}
</code></pre>
<h4 id="-"><strong>응답 예시</strong></h4>
<pre><code class="lang-json">{  
  <span class="hljs-attr">"id"</span>: <span class="hljs-number">1</span>,  
  <span class="hljs-attr">"userId"</span>: <span class="hljs-number">12345</span>,  
  <span class="hljs-attr">"hubId"</span>: <span class="hljs-string">"456e89f3-7d2c-4abc-9546-5f3e92d2b2b1"</span>,  
  <span class="hljs-attr">"type"</span>: <span class="hljs-string">"DELIVERY_MANAGER"</span>,  
  <span class="hljs-attr">"deliveryOrder"</span>: <span class="hljs-number">1</span>  
}
</code></pre>

</details>
