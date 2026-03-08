# Spring Boot Item API - DevOps Harjoitustyö

Tervetuloa projektiin! Tämä on minimaalinen Spring Boot REST API, jota käytetään DevOps-käytänteiden, CI/CD-putkien ja Docker-kontituksen demonstrointiin.

## Arkkitehtuurin lyhyt kuvaus
Projekti koostuu Java 17 & Spring Boot 3 -pohjaisesta taustajärjestelmästä, joka keskustelee MariaDB-tietokannan kanssa. Sovellus ja tietokanta on kontitettu Dockerilla, ja ne ajetaan yhdessä `docker-compose.yml` -tiedoston avulla.

## Konfiguraatioprofiilit (Spring Profiles)
Sovellus hyödyntää kolmea eri konfiguraatioprofiilia, jotka sijaitsevat `src/main/resources/` -kansiossa:

* **`dev` (application-dev.yml):** Paikalliseen kehitykseen. Yhdistää `localhost:3306` portissa pyörivään tietokantaan. Aktivoituu oletuksena.
* **`test` (application-test.yml):** Automaattisia testejä varten. Käyttää nopeaa H2-in-memory -tietokantaa. Aktivoituu GitHub Actionsissa ja testeissä (`@ActiveProfiles("test")`).
* **`prod` (application-prod.yml):** Tuotantoympäristöön (cPouta). Hakee tietokannan osoitteet ja salasanat turvallisesti ympäristömuuttujista.

## Ohjeet paikalliseen kehitykseen (Uuden kehittäjän opas)

Pääset alkuun projektin kanssa lokaalisti seuraavilla askelilla. Tarvitset asennettuna **Docker Desktopin**.

1. **Kloonaa repositorio:**
   `git clone https://github.com/SINUN_TUNNUKSESTI/projekti.git`
2. **Käynnistä ympäristö:**
   Avaa terminaali projektin juuressa ja aja komento:
   `docker compose up -d`
3. **Mitä tapahtuu?**
   Docker Compose käynnistää kaksi palvelua: `db` (MariaDB) ja `app` (Spring Boot API). API vastaa nyt lokaalisti osoitteessa `http://localhost:8080`.
   Voit sammuttaa ympäristön komennolla `docker compose down`.

## Testauksen kuvaus
Projektissa on CI-putkeen integroitu testaus. Testit ajetaan aina puhtaassa tilassa käyttäen erillistä `test`-profiilia ja H2-tietokantaa. Testiprofiilin eriyttäminen on tärkeää, jotta testiajot eivät sotke paikallista kehitystietokantaa tai yritä yhdistää tuotantokantaan CI-palvelimelta.

## CI/CD-putki (GitHub Actions)
Koodin pushaaminen `main`-haaraan laukaisee `.github/workflows/deploy.yml` -putken:
1. Hakee koodin.
2. Asettaa JDK 17 ja ajaa yksikkötestit komennolla `./mvnw test` (käyttäen `SPRING_PROFILES_ACTIVE=test`).
3. Rakentaa Docker-imagen.
4. Kirjautuu Docker Hubiin ja julkaisee imagen container registryyn.
5. Ottaa SSH-yhteyden tuotantopalvelimelle ja suorittaa deployn.

## Tuotantoympäristö (CSC cPouta)
Tuotantoympäristönä toimii CSC:n cPouta-virtuaalikone. Palvelimella pyörii Docker ja Docker Compose. GitHub Actions suorittaa palvelimella komennot `docker compose pull` (hakee uusimman version sovelluksesta Docker Hubista) ja `docker compose up -d` (käynnistää kontit uudelleen). 
Tuotannossa API palvelee suoraan palvelimen IP-osoitteesta portissa 8080.
