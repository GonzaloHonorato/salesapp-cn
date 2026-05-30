package com.salesapp.salesapp.config;

import com.salesapp.salesapp.model.Producto;
import com.salesapp.salesapp.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedProductos(ProductoRepository productoRepository) {
        return args -> {
            if (productoRepository.count() > 0) {
                return;
            }

            productoRepository.saveAll(List.of(
                    new Producto(null, "Pan marraqueta", "Panaderia", BigDecimal.valueOf(1200), 50, true),
                    new Producto(null, "Leche entera 1L", "Lacteos", BigDecimal.valueOf(1190), 30, true),
                    new Producto(null, "Arroz grado 1 1kg", "Despensa", BigDecimal.valueOf(1490), 40, true),
                    new Producto(null, "Huevos docena", "Frescos", BigDecimal.valueOf(3290), 20, true),
                    new Producto(null, "Manzana roja 1kg", "Frutas", BigDecimal.valueOf(1890), 35, true),
                    new Producto(null, "Aceite vegetal 900ml", "Despensa", BigDecimal.valueOf(2790), 25, true)
            ));
        };
    }
}
