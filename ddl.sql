CREATE EXTENSION if not exists pgcrypto;
DROP SCHEMA if exists fff cascade;
CREATE SCHEMA fff;
CREATE TYPE fff."role" AS ENUM (
	'USER',
	'MANAGER',
	'ADMIN');

CREATE TABLE users (
	id uuid default gen_random_uuid(),
	username varchar not null unique,
	password varchar not null,
	cart uuid default null,
	preferred uuid default null,
	access role default 'USER',
	
	constraint pk_user_id
		primary key (id)
);

CREATE TABLE locations (
	id uuid default gen_random_uuid(),
	number smallint not null,
	address varchar not null,
	city varchar not null,
	state varchar not null,
	zip varchar not null,
	manager uuid not null,
		
	constraint pk_location_id
		primary key (id),
		
	constraint fk_l_manager_id
		foreign key (manager) references users (id)
);

CREATE TABLE images (
	id uuid default gen_random_uuid(),
	data varchar not null,
	
	constraint pk_image_id
		primary key (id)
);

CREATE TABLE categories (
	id uuid default gen_random_uuid(),
	name varchar not null,
	image uuid default null,
	
	constraint pk_category_id
		primary key (id),
		
	constraint fk_c_iamge_id
		foreign key (image) references images(id)
);

CREATE TABLE transactions (
	id uuid default gen_random_uuid(),
	customer uuid not null,
	location uuid not null,
	cart boolean not null,
	modified timestamp not null,

	constraint pk_transaction_id
		primary key (id),
		
	constraint fk_t_customer_id
		foreign key (customer) references users (id),
		
	constraint fk_t_location_id
		foreign key (location) references locations (id)
);

CREATE TABLE items (
	id uuid default gen_random_uuid(),
	name varchar not null,
	description varchar not null,
	image uuid default null,
	price int not null,
	category uuid not null,
	
	constraint pk_item_id
		primary key (id),

	constraint fk_it_image_id
		foreign key (image) references images (id),

	constraint fk_it_category_id
		foreign key (category) references categories (id)
);

CREATE TABLE inventory (
	id uuid default gen_random_uuid(),
	location uuid not null,
	item uuid not null,
	quantity integer,
	reserved integer default 0,
	
	constraint pk_inventory_id
		primary key (location, item),
		
	constraint fk_in_location_id
		foreign key (location) references locations (id),
		
	constraint fk_in_item_id
		foreign key (item) references items (id)		
);

CREATE TABLE transentries (
	id uuid default gen_random_uuid(),
	transaction uuid not null,
	item uuid not null,
	quantity int not null,
	price int not null,

	constraint pk_transentry_id
		primary key (id),
		
	constraint fk_te_transaction_id
		foreign key (transaction) references transactions (id),
		
	constraint fk_te_item_id
		foreign key (item) references items (id)
);

ALTER TABLE users add
	constraint fk_u_cart_id
		foreign key (cart) references transactions (id);

ALTER TABLE users add
	constraint fk_u_preferred_id
		foreign key (preferred) references locations (id);
