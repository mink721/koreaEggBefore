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
    END(29),

    ORDER_BEFORE(31), // 주문접수
    ORDER_DEPOSIT(32), //입금확인
    ORDER_1(33), //구매자확인
    ORDER_2(34), //구매자배송준비중
    ORDER_3(35), //구매자배송완료
    ORDER_4(38), //구입완료
    ORDER_5(39) //주문취소

    ;

    private int code;

    CODE(int code){
        this.code = code;
    }

    public int getCode(){ return code;}

}
