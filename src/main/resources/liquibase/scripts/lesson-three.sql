-- liquibase formatted sql

-- changeset tsedyuk:1
CREATE INDEX student_name_index ON student(name);

-- changeset tsedyuk:2
CREATE INDEX faculty_name_color_index ON faculty(name, color);