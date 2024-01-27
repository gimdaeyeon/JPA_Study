package com.jpa.finalapp.domain.entity.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStand is a Querydsl query type for Stand
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStand extends EntityPathBase<Stand> {

    private static final long serialVersionUID = -1222783085L;

    public static final QStand stand = new QStand("stand");

    public final QProduct _super = new QProduct(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final NumberPath<Integer> price = _super.price;

    public final StringPath production = createString("production");

    //inherited
    public final NumberPath<Integer> quantity = _super.quantity;

    public final EnumPath<com.jpa.finalapp.domain.type.product.StandMaterials> standMaterials = createEnum("standMaterials", com.jpa.finalapp.domain.type.product.StandMaterials.class);

    public QStand(String variable) {
        super(Stand.class, forVariable(variable));
    }

    public QStand(Path<? extends Stand> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStand(PathMetadata metadata) {
        super(Stand.class, metadata);
    }

}

