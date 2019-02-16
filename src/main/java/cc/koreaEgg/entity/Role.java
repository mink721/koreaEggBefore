package cc.koreaEgg.entity;

public enum Role {

    USER,
    STORE,
    RETAILER,
    WHOLESALER,
    PARTNER,
    AGENT,
    ADMIN;

    private String roleName;

    public void Role(String str){
        this.roleName = str;
    }

    @Override
    public String toString() { return "ROLE_" + this.name();  }

}
