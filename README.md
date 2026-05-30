# SalesApp

Sistema de venta y boletas para una tienda pequena de productos comestibles.

## Componentes

- Frontend Angular con login IDaaS mediante Azure AD B2C/MSAL.
- BFF Spring Boot protegido por JWT en `/api/**`.
- Proteccion adicional tipo API Manager por header `Ocp-Apim-Subscription-Key`, configurable con `SALESAPP_API_MANAGER_REQUIRED`.
- Persistencia Oracle para productos. Los usuarios viven en el IDaaS, no en Oracle.

## Endpoints BFF

- `GET /api/productos`: lista productos activos seleccionables.
- `POST /api/ventas/generar`: recibe productos/cantidades y devuelve una boleta con subtotales y total.

## Desarrollo local

Frontend:

```bash
cd frontend
npm ci
npm run start
```

Backend:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

Variables relevantes:

```bash
SPRING_DATASOURCE_URL=jdbc:oracle:thin:@dbbooks_high?TNS_ADMIN=/app/wallet
SPRING_DATASOURCE_USERNAME=ADMIN
SPRING_DATASOURCE_PASSWORD=change-me
IDP_ISSUER_URI=https://ghdevcompany.b2clogin.com/ac1dbc9b-27a4-4004-a719-42d96af26d37/v2.0/
IDP_JWK_SET_URI=https://ghdevcompany.b2clogin.com/ghdevcompany.onmicrosoft.com/b2c_1_dn-gh/discovery/v2.0/keys
SALESAPP_API_MANAGER_REQUIRED=false
SALESAPP_API_MANAGER_SUBSCRIPTION_KEY=dev-salesapp-key
```

## Base de datos

Spring puede crear la tabla `PRODUCTOS` con `spring.jpa.hibernate.ddl-auto=update` en Oracle.
Tambien se incluyen scripts explicitos en `database/schema.sql` y `database/seed.sql`.

Para pruebas locales, el perfil `local` usa H2 en memoria y carga productos de ejemplo al iniciar.
