--Juan Luis Garcia 14189
--Olga Cobaquil 13020 

--*****************************************
-- Creacion de tabla color
--*****************************************
CREATE TABLE color
(
  id_color integer NOT NULL,
  color text,
  CONSTRAINT id_color PRIMARY KEY (id_color)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE color
  OWNER TO postgres;

--*****************************************
-- Creacion de tabla inventario
--*****************************************
CREATE TABLE inventario
(
  id_inventario integer NOT NULL,
  color_idcolor integer,
  talla_idtalla integer,
  marca_idmarca integer,
  prenda_idprenda integer,
  descuento boolean,
  "cantDescuento" numeric,
  precio numeric,
  CONSTRAINT id_inventario PRIMARY KEY (id_inventario),
  CONSTRAINT inventario_color FOREIGN KEY (color_idcolor)
      REFERENCES color (id_color) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT inventario_marca FOREIGN KEY (marca_idmarca)
      REFERENCES marca (id_marca) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT inventario_prenda FOREIGN KEY (prenda_idprenda)
      REFERENCES prenda (id_prenda) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT inventario_talla FOREIGN KEY (talla_idtalla)
      REFERENCES talla (id_talla) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE inventario
  OWNER TO postgres;

CREATE INDEX fki_inventario_color
  ON inventario
  USING btree
  (color_idcolor);
  
CREATE INDEX fki_inventario_marca
  ON inventario
  USING btree
  (marca_idmarca);

CREATE INDEX fki_inventario_prenda
  ON inventario
  USING btree
  (prenda_idprenda);

CREATE INDEX fki_inventario_talla
  ON inventario
  USING btree
  (talla_idtalla);

--*****************************************
-- Creacion de tabla marca
--*****************************************

CREATE TABLE marca
(
  id_marca integer NOT NULL,
  marca text,
  CONSTRAINT id_marca PRIMARY KEY (id_marca)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE marca
  OWNER TO postgres;


--*****************************************
-- Creacion de tabla prenda
--*****************************************

CREATE TABLE prenda
(
  id_prenda integer NOT NULL,
  prenda text,
  CONSTRAINT id_prenda PRIMARY KEY (id_prenda)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE prenda
  OWNER TO postgres;

--*****************************************
-- Creacion de tabla talla
--*****************************************

CREATE TABLE talla
(
  id_talla integer NOT NULL,
  talla text,
  CONSTRAINT id_talla PRIMARY KEY (id_talla)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE talla
  OWNER TO postgres;
