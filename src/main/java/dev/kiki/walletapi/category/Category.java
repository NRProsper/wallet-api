package dev.kiki.walletapi.category;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.kiki.walletapi.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "transaction-categories")
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

}
