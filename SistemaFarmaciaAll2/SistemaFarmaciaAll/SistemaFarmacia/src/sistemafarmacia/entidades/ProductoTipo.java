
package sistemafarmacia.entidades;


public class ProductoTipo {
    private int id;
    private String nombre;
    private String descripcion;
    private int estado;

    public ProductoTipo() {
    }

    public ProductoTipo(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public ProductoTipo(int id, String nombre, String descripcion, int estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }
    
    public String getEstadoDescripcion(){
        if (estado==1)return "Activo";
        else return "Inactivo";
    }

    @Override
    public String toString() {
        return nombre;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    
}
