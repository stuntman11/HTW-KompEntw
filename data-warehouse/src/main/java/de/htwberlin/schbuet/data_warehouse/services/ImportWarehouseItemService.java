package de.htwberlin.schbuet.data_warehouse.services;

import de.htwberlin.schbuet.data_warehouse.data.csv.WarehouseItemCsv;
import de.htwberlin.schbuet.data_warehouse.data.main.WarehouseItem;
import de.htwberlin.schbuet.data_warehouse.repos.WarehouseItemRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

@Service
public class ImportWarehouseItemService {

    private static final String TYPE = "text/csv";

    private final WarehouseItemRepository warehouseItemRepository;
    private final CsvParseService parseService;

    public ImportWarehouseItemService(WarehouseItemRepository warehouseItemRepository, CsvParseService parseService) {
        this.warehouseItemRepository = warehouseItemRepository;
        this.parseService = parseService;
    }

    @SneakyThrows
    public UUID importWarehouseData(MultipartFile file) {

        if (!hasCSVFormat(file)) {
            return null;
        }

        var list = parseService.parseWarehouseItemInputStream(file.getInputStream());
        UUID id = null;
        for (WarehouseItemCsv csv : list) {
            var item = warehouseItemRepository.findTop1ByProductId(csv.getIdAsUUID());
            if (item == null) {
                WarehouseItem wItem = new WarehouseItem();
                wItem.setDateCreated(new Date());
                wItem.setProductId(csv.getIdAsUUID());
                id = setAndSaveItem(csv, wItem);
            } else {
                item.setDateLastUpdate(new Date());
                id = setAndSaveItem(csv, item);
            }
        }
        return id;
    }

    private UUID setAndSaveItem(WarehouseItemCsv csv, WarehouseItem item) {
        item.setLatitude(csv.getLatitude());
        item.setDeliveryTimeInDays(csv.getDeliveryTimeInDays());
        item.setLongitude(csv.getLongitude());
        item.setQuantity(csv.getQuantity());
        item.setDateLastUpdate(new Date());

        return warehouseItemRepository.save(item).getId();
    }

    public boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }
}
