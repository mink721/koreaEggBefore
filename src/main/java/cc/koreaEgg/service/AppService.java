package cc.koreaEgg.service;

import cc.koreaEgg.entity.*;
import cc.koreaEgg.mapper.ServiceMapper;
import cc.koreaEgg.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
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
    public List<BoardMessage> selectBoardMessageList(Integer boardId, Integer status, String searchText, Criteria cri){
        return serviceMapper.selectBoardMessageList(boardId, status, searchText, cri);
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

    public int selectCountPriceInfoByAreaId(Integer areaId) {
        return serviceMapper.selectCountPriceInfoByAreaId(areaId);
    }

    public  List<PriceCast> selectPriceCastList(Criteria cri) {
        return serviceMapper.selectPriceCastList(cri);
    }

    public int selectCountPriceCast() {
       return serviceMapper.selectCountPriceCast();
    }

    public void createPriceCast(PriceCast cast) {
        serviceMapper.createPriceCast(cast);
    }

    public void updatePriceCast(PriceCast cast) {
        serviceMapper.updatePriceCast(cast);
    }

    public void deletePriceCast(long castId) {
        serviceMapper.deletePriceCast(castId);
    }

    public void deleteBoardMessage(long id) {
        serviceMapper.deleteBoardMessage(id);
    }

    public List<ContactUs> selectContactUsList(Long userId, Integer answerFlag, Criteria cri) {
        return serviceMapper.selectContactUsList(userId, answerFlag, cri);
    }

    public int selectCountContactUs(Long userId, Integer answerFlag) {
        return serviceMapper.selectCountContactUs(userId, answerFlag);
    }

    public void updateContactUs(ContactUs contact) {
        serviceMapper.updateContactUs(contact);
    }

    public ContactUs selectContactUs(long id) {
        return serviceMapper.selectContactUs(id);
    }

    public List<Board> selectBoardList() {
        return  serviceMapper.selectBoardList();
    }

    public void deleteContactUs(long id) {
        serviceMapper.deleteContactUs(id);
    }

    public void createContactUs(ContactUs cu) {
        serviceMapper.createContactUs(cu);
    }

    public PriceCast selectPriceCast() {
        return serviceMapper.selectPriceCast();
    }

    public void createUploadFile(UploadFile uploadFile){
        serviceMapper.createUploadFile(uploadFile);
    }
}