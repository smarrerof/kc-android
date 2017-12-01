# Dishr
Práctica de fundamentos de Android en Kotlin del V KeepCoding Startup Engineering Master Bootcamp (2017)

## Objetivos
El concepto de la práctica en sí es muy sencillo. Tras el verano, los dueños de restaurantes de calidad se han visto desbordados por tanta afluencia de clientes. Está muy bien tener muchos clientes, pero por culpa de eso han tenido algunos problemas:
- Los camareros no se acuerdan de lo que han pedido los clientes, especialmente cuando les piden algunos cambios- Algunos clientes han hecho un tour turístico por los hospitales cercanos debido a que no se les informó de los alérgenos que puedan tener algunas comidas

La aplicación debe contener:

- Debe descargar la lista de posibles platos del restaurante de un servicio. Como no es el propósito principal de esta práctica hacer el servicio puedes valerte de Mocky: http://www.mocky.io/ o de tu propio servicio, lo que prefieras- Una vez descargada la carta, desde la aplicación ha de ser posible acceder a un listado de las mesas de los clientes- Cuando se accede a una mesa debe poder verse lo que han pedido hasta el momento. No es necesario guardar qué ha pedido cada cliente individual, confiaremos en que el camarero se acuerde qué plato iba para cada cliente en una mesa- Desde esa vista de mesa se deben poder añadir platos. Para esto aparecerá otra pantalla donde se pueda elegir el plato de una lista. En esa lista aparece el nombre del plato, una pequeña imagen, unos iconos que indiquen los alérgenos que posee (si los tiene) y su precio- Al pulsar sobre un plato aparece una pantalla con la información del plato (imagen, algún detalle más...), y una caja de texto donde poner las pequeñas variantes que pueda pedir un cliente- Si guardamos dicho plato se añadirá a la lista de los platos que han pedido en una mesa- Debe existir un pequeño menú para calcular la cuenta**Otros**: La aplicación no tiene por qué persistir nada, bastante tienes ya con esto. Además oficialmente no se tiene constancia de ningún móvil Android que se cuelgue y haya que reiniciarlo, por lo que puedes respirar tranquilo.

## Valoración
En la aplicación se valorará:
- Buena aplicación de los conceptos vistos en el curso- Diseño material allá donde sea posible y tenga sentido- Organización y claridad del código- Facilidad de uso para el usuario- Distintas visualizaciones y formas de interactuar para distintos dispositivos como hemos visto en el curso (al menos distingue entre dispositivos tipo teléfono y tipo tableta)- Soporte de varias versiones de Android, cubriendo un mínimo del 90% delos dispositivos actuales

## Detalles a tener en cuentaEn el curso hemos visto que los procesos que pueden durar tiempo necesitan hacerse asíncronos, indicárselo al usuario... etc. Procura hacerlo, en esta práctica tienes algún lugar donde poder aplicarlo.
También hemos visto algunos pequeños detalles de aplicación de diseño material, intenta aplicarlos.La aplicación es deliberadamente “abierta” para que pienses (preferiblemente antes de ponerte a codificar) cómo diseñarla, qué pantallas abren cuáles y de qué modo... etc. No hay necesariamente una única forma “correcta” de hacerla y seguro (eso espero) que cada uno la hace diferente, pero sí que hay varias formas incorrectas: procura evitarlas.
En varios puntos he hablado de una lista. No tiene por qué ser un ListView si no quieres, eres libre de implementarlo como quieras siempre y cuando tenga sentido.
Piensa bien qué debe ser fragment, qué debe ser actividad y cómo comunicar unos con otros.
No te compliques con los datos del servicio, es decir, no hace falta que crees una carta de 128 platos con imágenes Hi-res, lo importante es que vea que has aprendido los fundamentos de Android.

# Detalles de la implementación

- Tanto las mesas como los platos son cargados desde una API Rest que puede ser consultada usando los siguientes endpoints:

	- Mesas: [https://dishrapp.firebaseio.com/table.json](https://dishrapp.firebaseio.com/table.json)
	- Platos: [https://dishrapp.firebaseio.com/dish.json](https://dishrapp.firebaseio.com/dish.json)

- Se han añadido las siguientes funcionalidad fuera de las especificaciones iniciales:

	- Todos los datos son descargados al arrancar la app en una especie de "SplashScreen". Los datos descargados son guardados en la app y no vuelven a ser descargados hasta la siguiente ejecución.
	- Se puede eliminar platos de una mesa haciendo una pulsación larga sobre él
	- Se pide confirmación al salir de la aplicación
	- Se puede limpiar una mesa (vaciar todos los platos)
