# Backend Proyecto Cloud Cost Platform

Backend desarrollado con **Spring Boot**, **PostgreSQL** y **Auth0**, desplegable en **AWS** usando **Terraform**.

La aplicación permite:

- autenticación con Auth0
- registro y consulta de cuentas cloud
- validación inicial de credenciales AWS
- almacenamiento de costos mensuales
- consulta de reportes de costo
- despliegue en AWS con:
  - Application Load Balancer
  - EC2
  - PostgreSQL RDS
  - RDS Proxy
  - S3 para artefactos

---

## 1. Tecnologías usadas

- Java 21
- Spring Boot
- Spring Security
- OAuth2 Resource Server
- PostgreSQL
- Maven
- Terraform
- AWS
- Auth0

---

## 2. Estructura del proyecto

```text
backend/
├── src/
│   ├── main/
│   │   ├── java/com/proyecto/backend/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
├── README.md
└── terraform/
    ├── main.tf
    ├── variables.tf
    ├── outputs.tf
    ├── user_data.sh.tftpl
    └── terraform.tfvars.example