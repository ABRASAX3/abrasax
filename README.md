# 뉴스피드 프로젝트

## 🔍 개요

개발자 혹은 개발자 지망생들이 소통을 할 수 있는 커뮤니티형 뉴스피드 API입니다.
개발을 하는 과정에서 직면하게 되는 다양한 상황들은 비슷한 경험을 해보지 못한 사람에게 쉽게 이해될 수 있는 내용이 아닙니다.
그래서 개발자들끼리 서로 이야기를 나누고 공감할 수 있는 커뮤니티 역할을 할 수 있는 뉴스피드를 만들게 되었습니다.
기본적으로 개발 공부를 하며 서로 질문이나 정보를 공유하는 것을 목적으로 하지만 일상과 관련된 내용이어도 괜찮습니다.😊

## 😊 ERD

![ERD.png](assets/ERD.png)

## 😊 API Specification

![img.png](assets/API_SPEC.png)
https://www.notion.so/cbf9a43296014c5dbfb0b85a7f9dcf76?v=be311eb26548474193ee4a3c43309ccb
http://localhost:8080/swagger-ui/index.html#

## 💻 실행 예시

https://youtu.be/7aZyFnnfp4E?feature=shared

## 📈 기능 상세

- **게시물 CRUD 기능**
    - 게시물 작성, 조회, 수정, 삭제 기능
    - 게시물 커서 기반 페이지네이션

- **게시물 Tag**
    - 게시물을 생성, 수정할 때 사용자가 내용과 관련된 여러 태그를 입력 가능
    - 원하는 태그를 가진 게시물 검색 기능

- **뉴스피드 기능(메인 페이지/전체 조회 페이지)**
    - 뉴스피드 페이지
        - 사용자가 다른 사용자의 게시물을 한 눈에 볼 수 있는 뉴스피드 페이지

- **상세보기 기능(디테일 페이지)**
    - 게시글의 상세 페이지
        - 게시글의 내용과 댓글을 볼 수 있는 상세페이지

- **댓글 CRUD 기능**
    - 댓글 작성, 조회, 수정, 삭제 기능
        - 사용자는 게시물에 댓글을 작성할 수 있고 본인의 댓글만 수정 및 삭제 가능

- **사용자 인증/인가 기능**
    - 회원가입 기능
        - ID와 비밀번호의 형태로 서비스에 가입 가능
            - 비밀번호는 암호화해서 저장
    - 로그인 및 로그아웃 기능
        - 자신의 계정으로 서비스에 로그인 가능
            - 로그인 시 토큰을 제공하고 만료 시간은 설정되어있지만 로그아웃은 미구현
    - 인가 기능
        - 게시물과 댓글 조회, 회원 가입, 로그인을 제외한 나머지 기능들은 JWT를 통해 인가

- **회원정보 관리**
    - 프로필 수정 기능
        - Nickname, Social Accounts 수정
    - 비밀번호 수정 기능
        - 현재 비밀번호와 새로운 비밀번호를 입력받아 검증 후 비밀번호 수정

- **소셜 로그인 기능 구현**
    - 네이버 로그인 구현
    - application.yml에 Naver Login API 관련 정보를 기입해야 합니다.
    - [참고 페이지](https://github.com/ABRASAX3/abrasax/pull/14)

## 환경설정

- Language : Kotlin 1.9.24
- IDEA : IntelliJ IDEA 2024.1
- JDK : temurin 17.0.11
- Database : super base & postgresql
- springframework.boot : 3.3.0
