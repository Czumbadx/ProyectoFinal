package sistemafarmacia.datos;

import com.mysql.jdbc.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import sistemafarmacia.entidades.ClienteJuridico;
import sistemafarmacia.entidades.ComprobanteFactura;
import sistemafarmacia.entidades.DetalleComprobanteFactura;
import sistemafarmacia.entidades.Persona;
import sistemafarmacia.entidades.Producto;
import sistemafarmacia.entidades.Usuario;

public class ComprobanteFacturaDAT {
    
    public ArrayList<ComprobanteFactura> getListaAll()throws Exception {
        ArrayList<ComprobanteFactura> lst = null;
        Connection cn= null;
        CallableStatement cstm =null;
        ResultSet rst = null;
        try {
            cn = Conexion.getConexion();
            cstm = cn.prepareCall("call proc_select_comprobantefactura() ");          
            rst = cstm.executeQuery();
            lst = new ArrayList<>();
            
            int idComprobante=0;
            ArrayList<DetalleComprobanteFactura> lstDetalleComprobanteFactura = null;
            ComprobanteFactura comprobanteFactura = null;
            
            DetalleComprobanteFactura detalleComprobanteFactura = null;
            while (rst.next()) { 
                detalleComprobanteFactura = new DetalleComprobanteFactura();
                Producto producto = new Producto();
                if(idComprobante!=rst.getInt(1)){
                    
                    if(comprobanteFactura!=null && lstDetalleComprobanteFactura!=null){                        
                        comprobanteFactura.setLstDetalle(lstDetalleComprobanteFactura);
                        lst.add(comprobanteFactura);
                    }
                    
                    lstDetalleComprobanteFactura = new ArrayList<>();
                    comprobanteFactura = new ComprobanteFactura();
                    Usuario usuario = new Usuario();    
                    Persona personaUsuario = new Persona();
                    //Persona cliente = new Persona();
                    ClienteJuridico clienteJuridico = new ClienteJuridico();
                                  
                    comprobanteFactura.setId(rst.getInt(1));
                    comprobanteFactura.setFecha(rst.getDate(2));
                    usuario.setId(rst.getInt(3));
                    clienteJuridico.setId(rst.getInt(4));
                    comprobanteFactura.setEstado(rst.getInt(5));
                    personaUsuario.setNombres(rst.getString(6));
                    personaUsuario.setApellidos(rst.getString(7));
                    clienteJuridico.setRuc(rst.getString(8));
                    clienteJuridico.setRazonSocial(rst.getString(9));
                    usuario.setPersona(personaUsuario);                                       
                    
                    producto.setId(rst.getInt(10));
                    detalleComprobanteFactura.setCantidad(rst.getInt(11));
                    detalleComprobanteFactura.setPrecioVenta(rst.getDouble(12));
                    producto.setNombre(rst.getString(13));
                    producto.setPrecio(rst.getDouble(14));
                    detalleComprobanteFactura.setProducto(producto);                     
                    lstDetalleComprobanteFactura.add(detalleComprobanteFactura);
                    
                    comprobanteFactura.setUsuario(usuario);
                    comprobanteFactura.setClienteJuridico(clienteJuridico);                                      
                    
                }else if(lstDetalleComprobanteFactura!=null){
                    producto.setId(rst.getInt(10));
                    detalleComprobanteFactura.setCantidad(rst.getInt(11));
                    detalleComprobanteFactura.setPrecioVenta(rst.getDouble(12));
                    producto.setNombre(rst.getString(13));
                    producto.setPrecio(rst.getDouble(14));
                    detalleComprobanteFactura.setProducto(producto);
                    lstDetalleComprobanteFactura.add(detalleComprobanteFactura);
                }
                idComprobante = rst.getInt(1);
            }
            if(comprobanteFactura!=null && lstDetalleComprobanteFactura!=null){
                comprobanteFactura.setLstDetalle(lstDetalleComprobanteFactura);
                lst.add(comprobanteFactura);
            }
            
        } catch (Exception e) {
            throw e;
        } finally{
            rst.close();
            cstm.close();
            cn.close();
        }
        return lst;
    }
    
    public int insert(ComprobanteFactura comprobanteFactura) throws Exception{
        Connection cn= null;
        CallableStatement cstm =null;
        ResultSet rst = null;
        int res = 0;
        try {
            cn = Conexion.getConexion();
            cn.setAutoCommit(false);            
            cstm = cn.prepareCall("call proc_insert_comprobantefactura(?,?,?) ");
            cstm.setDate(1, comprobanteFactura.getFecha());
            cstm.setInt(2, comprobanteFactura.getClienteJuridico().getId());
            cstm.setInt(3, comprobanteFactura.getUsuario().getId());
            rst = cstm.executeQuery();
            if (rst.next()) {
                res = rst.getInt(1);
            }
            
            for (int i = 0; i < comprobanteFactura.getLstDetalle().size(); i++) {
                cstm = cn.prepareCall("call proc_insert_detallecomprobantefactura(?,?,?,?) ");
                cstm.setInt(1, res);
                cstm.setInt(2, comprobanteFactura.getLstDetalle().get(i).getProducto().getId());
                cstm.setInt(3, comprobanteFactura.getLstDetalle().get(i).getCantidad());
                cstm.setDouble(4, comprobanteFactura.getLstDetalle().get(i).getPrecioVenta());
                rst = cstm.executeQuery();
            }
            cn.commit();
        } catch (Exception e) {
            cn.rollback();
            throw e;
        } finally{
            rst.close();
            cstm.close();
            cn.close();
        }
        return res;
    }
}
