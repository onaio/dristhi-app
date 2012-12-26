package org.ei.drishti.view.contract;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class CoupleDetails {
    private String wifeName;
    private String husbandName;
    private final String ecNumber;
    private final boolean isInArea;
    private String caste;
    private String economicStatus;

    public CoupleDetails(String wifeName, String husbandName, String ecNumber, boolean outOfArea) {
        this.wifeName = wifeName;
        this.husbandName = husbandName;
        this.ecNumber = ecNumber;
        this.isInArea = !outOfArea;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public CoupleDetails withCaste(String caste) {
        this.caste = caste;
        return this;
    }

    public CoupleDetails withEconomicStatus(String economicStatus) {
        this.economicStatus = economicStatus;
        return this;
    }
}
