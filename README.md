# Apolo - Android

App Android nativa desarrollada como caso práctico para monitoreo de envíos en tiempo real.

---

## Tecnologías utilizadas

| Categoría | Tecnología |
|---|---|
| Lenguaje | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Arquitectura | MVVM + Clean Architecture |
| Estado | ViewModel + StateFlow |
| Navegación | Navigation Compose |
| Inyección de dependencias | Hilt |
| Red | Retrofit + OkHttp |
| Concurrencia | Coroutines |
| Persistencia local | DataStore Preferences |
| Mapas | Google Maps SDK (Maps Compose) |

---

## Funcionalidades

- **Splash** — Validación de sesión activa al iniciar la app
- **Login** — Autenticación con credenciales reales contra API REST
- **Recuperación de contraseña** — Envío de solicitud al backend
- **Persistencia de sesión** — Token guardado con DataStore; la app recuerda la sesión entre aperturas
- **Monitoreo de vehículos** — Carga de vehículos desde API y visualización en Google Maps
- **Marcadores personalizados** — Cada marcador muestra ícono del vehículo rotado según ángulo, placa y velocidad, coloreado por estado
- **Notificaciones** — Listado real desde API con estados de envío
- **Billing** — Resumen visual de facturación con datos mock
- **Perfil** — Datos del usuario y cierre de sesión
- **Bottom Navigation** — Navegación entre secciones con diseño pill moderno

---

## Credenciales de prueba

```
Usuario:    admin
Contraseña: 123456_-2026
```

---

## Arquitectura

El proyecto sigue una Clean Architecture ligera dividida en cuatro capas:

```
app/src/main/java/com/apolo/tracking/
│
├── data/               # Fuentes de datos
│   ├── local/          # DataStore (SessionManager)
│   ├── mapper/         # Conversión DTO → modelo de dominio
│   ├── remote/         # Retrofit APIs y DTOs
│   └── repository/     # Implementaciones de repositorios
│
├── domain/             # Lógica de negocio pura
│   ├── model/          # Modelos de dominio (sin anotaciones JSON)
│   ├── repository/     # Interfaces de repositorios
│   └── usecase/        # Casos de uso
│
├── presentation/       # UI con Jetpack Compose
│   ├── components/     # Componentes reutilizables
│   ├── navigation/     # NavGraph y rutas
│   └── [pantalla]/     # Screen + ViewModel + UiState por pantalla
│
└── di/                 # Módulos Hilt
```

**Reglas de dependencia:**
- `presentation` solo conoce `domain`
- `data` implementa interfaces de `domain`
- `domain` no depende de ninguna capa externa

---

## Configuración del proyecto

### Requisitos

- Android Studio Hedgehog o superior
- JDK 17
- Android SDK API 24+

### Pasos para abrir el proyecto

1. Clona o descarga el repositorio
2. Abre Android Studio → **Open** → selecciona la carpeta `Apolo`
3. Espera que Gradle sincronice las dependencias

### Configurar API Key de Google Maps

1. Obtén una API Key en [Google Cloud Console](https://console.cloud.google.com/)
2. Habilita la API **Maps SDK for Android**
3. Crea el archivo `local.properties` en la raíz del proyecto (si no existe)
4. Agrega la siguiente línea:

```properties
MAPS_API_KEY=TU_API_KEY_AQUI
```

> `local.properties` está en `.gitignore` y nunca se sube al repositorio.

---

## Ejecutar el proyecto

Conecta un dispositivo Android o inicia un emulador (API 24+), luego presiona **Run** en Android Studio, o desde terminal:

```bash
./gradlew installDebug
```

---

## Generar APK

```bash
./gradlew assembleDebug
```

El APK generado se encuentra en:

```
app/build/outputs/apk/debug/app-debug.apk
```

---

## Información de la app

| Campo | Valor |
|---|---|
| Package | `com.apolo.tracking` |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 37 |
| Version | 1.0 (build 1) |
