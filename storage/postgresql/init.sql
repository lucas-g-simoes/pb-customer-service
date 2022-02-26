--
--  PlatformBuilders Test
--  Script Initialize Database
--

CREATE EXTENSION IF NOT EXISTS dblink;

DO
$do$
BEGIN
    -- DATABASE: pb-customer-db/pb-customer-schem
    IF EXISTS (SELECT 1 FROM pg_database WHERE datname = 'pb_customer_db') THEN
        RAISE NOTICE 'Database "pb_customer_db" already exists';
    ELSE
        -- DATABASE
        PERFORM dblink_exec('dbname=' || current_database(), 'CREATE DATABASE "pb_customer_db"');
        -- SCHEMA
        PERFORM dblink_exec('dbname=pb_customer_db', 'CREATE SCHEMA IF NOT EXISTS "pb_customer_schem"');
    END IF;
END
$do$;
