/* ───────────────────────────────
   0. PARAMÈTRES GÉNÉRAUX
   ─────────────────────────────── */
SET NAMES utf8mb4;
SET time_zone = '+00:00';
SET sql_mode  = 'STRICT_ALL_TABLES';

/* ───────────────────────────────
   1. CRÉATION DES BASES
   ─────────────────────────────── */
CREATE DATABASE IF NOT EXISTS auth_db
       CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS cinema_db
       CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS movie_db
       CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS schedule_db
       CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

/* ───────────────────────────────
   2. COMPTES APPLICATIFS
   ─────────────────────────────── */
CREATE USER IF NOT EXISTS 'auth_app'     @'%' IDENTIFIED BY 'auth_pwd';
CREATE USER IF NOT EXISTS 'cinema_app'   @'%' IDENTIFIED BY 'cinema_pwd';
CREATE USER IF NOT EXISTS 'movie_app'    @'%' IDENTIFIED BY 'movie_pwd';
CREATE USER IF NOT EXISTS 'schedule_app' @'%' IDENTIFIED BY 'schedule_pwd';

/* ───────────────────────────────
   3. SCHÉMA auth_db
   ─────────────────────────────── */
USE auth_db;

CREATE TABLE users (
                       id            INT AUTO_INCREMENT PRIMARY KEY,
                       username      VARCHAR(60)  NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       role          ENUM('OWNER', 'ADMIN') NOT NULL DEFAULT 'OWNER',
                       created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB;

GRANT ALL ON auth_db.* TO 'auth_app'@'%';

/* ───────────────────────────────
   4. SCHÉMA cinema_db
   ─────────────────────────────── */
USE cinema_db;

CREATE TABLE cinemas (
                         id       INT AUTO_INCREMENT PRIMARY KEY,
                         name     VARCHAR(120) NOT NULL,
                         address  VARCHAR(200) NOT NULL,
                         city     VARCHAR(90)  NOT NULL
) ENGINE = InnoDB;

GRANT ALL ON cinema_db.* TO 'cinema_app'@'%';

/* ───────────────────────────────
   5. SCHÉMA movie_db
   ─────────────────────────────── */
USE movie_db;

CREATE TABLE movies (
                        id          INT AUTO_INCREMENT PRIMARY KEY,
                        title       VARCHAR(150) NOT NULL,
                        duration    INT          NOT NULL,           -- minutes
                        language    VARCHAR(50)  NOT NULL,
                        subtitles   VARCHAR(50),                     -- nullable
                        director    VARCHAR(120) NOT NULL,
                        actors      VARCHAR(255) NOT NULL,
                        min_age     TINYINT      NOT NULL,
                        created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB;

GRANT ALL ON movie_db.* TO 'movie_app'@'%';

/* ───────────────────────────────
   6. SCHÉMA schedule_db
   ─────────────────────────────── */
USE schedule_db;

CREATE TABLE schedules (
                           id            INT AUTO_INCREMENT PRIMARY KEY,
                           cinema_id     INT NOT NULL,
                           movie_id      INT NOT NULL,
                           start_date    DATE NOT NULL,
                           end_date      DATE NOT NULL,
                           days_of_week  SET('MON','TUE','WED','THU','FRI','SAT','SUN') NOT NULL,
                           start_time    TIME NOT NULL,
                           FOREIGN KEY (cinema_id) REFERENCES cinema_db.cinemas(id)
                               ON DELETE CASCADE
                               ON UPDATE CASCADE,
                           FOREIGN KEY (movie_id)  REFERENCES movie_db.movies(id)
                               ON DELETE CASCADE
                               ON UPDATE CASCADE
) ENGINE = InnoDB;

GRANT ALL ON schedule_db.* TO 'schedule_app'@'%';

/* ───────────────────────────────
   7. FINALISATION
   ─────────────────────────────── */
FLUSH PRIVILEGES;
