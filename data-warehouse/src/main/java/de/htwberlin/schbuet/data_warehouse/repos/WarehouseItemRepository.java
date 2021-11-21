package de.htwberlin.schbuet.data_warehouse.repos;

import de.htwberlin.schbuet.data_warehouse.data.main.WarehouseItem;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
public interface  WarehouseItemRepository extends CrudRepository<WarehouseItem, Long> {
    WarehouseItem findById(UUID id);
    WarehouseItem findTop1AndProductId(UUID id);
}
