package sistemafarmacia.entidades;

public class DetalleComprobanteBoleta {
    private int id;
    private int estado;
    private Producto producto;
    private int cantidad;
    private double precioVenta;
    public DetalleComprobanteBoleta(){}
    public DetalleComprobanteBoleta(Producto producto, int cantidad, double precioVenta) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
    }

    public DetalleComprobanteBoleta(int id, int estado, Producto producto, int cantidad, double precioVenta) {
        this.id = id;
        this.estado = estado;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
    }
    
    public double getSubTotal(){
        return cantidad*precioVenta;
    }
            

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

   
    
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    
}
