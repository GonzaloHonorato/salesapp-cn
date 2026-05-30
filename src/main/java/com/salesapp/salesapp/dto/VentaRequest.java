package com.salesapp.salesapp.dto;

import java.util.List;

public record VentaRequest(List<ItemCompraRequest> items) {
}
