package dev.kiki.walletapi.category;

import dev.kiki.walletapi.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAllByUser(User user);

}
