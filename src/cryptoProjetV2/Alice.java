/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cryptoProjetV2;

import static cryptoProjetV2.CryptoProjetV2.*;
import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author Quentin Degrange
 */
public class Alice {
    private final BigInteger n;
    
    private BigInteger R1;
    private BigInteger R2;
    
    public BigInteger getR1(){
        return R1;
    }   
    
    public BigInteger getR2(){
        return R2;
    } 
    
    public Alice(BigInteger n){
        this.n = n;
        
        BigInteger r1 = new BigInteger("11111");
        BigInteger r2 = new BigInteger("22222");
        
        this.R1 = encrypt(r1);
        this.R2 = encrypt(r2);
    }
    
    public void transformeRep(BigInteger I){
        System.out.println("ancien R1 : " + R1);
        
        BigInteger rand1;
        do {
            rand1 = new BigInteger(n.bitLength(), new Random());
        } while (rand1.compareTo(n) >= 0);
        
        BigInteger M1 = I.multiply(encrypt((new BigInteger("1")).negate())).modPow(rand1, n.pow(2));
        this.R1 = R1.multiply(M1).mod(n.pow(2));
        
        System.out.println("nouveau R1 : " + R1);
        
        
        System.out.println("ancien R2 : " + R2);
        
        BigInteger rand2;
        do {
            rand2 = new BigInteger(n.bitLength(), new Random());
        } while (rand2.compareTo(n) >= 0);
        
        BigInteger M2 = I.multiply(encrypt((new BigInteger("2")).negate())).modPow(rand2, n.pow(2));
        this.R2 = R2.multiply(M2).mod(n.pow(2));
        
        System.out.println("nouveau R2 : " + R2);
    }
    
    public BigInteger encrypt(BigInteger mes){
        BigInteger r;
        do {
            r = new BigInteger(n.bitLength(), new Random());
        } while (r.compareTo(n) >= 0);
        
//        BigInteger encrypt = (mes.multiply(n).add(BigInteger.ONE)).multiply(r.modPow(n, n.pow(2)));//.mod(n.pow(2));
        BigInteger encrypt = BigInteger.ONE.add(n).modPow(mes, n.pow(2)).multiply(r.modPow(n, n.pow(2))).mod(n.pow(2));

        return encrypt;
    }
}
