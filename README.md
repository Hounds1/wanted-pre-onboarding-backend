# wanted-pre-onboarding-backend
wanted-pre-onboarding-backend 요구 사항에 맞춰 구현한 게시판 예제입니다.

# 현재 완료된 과제
1. 회원가입 엔드포인트 개설, 유효성 검사(이메일 형식, 패스워드 길이) (테스트 완료)
2. 사용자 로그인 엔드포인트, 유효성 검사(이메일 형식) (테스트 완료)
3. 게시글 생성 엔드포인트 개설 (테스트 완료)
4. 게시글 조회 엔드포인트 개설(Paging, 단건 조회) (테스트 완료)
5. 게시글 업데이트 엔드포인트 개설 및 검증 구현(게시자 또는 관리자가 아닐 경우 거부) (테스트 완료)
6. 게시글 삭제 엔드포인트 개설 및 검증 구현(게시자 또는 관리자가 아닐 경우 거부) (테스트 완료)

# 부가적으로 생성된 기능
1. Global Error 생성 및 Advice
2. IntegrationTestSupport 테스트 환경 의존성 통합
3. JpaAuditor 활성화
4. IntegrationTestSupporters 생성 및 테스트 의존성 통합
5. 게시판 엔드포인트 생성(관리자가 아닐 경우 거부) (테스트 완료)
6. Spring Rest Docs 적용 (현행 테스트 문서화 완료)
