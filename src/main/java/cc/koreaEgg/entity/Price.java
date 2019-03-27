package cc.koreaEgg.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Alias("price")
public class Price {

    private Long id;
    private Long productId;
    private int cost;
    private int consumer;
    private int store;
    private int retail;
    private int wholesale;
    private int partner;
    private int agent;
    private Timestamp regDate;

    public Price(){

    }

    public Price(Product pd){
        this.productId = pd.getId();
        this.cost = pd.getCost();
        this.consumer = pd.getConsumer();
        this.store = pd.getStore();
        this.retail = pd.getRetail();
        this.wholesale = pd.getWholesale();
        this.partner = pd.getPartner();
        this.agent = pd.getAgent();
    }


}
