/* =========================================================================
   MAILLON 3 — JAVASCRIPT : l'interface
   Les données arrivent du maillon Java, dans donnees.js :
     PILOTES = [{nom, ecurie, points, victoires}, ...]
     ECURIES = [{nom, points, victoires}, ...]
   Complétez les trois fonctions, puis ouvrez index.html dans le navigateur.
   ========================================================================= */

// 1. trierParPoints(liste) : renvoie une NOUVELLE liste triée par points
//    DÉCROISSANTS. La liste reçue ne doit pas être modifiée.
//    À points égaux, celui qui a le plus de victoires passe devant.
function trierParPoints(liste) {
  const copie = [...liste];
  copie.sort((a, b) => {
    if (a.points !== b.points) return b.points - a.points;
    return b.victoires - a.victoires;
  })
  return copie;
}

// 2. remplirTableau(idCorps, liste) : remplit le <tbody> dont l'id est fourni.
//    Une ligne <tr> par entrée, avec dans l'ordre les cellules <td> :
//      rang (1, 2, 3...) | nom | écurie (chaîne vide si absente) | points | victoires
//    Chaque <tr> porte l'attribut data-nom. Un nouvel appel REMPLACE le contenu.
function remplirTableau(idCorps, liste) {
  const corps = document.getElementById(idCorps);
  corps.innerHTML = '';

  liste.forEach((entree, i) => {
    const tr = document.createElement("tr");
    tr.setAttribute("data-nom", entree.nom);

    const valeurs = [i + 1, entree.nom, entree.ecurie || '', entree.points, entree.victoires];
    for (const valeur of valeurs) {
      const td = document.createElement("td");
      td.textContent = valeur;
      tr.appendChild(td);
    }

    corps.appendChild(tr);
  })
}

// 3. marquerPodium(idCorps) : ajoute la classe CSS "podium" aux TROIS PREMIÈRES
//    lignes du tableau, et la retire de toutes les autres.
function marquerPodium(idCorps) {
  const lignes = document.getElementById(idCorps).querySelectorAll("tr");
  lignes.forEach((tr, i) => {
    if (i < 3) {
      tr.classList.add("podium");
    } else {
      tr.classList.remove("podium");
    }
  });
}

/* --- FOURNI — NE PAS MODIFIER : affichage de la saison ------------------- */
function afficherSaison() {
  if (typeof PILOTES === "undefined") {
    return;
  }
  remplirTableau("corps-pilotes", trierParPoints(PILOTES));
  marquerPodium("corps-pilotes");
  remplirTableau("corps-ecuries", trierParPoints(ECURIES));
  marquerPodium("corps-ecuries");
}
