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

DROP FUNCTION get_classes_for_coach
CREATE FUNCTION get_classes_for_coach(id NUMERIC)
RETURNS TABLE (coach_id INT, class_id INT) 
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY SELECT class_coaches.coach_id, class_coaches.class_id FROM class_coaches WHERE class_coaches.coach_id = id;
END;
$$;

