package com.github.semiprojectshop.repository.sihu.user;

import com.github.semiprojectshop.config.module.converter.RoleConverter;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
//@Table(name = "roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    @Convert(converter = RoleConverter.class)
    private RoleName name;

    public enum RoleName {
        ROLE_USER,
        ROLE_ADMIN
    }

    public static Roles fromOnlyName(RoleName roleName) {
        return switch (roleName) {
            case ROLE_USER -> {
                Roles roles = new Roles();
                roles.roleId = 2L;
                yield roles;

            }
            case ROLE_ADMIN -> {
              Roles roles = new Roles();
                roles.roleId = 1L;
                yield roles;
            }
        };

    }



}
