# Getting Started
Spring Boot Coffe Machine demo application. 

### Start application
`
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.mail.host=host --spring.mail.port=port --spring.mail.username=username --spring.mail.password=password --spring.mail.properties.mail.smtp.auth=true --spring.mail.properties.mail.smtp.startttls.enable=true --admin.username=noreply@localhost --admin.password=latte --mail.sender=admin@localhost"
`

* admin.username is application initial admin username - default value admin@localhost
* admin.password is application initial admin password - default value password

### Url
* http://localhost:8080/h2-console database console, username: sa, password: password


### REST documentation
https://documenter.getpostman.com/view/14843030/Tz5jf1JG
