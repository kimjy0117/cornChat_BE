# 프로젝트 소개
실시간으로 친구들과 웹사이트를 통해 채팅할 수 있는 서비스 제공

<br><br>

## 콘챗 web project
>**개발 기간** : 20240907 ~

>**배포된 주소** : http://www.cornchat.site

<br> <br>


## Stacks
#### Design (UI/UX) 
![Figma](https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white)  ![Styled Components](https://img.shields.io/badge/Styled%20Components-DB7093?style=for-the-badge&logo=styled-components&logoColor=white)

#### Environment
![VS Code](https://img.shields.io/badge/VS%20Code-007ACC?style=for-the-badge&logo=visual-studio-code&logoColor=white)  ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-000000?style=for-the-badge&logo=intellij-idea&logoColor=white)  ![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)    ![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)

#### Frontend 
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)  ![React](https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=black)  ![Vite](https://img.shields.io/badge/Vite-4B32C3?style=for-the-badge&logo=vite&logoColor=white)  ![axios](https://img.shields.io/badge/axios-007ACC?style=for-the-badge&logo=axios&logoColor=white)


#### Backend
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)  ![Spring Security](https://img.shields.io/badge/Spring%20Security-4A5B6D?style=for-the-badge&logo=spring-security&logoColor=white)  ![Spring Cloud AWS](https://img.shields.io/badge/Spring%20Cloud%20AWS-6DB33F?style=for-the-badge&logo=spring-cloud&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-5D8AA8?style=for-the-badge&logo=spring-data&logoColor=white)  ![Spring Mail](https://img.shields.io/badge/Spring%20Mail-9BCA8E?style=for-the-badge&logo=spring&logoColor=white)  ![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white) <img src="https://img.shields.io/badge/mongodb-47A248?style=for-the-badge&logo=mongodb&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/redis-FF4438?style=for-the-badge&logo=redis&logoColor=white">
<br><br>

## 주요기능
![image](https://github.com/user-attachments/assets/04e9c914-9013-4773-94dc-cf5fa3115aea)
![image](https://github.com/user-attachments/assets/03d3a2f9-4cb2-485a-88d4-a530f2280505)

<br><br>

# 🌴 cornChat Directory Tree
<pre style="background-color: #f0f0f0; padding: 10px; border-radius: 5px;">
📁 cornChat_BE
src
└── main
    └── java
        └── org.example.cornchat_be
            ├── apiPayload         # API 응답 코드 및 예외 처리 관련 클래스
            │   ├── code           # 응답 코드 정의
            │   └── exception      # 사용자 정의 예외 클래스
            ├── domain             # 주요 도메인 로직 및 엔티티
            │   ├── chat           # 채팅 관련 엔티티 및 로직
            │   ├── common         # 공통 도메인 클래스
            │   ├── friend         # 친구 관리 관련 도메인
            │   └── user           # 사용자 관련 도메인
            ├── sms                # SMS 기능 관련 로직
            │   ├── controller     # SMS 컨트롤러
            │   ├── dto            # SMS 전송 관련 DTO
            │   └── service        # SMS 서비스 로직
            ├── util               # 유틸리티 및 설정 관련 클래스
            │   ├── config         # 애플리케이션 설정 클래스
            │   ├── jwt            # JWT 관련 유틸리티
            │   ├── principal      # 사용자 인증 관련 클래스
            │   └── redis          # Redis 관련 유틸리티
            ├── SecurityUtil       # 보안 관련 유틸리티 클래스
            ├── CornChatBeApplication # 스프링 부트 애플리케이션 진입점
            └── TestController     # 테스트용 컨트롤러
</pre>
<br> <br>

## 개발환경
![image](https://github.com/user-attachments/assets/99407e81-7e38-47f1-90d3-50d2eb283421)
<br> <br>

## ERD
![image](https://github.com/user-attachments/assets/59c1c533-41b7-40bf-af6f-2ff441413a74)

