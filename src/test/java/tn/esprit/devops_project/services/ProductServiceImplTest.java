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
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@ActiveProfiles("test")
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    @DatabaseSetup("/data-set/product-data.xml")
    void addProduct() {
        Product product = new Product();
        product.setTitle("TitreDuProduit");
        product.setPrice(10.0f);
        product.setQuantity(5);
        product.setCategory(ProductCategory.CLOTHING);

        Long existingStockId = 2L;

        product = productService.addProduct(product, existingStockId);

        assertEquals(product.getTitle(), "TitreDuProduit");
        assertEquals(product.getPrice(), 10.0f);
        assertEquals(product.getQuantity(), 5);
        assertEquals(product.getCategory(), ProductCategory.CLOTHING);

        // Si votre méthode de service fonctionne correctement,
        // l'ID du stock associé au produit doit être 2L (existingStockId).
        assertEquals(product.getStock().getIdStock(), existingStockId);
    }

    @Test
    @DatabaseSetup("/data-set/product-data.xml")
    void retrieveProduct() {
        Long productId = 2L; // Supposons qu'un produit avec l'ID 1 existe dans vos données de test
        Product product = productService.retrieveProduct(productId);

        // Assertions pour les détails du produit
        assertEquals(productId, product.getIdProduct());
        // Ajoutez d'autres assertions pour d'autres propriétés du produit
    }

    @Test
    @DatabaseSetup("/data-set/product-data.xml")
    void retrieveAllProduct() {
        List<Product> productList = productService.retreiveAllProduct();

        // Assertion sur la taille de la liste de produits ou d'autres attentes
    }

    @Test
    @DatabaseSetup("/data-set/product-data.xml")
    void retrieveProductByCategory() {
        ProductCategory category = ProductCategory.CLOTHING; // Remplacez par la catégorie désirée
        List<Product> productList = productService.retrieveProductByCategory(category);

        // Assertion sur la liste des produits avec la catégorie spécifiée
    }

    @Test
    @DatabaseSetup("/data-set/product-data.xml")
    void deleteProduct() {
        Long productId = 2L; // Supposons qu'un produit avec l'ID 1 existe dans vos données de test

        // Tentez de supprimer le produit
        productService.deleteProduct(productId);

        // Essayez de récupérer le produit
        try {
            productService.retrieveProduct(productId);
        } catch (NullPointerException e) {
            // L'exception a été levée, ce qui est attendu
            return;
        }

        fail("L'exception NullPointerException n'a pas été levée lors de la suppression du produit.");
    }
}
