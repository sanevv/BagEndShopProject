package com.github.semiprojectshop.repository.sihu.product.wish;

import com.github.semiprojectshop.repository.sihu.product.Product;
import com.github.semiprojectshop.repository.sihu.user.MyUser;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Wish {
    @EmbeddedId
    private WishPk wishPk;

    private LocalDateTime createdAt;

    public static Wish of(MyUser user, Product product){
        Wish wish = new Wish();
        wish.wishPk = WishPk.of(product, user);
        wish.createdAt = LocalDateTime.now();
        return wish;
    }

}
