## Threshold Encryption using secp256k1

A pure Java implementation of a Threshold Encryption scheme.

This particular threshold encryption is a way of encoding the data by a party, such that it can be decrypted only by multiple participants collaborating.

T out of N participants need to be part of the threshold decryption scheme to be able to retrieve the plaintext.

Part of [Weavechain](https://weavechain.com): The Layer-0 For Data

### Usage

#### Gradle Groovy DSL

```
implementation 'com.weavechain:threshold-encrypt:1.0.1'
```

#### Gradle Kotlin DSL

```
implementation("com.weavechain:threshold-encrypt:1.0.1")
```

##### Apache Maven

```xml
<dependency>
  <groupId>com.weavechain</groupId>
  <artifactId>threshold-encrypt</artifactId>
  <version>1.0.1</version>
</dependency>
```

#### Sample

Number encryption

```java
int T = 3;
int N = 5;
ThresholdEncSecp tsig = new ThresholdEncSecp(T, N);

ThresholdEncSecpParams params = tsig.generate(null);

List<byte[]> partialShares = params.getPrivateShares().subList(0, 3);
Set<Integer> nodes = Set.of(0, 1, 2);

boolean check = ThresholdEncSecp.verify(partialShares.get(0), 1, params.getPublicShares());
System.out.println(check ? "Success" : "Fail");

BigInteger value = new BigInteger("1234567890");
byte[] enc = ThresholdEncSecp.encrypt(params.getPublicKey(), value);

byte[] privateKey = tsig.reconstruct(partialShares, nodes);
BigInteger decoded = ThresholdEncSecp.decrypt(privateKey, enc);

boolean match = Objects.equals(decoded, value);
System.out.println(match ? "Success" : "Fail");
```

Message encryption

```java
int T = 3;
int N = 5;
ThresholdEncSecp tsig = new ThresholdEncSecp(T, N);

ThresholdEncSecpParams params = tsig.generate(null);

List<byte[]> partialShares = params.getPrivateShares().subList(0, 2);
partialShares.add(params.getPrivateShares().get(4));
Set<Integer> nodes = Set.of(0, 1, 4);

boolean check = ThresholdEncSecp.verify(partialShares.get(0), 1, params.getPublicShares());
System.out.println(check ? "Success" : "Fail");

String value = "test message to be decrypted by t out of n recipients";
byte[] enc = ThresholdEncSecp.encrypt(params.getPublicKey(), value);

byte[] privateKey = tsig.reconstruct(partialShares, nodes);
String decoded = ThresholdEncSecp.decryptString(privateKey, enc);

boolean match = Objects.equals(decoded, value);
System.out.println(match ? "Success" : "Fail");
```

#### Weavechain

Read more about Weavechain at [https://docs.weavechain.com](https://docs.weavechain.com)