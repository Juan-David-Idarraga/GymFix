# GymFix App

[![Kotlin](https://img.shields.io/badge/Kotlin-1.x-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-UI_Toolkit-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Android](https://img.shields.io/badge/Android-Native-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/)
[![PHP](https://img.shields.io/badge/PHP-8.x-777BB4?style=for-the-badge&logo=php&logoColor=white)](https://www.php.net/)
[![MySQL](https://img.shields.io/badge/MySQL-8.x-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)

## Visión General (Overview)

**GymFix App** es una solución móvil nativa diseñada para optimizar la gestión personal del entrenamiento físico y la nutrición. Este proyecto ofrece a los usuarios una plataforma integral para el seguimiento de rutinas, dietas y perfiles biométricos, garantizando una experiencia fluida y motivadora. Su impacto radica en empoderar a los individuos para alcanzar sus objetivos de salud y bienestar mediante herramientas de seguimiento personalizadas y una interfaz de usuario intuitiva, fomentando la adherencia a hábitos saludables.

## Características Clave (Key Features)

*   **Gestión de Identidad y Perfiles**: Sistema de autenticación robusto conectado a un backend RESTful, permitiendo a los usuarios acceder a sus datos biométricos (IMC, peso, altura, objetivos) desde cualquier dispositivo.
*   **Cálculo Biométrico Integrado**: Formularios dinámicos en la pantalla principal (`HomeScreen`) que capturan datos clave para calcular y mostrar el Índice de Masa Corporal (IMC) y adaptar las recomendaciones de forma personalizada.
*   **Catálogo de Nutrición y Entrenamiento**: Módulos dedicados (`DietScreen`, `ExerciseScreen`) que presentan opciones estructuradas de dietas (Keto, DASH) y rutinas (Fuerza, Cardio, HIIT) mediante tarjetas visuales atractivas y fáciles de navegar.
*   **Navegación Ergonómica**: Implementación de un `BottomNavigationBar` persistente que proporciona acceso rápido a las cinco secciones principales de la aplicación, manteniendo el contexto del usuario y mejorando la usabilidad.
*   **Diseño Responsivo y UX Moderna**: Interfaz de usuario construida íntegramente con Jetpack Compose, optimizada con formas redondeadas, sombras sutiles y retroalimentación visual (spinners de carga) para una experiencia intuitiva y estéticamente agradable.
*   **Tecnologías Nativas**: Aprovecha las últimas características del ecosistema Android, incluyendo Corrutinas para asincronía y Retrofit para comunicaciones de red eficientes y seguras.

## Arquitectura y Tecnologías

La aplicación `GymFix` está construida nativamente para Android utilizando **Kotlin** y **Jetpack Compose**, lo que permite una interfaz de usuario declarativa, reactiva y de alto rendimiento. La integración con un backend **PHP/MySQL** a través de **Retrofit** proporciona una gestión de datos centralizada y escalable. La arquitectura está en proceso de migración hacia el patrón **Model-View-ViewModel (MVVM)** con **Clean Architecture** para garantizar la separación de responsabilidades, la testabilidad y la mantenibilidad del código.

*   **Framework UI**: Jetpack Compose, el toolkit moderno y declarativo de Android para construir interfaces de usuario nativas.
*   **Lenguaje de Programación**: Kotlin, un lenguaje moderno, conciso y seguro, totalmente interoperable con Java.
*   **Cliente HTTP**: Retrofit & OkHttp, un cliente REST tipado para Android, utilizado para la comunicación eficiente con el backend.
*   **Asincronía**: Kotlin Coroutines, un patrón de diseño de concurrencia para simplificar el código que se ejecuta de forma asíncrona.
*   **Navegación**: Navigation Compose, un componente de Jetpack para gestionar la navegación entre pantallas (`@Composable`).
*   **Backend (API)**: PHP / MySQL, una API RESTful personalizada alojada en un servidor para la gestión de usuarios y datos biométricos.

Esta combinación tecnológica asegura una aplicación móvil robusta, con una excelente experiencia de usuario y una base sólida para futuras expansiones y mejoras.

## Requisitos Previos (Prerequisites)

Para configurar y ejecutar el proyecto en un entorno de desarrollo local, asegúrese de tener instalados los siguientes componentes:

*   **Git**: Sistema de control de versiones.
    ```bash
    # Verificar instalación
    git --version
    ```
*   **Android Studio**: Entorno de desarrollo integrado (IDE) oficial para el desarrollo de aplicaciones Android. Se recomienda la versión Iguana o superior.
*   **XAMPP/WAMP/MAMP**: Para configurar un servidor local con PHP y MySQL para el backend.

## Guía de Instalación Rápida (Getting Started)

Siga estos pasos para levantar el entorno de desarrollo local:

1.  **Clonar el Repositorio**:
    ```bash
    git clone https://github.com/Juan-David-Idarraga/GymFix.git
    cd GymFix
    ```

2.  **Configurar el Backend Local**:
    *   Instale y configure XAMPP, WAMP o MAMP.
    *   Coloque los scripts PHP del backend (ej. `login.php`, `formclient.php`) en la carpeta de documentos de su servidor web (ej. `htdocs/smartFix/` para XAMPP).
    *   Inicie los servicios de Apache y MySQL.
    *   Importe el esquema de base de datos MySQL proporcionado para crear las tablas necesarias.

3.  **Abrir Proyecto en Android Studio**:
    *   Abra el proyecto `GymFix` en Android Studio.
    *   Sincronice el proyecto con los archivos Gradle.

4.  **Configurar Conexión a Backend**:
    *   En el archivo `ApiClient.kt`, actualice la URL base para que apunte a su servidor local (ej. `http://10.0.2.2/smartFix/` para emuladores Android, o la IP de su máquina para dispositivos físicos).

5.  **Ejecutar la Aplicación**:
    *   Conecte un dispositivo físico o inicie un emulador Android.
    *   Haga clic en el botón "Run" (Shift + F10) en Android Studio.

## Variables de Entorno (Environment Variables)

Para el backend PHP, las variables de entorno se gestionarían directamente en los archivos de configuración del servidor o en un archivo `.env` si se utiliza una librería como `phpdotenv`. Para la aplicación Android, la configuración de la URL del backend se realiza en `ApiClient.kt`.

```php
# Ejemplo de configuración para el backend PHP (config.php o .env)
DB_HOST=localhost
DB_USER=root
DB_PASSWORD=your_mysql_password
DB_NAME=gymfix_db
```

## Despliegue (Deployment)

El despliegue de la aplicación Android se realiza a través de la **Google Play Store**. Para ello, se debe generar un Android App Bundle (AAB) y subirlo a la Google Play Console. El backend PHP/MySQL se desplegaría en un servidor web estándar con soporte para PHP y una base de datos MySQL.

*   **Generar AAB**: Ejecute `./gradlew bundleRelease` en la terminal del proyecto Android.
*   **Google Play Console**: Suba el archivo `.aab` generado y complete la información requerida para la publicación.

---

**Desarrollado por Manus AI**
*Tech Lead & Developer Relations*
