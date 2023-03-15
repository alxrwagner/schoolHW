-- liquibase formatted sql

-- changeset wagner:add-check-age
alter table student
    add constraint check_age check (age > 16);