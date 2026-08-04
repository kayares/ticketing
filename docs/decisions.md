# 결정 사항

## 좌석 status를 ShowSeat에 둔 이유

status는 좌석이 AVAILABLE인지, HELD인지, SOLD인지 나타내는 속성이다.
좌석의 상태이므로 Seat이나 ShowSeat 둘 중 하나에 넣는 것이 자연스럽다.
하지만 Seat에 넣게 되면 상태가 좌석 당 하나씩만 존재한다.
한 회차에서 좌석이 팔리면 다른 회차에서도 그 좌석이 SOLD로 보인다.
ShowSeat에 두면 회차별로 상태가 독립적으로 관리된다.

## ShowSeat의 행을 사전 생성한 이유

Showing을 만들 때 ShowSeat을 AVAILABLE 상태로 미리 행을 생성하여 table에 넣어 둔다.
락은 원래 있던 행들을 잠가 변경하지 못하게 하는데,
잠글 행이 없으면 락을 걸 수 없다.
행을 미리 만들지 않으면 100명이 동시에 같은 좌석을 조회했을 때
전부 예매 가능하다 판단하고 INSERT를 시도한다.
그 결과 한 좌석이 여러 명에게 팔린다.
미리 만들어 두면 예매가 기존 행의 status를
AVAILABLE에서 HELD로 바꾸는 UPDATE가 되어 락이 동작한다.

## Reservation에 unique 제약이 없는 이유

Reservation은 ShowSeat과 User를 매치시키는 Entity라 1:1처럼 보이지만 그건 SOLD 한정에서이다.
실제로는 Reservation이 CANCELED될 수 있고 이력이 남는다.
unique로 제약을 걸면 다른 유저에게 INSERT를 할 수 없게 된다.
unique는 방어 수단으로 쓰지 않고, 락으로 방어한다.
