/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptotp2;

import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author Quentin Degrange
 */
public class CryptoTP2 {
    // Exercice 3.3

    public static void main(String[] args) {
        // générer 2 entiers premiers
        BigInteger p = createNbPremier(100);
        BigInteger q = createNbPremier(100);
        // Calcul de n
        BigInteger n = calcN(p, q);
        BigInteger phi = calcPhi(p, q);
        BigInteger m = n.modInverse(phi);
        
        //Calcul de r
        BigInteger r;
        do {
            r = new BigInteger(n.bitLength(), new Random());
        } while (r.compareTo(n) >= 0);
        
        BigInteger x = new BigInteger("1589489616189");
        BigInteger X = (x.multiply(n).add(BigInteger.ONE)).multiply(r.modPow(n, n.pow(2))).mod(n.pow(2));
        
        System.out.println("message : " + x);
        System.out.println("encryption : " + X);
        
        // 1) vérifications
        BigInteger X2 = r.modPow(n, n);
        System.out.println("X = r^n mod n : " + X.mod(n) + " = " + X2);
        
        BigInteger r2 = X.modPow(m, n);
        System.out.println("r = X^m mod n : " + r.mod(n) + " = " + r2);
        
        BigInteger x2 = ((r.modPow(n, n.pow(2)).modInverse(n.pow(2))).multiply(X).mod(n.pow(2))).subtract(BigInteger.ONE).divide(n);
        System.out.println("x = (X.r^-n mod n² - 1)/n : " + x + " = " + x2);
        
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
