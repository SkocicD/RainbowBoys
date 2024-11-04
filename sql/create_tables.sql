CREATE TABLE classes(
	class_id INT PRIMARY KEY,
	class_name VARCHAR(60),
	time TIMESTAMP
);

CREATE TABLE coaches(
	coach_id INT PRIMARY KEY,
	first_name VARCHAR(40),
	last_name VARCHAR(40)
);

CREATE TABLE gymnasts(
	gymnast_id INT PRIMARY KEY,
	first_name VARCHAR(40),
	last_name VARCHAR(40),
	birthdate TIMESTAMP,
	class_id INT,
	floor_progress TIMESTAMP[],
	pommel_progress TIMESTAMP[],
	rings_progress TIMESTAMP[],
	vault_progress TIMESTAMP[],
	pbar_progress TIMESTAMP[],
	hbar_progress TIMESTAMP[],

	FOREIGN KEY (class_id) REFERENCES classes(class_id)
		ON DELETE SET NULL
		ON UPDATE CASCADE
);

CREATE TABLE class_coaches(
	class_id INT NOT NULL,
	coach_id INT NOT NULL,
	FOREIGN KEY (class_id) REFERENCES classes(class_id)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	FOREIGN KEY (coach_id) REFERENCES coaches(coach_id)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	UNIQUE (class_id, coach_id)
);

