CREATE TYPE Role AS ENUM(
    'USER',
    'ADMIN'
);
CREATE TYPE Status AS ENUM(
    'NOT_STARTED',
    'IN_PROGRESS',
    'COMPLETED',
    'CANCELED'
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL,
    email varchar(255) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    role Role DEFAULT 'USER',
    created_at timestamp(3) without time zone default current_timestamp,
    updated_at timestamp(3) without time zone default current_timestamp
);

CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL,
    description varchar(255),
    created_at timestamp(3) without time zone default current_timestamp,
    updated_at timestamp(3) without time zone default current_timestamp
);

CREATE TABLE todos (
    id SERIAL PRIMARY KEY,
    title varchar(255) NOT NULL,
    description text,
    status Status DEFAULT 'NOT_STARTED',
    deadline DATE,
    user_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    created_at timestamp(3) without time zone default current_timestamp,
    updated_at timestamp(3) without time zone default current_timestamp,
    CONSTRAINT fk_todos_users FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT fk_todos_categories FOREIGN KEY(category_id) REFERENCES categories(id)
);

