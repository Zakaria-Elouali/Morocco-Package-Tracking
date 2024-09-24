CREATE TABLE IF NOT EXISTS Employee(

       id   SERIAL      PRIMARY KEY,
        firstName       VARCHAR(255),
        secondName      VARCHAR(255),
        dateOfBirth     Date,
        address         VARCHAR(255),
        city            VARCHAR(255),
        postalCode      VARCHAR(255),
        email           VARCHAR(255),
        phone           VARCHAR(255),
        jobTitle        VARCHAR(255),
        created_by               VARCHAR(255),
        created_at               TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
        last_modified_by         VARCHAR(255),
        last_modified_at         TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
        status_code              VARCHAR(255) DEFAULT 'ACTIVE'
    );

CREATE TABLE IF NOT EXISTS Users (
            id SERIAL       PRIMARY KEY,
            username        VARCHAR(255) UNIQUE NOT NULL,
            email           VARCHAR(255) UNIQUE NOT NULL,
            password   VARCHAR(255) NOT NULL,
            first_name      VARCHAR(255),
            last_name       VARCHAR(255),
            date_of_birth   DATE,
            address         VARCHAR(255),
            city            VARCHAR(255),
            postal_code     VARCHAR(20),
            country         VARCHAR(100),
            phone           VARCHAR(20),
            role            VARCHAR(50) DEFAULT 'user',
            last_login      TIMESTAMP,
            created_by               VARCHAR(255),
            created_at               TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
            last_modified_by         VARCHAR(255),
            last_modified_at         TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
            status_code              VARCHAR(255) DEFAULT 'ACTIVE'
    );
