package com.salesapp.salesapp.service;

import com.salesapp.salesapp.dto.BoletaResponse;
import com.salesapp.salesapp.dto.DetalleBoletaResponse;
import com.salesapp.salesapp.dto.ItemCompraRequest;
import com.salesapp.salesapp.dto.VentaRequest;
import com.salesapp.salesapp.model.Producto;
import com.salesapp.salesapp.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class VentaService {

    private final ProductoRepository productoRepository;

    public VentaService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public BoletaResponse generarBoleta(VentaRequest request) {
        if (request.items() == null || request.items().isEmpty()) {
            throw new IllegalArgumentException("La compra debe contener al menos un producto.");
        }

        List<DetalleBoletaResponse> productos = request.items().stream()
                .map(this::crearDetalle)
                .toList();

        BigDecimal total = productos.stream()
                .map(DetalleBoletaResponse::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new BoletaResponse(OffsetDateTime.now(), productos, total);
    }

    private DetalleBoletaResponse crearDetalle(ItemCompraRequest item) {
        if (item.cantidad() == null || item.cantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }

        Producto producto = productoRepository.findById(item.productoId())
                .filter(Producto::getActivo)
                .orElseThrow(() -> new IllegalArgumentException("Producto no disponible: " + item.productoId()));

        if (producto.getStock() != null && item.cantidad() > producto.getStock()) {
            throw new IllegalArgumentException("Stock insuficiente para " + producto.getNombre());
        }

        BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(item.cantidad()));

        return new DetalleBoletaResponse(
                producto.getId(),
                producto.getNombre(),
                item.cantidad(),
                producto.getPrecio(),
                subtotal
        );
    }
}
