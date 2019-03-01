package cc.koreaEgg.entity;

public enum Role {

    USER("일반"),
    STORE("소매점"),
    RETAILER("유통인"),
    WHOLESALER("도매유통인"),
    PARTNER("파트너"),
    AGENT("대리점"),
    ADMIN("관리자");

    private String roleName;

    Role(String str){
        this.roleName = str;
    }

    public String getRoleName(){ return roleName;}
    @Override
    public String toString() { return "ROLE_" + this.name();  }

}