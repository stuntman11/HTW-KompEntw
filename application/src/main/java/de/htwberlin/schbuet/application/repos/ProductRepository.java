package de.htwberlin.schbuet.application.repos;

import de.htwberlin.schbuet.application.data.main.Product;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
public interface ProductRepository extends CrudRepository<Product, Long> {
    Product findById(UUID id);
}
