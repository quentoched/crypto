package cryptoProjetV2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class Alice {

    private final BigInteger n;

    private final ArrayList<BigInteger> r = new ArrayList<>(); // Ce sont les réponses décryptées. Comme r n'a pas de getter, personne n'y a accès
    private final ArrayList<BigInteger> R = new ArrayList<>(); // Ce sont les réponses encryptées.

    public ArrayList<BigInteger> getR() {
        return R;
    }

    public Alice(BigInteger n) {
        this.n = n;

        // cela permet simplement de compter à partir de 1
        r.add(0, BigInteger.ZERO);
        R.add(0, BigInteger.ZERO);

        r.add(1, BigInteger.valueOf(113));
        r.add(2, BigInteger.valueOf(170));
        r.add(3, BigInteger.valueOf(66000000));
        r.add(4, BigInteger.valueOf(42));
        r.add(5, BigInteger.valueOf(6));
        
        for (int i = 1; i < r.size(); i++) {
            R.add(i, this.encrypt(r.get(i)));
        }
        System.out.println("Alice : je connais n donc je peux seulement encrypter. J'ai dailleurs encrypté toutes mes réponses.");
    }

    public void transformeRep(BigInteger I) {
        BigInteger rand;
        BigInteger Rk;

        for (int k = 1; k < R.size(); k++) {
            Rk = R.get(k);

            do {
                rand = new BigInteger(n.bitLength(), new Random());
            } while (rand.compareTo(n) >= 0);

            BigInteger Mk = I.multiply(encrypt((BigInteger.valueOf(k)).negate())).modPow(rand, n.pow(2));
            Rk = Rk.multiply(Mk).mod(n.pow(2));

            R.set(k, Rk);
        }
    }

    public final BigInteger encrypt(BigInteger mes) {
        BigInteger rand;
        do {
            rand = new BigInteger(n.bitLength(), new Random());
        } while (rand.compareTo(n) >= 0);

        BigInteger encrypt = BigInteger.ONE.add(n).modPow(mes, n.pow(2)).multiply(rand.modPow(n, n.pow(2))).mod(n.pow(2));

        return encrypt;
    }
}
