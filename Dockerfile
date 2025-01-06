# JDK 17 이미지 사용
FROM openjdk:17-alpine

# 애플리케이션 jar 파일을 컨테이너에 복사
# 빌드 시 ./target 디렉토리에 생성된 jar 파일을 지정
COPY target/cornchat-be.jar /cornchat-be.jar

# 환경 변수 설정 (런타임에서 전달받도록 설정)
# MySql
ENV PROD_MYSQL_HOSTNAME ""
ENV PROD_MYSQL_DB_NAME ""
ENV PROD_MYSQL_DB_USERNAME ""
ENV PROD_MYSQL_DB_PASSWORD ""

# Mongo
ENV PROD_MONGO_DB_HOST ""
ENV PROD_MONGO_DB_NAME ""

# Redis
ENV PROD_REDIS_HOST ""
ENV PROD_REDIS_PORT ""
ENV PROD_REDIS_PASSWORD ""

# Jwt
ENV PROD_JWT_SECRET ""

# Mail
ENV GOOGLE_EMAIL ""
ENV EMAIL_PASSWORD ""

# 애플리케이션 실행 명령어
ENTRYPOINT ["java", "-jar", "/cornchat-be.jar"]

# 컨테이너가 외부와 통신할 포트를 설정
EXPOSE 8080