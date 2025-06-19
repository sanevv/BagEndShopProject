package com.github.semiprojectshop.repository.sihu.product;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Enumerated(EnumType.STRING)
    private CategoryName categoryName;

    public enum CategoryName{
        CROSS_BAG,
        BACK_PACK,
        MESSENGER_BAG
    }

    public static Category fromOnlyName(CategoryName categoryName) {
        return switch (categoryName){
            case CROSS_BAG -> {
                Category category = new Category();
                category.categoryId = 3L;
                yield category;
            }
            case BACK_PACK -> {
                Category category = new Category();
                category.categoryId = 2L;
                yield category;
            }
            case MESSENGER_BAG -> {
                Category category = new Category();
                category.categoryId = 1L;
                yield category;
            }
        };
    }

}
