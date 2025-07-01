package com.github.semiprojectshop.service.sihu.product;

import com.github.semiprojectshop.repository.sihu.product.wish.WishJpa;
import com.github.semiprojectshop.web.sihu.dto.product.wish.WishResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishService {
    private final WishJpa wishJpa;
    @Transactional(readOnly = true)
    public List<WishResponse> getMyWishList(long userId) {

        return wishJpa.findMyWishListByUserId(userId);
    }
}
