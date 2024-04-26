package sistemafarmacia.entidades;
public class Laboratorio {
    private int id;
    private String nombre;
    private String direccion;
    private int estado;
    
    public Laboratorio(){}

    public Laboratorio(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }
    
    public Laboratorio(int id, String nombre, String direccion, int estado) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    
}
