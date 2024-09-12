package edu.example.coffeeproject.service;

import edu.example.coffeeproject.DTO.request.OrderRequestDTO;
import edu.example.coffeeproject.DTO.response.OrderResponseDTO;
import edu.example.coffeeproject.entity.Order;
import edu.example.coffeeproject.entity.OrderItem;
import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.exception.OrderException;
import edu.example.coffeeproject.exception.ProductException;
import edu.example.coffeeproject.mapper.OrderItemMapper;
import edu.example.coffeeproject.mapper.OrderMapper;
import edu.example.coffeeproject.repository.OrderRepository;
import edu.example.coffeeproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class OrderService { // 주석 개판임;; 정리해야함, 근데 천천히 읽으면 이해는 잘됨

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    // 주문 등록
    public OrderResponseDTO addOrder(OrderRequestDTO orderRequestDTO) {

        // 1. OrderItem 목록을 생성: 각 OrderItemRequestDTO를 기반으로 OrderItem 엔티티로 변환
        List<OrderItem> orderItems = orderRequestDTO.getOrderItems().stream()
                .map(orderItemRequestDTO -> {
                    // 2. Product를 조회: productId로 Product 엔티티 조회
                    Optional<Product> optionalProduct = productRepository.findById(orderItemRequestDTO.getProductId());
                    Product foundProduct = optionalProduct.orElseThrow(ProductException.NOT_FOUND::get);
                    // 여기서 .stream을 하는 이유는 .map 메서드를 사용하기 위해서?(-> stream은 리스트(컬렉션)에만 사용가능?

                    // 3. OrderItem 엔티티로 변환: Product와 Order(아직 생성되지 않음)를 사용
                    return OrderItemMapper.toOrderItemEntity(orderItemRequestDTO, foundProduct, null); // Order는 밑에서 설정
                }).collect(Collectors.toList());
        // 오더 리퀘스트 dto안에 있는 오더아이템리퀘스트 리스트 빼와서 리스트안에 오더아이템리퀘스트 한개식 돌면서,
        // 가지고 잇는 프로덕트 아이디로 프로덕트 조회하고, 오더아이템리퀘스트 + 프로덕트 + 오더(아직null) 사용해서 오더아이템 엔티티 "1개"만듬
        // 이걸 "오더아이템리퀘스트 리스트"의 모든 오더아이템리퀘스트에 적용 -> 엔티티 여러개 -> tolist해서 반환

        // 4. Order 엔티티를 생성: 요청 받은 데이터와 OrderItem 목록을 포함하여 Order 생성
        Order order = OrderMapper.toOrderEntity(orderRequestDTO, orderItems);
        // 위에 만들어둔 오더아아템 엔티티 리스트 + 전달받은 오더리퀘스트 dto로 오더 엔티티 저장
        // -> 아직 오더아이템이 어떤 오더에 들어갈지 값을 넣어주지 않았음

        // 5. OrderItem에 Order를 설정: OrderItem들에 Order를 설정해줌
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        // 오더를 만들었으니 아까 지정해주지 못했던 오더아이템 엔티티들에게 전부 위에서 지정한 오더를 넣어줌
        // -> 이제 오더아이템은 받은 해당 오더를 참조함
        // 여기서 오더아이템 엔티티에 setter를 써야하기 때문에(setOrder), 해당 엔티티에 @Setter 추가 

        // 6. Order 저장: 생성된 Order와 연관된 OrderItem을 데이터베이스에 저장
        Order savedOrder = orderRepository.save(order);
        // 완성된 오더(+오더아이템리스트)를 기본 JPA메서드로 저장

        // 7. 저장된 Order를 DTO로 변환: 저장된 결과를 클라이언트에게 반환하기 위해 DTO로 변환
        return OrderMapper.toOrderResponseDTO(savedOrder);
    }

    // email 해당하는 주문 전체 조회
    public List<OrderResponseDTO> readOrder(String email) {
        Optional<List<Order>> optionalOrderList = orderRepository.findByEmailList(email);
        List<Order> foundOrderList = optionalOrderList.orElseThrow(OrderException.NOT_FOUND::get);
        
        // List<Order>를 List<OrderResponseDTO>로 변환
        return foundOrderList.stream()
                .map(OrderMapper::toOrderResponseDTO) // 각 Order 객체를 OrderResponseDTO로 변환
                .collect(Collectors.toList()); // 리스트로 변환
        // OrderMapper.toOrderResponseDTO -> 이 메서드는 Order 엔티티 1개만 받을 수 있으니,
        // 리스트를 .stream을 써서 map으로 하나하나씩 돌면서 toOrderResponseDTO 메서드 적용하고,
        // 그렇게 만들어진 OrderResponseDTO 들을 리스트화 해서 반환(to.List)
    }

    //// orderStatus, totalPrice 안 들어감, orderItems quantity 0 들어가짐 ////
    //// + 수량 변경, 수량 0이 되면 삭제하는 기능 ////
    //// 기획서에서 가장 중요한 것은 벤치마킹 -> 만드는 이유(경쟁성을 가질 수 있는 부분, 기존 시스템의 개선을 어필하면 좋음) ////
    // 주문 변경 -> 변경하고 싶은 부분만 주고 알아서 기존것은 유지하는 그런 거 아니고,
    // 기존 값으로 전부다 주고 이후에 변경할 곳만 변경하는 느낌!!
    public OrderResponseDTO updateOrder(Long orderId, OrderRequestDTO orderRequestDTO) {
        // 여기서 받는 매개변수 email로 바꿔보기 -> 아마도 findbyemail 쿼리 레포지토리에 추가해야할듯!!

        // 1. order 항목 수정(이메일,주소,우편번호)
        // 2. orderItem 항목 수정(상품 번호,수량)
        // ->  일단 orderItems 전체 삭제하고 그대로 오는 거 다시 받으면 됨(등록 + 삭제와 비슷)

        // 1. order 항목 수정(이메일, 주소, 우편번호)
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Order updatedOrder = optionalOrder.orElseThrow(OrderException.NOT_FOUND::get);
        updatedOrder.changeEmail(orderRequestDTO.getEmail());
        updatedOrder.changeAddress(orderRequestDTO.getAddress());
        updatedOrder.changePostcode(orderRequestDTO.getPostcode());

        // 2. 기존의 OrderItem 목록 삭제
        updatedOrder.getOrderItems().clear();

        // 3. 클라이언트에서 요청받은 데이터로 새로운 OrderItem 목록 생성
        List<OrderItem> updatedOrderItems = orderRequestDTO.getOrderItems().stream()
                .map(orderItemRequestDTO -> {
                    Optional<Product> optionalProduct = productRepository.findById(orderItemRequestDTO.getProductId());
                    Product foundProduct = optionalProduct.orElseThrow(ProductException.NOT_FOUND::get);

                    // OrderItem 엔티티로 변환 (updatedOrder와 Product 같이 지정)
                    // 등록에서의 이 단계에서는 Order가 null이였는데, 여기서는
                    // -> 위에서 Order의 필드를 먼저 수정하면서 수정된 updatedOrder를 선언해 놓아,
                    // 바로 지정해줄 수 있기 때문
                    return OrderItemMapper.toOrderItemEntity(orderItemRequestDTO, foundProduct, updatedOrder);
                }).collect(Collectors.toList());

        // 4. 새로 생성된 updatedOrderItems을(List) Order에 지정
        // -> 수정된 orderItem 엔티티들이 해당 Order 엔티티를 참조
        updatedOrder.getOrderItems().addAll(updatedOrderItems);

        // 5. 수정된 Order를 저장
        Order updatedOrderWithOrderItems = orderRepository.save(updatedOrder);

        return OrderMapper.toOrderResponseDTO(updatedOrderWithOrderItems);
    }

    // 주문 삭제
    public void deleteOrder(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Order foundOrder =optionalOrder.orElseThrow(OrderException.NOT_FOUND::get);
        orderRepository.delete(foundOrder);
    }


    // 전체 주문 조회
    public List<OrderResponseDTO> readAllOrders() {
        return orderRepository.findAll().stream()
                              .map(OrderMapper::toOrderResponseDTO)
                              .collect(Collectors.toList());
    }

    // 주문 페이징

}


//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(OrderException.NOT_FOUND::get);
//
//        order.changeEmail(orderRequestDTO.getEmail());
//        order.changeAddress(orderRequestDTO.getAddress());
//        order.changePostcode(orderRequestDTO.getPostcode());
//
//        // 기존 orderItems 삭제
//        order.getOrderItems().clear();
//
//        List<OrderItem> orderItems = orderRequestDTO.getOrderItems().stream()
//                .map(orderItemRequestDTO -> {
//                    Optional<Product> product = productRepository.findById(orderItemRequestDTO.getProductId());
//                    Product getProduct = product.get();
//
//                    return OrderItemMapper.toOrderItemEntity(orderItemRequestDTO, getProduct);
//                }).collect(Collectors.toList());
//
//        // 변경된 orderItems 추가
//        order.getOrderItems().addAll(orderItems);
//
//        Order updatedOrder = orderRepository.save(order);
//        return OrderMapper.toOrderResponseDTO(updatedOrder);