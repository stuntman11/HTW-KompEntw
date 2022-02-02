package de.htwberlin.schbuet.data_warehouse.services;

import de.htwberlin.schbuet.data_warehouse.data.request.RequestWarehouseItem;
import de.htwberlin.schbuet.data_warehouse.data.main.WarehouseItem;
import de.htwberlin.schbuet.data_warehouse.errors.ResourceNotFoundException;
import de.htwberlin.schbuet.data_warehouse.repos.WarehouseItemRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

@Service
@Slf4j
public class WarehouseService {

    private final WarehouseItemRepository warehouseItemRepository;

    public WarehouseService(WarehouseItemRepository warehouseItemRepository) {
        this.warehouseItemRepository = warehouseItemRepository;
    }

    public WarehouseItem getWarehouseItemByUUID(UUID uuid) {
        return warehouseItemRepository.findTop1ByProductId(uuid);
    }

    @SneakyThrows
    public void deleteWarehouseItem(UUID uuid) {
        var item = this.getWarehouseItemByUUID(uuid);
        if (item == null) {
            log.warn("could not find a warehouse item with id " + uuid);
            throw new ResourceNotFoundException(uuid);
        }
    }

    public UUID createOrUpdateWarehouseItem(RequestWarehouseItem requestItem) {
        var item = this.getWarehouseItemByUUID(requestItem.getProductId());
        if (item == null) {
            log.info("no warehouse item found for product id" + requestItem.getProductId());
            return this.createWarehouseItem(requestItem);
        } else {
            log.info("warehouse item found for product id" + requestItem.getProductId());
            return this.updateWarehouseItem(item);
        }
    }

    public UUID createWarehouseItem(RequestWarehouseItem requestItem) {
        var warehouseItem = WarehouseItem.builder()
                .productId(requestItem.getProductId())
                .quantity(requestItem.getQuantity())
                .deliveryTimeInDays(requestItem.getDeliveryTimeInDays())
                .latitude(requestItem.getLatitude())
                .longitude(requestItem.getLongitude())
                .dateCreated(Calendar.getInstance().getTime())
                .dateLastUpdate(Calendar.getInstance().getTime())
                .build();

        var savedItem = warehouseItemRepository.save(warehouseItem);
        log.info("New warehouse item for product " + requestItem.getProductId() + " was created. ID:" + savedItem.getId());
        return savedItem.getId();
    }

    public UUID updateWarehouseItem(WarehouseItem warehouseItem) {
        warehouseItem.setQuantity(warehouseItem.getQuantity());
        warehouseItem.setDeliveryTimeInDays(warehouseItem.getDeliveryTimeInDays());
        warehouseItem.setLatitude(warehouseItem.getLatitude());
        warehouseItem.setLongitude(warehouseItem.getLongitude());
        warehouseItem.setDateLastUpdate(Calendar.getInstance().getTime());

        warehouseItemRepository.save(warehouseItem);
        log.info("Warehouse item was updated. ID:" + warehouseItem.getId());
        return warehouseItem.getId();
    }
}
