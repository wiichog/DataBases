--Juan Luis Garcia 14189
--Olga Cobaquil 13020 

--*****************************************
-- Mostrar id_inventario, prenda y precio de todo el inventario
--*****************************************
SELECT T0.ID_INVENTARIO,T1.PRENDA,T0.PRECIO
FROM INVENTARIO AS T0 INNER JOIN PRENDA AS T1
ON T0.PRENDA_IDPRENDA=T1.ID_PRENDA

--*****************************************
-- Mostrar id_inventario, prenda y color de todas las prendas blancas
--*****************************************
SELECT T0.ID_INVENTARIO,T1.PRENDA,T2.COLOR
FROM INVENTARIO AS T0
INNER JOIN PRENDA AS T1 ON T0.PRENDA_IDPRENDA=T1.ID_PRENDA
INNER JOIN COLOR AS T2 ON T0.COLOR_IDCOLOR=T2.ID_COLOR
WHERE T2.COLOR = 'BLANCO'

--*****************************************
-- Mostrar id_inventario, prenda, color y talla de los suéteres rojos, amarillos o anaranjados con precio mayor a $50
--*****************************************
SELECT T0.ID_INVENTARIO,T1.PRENDA,T2.COLOR,T3.TALLA
FROM INVENTARIO AS T0
INNER JOIN PRENDA AS T1 ON T0.PRENDA_IDPRENDA=T1.ID_PRENDA
INNER JOIN COLOR AS T2 ON T0.COLOR_IDCOLOR=T2.ID_COLOR
INNER JOIN TALLA AS T3 ON T0.TALLA_IDTALLA=T3.ID_TALLA
WHERE (T2.COLOR='ROJO' OR T2.COLOR='AMARILLO' OR T2.COLOR='ANARANJADO') AND T0.PRECIO>50 AND T1.PRENDA='Sueter'

--*****************************************
-- Mostrar id_inventario, prenda, color, marca y talla de todo el inventario
--*****************************************
SELECT T0.ID_INVENTARIO,T1.PRENDA,T2.COLOR,T3.TALLA,T4.MARCA
FROM INVENTARIO AS T0
INNER JOIN PRENDA AS T1 ON T0.PRENDA_IDPRENDA=T1.ID_PRENDA
INNER JOIN COLOR AS T2 ON T0.COLOR_IDCOLOR=T2.ID_COLOR
INNER JOIN TALLA AS T3 ON T0.TALLA_IDTALLA=T3.ID_TALLA
INNER JOIN MARCA AS T4 ON T0.MARCA_IDMARCA=T4.ID_MARCA

--*****************************************
-- Mostrar id_inventario, prenda, color, marca, talla, descuento y precio final de todas las prendas con descuento, ordenado por
-- marca ascendente y por descuento descendente. 
--*****************************************
SELECT T0.ID_INVENTARIO,T1.PRENDA,T2.COLOR,T3.TALLA,T4.MARCA,
T0.DESCUENTO, T0.PRECIO-(T0.PRECIO*T0.CANTDESCUENTO) AS PrecioFinal
FROM INVENTARIO AS T0
INNER JOIN PRENDA AS T1 ON T0.PRENDA_IDPRENDA=T1.ID_PRENDA
INNER JOIN COLOR AS T2 ON T0.COLOR_IDCOLOR=T2.ID_COLOR
INNER JOIN TALLA AS T3 ON T0.TALLA_IDTALLA=T3.ID_TALLA
INNER JOIN MARCA AS T4 ON T0.MARCA_IDMARCA=T4.ID_MARCA
WHERE DESCUENTO=TRUE
ORDER BY T4.MARCA ASC, T0.DESCUENTO DESC
