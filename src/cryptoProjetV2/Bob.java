package cryptoProjetV2;

import static cryptoProjetV2.CryptoProjetV2.calcPhi;
import java.math.BigInteger;
import java.util.Random;

public class Bob {
    private final BigInteger n;
    private final BigInteger phi;
    private final BigInteger m;
    
    public Bob(BigInteger p, BigInteger q, BigInteger n){
        this.phi = calcPhi(p, q);
        this.m = n.modInverse(phi);
        this.n = n;
        
        System.out.println("Bob : je connais n et phi donc je peux encrypter et décrypter.");
    }
    
    public final BigInteger encrypt(BigInteger mes){
        BigInteger r;
        do {
            r = new BigInteger(n.bitLength(), new Random());
        } while (r.compareTo(n) >= 0);
        
        BigInteger encrypt = BigInteger.ONE.add(n).modPow(mes, n.pow(2)).multiply(r.modPow(n, n.pow(2))).mod(n.pow(2));
 
        return encrypt;
    }

     public final BigInteger decrypt(BigInteger mes){
        BigInteger r = mes.modPow(n.modPow(BigInteger.ONE.negate(), phi), n);

        BigInteger decrypt = ((r.modPow(n, n.pow(2)).modInverse(n.pow(2))).multiply(mes).mod(n.pow(2))).subtract(BigInteger.ONE).divide(n);
    
        return decrypt;
     }
}
