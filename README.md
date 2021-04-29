# Úloha

Vytvorte v projekte:

- REST rozhranie kde sa neregistrovaný používateľ môže registrovať, pričom mu budú pridelené   READ_ONLY práva. 

- Rozhranie pomocou ktorého prihlásený administrátor môže:

  - upraviť heslo a práva konkrétnemu užívateľovi,

  - zobraziť všetkých užívateľov zaregistrovaných v systéme,

  - vymazať užívateľa.


# DemoProject

How to start the DemoProject application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/demo_project-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8081/healthcheck`