package edu.example.coffeeproject.service;

import edu.example.coffeeproject.dto.PageRequestDTO;
import edu.example.coffeeproject.dto.ProductDTO;
import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.exception.ProductException;
import edu.example.coffeeproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ProductService {
    private final ProductRepository productRepository;

    public ProductDTO register(ProductDTO productDTO) {
        try {
            Product product = productDTO.toEntity();
            productRepository.save(product);
            return new ProductDTO(product);
        } catch (Exception e) {               //상품 등록 시 예외가 발생한 경우
            log.error("--- " + e.getMessage());       //에러 로그로 발생 예외의 메시지를 기록하고
            throw ProductException.NOT_REGISTERED.get();  //예외 메시지를 Product NOT Registered로 지정하여 ProductTaskException 발생시키기
        }

    }

    public ProductDTO read(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(ProductException.NOT_REGISTERED::get);
        return new ProductDTO(product);
    }

    public Page<ProductDTO> getList(PageRequestDTO pageRequestDTO) {//상품 목록
        try {
            Sort sort = Sort.by("productId").descending();
            Pageable pageable = pageRequestDTO.getPageable(sort);
            return productRepository.list(pageable);

        }catch (Exception e) {
            log.error("--- " + e.getMessage());
            throw ProductException.NOT_FETCHED.get();  //예외가 발생한 경우 Product NOT_FETCHED 메시지로 예외 발생시키기
        }
    }

    public ProductDTO modify(ProductDTO productDTO) {
        Optional<Product> foundproduct = productRepository.findById(productDTO.getProductId());  //수정하려는 상품을 데이터베이스에서 조회해서
        Product modifyProduct = foundproduct.orElseThrow(ProductException.NOT_FOUND::get);  //해당 상품이 없는 경우 Product NOT_FOUND 예외 발생시키기

        try {
            modifyProduct.changeProductName(productDTO.getProductName());  //상품 이름, 가격, 설명 수정
            modifyProduct.changePrice(productDTO.getPrice());
            modifyProduct.changeDescription(productDTO.getDescription());

            return new ProductDTO(modifyProduct);  //변경된 상품을 반환
        } catch (Exception e) {
            log.error("--- " + e.getMessage());
            throw ProductException.NOT_MODIFIED.get();  //예외가 발생한 경우 Product NOT Modified 메시지로 예외 발생시키기
        }
    }
    public void remove(Long pno) {
        Optional<Product> foundproduct = productRepository.findById(pno);
        Product product = foundproduct.orElseThrow(ProductException.NOT_FOUND::get);

        try {
            productRepository.delete(product);    //productId에 해당하는 데이터 삭제 메서드 호출
        } catch(Exception e) {
            log.error("--- " + e.getMessage());
            throw ProductException.NOT_REMOVED.get();
        }
    }

}

