package com.github.semiprojectshop.web.sihu.dto.product;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
public class ProductCreateRequest {
    private long categoryId;
    private String productName;
    private long stock;
    private long price;
    private MultipartFile productContents;
    private MultipartFile mainImage;
    private List<MultipartFile> files;
    private String productInfo;
    private String productSize;
    private String matter;

    public static ProductCreateRequest of(long categoryId, String productName, long stock, long price, MultipartFile productContents, MultipartFile mainImage, List<MultipartFile> files, String ProductInfo, String productSize, String matter) {
        ProductCreateRequest request = new ProductCreateRequest();
        request.categoryId = categoryId;
        request.productName = productName;
        request.stock = stock;
        request.price = price;
        request.productContents = productContents;
        request.mainImage = mainImage;
        request.files = files;
        request.productInfo = ProductInfo;
        request.productSize = productSize;
        request.matter = matter;
        return request;
    }
}
