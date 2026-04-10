# Sprint 2 - ASR1 Latencia (Spring Boot + ALB + RDS Proxy)

## 1) Analisis rapido de tu diagrama y tabla

### Lo que esta bien alineado
- Flujo principal correcto para medir latencia end-to-end: `Usuario -> UI -> ALB -> Backend -> RDS Proxy -> PostgreSQL`.
- El ASR objetivo esta claro: `p95 < 3s`.
- El uso de `RDS Proxy` aplica la tactica `Reduce Overhead` al evitar apertura de conexion nueva por request.
- `Cliente-Servidor` y `Arquitectura en capas` estan bien justificados para medir y optimizar latencia.

### Ajustes recomendados para que el experimento sea defendible
- En la medicion, incluye dos escenarios:
1. `Sin Proxy`: app conectada directo a endpoint RDS.
2. `Con Proxy`: app conectada a endpoint RDS Proxy.
- Reporta al menos estos campos por corrida: `Threads`, `Ramp-Up`, `Average`, `p95`, `Throughput`, `Error %`.
- Mantener criterio de exito del ASR: `p95 < 3000 ms` en al menos `95%` de peticiones.

## 2) Cambios ya aplicados en tu backend

Se implemento el backend base del tutorial dentro de tu proyecto actual, sin cambiar tecnologias.

### Estructura agregada
- `src/main/java/com/proyecto/backend/account/Account.java`
- `src/main/java/com/proyecto/backend/account/AccountProvider.java`
- `src/main/java/com/proyecto/backend/account/AccountRepository.java`
- `src/main/java/com/proyecto/backend/account/AccountService.java`
- `src/main/java/com/proyecto/backend/account/AccountController.java`
- `src/main/java/com/proyecto/backend/account/dto/AccountRegisterRequest.java`
- `src/main/java/com/proyecto/backend/account/dto/AccountRegisterResponse.java`
- `src/main/java/com/proyecto/backend/api/ApiExceptionHandler.java`

### Configuracion aplicada
- En `pom.xml`:
1. Se agrego `spring-boot-starter-actuator` para `GET /actuator/health` (health check del ALB).
- En `src/main/resources/application.properties`:
1. Datasource parametrizado para endpoint de RDS Proxy.
2. Ajustes HikariCP (`maximum-pool-size`, `minimum-idle`, `connection-timeout`, etc.).
3. `management.endpoints.web.exposure.include=health`.

### Endpoints disponibles
- `POST /api/accounts/register`
- `GET /api/accounts`
- `GET /actuator/health`

## 3) Donde va cada cosa (carpeta / entidad)

### Capa API
- `AccountController` en `.../account/` expone endpoints REST.
- `ApiExceptionHandler` en `.../api/` centraliza errores de validacion.

### Capa de negocio
- `AccountService` en `.../account/` aplica validaciones y orquesta guardado.

### Capa de persistencia
- `AccountRepository` en `.../account/` (Spring Data JPA).
- `Account` en `.../account/` mapea tabla `accounts` en PostgreSQL.

### Configuracion
- `application.properties` en `src/main/resources` define conexion DB y tuning del pool.

## 4) Lo que debes hacer ahora (paso a paso)

### Variables de entorno para levantar con Proxy
En cada EC2 donde corra el backend define:

```bash
export APP_DATASOURCE_HOST=bite-proxy.proxy-xxxx.us-east-1.rds.amazonaws.com
export APP_DATASOURCE_PORT=5432
export APP_DATASOURCE_DATABASE=bite_accounts
export SPRING_DATASOURCE_USERNAME=bite_user
export SPRING_DATASOURCE_PASSWORD='tu_password'
```

Para escenario `sin proxy`, cambia solo `APP_DATASOURCE_HOST` al endpoint directo de RDS.

### Build y arranque
```bash
mvn clean package -DskipTests
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

### Pruebas rapidas
```bash
curl http://localhost:8080/actuator/health
curl -X POST http://localhost:8080/api/accounts/register \
  -H 'Content-Type: application/json' \
  -d '{"name":"EmpresaTest","provider":"AWS"}'
curl http://localhost:8080/api/accounts
```

### Configuracion AWS (segun tu guia PDF)
1. Crear RDS PostgreSQL.
2. Crear Secret en Secrets Manager para credenciales DB.
3. Crear RDS Proxy asociado a esa RDS.
4. Levantar 2 EC2 con la app Spring Boot.
5. Crear Target Group HTTP:8080 con health check `/actuator/health`.
6. Crear ALB HTTP:80 apuntando al Target Group.
7. Ejecutar JMeter contra `http://<DNS_ALB>/api/accounts/register`.

## 5) Formato sugerido de tabla de resultados (entrega)

| Escenario | Threads | Ramp-Up | Avg (ms) | p95 (ms) | Throughput (req/s) | Error % | Cumple ASR1 |
|---|---:|---:|---:|---:|---:|---:|---|
| Sin Proxy | 10 | 10 |  |  |  |  |  |
| Sin Proxy | 50 | 10 |  |  |  |  |  |
| Sin Proxy | 100 | 10 |  |  |  |  |  |
| Con Proxy | 10 | 10 |  |  |  |  |  |
| Con Proxy | 50 | 10 |  |  |  |  |  |
| Con Proxy | 100 | 10 |  |  |  |  |  |
| Con Proxy | 200 | 10 |  |  |  |  |  |
| Con Proxy | 400 | 10 |  |  |  |  |  |
| Con Proxy | 600 | 10 |  |  |  |  |  |

Regla: `Cumple ASR1 = SI` solo si `p95 < 3000 ms` y `Error % <= 10`.
