CREATE OR REPLACE FUNCTION get_coach(id NUMERIC)
RETURNS TABLE(coach_id INT, first_name VARCHAR(40), last_name VARCHAR(40))
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY SELECT coaches.coach_id, coaches.first_name, coaches.last_name FROM coaches WHERE coaches.coach_id = id;
END;
$$;


CREATE OR REPLACE FUNCTION get_classes(clsname TEXT)
RETURNS SETOF classes
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY SELECT * FROM classes
    WHERE classes.name = clsname or clsname IS NULL;
END;
$$;

CREATE OR REPLACE PROCEDURE insert_gymnast(param_fname TEXT, param_lname TEXT, param_birthdate DATE, param_classid INTEGER)
LANGUAGE plpgsql
AS $$
DECLARE
    new_gymnast_id INTEGER;
BEGIN
    INSERT INTO gymnasts (first_name, last_name, birthdate)
    VALUES (param_fname, param_lname, param_birthdate)
    RETURNING id INTO new_gymnast_id;

    INSERT INTO gymnast_classes (gymnast_id, class_id)
    VALUES (new_gymnast_id, param_classid);
END;
$$;

CREATE OR REPLACE FUNCTION get_classes_for_coach(id NUMERIC)
RETURNS TABLE (coach_id INT, class_id INT) 
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY SELECT class_coaches.coach_id, class_coaches.class_id FROM class_coaches WHERE class_coaches.coach_id = id;
END;
$$;

CREATE OR REPLACE FUNCTION get_gymnast(id NUMERIC)
RETURNS SETOF gymnasts
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY SELECT * FROM gymnasts WHERE gymnasts.id = id;
END;
$$;

CREATE OR REPLACE FUNCTION get_gymnasts(fname TEXT, lname TEXT, argage NUMERIC, clsname TEXT)
RETURNS SETOF gymnasts 
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY 
    SELECT g.* FROM gymnasts g 
    LEFT JOIN gymnast_classes gc ON g.id=gc.gymnast_id
    LEFT JOIN classes c ON gc.class_id = c.id
    WHERE ((g.first_name ILIKE '%' || fname || '%' OR fname IS NULL)
	AND (lname IS NULL or g.last_name ILIKE '%' || lname || '%'))
    AND (c.name = clsname or clsname IS NULL)
    AND (argage IS NULL OR argage = EXTRACT(YEAR FROM AGE(g.birthdate)));
END;
$$;



