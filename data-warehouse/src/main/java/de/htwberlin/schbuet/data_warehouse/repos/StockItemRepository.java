package de.htwberlin.schbuet.data_warehouse.repos;

import de.htwberlin.schbuet.data_warehouse.data.main.StockItem;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
public interface StockItemRepository extends CrudRepository<StockItem, Long> {
    StockItem findById(UUID id);
    StockItem findTop1ByProductId(UUID id);
}
