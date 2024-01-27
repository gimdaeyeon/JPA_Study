package com.jpa.finalapp.domain.entity.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCover is a Querydsl query type for Cover
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCover extends EntityPathBase<Cover> {

    private static final long serialVersionUID = -1237688460L;

    public static final QCover cover = new QCover("cover");

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

    public final NumberPath<Integer> size = createNumber("size", Integer.class);

    public QCover(String variable) {
        super(Cover.class, forVariable(variable));
    }

    public QCover(Path<? extends Cover> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCover(PathMetadata metadata) {
        super(Cover.class, metadata);
    }

}

