# GymFix App

## Proyecto de Gestión Integral de Fitness y Nutrición

Este proyecto representa una solución móvil nativa diseñada para optimizar la gestión personal del entrenamiento físico y la nutrición. Desarrollada por **Technology of Jota** y liderada por **Juan Idarraga**, esta plataforma centraliza funcionalidades críticas para el seguimiento de rutinas, dietas y perfiles biométricos, garantizando una experiencia de usuario fluida y motivadora.

## Visión General del Proyecto

La aplicación `GymFix` es una plataforma móvil construida nativamente para Android utilizando **Kotlin** y **Jetpack Compose**, ofreciendo una interfaz de usuario declarativa, reactiva y de alto rendimiento. La integración con un backend **PHP/MySQL** a través de **Retrofit** proporciona una gestión de datos centralizada, mientras que el diseño visual se apoya en una paleta de colores vibrante (Naranja/Amarillo) que refuerza la identidad energética de la marca.

## Características Clave

*   **Gestión de Identidad y Perfiles**: Sistema de autenticación conectado a un backend RESTful, permitiendo a los usuarios acceder a sus datos biométricos (IMC, peso, altura, objetivos) desde cualquier dispositivo.
*   **Cálculo Biométrico Integrado**: Formularios dinámicos en la pantalla principal (`HomeScreen`) que capturan datos clave para calcular y mostrar el Índice de Masa Corporal (IMC) y adaptar las recomendaciones.
*   **Catálogo de Nutrición y Entrenamiento**: Módulos dedicados (`DietScreen`, `ExerciseScreen`) que presentan opciones estructuradas de dietas (Keto, DASH) y rutinas (Fuerza, Cardio, HIIT) mediante tarjetas visuales atractivas.
*   **Navegación Ergonómica**: Implementación de un `BottomNavigationBar` persistente que proporciona acceso rápido a las cinco secciones principales de la aplicación, manteniendo el contexto del usuario.
*   **Diseño Responsivo y UX Moderna**: Interfaz de usuario construida íntegramente con Jetpack Compose, optimizada con formas redondeadas, sombras sutiles y retroalimentación visual (spinners de carga) para una experiencia intuitiva.
*   **Tecnologías Nativas**: Aprovecha las últimas características del ecosistema Android, incluyendo Corrutinas para asincronía y Retrofit para comunicaciones de red eficientes.

## Stack Tecnológico

| Categoría | Tecnología | Descripción |
| :--- | :--- | :--- |
| **Framework UI** | Jetpack Compose | Toolkit moderno y declarativo de Android para construir interfaces de usuario nativas. |
| **Lenguaje de Programación** | Kotlin | Lenguaje moderno, conciso y seguro, totalmente interoperable con Java. |
| **Cliente HTTP** | Retrofit & OkHttp | Cliente REST tipado para Android, utilizado para la comunicación con el backend. |
| **Asincronía** | Kotlin Coroutines | Patrón de diseño de concurrencia para simplificar el código que se ejecuta de forma asíncrona. |
| **Navegación** | Navigation Compose | Componente de Jetpack para gestionar la navegación entre pantallas (Composables). |
| **Backend (API)** | PHP / MySQL | API RESTful personalizada alojada en servidor para la gestión de usuarios y datos biométricos. |

## Arquitectura del Sistema (Proyectada a MVVM)

El proyecto está en proceso de migración hacia el patrón **Model-View-ViewModel (MVVM)** con **Clean Architecture** para garantizar la separación de responsabilidades y la testabilidad.

1.  **Capa de Presentación (UI)**: Contiene las funciones `@Composable` de Jetpack Compose. Es responsable únicamente de observar el estado emitido por el ViewModel y renderizar la interfaz.
2.  **Capa de Presentación (ViewModel)**: Gestiona el estado de la UI (`StateFlow`) y maneja la lógica de presentación. Sobrevive a los cambios de configuración (ej. rotación de pantalla).
3.  **Capa de Dominio (Domain)**: Contiene los Casos de Uso (ej. `CalcularIMCUseCase`, `LoginUsuarioUseCase`) que encapsulan la lógica de negocio pura.
4.  **Capa de Datos (Data)**: Implementa los repositorios. Contiene los Data Sources remotos (Retrofit API) y locales (Room Database para caché offline).

## Flujo de Datos (Ejemplo: Login)

1.  **Interacción del Usuario**: El usuario ingresa sus credenciales en `LoginScreen` y pulsa "Continuar".
2.  **ViewModel**: La UI invoca una función en el `LoginViewModel`, el cual actualiza el estado a `cargando = true`.
3.  **Corrutina & Repositorio**: El ViewModel lanza una corrutina en el hilo `Dispatchers.IO` y llama al `AuthRepository`.
4.  **Llamada de Red**: El Repositorio utiliza `ApiClient` (Retrofit) para realizar una petición POST asíncrona al endpoint `login.php`.
5.  **Respuesta**: El backend valida las credenciales en MySQL y devuelve un JSON con el resultado.
6.  **Actualización de Estado**: El Repositorio mapea el JSON a un objeto de dominio, el ViewModel actualiza el `StateFlow` con el resultado (éxito o error), y la UI reacciona automáticamente navegando a `HomeScreen` o mostrando un mensaje de error.

## Guía de Despliegue (Android & Backend)

### 1. Configuración del Backend (PHP/MySQL)
1.  Desplegar los scripts PHP (`login.php`, `formclient.php`) en un servidor web (Apache/Nginx) con soporte PHP.
2.  Importar el esquema de base de datos MySQL proporcionado para crear las tablas de usuarios y registros biométricos.
3.  Asegurar que el servidor tenga un certificado SSL válido (HTTPS) para encriptar el tráfico de red.

### 2. Configuración de la Aplicación (Android)
1.  Actualizar la URL base en `ApiClient.kt` para que apunte al servidor de producción (ej. `https://api.gymfix.com/`).
2.  Eliminar `android:usesCleartextTraffic="true"` del `AndroidManifest.xml` para forzar conexiones seguras.
3.  Configurar el archivo `keystore.jks` y las credenciales de firma en `app/build.gradle.kts` para la variante `release`.

### 3. Despliegue a Google Play Store
1.  Generar el Android App Bundle (AAB) ejecutando `./gradlew bundleRelease`.
2.  Subir el archivo `.aab` generado a la Google Play Console.
3.  Completar las fichas de la tienda, políticas de privacidad y cuestionarios de clasificación de contenido antes de enviar a revisión.

## Estándares de Contribución

Para mantener la calidad y consistencia del código, todos los contribuidores deben adherirse a los siguientes estándares:

1.  **Convenciones de Nomenclatura (Kotlin)**:
    *   Clases, Interfaces y Composables: `PascalCase` (ej. `ProfileScreen`).
    *   Variables, Funciones y Propiedades: `camelCase` (ej. `navController`, `onLoginClick()`).
    *   Constantes: `SCREAMING_SNAKE_CASE` (ej. `BASE_URL`).
2.  **Gestión de Ramas (Git Flow)**:
    *   `main`: Rama de producción, siempre estable.
    *   `develop`: Rama de integración para la próxima versión.
    *   `feature/nombre-funcionalidad`: Ramas efímeras para nuevas características.
3.  **Commits (Conventional Commits)**:
    *   Usar prefijos estandarizados: `feat:`, `fix:`, `refactor:`, `docs:`, `chore:`, `ui:`.
    *   Ejemplo: `feat: integrar Retrofit para autenticación de usuarios`.
4.  **Calidad de Código (Compose)**:
    *   Las funciones `@Composable` deben ser *stateless* (sin estado interno) siempre que sea posible, elevando el estado (State Hoisting) a sus componentes padres o ViewModels.
    *   Evitar lógica de negocio compleja o llamadas de red directamente dentro de los Composables.

## Instalación y Configuración (Para Desarrollo Local)

Para ejecutar este proyecto localmente, sigue los siguientes pasos:

1.  **Clonar el Repositorio**:
    ```bash
    git clone https://github.com/Juan-David-Idarraga/GymFix.git
    cd GymFix
    ```
2.  **Configurar el Backend Local**:
    *   Instalar XAMPP/WAMP o similar.
    *   Colocar los archivos PHP en la carpeta `htdocs/smartFix/`.
    *   Iniciar los servicios de Apache y MySQL.
3.  **Ejecutar la Aplicación**:
    *   Abrir el proyecto en **Android Studio** (Iguana o superior recomendado).
    *   Sincronizar el proyecto con los archivos Gradle.
    *   Conectar un dispositivo físico o iniciar un emulador (asegurarse de que la IP `10.0.2.2` en `ApiClient.kt` sea correcta para el emulador).
    *   Hacer clic en "Run" (Shift + F10).

## Autor y Empresa

Este proyecto ha sido desarrollado por:

**Juan Idarraga**
*   **Empresa**: Technology of Jota
*   **Portafolio**: https://www.linkedin.com/in/juan-david-idarraga-11088b387/

---
