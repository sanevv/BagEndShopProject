package com.github.semiprojectshop.repository.kyeongsoo.model;

import com.github.semiprojectshop.repository.kyeongsoo.productDomain.ProductVO;

import java.sql.SQLException;

public interface DaoCustom {
    ProductVO callTheSelectedValue(ProductVO pvo) throws SQLException;
}
