package com.mcal.apkprotector.data.gson;

import com.google.gson.annotations.SerializedName;

public class KeystoreConfig {
    @SerializedName("zipaligner")
    public boolean zipaligner;

    @SerializedName("keystorePath")
    public String keystorePath;
    @SerializedName("keystorePassword")
    public String keystorePassword;
    @SerializedName("keystoreAlias")
    public String keystoreAlias;
    @SerializedName("certPassword")
    public String certPassword;

    @SerializedName("signatureV1Enabled")
    public boolean signatureV1Enabled;
    @SerializedName("signatureV2Enabled")
    public boolean signatureV2Enabled;
    @SerializedName("signatureV3Enabled")
    public boolean signatureV3Enabled;
    @SerializedName("signatureV4Enabled")
    public boolean signatureV4Enabled;

    public KeystoreConfig(String keystorePath, String keystorePassword, String keystoreAlias, String certPassword, boolean signatureV1Enabled, boolean signatureV2Enabled, boolean signatureV3Enabled, boolean signatureV4Enabled, boolean zipaligner) {
        this.keystorePath = keystorePath;
        this.keystorePassword = keystorePassword;
        this.keystoreAlias = keystoreAlias;
        this.certPassword = certPassword;
        this.signatureV1Enabled = signatureV1Enabled;
        this.signatureV2Enabled = signatureV2Enabled;
        this.signatureV3Enabled = signatureV3Enabled;
        this.signatureV4Enabled = signatureV4Enabled;
        this.zipaligner = zipaligner;
    }
}