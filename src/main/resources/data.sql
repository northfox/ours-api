delete from TRX_TODO where PROJECT_ID = 99999;
delete from TRX_PROJECT where ID = 99999;
delete from MST_STATUS;
delete from TRX_WILL_TODO where BACKUP_ID = 99999;
delete from TRX_WILL_BACKUP where ID = 99999;

insert into MST_STATUS(ID, SORT, NAME) values(0, 0, '未着手');
insert into MST_STATUS(ID, SORT, NAME) values(1, 10, '着手中');
insert into MST_STATUS(ID, SORT, NAME) values(2, 20, '待ち');
insert into MST_STATUS(ID, SORT, NAME) values(3, 30, '完了');

insert into TRX_PROJECT(ID, TITLE) values(99999, '式');

insert into TRX_TODO(PROJECT_ID, TITLE, STATUS_ID, COST) values(99999, '芳名帳', 0, 100);
insert into TRX_TODO(PROJECT_ID, TITLE, STATUS_ID) values(99999, '招待状', 2);
insert into TRX_TODO(PROJECT_ID, TITLE, STATUS_ID, DONE_AT) values(99999, '料理', 3, now());

insert into TRX_WILL_BACKUP(ID, SAVED_KEYWORD) values (99999, 'test' );
insert into TRX_WILL_TODO(BACKUP_ID, SENSE, CONTENT) values ( null, 'taste', 'taste content' );
insert into TRX_WILL_TODO(BACKUP_ID, SENSE, CONTENT, DONE_AT, DONE_BY) values ( null, 'taste', 'taste content', now(), 'done-who');
insert into TRX_WILL_TODO(BACKUP_ID, SENSE, CONTENT, DELETED_AT, DELETED_BY) values ( null, 'taste', 'taste content', now(), 'delete-who');
insert into TRX_WILL_TODO(BACKUP_ID, SENSE, CONTENT) values ( null, 'taste', 'taste content' );
insert into TRX_WILL_TODO(BACKUP_ID, SENSE, CONTENT) values ( 99999, 'taste', 'backup taste content' );
insert into TRX_WILL_TODO(BACKUP_ID, SENSE, CONTENT) values ( 99999, 'taste', 'backup taste content 2' );
insert into TRX_WILL_TODO(BACKUP_ID, SENSE, CONTENT) values ( 99999, 'touch', 'backup touch content' );
insert into TRX_WILL_TODO(BACKUP_ID, SENSE, CONTENT, DONE_AT, DONE_BY) values ( 99999, 'taste', 'taste content', now(), 'done-who');
insert into TRX_WILL_TODO(BACKUP_ID, SENSE, CONTENT, DELETED_AT, DELETED_BY) values ( 99999, 'taste', 'taste content', now(), 'delete-who');
