/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptoProjet;

import static cryptoProjet.CryptoProjet.calcN;
import static cryptoProjet.CryptoProjet.calcPhi;
import static cryptoProjet.CryptoProjet.createNbPremier;
import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author Quentin Degrange
 */
public class Alice {
    private final String response;
    private final BigInteger p;
    private final BigInteger q;
    private final BigInteger n;
    private final BigInteger m;
    private final BigInteger phi;
    
    private BigInteger r;
    
    public BigInteger getN(){
        return n;
    }
    
    public Alice(String response){
        this.response = response;
        // générer 2 entiers premiers
        p = createNbPremier(100);
        q = createNbPremier(100);
        n = calcN(p, q); // clé publique
        
        phi = calcPhi(p, q);
        m = n.modInverse(phi);
    }
    
    public BigInteger encrypt(BigInteger mes){
        do {
            r = new BigInteger(n.bitLength(), new Random());
        } while (r.compareTo(n) >= 0);
        
        System.out.println("Alice :  r -> " + r);
        
        BigInteger encrypt = (mes.multiply(n).add(BigInteger.ONE)).multiply(r.modPow(n, n.pow(2))).mod(n.pow(2));
        
        System.out.println("Alice : encryption -> " + encrypt);
 
        return encrypt;
    }
    
     public BigInteger decrypt(BigInteger mes){
//         r = mes.modPow(n.subtract(BigInteger.ONE), phi).mod(n);
        BigInteger decrypt = ((r.modPow(n, n.pow(2)).modInverse(n.pow(2))).multiply(mes).mod(n.pow(2))).subtract(BigInteger.ONE).divide(n);
    
        System.out.println("Alice : decrypt -> " + decrypt);

        return decrypt;
     }
     
     public BigInteger getResponse(){
         return encrypt(new BigInteger(response));
     }
    
  
}
