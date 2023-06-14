## Threshold Encryption using secp256k1

A pure Java implementation of a Threshold Encryption scheme.

This particular threshold encryption is a way of encoding the data by a party, such that it can be decrypted only by multiple participants collaborating.

T out of N participants need to be part of the threshold decryption scheme to be able to retrieve the plaintext.

Part of [Weavechain](https://weavechain.com): The Layer-0 For Data

### Usage

#### Gradle Groovy DSL

```
implementation 'com.weavechain:threshold-encrypt:1.0'
```

#### Gradle Kotlin DSL

```
implementation("com.weavechain:threshold-encrypt:1.0")
```

##### Apache Maven

```xml
<dependency>
  <groupId>com.weavechain</groupId>
  <artifactId>threshold-encrypt</artifactId>
  <version>1.0</version>
</dependency>
```

#### Sample

```java
int T = 3;
int N = 5;
ThresholdEncSecp tsig = new ThresholdEncSecp(T, N);

ThresholdEncSecpParams params = tsig.generate(null);

List<byte[]> partialShares = params.getPrivateShares().subList(0, 3);

boolean check = ThresholdEncSecp.verify(partialShares.get(0), 1, params.getPublicShares());

String value = "test message to be decrypted by t out of n recipients";
byte[] enc = ThresholdEncSecp.encrypt(params.getPublicKey(), value);

byte[] privateKey = tsig.reconstruct(partialShares);
String decoded = ThresholdEncSecp.decryptString(privateKey, enc);

boolean match = Objects.equals(decoded, value);
System.out.println(match ? "Success" : "Fail");
```

#### Weavechain

Read more about Weavechain at [https://docs.weavechain.com](https://docs.weavechain.com)