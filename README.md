# Awalé Game

Awalé est un jeu traditionnel africain de type **mancala** où deux joueurs s'affrontent en semant des graines dans des trous disposés sur un plateau. Le jeu implémente des règles avancées et des règles spéciales telles que les graines de couleurs différentes, les captures et la gestion de la famine (starvation). Ce projet est une version numérique d'Awalé, intégrant une intelligence artificielle (IA) basée sur l'algorithme Minimax avec élagage Alpha-Bêta.

---

## **Règles du jeu**

1. **Plateau de départ** :

   - Le plateau comporte 16 trous, répartis également entre les deux joueurs.
   - Chaque trou commence avec 2 graines rouges et 2 graines bleues.

2. **Tour de jeu** :

   - Les joueurs jouent à tour de rôle.
   - À chaque tour, un joueur sélectionne un trou et sème toutes les graines rouges ou bleues qu'il contient.
   - Les graines rouges ne peuvent être semées que dans les trous adverses, tandis que les graines bleues sont semées partout.

3. **Captures** :

   - Un trou contenant exactement 2 ou 3 graines (au total, rouges et bleues) à la fin d'un semis est capturé par le joueur ayant effectué le mouvement.
   - Après une capture on vérifie le trou juste avant et si ce dernier contient également 2 ou 3 graines il est capturé. Ainsi de suite jusqu'à tombé sur un trou que l'on ne peut pas capturé.

4. **Famine (Starvation)** :

   - Si un joueur ne peut plus effectuer de mouvement parce qu'il ne possède plus de graines dans ses trous, son adversaire capture toutes les graines restantes sur le plateau.
   - Le vainqueur est déterminé par le score final.

5. **Fin de partie** :

   - La partie se termine lorsque :
     - Il reste moins de 8 graines sur le plateau.
     - L'un des joueurs ne peut plus jouer (famine).
     - Un joueur atteint les conditions de victoire (>32 graines).

6. **Score final** :
   - Le joueur avec le plus grand nombre de graines capturées remporte la partie.

---

## **Fonctionnalités**

- **Mode Solo** : Jouez contre une intelligence artificielle basée sur l'algorithme Minimax.
- **Règles avancées** : Captures automatiques, famine, et gestion des graines rouges et bleues.
- **Interface Console** : Affichage du plateau en temps réel avec des couleurs pour les graines rouges et bleues.
- **Performance optimisée** :
  - Algorithme Minimax avec élagage Alpha-Bêta.
  - Limitation dynamique de la profondeur pour garantir un temps de calcul inférieur à 2 secondes.
  - Sélection des mouvements optimaux avec une logique de secours (coup aléatoire si nécessaire).
