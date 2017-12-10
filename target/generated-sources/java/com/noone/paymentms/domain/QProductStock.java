package com.noone.paymentms.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductStock is a Querydsl query type for ProductStock
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProductStock extends EntityPathBase<ProductStock> {

    private static final long serialVersionUID = -1848115047L;

    public static final QProductStock productStock = new QProductStock("productStock");

    public final NumberPath<Long> createdBy = createNumber("createdBy", Long.class);

    public final DateTimePath<java.util.Date> createdDate = createDateTime("createdDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> modifiedBy = createNumber("modifiedBy", Long.class);

    public final DateTimePath<java.util.Date> modifiedDate = createDateTime("modifiedDate", java.util.Date.class);

    public final StringPath name = createString("name");

    public final StringPath price = createString("price");

    public final StringPath produceDate = createString("produceDate");

    public final StringPath rfid = createString("rfid");

    public final StringPath sku = createString("sku");

    public final StringPath status = createString("status");

    public QProductStock(String variable) {
        super(ProductStock.class, forVariable(variable));
    }

    public QProductStock(Path<? extends ProductStock> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductStock(PathMetadata metadata) {
        super(ProductStock.class, metadata);
    }

}

