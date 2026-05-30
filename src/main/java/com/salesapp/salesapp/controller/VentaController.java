package com.salesapp.salesapp.controller;

import com.salesapp.salesapp.dto.BoletaResponse;
import com.salesapp.salesapp.dto.VentaRequest;
import com.salesapp.salesapp.service.VentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping("/generar")
    public ResponseEntity<BoletaResponse> generarBoleta(@RequestBody VentaRequest request) {
        return ResponseEntity.ok(ventaService.generarBoleta(request));
    }
}
