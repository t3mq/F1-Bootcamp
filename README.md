# Projet F1 Bootcamp

Le projet est constitué en plusieurs étapes : chaque langage prend le fichier produit par le précedent et fait une étape du traitement.

## Structure

```
donnees/ données bruts (resultats.csv : 5 courses, 10 pilotes)
01-python/ étape 1 : nettoyage des résultats (ingestion.ypnb)
02-java/ étape 2 : calcul des classements
03-js/ étape 3 : affichage des classements dans le navigateur
```

## Étape 1 : Python (Igestion)

Lit l'export brut `donnees/resultats.csv` et produit `02-java/courses_propres.csv` au format du contrat 1.

Deux transformations :

1. Le chrono `1:33:614` devient un nombre de secondes `93.614`.
2. Un abandon devient la position `0` avec un temps vide, et la colonne `statut` disparaît.

Fonctions :

- `temps_en_secondes(texte)` : convertit un chrono en secondes, `None` si vide.
- `lire_resultats(chemin)` : lit le CSV brut et renvoie une liste de dictionnaires.
- `ecrire_course_propres(chemin, lignes)` : écrit le fichier du contrat 1.

**Contrat 1** (`course_propres.csv`) : 
```
course;pilote;ecurie;position;temps_tour
Bahrein;VERSTAPPEN;Red Bull;1;93.614
Bahrein;HAMILTON;Mercedes;0;
```

## Étape 2 : Java (Calcul)

Lit `courses_propres.csv` et calcule les classements. Les classes `Ligne`, `Resultat` et `Chargeur` sont fournies.

- `pointPourPosition(position)` : barème 25, 18, 15, 12, 10, 8, 6, 4, 2, 1 pour le top 10, 0 sinon (abandon compris).
- `classementPilotes(lignes)` : points, victoires et 2e places par pilote.
- `classementEcuries(ligne, pilote)` : moyenne des positions hors abandons, arrondie à 2 décimales.

Ordre de tri des classements : points, puis victoires, puis 2e places (décroissants), puis nom (A à Z).

## Étape 3 : JavaScript (Interface) 

Affiche les classements pilotes et écuries dans une page web. Les données arrivent de l'étape Java dans `donnees.js`

```
PILOTES = [{nom, ecurie, points, victoires}, ...]
ECURIES = [{nom, points, victoires}, ...]
```

Fonctions :
- `trierParPoints(liste)` : renvoie une nouvelle liste triée par points puis victoires (décroissants), sans modifier l'originale.
- `remplirTableau(idCorps, liste)` : remplit le tableau avec une ligne par entrée (rang, nom, écurie, points, victoires). Un nouvel appel remplace le contenu.
- `marquerPodium(idCorps)` : ajoute la classe `podium` aux trois premières lignes et la retire des autres.

