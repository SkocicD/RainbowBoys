DROP PROCEDURE update_coach;
CREATE PROCEDURE update_coach(name TEXT, id NUMERIC)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO coaches(coach_id,first_name) VALUES (id, name);
END;
$$;

DROP FUNCTION get_coach;
CREATE FUNCTION get_coach(id NUMERIC)
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

DROP FUNCTION get_gymnast;
CREATE FUNCTION get_gymnast(id NUMERIC)
RETURNS TABLE (first_name VARCHAR(40), last_name VARCHAR(40), class_id INT)
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY SELECT gymnasts.first_name, gymnasts.last_name, gymnasts.class_id FROM gymnasts WHERE gymnasts.gymnast_id = id;
END;
$$;


DROP FUNCTION get_gymnasts_by_single_name;
CREATE FUNCTION get_gymnasts_by_single_name(name TEXT, clsname TEXT)
RETURNS TABLE (gymnast_id INT, first_name VARCHAR(40), last_name VARCHAR(40), class_id INT, class_name VARCHAR(60))
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY SELECT g.gymnast_id, g.first_name, g.last_name, g.class_id, c.class_name FROM gymnasts g JOIN classes c ON g.class_id = c.class_id
    WHERE (g.first_name ILIKE '%' || name || '%'
	OR g.last_name ILIKE '%' || name || '%')
	AND c.class_name ILIKE '%' || clsname || '%';
END;
$$;


DROP FUNCTION get_gymnasts_by_full_name;
CREATE FUNCTION get_gymnasts_by_full_name(fname TEXT, lname TEXT, clsname TEXT)
RETURNS TABLE (gymnast_id INT, first_name VARCHAR(40), last_name VARCHAR(40), class_id INT, class_name VARCHAR(60))
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY SELECT g.gymnast_id, g.first_name, g.last_name, g.class_id, c.class_name FROM gymnasts g JOIN classes c ON g.class_id = c.class_id
    WHERE (g.first_name ILIKE '%' || fname || '%'
	AND g.last_name ILIKE '%' || lname || '%')
	AND c.class_name ILIKE '%' || clsname || '%';
END;
$$;



