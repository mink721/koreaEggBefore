package cc.koreaEgg.entity;

public enum Role {

    CONSUMER,
    STORE,
    RETAILER,
    WHOLESALER,
    PARTNER,
    AGENT,
    ADMIN;

    @Override
    public String toString() { return "ROLE_" + this.toString();  }
}
