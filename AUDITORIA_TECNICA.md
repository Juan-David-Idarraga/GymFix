# Auditoría Técnica Integral — GymFix

**Fecha:** Abril 2026
**Proyecto:** GymFix — Aplicación Móvil Android (Jetpack Compose)
**Auditor:** Juan Idarraga — Senior Technical Lead & Software Auditor
**Referencia de Estilo:** Fundación Reiki Intranet (Technology of Jota)

---

## 1. Resumen Ejecutivo

Se ha realizado una revisión exhaustiva del repositorio `GymFix`, una aplicación móvil nativa para Android desarrollada con **Kotlin** y **Jetpack Compose**. El proyecto representa una solución para la gestión de rutinas de gimnasio, dietas y perfiles de usuario, conectada a un backend PHP mediante Retrofit.

El análisis cubre la totalidad del código fuente disponible en la rama `main`, incluyendo la configuración de Gradle, el manifiesto de Android, la capa de red (API) y las pantallas de interfaz de usuario.

**Veredicto general:** El proyecto demuestra un buen entendimiento de los paradigmas modernos de desarrollo Android (UI declarativa con Compose, corrutinas, Retrofit). Sin embargo, presenta **deficiencias arquitectónicas significativas** en la gestión de estado, inyección de dependencias y seguridad de red que deben abordarse antes de considerar un despliegue en producción.

---

## 2. Inventario del Proyecto

| Elemento | Detalle |
| :--- | :--- |
| **Framework UI** | Jetpack Compose (BOM 2024.09.00) |
| **Lenguaje** | Kotlin 2.0.21 |
| **Plataforma objetivo** | Android (Min SDK 24, Target SDK 34) |
| **Dependencias principales** | `retrofit2:2.11.0`, `okhttp3:4.12.0`, `navigation-compose:2.7.5`, `kotlinx-coroutines` |
| **Arquitectura actual** | Monolítica (UI y lógica de red acopladas) |
| **Backend** | API REST en PHP (localhost/smartFix) |
| **Commits totales** | 2 commits |

---

## 3. Hallazgos de Calidad de Código y Arquitectura

### 3.1. Acoplamiento Fuerte entre UI y Capa de Red (Crítico)

**Descripción:** Las pantallas de Jetpack Compose (ej. `LoginScreen.kt`) contienen lógica de negocio y llamadas directas a la API de red dentro de los callbacks de los botones, utilizando `scope.launch`.

**Evidencia concreta:**

```kotlin
// LoginScreen.kt
Button(
    onClick = {
        cargando = true
        scope.launch {
            try {
                val res = ApiClient.api.login(usuario, contrasena)
                // Lógica de negocio mezclada con UI
                if (res.isSuccessful) { ... }
            } catch (e: Exception) { ... }
        }
    }
)
```

**Impacto:**
- **Testabilidad nula:** Es imposible realizar pruebas unitarias de la lógica de login sin instanciar la UI completa.
- **Mantenibilidad:** Cualquier cambio en la API requiere modificar los componentes visuales.
- **Violación de Clean Architecture:** La capa de presentación conoce detalles de implementación de la capa de datos.

**Recomendación:** Implementar el patrón **MVVM (Model-View-ViewModel)**. Extraer toda la lógica de red y gestión de estado a un `LoginViewModel` utilizando `StateFlow` o `State`, y observar estos estados desde la UI.

---

### 3.2. Ausencia de Inyección de Dependencias (Alto)

**Descripción:** El cliente de red (`ApiClient`) está implementado como un `object` (Singleton) que instancia Retrofit directamente.

**Evidencia concreta:**

```kotlin
// Api.kt
object ApiClient {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2/smartFix/")
            // ...
    }
}
```

**Impacto:** El uso de Singletons rígidos impide la sustitución de dependencias para pruebas (mocking) y dificulta la gestión del ciclo de vida de los componentes de red.

**Recomendación:** Integrar **Hilt** o **Dagger** para la inyección de dependencias. Proveer el `ApiService` a través de un módulo de red inyectable en los ViewModels.

---

### 3.3. Gestión de Estado UI Frágil (Medio)

**Descripción:** El estado de los formularios (ej. `HomeScreen.kt`, `LoginScreen.kt`) se maneja con múltiples variables `mutableStateOf` dispersas en la función Composable.

**Evidencia concreta:**

```kotlin
// HomeScreen.kt
var genero by remember { mutableStateOf("") }
var edad by remember { mutableStateOf("") }
var peso by remember { mutableStateOf("") }
var altura by remember { mutableStateOf("") }
var objetivo by remember { mutableStateOf("") }
```

**Impacto:** A medida que los formularios crecen, la función Composable se vuelve inmanejable. Además, el estado se pierde si hay un cambio de configuración (ej. rotación de pantalla) porque no se usa `rememberSaveable` ni un ViewModel.

**Recomendación:** Agrupar el estado del formulario en una `data class` (ej. `ProfileFormState`) y gestionarlo desde un ViewModel que sobreviva a los cambios de configuración.

---

## 4. Hallazgos de Seguridad

### 4.1. Tráfico de Red en Texto Plano (Cleartext Traffic) (Crítico)

**Descripción:** El proyecto está configurado explícitamente para permitir tráfico HTTP no encriptado, tanto en el manifiesto como en la configuración de seguridad de red.

**Evidencia concreta:**

```xml
<!-- AndroidManifest.xml -->
<application android:usesCleartextTraffic="true" ...>

<!-- network_security_config.xml -->
<domain-config cleartextTrafficPermitted="true">
    <domain includeSubdomains="true">10.0.2.2</domain>
</domain-config>
```

**Impacto:** Las credenciales de usuario (nombre y contraseña) se envían en texto plano a través de la red, haciéndolas vulnerables a ataques de intermediario (Man-in-the-Middle). Aunque es aceptable para desarrollo local (`10.0.2.2`), es un riesgo crítico si se despliega a producción.

**Recomendación:** Eliminar `android:usesCleartextTraffic="true"` para entornos de producción. Asegurar que el backend de producción utilice **HTTPS** con certificados SSL válidos.

---

### 4.2. Almacenamiento de Credenciales Inseguro (Alto)

**Descripción:** La API de login devuelve un objeto `UserData` tras una autenticación exitosa. Sin embargo, la aplicación no implementa ningún mecanismo seguro para almacenar la sesión del usuario (token o datos).

**Impacto:** El usuario tendría que iniciar sesión cada vez que abre la aplicación, o peor aún, si se implementa un guardado en `SharedPreferences` sin encriptar, los datos serían vulnerables en dispositivos rooteados.

**Recomendación:** Implementar **EncryptedSharedPreferences** (de la librería de seguridad de AndroidX) o **DataStore** para almacenar tokens de sesión de forma segura.

---

## 5. Hallazgos de Performance y UX

### 5.1. Bloqueo del Hilo Principal en Navegación (Medio)

**Descripción:** En `BottomNavBar.kt`, la navegación limpia el backstack de forma agresiva, lo que puede causar parpadeos visuales.

**Evidencia concreta:**

```kotlin
// BottomNavBar.kt
navController.navigate(item.route) {
    popUpTo("home") { inclusive = false }
    launchSingleTop = true
}
```

**Recomendación:** Utilizar `saveState = true` y `restoreState = true` en las opciones de navegación del Bottom Navigation para preservar el estado de las pestañas al cambiar entre ellas, mejorando significativamente la experiencia de usuario.

---

### 5.2. Imágenes Hardcodeadas y Sin Caché (Medio)

**Descripción:** Las pantallas `DietScreen` y `ExerciseScreen` utilizan recursos locales (`android.R.drawable.ic_menu_gallery`) como placeholders para imágenes.

**Recomendación:** Integrar la librería **Coil** para la carga asíncrona y el cacheo de imágenes desde URLs remotas en Jetpack Compose.

---

## 6. Matriz de Priorización de Hallazgos

| # | Hallazgo | Severidad | Esfuerzo | Prioridad |
| :--- | :--- | :---: | :---: | :---: |
| 3.1 | Acoplamiento Fuerte entre UI y Capa de Red | Crítico | Alto | P0 |
| 4.1 | Tráfico de Red en Texto Plano (Cleartext) | Crítico | Bajo | P0 |
| 3.2 | Ausencia de Inyección de Dependencias (Hilt) | Alto | Medio | P1 |
| 4.2 | Almacenamiento de Credenciales Inseguro | Alto | Medio | P1 |
| 3.3 | Gestión de Estado UI Frágil (Sin ViewModels) | Medio | Medio | P2 |
| 5.1 | Optimización de Navegación (BottomBar) | Medio | Bajo | P2 |
| 5.2 | Carga de Imágenes sin Caché (Coil) | Medio | Bajo | P3 |

---

## 7. Deuda Técnica Estimada

Basándose en la complejidad del proyecto actual, se estima la siguiente inversión para llevar la aplicación a estándares de producción empresariales:

| Categoría | Horas Estimadas |
| :--- | :---: |
| Refactorización a MVVM (ViewModels y StateFlow) | 12–16 h |
| Implementación de Inyección de Dependencias (Hilt) | 6–8 h |
| Configuración de Seguridad (HTTPS, EncryptedPrefs) | 4–6 h |
| Integración de Coil y optimización de UI | 4–6 h |
| Configuración de CI/CD y tests unitarios básicos | 8–10 h |
| **Total estimado** | **34–46 h** |

---

*Este reporte fue generado mediante análisis estático del código fuente. Se recomienda complementarlo con pruebas de integración con el backend de producción.*
