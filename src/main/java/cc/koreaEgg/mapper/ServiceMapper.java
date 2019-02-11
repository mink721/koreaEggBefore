package cc.koreaEgg.mapper;

import cc.koreaEgg.entity.PriceInfo;
import cc.koreaEgg.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ServiceMapper {
    PriceInfo selectAllPriceInfo();
}
