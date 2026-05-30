package com.salesapp.salesapp.service;

import com.salesapp.salesapp.model.Producto;
import com.salesapp.salesapp.pattern.ProductoBuilder;
import com.salesapp.salesapp.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerActivos() {
        return productoRepository.findByActivoTrueOrderByNombreAsc();
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Producto guardar(Producto producto) {
        Producto nuevoProducto = new ProductoBuilder()
                .nombre(producto.getNombre())
                .categoria(producto.getCategoria())
                .precio(producto.getPrecio())
                .stock(producto.getStock())
                .activo(producto.getActivo() == null || producto.getActivo())
                .build();
        return productoRepository.save(nuevoProducto);
    }

    public Producto actualizar(Long id, Producto productoActualizado) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        producto.setNombre(productoActualizado.getNombre());
        producto.setCategoria(productoActualizado.getCategoria());
        producto.setPrecio(productoActualizado.getPrecio());
        producto.setStock(productoActualizado.getStock());
        producto.setActivo(productoActualizado.getActivo() == null || productoActualizado.getActivo());
        return productoRepository.save(producto);
    }
}
