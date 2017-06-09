CREATE TRIGGER E1
AFTER INSERT ON estudiante
FOR EACH ROW
WHEN NEW.PROMEDIO > 95 AND NEW.PROMEDIO <=100
BEGIN
    INSERT INTO asignacion VALUES (1000,NEW.id,101, 'D', NULL);
    INSERT INTO asignacion VALUES (1001,NEW.id,101, 'A', NULL);
END

INSERT INTO estudiante VALUES(10, 'Rodrigo', 'Cruz', 99);
INSERT INTO estudiante VALUES(11, 'Maria', 'Lara', 70);

SELECT *
from asignacion


CREATE TRIGGER E2
    AFTER DELETE ON estudiante
    FOR EACH ROW
BEGIN
    DELETE 
        FROM asignacion
        WHERE estudiante_id = OLD.id;
END;

DELETE FROM estudiante WHERE id = 10;


Select *
from asignacion

CREATE TRIGGER E3
AFTER UPDATE OF id ON universidad
FOR EACH ROW
BEGIN
    UPDATE asignacion
    SET universidad_id = NEW.id
    WHERE universidad_id = OLD.id;
END;

INSERT INTO universidad VALUES (2, 'URL', NULL, NULL);
INSERT INTO universidad VALUES (1, 'UVG', NULL, NULL);

UPDATE universidad SET id = 500 WHERE universidad = 'UVG';
UPDATE universidad SET id = 600 WHERE universidad = 'URL';


CREATE TRIGGER E4
BEFORE INSERT ON universidad
FOR EACH ROW
WHEN exists (SELECT * FROM universidad WHERE ID = NEW.id)
BEGIN
    SELECT raise (ignore);
END;

CREATE TRIGGER E5
BEFORE UPDATE OF id ON universidad
FOR EACH ROW
WHEN exists (SELECT * FROM universidad WHERE ID = NEW.id)
BEGIN
    SELECT raise (ignore);--esto solo pasa en sqllite
END;


CREATE TRIGGER E6
BEFORE INSERT ON asignacion
FOR EACH ROW
WHEN (SELECT count(*) FROM asignacion
 WHERE universidad_id = NEW.universidad_id) > 10
BEGIN
    UPDATE universidad SET universidad = universidad || 'FULL'
    WHERE id=NEW.universidad_id;
END;

CREATE TRIGGER E7
BEFORE INSERT ON estudiante
FOR EACH ROW
WHEN NEW.promedio > 100
BEGIN
    SELECT raise(ignore);
END

CREATE TRIGGER E7 
BEFORE INSERT ON estudiante
FOR EACH ROW
WHEN NEW.promedio > 100
BEGIN    
    SELECT raise(ignore);
END;

INSERT INTO estudiante VALUES ('1003', 'Mario', 'Valdez', 100);

CREATE TRIGGER autoasignacion
AFTER INSERT ON asignacion
FOR EACH ROW
WHEN (NEW.universidad_id = 500 AND (SELECT promedio FROM estudiante
WHERE id = NEW.estudiante_id) < 61)
BEGIN
    	UPDATE asignacion
    	SET nota = 0
    	WHERE estudiante_id = NEW.estudiante_id
    	AND universidad_id = NEW.universidad_id;
END


INSERT INTO estudiante VALUES (123, 'Ana', 'Juarez', 50);
INSERT INTO estudiante VALUES (234, 'Luis', 'Gomez', 45);

INSERT INTO asignacion VALUES (2000, 123, 500, 'D', NULL);
INSERT INTO asignacion VALUES (2001, 234, 500, 'A', NULL);
INSERT INTO asignacion VALUES (2002, 234, 501, 'D', NULL);

INSERTINSACREATE TRIGGER E10
AFTER INSERT ON T1
FOR EACH ROW
WHEN (SELECT count(*) FROM t1) < 10
BEGIN
    INSERT INTO T1 VALUES(NEW.A+1);
END

CREATE TRIGGER E111
AFTER INSERT ON T1
FOR EACH ROW
BEGIN 
    UPDATE R1 SET A = 2;
END;

CREATE TRIGGER E112
AFTER INSERT ON T1 
FOR EACH ROW 
WHEN exists (SELECT * FROM T1 WHERE A = 2)
BEGIN
    UPDATE T1 SET A = 3;
END;

