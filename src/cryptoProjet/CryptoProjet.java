package cryptoProjet;

import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author Quentin Degrange
 */
public class CryptoProjet {
    
    public static void main(String[] args) {
     
        Alice a = new Alice("11111"); 
        Bob b = new Bob(a.getN());
        
        System.out.println("n = " + a.getN());
        
        // Alice envoi ses réponses encryptées à Bob
        BigInteger res = b.askAliceEncryptedRes(a);
        
        // Bob ajoute un random, encrypte son message et l'envoi à Alice
        BigInteger EncryptResRand = b.addRandom(res);
        
        // Alice décrypte le message avec sa clé privée et l'envoi à Bob
        BigInteger resRand = a.decrypt(EncryptResRand);
        
        // Bob enlève le random
        BigInteger finalRes = b.removeRand(resRand);
        
        System.out.println(finalRes);
        
    }
    
    public static boolean estPremierFermat(BigInteger n){
        // si 2^{x-1} = 1 mod (x) alors x est premier avec une bonne probabilité
        // cela ne marche pas avec 561 ...
        
        BigInteger val = BigInteger.valueOf(2).modPow(n.subtract(BigInteger.valueOf(1)), n);
         return val.compareTo(BigInteger.valueOf(1)) == 0;
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
        return (p.subtract(BigInteger.valueOf(1))).multiply(q.subtract(BigInteger.valueOf(1)));
    }
}
