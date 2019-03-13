package cc.koreaEgg.entity;

/**
 * Created by mink721 on 2019-03-06.
 */
public enum CODE {


    FALSE(0),
    TRUE(1),
    ACTIVE(11),
    DELETE(99),

    START(21),
    END(29);


    private int code;

    CODE(int code){
        this.code = code;
    }

    public int getCode(){ return code;}

}
