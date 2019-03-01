package cc.koreaEgg.service;

import cc.koreaEgg.entity.Area;
import cc.koreaEgg.entity.BoardMessage;
import cc.koreaEgg.entity.Criteria;
import cc.koreaEgg.entity.PriceInfo;
import cc.koreaEgg.mapper.ServiceMapper;
import cc.koreaEgg.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class AppService {

    @Autowired
    ServiceMapper serviceMapper;

    public void createPriceInfo(PriceInfo priceInfo){
        serviceMapper.createPriceInfo(priceInfo);
    }

    public List<PriceInfo> selectAllPriceInfo(){
       return serviceMapper.selectAllPriceInfo();
    }

    public void createBoardMessage(BoardMessage boardMessage){
         serviceMapper.createBoardMessage(boardMessage);
    }
    public void updateBoardMessage(BoardMessage boardMessage){
         serviceMapper.updateBoardMessage(boardMessage);
    }
    public int selectBoardMessageListCount(Integer boardId, Integer status, String searchText){
        return serviceMapper.selectBoardMessageListCount(boardId, status, searchText);
    }
    public List<BoardMessage> selectBoardMessageList(Integer boardId, Integer status, String searchText, Integer offset, Integer limit){
        return serviceMapper.selectBoardMessageList(boardId, status, searchText, offset, limit);
    }

    public BoardMessage selectBoardMessage(long id){
        return serviceMapper.selectBoardMessage(id);
    }


    public List<Area> selectAreaList() {
        return serviceMapper.selectAreaList();
    }

    public List<PriceInfo> selectPriceInfoByAreaId(Criteria cri, Integer areaId){
        return serviceMapper.selectPriceInfoByAreaId(cri, areaId);
    }

    public void updatePriceInfo(PriceInfo info) { serviceMapper.updatePriceInfo(info);
    }

    public void deletePriceInfo(long infoId) { serviceMapper.deletePriceInfo(infoId);
    }

    public int selectCountPriceInfoByAreaId(Criteria cri, Integer areaId) {
        return serviceMapper.selectCountPriceInfoByAreaId(cri, areaId);
    }
}