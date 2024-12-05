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

CREATE OR REPLACE PROCEDURE update_gymnast_classes(param_gymnast_id INTEGER, param_class_id INTEGER)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO gymnast_classes(gymnast_id, class_id)
    VALUES (param_gymnast_id, param_class_id)
    ON CONFLICT (gymnast_id, class_id) DO NOTHING;
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
    WHERE id = id_param;
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
    SELECT g.* FROM gymnasts g 
    LEFT JOIN gymnast_classes gc ON g.id=gc.gymnast_id
    LEFT JOIN classes c ON gc.class_id = c.id
    WHERE ((g.first_name ILIKE '%' || fname || '%' OR fname IS NULL)
	AND (lname IS NULL or g.last_name ILIKE '%' || lname || '%'))
    AND (c.name = clsname or clsname IS NULL)
    AND (argage IS NULL OR argage = EXTRACT(YEAR FROM AGE(g.birthdate)));
END;
$$;



