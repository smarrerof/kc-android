# Dishr
Práctica de fundamentos de Android en Kotlin del V KeepCoding Startup Engineering Master Bootcamp (2017)

## Objetivos
El concepto de la práctica en sí es muy sencillo. Tras el verano, los dueños de restaurantes de calidad se han visto desbordados por tanta afluencia de clientes. Está muy bien tener muchos clientes, pero por culpa de eso han tenido algunos problemas:


La aplicación debe contener:

- Debe descargar la lista de posibles platos del restaurante de un servicio. Como no es el propósito principal de esta práctica hacer el servicio puedes valerte de Mocky: http://www.mocky.io/ o de tu propio servicio, lo que prefieras

## Valoración
En la aplicación se valorará:


## Detalles a tener en cuenta





# Detalles de la implementación

- Tanto las mesas como los platos son cargados desde una API Rest que puede ser consultada usando los siguientes endpoints:

	- Mesas: [https://dishrapp.firebaseio.com/table.json](https://dishrapp.firebaseio.com/table.json)
	- Platos: [https://dishrapp.firebaseio.com/dish.json](https://dishrapp.firebaseio.com/dish.json)

- Se han añadido las siguientes funcionalidad fuera de las especificaciones iniciales:

	- Todos los datos son descargados al arrancar la app en una especie de "SplashScreen". Los datos descargados son guardados en la app y no vuelven a ser descargados hasta la siguiente ejecución.
	- Se puede eliminar platos de una mesa haciendo una pulsación larga sobre él
	- Se pide confirmación al salir de la aplicación
	- Se puede limpiar una mesa (vaciar todos los platos)