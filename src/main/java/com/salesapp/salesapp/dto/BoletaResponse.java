package com.salesapp.salesapp.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record BoletaResponse(
        OffsetDateTime fecha,
        List<DetalleBoletaResponse> productos,
        BigDecimal total
) {
}
