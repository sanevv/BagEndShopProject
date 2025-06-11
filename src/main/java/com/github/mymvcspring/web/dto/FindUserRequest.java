package com.github.mymvcspring.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindUserRequest {
    private String name;
    private String email;
    private String userId;
}
