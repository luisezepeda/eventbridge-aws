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

Ejemplo `application.yml` (mínimo):

```yaml
eventbridge:
	region: us-east-2
	source-bus: visit-request-bus
	destination-bus: visit-bus
	rule-name: rule-events
	create-rule: false
	event-pattern: |
		{ "source": ["my-global-custom-app"], "detail-type": ["detail-type"] }

aws:
	credentials:
		access-key: ${AWS_ACCESS_KEY_ID:}
		secret-key: ${AWS_SECRET_ACCESS_KEY:}
		session-token: ${AWS_SESSION_TOKEN:}
```

Variables de entorno (PowerShell):

```powershell
$Env:AWS_ACCESS_KEY_ID = '...'
$Env:AWS_SECRET_ACCESS_KEY = '...'
# $Env:AWS_SESSION_TOKEN = '...'  # si usas credenciales temporales
```

Ejemplo de política IAM mínima (para crear/actualizar regla y targets):

```json
{
	"Version": "2012-10-17",
	"Statement": [
		{
			"Sid": "AllowManageRule",
			"Effect": "Allow",
			"Action": [
				"events:PutRule",
				"events:PutTargets",
				"events:DescribeRule"
			],
			"Resource": "arn:aws:events:us-east-2:<ACCOUNT_ID>:rule/visit-request-bus/rule-events"
		}
	]
}
```

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
