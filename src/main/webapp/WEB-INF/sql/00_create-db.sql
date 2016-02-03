DROP DATABASE IF EXISTS "contactsserver_db";
DROP USER IF EXISTS "contactsserver_user";
CREATE USER "contactsserver_user" PASSWORD 'welcome';
CREATE DATABASE "contactsserver_db" owner "contactsserver_user" ENCODING = 'UTF-8';
