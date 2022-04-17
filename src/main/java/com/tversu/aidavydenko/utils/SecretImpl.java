package com.tversu.aidavydenko.utils;

public class SecretImpl implements Secret{
    private Integer secret;
    private Integer partsCount;
    private Integer p;

    public SecretImpl(SecretImpl readSecretImpl) {
        this.setP(readSecretImpl.getP());
        this.setPartsCount(readSecretImpl.partsCount);
        this.setSecret(readSecretImpl.secret);
    }

    @Override
    public String toString() {
        return "Secret{" +
                "secret=" + secret +
                ", partsCount=" + partsCount +
                ", p=" + p +
                '}';
    }

    public Integer getSecret() {
        return secret;
    }

    public void setSecret(Integer secret) {
        this.secret = secret;
    }

    public Integer getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(Integer partsCount) {
        this.partsCount = partsCount;
    }

    public Integer getP() {
        return p;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public SecretImpl() {
    }

    public SecretImpl(Integer secret, Integer partsCount, Integer p) {
        this.secret = secret;
        this.partsCount = partsCount;
        this.p = p;
    }
}
