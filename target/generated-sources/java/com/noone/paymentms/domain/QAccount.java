package com.noone.paymentms.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAccount is a Querydsl query type for Account
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAccount extends EntityPathBase<Account> {

    private static final long serialVersionUID = -1460467269L;

    public static final QAccount account = new QAccount("account");

    public final StringPath accountName = createString("accountName");

    public final StringPath accountStatus = createString("accountStatus");

    public final StringPath accountType = createString("accountType");

    public final StringPath contactNumber = createString("contactNumber");

    public final NumberPath<Long> createdBy = createNumber("createdBy", Long.class);

    public final DateTimePath<java.util.Date> createdDate = createDateTime("createdDate", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath loginId = createString("loginId");

    public final NumberPath<Long> modifiedBy = createNumber("modifiedBy", Long.class);

    public final DateTimePath<java.util.Date> modifiedDate = createDateTime("modifiedDate", java.util.Date.class);

    public final StringPath nonceToken = createString("nonceToken");

    public final NumberPath<Long> parentId = createNumber("parentId", Long.class);

    public final StringPath passwordHash = createString("passwordHash");

    public final StringPath passwordSalt = createString("passwordSalt");

    public final StringPath phoneNumber = createString("phoneNumber");

    public QAccount(String variable) {
        super(Account.class, forVariable(variable));
    }

    public QAccount(Path<? extends Account> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccount(PathMetadata metadata) {
        super(Account.class, metadata);
    }

}

