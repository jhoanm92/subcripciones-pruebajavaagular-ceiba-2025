# Plataforma de gestión de Fondos (FPV / FIC) – BTG Pactual

Aplicación web (API REST) que permite a un cliente:
1) **Suscribirse** a un fondo (apertura),
2) **Cancelar** una suscripción a un fondo (salida),
3) **Consultar historial** de transacciones (aperturas/cancelaciones),
4) **Notificar** por email o SMS según la preferencia del usuario.

> **Reglas de negocio clave**
> - Saldo inicial del cliente: **COP $500.000**.
> - Cada transacción tiene un **ID único** y **fecha**.
> - Cada fondo define un **monto mínimo de vinculación**.
> - Al cancelar, el **monto de vinculación retorna** al cliente.
> - Si no hay saldo suficiente:
>   “**No tiene saldo disponible para vincularse al fondo <Nombre del fondo>**”.

## 1. Tecnologías y justificación

- **Backend:** Spring Boot 3, Spring Web, Spring Data JPA
- **Persistencia:** PostgreSQL (o H2 para pruebas locales)
- **DTO & Mapeo:** MapStruct + Lombok
- **Documentación:** springdoc-openapi (Swagger UI)

## 2. Arquitectura (resumen)

- **Capa API:** Controllers REST (validación, manejo de errores).
- **Capa Servicio:** Reglas de negocio (aperturas/cancelaciones, validación de saldo, historial, notificaciones).
- **Capa Persistencia:** Repositorios JPA.
- **Dominio:**
  - `Cliente`
  - `Fondo`
  - `EstadoTransaccion`
  - `Transaccion`
  - `Suscripcion` (tabla intermedia con **clave compuesta** `cliente_id` + `fondo_id`).

## 4. Endpoints principales

- **Swagger UI:** `http://localhost:8090/api/swagger-ui/index.html#/`
- **OpenAPI JSON:** `http://localhost:8090/api/v3/api-docs`
- **OpenAPI YAML:** `http://localhost:8090/api/v3/api-docs.yaml`

## 6. Ejecución local

Requisitos: JDK 17+, Maven 3.8+, PostgreSQL o H2

```bash
mvn clean install
mvn clean spring-boot:run
```

## 7. Pruebas

- Unitarias (JUnit 5, Mockito)
- Integración (Spring Boot Test + MockMvc)

Colección Postman incluida: **BTG.postman_collection.json**
