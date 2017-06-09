-- Select *
-- From estudiante
-- 
-- Select avg(promedio)
-- From estudiante
-- 
-- Select min(promedio)
-- From estudiante
-- 
-- Select min(promedio)
-- From estudiante
-- 
-- Select min(promedio)
-- From estudiante,asignacion
-- WHERE estudiante.id = asignacion.estudiante_id
-- AND materia = 'C'
-- 
-- Select avg(promedio)
-- From estudiante,asignacion
-- WHERE estudiante.id = asignacion.estudiante_id
-- AND materia = 'C'
-- 
-- Select  min(nota)
-- From estudiante,asignacion
-- WHERE estudiante.id = asignacion.estudiante_id
-- AND materia = 'C'
-- 
-- Select  avg(promedio)
-- From estudiante
-- WHERE estudiante.id IN
-- (SELECT estudiante_id
--  FROM asignacion
--  WHERE materia = 'C')
-- 
--  Select *
-- FROM estudiante
-- WHERE promedio > 95
-- 
-- Select count(*)
-- FROM estudiante
-- WHERE promedio > 95
-- 
-- Select count(*)
-- FROM asignacion
-- WHERE universidad_id = 55
-- 
-- Select count(DISTINCT estudiante_id)
-- FROM asignacion
-- WHERE universidad_id = 55
-- 
-- Select *
-- FROM estudiante E1
-- WHERE (SELECT count(*) FROM estudiante E2
--        WHERE E2.id <> E1.id AND E2.promedio = E1.promedio) = 
--       (Select count(*) FROM estudiante E2
--        WHERE E2.id <> E1.id AND E2.nombre = E1.nombre)
-- 	   
-- SELECT BD.avg_prom, NO_BD.avg_prom
-- FROM (SELECT avg(promedio) as avg_prom FROM estudiante 
--       WHERE id IN (
--           		SELECT estudiante_id FROM asignacion WHERE materia = 'D' )) as BD, 
--       (SELECT avg(promedio) as avg_prom FROM estudiante 
--        WHERE id NOT IN (
--            		SELECT estudiante_id FROM asignacion WHERE materia = 'D')) as NO_BD
-- 
-- 
-- SELECT BD.avg_prom - NO_BD.avg_prom
-- FROM (SELECT avg(promedio) as avg_prom FROM estudiante 
--       WHERE id IN (
--           		SELECT estudiante_id FROM asignacion WHERE materia = 'D' )) as BD, 
--       (SELECT avg(promedio) as avg_prom FROM estudiante 
--        WHERE id NOT IN (
--            		SELECT estudiante_id FROM asignacion WHERE materia = 'D')) as NO_BD
-- 
-- SELECT (SELECT avg(promedio) as avg_prom FROM estudiante
--       WHERE id IN (
--           		SELECT estudiante_id FROM asignacion WHERE materia = 'D' )) - 
--       (SELECT avg(promedio) as avg_prom FROM estudiante 
--        WHERE id NOT IN (
--            		SELECT estudiante_id FROM asignacion WHERE materia = 'D')) as DIF
-- FROM estudiante
-- 
-- SELECT DISTINCT (SELECT avg(promedio) as avg_prom FROM estudiante
--       WHERE id IN (
--           		SELECT estudiante_id FROM asignacion WHERE materia = 'D' )) - 
--       (SELECT avg(promedio) as avg_prom FROM estudiante 
--        WHERE id NOT IN (
--            		SELECT estudiante_id FROM asignacion WHERE materia = 'D')) as DIF
-- FROM estudiante
-- 
-- SELECT *
-- FROM asignacion
-- ORDER BY universidad_id
-- 
-- SELECT universidad_id, count(*)
-- FROM asignacion
-- GROUP BY universidad_id
-- ORDER BY universidad_id
-- 
-- SELECT u.id, count(*)
-- FROM universidad u JOIN asignacion a ON (u.id = a.universidad_id)
-- GROUP BY u.universidad
-- ORDER BY u.id
-- 
-- SELECT u.id,universidad, count(*)
-- FROM universidad u JOIN asignacion a ON (u.id = a.universidad_id)
-- GROUP BY u.id
-- ORDER BY u.id
-- 
-- SELECT u.direccion,sum(e.promedio)
-- FROM universidad u JOIN asignacion a ON (u.id = a.universidad_id)
-- JOIN estudiante e ON (e.id = a.estudiante_id)
-- GROUP BY u.direccion
-- 
-- SELECT u.universidad,a.materia,a.nota
-- FROM asignacion a JOIN universidad u ON (u.id = a.universidad_id)
-- ORDER BY u.id, a.materia
-- 
-- SELECT u.universidad,a.materia,min(a.nota) as nota_menor, max(a.nota) as nota_mayor
-- FROM asignacion a JOIN universidad u ON (u.id = a.universidad_id)
-- GROUP BY u.id, a.materia
-- 
-- SELECT * FROM (SELECT u.universidad, a.materia,min(a.nota) as nota_menor , max(a.nota) as nota_mayor
-- FROM asignacion a JOIN universidad u ON (u.id = a.universidad_id)
-- GROUP BY u.id, a.materia) RES 
-- WHERE RES.nota_menor <> RES.nota_mayor
-- 
-- SELECT nota_mayor - nota_menor
-- FROM (SELECT u.universidad, a.materia,min(a.nota) as nota_menor , max(a.nota) as nota_mayor
-- FROM asignacion a JOIN universidad u ON (u.id = a.universidad_id)
-- GROUP BY u.id, a.materia) RES 
-- WHERE RES.nota_menor <> RES.nota_mayor
-- 
-- SELECT max(nota_mayor - nota_menor)
-- FROM (SELECT u.universidad, a.materia,min(a.nota) as nota_menor , max(a.nota) as nota_mayor
-- FROM asignacion a JOIN universidad u ON (u.id = a.universidad_id)
-- GROUP BY u.id, a.materia) RES 
-- WHERE RES.nota_menor <> RES.nota_mayor
-- 
-- SELECT e.id, u.universidad
-- FROM estudiante e JOIN asignacion a ON (e.id = a.estudiante_id)
-- JOIN universidad u ON (u.id = a.universidad_id)
-- ORDER BY e.id
-- 
-- SELECT e.id, count(DISTINCT u.universidad)
-- FROM estudiante e JOIN asignacion a ON (e.id = a.estudiante_id)
-- JOIN universidad u ON (u.id = a.universidad_id)
-- GROUP BY e.id
-- 
-- SELECT e.id, e.nombre, count(DISTINCT u.universidad)
-- FROM estudiante e JOIN asignacion a ON (e.id = a.estudiante_id)
-- JOIN universidad u ON (u.id = a.universidad_id)
-- GROUP BY e.id
-- 
-- UNION
-- 
-- SELECT e.id, e.nombre, 0
-- FROM estudiante e JOIN asignacion a ON (e.id = a.estudiante_id)
-- JOIN universidad u ON (u.id = a.universidad_id)
-- WHERE e.id NOT IN (SELECT estudiante_id FROM asignacion)
-- 
-- SELECT u.universidad
-- FROM universidad u JOIN asignacion a ON (u.id = a.universidad_id)
-- GROUP BY u.universidad
-- HAVING count(*) < 3 
-- 
-- SELECT materia
-- FROM estudiante e JOIN asignacion a ON (e.id = a.estudiante_id)
-- GROUP BY materia 
-- HAVING max(e.promedio) < (SELECT avg(promedio) FROM estudiante)
-- 
-- SELECT materia
-- FROM estudiante e JOIN asignacion a ON (e.id = a.estudiante_id)
-- GROUP BY materia 
-- HAVING max(e.promedio) < 100

insert into universidad
VALUES (101,'UVG', 'Guatemala', '222333' )

SELECT * FROM UNIVERSIDAD ORDER BY ID DESC

INSERT INTO ESTUDIANTE VALUES (200, 'PEPITO' , 'PEREZ' , 90)

SELECT * FROM ESTUDIANTE WHERE ID NOT IN (SELECT ESTUDIANTE_ID FROM ASIGNACION)

INSERT INTO ASIGNACION
SELECT NEXTVAL('ASIGNACION_ID_SEQ'), ESTUDIANTE.ID, 101, 'D', NULL
FROM ESTUDIANTE
WHERE ID NOT IN (SELECT ESTUDIANTE_ID FROM ASIGNACION)

SELECT * FROM ASIGNACION ORDER BY ID DESC


SELECT *
FROM ESTUDIANTE
WHERE ID IN (SELECT ESTUDIANTE_ID FROM ASIGNACION WHERE MATERIA = 'D' AND NOTA <61)


SELECT NULL, ESTUDIANTE.ID, 101, 'D', NULL
FROM ESTUDIANTE
WHERE ID IN (SELECT ESTUDIANTE_ID FROM ASIGNACION WHERE MATERIA = 'D' AND NOTA <61)

INSERT INTO ASIGNACION
SELECT NEXTVAL('ASIGNACION_ID_SEQ'), ESTUDIANTE.ID, 101, 'D', NULL
FROM ESTUDIANTE
WHERE ID IN (SELECT ESTUDIANTE_ID FROM ASIGNACION WHERE MATERIA = 'D' AND NOTA <61)

DELETE FROM ESTUDIANTE WHERE ID IN(
SELECT ESTUDIANTE_ID
FROM ASIGNACION
GROUP BY ESTUDIANTE_ID
HAVING COUNT(DISTINCT MATERIA) > 7)


SELECT *
FROM ESTUDIANTE WHERE ID  = 110

SELECT COUNT(*) FROM ESTUDIANTE

DELETE FROM ASIGNACION WHERE ESTUDIANTE_ID IN(
SELECT ESTUDIANTE_ID--, COUNT(DISTINCT MATERIA)
FROM ASIGNACION
GROUP BY ESTUDIANTE_ID
HAVING COUNT(DISTINCT MATERIA) > 7)


SELECT *
FROM ASIGNACION 
WHERE UNIVERSIDAD_ID = 55
AND ESTUDIANTE_ID IN (SELECT ID FROM ESTUDIANTE WHERE PROMEDIO<61)


UPDATE ASIGNACION 
SET NOTA = 61
WHERE UNIVERSIDAD_ID = 55
AND ESTUDIANTE_ID IN (SELECT ID FROM ESTUDIANTE WHERE PROMEDIO < 61)


SELECT * FROM ASIGNACION
WHERE MATERIA = 'B'
AND ESTUDIANTE_ID IN (
	SELECT ID FROM ESTUDIANTE
	WHERE PROMEDIO >= ALL
	(SELECT PROMEDIO FROM ESTUDIANTE WHERE ID IN
	(SELECT ESTUDIANTE_ID FROM ASIGNACION WHERE MATERIA = 'B')))


	
UPDATE asignacion 
SET materia = 'D' 
WHERE materia = 'B' 
AND estudiante_id IN
		(SELECT id FROM estudiante 
		WHERE promedio >= ALL 
				(SELECT promedio FROM estudiante 
				WHERE id IN (SELECT estudiante_id FROM asignacion WHERE materia = 'B')))



