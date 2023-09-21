CREATE TABLE media (
    id SERIAL PRIMARY KEY,
    file_name varchar(255),
    mime_type varchar(50),
    size INT,
    description text,
    upload_date DATE,
    todo_id INT,
    user_id INT,
    created_at timestamp(3) without time zone default current_timestamp,
    updated_at timestamp(3) without time zone default current_timestamp,
    CONSTRAINT FK_media_todos FOREIGN KEY(todo_id) REFERENCES todos(id),
    CONSTRAINT FK_media_users FOREIGN KEY(user_id) REFERENCES users(id)
)