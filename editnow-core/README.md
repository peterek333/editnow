Deploy editnow-core na PCF
1. Jeśli są zmiany na bazie to zmienić<br/>
spring.jpa.hibernate.ddl-auto=validate
<br/>
na<br/>
spring.jpa.hibernate.ddl-auto=create
<br/>
cofnąć zmiane po wgraniu, bo można przekroczyc limit zapytań <3600
2. Z poziomu projektu editnow-core<br/>
cf push


Budowanie z profilem<br/>
clean install -Plocal -DprofileIdEnabled=true


Wlaczanie z profilem<br/>
java -r -Dspring.profiles.active=local backend-0.0.1-SNAPSHOT.jar
