package domain;

import org.junit.jupiter.api.Test;

import java.util.List;


class ProductAndPromotionLoaderTest {

    @Test
    void loadProducts() {
    }

    @Test
    void processFile_test() {
        ProductAndPromotionLoader loader = new ProductAndPromotionLoader();
        Products products = loader.loadProducts("src/main/resources/products.md");
    }
}
