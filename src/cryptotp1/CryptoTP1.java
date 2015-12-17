/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptotp1;

import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author Quentin Degrange
 */
public class CryptoTP1 {

    public static void main(String[] args) {
        BigInteger v = new BigInteger("1589489616189");
        
        // 3) test si un nombre est premier
        boolean r = estPremier(v);
        System.out.println(r);
        
        // 4) test de Fermat
        boolean r2 = estPremierFermat(v);
        System.out.println(r2);
        
        // 5) générer 2 entiers premiers de 512 bits
        BigInteger p = createNbPremier(512);
        BigInteger q = createNbPremier(512);
        System.out.println("p : " + p);
        System.out.println("q : " + q);
        
        // 6) Calcul de n et phi
        BigInteger n = calcN(p, q);
        BigInteger phi = calcPhi(p, q);
        System.out.println("n : " + n);
        System.out.println("phi : " + phi);
        
        // 7) calcul de e
        BigInteger e = E(phi);
        System.out.println("e : " + e);
        
        // 8) calcul de d
        BigInteger d = e.modInverse(phi);
        System.out.println("d x e mod n = " + d.multiply(e).mod(n));
        
        // 9) vérification de l'encryption/décryption
        System.out.println("x = " + v); // message original
        BigInteger X = v.modPow(e, n);
        System.out.println("X = x^e = " + X); // encryption
        System.out.println("X^d = " + X.modPow(d, n)); // décryption
    }
    
    public static boolean estPremier(BigInteger n){
         for (int i = 2; i < (int) Math.sqrt(n.doubleValue()); i++){
             if (n.mod(BigInteger.valueOf(i)).compareTo(BigInteger.valueOf(0)) == 0){
                 return false;
             }
         }
         return true;
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

    private static BigInteger E(BigInteger phi) {
        BigInteger e;
        do {
            e = createNbPremier(16);
        } while((e.gcd(phi)).compareTo(BigInteger.valueOf(1)) != 0); //tant que e n'est pas premier avec phi
        return e;
    }

   
    
    

}
