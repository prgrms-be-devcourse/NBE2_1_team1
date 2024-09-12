package edu.example.coffeeproject.service;

import edu.example.coffeeproject.DTO.PageRequestDTO;
import edu.example.coffeeproject.DTO.request.ProductRequestDTO;
import edu.example.coffeeproject.DTO.response.ProductResponseDTO;
import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.exception.ProductException;
import edu.example.coffeeproject.mapper.ProductMapper;
import edu.example.coffeeproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 등록
    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) {

        try {
            // Mapper는 static -> 아무 제약 없이 사용 가능
            // ProductRequestDTO -> Entity
            Product product = ProductMapper.toProductEntity(productRequestDTO);

            // JPA 기본 메서드 save() -> 실질적으로 DB에 저장
            productRepository.save(product);

            // 실질적으로 DB에 저장된 데이터(product)를 매개변수로 받아,
            // Entity -> oProductResponseDTO
            return ProductMapper.toProductResponseDTO(product);
        } catch (Exception e) {
            log.error("---상품 등록 예외 메시지 : " + e.getMessage());
            throw ProductException.NOT_REGISTERED.get();
        }
    }

    // 상품 조회
    public ProductResponseDTO readProduct(Long productId) {

        // 전달받은 productId로 JPA 기본 메서드를 사용하여,
        // 해당 컬럼을 DB에서 데이터 조회한 뒤, optionalProduct에 저장
        Optional<Product> optionalProduct = productRepository.findById(productId);

        // Optional을 벗기기 위해 예외 처리(상품이 존재하지 않음 -> NOT_FOUND)
        Product foundProduct = optionalProduct.orElseThrow(ProductException.NOT_FOUND::get);

        // 완성된 Product 엔티티를(foundProduct) Mapper를 사용하여 DTO로 바꾼 후, 반환
        return ProductMapper.toProductResponseDTO(foundProduct);
    }

    // 상품 수정
    public ProductResponseDTO modifyProduct(Long productId, ProductRequestDTO productRequestDTO) {

        Optional<Product> optionalProduct = productRepository.findById(productId);
        Product foundProduct = optionalProduct.orElseThrow(ProductException.NOT_FOUND::get);

        try {
            // 요청받은 데이터들을(productRequestDTO) get하여,
            // DB에 있던 원래 Product 엔티티의 값들을(foundProduct)
            // 선언해둔 change 메서드를 이용하여, 요청받은 데이터들로 변경
            foundProduct.changeProductName(productRequestDTO.getProductName());
            foundProduct.changeCategory(productRequestDTO.getCategory());
            foundProduct.changePrice(productRequestDTO.getPrice());
            foundProduct.changeDescription(productRequestDTO.getDescription());

            // 요청받은 데이터들로 수정하여,
            // 완성된 Product 엔티티를(foundProduct) Mapper를 사용하여 DTO로 바꾼 후, 반환
            return ProductMapper.toProductResponseDTO(foundProduct);
        } catch (Exception e) {
            log.error("---상품 수정 예외 메시지 : " + e.getMessage());
            throw ProductException.NOT_MODIFIED.get(); // 수정 실패시 예외 발생
        }
    }

    // 상품 삭제
    public void removeProduct(Long productId) {

        Optional<Product> optionalProduct = productRepository.findById(productId);
        Product foundProduct = optionalProduct.orElseThrow(ProductException.NOT_FOUND::get);

        try {
            productRepository.delete(foundProduct);   // 해당 Product 엔티티(foundProduct) 삭제
        } catch (Exception e) {
            log.error("---상품 삭제 예외 메시지 : " + e.getMessage());
            throw ProductException.NOT_REMOVED.get(); // 삭제 실패시 예외 발생
        }
    }

    // 상품 전체 조회
    public List<ProductResponseDTO> readAllProducts() {

        try {
            // JPA 기본 메서드 findAll()로 모든 상품을 조회(매개변수 필요 없음)
            List<Product> products = productRepository.findAll();

            // Product 엔티티 리스트의 각 데이터를 순회하며 DTO로 변환한 뒤(.mao), DTO 리스트로 변환(.toList)
            List<ProductResponseDTO> productResponseDTOList =
                                     products.stream()
                                             .map(ProductMapper::toProductResponseDTO)
                                             .toList();

            return productResponseDTOList;

        /*  위 코드와 밑 코드는 기능적으로 동일하지만,
            밑 코드는 가독성이 떨어짐
        List<ProductResponseDTO> productResponseDTOList =
                productRepository.findAll()
                                .stream()
                                .map(ProductMapper::toProductResponseDTO)
                                .toList();

         */
        } catch (Exception e) {
            log.error("---상품 전체 조회 예외 메시지 : " + e.getMessage());
            throw  ProductException.NOT_FOUND.get(); // 전체 조회 실패시 예외 발생
        }
    }

    // 상품 페이지(페이징)
    public Page<ProductResponseDTO> getPageList(PageRequestDTO pageRequestDTO) {//상품 목록
        
        try {
            // 설명 추가(+search, pageRequest)
            Sort sort = Sort.by("productId").ascending();
            Pageable pageable = pageRequestDTO.getPageable(sort);
            return productRepository.list(pageable);

        } catch (Exception e) {
            log.error("--- " + e.getMessage());
            throw ProductException.NOT_FETCHED.get();
            // NOT_FETCHED -> 상품 목록을 가져오는데 실패했을 때 발생하는 에러
        }
    }

}


