# CQRS Patron de diseno de arquitectura

## Principios
Query - Retorna datos para la capa de presentacion usando DTO
Command - Operaciones que no retornan datos o no datos necesarios

# Event Sourcing
Patron que define que todos los cambios en alguna entidad u objetos debe ser almacenado en una secuencia inmutable de eventos, la cual es almacenada
en un EventStore, el cual guarda todos esos eventos dentro de una base de datos propia.

## Beneficios
1. La BD entrega un log completo con cada uno de los cambios registrados
2. El estado del objeto puede ser recreado
3. Mejora el procesamiento de escritura
4. NO puedo manipular eventos ya registrados. SOlo se pueden agregar.

# Red virtual Docker
- docker ps
- docker network ls
- docker network create --attachable -d bridge bankingNet

# Zookeper y Kafka
Zookeper es el administrador de servicios de Kafka, Kafka es el middleware

# Mediator Patter
Es un patron de diseno de tipo comportamiento, promueve un acoplamiento de objetos y simplifica la comunicacion entre los objetos
