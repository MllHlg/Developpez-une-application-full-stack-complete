# MDD - Monde de Dév

MDD a pour but d'être le prochain réseau social dédié aux développeurs. Le but de ce réseau social est d’aider les développeurs qui cherchent un travail par la mise en relation et la collaboration entre pairs.

Ceci est un MVP (Minimum Viable Product) de ce projet. Il permet de s’abonner à des thèmes liés à la programmation. Son fil d’actualité affiche chronologiquement les articles correspondants. L’utilisateur peut également écrire des articles et poster des commentaires.

## 🛠️ Prérequis
* **Java** : version 17 ou supérieure
* **Node.js** : version 18 ou supérieure
* **Angular CLI** : version 17+
* **MySQL** : version 8.0

## 🚀 Installation et Lancement

### 1. Base de données
1. Créez une base de données MySQL nommée `mdd`.
2. Vous pouvez exécuter le script `/back/src/main/resources/sql/insert_data.sql` pour ajouter des éléments à la base de données.

### 2. Back-end (Spring Boot)
1. Allez dans le dossier `/back`.
2. Configurez le fichier `src/main/resources/application.properties` :
   * `spring.datasource.url=jdbc:mysql://localhost:3306/mdd`
   * `spring.datasource.username=VOTRE_USER`
   * `spring.datasource.password=VOTRE_PASSWORD`
   * `security.jwt.secret-key=UNE_CLE_DE_32_CARACTERES_MINIMUM`
3. Lancez le serveur : `./mvnw spring-boot:run`

Vous pouvez voir la documentation de l'API depuis cette adresse une fois le serveur lancé : `http://localhost:9000/swagger-ui/index.html#/`

### 3. Front-end (Angular)
1. Allez dans le dossier `/front`.
2. Installez les dépendances : `npm install`
3. Lancez l'application : `ng serve`
4. Accédez à l'application via `http://localhost:4200`
