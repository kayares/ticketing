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
미리 만들어 두면 예매가 기존 행의 status를
AVAILABLE에서 SOLD로 바꾸는 UPDATE가 되어 락이 동작한다.

## Reservation에 unique 제약이 없는 이유
2026-08-04

unique 제약을 쓰려면 어떤 경우에서도 중복이 존재해서는 안 되는 경우이다.
ShowSeat의 경우 Showing과 Seat이 조합이 중복되는 경우가 존재해서는 안 되기에 unique를 쓴다.
Reservation은 ShowSeat과 User를 매치시키는 Entity라 1:1처럼 보이지만 그건 SOLD 한정에서이다.
실제로는 Reservation이 삭제되지 않고 상태가 CANCELED로 바뀌고 이력이 남는다.
따라서 CANCELED된 Reservation까지 포함하여 unique로 제약을 걸면
그 행이 자리를 차지하고 있어서 Reservation이 예약 가능하다 해도 다른 유저에게 INSERT를 할 수 없게 된다.
고로 unique 제약을 걸지 않는다.
`feat: add reservation creation API without lock` 커밋 시점에는 중복 예약을 막는 장치가 없다.
그게 oversold 측정의 조건이고, 이후 락으로 막을 것이다.

## Venue 생성 시 좌석을 함께 생성한 이유
2026-08-06

Venue 생성 시 rowCount, colCount도 파라미터로 받아서 그에 해당하는 개수의 Seat들을 동시에 생성한다.
락 실험은 경합 대상 행이 이미 존재해야 성립하므로 Seat을 미리 만들어둔다.
부수적으로 Seat이 0개인 Venue가 생기는 것도 막는다.
이는 하나의 트랜잭션 안에서 일어나므로 Seat 생성 실패 시 Venue도 롤백된다.
대안은 Venue만 먼저 만들고 수정 API로 Seat을 추가하는 방식인데, 위 이유로 선택하지 않았다.

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
