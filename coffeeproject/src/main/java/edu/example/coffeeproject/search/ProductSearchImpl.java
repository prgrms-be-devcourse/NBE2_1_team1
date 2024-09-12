package edu.example.coffeeproject.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import edu.example.coffeeproject.dto.ProductDTO;
import edu.example.coffeeproject.entity.Product;
import edu.example.coffeeproject.entity.QProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {
    public ProductSearchImpl() {super(Product.class);}

    @Override
    public Page<ProductDTO> list(Pageable pageable) {
        QProduct product = QProduct.product;

        JPQLQuery<Product> query = from(product);


        JPQLQuery<ProductDTO> dtoQuery
                = query.select(Projections.bean(
                ProductDTO.class,
                product.productId,
                product.productName,
                product.category,
                product.price,
                product.description));

        getQuerydsl().applyPagination(pageable, dtoQuery);    //페이징
        List<ProductDTO> productList = dtoQuery.fetch();   //쿼리 실행
        long count = dtoQuery.fetchCount();                //레코드 수 조회

        return new PageImpl<>(productList, pageable, count);
    }
}
