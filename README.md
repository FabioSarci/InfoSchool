# InfoSchool

## Obiettivo
InfoSchool è un progetto backend sviluppato per gestire corsi, utenti, ruoli e altre funzionalità correlate a un sistema scolastico. L'obiettivo principale è fornire un'API robusta e sicura per la gestione delle informazioni scolastiche.

## Tecnologie
- **Linguaggio**: Java 21
- **Framework**: Spring Boot
- **Database**: MySQL
- **Librerie principali**:
  - Spring Security
  - Spring Data JPA
  - Apache POI (per l'esportazione in Excel)
  - iText (per la generazione di PDF)
- **Strumenti di documentazione**: OpenAPI/Swagger

## Architettura
Il progetto segue un'architettura a livelli:
1. **Controller**: Gestisce le richieste HTTP e restituisce le risposte.
2. **Service**: Contiene la logica di business.
3. **Repository**: Interagisce con il database utilizzando Spring Data JPA.
4. **Model**: Definisce le entità del dominio.
5. **DTO**: Utilizzato per trasferire dati tra i livelli.

## Sicurezza e autenticazione
- **Autenticazione**: Implementata tramite Spring Security con JWT (JSON Web Token).
- **Autorizzazione**: Basata sui ruoli (`ROLE_ADMIN`, `ROLE_TEACHER`, `ROLE_USER`).
- **Crittografia**: Le password sono crittografate utilizzando BCrypt.

## Funzionalità principali
- Gestione di utenti, ruoli e corsi.
- Registrazione di utenti a corsi.
- Esportazione di dati in formato CSV ed Excel.
- Generazione di PDF con profili utente.
- API sicure con autenticazione e autorizzazione.

## Funzionalità aggiuntive
- Gestione delle materie insegnate e dei progetti associati ai corsi.
- Endpoint per la gestione delle mail interne.
- Documentazione API tramite Swagger UI.

## Testing
- **Unit Testing**: Implementato per i servizi principali utilizzando JUnit.
- **Integration Testing**: Test degli endpoint API con Postman.
- **Mocking**: Utilizzato Mockito per simulare repository e servizi.

## Avvio del progetto
1. Clona il repository:
   ```bash
   git clone https://github.com/tuo-repository/infoschool.git
   ```
2. Configura il database in `application.properties`.
3. Avvia l'applicazione:
   ```bash
   mvn spring-boot:run
   ```
4. Accedi alla documentazione API:
   ```
   http://localhost:8080/swagger-ui/index.html
   ```

## Dataset iniziale
Il progetto include un dataset iniziale caricato tramite la classe `DataLoader`. Questo dataset comprende:
- **Ruoli predefiniti**: `ROLE_ADMIN`, `ROLE_TEACHER`, `ROLE_USER`.
- **Utenti di esempio**: Admin, insegnanti e studenti.
- **Corsi**: Esempi di corsi con materie e progetti associati.
- **Progetti**: Collegati ai corsi per simulare esami.

## Design Pattern utilizzati
- **Singleton**: Utilizzato per la configurazione di componenti Spring.
- **DTO (Data Transfer Object)**: Per trasferire dati tra i livelli.
- **Repository Pattern**: Per l'accesso ai dati tramite Spring Data JPA.
- **Factory**: Per la creazione di oggetti complessi (es. mapper).

## Note finali
InfoSchool è un progetto pensato per essere estendibile e scalabile. Per qualsiasi domanda o contributo, contattaci o apri una issue nel repository.