CREATE OR REPLACE FUNCTION get_coach(id NUMERIC)
RETURNS TABLE(coach_id INT, first_name VARCHAR(40), last_name VARCHAR(40))
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY SELECT coaches.coach_id, coaches.first_name, coaches.last_name FROM coaches WHERE coaches.coach_id = id;
END;
$$;

DROP FUNCTION get_classes_for_coach;
CREATE FUNCTION get_classes_for_coach(id NUMERIC)
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

CREATE OR REPLACE FUNCTION get_gymnasts_by_single_name(input_name TEXT, clsname TEXT)
RETURNS SETOF gymnasts
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY
    SELECT g.* FROM gymnasts g 
    JOIN gymnast_classes gc ON gc.gymnast_id=g.id
    JOIN classes c ON gc.class_id = c.id
    WHERE (g.first_name ILIKE '%' || input_name || '%'
	OR g.last_name ILIKE '%' || input_name || '%')
    AND c.name = clsname;
END;
$$;

CREATE OR REPLACE FUNCTION get_gymnasts_by_full_name(fname TEXT, lname TEXT, clsname TEXT)
RETURNS SETOF gymnasts 
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY 
    SELECT g.* FROM gymnasts g 
    JOIN gymnast_classes gc ON gc.gymnast_id=g.id
    JOIN classes c ON gc.class_id = c.id
    WHERE (g.first_name ILIKE '%' || fname || '%'
	AND g.last_name ILIKE '%' || lname || '%')
    AND c.name = clsname;
END;
$$;



