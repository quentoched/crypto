/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptoProjet;

import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author Quentin Degrange
 */
public class Bob {
    private final BigInteger n;
    private BigInteger r;
    
    public Bob(BigInteger n){
        this.n = n;
    }
    
    public BigInteger askAliceEncryptedRes(Alice a){
        return a.getResponse();
    }

    BigInteger addRandom(BigInteger res) {
        do {
            r = new BigInteger(n.bitLength(), new Random());
        } while (r.compareTo(n) >= 0);
                
        BigInteger resRand = res.modPow(r, n.pow(2));
        
        System.out.println("Bob : j'ajoute un random -> " + resRand);

        return resRand;
    }

    BigInteger removeRand(BigInteger resRand) {     
        System.out.println("Bob : le random vaut -> " + r);
        BigInteger fin = resRand.subtract(r).mod(n); // doit-on ajouter mod(n) ?
        System.out.println("Bob : j'enlève le random -> " + fin);
        return fin;
    }
}
