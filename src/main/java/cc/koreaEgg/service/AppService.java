package cc.koreaEgg.service;

import cc.koreaEgg.entity.PriceInfo;
import cc.koreaEgg.mapper.ServiceMapper;
import cc.koreaEgg.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
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

}