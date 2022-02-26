--- ------------------------------- ---
---       CREATE TABLE SQL         ---
--- ------------------------------- ---

--- Customer
CREATE TABLE pb_customer (
    CUSTOMER_ID        VARCHAR(36)  NOT NULL,
    CUSTOMER_NAME      VARCHAR(255) NOT NULL,
    CUSTOMER_CODE      VARCHAR(14)  NOT NULL,
    CUSTOMER_BIRTHDATE date         NOT NULL,
    CUSTOMER_ACTIVE    BOOL         NOT NULL,
    CONSTRAINT PK_CUSTOMER_ID PRIMARY KEY (CUSTOMER_ID),
    CONSTRAINT UK_CUSTOMER_CODE UNIQUE (CUSTOMER_CODE)
);
CREATE INDEX IDX_CUSTOMER_CODE ON pb_customer (CUSTOMER_CODE);
