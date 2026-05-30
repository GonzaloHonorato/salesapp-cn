package com.salesapp.salesapp.pattern;

import com.salesapp.salesapp.model.Producto;

import java.math.BigDecimal;

/**
 * Patron Builder para construir productos antes de persistirlos en Oracle DB.
 */
public class ProductoBuilder {

    private String nombre;
    private String categoria;
    private BigDecimal precio;
    private Integer stock;
    private Boolean activo = true;

    public ProductoBuilder nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public ProductoBuilder categoria(String categoria) {
        this.categoria = categoria;
        return this;
    }

    public ProductoBuilder precio(BigDecimal precio) {
        this.precio = precio;
        return this;
    }

    public ProductoBuilder stock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public ProductoBuilder activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public Producto build() {
        Producto producto = new Producto();
        producto.setNombre(this.nombre);
        producto.setCategoria(this.categoria);
        producto.setPrecio(this.precio);
        producto.setStock(this.stock);
        producto.setActivo(this.activo);
        return producto;
    }
}
