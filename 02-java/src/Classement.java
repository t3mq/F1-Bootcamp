/* =========================================================================
   MAILLON 2 — JAVA : le moteur de calcul
   Complétez les quatre méthodes. Les classes Ligne, Resultat et Chargeur
   sont fournies : ne les modifiez pas.
       javac -encoding UTF-8 -d out src/*.java
       java -Dstdout.encoding=UTF-8 -cp out Tests     (les tests)
       java -Dstdout.encoding=UTF-8 -cp out Main      (la production)
   ========================================================================= */

import java.util.List;
import java.util.ArrayList;

public class Classement {

    /**
     * Barème officiel des dix premiers. FOURNI — NE PAS MODIFIER.
     */
    public static final int[] BAREME = {25, 18, 15, 12, 10, 8, 6, 4, 2, 1};

    // 1. pointsPourPosition(position) : points marqués pour cette position.
    //    1 -> 25, 2 -> 18, ..., 10 -> 1. Au-delà de la 10e place : 0.
    //    Un abandon vaut la position 0, donc 0 point.
    public static int pointsPourPosition(int position) {
        if (position < 1 || position > BAREME.length) {
            return 0;
        }
        return BAREME[position - 1];
    }

    // 2. classementPilotes(lignes) : un Resultat par pilote, avec ses points,
    //    ses victoires (position 1) et ses 2e places, trié par :
    //    points décroissants, puis victoires, puis 2e places, puis nom (A→Z).
    public static List<Resultat> classementPilotes(List<Ligne> lignes) {
        List<Resultat> resultat = new ArrayList<>();

        for (Ligne l : lignes) {
            Resultat r = chercher(resultat, l.pilote());
            if (r == null) {
                r = new Resultat(l.pilote(), l.ecurie());
                resultat.add(r);
            }
            r.points += pointsPourPosition(l.position());
            if (l.position() == 1) {
                r.victoires++;
            } else if (l.position() == 2) {
                r.deuxiemes++;
            }
        }
        trier(resultat);
        return resultat;
    }

    private static Resultat chercher(List<Resultat> liste, String nom) {
        for (Resultat r : liste) {
            if (r.nom.equals(nom)) {
                return r;
            }
        }

        return null;
    }

    private static void trier(List<Resultat> liste) {
        liste.sort((a, b) -> {
            if (a.points != b.points) {
                return b.points - a.points;
            }
            if (a.victoires != b.victoires) {
                return b.victoires - a.victoires;
            }
            if (a.deuxiemes != b.deuxiemes) {
                return b.deuxiemes - a.deuxiemes;
            }
            return a.nom.compareTo(b.nom);
        });
    }

    // 3. classementEcuries(pilotes) : additionne les points, victoires et
    //    2e places des pilotes de chaque écurie. Même ordre de tri.
    public static List<Resultat> classementEcuries(List<Resultat> pilotes) {
        List<Resultat> resultat = new ArrayList<>();

        for (Resultat p : pilotes) {
            Resultat e = chercher(resultat, p.ecurie);
            if (e == null) {
                e = new Resultat(p.ecurie, "");
                resultat.add(e);
            }
            e.points += p.points;
            e.victoires += p.victoires;
            e.deuxiemes += p.deuxiemes;
        }

        trier(resultat);
        return resultat;
    }

    // 4. positionMoyenne(lignes, pilote) : moyenne des positions de ce pilote,
    //    ABANDONS EXCLUS, arrondie à 2 décimales. 0 s'il n'a jamais terminé.
    //    Ex. positions 1, 2 et un abandon -> 1.5
    public static double positionMoyenne(List<Ligne> lignes, String pilote) {
        int somme = 0, nb = 0;
        for (Ligne l : lignes) {
            if (l.pilote().equals(pilote) && l.position() > 0) {
                somme += l.position();
                nb++;
            }
        }
        if  (nb == 0) {
            return 0.0;
        }
        return Math.round((double) somme * 100.0 / nb ) / 100.0;
    }
}
