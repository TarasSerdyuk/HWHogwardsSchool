CREATE TABLE person (
                        id serial PRIMARY KEY,
                        name VARCHAR(255) NOT NULL ,
                        age INTEGER CONSTRAINT CHECK ( age >= 18 ),
                        has_license BOOLEAN DEFAULT FALSE,
                        car_id INTEGER REFERENCES car(id)
);

CREATE TABLE car (
                     id serial PRIMARY KEY,
                     brand VARCHAR(255) NOT NULL,
                     model VARCHAR(255) UNIQUE,
                     cost INTEGER
)