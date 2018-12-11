# SpaceBattle
EN: Multiplayer shooting game, inspired by the retro video game Xpilot.

FR: Jeu de tir multijoueur, inspiré du jeu vidéo rétro Xpilot.

## For developpers / Pour les développeurs
EN: This project is coded in Java, using the Slick2D library.

FR: Ce projet est codé en Java, à l'aide de la bibliothèque Slick2D.

### Prerequisites / Prérequis
EN: You need to have a Java IDE, the following steps will be done with Eclipse.

FR: Vous avez besoin d'avoir un IDE Java, les étapes suivantes seront réalisées avec Eclipse.

### Installing / Installation
EN: Clone the repository.

FR: Cloner le dépôt.

`git clone https://github.com/SebGonzalez/SpaceBattle.git .`

EN: Download archives of libraries and natives.

FR: Télécharger les archives des libraires et natifs.

`https://drive.google.com/drive/folders/1B6r9rlw-1isVJJF9cq5zlvwJhc3gsrnH?usp=sharing`

EN: Extract the archive, then copy the "lib" and "native" folders in the project's location.

FR: Extraire l'archive, puis copier les dossiers "lib" et "native" dans le répertoire du projet.

EN: Open the project with Eclipse. Right click -> Properties -> Java Build Path -> Add External JARs
    Select all the JARs in the "lib" folder.

FR: Ouvrir le projet avec Eclipse. Clic droit -> Properties -> Java Build Path -> Add External JARs
    Sélectionnez tout les JARs contenus dans le dossier "lib".
    
![capture d ecran 21](https://user-images.githubusercontent.com/43208062/47381708-f24e7500-d700-11e8-887a-da26980828c0.png)

![capture d ecran 22](https://user-images.githubusercontent.com/43208062/47382211-0777d380-d702-11e8-9bf0-e155288a9cd7.png)

EN: Open the Run Configurations menu, Arguments tab, then copy the line below in VM Arguments:

FR: Ouvrir le menu Run Configurations, onglet Arguments, puis copier le code ci-dessous dans VM Arguments:

`-Djava.library.path=C:\User\YourName\...\SpaceBattle-master\native`

![capture d ecran 23](https://user-images.githubusercontent.com/43208062/47382281-39893580-d702-11e8-9f33-5a079f6c342c.png)

![capture d ecran 24](https://user-images.githubusercontent.com/43208062/47382349-63daf300-d702-11e8-9b69-4882ea40b7c1.png)

## Running / Fonctionnement

EN: Launch the server with "SpaceBattle_Serveur.jar".
    Launch the game with "SpaceBattle.jar".

FR: Exécuter le serveur avec "SpaceBattle_Serveur.jar".
    Executer le jeu avec "SpaceBattle.jar".
    
## Authors / Auteurs
* ARTERO Axel
* BIEMAR Alexandre
* BOUDRAA Amine
* GONZALEZ Sébastien

## Informations / Informations
EN: This is a study project, we invite you to recover this code and to add new feathures.
    Some links below (the tutorial is in french) to learn how to use the Slick2D library.

FR: Il s'agit d'un projet d'études, nous vous invitons à récupérer ce code et à ajouter de nouvelles fonctionnalités.
    Quelques liens ci-dessous pour apprendre à utiliser la librairie Slick2D
    
`http://www.shionn.org/tutoriels-slick-2d
http://slick.ninjacave.com/javadoc/`

