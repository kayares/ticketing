# 결정 사항

## 좌석 status를 ShowSeat에 둔 이유
2026-08-04

status는 좌석이 AVAILABLE인지, HELD인지, SOLD인지 나타내는 속성이다.
좌석의 상태이므로 Seat이나 ShowSeat 둘 중 하나에 넣는 것이 자연스럽다.
하지만 Seat에 넣게 되면 상태가 좌석 당 하나씩만 존재한다.
한 회차에서 좌석이 팔리면 다른 회차에서도 그 좌석이 SOLD로 보인다.
ShowSeat에 두면 회차별로 상태가 독립적으로 관리된다.

## ShowSeat의 행을 사전 생성한 이유
2026-08-04

Showing을 만들 때 ShowSeat을 AVAILABLE 상태로 미리 행을 생성하여 table에 넣어 둔다.
락은 원래 있던 행들을 잠가 변경하지 못하게 하는데,
잠글 행이 없으면 락을 걸 수 없다.
행을 미리 만들지 않으면 100명이 동시에 같은 좌석을 조회했을 때
전부 예매 가능하다 판단하고 INSERT를 시도한다.
그 결과 한 좌석이 여러 명에게 팔린다.
미리 만들어 두면 예매가 기존 행의 status를
AVAILABLE에서 HELD로 바꾸는 UPDATE가 되어 락이 동작한다.

## Reservation에 unique 제약이 없는 이유
2026-08-04

Reservation은 ShowSeat과 User를 매치시키는 Entity라 1:1처럼 보이지만 그건 SOLD 한정에서이다.
실제로는 Reservation이 삭제되지 않고 상태가 CANCELED로 바뀌고 이력이 남는다.
unique로 제약을 걸면 그 행이 자리를 차지하고 있어서 다른 유저에게 INSERT를 할 수 없게 된다.
unique는 방어 수단으로 쓰지 않고, 락으로 방어한다.

## Venue 생성 시 좌석 동시 생성
2026-08-06

Venue 생성 시 rowNo, colNo도 파라미터로 받아서 그에 해당하는 개수의 Seat들을 동시에 생성한다.
이에 따라 좌석이 0개인 Venue를 생성하지 않고 처음부터 좌석의 개수가 정해진 Venue를 생성할 수 있다.
이는 하나의 트랜잭션 안에서 일어나므로 Seat 생성 실패 시 Venue도 롤백된다.

## Venue 생성 시 모든 좌석을 동일 등급으로 초기화
2026-08-06

Venue 생성 시 grade를 파라미터로 받지 않고
기본 grade인 A를 `Grade.defaultGrade()`로 모든 Seat에 할당한다.
프로젝트의 목표가 동시성 제어이기 때문에 세부적 등급 지정은 범위 밖으로 뒀다.
이 설계에서는 등급별 활용 기능을 넣을 때 생성 API를 수정해야 한다.

## IDENTITY 전략의 배치 INSERT 제약
2026-08-06

좌석 100개 짜리 Venue를 생성하면 INSERT가 101건(Venue 1 + Seat 100) 실행된다.
원인은 GenerationType.IDENTITY이다.
영속성 컨텍스트는 엔티티를 id로 관리하는데, IDENTITY 설정을 쓰면 DB가 INSERT를 실행해야 id를 반환한다.
따라서 persist() 시점에 INSERT가 즉시 나가고 쓰기 지연이 성립하지 않고 batch로 보낼 수도 없다.
SEQUENCE는 INSERT와 별개로 id만 먼저 받아올 수 있다.
현재에서는 IDENTITY 그대로 쓰고 MySQL 전환 이후에 재검토하기로 한다.
이 현상은 N+1과는 다른 문제이다.
N+1은 엔티티 조회 시 연관된 엔티티를 각자 SELECT하는 것이고,
이 현상은 쓰기 시 id 할당에 대한 문제이다.
