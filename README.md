# able

## 프로젝트 소개
동시성 제어와 Cache 라는 키워드를 중심으로 구현한 인터넷 쇼핑몰입니다.

<br>

## 개발 기간
2024.11.22~2024.11.29

<br>

## 업무 분배
- 김지혜 : 인증/인가, 검색에 캐싱 적용
- 윤상진 : 상품 검색, 검색에 캐싱 적용
- 정다운 : 상품, 상품에 동시성 적용
- 조연우 : 쿠폰, 쿠폰에 동시성 적용

<br>

## 주요기능
- Spring Security를 회원가입 및 로그인
- JWT를 통한 인증 및 권한 관리
- QueryDSL을 사용한 검색 기능
- DB, Cache를 사용한 인기 검색어 기능
- Lettuce를 이용한 상품, 쿠폰 재고 감소 기능

<br>

## API 명세서
https://www.notion.so/teamsparta/30261dd286e140139a979cf6a7d2b8fe?v=c73c313551414bb691d514de23f83638

<br>

## ERD
<img width="1159" alt="스크린샷 2024-11-29 오전 9 35 31" src="https://github.com/user-attachments/assets/0377672c-778a-40b7-af7d-d64080352a6a">


<br>


## 와이어프레임
https://www.figma.com/board/jsWVjjyKfxR48pTlnp8DIP/10%EC%A1%B0-%EC%87%BC%ED%95%91%EB%AA%B0?node-id=0-1&t=dlfmKCeISrtAAXP6-1
<br/>

## 쿠폰 발급 트러블 슈팅
https://velog.io/@yeonu6550/%EB%8F%99%EC%8B%9C%EC%84%B1-%EC%A0%9C%EC%96%B4-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85
<br/>

## DB vs Cache 부하 테스트 결과
https://development-diary-for-me.tistory.com/209
