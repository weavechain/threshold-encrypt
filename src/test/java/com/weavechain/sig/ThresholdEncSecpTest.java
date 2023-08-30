package com.weavechain.node.sig;

import com.google.common.truth.Truth;
import com.weavechain.sig.ThresholdEncSecp;
import com.weavechain.sig.ThresholdEncSecpParams;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.security.Security;
import java.util.List;
import java.util.Set;

public class ThresholdEncSecpTest {

    @BeforeClass
    public void setUp() {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Test
    protected void testEncryptNumber() throws Exception {
        ThresholdEncSecp tsig = new ThresholdEncSecp(3, 5);

        ThresholdEncSecpParams params = tsig.generate(null);

        List<byte[]> partialShares = params.getPrivateShares().subList(0, 3);
        Set<Integer> nodes = Set.of(0, 1, 2);

        boolean check = ThresholdEncSecp.verify(partialShares.get(0), 1, params.getPublicShares());
        Truth.assertThat(check).isTrue();

        //value encrypted using target pub key
        BigInteger value = new BigInteger("1234567890");
        byte[] enc = ThresholdEncSecp.encrypt(params.getPublicKey(), value);

        //value decrypted by t out of n
        byte[] privateKey = tsig.reconstruct(partialShares, nodes);
        Truth.assertThat(privateKey).isEqualTo(params.getPrivateKey());
        BigInteger dec = ThresholdEncSecp.decrypt(privateKey, enc);
        Truth.assertThat(dec).isEqualTo(value);
    }

    @Test
    protected void testEncryptString() throws Exception {
        ThresholdEncSecp tsig = new ThresholdEncSecp(3, 5);

        ThresholdEncSecpParams params = tsig.generate(null);

        List<byte[]> partialShares = params.getPrivateShares().subList(0, 2);
        partialShares.add(params.getPrivateShares().get(4));
        Set<Integer> nodes = Set.of(0, 1, 4);

        boolean check = ThresholdEncSecp.verify(partialShares.get(0), 1, params.getPublicShares());
        Truth.assertThat(check).isTrue();

        String value = "test message to be decrypted by t out of n recipients";
        byte[] enc = ThresholdEncSecp.encrypt(params.getPublicKey(), value);

        byte[] privateKey = tsig.reconstruct(partialShares, nodes);
        Truth.assertThat(privateKey).isEqualTo(params.getPrivateKey());
        String dec = ThresholdEncSecp.decryptString(privateKey, enc);
        Truth.assertThat(dec).isEqualTo(value);
    }
}
