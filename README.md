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
*  MainActivity es la actividad principal de la aplicación. Aquí se muestra la lista de categorías de platos disponibles.
*  MealsListActivity es la actividad que se muestra cuando se selecciona una categoría. Aquí se muestra la lista de platos en la categoría seleccionada.
*  DetailActivity es la actividad que se muestra cuando se selecciona un plato. Aquí se muestra información a detalle sobre el plato seleccionado, incluyendo un vídeo sobre cómo prepararlo (si tiene), un indicador de categoría y área, un botón para agregar el plato a favoritos, una lista de ingredientes con medidas e imágenes, instrucciones y un botón para compartir información.
*  FavoritesActivity permite al usuario ver sus platos guardados como favoritos. Gracias al uso de SQLite, se pueden ver incluso sin conexión.
*  FavoriteDetailActivity es la vista offline de DetailActivity para los platos favoritos. Retiene la mayoría de funcionalidad de su versión online, lo único que no se guarda son el vídeo y las imágenes de los ingredientes.
*  SettingsActivity permite al usuario configurar ajustes de la aplicación. Las opciones que hay son para activar/desactivar el mdoo oscuro y cambiar el idioma.

*  **Debe poder pasar un extra (parámetro) entre al menos dos actividades, recuperarlo y usarlo en la segunda actividad.**

Cuando se pasa de MainActivity a MealsListActivity, se usa el nombre de la categoría para decidir qué platos se muestra al usuario.
Cuando se pasa de MealsListActivity a DetailActivity o de FavoritesActivity a FavoriteDetailActivity, se usa el ID del plato elegido para mostrar detalles sobre el mismo.

*  **Debe mostrar al menos un diálogo (AlertDialog) en respuesta a una acción del usuario.**

En la MainActivity, los ítems de cateogrías tienen un botón que permiten mostrar más información sobre ésta.

*  **Debe implementar un sistema de sesión, guardando al menos un valor ens esión mediante SharedPreferences.**

La aplicación guarda los ajustes del usuario y los sigue usando en las siguientes sesiones.

*  **Debe incluir una tabla en una base de datos (SQLite) para almacenar y gestionar datos relevantes para la aplicación.**

Los platos favoritos del usuario se guardan y obtienen de una base de datos local, permitiendo al usuario accederlos sin necesitar conexión a Internet.

*  **Debe realizar llamadas a un API Rest para obtener datos utilizando Retrofit.**

Para la obtención de todos los datos sobre los platos disponibles, se hacen llamadas a la API de TheMealDB, como fue mencionado antes.

*  **Debe utilizar un RecyclerView para mostrar una lista de elementos, y capturar al menos un evento de clic en cada elemento con una función lambda.**

La aplicación contiene 3 RecyclerView:
*  item_category muestra el nombre y una imágen de la categoría, junto con un botón para obtener más información y otro evento de clic para llevar al usuario a los platos de ésa categoría.
*  item_meal muestra el nombre y una imágen del plato, junto con un botón para marcar el plato como favorito y otro evento de clic para llevar al usuario a los detalles de ése plato.
*  item_gallery muestra la imágen de un ingrediente del plato junto con su medida y nombre.

También he realizado 3 de los 4 requisitos para los puntos extra:

* **Mostrar un menú en la AppBar (barra superior)**

En algunas vistas se usa un menú personalizado, para que el usuario pueda acceder a la búsqueda de categorías/platos por nombre, sus platos favoritos y la configuración.

* **Internacionalización (un idioma es suficiente)**

La aplicación se encuentra disponible tanto en español como en inglés. Todos los diálogos fueron traducidos.

* **Usar ViewBinding en vez de findViewById(resId: Int)**

Todas las vistas en la aplicación usan ViewBinding.

El único que no implementé fue el último requisito (Investigar y usar TextField de Material Design), ya que no se me ocurrió un buen lugar para ponerlo en la aplicación.
