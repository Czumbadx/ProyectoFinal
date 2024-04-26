
package sistemafarmacia.entidades;

public class ClienteJuridico {
    private int id;
    private String razonSocial;
    private String direccion;
    private String ruc;
    private int estado;
    
    public ClienteJuridico(){}

    public ClienteJuridico(String razonSocial, String direccion, String ruc) {
        this.razonSocial = razonSocial;
        this.direccion = direccion;
        this.ruc = ruc;
    }
    
    public ClienteJuridico(int id, String razonSocial, String direccion, String ruc, int estado) {
        this.id = id;
        this.razonSocial = razonSocial;
        this.direccion = direccion;
        this.ruc = ruc;
        this.estado = estado;
    }
    
    

   

    public String getEstadoDescripcion(){
        if (estado==1)return "Activo";
        else return "Inactivo";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

   

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    

    
}
