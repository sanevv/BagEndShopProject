package com.github.mymvcspring.web.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoinUpdateRequest {
    private String userId;
    private long coinAmount;
    private long pointAmount;

}
