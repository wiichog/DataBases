SELECT modelo_pc,velocidad,discoduro FROM pc WHERE precio>1000

SELECT modelo_pc,velocidad as Gigahertz,discoduro AS gigabytes FROM pc WHERE precio>1000 ORDER BY discoduro desc

SELECT fabricante from impresora,producto
where impresora.modelo_impresora = producto.modelo_producto

SELECT modelo_portatil,ram,pantalla FROM portatil where precio>1500

SELECT modelo_impresora,tipo,precio FROM impresora ORDER BY tipo

SELECT modelo_pc,discoduro from PC where velocidad>3.2 and precio>2000