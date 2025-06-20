package com.github.semiprojectshop.repository.kyeongsoo.model;

import com.github.semiprojectshop.repository.kyeongsoo.domain.ProductVO;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

public interface DaoCustom {
    ProductVO callTheSelectedValue(ProductVO pvo) throws SQLException;
}
