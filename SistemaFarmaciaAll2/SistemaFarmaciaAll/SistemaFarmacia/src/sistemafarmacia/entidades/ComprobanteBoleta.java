
package sistemafarmacia.entidades;

import java.sql.Date;
import java.util.ArrayList;

public class ComprobanteBoleta {
    private int id;
    private int estado;
    private Date fecha;
    private Persona persona;
    private Usuario usuario;
    private ArrayList<DetalleComprobanteBoleta> lstDetalle;

    public ComprobanteBoleta(){};
        
    public ComprobanteBoleta(Date fecha, Persona persona, Usuario usuario, ArrayList<DetalleComprobanteBoleta> lstDetalle) {
        this.fecha = fecha;
        this.persona = persona;
        this.usuario = usuario;
        this.lstDetalle = lstDetalle;
    }

    public ComprobanteBoleta(int id, int estado, Date fecha, Persona persona, Usuario usuario, ArrayList<DetalleComprobanteBoleta> lstDetalle) {
        this.id = id;
        this.estado = estado;
        this.fecha = fecha;
        this.persona = persona;
        this.usuario = usuario;
        this.lstDetalle = lstDetalle;
    }
    
    public double getTotal(){
        double total = 0;
        for (DetalleComprobanteBoleta detalleComprobanteBoleta : lstDetalle) {
            total+=detalleComprobanteBoleta.getCantidad()*detalleComprobanteBoleta.getPrecioVenta();
        }
        return total;
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
    
    

    public String getEstadoDescripcion(){
        if (estado==1)return "Activo";
        else return "Inactivo";
    }
    
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

  
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public ArrayList<DetalleComprobanteBoleta> getLstDetalle() {
        return lstDetalle;
    }

    public void setLstDetalle(ArrayList<DetalleComprobanteBoleta> lstDetalle) {
        this.lstDetalle = lstDetalle;
    }

    
    
    
}
