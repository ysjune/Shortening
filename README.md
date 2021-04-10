# - 개요

**URL을 입력받아 짧게 줄여주고, Shortening된 URL을 입력하면 원래 URL로 리다이렉트하는 URL Shortening Service**
예) https://en.wikipedia.org/wiki/URL_shortening => http://localhost/JZfOQNro



# - 요구사항

1. URL 입력폼 제공 및 결과 출력
2. 단축 URL 은 8자리 이내
3. 동일한 URL의 요청은 동일한 단축 URL 응답
4. 단축 URL 을 입력받으면 원래 URL 로 리다이렉트
5. 동일한 요청 수에 대한 카운트 확인



# - 사용 기술

- Spring Boot
- Maven
- JPA
- H2



# - 빌드 및 실행

(ubuntu linux 기준 및 root 권한이 아닐 경우 sudo 추가)

- java8 설치

  ```
  apt-get install openjdk-8-jdk
  ```

- git 설치

  ```
  apt-get install git
  ```

- maven 설치

  ```
  apt install maven
  ```

- 소스 받기

  ```
  git clone https://github.com/ysjune/Shortening.git
  ```

- 빌드

  ```
  cd Shortening/
  
  mvn package
  
  java -jar target/shortening-0.0.1-SNAPSHOT.jar
  ```

- 실행

  ```
  localhost:8080
  ```