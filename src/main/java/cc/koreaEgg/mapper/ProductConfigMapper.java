package cc.koreaEgg.mapper;

import cc.koreaEgg.entity.Product;

import java.util.List;

public interface ProductConfigMapper {

	List<Product> readFeaturedProducts();

	Product readProductById(Long productId);

}
