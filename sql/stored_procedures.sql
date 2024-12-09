CREATE OR REPLACE FUNCTION get_classes(clsname TEXT)
RETURNS SETOF classes
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY SELECT * FROM classes as c
    WHERE (c.name ILIKE '%' || clsname || '%' OR clsname IS NULL);
END;
$$;

CREATE OR REPLACE FUNCTION get_classes_for_gymnast(param_gymnast_id INT)
RETURNS SETOF classes
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY SELECT c.* FROM classes c
    INNER JOIN 
        gymnast_classes gc ON c.id = gc.class_id
    WHERE 
        gc.gymnast_id = param_gymnast_id;
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

CREATE OR REPLACE PROCEDURE insert_class(param_name TEXT)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO classes(name)
    VALUES (param_name);
END;
$$;

CREATE OR REPLACE PROCEDURE delete_gymnast_classes(param_gymnast_id INTEGER)
LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM gymnast_classes as gc
    WHERE gc.gymnast_id = param_gymnast_id;
END;
$$;

CREATE OR REPLACE PROCEDURE insert_gymnast_classes(param_gymnast_id INTEGER, param_class_id INTEGER)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO gymnast_classes(gymnast_id, class_id)
    VALUES (param_gymnast_id, param_class_id);
END;
$$;

CREATE OR REPLACE PROCEDURE update_gymnast(param_id INTEGER, param_floor_progress DATE[], param_pommel_progress DATE[], param_rings_progress DATE[],
    param_vault_progress DATE[], param_pbar_progress DATE[], param_hbar_progress DATE[])
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE gymnasts
    SET 
        floor_progress = param_floor_progress,
        pommel_progress = param_pommel_progress,
        rings_progress = param_rings_progress,
        vault_progress = param_vault_progress,
        pbar_progress = param_pbar_progress,
        hbar_progress = param_hbar_progress
    WHERE gymnasts.id = param_id;
END;
$$;

CREATE OR REPLACE FUNCTION get_gymnast(param_id NUMERIC)
RETURNS SETOF gymnasts
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY SELECT * FROM gymnasts WHERE gymnasts.id = param_id;
END;
$$;

CREATE OR REPLACE FUNCTION get_gymnasts(fname TEXT, lname TEXT, argage NUMERIC, clsname TEXT)
RETURNS SETOF gymnasts 
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY 
    SELECT DISTINCT g.* FROM gymnasts g 
    LEFT JOIN gymnast_classes gc ON g.id=gc.gymnast_id
    LEFT JOIN classes c ON gc.class_id = c.id
    WHERE ((fname IS NULL AND lname IS NULL) OR (LNAME IS NULL AND (g.first_name ILIKE '%' || fname || '%' OR g.last_name ILIKE '%' || fname || '%'))
	OR (g.first_name ILIKE '%' || fname || '%' AND g.last_name ILIKE '%' || lname || '%'))
    AND (c.name = clsname or clsname IS NULL)
    AND (argage IS NULL OR argage = EXTRACT(YEAR FROM AGE(g.birthdate)));
END;
$$;

CREATE OR REPLACE FUNCTION get_coaches(param_first_name TEXT, param_last_name TEXT)
RETURNS SETOF coaches 
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY 
    SELECT c.* FROM coaches c 
	WHERE ((param_first_name IS NULL AND param_last_name IS NULL)
    OR (param_last_name IS NULL AND (c.first_name ILIKE '%' || param_first_name || '%' OR c.last_name ILIKE '%' || param_first_name || '%'))
	OR (c.first_name ILIKE '%' || param_first_name || '%' or c.last_name ILIKE '%' || param_last_name || '%'));
END;
$$;

CREATE OR REPLACE PROCEDURE insert_coach(param_first_name TEXT, param_last_name TEXT)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO coaches(first_name, last_name)
    VALUES (param_first_name, param_last_name);
END;
$$;

CREATE OR REPLACE PROCEDURE delete_class_coaches(param_coach_id INTEGER)
LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM class_coaches as cc
    WHERE cc.coach_id = param_coach_id;
END;
$$;

CREATE OR REPLACE PROCEDURE insert_class_coaches(param_coach_id INTEGER, param_class_id INTEGER)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO class_coaches(coach_id, class_id)
    VALUES(param_coach_id, param_class_id);
END;
$$;

CREATE OR REPLACE FUNCTION get_class_coaches(param_coach_id INTEGER)
RETURNS SETOF classes
LANGUAGE plpgsql
AS $$
BEGIN
    RETURN QUERY 
    SELECT c.* FROM classes c JOIN class_coaches cc ON c.id = cc.class_id
    WHERE cc.coach_id = param_coach_id;
END;
$$;
