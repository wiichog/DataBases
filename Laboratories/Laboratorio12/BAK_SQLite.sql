
-- Table: estudiante
CREATE TABLE estudiante ( 
    id       INT     PRIMARY KEY
                     NOT NULL,
    nombre   VARCHAR,
    apellido VARCHAR,
    promedio REAL 
);


-- Table: universidad
CREATE TABLE universidad ( 
    id          INT     PRIMARY KEY
                        NOT NULL,
    universidad VARCHAR,
    direccion   VARCHAR,
    telefono    VARCHAR 
);


-- Table: asignacion
CREATE TABLE asignacion ( 
    id             INT     PRIMARY KEY
                           NOT NULL,
    estudiante_id  INT,
    universidad_id INT,
    materia        VARCHAR,
    nota           REAL 
);
