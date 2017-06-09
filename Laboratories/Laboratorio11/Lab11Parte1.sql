--**************************************
--Encuentre la velocidad promedio de las PC
--**************************************
SELECT AVG(VELOCIDAD) 
FROM PC 

--**************************************
--Encuentre la velocidad promedio de portátiles que cuestan más de $1,000.00
--**************************************
SELECT AVG(VELOCIDAD) 
FROM PORTATIL
WHERE PRECIO>1000

--**************************************
--Encuentre precio promedio de PCs elaboradas por DELL
--**************************************
SELECT AVG(PRECIO) as "PRECIO PROMEDIO"
FROM PORTATIL AS T0 INNER JOIN PRODUCTO AS T1 ON T0.MODELO_PORTATIL = T1.MODELO_PRODUCTO
WHERE T1.FABRICANTE='DELL'

--**************************************
--Encuentre, para cada fabricante, el tamaño de pantalla promedio de sus portátiles
--**************************************

SELECT AVG(PANTALLA) as "TAMANO PROMEDIO DE PANTALLA",FABRICANTE
FROM PORTATIL AS T0 INNER JOIN PRODUCTO AS T1 ON T0.MODELO_PORTATIL = T1.MODELO_PRODUCTO
GROUP BY FABRICANTE


--**************************************
--Encuentre, para cada diferente velocidad, el promedio de precio para una PC
--**************************************

SELECT AVG(PRECIO),VELOCIDAD FROM PC
GROUP BY VELOCIDAD

--**************************************
--Encuentre, para cada fabricante que vende PC, el precio máximo de una PC
--**************************************

SELECT MAX(PRECIO) as "PRECIO MAXIMO POR FABRICANTE DE PC",FABRICANTE
FROM PC AS T0 INNER JOIN PRODUCTO AS T1 ON T0.MODELO_PC = T1.MODELO_PRODUCTO
GROUP BY FABRICANTE

--**************************************
--Encuentre, para cada PC con velocidad arriba de 2.0, el precio promedio
--**************************************

SELECT AVG(PRECIO) as "PRECIO PROMEDIO POR MODELO DE PC",MODELO_PC
FROM PC AS T0 INNER JOIN PRODUCTO AS T1 ON T0.MODELO_PC = T1.MODELO_PRODUCTO
WHERE VELOCIDAD>2
GROUP BY MODELO_PC





