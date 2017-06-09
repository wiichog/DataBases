SELECT fabricante
from pc,producto
where pc.modelo_pc=producto.modelo_producto
and velocidad>=3.0


SELECT * from impresora as E1
WHERE NOT EXISTS(SELECT * FROM impresora AS E2
		WHERE E2.precio > E1.precio)

SELECT * FROM PORTATIL
WHERE VELOCIDAD <= ALL(
SELECT VELOCIDAD
FROM PC )