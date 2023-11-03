package tn.esprit.devops_project.services;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.devops_project.entities.Stock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@ActiveProfiles("test")
class StockServiceImplTest {

    @Autowired
    private StockServiceImpl stockService;
    @Test
    @DatabaseSetup("/data-set/stock-data.xml")
    void addStock() {
        final Stock stock = new Stock();
        stock.setTitle("KARMA");
        this.stockService.addStock(stock);
    }

    @Test
    @DatabaseSetup("/data-set/stock-data.xml")
    void retrieveStock() {
        final Stock stock = this.stockService.retrieveStock(2L);
        assertEquals("stock 1", stock.getTitle());
    }

    @Test
    @DatabaseSetup("/data-set/stock-data.xml")
    void retrieveAllStock() {
        // Call the retrieveAllStock method
        List<Stock> allStocks = stockService.retrieveAllStock();

        // Assert that the list is not null and not empty
        assertNotNull(allStocks);
        assertFalse(allStocks.isEmpty());

        // Check the size of the list (number of elements expected in the list)
        assertEquals(2, allStocks.size());

        // Check the titles of the stocks in the list
        assertEquals("stock 1", allStocks.get(0).getTitle());

    }


}
