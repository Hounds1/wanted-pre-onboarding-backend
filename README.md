# wanted-pre-onboarding-backend
wanted-pre-onboarding-backend 요구 사항에 맞춰 구현한 게시판 예제입니다.

# API 제작 과제 기본 요구사항
## API 요구 사항
게시판을 관리하는 RESTful API를 개발해 주세요. 이때, 다음의 기능을 구현해야 합니다. 데이터베이스의 테이블 설계는 지원자분의 판단에 맡겨져 있습니다. 요구사항을 충족시키는 데 필요하다고 생각되는 구조로 자유롭게 설계해 주세요.

- **과제 1. 사용자 회원가입 엔드포인트**
    - 이메일과 비밀번호로 회원가입할 수 있는 엔드포인트를 구현해 주세요.
    - 이메일과 비밀번호에 대한 유효성 검사를 구현해 주세요.
        - 이메일 조건: **@** 포함
        - 비밀번호 조건: 8자 이상
        - 비밀번호는 반드시 암호화하여 저장해 주세요.
        - 이메일과 비밀번호의 유효성 검사는 위의 조건만으로 진행해 주세요. 추가적인 유효성 검사 조건은 포함하지 마세요.
- **과제 2. 사용자 로그인 엔드포인트**
    - 사용자가 올바른 이메일과 비밀번호를 제공하면, 사용자 인증을 거친 후에 JWT(JSON Web Token)를 생성하여 사용자에게 반환하도록 해주세요.
    - 과제 1과 마찬가지로 회원가입 엔드포인트에 이메일과 비밀번호의 유효성 검사기능을 구현해주세요.
- **과제 3. 새로운 게시글을 생성하는 엔드포인트**
- **과제 4. 게시글 목록을 조회하는 엔드포인트**
    - 반드시 Pagination 기능을 구현해 주세요.
- **과제 5. 특정 게시글을 조회하는 엔드포인트**
    - 게시글의 ID를 받아 해당 게시글을 조회하는 엔드포인트를 구현해 주세요.
- **과제 6. 특정 게시글을 수정하는 엔드포인트**
    - 게시글의 ID와 수정 내용을 받아 해당 게시글을 수정하는 엔드포인트를 구현해 주세요.
    - 게시글을 수정할 수 있는 사용자는 게시글 작성자만이어야 합니다.
- **과제 7. 특정 게시글을 삭제하는 엔드포인트**
    - 게시글의 ID를 받아 해당 게시글을 삭제하는 엔드포인트를 구현해 주세요.
    - 게시글을 삭제할 수 있는 사용자는 게시글 작성자만이어야 합니다.

# 현재 완료된 과제
1. 회원가입 엔드포인트 개설, 유효성 검사(이메일 형식, 패스워드 길이) (테스트 완료)
2. 사용자 로그인 엔드포인트, 유효성 검사(이메일 형식) (테스트 완료)<br>
   - 비밀번호를 검증하지 않은 이유는 로그인에 실패 시 비밀번호에 대한 어떠한 단서도 남겨줘서는
   안된다고 생각해서 입니다. 예를 들어, 검증 로직을 타고 "비밀번호는 8자 이상입니다."등의 힌트를
   주는 순간 공격자의 예상 범위가 좁혀지기 때문입니다. 물론 회원가입 검증 로직에서 이를 제공하지만
   다른 분석 없이 로그인을 시도하는 경우 또한 배제할 수 없으므로 구태여 단서를 제공해서는 안된다는 것이
   제 생각입니다.
4. 게시글 생성 엔드포인트 개설 (테스트 완료)
5. 게시글 조회 엔드포인트 개설(Paging, 단건 조회) (테스트 완료)
6. 게시글 업데이트 엔드포인트 개설 및 검증 구현(게시자 또는 관리자가 아닐 경우 거부) (테스트 완료)
7. 게시글 삭제 엔드포인트 개설 및 검증 구현(게시자 또는 관리자가 아닐 경우 거부) (테스트 완료)

# 부가적으로 생성된 기능
1. Global Error 생성 및 Advice
2. JpaAuditor 활성화
3. IntegrationTestSupporters 생성 및 테스트 의존성 통합
4. 게시판 엔드포인트 생성(관리자가 아닐 경우 거부) (테스트 완료)
5. Spring Rest Docs 적용 (현행 테스트 문서화 완료)
