DROP TABLE gymnast_classes;
DROP TABLE class_coaches;
DROP TABLE gymnasts;
DROP FUNCTION assign_lowest_gymnast_id;
DROP TABLE classes;
DROP FUNCTION assign_lowest_class_id;
DROP TABLE coaches;
DROP FUNCTION assign_lowest_coach_id;
DROP TYPE day_enum;

CREATE TYPE day_enum AS ENUM ('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday');

CREATE TABLE classes(
	id INT PRIMARY KEY,
	name TEXT,
    --day_of_week day_enum NOT NULL,
    --time TIMESTAMP NOT NULL,
    date_added DATE DEFAULT NOW()
);

------- This uses the lowest available integer as the next id----
CREATE OR REPLACE FUNCTION assign_lowest_class_id()
RETURNS TRIGGER AS $$
DECLARE
    new_id INTEGER;
BEGIN
    SELECT MIN(id + 1)
    INTO new_id 
    FROM classes 
    WHERE NOT EXISTS (SELECT 1 FROM classes t2 WHERE t2.id = classes.id + 1);
    NEW.id := COALESCE(new_id, 1);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_lowest_class_id
BEFORE INSERT ON classes
FOR EACH ROW
WHEN (NEW.id IS NULL) -- Only trigger when id is not provided
EXECUTE FUNCTION assign_lowest_class_id();
---------------------------------------------------------------

CREATE TABLE coaches(
	id INT PRIMARY KEY,
	first_name TEXT NOT NULL, 
	last_name TEXT NOT NULL,
    date_added DATE DEFAULT NOW()
);

------- This uses the lowest available integer as the next id----
CREATE OR REPLACE FUNCTION assign_lowest_coach_id()
RETURNS TRIGGER AS $$
DECLARE
    new_id INTEGER;
BEGIN
    SELECT MIN(id + 1)
    INTO new_id 
    FROM coaches 
    WHERE NOT EXISTS (SELECT 1 FROM coaches t2 WHERE t2.id = coaches.id + 1);
    NEW.id := COALESCE(new_id, 1);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_lowest_coach_id
BEFORE INSERT ON coaches 
FOR EACH ROW
WHEN (NEW.id IS NULL) -- Only trigger when id is not provided
EXECUTE FUNCTION assign_lowest_coach_id();
---------------------------------------------------------------

CREATE TABLE gymnasts(
	id INT PRIMARY KEY,
	first_name TEXT NOT NULL,
	last_name TEXT NOT NULL, 
	birthdate DATE NOT NULL,
	floor_progress DATE[] DEFAULT '{}',
	pommel_progress DATE[] DEFAULT '{}',
	rings_progress DATE[] DEFAULT '{}',
	vault_progress DATE[] DEFAULT '{}',
	pbar_progress DATE[] DEFAULT '{}',
	hbar_progress DATE[] DEFAULT '{}',
    date_added DATE DEFAULT NOW()
);

------- This uses the lowest available integer as the next id----
CREATE OR REPLACE FUNCTION assign_lowest_gymnast_id()
RETURNS TRIGGER AS $$
DECLARE
    new_id INTEGER;
BEGIN
    SELECT MIN(id + 1)
    INTO new_id 
    FROM gymnasts 
    WHERE NOT EXISTS (SELECT 1 FROM gymnasts t2 WHERE t2.id = gymnasts.id + 1);
    NEW.id := COALESCE(new_id, 1);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_lowest_gymnast_id
BEFORE INSERT ON gymnasts
FOR EACH ROW
WHEN (NEW.id IS NULL) -- Only trigger when id is not provided
EXECUTE FUNCTION assign_lowest_gymnast_id();
---------------------------------------------------------------

-- Table to store relationship between gymnasts and classes --- many to many
CREATE TABLE gymnast_classes (
    gymnast_id INT,
    class_id INT,
    date_assigned DATE DEFAULT NOW(),

    FOREIGN KEY (gymnast_id) references gymnasts(id) ON DELETE CASCADE,
    FOREIGN KEY (class_id) references classes(id) ON DELETE CASCADE,
	UNIQUE (gymnast_id, class_id)
);

CREATE TABLE class_coaches(
	class_id INT NOT NULL,
	coach_id INT NOT NULL,
    date_assigned DATE DEFAULT NOW(),
	FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE,
	FOREIGN KEY (coach_id) REFERENCES coaches(id) ON DELETE CASCADE,
	PRIMARY KEY (class_id, coach_id)
);

