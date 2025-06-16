package com.github.mymvcspring.web.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class CoinUpdateResponse {
    private final long coinAmount;
    private final long pointAmount;
    private final long currentCoin;
    private final long currentPoint;

}
