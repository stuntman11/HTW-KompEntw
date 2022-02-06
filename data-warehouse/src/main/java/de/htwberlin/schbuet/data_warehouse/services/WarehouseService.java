package de.htwberlin.schbuet.data_warehouse.services;

import de.htwberlin.schbuet.data_warehouse.data.request.RequestStockItem;
import de.htwberlin.schbuet.data_warehouse.data.main.StockItem;
import de.htwberlin.schbuet.data_warehouse.errors.ResourceNotFoundException;
import de.htwberlin.schbuet.data_warehouse.repos.StockItemRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class WarehouseService {

    private final StockItemRepository stockItemRepository;

    public WarehouseService(StockItemRepository stockItemRepository) {
        this.stockItemRepository = stockItemRepository;
    }

    public StockItem getStockItemByUUID(UUID uuid) {
        return stockItemRepository.findTop1ByProductId(uuid);
    }

    @SneakyThrows
    public void deleteStockItem(UUID uuid) {
        var stock = this.getStockItemByUUID(uuid);
        if (stock == null) {
            log.warn("could not find a stock item with id " + uuid);
            throw new ResourceNotFoundException(uuid);
        }
        stockItemRepository.delete(stock);
    }

    public UUID createOrUpdateStockItem(RequestStockItem requestItem) {
        var stock = this.getStockItemByUUID(requestItem.getProductId());
        if (stock == null) {
            log.info("no stock item found for product id " + requestItem.getProductId());
            return this.createStockItem(requestItem);
        } else {
            log.info("stock item found for product id " + requestItem.getProductId());
            return this.updateStockItem(stock, requestItem);
        }
    }

    public List<StockItem> getAllStockItems() {
        return stockItemRepository.findAll();
    }

    public UUID createStockItem(RequestStockItem requestItem) {
        var stockItem = StockItem.builder()
                .productId(requestItem.getProductId())
                .quantity(requestItem.getQuantity())
                .deliveryTimeInDays(requestItem.getDeliveryTimeInDays())
                .latitude(requestItem.getLatitude())
                .longitude(requestItem.getLongitude())
                .dateCreated(Calendar.getInstance().getTime())
                .dateLastUpdate(Calendar.getInstance().getTime())
                .build();

        stockItem = stockItemRepository.save(stockItem);
        log.info("New stock item for product " + requestItem.getProductId() + " was created. ID:" + stockItem.getId());
        return stockItem.getId();
    }

    public UUID updateStockItem(StockItem stock, RequestStockItem request) {
        stock.setQuantity(request.getQuantity());
        stock.setDeliveryTimeInDays(request.getDeliveryTimeInDays());
        stock.setLatitude(request.getLatitude());
        stock.setLongitude(request.getLongitude());
        stock.setDateLastUpdate(Calendar.getInstance().getTime());

        stockItemRepository.save(stock);
        log.info("stock item was updated. ID:" + stock.getId());
        return stock.getId();
    }
}
