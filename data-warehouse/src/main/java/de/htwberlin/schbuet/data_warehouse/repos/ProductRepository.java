package de.htwberlin.schbuet.data_warehouse.repos;

import de.htwberlin.schbuet.data_warehouse.data.main.Product;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Transactional
public interface ProductRepository extends CrudRepository<Product, Long> {
    Product findById(UUID id);
    List<Product> findAll();
    Product findTop1ByProductID(UUID id);
}
