package fpt.t2009m1.asm_springboot.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import fpt.t2009m1.asm_springboot.entity.base.BaseEntity;
import fpt.t2009m1.asm_springboot.entity.myenum.ProductStatus;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Id

    private String id;
    private String name;
    private BigDecimal price;
    private String description;
    @Lob
    private String detail;
    private String thumbnails;
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus status;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id")
    @JsonManagedReference
    private Category category;

}
