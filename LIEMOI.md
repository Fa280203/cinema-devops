# 🎬 Cinema REST Micro‑services

Projet académique (EFREI) — architecture **micro‑services Java** pour la gestion des cinémas parisiens (inspiration : AlloCiné).

---

## Sommaire

1. [Fonctionnalités](#fonctionnalités)
2. [Pile technique](#pile-technique)
3. [Architecture & ports](#architecture--ports)
4. [Démarrage rapide](#démarrage-rapide)
5. [Développement local sans Docker](#développement-local-sans-docker)
6. [Structure du projet](#structure-du-projet)
7. [Endpoints principaux](#endpoints-principaux)
8. [Crédits & licence](#crédits--licence)

---

## Fonctionnalités

| Micro‑service   | Description                                                    |  Endpoints clés         |
| --------------- | -------------------------------------------------------------- | ----------------------- |
| **Auth**        | Authentification exploitants, émission & vérification JWT      | `/login`, `/register`   |
| **Cinema**      | CRUD des cinémas parisiens                                     | `/cinemas`              |
| **Movie**       | CRUD des films (titre, durée, langue…)                         | `/movies`               |
| **Schedule**    | Gestion des séances (période + jours + heure)                  | `/schedules`            |
| **Public**      | Agrégation en lecture seule pour l’UI publique                 | `/public/{city}/movies` |
| **API Gateway** | Point d’entrée unique : routage vers les services + filtre JWT | `/`                     |

---

## Pile technique

* **Java 11** ↔ Tomcat 9 (Servlet 4, namespace `javax.*`)
* **JAX‑RS / Jersey 2.41** + **Jackson** (JSON)
* **Maven multi‑module**
* **Docker + Docker Compose** (1 conteneur Tomcat par service)
* (À venir) MySQL par service, Redis pour le cache du Public Service

---

## Architecture & ports

| Service     | Port conteneur | Port hôte | Context path        |
| ----------- |----------------|-----------| ------------------- |
| API Gateway | 8096           | 8096      | `/`                 |
| Auth        | 8090           | 8090      | `/auth-service`     |
| Cinema      | 8091           | 8091      | `/cinema-service`   |
| Movie       | 8092           | 8092      | `/movie-service`    |
| Schedule    | 8093           | 8093      | `/schedule-service` |
| Public      | 8094           | 8094      | `/public-service`   |

---

## Démarrage rapide

Prérequis : **Docker 20+** & **Docker Compose v2**.

```bash
# 1 — Cloner le repo
git clone https://github.com/<votre-org>/cinema-rest-microservices.git
cd cinema-rest-microservices

# 2 — Construire et lancer tous les conteneurs
docker compose up --build -d

# 3 — Vérifier :
curl http://localhost:8096/           # -> API Gateway (sera 404 tant que non codé)
curl http://localhost:8090/auth-service/webapi/myresource
```

Arrêt : `docker compose down`. Les logs : `docker compose logs -f auth`.

---

## Développement local **sans Docker**

Chaque service embarque un plugin Tomcat :

```bash
mvn -pl auth-service tomcat7:run   # http://localhost:8090/auth-service/
```

Réitérez pour cinema‑service (8091)…

Commande globale :

```bash
mvn clean verify   # compile les 6 WAR
```

---

## Structure du projet

```
cinema-rest-microservices/
├─ pom.xml                ← POM parent (packaging pom)
├─ auth-service/
│   ├─ pom.xml            ← packaging war + tomcat7‑plugin
│   ├─ Dockerfile         ← multi‑stage build Maven ► Tomcat
│   └─ src/main/java/…
├─ cinema-service/
│   └─ …
├─ movie-service/
├─ schedule-service/
├─ public-service/
├─ api-gateway/
└─ docker-compose.yml
```

---

## Endpoints principaux (draft)

| Méthode | URL                    | Service  | Auth ? |
| ------- | ---------------------- | -------- | ------ |
| `POST`  | `/login`               | Auth     | non    |
| `POST`  | `/cinemas`             | Cinema   | oui    |
| `GET`   | `/movies/{id}`         | Movie    | oui    |
| `POST`  | `/schedules`           | Schedule | oui    |
| `GET`   | `/public/paris/movies` | Public   | non    |

---


