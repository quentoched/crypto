package cryptoProjetV2;

import java.math.BigInteger;
import java.util.Random;

public class CryptoProjetV2 {
    
    public static void main(String[] args) {
        
         // générer 2 entiers premiers
        BigInteger p = createNbPremier(100);
        BigInteger q = createNbPremier(100);
        BigInteger n = calcN(p, q); // clé publique
     
        Alice a = new Alice(n);
        
        Bob b = new Bob(p,q,n);
                
        // Bob choisi un numéro de réponse et l'encrypte
        BigInteger i = b.getI();
        BigInteger I = b.encrypt(i);
        
        System.out.println("Bob encrypte i : encryption -> " + I);
        
        // Alice transforme ses réponses encryptées à l'aide du i
        a.transformeRep(I); // TODO
        
        // Alice envoi ses réponses encryptées et transformées à Bob
        BigInteger R1 = a.getR1();
        BigInteger R2 = a.getR2();
        
        // Bob décrypte les réponses mais ne peut voir que celle où le masque s'annule
        BigInteger rep1 = b.decrypt(R1);
        BigInteger rep2 = b.decrypt(R2);
        
        System.out.println("rep1 : " + rep1);
        System.out.println("rep2 : " + rep2);
    }
    
    public static boolean estPremierFermat(BigInteger n){
        // si 2^{x-1} = 1 mod (x) alors x est premier avec une bonne probabilité
        // cela ne marche pas avec 561 par exemple ...
        
        BigInteger val = BigInteger.valueOf(2).modPow(n.subtract(BigInteger.ONE), n);
         return val.compareTo(BigInteger.ONE) == 0;
    }
    
    public static BigInteger createNbPremier(int nbBits){
        BigInteger ret;
        do {
            ret = new BigInteger(nbBits, new Random());
        } while(!estPremierFermat(ret));
        return ret;
    }
    
    public static BigInteger calcN(BigInteger p, BigInteger q){
        return p.multiply(q);
    }
    
    public static BigInteger calcPhi(BigInteger p, BigInteger q){
        return (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
    }
}
