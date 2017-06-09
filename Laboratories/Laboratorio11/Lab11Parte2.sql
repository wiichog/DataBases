--**************************************
--Utilizando dos instrucciones INSERT, guarde en la base de datos el nuevo modelo de PC X-55441, cuyo fabricante es DELL, con
--3.2 de velocidad, 2 de RAM, 180 de disco duro y $2,499.00 de precio.
--**************************************
INSERT INTO MODELO
VALUES('X-55441',3.2,2,180,2499)
INSERT INTO PRODUCTO
VALUES('DELL','X-55441',1)

--**************************************
--Borre todas las PC con menos de 100 GB de disco duro
--**************************************
DELETE 
FROM PC 
WHERE DISCODURO<100


--**************************************
--Borre todas las portátiles elaboradas por un fabricante que no fabrica impresoras
--**************************************
DELETE FROM PORTATIL
WHERE MODELO_PORTATIL IN
(
SELECT MODELO_PORTATIL FROM PORTATIL AS T0
INNER JOIN PRODUCTO AS T1 ON T1.MODELO_PRODUCTO=T0.MODELO_PORTATIL
WHERE FABRICANTE NOT IN (SELECT FABRICANTE
FROM PRODUCTO
WHERE TIPO = '3'
GROUP BY FABRICANTE))

--**************************************
--DELL compró a HP. Cambie todos los productos elaborados por HP de tal manera que ahora sean elaborados por DELL.
--**************************************
UPDATE PRODUCTO
SET FABRICANTE = 'DELL'
WHERE FABRICANTE = 'HP'

--**************************************
--Para cada PC, duplique la cantidad de RAM y agregue 60 GB de disco duro (utilizar solo una instrucción).
--**************************************
UPDATE PC
SET VELOCIDAD = VELOCIDAD * 2 ,DISCODURO = DISCODURO + 60