package cc.koreaEgg.service;

import cc.koreaEgg.entity.Product;
import cc.koreaEgg.mapper.ProductConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductConfigService {

	@Autowired
	private ProductConfigMapper productConfigMapper;

	public List<Product> getFeaturedProducts() {
		List<Product> featuredProdList = productConfigMapper
				.readFeaturedProducts();
		return featuredProdList;
	}


	public Product getProductById(Long productId) {
		Product product = productConfigMapper.readProductById(productId);
		return product;
	}

}
