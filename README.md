Schedule Mangement Application API 명세서

1. 공통 정보

Base URL: http://localhost:8080

인증 방식:

로그인 성공 시 발급되는 JSESSIONID 쿠키를 사용한 세션 기반 인증.

인증: 필수로 표시된 API는 요청 시 반드시 이 쿠키를 포함해야 한다.

데이터 형식: 모든 요청(Request) 및 응답(Response) 본문은 application/json 형식

공통 에러 응답:

400 Bad Request: 요청 값 유효성 검증 실패 또는 비즈니스 로직 상의 에러.

"비밀번호는 8~15자, 영문, 숫자, 특수문자를 포함해야 한다."

401 Unauthorized: 인증(로그인)되지 않은 상태에서 보호된 API에 접근 시.

"로그인이 필요한 서비스입니다."

500 Internal Server Error: 서버 내부 로직 처리 중 발생한 예외.

2. 사용자 (Users) API
   
2.1. 회원가입
   
설명: 새로운 사용자를 등록합니다.

Endpoint: POST /users

인증: 불필요

Request Body:

{
  "name": "test",
  "email": "test@naver.com",
  "password": "test1234!"
}

Success Response (200 OK):

{
  "id": 1,
  "name": "test",
  "email": "test@naver.com",
  "createdAt": "2025-08-13T20:12:00",
  "modifiedAt": "2025-08-13T20:12:00"
}

2.2. 로그인

설명: 이메일과 비밀번호로 로그인하여 세션을 생성합니다.

Endpoint: POST /users/login

인증: 불필요

Request Body:

{
  "email": "test@naver.com",
  "password": "test1234!"
}

Success Response (200 OK):

Set-Cookie 헤더에 JSESSIONID 쿠키 포함.

Body: "로그인 성공"

2.3. 로그아웃

설명: 현재 세션을 만료시켜 로그아웃합니다.

Endpoint: POST /users/logout

인증: 필수

Success Response (200 OK):

Body: "로그아웃 성공"

3. 할일 (Todos) API

   
3.1. 할일 작성

설명: 로그인한 사용자가 새로운 할일을 작성합니다.

Endpoint: POST /users/{userId}/todos

인증: 필수

Request Body:

{
  "title": "저녁 장보기",
  "content": "우유, 계란, 빵 구매"
}

Success Response (201 Created):

{
  "id": 1,
  "title": "저녁 장보기",
  "content": "우유, 계란, 빵 구매",
  "username": "test",
  "createdAt": "2025-08-13T22:30:00",
  "modifiedAt": "2025-08-13T22:30:00"
}

3.2. 특정 사용자의 할일 목록 조회

설명: 특정 사용자가 작성한 모든 할일 목록을 조회합니다.

Endpoint: GET /users/{userId}/todos

인증: 필수

Success Response (200 OK):

[
  {
    "id": 1,
    "title": "저녁 장보기",
    "content": "우유, 계란, 빵 구매",
    "username": "test",
    "createdAt": "2025-08-13T22:30:00",
    "modifiedAt": "2025-08-13T22:30:00"
  }
]

3.3. 할일 수정

설명: 본인이 작성한 할일의 내용을 수정합니다.

Endpoint: PUT /users/{userId}/todos/{todoId}

인증: 필수 (본인만 가능)

Request Body:

{
  "title": "저녁 장보기 (수정)",
  "content": "두부 추가 구매"
}

Success Response (200 OK): 수정된 할일 정보 반환.

3.4. 할일 삭제

설명: 본인이 작성한 할일을 삭제합니다.

Endpoint: DELETE /users/{userId}/todos/{todoId}

인증: 필수 (본인만 가능)

Success Response (204 No Content): 본문 내용 없음.

4. 댓글 (Comments) API

4.1. 댓글 작성

설명: 특정 할일에 로그인한 사용자가 댓글을 작성합니다.

Endpoint: POST /todos/{todoId}/comments

인증: 필수

Request Body:

{
  "content": "저녁 메뉴가 뭔가요??"
}

Success Response (201 Created):

{
  "id": 1,
  "content": "저녁 메뉴가 뭔가요??!",
  "username": "test",
  "createdAt": "2025-08-13T22:40:00",
  "modifiedAt": "2025-08-13T22:40:00"
}

4.2. 특정 할일의 댓글 목록 조회

설명: 특정 할일에 달린 모든 댓글을 조회합니다.

Endpoint: GET /todos/{todoId}/comments

인증: 필수

Success Response (200 OK): 댓글 목록 배열 반환.

4.3. 댓글 수정

설명: 본인이 작성한 댓글을 수정합니다.

Endpoint: PUT /todos/{todoId}/comments/{commentId}

인증: 필수 (본인만 가능)

Request Body:

{
  "content": "저녁 메뉴가 뭔가요??"
}

Success Response (200 OK): 수정된 댓글 정보 반환.

4.4. 댓글 삭제

설명: 본인이 작성한 댓글을 삭제합니다.

Endpoint: DELETE /todos/{todoId}/comments/{commentId}

인증: 필수 (본인만 가능)

Success Response (200 OK):

Body: "댓글 삭제 성공"

ERD

<img width="864" height="513" alt="image" src="https://github.com/user-attachments/assets/b59bb8c7-5c38-4604-985b-8f90b25986b9" />
