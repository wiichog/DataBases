CREATE DATABASE "esquemaEquipos"
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'English_United States.1252'
       LC_CTYPE = 'English_United States.1252'
       CONNECTION LIMIT = -1;

CREATE TABLE public.impresora
(
  modelo_impresora text NOT NULL, -- 
  color integer,
  tipo text, -- 
  precio integer,
  CONSTRAINT modelo_impresora PRIMARY KEY (modelo_impresora),
  CONSTRAINT impresora_producto FOREIGN KEY (modelo_impresora)
      REFERENCES public.producto (modelo_producto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.impresora
  OWNER TO postgres;
COMMENT ON COLUMN public.impresora.modelo_impresora IS '
';
COMMENT ON COLUMN public.impresora.tipo IS '
';

CREATE TABLE public.pc
(
  modelo_pc text NOT NULL,
  velocidad numeric,
  ram integer,
  discoduro integer,
  precio integer,
  CONSTRAINT modelo_pc PRIMARY KEY (modelo_pc),
  CONSTRAINT pc_producto FOREIGN KEY (modelo_pc)
      REFERENCES public.producto (modelo_producto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.pc
  OWNER TO postgres;

CREATE TABLE public.portatil
(
  modelo_portatil text NOT NULL,
  velocidad numeric,
  ram integer,
  discoduro integer,
  pantalla text,
  precio integer,
  CONSTRAINT modelo_portatil PRIMARY KEY (modelo_portatil),
  CONSTRAINT portatil_producto FOREIGN KEY (modelo_portatil)
      REFERENCES public.producto (modelo_producto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.portatil
  OWNER TO postgres;

CREATE TABLE public.producto
(
  fabricante text,
  modelo_producto text NOT NULL,
  tipo text,
  CONSTRAINT modelo_producto PRIMARY KEY (modelo_producto)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.producto
  OWNER TO postgres;
