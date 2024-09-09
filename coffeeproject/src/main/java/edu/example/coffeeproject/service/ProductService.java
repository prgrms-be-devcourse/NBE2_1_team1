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

//    public Page<ProductDTO> getList(PageRequestDTO pageRequestDTO) {//상품 목록
//        try {
//            Sort sort = Sort.by("pno").descending();
//            Pageable pageable = pageRequestDTO.getPageable(sort);
//            Page<Product> productPage =  productRepository.findAll(pageable);
//
//        }catch (Exception e) {
//            log.error("--- " + e.getMessage());
//            throw ProductException.NOT_FETCHED.get();  //예외가 발생한 경우 Product NOT_FETCHED 메시지로 예외 발생시키기
//        }
//    }
}

