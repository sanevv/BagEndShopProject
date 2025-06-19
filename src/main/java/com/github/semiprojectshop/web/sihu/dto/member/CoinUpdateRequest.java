package com.github.semiprojectshop.web.sihu.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoinUpdateRequest {
    private String userId;
    private long coinAmount;
    private long pointAmount;

}
