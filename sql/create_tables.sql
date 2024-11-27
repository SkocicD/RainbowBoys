CREATE TYPE day_enum AS ENUM ('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday');

CREATE TABLE classes(
	class_id INT PRIMARY KEY,
	class_name TEXT,
    day_of_week day_enum NOT NULL,
	time TIMESTAMP NOT NULL,
    date_added DATE
);

CREATE TABLE coaches(
	coach_id INT PRIMARY KEY,
	first_name TEXT NOT NULL, 
	last_name TEXT NOT NULL,
    date_added DATE
);

CREATE TABLE gymnasts(
	gymnast_id INT PRIMARY KEY,
	first_name TEXT NOT NULL,
	last_name TEXT NOT NULL, 
	birthdate DATE NOT NULL,
	floor_progress DATE[],
	pommel_progress DATE[],
	rings_progress DATE[],
	vault_progress DATE[],
	pbar_progress DATE[],
	hbar_progress DATE[],
    date_added DATE,

	FOREIGN KEY (class_id) REFERENCES classes(class_id) ON DELETE SET NULL
);

-- Table to store relationship between gymnasts and classes --- many to many
CREATE TABLE gymnast_classes (
    gymnast_id INT,
    class_id INT,
    date_assigned DATE,

    FOREIGN KEY (gymnast_id) references gymnasts(gymnast_id) ON DELETE CASCADE,
    FOREIGN KEY (class_id) references classes(class_id) ON DELETE CASCADE,
	UNIQUE (gymnast_id, class_id)
);

CREATE TABLE class_coaches(
	class_id INT NOT NULL,
	coach_id INT NOT NULL,
    date_assigned DATE,
	FOREIGN KEY (class_id) REFERENCES classes(class_id) ON DELETE CASCADE,
	FOREIGN KEY (coach_id) REFERENCES coaches(coach_id) ON DELETE CASCADE,
	UNIQUE (class_id, coach_id)
);

