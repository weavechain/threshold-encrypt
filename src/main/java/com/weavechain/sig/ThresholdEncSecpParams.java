package com.weavechain.sig;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ThresholdEncSecpParams {

    private final byte[] privateKey;

    private final byte[] publicKey;

    private final List<byte[]> privateShares;

    private final List<byte[]> publicShares;
}