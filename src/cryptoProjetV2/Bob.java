/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptoProjetV2;

import static cryptoProjetV2.CryptoProjetV2.calcPhi;
import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author Quentin Degrange
 */
public class Bob {
    private final BigInteger n;
    private final BigInteger phi;
    private final BigInteger m;
    
    private final BigInteger i;
        
    public Bob(BigInteger p, BigInteger q, BigInteger n){
        this.phi = calcPhi(p, q);
        this.m = n.modInverse(phi);
        this.n = n;
        
        this.i = new BigInteger("1");
    }
    
    public BigInteger encrypt(BigInteger mes){
        BigInteger r;
        do {
            r = new BigInteger(n.bitLength(), new Random());
        } while (r.compareTo(n) >= 0);
        
//                BigInteger encrypt = (mes.multiply(n).add(BigInteger.ONE)).multiply(r.modPow(n, n.pow(2)));//.mod(n.pow(2));
        BigInteger encrypt = BigInteger.ONE.add(n).modPow(mes, n.pow(2)).multiply(r.modPow(n, n.pow(2))).mod(n.pow(2));
 
        return encrypt;
    }

     public BigInteger decrypt(BigInteger mes){
        BigInteger r = mes.modPow(n.modPow(BigInteger.ONE.negate(), phi), n);

        BigInteger decrypt = ((r.modPow(n, n.pow(2)).modInverse(n.pow(2))).multiply(mes).mod(n.pow(2))).subtract(BigInteger.ONE).divide(n);
    
        System.out.println("Bob : decryption -> " + decrypt);

        return decrypt;
     }
     
     public BigInteger getI(){
         return this.i;
     }
}
