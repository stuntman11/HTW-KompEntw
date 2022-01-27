package de.htwberlin.schbuet.data_warehouse.services;

import de.htwberlin.schbuet.data_warehouse.data.body.BodyWarehouseItem;
import de.htwberlin.schbuet.data_warehouse.repos.WarehouseItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class WarehouseService {

    private static final Logger log = LoggerFactory.getLogger(WarehouseService.class);

    private final WarehouseItemRepository warehouseItemRepository;

    public WarehouseService(WarehouseItemRepository warehouseItemRepository) {
        this.warehouseItemRepository = warehouseItemRepository;
    }


    public UUID createOrUpdateWarehouseItem(BodyWarehouseItem item) {
        return null;
    }

    private UUID createWarehouseItem(BodyWarehouseItem item) {
        return null;
    }

    private UUID updateWarehouseItem(BodyWarehouseItem item) {
        return null;
    }
}
