package com.github.semiprojectshop.repository.seungho.model;

import java.sql.SQLException;
import java.util.List;

import com.github.semiprojectshop.repository.seungho.domain.CategoryVO;

public interface CategoryDAO {

	List<CategoryVO> getCategoryInfo() throws SQLException;

}
