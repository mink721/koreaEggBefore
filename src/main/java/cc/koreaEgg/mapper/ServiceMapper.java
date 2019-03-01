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
    void createBoardMessage(BoardMessage boardMessage);

    void updateBoardMessage(BoardMessage boardMessage);
    int selectBoardMessageListCount(@Param("boardId") Integer boardId, @Param("status") Integer status, @Param("searchText") String searchText);
    List<BoardMessage> selectBoardMessageList(@Param("boardId") Integer boardId, @Param("status") Integer status, @Param("searchText") String searchText, @Param("cri") Criteria cri);
    BoardMessage selectBoardMessage(long id);
    void deleteBoardMessage(long id);   // status만 99로 업데이트함.

    void createPriceInfo(PriceInfo priceInfo);
    void deletePriceInfo(@Param("id") long id);
    void updatePriceInfo(PriceInfo info);

    // TODO: 2019-03-02
    void createPriceCast(PriceCast cast);
    void updatePriceCast(PriceCast cast);
    void deletePriceCast(long id);
    int selectCountPriceCast();
    List<PriceCast> selectPriceCastList(@Param("cri") Criteria cri);

    void createContactUs(ContactUs contactUs);
    void updateContactUs(ContactUs contactUs);
    int selectCountContactUs(@Param("userId") Long userId);
    List<ContactUs> selectContactUsList(@Param("userId") Long userId, @Param("cri") Criteria cri);
}
