# MealsList-Android

Una sencilla aplicación para Android que muestra una lista de recetas de cocina, desarrollada en Kotlin.

## Características

*   Ver una lista de recetas de comida.
*   Buscar recetas por categoría.
*   Ver los detalles de una receta, incluyendo ingredientes e instrucciones.
*   Guardar recetas en favoritos para poder verlas incluso sin conexión a internet.

## Dependencias utilizadas

*   **Retrofit**: Para realizar peticiones a la API de [TheMealDB](https://www.themealdb.com/).
*   **Coil**: Para la carga de imágenes.
*   **SQLite**: Para almacenar las recetas favoritas en una base de datos local.
*   **[Youtube Player](https://github.com/PierfrancescoSoffritti/android-youtube-player)**: Para reproducir videos de YouTube.

## Cómo compilar y ejecutar

1.  Clona este repositorio.
2.  Abre el proyecto en Android Studio.
3.  Ejecuta la aplicación en un emulador o en un dispositivo físico.

## Cómo se cumplen los requisitos de la práctica

*  **Debe constar de al menos tres actividades diferentes. Se valorarán según la complejidad y la funcionalidad de cada una.**

La aplicación consta de 6 actividades, doble de lo pedido en los requisitos:
*  MainActivity es la actividad principal de la aplciación

*  **Debe poder pasar un extra (parámetro) entre al menos dos actividades, recuperarlo y usarlo en la segunda actividad.**

*  **Debe mostrar al menos un diálogo (AlertDialog) en respuesta a una acción del usuario.**

En la MainActivity, los elementos de cateogrías tienen un botoó

*  **Debe implementar un sistema de sesión, guardando al menos un valor ens esión mediante SharedPreferences.**



*  **Debe incluir una tabla en una base de datos (SQLite) para almacenar y gestionar datos relevantes para la aplicación.**

*  **Debe realizar llamadas a un API Rest para obtener datos utilizando Retrofit.**

Para la obtención de todos los datos sobre los platos disponibles, se hacen llamadas a la API de TheMealDB, como antes fue mencionado.

*  **Debe utilizar un RecyclerView para mostrar una lista de elementos, y capturar al menos un evento de clic en cada elemento con una función lambda.**

La aplicación contiene 3 RecyclerView

También he realizado 3 de los 4 requisitos para los puntos extra:

* **Mostrar un menú en la AppBar (barra superior)**

En algunas vistas se usa un menú personalizado, para que el usuario pueda acceder a la búsqueda de categorías/platos por nombre, sus platos favoritos y la configuración.

* **Internacionalización (un idioma es suficiente)**

La aplicación se encuentra disponible tanto en español como en inglés. Todos los diálogos fueron traducidos

* **Usar ViewBinding en vez de findViewById(resId: Int)**

Todas las vistas en la aplicación usan ViewBinding.

El único que no implementé fue el último requisito (Investigar y usar TextField de Material Design), ya que no se me ocurrió un buen lugar para ponerlo en la aplicación.