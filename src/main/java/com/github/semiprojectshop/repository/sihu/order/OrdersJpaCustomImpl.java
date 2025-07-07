package com.github.semiprojectshop.repository.sihu.order;

import com.github.semiprojectshop.web.sihu.dto.admin.Months3UsageAmountResponse;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class OrdersJpaCustomImpl implements OrdersJpaCustom {
    private final JPAQueryFactory queryFactory;

    //3달간 월간 판매금액 조회
    @Override
    public List<Months3UsageAmountResponse> find3MonthsUsageAmount() {
        // QueryDSL을 사용하여 3달간의 월간 판매금액을 조회하는 로직을 작성합니다.
        LocalDate localDate = LocalDate.now();
        LocalDateTime startLocal = localDate.minusMonths(3).withDayOfMonth(1).atStartOfDay();
        LocalDateTime endLocal = localDate.withDayOfMonth(1).minusDays(1).atTime(23, 59, 59);

        return queryFactory.select(Projections.fields(Months3UsageAmountResponse.class,
                QOrders.orders1.createdAt.month().as("month"),
                QOrdersProduct.ordersProduct.atPrice.sumLong().as("usageAmount")
                        ))
                .from(QOrders.orders1)
                .join(QOrdersProduct.ordersProduct).on(QOrders.orders1.eq(QOrdersProduct.ordersProduct.order))

                .where(
                        QOrders.orders1.createdAt.between(
                                startLocal, endLocal
                        )
                )
                .groupBy(QOrders.orders1.createdAt.month())
                .orderBy(QOrders.orders1.createdAt.month().asc())
                .fetch();


    }
}
