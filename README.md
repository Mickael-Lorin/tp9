# TP7 — Ciné-Club : API sécurisée avec JWT

API Spring Boot (architecture en couches) sécurisée par JWT + Refresh Token,
persistance PostgreSQL via Docker Compose et migrations Flyway.

## Prérequis

- JDK 17
- Docker + Docker Compose

## Démarrage

```bash
# 1. Variables d'environnement (le .env n'est pas versionné)
cp .env.example .env        # puis adapter si besoin

# 2. Base de données PostgreSQL (conteneur tp7-cineclub-postgres, port 5440)
docker compose up -d

# 3. Lancer l'application (Flyway crée le schéma + insère les données seed)
./mvnw clean spring-boot:run
```

L'API écoute sur `http://localhost:8080`.

## Comptes seedés (V2__seed_data.sql)

| Rôle  | Email                | Mot de passe |
|-------|----------------------|--------------|
| ADMIN | admin@cineclub.fr    | `Admin123!`  |
| USER  | lenny@cineclub.fr    | `User1234!`  |

Récupérer un token : `POST /api/auth/login` avec l'un de ces comptes.

## Tests

```bash
docker compose up -d        # requis : Tp7ApplicationTests utilise @SpringBootTest
./mvnw test                 #          (charge le contexte complet + datasource)
```

## Collection Bruno

Une collection Bruno (`CDA Spring`) couvre l'ensemble de la matrice de tests
(section 18 du sujet) : auth, lectures publiques, opérations ADMIN, et
vérifications de sécurité 401/403.
# tp9
