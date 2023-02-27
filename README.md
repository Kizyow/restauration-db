# Système de restauration
#### Alexandre PERROT S4-AIL1

## Description
Un projet pour l'IUT Nancy-Charlemagne en S4 dans le module de Qualité et au delà du relationnel
où on doit mettre en place une application permettant de gérer un restaurant en utilisant une base de donnée Oracle en mode transactionnel

## Fonctionnalités

- Système d'authentification pour le restaurant (serveur/gestionnaire)
- Un serveur peut réaliser ces opérations suivantes
  - Consulter les tables disponibles pour une date et heure donnée
  - Réserver une table pour une date et heure donnée
  - Consulter les plats disponibles
  - Commander des plats
- Un gestionnaire, en plus de pouvoir faire tous les opérations d'un serveur, peut faire en plus
  - Gestion des serveurs (création et mise à jour)
  - Affecter un serveur à une table à une date donnée
  - Gestion des plats (création et mise à jour)
  - Calculer le montant d'une réservation et payer (mise à jour de la table)
  - Affichage des serveurs ayant fait du chiffre d'affaire sur une periode donnée
  - Affichage des serveurs n'ayant pas fait de chiffre d'affaire sur une periode donnée

## Détails technique

L'application à été produite en Java 17 (avec JavaFX 17 et Gradle en gestionnaire de dépendances)
Pour pouvoir utiliser ce projet, vous devez lancer un gradle init (attendez qu'il download toutes les dépendences)

**Et vous pouvez lancer l'application à partir du Main** (dans src/main/java/fr/perrot54u/restaurationdb/Main.java)

Si vous souhaitez utiliser une autre base de donnée que celle de l'IUT Charlemagne, vous pouvez créer votre base de donnée Oracle
(changez l'URL de connexion dans DBConnection.java) et je vous ai fourni un fichier SQL dans resources/sql permettant
de créer les tables avec le jeu de données.


