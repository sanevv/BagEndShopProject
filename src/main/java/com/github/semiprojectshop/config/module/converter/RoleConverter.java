package com.github.semiprojectshop.config.module.converter;

import com.github.semiprojectshop.repository.sihu.user.RolesEnum;

public class RoleConverter extends MyConverter<RolesEnum> {
    public RoleConverter() {
        super(RolesEnum.class);
    }
}
