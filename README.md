# 🎓 InfoSchool

Backend system for school management  
Robusto, sicuro ed estensibile — sviluppato con Java 21 e Spring Boot.

## 📌 Obiettivo

InfoSchool è un progetto backend progettato per gestire corsi, utenti, ruoli e funzionalità correlate a un sistema scolastico.  
L’obiettivo è fornire un’API solida e sicura per la gestione centralizzata delle informazioni scolastiche.

## ⚙️ Tecnologie utilizzate

- **Linguaggio**: Java 21
- **Framework principale**: Spring Boot
- **Database**: MySQL
- **Librerie**:
  - Spring Security (autenticazione/autorizzazione)
  - Spring Data JPA (interazione con il database)
  - Apache POI (esportazione in Excel)
  - iText (generazione PDF)
- **Documentazione API**: OpenAPI / Swagger

## 🧱 Architettura del progetto

Il progetto adotta un'architettura a livelli, seguendo i principi della Clean Architecture:

```
Controller → Service → Repository → Model
                         ↑
                        DTO
```

**Descrizione livelli**:
- **Controller**: gestisce le richieste HTTP.
- **Service**: contiene la logica di business.
- **Repository**: accede al database con Spring Data JPA.
- **Model**: entità del dominio.
- **DTO (Data Transfer Object)**: oggetti per il passaggio dei dati tra livelli.

## 🔐 Sicurezza e autenticazione

- **Autenticazione**: JWT (JSON Web Token) tramite Spring Security
- **Autorizzazione**: basata sui ruoli:
  - ROLE_ADMIN
  - ROLE_TEACHER
  - ROLE_USER
- **Crittografia**: password sicure con algoritmo BCrypt

## ✅ Funzionalità principali

- Gestione utenti, ruoli e corsi
- Iscrizione utenti ai corsi
- Esportazione dati in CSV ed Excel
- Generazione PDF dei profili utente
- Endpoint sicuri con JWT e controllo ruoli
- Documentazione interattiva tramite Swagger UI

## ➕ Funzionalità aggiuntive

- Gestione materie e progetti associati ai corsi
- Invio e gestione di mail interne
- Mocking con Mockito

## 🧪 Testing

- Unit Test con JUnit
- Integration Test via Postman
- Mocking con Mockito

## 🚀 Avvio del progetto

Clona il repository:

```bash
git clone https://github.com/tuo-repository/infoschool.git
```

Configura il database nel file `application.properties`.

Avvia l’applicazione:

```bash
mvn spring-boot:run
```

Accedi alla documentazione API:

```bash
http://localhost:8080/swagger-ui/index.html
```

## 📦 Dataset iniziale

Incluso tramite la classe `DataLoader`:

- Ruoli: ROLE_ADMIN, ROLE_TEACHER, ROLE_USER
- Utenti di esempio (admin, insegnanti, studenti)
- Corsi con materie e progetti associati
- Progetti per simulare esami e attività didattiche
- Elaborati per simulare la partecipazione degli studenti agli esami
- Mail inviate da un admin o docente ad un docente o studente
- Materie assegnate ai corsi
- TeachedSubject che rappresentano i periodi nei quali un docente insegna una materia
- 

## 🧰 Design Pattern utilizzati

- **Singleton**: configurazioni centralizzate dei componenti
- **DTO**: trasferimento dati tra livelli
- **Repository Pattern**: accesso ai dati tramite Spring Data
- **Factory**: creazione oggetti complessi (es. mapper)

## 📝 Note finali

Con InfoSchool mi preparo ad intraprendere la mia carriera da programmatore.
Ringrazio chiunque creda in me e riconosca il mio valore. 

> "La tecnologia al servizio della formazione." 👨‍🏫
