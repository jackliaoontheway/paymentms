package com.noone.paymentms.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QNooneOrder is a Querydsl query type for NooneOrder
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QNooneOrder extends EntityPathBase<NooneOrder> {

    private static final long serialVersionUID = -1171299589L;

    public static final QNooneOrder nooneOrder = new QNooneOrder("nooneOrder");

    public final NumberPath<Long> createdBy = createNumber("createdBy", Long.class);

    public final DateTimePath<java.util.Date> createdDate = createDateTime("createdDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> modifiedBy = createNumber("modifiedBy", Long.class);

    public final DateTimePath<java.util.Date> modifiedDate = createDateTime("modifiedDate", java.util.Date.class);

    public final StringPath orderNum = createString("orderNum");

    public final StringPath payCode = createString("payCode");

    public final StringPath status = createString("status");

    public final NumberPath<Double> totalFee = createNumber("totalFee", Double.class);

    public QNooneOrder(String variable) {
        super(NooneOrder.class, forVariable(variable));
    }

    public QNooneOrder(Path<? extends NooneOrder> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNooneOrder(PathMetadata metadata) {
        super(NooneOrder.class, metadata);
    }

}

