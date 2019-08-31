delete from TRX_TODO where PROJECT_ID = 99999;
delete from TRX_PROJECT where ID = 99999;
delete from MST_STATUS;

insert into MST_STATUS(ID, SORT, NAME) values(0, 0, '未着手');
insert into MST_STATUS(ID, SORT, NAME) values(1, 10, '着手中');
insert into MST_STATUS(ID, SORT, NAME) values(2, 20, '待ち');
insert into MST_STATUS(ID, SORT, NAME) values(3, 30, '完了');

insert into TRX_PROJECT(ID, TITLE) values(99999, '式');

insert into TRX_TODO(PROJECT_ID, TITLE, STATUS_ID, COST) values(99999, '芳名帳', 0, 100);
insert into TRX_TODO(PROJECT_ID, TITLE, STATUS_ID) values(99999, '招待状', 2);
insert into TRX_TODO(PROJECT_ID, TITLE, STATUS_ID, DONE_AT) values(99999, '料理', 3, now());