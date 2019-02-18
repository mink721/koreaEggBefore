package cc.koreaEgg.mapper;

import cc.koreaEgg.entity.Area;
import cc.koreaEgg.entity.BoardMessage;
import cc.koreaEgg.entity.PriceInfo;
import cc.koreaEgg.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ServiceMapper {
    List<Area> selectAreaList();
    List<PriceInfo> selectAllPriceInfo();
    List<PriceInfo> selectPriceInfoByAreaId(@Param("areaId") Integer areaId, @Param("offset") Integer offset, @Param("limit") Integer limit);
    void createPriceInfo(PriceInfo priceInfo);
    void deletePriceInfo(long id);

    void createBoardMessage(BoardMessage boardMessage);
    void updateBoardMessage(BoardMessage boardMessage);
    int selectBoardMessageListCount(@Param("boardId") Integer boardId, @Param("status") Integer status, @Param("searchText") String searchText);
    List<BoardMessage> selectBoardMessageList(@Param("boardId") Integer boardId, @Param("status") Integer status, @Param("searchText") String searchText, @Param("offset") Integer offset, @Param("limit") Integer limit);
}
