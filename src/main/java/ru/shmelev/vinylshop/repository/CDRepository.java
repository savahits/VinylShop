package ru.shmelev.vinylshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.shmelev.vinylshop.domain.Product;

import java.util.List;

@Repository
public interface CDRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.format = 'cd'")
    List<Product> findAllCD();

}
