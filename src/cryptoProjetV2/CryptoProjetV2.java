package cryptoProjetV2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CryptoProjetV2 {

    public static void main(String[] args) {

        // générer 2 entiers premiers
        BigInteger p = createNbPremier(100);
        BigInteger q = createNbPremier(100);
        BigInteger n = calcN(p, q); // clé publique

        Alice a = new Alice(n);

        Bob b = new Bob(p, q, n);
        
        System.out.println("Alice : Bob, que veux-tu savoir ? Attention, je ne donnerais réponse qu'à une seule question.");
        
        System.out.println("Q1 : Quel est l'âge du capitaine ?");
        System.out.println("Q2 : Quel est le quotient intelectuel d'Einstein ?");
        System.out.println("Q3 : Quelle est la population de la France ?");
        System.out.println("Q4 : Quelle est la réponse à la question universelle ?");
        System.out.println("Q5 : Quel est l'âge du cheval blanc d'Henri IV ?");
        
        System.out.println("Dis moi le numéro de la question dont tu veux connaitre la réponse (1, 2, 3, 4 ou 5) :");
        
        Scanner sc = new Scanner(System.in);
        BigInteger i = BigInteger.valueOf(sc.nextInt());
        
        // Bob choisi un numéro de réponse et l'encrypte
        BigInteger I = b.encrypt(i);

        System.out.println("Bob : j'ai choisi i = " + i);
        System.out.println("Bob : l'encryption de i donne I = " + I);
        System.out.println("Bob : je donne ce I à Alice pour qu'elle mette un masque sur toutes les réponses, sauf celle qui m'intéresse.");

        // Alice transforme ses réponses encryptées à l'aide du i
        System.out.println("Alice : je transforme mes réponses encryptées avec le I choisi. Et je les envoies à Bob. Je ne peux malheureusement pas savoir à quelle question je réponds ...");
        a.transformeRep(I);
        
        // Alice envoi ses réponses encryptées et transformées à Bob
        ArrayList<BigInteger> R = a.getR();

        // Bob décrypte les réponses mais ne peut voir que celle où le masque s'annule
        BigInteger rep;
        System.out.println("Bob : voila les réponses que j'ai récupéré. Malheureusement, nous ne pouvons déchiffrer que la " + i + "ème réponse.");
        for (int k = 1; k < R.size(); k++) {
            rep = b.decrypt(R.get(k));
            System.out.println("rep" + k + " : " + rep);
        }
    }

    // fonctions pour le calcul de n et phi
    public static boolean estPremierFermat(BigInteger n) {
        // si 2^{x-1} = 1 mod (x) alors x est premier avec une bonne probabilité
        // cela ne marche pas avec 561 par exemple ...

        BigInteger val = BigInteger.valueOf(2).modPow(n.subtract(BigInteger.ONE), n);
        return val.compareTo(BigInteger.ONE) == 0;
    }

    public static BigInteger createNbPremier(int nbBits) {
        BigInteger ret;
        do {
            ret = new BigInteger(nbBits, new Random());
        } while (!estPremierFermat(ret));
        return ret;
    }

    public static BigInteger calcN(BigInteger p, BigInteger q) {
        return p.multiply(q);
    }

    public static BigInteger calcPhi(BigInteger p, BigInteger q) {
        return (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
    }
}
