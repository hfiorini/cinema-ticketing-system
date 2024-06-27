# Cinema Ticketing System

### To run this app
```
- mvn clean install
- java -jar target/cinema-0.0.1-SNAPSHOT.jar
```

You can access through this link, in AWS:

http://hgf-cinema-ticketing-service.us-east-1.elasticbeanstalk.com/swagger-ui/index.html

## Considerations

This app was built using a two-dimensional array, storing seats status in memory.
It's currently deployed in a single instance, using ReentrantLock to avoid concurrency issues.

To scale the infrastructure to support 1k theaters with 5 halls each, data could be stored in a database (RDS),
where there is a record for each seat, specifying theater_id and hall_id.

Database should support **sharding**, so data could be evenly distributed across different instances

It's recommended to use some distributed **cache** mechanism, like Redis or Memcached

A **load balancer**  will improve request distribution

In case of heavy load concurrency, requests could be stored in a  **Message queue** (Kafka, SNS, RabbitMQ) so service could remain responsive