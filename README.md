# Book-Management-System

## 구현 내용

- Restful API ( Resource, HTTP Method, Self Description )
- 회원 가입
- JWT 기반 로그인
- 도서 등록, 수정, 삭제
- 도서 대여, 반납
- 도서 조회 검색
- 대여 목록 조회
- 도서 관리 내역 조회, 검색
---

## URI
### Users API 
https://github.com/durin93/Book-Management-System/wiki/USERS-API

메소드 | 경로 | 설명 | 
|----- | ----------- | ------- | 
| POST |  /api/users | 회원 등록 |
| POST |  /api/users/authentication | 로그인 |


### Books API 
https://github.com/durin93/Book-Management-System/wiki/Books-API

메소드 | 경로 | 설명 | 
|----- | ----------- | ------- | 
| POST |  /api/books | 도서 등록 |
| PUT |  /api/books/{id} | 도서 수정 |
| DELETE |  /api/books/{id} | 도서 삭제 |
| PUT |  /api/books/{id}/rent | 도서 대여 |
| PUT |  /api/books/{id}/giveBack | 도서 반납 |
| GET |  /api/books/{id} | 도서 조회 |
| GET |  /api/books | 도서 검색 |
| GET |  /api/books/users/{id} | 사용자가 대여한 도서 목록 조회 |


### Histories API 
https://github.com/durin93/Book-Management-System/wiki/Histories-API

메소드 | 경로 | 설명 | 
|----- | ----------- | ------- | 
| GET |  /api/histories | 도서 내역 검색 |
| GET |  /api/histories/{id} | 도서 내역 조회 |


## 실행 하기

저장소를 git clone 또는 다운로드 후 

1. 터미널로 mvn clean package 후 target폴더에서(cd target) java -jar bookmanagement-0.0.1-SNAPSHOT.jar 실행시킵니다.(중지 시 CTRL + C)
또는 2. 스프링부트로 프로젝트를 열고 BookmanagementApplication 을 실행시킵니다.
