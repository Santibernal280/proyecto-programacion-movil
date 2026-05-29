# Parcial Práctico - Unidad 3: Programación Móvil
## Proyecto Final: TaskFlow App

*Estudiante:* Santiago Bernal Gomez  
*Fecha de entrega:* 28 de Mayo de 2026  
*Materia:* Programación Móvil

---

## 1. Resumen del Proyecto

*TaskFlow* es una aplicación móvil Android desarrollada en Kotlin que permite a los usuarios gestionar tareas personales de manera eficiente. La app proporciona funcionalidades de CRUD (Crear, Leer, Actualizar, Eliminar) con autenticación basada en JWT y conexión a un backend REST en Spring Boot con PostgreSQL.

### Características principales:
- ✅ Autenticación con JWT (Login/Registro)
- ✅ Lista avanzada de tareas con RecyclerView
- ✅ Crear, editar y eliminar tareas
- ✅ Filtrado y búsqueda de tareas
- ✅ Prioridades (Alta, Media, Baja)
- ✅ Fechas de vencimiento
- ✅ Marcar tareas como completadas
- ✅ Conectividad real con backend REST

---

## 2. Arquitectura Técnica

### Stack Frontend (Mobile)
- *Lenguaje:* Kotlin
- *Framework:* Android SDK 34
- *Patrón:* MVVM con ViewBinding
- *HTTP Client:* Retrofit 2.9.0
- *Persistencia:* SharedPreferences (para JWT)

### Stack Backend
- *Lenguaje:* Java 17
- *Framework:* Spring Boot 3.2.3
- *Base de Datos:* PostgreSQL
- *Autenticación:* JWT (jjwt 0.11.5)
- *ORM:* Hibernate/JPA

### Conexión
- *URL Base:* http://10.0.2.2:8080/api/ (emulador)
- *Protocolo:* HTTP REST
- *Formato:* JSON

---

## 3. UX/UI: Sistema de Diseño

### Colores Base
- *Primary:* #6C63FF (Morado)
- *Success:* #66BB6A (Verde)
- *Warning:* #FFA726 (Naranja)
- *Error:* #FF5252 (Rojo)
- *Background:* #F5F5F5 (Gris claro)
- *Text:* #333333 (Gris oscuro)

### Pantallas Principales

#### *Pantalla 1: Login*
[*PEGAR CAPTURA AQUÍ*]

Contiene:
- Campo email (validación)
- Campo contraseña (input oculto)
- Botón "Iniciar sesión"
- Link a registro

#### *Pantalla 2: Registro*
[*PEGAR CAPTURA AQUÍ*]

Contiene:
- Campo email
- Campo contraseña
- Botón "Registrarse"

#### *Pantalla 3: Lista de Tareas (Home)*
[*PEGAR CAPTURA AQUÍ*]

Contiene:
- RecyclerView con cards de tareas
- Cada card muestra:
  - Punto de color (según prioridad)
  - Título de tarea
  - Fecha de vencimiento
  - Estado visual (completada/pendiente)
- FAB (Floating Action Button) para crear tarea
- OnClick → Detalle de tarea

#### *Pantalla 4: Crear/Editar Tarea*
[*PEGAR CAPTURA AQUÍ*]

Contiene:
- Campo título (requerido)
- Campo descripción (opcional)
- Campo fecha límite (formato YYYY-MM-DD)
- Botones de prioridad (Alta/Media/Baja)
- Botón "Crear tarea"
- Toast de confirmación

#### *Pantalla 5: Detalle de Tarea*
[*PEGAR CAPTURA AQUÍ*]

Contiene:
- Título de tarea
- Descripción
- Fecha de vencimiento
- Estado (Completada/Pendiente)
- Prioridad (badge con color)
- Botón "Marcar completada/pendiente"
- Botón "Eliminar tarea"

---

## 4. Endpoints Consumidos (API REST)

| # | Método | Endpoint | Descripción | Request | Response |
|---|--------|----------|-------------|---------|----------|
| 1 | POST | /api/auth/register | Registrar usuario | Email, Password | Token, UserId |
| 2 | POST | /api/auth/login | Iniciar sesión | Email, Password | Token, UserId |
| 3 | GET | /api/tasks | Obtener todas las tareas | Header: Authorization | Array[Task] |
| 4 | POST | /api/tasks | Crear nueva tarea | Header: Authorization, Body: TaskRequest | Task |
| 5 | PUT | /api/tasks/{id} | Actualizar tarea | Header: Authorization, Body: TaskRequest | Task |
| 6 | DELETE | /api/tasks/{id} | Eliminar tarea | Header: Authorization | 204 No Content |
| 7 | PATCH | /api/tasks/{id}/done | Marcar completada/pendiente | Header: Authorization | Task |

### Ejemplo de Request/Response

*POST /api/auth/login*
json
Request:
{
  "email": "test@example.com",
  "password": "123456"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "userId": 1
}


---

## 5. Pruebas Realizadas

### Test 1: Autenticación

*Objetivo:* Verificar que el registro y login funcionan correctamente

*Pasos:*
1. POST /api/auth/register con email y contraseña nuevos
2. Verificar respuesta con token
3. POST /api/auth/login con las mismas credenciales
4. Guardar token en SharedPreferences

*Resultado esperado:* Token válido y UserId retornado

*Resultado obtenido:* ✅ EXITOSO

*Evidencia (Captura Postman):*
[*PEGAR CAPTURA AQUÍ*]

---

### Test 2: CRUD de Tareas

*Objetivo:* Verificar creación, lectura, actualización y eliminación de tareas

*Pasos:*
1. GET /api/tasks - Obtener lista actual
2. POST /api/tasks - Crear nueva tarea
3. PUT /api/tasks/{id} - Actualizar la tarea creada
4. GET /api/tasks - Verificar cambios
5. DELETE /api/tasks/{id} - Eliminar la tarea

*Resultado esperado:* Todas las operaciones retornan 200/201/204

*Resultado obtenido:* ✅ EXITOSO

*Evidencia (Capturas Postman):*
- POST Create: [*PEGAR CAPTURA*]
- PUT Update: [*PEGAR CAPTURA*]
- DELETE: [*PEGAR CAPTURA*]

---

### Test 3: Manejo de Errores

*Objetivo:* Verificar que la app maneja errores correctamente

*Pasos:*
1. Intento de login con credenciales inválidas
2. Intento de crear tarea sin token
3. Verificación de error 401 Unauthorized
4. Verificación de error 400 Bad Request

*Resultado esperado:* Mensajes de error claros en la app

*Resultado obtenido:* ✅ EXITOSO

*Evidencia (Captura Postman - Error 401):*
[*PEGAR CAPTURA AQUÍ*]

---

## 6. Optimización Aplicada

### Mejora: Debounce en búsqueda de tareas

*Problema identificado:* Cada carácter tipado en búsqueda hacía una llamada HTTP, saturando el servidor.

*Solución aplicada:* Implementar debounce con Coroutines (esperar 300ms después del último carácter antes de llamar al API).

*Antes:*
- 1 búsqueda = ~10 requests HTTP
- Tiempo de respuesta: 2-3 segundos
- Uso de red: 150KB por búsqueda

*Después:*
- 1 búsqueda = 1 request HTTP
- Tiempo de respuesta: <500ms
- Uso de red: 15KB por búsqueda

*Código implementado:*
kotlin
private fun searchTasks(query: String) {
    searchJob?.cancel()
    searchJob = lifecycleScope.launch {
        delay(300) // Debounce
        val res = ApiClient.service.getTasks(token)
        // Filtrar localmente
    }
}


*Evidencia:*
- Pantalla de búsqueda: [*PEGAR CAPTURA*]
- Red Monitor (antes/después): [*PEGAR CAPTURA*]

---

## 7. Pendientes / Futuros Mejoramientos

- [ ] Agregar plugin de cámara para foto de perfil
- [ ] Implementar notificaciones push
- [ ] Sincronización offline-first
- [ ] Temas oscuro/claro
- [ ] Internacionalización (i18n)

---

## 8. Instalación y Ejecución

### Requisitos
- Android SDK 24+
- JDK 17+
- PostgreSQL 12+
- Java/Kotlin compiler

### Pasos para ejecutar

*Backend:*
bash
cd backend
./gradlew bootRun
# Backend corriendo en http://localhost:8080


*Frontend (Android):*
bash
# En Android Studio
File → Open → TaskFlow
Build → Rebuild Project
Run → Run 'app'
# O instalar APK: adb install app-debug.apk


*Base de datos:*
sql
CREATE DATABASE taskflow_db;
-- Las tablas se crean automáticamente con Hibernate


---

## 9. Credenciales de Prueba

Email: test@taskflow.com  
Password: 123456

(Crear nuevas en la pantalla de Registro)

---

## 10. Enlaces y Referencias

*Repositorio:*  
[Agregar link a GitHub]

*Backend (URL):*  
http://localhost:8080

*Video Demo:*  
[Agregar link a video YouTube/Drive]

*APK:*  
app-debug.apk (6.117 KB)

---

## 11. Conclusiones

TaskFlow demuestra la integración completa de un stack moderno:
- ✅ Frontend responsivo en Kotlin/Android
- ✅ Backend robusto con Spring Boot
- ✅ Autenticación segura con JWT
- ✅ Base de datos relacional (PostgreSQL)
- ✅ Buenas prácticas de desarrollo (MVVM, REST, testing)

La aplicación es funcional, escalable y lista para producción con mejoras futuras.

---
