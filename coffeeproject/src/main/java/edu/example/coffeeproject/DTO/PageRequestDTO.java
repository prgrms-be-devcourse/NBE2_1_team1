package edu.example.coffeeproject.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageRequestDTO {

    @Builder.Default
    @Min(1)
    private int page = 1;   // 페이지 번호 -> 첫번째 페이지 0부터 시작

    @Builder.Default
    @Min(5)
    @Max(50)
    private int size = 5;  // 한 페이지 게시물 수 -> 5 미만이면 5으로 지정

    // 페이지번호, 페이지 게시물 수, 정렬 순서를 Pageable 객체로 반환
    public Pageable getPageable(Sort sort) {
        int pageNum = page < 0 ? 1 : page - 1; // 페이지는 0부터 시작 -> "요청 값 -1"
        int sizeNum = size <= 5 ? 5 : size;
        return PageRequest.of(pageNum, sizeNum, sort.ascending());
    }

}
