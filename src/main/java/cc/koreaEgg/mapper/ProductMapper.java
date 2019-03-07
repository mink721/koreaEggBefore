package cc.koreaEgg.mapper;

import cc.koreaEgg.entity.Price;
import cc.koreaEgg.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {

    List<Product> selectProductList(@Param("areaId") Integer areaId);
    Product selectProduct(@Param("id") long id);
    long createProduct(Product product);
    long createPrice(Price price);
}
