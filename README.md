# MDP EventBridge Service

Microservicio para recibir eventos de AWS EventBridge y reenviarlos a otro bus.

## Requisitos
- Java 17
- Maven 3.9+
- Credenciales AWS (variables de entorno o `application.yml`)

## Configuración
Editar `src/main/resources/application.yml`:
- `eventbridge.region`, `source-bus`, `destination-bus`
- `eventbridge.event-pattern` (JSON)
- `aws.credentials.*` o usar provider chain por defecto
- `eventbridge.create-rule=true` y `destination-bus-arn` si deseas que el servicio cree/actualice la regla y asigne el target al bus de destino.

## Ejecutar
```bash
mvn spring-boot:run
```

## Endpoint
- `POST /events`: recibe un evento con la estructura esperada y lo publica en `eventbridge.destination-bus`.

## Notas
- Para que EventBridge envíe eventos a este servicio, configura un API Destination y una Connection en EventBridge apuntando a `POST /events`.
- Alternativamente, habilita `create-rule` y usa `destination-bus-arn` para enrutar directamente desde `source-bus` a `destination-bus` sin pasar por HTTP.
- Logs con `@Slf4j`.
