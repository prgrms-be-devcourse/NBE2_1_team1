package edu.example.coffeeproject.service;

import edu.example.coffeeproject.DTO.request.ProductRequestDTO;
import edu.example.coffeeproject.DTO.response.ProductResponseDTO;
import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.mapper.ProductMapper;
import edu.example.coffeeproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

        // ProductRequestDTO -> Entity
        // Mapper는 static이라서 아무 제약 없이 사용 가능
        Product product = ProductMapper.toProductEntity(productRequestDTO);

        // JPA 기본 메서드 save() -> 실질적으로 DB에 저장
        productRepository.save(product);

        // 실질적으로 DB에 저장된 데이터(product)를 매개변수로 받아,
        // Entity -> oProductResponseDTO
        return ProductMapper.toProductResponseDTO(product);

    }

    // 상품 조회
    public ProductResponseDTO readProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return ProductMapper.toProductResponseDTO(product.get());
    }

    // 상품 수정
    public ProductResponseDTO modifyProduct(Long productId, ProductRequestDTO productRequestDTO) {

        // 클라이언트에게 전달 받은 productId를 매개변수로
        // JPA 기본 메서드 findById를 사용하여 해당 컬럼을 product에 저장
        Optional<Product> product = productRepository.findById(productId);

        // Optional을 .get()으로 벗겨서 foundProduct에 저장
        // -> 자유로운 Product 객체가 됨(엔티티)
        Product foundProduct = product.get();

        // 요청한 데이터에서(productRequestDTO) get해서
        // product 엔티티의 change 메서드로 실질적인 DB 데이터(foundproduct) 변경
        foundProduct.changeProductName(productRequestDTO.getProductName());
        foundProduct.changeCategory(productRequestDTO.getCategory());
        foundProduct.changePrice(productRequestDTO.getPrice());
        foundProduct.changeDescription(productRequestDTO.getDescription());

        // 요청한 데이터로 수정한 product 엔티티를 productResponseDTO로 변경 후, 반환
        return ProductMapper.toProductResponseDTO(foundProduct);
        }

    // 상품 삭제
    public void removeProduct(Long productId) {

        // restz의 상품 service 참고하면, 예외처리 가능
        productRepository.deleteById(productId);
    }

    // 상품 전체 조회
    public List<ProductResponseDTO> readAllProducts() {

        // JPA 기본 메서드 findAll()로 모든 상품을 조회(매개변수 필요 X)
        List<Product> products = productRepository.findAll();

        // Product 엔티티 리스트 -> ProductResponseDTO 리스트로 변환
        List<ProductResponseDTO> productResponseDTOList =
                products.stream()
                        .map(ProductMapper::toProductResponseDTO)
                        .toList();

        /*  위 코드와 밑 코드는 기능적으로 동일하지만,
            밑 코드가 가독성이 떨어짐
        List<ProductResponseDTO> productResponseDTOList =
                productRepository.findAll()
                                .stream()
                                .map(ProductMapper::toProductResponseDTO)
                                .toList();

         */

        return productResponseDTOList;
    }

}


    // 상품 페이지(페이징)
//    public Page<ProductResponseDTO> getList(Pageable pageable) {//상품 목록
//        try {
//            Sort sort = Sort.by("productId").descending();
//            Pageable pageable = pageRequestDTO.getPageable(sort);
//            return productRepository.list(pageable);
//
//        }catch (Exception e) {
//            log.error("--- " + e.getMessage());
//            throw ProductException.NOT_FETCHED.get();  //예외가 발생한 경우 Product NOT_FETCHED 메시지로 예외 발생시키기
//        }
//    }


