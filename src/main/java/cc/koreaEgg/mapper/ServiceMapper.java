package cc.koreaEgg.mapper;

import cc.koreaEgg.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ServiceMapper {
    List<Area> selectAreaList();
    List<PriceInfo> selectAllPriceInfo();
    List<PriceInfo> selectPriceInfoByAreaId(@Param("cri") Criteria cri, @Param("areaId") Integer areaId);
    int selectCountPriceInfoByAreaId(@Param("cri") Criteria cri, @Param("areaId") Integer areaId);
    void createPriceInfo(PriceInfo priceInfo);
    void deletePriceInfo(@Param("id") long id);

    void createBoardMessage(BoardMessage boardMessage);
    void updateBoardMessage(BoardMessage boardMessage);
    int selectBoardMessageListCount(@Param("boardId") Integer boardId, @Param("status") Integer status, @Param("searchText") String searchText);
    List<BoardMessage> selectBoardMessageList(@Param("boardId") Integer boardId, @Param("status") Integer status, @Param("searchText") String searchText, @Param("offset") Integer offset, @Param("limit") Integer limit);
    BoardMessage selectBoardMessage(long id);

    /* TODO AN 만들어줭*/
    void updatePriceInfo(PriceInfo info);
}
