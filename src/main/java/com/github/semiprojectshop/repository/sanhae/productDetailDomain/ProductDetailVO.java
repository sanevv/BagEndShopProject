package com.github.semiprojectshop.repository.sanhae.productDetailDomain;

import com.github.semiprojectshop.config.DiscountConstants;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor // 전체필드를 포함한 생성자
@NoArgsConstructor // 기본생성자
public class ProductDetailVO {
    private int productId;      // 상품번호
    private int userId;         // 사용자번호
    private String productName; // 상품이름
    private String productInfo; // 상품정보
    private String productContents; // 상품소개
    private int price; // 상품가격
    private int stock; // 상품재고
    private String productSize; // 상품규격
    private String matter;      // 상품재질
    private String createdAt;   // 상품등록일

    // product_image 테이블 조인해서 사용할거임
    private String productImagePath; // 대표이미지 경로

    // public static final 로 선언되어 있어서 객체 생성 없이 클래스명.상수명 으로 접근하는거야
    // 할인율 %
    public long getDiscountRate() {
        return Math.round(DiscountConstants.DISCOUNT_RATE * 100);
    }
    // 정가에서 할인율 만큼 계산한 가격 : 실제 구매가격
    public double getDiscountedPrice() {
        return Math.floor(price * (1 - DiscountConstants.DISCOUNT_RATE));
    }

    // @Getter(AccessLevel.NONE) : Getter 메서드를 생성하지 않도록 막는 역할 바로 밑에만 적용 됨
    @Getter(AccessLevel.NONE)
    private int categoryId;       // 카테고리 번호
    private String productStatus; // 상품 상태 : 판매중, 품절, 숨김
}
