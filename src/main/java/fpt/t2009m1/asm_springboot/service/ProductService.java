package fpt.t2009m1.asm_springboot.service;

import fpt.t2009m1.asm_springboot.entity.Product;
import fpt.t2009m1.asm_springboot.entity.myenum.ProductStatus;
import fpt.t2009m1.asm_springboot.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Page<Product> findAllByActive(int page, int limit){
        return productRepository.findAllByStatusEquals(ProductStatus.ACTIVE, PageRequest.of(page, limit));
    }

    public Optional<Product> findById(String id){
        return productRepository.findById(id);
    }

    public Product save(Product product){
        return productRepository.save(product);
    }

    public void deleteById(String id){
        productRepository.deleteById(id);
    }

    public Page<Product> findAll(int page, int limit) {
        return productRepository.findAll(PageRequest.of(page, limit));
    }

    public Page<Product> searchByName(String search, int page, int limit){
        return productRepository.findAllByNameContains(search, PageRequest.of(page, limit));
    }

    public Page<Product> searchByPrice(BigDecimal price, int page, int limit){
        return productRepository.findAllByPriceEquals(price, PageRequest.of(page, limit));
    }

    public Page<Product> searchByCategoryName(String search, int page, int limit){
        return productRepository.findAllByCategory_Name(search, PageRequest.of(page, limit));
    }

    public Page<Product> searchByPriceBetween(BigDecimal min, BigDecimal max, int page, int limit){
        return productRepository.findAllByPriceBetween(min, max, PageRequest.of(page, limit));
    }
}
