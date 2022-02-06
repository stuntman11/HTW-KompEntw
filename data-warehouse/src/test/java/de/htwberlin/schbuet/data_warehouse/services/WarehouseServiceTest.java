package de.htwberlin.schbuet.data_warehouse.services;

import de.htwberlin.schbuet.data_warehouse.data.request.RequestStockItem;
import de.htwberlin.schbuet.data_warehouse.errors.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest
class WarehouseServiceTest {

    @Autowired
    private WarehouseService warehouse;

    private RequestStockItem item1;
    private RequestStockItem item2;

    @BeforeEach
    void setUp() {
        item1 = new RequestStockItem(UUID.fromString("854de25a-9fb5-428c-a3bd-68418bbcc1e2"), 5, 2, 52.520008, 13.404954);
        item2 = new RequestStockItem(UUID.fromString("f30b1ccd-9f7b-469e-879e-ae08c1aa708c"), 3, 4, 48.137154, 11.576124);
        warehouse.createOrUpdateStockItem(item1);
        warehouse.createOrUpdateStockItem(item2);
    }

    @Test
    void testCreateTwoItemsShouldBeSavedInDatabase() {
        var savedItem1 = warehouse.getStockItemByUUID(UUID.fromString("854de25a-9fb5-428c-a3bd-68418bbcc1e2"));
        var savedItem2 = warehouse.getStockItemByUUID(UUID.fromString("f30b1ccd-9f7b-469e-879e-ae08c1aa708c"));

        assertEquals("854de25a-9fb5-428c-a3bd-68418bbcc1e2", savedItem1.getProductId().toString());
        assertEquals("f30b1ccd-9f7b-469e-879e-ae08c1aa708c", savedItem2.getProductId().toString());

        assertEquals(5, savedItem1.getQuantity());
        assertEquals(3, savedItem2.getQuantity());

        assertEquals(2, savedItem1.getDeliveryTimeInDays());
        assertEquals(4, savedItem2.getDeliveryTimeInDays());

        assertEquals(52.520008, savedItem1.getLatitude());
        assertEquals(48.137154, savedItem2.getLatitude());

        assertEquals(13.404954, savedItem1.getLongitude());
        assertEquals(11.576124, savedItem2.getLongitude());

        assertNotNull(savedItem1.getDateCreated());
        assertNotNull(savedItem2.getDateCreated());

        assertNotNull(savedItem1.getDateLastUpdate());
        assertNotNull(savedItem2.getDateLastUpdate());
    }

    @Test
    void testCreateOrUpdateShouldUpdateItemsIfAlreadyExist() {
        item1 = new RequestStockItem(UUID.fromString("854de25a-9fb5-428c-a3bd-68418bbcc1e2"), 1, 6, 	51.520008, 	12.404954);
        warehouse.createOrUpdateStockItem(item1);

        var savedItem1 = warehouse.getStockItemByUUID(UUID.fromString("854de25a-9fb5-428c-a3bd-68418bbcc1e2"));
        var savedItem2 = warehouse.getStockItemByUUID(UUID.fromString("f30b1ccd-9f7b-469e-879e-ae08c1aa708c"));

        assertEquals(2, warehouse.getAllStockItems().size());

        assertEquals("854de25a-9fb5-428c-a3bd-68418bbcc1e2", savedItem1.getProductId().toString());
        assertEquals("f30b1ccd-9f7b-469e-879e-ae08c1aa708c", savedItem2.getProductId().toString());

        assertEquals(1, savedItem1.getQuantity());
        assertEquals(3, savedItem2.getQuantity());

        assertEquals(6, savedItem1.getDeliveryTimeInDays());
        assertEquals(4, savedItem2.getDeliveryTimeInDays());

        assertEquals(51.520008, savedItem1.getLatitude());
        assertEquals(48.137154, savedItem2.getLatitude());

        assertEquals(12.404954, savedItem1.getLongitude());
        assertEquals(11.576124, savedItem2.getLongitude());

        assertTrue(savedItem1.getDateLastUpdate().getTime() > savedItem1.getDateCreated().getTime());
    }

    @Test
    void testDeleteItemShouldDeleteItem() {
        assertEquals(2, warehouse.getAllStockItems().size());
        
        warehouse.deleteStockItem(UUID.fromString("854de25a-9fb5-428c-a3bd-68418bbcc1e2"));
        
        assertEquals(1, warehouse.getAllStockItems().size());
        assertNull(warehouse.getStockItemByUUID(UUID.fromString("854de25a-9fb5-428c-a3bd-68418bbcc1e2")));
        assertNotNull(warehouse.getStockItemByUUID(UUID.fromString("f30b1ccd-9f7b-469e-879e-ae08c1aa708c")));
    }

    @Test
    void testDeleteNonExistingItemShouldThrowException() {
        assertEquals(2, warehouse.getAllStockItems().size());
        assertThrows(ResourceNotFoundException.class, () -> {
            warehouse.deleteStockItem(UUID.fromString("dd7a2f5f-2ed6-4b71-86a7-9e0b9ec818c8"));
        });
        assertEquals(2, warehouse.getAllStockItems().size());
    }

    @Test
    void testGetItemByUUIDShouldReturnCorrectValue() {
        var savedItem1 = warehouse.getStockItemByUUID(UUID.fromString("854de25a-9fb5-428c-a3bd-68418bbcc1e2"));
        
        assertEquals("854de25a-9fb5-428c-a3bd-68418bbcc1e2", savedItem1.getProductId().toString());
    }

    @Test
    void testGetItemByUnknownUUIDShouldReturnNull() {
        var savedItem1 = warehouse.getStockItemByUUID(UUID.fromString("854de25a-0000-0000-0000-68418bbcc1e2"));
        
        assertNull(savedItem1);
    }

    @Test
    void testGetItemByNullShouldReturnNull() {
        var savedItem1 = warehouse.getStockItemByUUID(null);
        
        assertNull(savedItem1);
    }
}