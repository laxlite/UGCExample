create type auth_status as enum ('PENDING', 'APPROVED');
create type post_status as enum ('DRAFT', 'PENDING', 'REJECTED', 'APPROVED');

create table if not exists tag (
	id bigint generated always as identity primary key,
	name varchar(30) not null unique
);

create table if not EXISTS "user" (
	id BIGINT GENERATED always as identity PRIMARY key,
	nickname varchar(50) not null UNIQUE,
	created_at TIMESTAMPTZ not null default now(),
	avatar varchar not null,
	hash varchar not null,
	rating float8 not null default 0.0,
    email varchar not null unique,
    status auth_status not null
);

create table if not exists "admin" (
	id BIGINT GENERATED always as identity PRIMARY key,
	name varchar(100) not null,
    email varchar not null unique,
    hash varchar not null
);

create table if not exists post (
	id BIGINT GENERATED always as identity PRIMARY key,
    user_id bigint references "user"(id) on delete set null,
	status post_status not null default 'DRAFT',
	header varchar(100) not null,
	content varchar default '',
	rating integer not null default 0
);

create table if not exists post_tag (
	post_id bigint not null references post(id) on delete cascade,
	tag_id bigint not null references tag(id) on delete cascade,
	primary key(post_id, tag_id)
);

create table if not exists comment (
	id BIGINT GENERATED always as identity PRIMARY key,
	user_id bigint references "user"(id) on delete set null,
	content varchar not null,
	rating integer not null default 0
);

create table if not exists post_comment (
	post_id bigint references post(id) on delete cascade,
	comment_id bigint references comment(id) on delete cascade,
	primary key(post_id, comment_id)
);

create table if not exists event (
	id BIGINT GENERATED always as identity PRIMARY key,
	content varchar not null
);

create table if not exists user_event (
	user_id bigint not null references "user"(id) on delete cascade,
	event_id bigint not null references event(id) on delete cascade,
	primary key(user_id, event_id)
);