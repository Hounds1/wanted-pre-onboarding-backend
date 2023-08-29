# RedisPolicy

---
이 문서는 프로젝트에서 Redis를 어떤 방식으로 사용 중인지 기록하고 있습니다.

---

## 구현체

### 1. RedisService
### 2. RedisPrePatcher
### 3. RedisKeys

---

## 용도

### 1. RedisService
 
- RedisService는 StringRedisTemplate을 주입하여 사용 중인 일종의 유틸리티 클래스의 개념으로 사용됩니다.<br>
RedisService는 Redis에 데이터를 조회하거나 저장하고 삭제하는 등의 기본적인 Redis 동작을 내장하고 있습니다.<br>
그 외에도 특정 Pattern을 충족하는 Key를 모두 삭제하는 등의 로직이 구현 되어있습니다.
- 예를 들어, writeToRedisWithTTL(String, String, long) 을 통해 수명이 정해져 있는 데이터를 넣거나<br>
  removeKeysWithPattern(String)을 통해 파라미터와 일치하는 패턴을 가진 Key를 모두 삭제할 수 있습니다.

### 2. RedisPrePatcher
- RedisPrePatcher는 이 프로젝트에서 사용 중인 초기 로딩용 유틸리티 클래스입니다.<br>
RedisPrePatcher는 서버 초기 가동시 상위 추천수의 개시물 10개(임시)를 미리 로드하기 위해<br>
사용되고 있습니다. 다음은 동작 과정입니다.
 - 1. 서버가 처음 가동될 시 먼저 Redis에 로드 되어있는 상위 추천 게시물이 있는지 확인하고
비웁니다.
   2. 게시물을 비우는 removeKeysWithPattern(String)의 작업이 끝나고 Redis에 목표하는 데이터를 로드하기 시작합니다.<br>
   3. 먼저, LikeReadService를 호출해 상위 10개의 컨텐츠의 추천수를 추출하기 위해 contentId로 그룹화하고 내림차순으로 정렬하여 쿼리합니다.
   4. 추출한 contentId List를 사용하여 ContentReadService를 호출하고 SimpleContentResponse를 돌려받고 각 contentId에 따라<br>
"top-recommend-{contentId}"를 Key로 사용하여 Redis에 적재합니다.
   5. 동시에 클라이언트에서 상위 게시물을 노출시키기 위한 메타데이터를 함께 캐싱합니다.</br>
"top-recommend-meta"를 Key로 저장하며 contentId, title, likeCount(좋아요 수)를 포함하고 있습니다.
   6. "top-recommend-{contentId}"는 각각의 게시물을 직렬화하여 저장하고 "top-recommend-meta"는 해당 게시물들의 메타데이터 리스트를 직렬화하여 저장합니다.
- 추가적으로 RedisPrePatcher는 서버 최초 가동 시에 1번 그 후로는 매 시간 정각마다 데이터를 밀고 다시 캐싱하도록 설정되어 있습니다.

### 3. RedisKeys
- RedisKeys는 큰 의미를 담고있지는 않습니다. 단지, Redis에서 사용하게 될 Key들을 용도에 따라 모아두고 사용할 수 있으면 <br>좋겠다고 생각하여 만든 Enum 클래스입니다.

---
## 운영 현황

### 1. 일반 게시물 캐싱
- 최신 글들은 페이지 앞 쪽에 위치하기 때문에 여러 사람에 의해 호출 될 가능성이 높습니다.<br>
 그렇기 때문에 어떤 게시물이 조회 된다면 30분의 유효 시간을 가지고 캐싱됩니다.

### 2. 상위 추천 수 게시물 캐싱
- RedisPrePatcher의 동작 원리 그대로 캐싱을 진행합니다.