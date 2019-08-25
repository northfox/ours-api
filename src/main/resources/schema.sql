create table if not exists mst_status
(
    id         serial primary key,
    name       varchar(4000) unique not null,
    created_at timestamp            not null default now(),
    updated_at timestamp            not null default now(),
    deleted_at timestamp

);


create table if not exists trx_project
(
    id         serial primary key,
    title      varchar(4000) unique not null,
    created_at timestamp            not null default now(),
    updated_at timestamp            not null default now(),
    deleted_at timestamp
);

create table if not exists trx_todo
(
    project_id int references trx_project (id),
    id         serial unique not null,
    title      varchar(4000) not null,
    status_id  int references mst_status (id),
    deadline   timestamp,
    created_at timestamp     not null default now(),
    updated_at timestamp     not null default now(),
    done_at    timestamp,
    deleted_at timestamp,
    primary key (project_id, id)
);
