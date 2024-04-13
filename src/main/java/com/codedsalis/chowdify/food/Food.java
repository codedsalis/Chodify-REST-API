package com.codedsalis.chowdify.food;


import com.codedsalis.chowdify.shared.BaseEntity;
import com.codedsalis.chowdify.user.User;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "foods")
public class Food extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column()
    private String image;

    private String category;
}
