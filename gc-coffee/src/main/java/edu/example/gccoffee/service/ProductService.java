package edu.example.gccoffee.service;

import edu.example.gccoffee.dto.PageRequestDTO;
import edu.example.gccoffee.dto.ProductDTO;
import edu.example.gccoffee.entity.Category;
import edu.example.gccoffee.entity.Product;
import edu.example.gccoffee.exception.ProductException;
import edu.example.gccoffee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ProductService {

    private final ProductRepository productRepository;

    // 새로운 제품 생성
    public ProductDTO create(ProductDTO productDTO) {
        try {
            Product product = productDTO.toEntity();
            productRepository.save(product);
            return new ProductDTO(product);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw ProductException.PRODUCT_NOT_REGISTERED.getProductTaskException();
        }
    }

    // 기존 제품 수정
    public ProductDTO update(ProductDTO productDTO) {
        Optional<Product> foundProduct = productRepository.findById(productDTO.getProductId());
        Product product = foundProduct.orElseThrow(ProductException.PRODUCT_NOT_FOUND::getProductTaskException);

        try {
            product.changeProductName(productDTO.getProductName());
            product.changeCategory(Category.valueOf(productDTO.getCategory()));
            product.changePrice(productDTO.getPrice());
            product.changeDescription(productDTO.getDescription());
            return new ProductDTO(product);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw ProductException.PRODUCT_NOT_MODIFIED.getProductTaskException();
        }
    }

    // 제품 삭제
    public void delete(Long productId) {
        Optional<Product> foundProduct = productRepository.findById(productId);
        Product product = foundProduct.orElseThrow(ProductException.PRODUCT_NOT_FOUND::getProductTaskException);
        try {
            productRepository.deleteById(productId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw ProductException.PRODUCT_NOT_REMOVED.getProductTaskException();
        }
    }

    // 페이지 별 조회
    public Page<ProductDTO> getPage(PageRequestDTO pageRequestDTO) {
        try {
            Sort sort = Sort.by("productId").descending();
            Pageable pageable = pageRequestDTO.toPageable(sort);
            return productRepository.getPage(pageable);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw ProductException.PRODUCT_NOT_FOUND.getProductTaskException();
        }
    }

    // 모든 제품 조회
    public List<ProductDTO> getList() {
        return productRepository.findAll().stream().map(ProductDTO::new).collect(Collectors.toList());
    }

    public ProductDTO read(Long productId) {
        return new ProductDTO(productRepository.findById(productId).orElseThrow(ProductException.PRODUCT_NOT_FOUND::getProductTaskException));
    }
}
