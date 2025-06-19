# 📦 Projet de Facturation – Spring Boot

Ce projet est une application backend développée avec **Spring Boot**, permettant de gérer des **factures**, leurs **lignes**, ainsi que les **clients** associés. Il suit une architecture RESTful et expose des endpoints documentés avec **Swagger (OpenAPI)**.

## 🛠️ Technologies utilisées

- **Java 17**
- **Spring Boot 3**
- **Spring Web** – pour créer des API REST
- **Spring Data JPA** – pour la gestion des entités et des bases de données
- **H2 Database** – base de données en mémoire (utilisée pour le développement/test)
- **Spring Boot DevTools** – pour le rechargement automatique
- **Validation (Jakarta Bean Validation)** – pour valider les données entrantes
- **SpringDoc OpenAPI (Swagger)** – pour la documentation des endpoints
- **Maven** – gestionnaire de dépendances et de compilation

## 🧾 Fonctionnalités principales

- Création, modification et suppression de **clients**
- Création de **factures** associées à un client
- Ajout de **lignes de facture** avec quantités, prix unitaires, TVA
- Calcul automatique des **totaux HT, TVA, TTC**
- Validation des données (ex : email valide,quantite etc.)
- Console H2 disponible pour inspection de la base de données
- Interface Swagger pour tester les endpoints facilement

## 💾 Base de données H2


- Console disponible ici : [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- URL JDBC : `jdbc:h2:file:./data/arimayi_db`
- Nom d'utilisateur : `root`
- Mot de passe : *(laisser vide)*


## 🚀 Lancement de l'application

### 1. Prérequis
- Java 17
- Maven

### 2. Lancer via Maven 

```bash
mvn spring-boot:run
```
### 3. Accéder à l'API:
http://localhost:8080/swagger-ui.html

