package com.tversu.aidavydenko.utils;

import java.util.Map;

public class SecretPart {
    private Integer point;
    private Integer value;
    private Integer p;

    public SecretPart() {
    }

    public SecretPart(Integer point, Integer value, Integer p) {
        this.point = point;
        this.value = value;
        this.p = p;
    }
    public SecretPart(Map.Entry<Integer, Integer> entry, Integer p) {
        this.point = entry.getKey();
        this.value = entry.getValue();
        this.p = p;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getP() {
        return p;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SecretPart)) return false;

        SecretPart that = (SecretPart) o;

        if (!getPoint().equals(that.getPoint())) return false;
        if (!getValue().equals(that.getValue())) return false;
        return getP().equals(that.getP());
    }

    @Override
    public int hashCode() {
        int result = getPoint().hashCode();
        result = 31 * result + getValue().hashCode();
        result = 31 * result + getP().hashCode();
        return result;
    }
}
