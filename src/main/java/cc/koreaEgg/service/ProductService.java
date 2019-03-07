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
        long productId = productMapper.createProduct(product);
        product.getPrice().setProductId(productId);
        productMapper.createPrice(product.getPrice());
    }

   /* public List<Product> selectProductList(){
       return productMapper.selectProductList();
    }*/

    public Product selectProduct(long id){
        return productMapper.selectProduct(id);
    }

}