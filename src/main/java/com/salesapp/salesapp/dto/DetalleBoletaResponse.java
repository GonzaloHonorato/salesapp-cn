package com.salesapp.salesapp.dto;

import java.math.BigDecimal;

public record DetalleBoletaResponse(
        Long productoId,
        String nombre,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotal
) {
}
