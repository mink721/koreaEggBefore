package cc.koreaEgg.service;

import cc.koreaEgg.entity.*;
import cc.koreaEgg.mapper.ProductMapper;
import cc.koreaEgg.mapper.ServiceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class ProductService {

    @Autowired
    ProductMapper productMapper;

    public void createProduct(Product product){
       productMapper.createProduct(product);
        //product.getPrice().setProductId(productId);
       // productMapper.createPrice(product.getPrice());
    }

    public Product selectProduct(long id, Role role){
        return productMapper.selectProduct(id, role);
    }

    public void createShop(Shop shop){ productMapper.createShop(shop);}

    public List<Shop> selectShopList(Criteria cri, double lon, double lat, String role){ return productMapper.selectShopList(cri, lon, lat, role);}

    public List<Product> selectProductList(Criteria cri, Role role, Long shopId, Integer size) {
        return productMapper.selectProductList(cri, role, shopId, size);
    }
}