create TYPE if not exists auth_status as enum ('PENDING', 'APPROVED');
create type if not exists post_status as enum ('DRAFT', 'PENDING', 'REJECTED', 'APPROVED');

create if not exists table tag (
	id bigint generated always as identity primary key,
	name varchar(30) unique(lower(name))
)

create if not exists table auth (
	id bigint generated always as identity primary key,
	email varchar not null,
	hash varchar not null,
	status auth_status not null
);

create if not EXISTS table "user" (
	id BIGINT GENERATED always as identity PRIMARY key,
	nickname varchar(50) not null UNIQUE,
	created_at TIMESTAMPTZ not null,
	avatar varchar not null,
	hash varchar not null,
	rating float8 not null default 0.0
);

create if not exists table "admin" (
	id BIGINT GENERATED always as identity PRIMARY key,
	name varchar(100) not null
);

create if not exists table post (
	id BIGINT GENERATED always as identity PRIMARY key,
	author bigint references "user"(id) on delete set null,
	status post_status not null default 'DRAFT',
	header varchar(100) not null,
	content varchar not null,
	rating integer not null default 0
);

create if not exists table post_tag (
	post_id bigint not null references post(id) on delete cascade,
	tag_id bigint not null references tag(id) on delete cascade,
	primary key(post_id, tag_id)
);

create if not exists table comment (
	id BIGINT GENERATED always as identity PRIMARY key,
	author bigint references "user"(id) on delete set null,
	content varchar not null,
	rating integer not null default 0
);

create if not exists table post_comment (
	post_id bigint references post(id) on delete cascade,
	comment_id bigint references comment(id) on delete cascade,
	primary key(post_id, comment_id)
);

create if not exists table event (
	id BIGINT GENERATED always as identity PRIMARY key,
	content varchar not null,
);

create if not exists table user_event (
	user_id bigint references "user"(id) on delete cascade,
	event_id bigint references event(id) on delete cascade,
	primary key(user_id, event_id)
);