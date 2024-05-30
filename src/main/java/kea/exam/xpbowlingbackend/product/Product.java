package kea.exam.xpbowlingbackend.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private double price;
    private String imgURL;

    public Product(String name, double price, String imgURL) {
        this.name = name;
        this.price = price;
        this.imgURL = imgURL;
    }
}
