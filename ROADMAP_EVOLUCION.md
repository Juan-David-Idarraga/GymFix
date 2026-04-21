# Roadmap de Evolución — GymFix

**Fecha:** Abril 2026
**Proyecto:** GymFix — Aplicación Móvil Android (Jetpack Compose)
**Autor:** Juan Idarraga — Senior Technical Lead

---

## 1. Visión Estratégica

El proyecto "GymFix" tiene el potencial de convertirse en una herramienta integral para la gestión del fitness personal. Para elevarlo de un prototipo funcional a una aplicación competitiva en el mercado de la salud y el bienestar, es necesario implementar funcionalidades que personalicen la experiencia del usuario, fomenten la consistencia y aprovechen las capacidades del dispositivo móvil.

A continuación, se proponen 3 funcionalidades de alto impacto, justificadas desde la perspectiva técnica y de experiencia de usuario (UX).

---

## 2. Funcionalidad 1: Generación de Rutinas y Dietas Personalizadas con IA

### 2.1. Descripción
Integrar un motor de Inteligencia Artificial (como OpenAI GPT-4o-mini o Gemini Flash) que analice los datos del usuario (edad, peso, altura, género, objetivo) ingresados en el `HomeScreen` y genere un plan de entrenamiento y nutrición dinámico y altamente personalizado, en lugar de mostrar opciones estáticas.

### 2.2. Justificación de Usuario (UX)
Actualmente, las pantallas `DietScreen` y `ExerciseScreen` muestran tarjetas estáticas genéricas ("Dieta cetogénica", "Ejercicios de fuerza"). Los usuarios de aplicaciones de fitness buscan **hiper-personalización**. Recibir un plan adaptado exactamente a su IMC y objetivos aumenta drásticamente el valor percibido de la aplicación y la probabilidad de conversión a un modelo premium.

### 2.3. Justificación Técnica
- **Viabilidad:** La integración de APIs de LLM es directa mediante Retrofit.
- **Arquitectura:** Requiere implementar un backend intermediario (BFF - Backend for Frontend) en Node.js o Python que gestione las claves de API de IA de forma segura, evitando exponerlas en el cliente Android.
- **Monetización:** Esta característica es el candidato ideal para un modelo de suscripción (ej. "GymFix Pro: Tu entrenador personal con IA").

---

## 3. Funcionalidad 2: Seguimiento de Progreso Visual y Biométrico (Tracker)

### 3.1. Descripción
Implementar un módulo de seguimiento donde el usuario pueda registrar su peso diario, medidas corporales y subir fotos de progreso ("Before & After"). Los datos se visualizarán mediante gráficos interactivos de líneas y barras.

### 3.2. Justificación de Usuario (UX)
La motivación en el fitness proviene de ver resultados tangibles. Un tracker visual permite a los usuarios celebrar pequeñas victorias y mantener la consistencia a largo plazo. La comparación fotográfica es una de las herramientas de retención más poderosas en la industria del fitness.

### 3.3. Justificación Técnica
- **Viabilidad:** Utilización de librerías de gráficos nativas para Compose como `Vico` o `MPAndroidChart` (mediante AndroidView).
- **Persistencia:** Requiere implementar una base de datos local robusta (Room Database) para almacenar el historial biométrico y sincronizarlo con el backend (PHP/MySQL) para respaldo en la nube.
- **Multimedia:** Implica integrar el selector de imágenes de Android (Photo Picker) y la cámara, gestionando eficientemente el almacenamiento local y la subida de imágenes al servidor mediante `MultipartBody` en Retrofit.

---

## 4. Funcionalidad 3: Temporizador de Descanso y Modo "Workout Activo"

### 4.1. Descripción
Crear un modo inmersivo de "Entrenamiento Activo" que guíe al usuario serie por serie. Incluirá un temporizador de descanso integrado que notifique al usuario (mediante vibración o sonido) cuando sea el momento de la siguiente serie, funcionando incluso si la aplicación está en segundo plano.

### 4.2. Justificación de Usuario (UX)
Durante el entrenamiento, los usuarios no quieren interactuar constantemente con su teléfono. Un temporizador automático que gestione los descansos entre series reduce la fricción cognitiva y mantiene el ritmo del entrenamiento, convirtiendo a GymFix en una herramienta indispensable *durante* la sesión de gimnasio, no solo antes o después.

### 4.3. Justificación Técnica
- **Viabilidad:** Requiere el uso de **Foreground Services** y **WorkManager** en Android para mantener el temporizador activo y preciso cuando la aplicación pasa a segundo plano o la pantalla se apaga.
- **Notificaciones:** Implica la creación de canales de notificación (Notification Channels) de alta prioridad para alertar al usuario.
- **Arquitectura:** Exige un manejo cuidadoso del estado global del entrenamiento (ej. usando `StateFlow` en un Singleton inyectado) para que la UI se sincronice perfectamente con el servicio en segundo plano al reabrir la app.

---

## 5. Fases de Implementación Sugeridas

1.  **Fase 1 (Estabilización Arquitectónica):** Refactorización a MVVM, implementación de Hilt para inyección de dependencias y asegurar la capa de red (HTTPS).
2.  **Fase 2 (Core Value):** Desarrollo del modo "Workout Activo" con temporizador en segundo plano (Funcionalidad 3).
3.  **Fase 3 (Retención):** Implementación del Tracker biométrico y gráficos de progreso con Room Database (Funcionalidad 2).
4.  **Fase 4 (Monetización):** Integración del motor de IA para rutinas hiper-personalizadas y lanzamiento de suscripciones premium (Funcionalidad 1).
