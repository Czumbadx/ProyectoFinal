
package sistemafarmacia.entidades;

public class Persona {
    private int id;
    private String nombres;
    private String apellidos;
    private int dni;
    private int estado;
    private String direccion;
    
    public Persona(){}

    public Persona(String nombres, String apellidos, int dni, String direccion) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.direccion = direccion;
    }    
    
    public Persona(int id, String nombres, String apellidos, int dni, String direccion, int estado) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.estado = estado;
        this.direccion = direccion;
    }
    
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    
   
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    
    public String getEstadoDescripcion(){
        if (estado==1)return "Activo";
        else return "Inactivo";
    }
}
