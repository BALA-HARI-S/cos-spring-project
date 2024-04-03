CREATE TABLE food_item (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DOUBLE PRECISION NOT NULL CHECK (price >= 0),
    created TIMESTAMP NOT NULL,
    modified TIMESTAMP NOT NULL
);

CREATE TABLE food_menu (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    created TIMESTAMP NOT NULL,
    modified TIMESTAMP NOT NULL
);

CREATE TABLE menu_food_item_map (
    id SERIAL PRIMARY KEY,
    food_menu_id BIGINT NOT NULL,
    FOREIGN KEY (food_menu_id) REFERENCES food_menu(id)
);

CREATE TABLE food_menu_item_quantity_map (
    id SERIAL PRIMARY KEY,
    menu_food_item_map_id BIGINT NOT NULL,
    quantity INT NOT NULL CHECK (quantity >= 0),
    created TIMESTAMP NOT NULL,
    modified TIMESTAMP NOT NULL,
    FOREIGN KEY (menu_food_item_map_id) REFERENCES menu_food_item_map(id)
);

CREATE TABLE food_order (
    id SERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    total_cost DOUBLE PRECISION NOT NULL CHECK (total_cost >= 0),
    order_status VARCHAR(255) NOT NULL,
    created TIMESTAMP NOT NULL
);

CREATE TABLE order_food_item_quantity_map (
    id SERIAL PRIMARY KEY,
    food_order_id BIGINT NOT NULL,
    food_menu_item_map_id BIGINT NOT NULL,
    quantity INT NOT NULL CHECK (quantity >= 0),
    FOREIGN KEY (food_order_id) REFERENCES food_order(id),
    FOREIGN KEY (food_menu_item_map_id) REFERENCES food_menu_item_quantity_map(id)
);
