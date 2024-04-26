
package sistemafarmacia.entidades;

import java.sql.Date;
import java.util.ArrayList;

public class ComprobanteFactura {
    private int id;
    private int estado;
    private Date fecha;
    private ClienteJuridico clienteJuridico;
    private Usuario usuario;
    private ArrayList<DetalleComprobanteFactura> lstDetalle;

    public ComprobanteFactura(){}
    public ComprobanteFactura(Date fecha, ClienteJuridico clienteJuridico, Usuario usuario, ArrayList<DetalleComprobanteFactura> lstDetalle) {
        this.fecha = fecha;
        this.clienteJuridico = clienteJuridico;
        this.usuario = usuario;
        this.lstDetalle = lstDetalle;
    }

    public ComprobanteFactura(int id, int estado, Date fecha, ClienteJuridico clienteJuridico, Usuario usuario, ArrayList<DetalleComprobanteFactura> lstDetalle) {
        this.id = id;
        this.estado = estado;
        this.fecha = fecha;
        this.clienteJuridico = clienteJuridico;
        this.usuario = usuario;
        this.lstDetalle = lstDetalle;
    }

    public double getTotal(){
        double total = 0;
        for (DetalleComprobanteFactura detalleComprobanteFactura : lstDetalle) {
            total+=detalleComprobanteFactura.getCantidad()*detalleComprobanteFactura.getPrecioVenta();
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
    
    public ClienteJuridico getClienteJuridico() {
        return clienteJuridico;
    }

    public void setClienteJuridico(ClienteJuridico clienteJuridico) {
        this.clienteJuridico = clienteJuridico;
    }

    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ArrayList<DetalleComprobanteFactura> getLstDetalle() {
        return lstDetalle;
    }

    public void setLstDetalle(ArrayList<DetalleComprobanteFactura> lstDetalle) {
        this.lstDetalle = lstDetalle;
    }
    
    
}
