# Projet Donjon - PC L3 Info Luminy
par YUN Eunsun et IKHLEF Eddy, L3 Informatique Luminy Groupe 2 (2020-2021)
## COMMENT JOUER
### Interactions
Toute les interactions sont graphiques.
Tout à droite: zone d"interaction du personnage
avec:
- Des croix directionnelles: appuyez dessus pour se deplacer à GAUCHE/HAUT/DROITE/BAS
Un bouton interaction (bulle de texte): avoir des feedback sur la salle ou recuperer/swapper les objets d'un coffre.
- Bouton d'attaque, objet, magique: permet d'attaquer, d'utiliser un item ou une magie en cliquant dessus en combat ou hors d'un combat*, **)
*Il est possible d'utiliser des objets ou magie de soin hors combat pour se soigner, on les reconnaît par le fait que la valeur de leurs dégâts sont négatif.
**Si vous n'avez pas d'arme/objet/magie il n'y aura pas de texte d'indication en dessous des boutons, si vous en avez il y aura une information. 
### Interface
En haut a droite vous avez le dessin de la salle, qui est toujours orienté de la même façon, en regardant vers le haut. la minimap (matrice en bas a gauche) represente le donjon avec:
- cases noires: cases non visitées
- cases grises: cases visitées
- cases rouges: murs
- case verte: position actuelle
- case or: sortie du donjon (dessiner dès le début)

A droite de la minimap vous avez les logs afficher, dès qu'un nouveau texte est afficher il sera mis tout en haut (sens de lecture de bas vers le haut)

### Combat
Si vous rentrer dans une salle avec un monstre, "you face a monstre" sera afficher dans le log signifiant le début d'un combat (le monstre sera aussi afficher). Lors d'un combat vous devez vous battre, vous ne pouvez pas fuir sous peine de se faire attaquer. De même si vous essayer d'utiliser un objet ou magie sans en avoir vous ne ferez rien et le monstre va vous attaquer. Si une salle possède un monstre et un coffre vous devrez tuer le monstre pour obtenir le coffre.file:///D:/Downloads/genealogie.pl

### Coffres et Inventaire
Vous pouvez porter au maximum 1 arme, 1 objet, 1 magie. Si vous possédez l'un deux et ouvrer un coffre, un swap aura lieu. Ainsi vous pourrez toujours récupérer l'objet déposer dans ce coffre (swap)

### Mort et victoire
en absence de temps pour un écran titre et une gestion de paramètre, si l'on gagne (en rentrant dans la salle de victoire) ou meurt (pv joueur <= 0) un message de victoire/game over s'affiche pour du feedback et bloque les actions. Appuyez sur le bouton d'interaction pour quitter.

### Changer la taille du donjon et difficulté initiale:
les donjons on des grilles carrées. Vous trouverez dans le SceneController ces lignes:

    //20 = nb de cellules du donjon (20*20), reduire la taille pour simplifier, ne pas aller au dessus de 60.  
    private final DifficultyStrategy difficultyStrategy = new SimpleDifficultyEnhance(1, 20);
qui explique comment changer manuellement la taille du donjon et la difficulté de départ. Avec plus de temps nous l'aurions implémenter avec l’écran titre, une sélection de difficulté. 
La difficulté implémenter pour le moment est simple: a chaque mouvement dans une nouvelle salle, elle augmente de 1. 

### Pseudo Courbe de Progression
au plus la difficulté est élevées, au plus les stats des armes, objets et magies augmentent. De meme pour l'attaque et la vie des monstres. Si nous nous soignons avec un objet qui nous rends plus que la vie max, notre vie max augmenteras pour éviter de mourrir en un coup dans de grandes difficultées.

### Patrons de conceptions
- Strategy: gestion de la difficulté, génération des donjons
- Factory: gestion de création des coffres, monstres, objets, armes, magie et personnage.
- "visitor" (comme visitor sans visitor ?) pour les classes Drawable.

### Principes SOLID
On a essayer de respecter au mieux possible les différents principes et le keep it simple.

### Problèmes possibles
On a relever certains soucis mais assez rares:
- des fois quand on se deplace pour la première fois la case de d"part reste verte, si on repasse dessus tout est regler
- arriver une seule fois (on ne sais pas pourquoi): salle de depart = salle de fin
- arriver une seule fois (on ne sais pas pourquoi): salle de fin non accessible