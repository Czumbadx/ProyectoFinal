package sistemafarmacia.entidades;

import java.sql.Date;

public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private int stock;
    private ProductoTipo tipo;
    private Laboratorio laboratorio;
    private int estado;
    private Date fechaVencimiento;

    public String getEstadoDescripcion(){
        if (estado==1)return "Activo";
        else return "Inactivo";
    }
    public Producto(){}

    public Producto(String nombre, double precio, int stock, ProductoTipo tipo, Laboratorio laboratorio, Date fechaVencimiento) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.tipo = tipo;
        this.laboratorio = laboratorio;
        this.fechaVencimiento = fechaVencimiento;
    }
    
    public Producto(int id, String nombre, double precio, int stock, ProductoTipo tipo, Laboratorio laboratorio, int estado, Date fechaVencimiento) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.tipo = tipo;
        this.laboratorio = laboratorio;
        this.estado = estado;
        this.fechaVencimiento = fechaVencimiento;
    }
    
    

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public ProductoTipo getTipo() {
        return tipo;
    }

    public void setTipo(ProductoTipo tipo) {
        this.tipo = tipo;
    }

    public Laboratorio getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(Laboratorio laboratorio) {
        this.laboratorio = laboratorio;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    
    
    
}
