
package sistemafarmacia.datos;

import com.mysql.jdbc.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import sistemafarmacia.entidades.ComprobanteBoleta;
import sistemafarmacia.entidades.DetalleComprobanteBoleta;
import sistemafarmacia.entidades.Persona;
import sistemafarmacia.entidades.Producto;
import sistemafarmacia.entidades.Usuario;


public class ComprobanteBoletaDAT {
    
    public ArrayList<ComprobanteBoleta> getListaAll()throws Exception {
        ArrayList<ComprobanteBoleta> lst = null;
        Connection cn= null;
        CallableStatement cstm =null;
        ResultSet rst = null;
        try {
            cn = Conexion.getConexion();
            cstm = cn.prepareCall("call proc_select_comprobanteboleta() ");          
            rst = cstm.executeQuery();
            lst = new ArrayList<>();
            
            int idComprobante=0;
            ArrayList<DetalleComprobanteBoleta> lstDetalleComprobanteBoleta = null;
            ComprobanteBoleta comprobanteBoleta = null;
            
            DetalleComprobanteBoleta detalleComprobanteBoleta = null;
            while (rst.next()) { 
                detalleComprobanteBoleta = new DetalleComprobanteBoleta();
                Producto producto = new Producto();
                if(idComprobante!=rst.getInt(1)){
                    
                    if(comprobanteBoleta!=null && lstDetalleComprobanteBoleta!=null){                        
                        comprobanteBoleta.setLstDetalle(lstDetalleComprobanteBoleta);
                        lst.add(comprobanteBoleta);
                    }
                    
                    lstDetalleComprobanteBoleta = new ArrayList<>();
                    comprobanteBoleta = new ComprobanteBoleta();
                    Usuario usuario = new Usuario();    
                    Persona personaUsuario = new Persona();
                    Persona cliente = new Persona();
                                  
                    comprobanteBoleta.setId(rst.getInt(1));
                    comprobanteBoleta.setFecha(rst.getDate(2));
                    usuario.setId(rst.getInt(3));
                    cliente.setId(rst.getInt(4));
                    comprobanteBoleta.setEstado(rst.getInt(5));
                    personaUsuario.setNombres(rst.getString(6));
                    personaUsuario.setApellidos(rst.getString(7));
                    cliente.setNombres(rst.getString(8));
                    cliente.setApellidos(rst.getString(9));
                    usuario.setPersona(personaUsuario);                                       
                    
                    producto.setId(rst.getInt(10));
                    detalleComprobanteBoleta.setCantidad(rst.getInt(11));
                    detalleComprobanteBoleta.setPrecioVenta(rst.getDouble(12));
                    producto.setNombre(rst.getString(13));
                    producto.setPrecio(rst.getDouble(14));
                    detalleComprobanteBoleta.setProducto(producto);                     
                    lstDetalleComprobanteBoleta.add(detalleComprobanteBoleta);
                    
                    comprobanteBoleta.setUsuario(usuario);
                    comprobanteBoleta.setPersona(cliente);                                      
                    
                }else if(lstDetalleComprobanteBoleta!=null){
                    producto.setId(rst.getInt(10));
                    detalleComprobanteBoleta.setCantidad(rst.getInt(11));
                    detalleComprobanteBoleta.setPrecioVenta(rst.getDouble(12));
                    producto.setNombre(rst.getString(13));
                    producto.setPrecio(rst.getDouble(14));
                    detalleComprobanteBoleta.setProducto(producto);
                    lstDetalleComprobanteBoleta.add(detalleComprobanteBoleta);
                }
                idComprobante = rst.getInt(1);
            }
            if(comprobanteBoleta!=null && lstDetalleComprobanteBoleta!=null){
                comprobanteBoleta.setLstDetalle(lstDetalleComprobanteBoleta);
                lst.add(comprobanteBoleta);
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
    
    public int insert(ComprobanteBoleta comprobanteBoleta) throws Exception{
        Connection cn= null;
        CallableStatement cstm =null;
        ResultSet rst = null;
        int res = 0;
        try {
            cn = Conexion.getConexion();
            cn.setAutoCommit(false);            
            cstm = cn.prepareCall("call proc_insert_comprobanteboleta(?,?,?) ");
            cstm.setDate(1, comprobanteBoleta.getFecha());
            cstm.setInt(2, comprobanteBoleta.getPersona().getId());
            cstm.setInt(3, comprobanteBoleta.getUsuario().getId());            
            rst = cstm.executeQuery();
            if (rst.next()) {
                res = rst.getInt(1);
            }else{
                cn.rollback();
                cn.close();
                return 0;
            }
            if(res>0 && comprobanteBoleta.getLstDetalle().size()>0){
                for (int i = 0; i < comprobanteBoleta.getLstDetalle().size(); i++) {
                    cstm = cn.prepareCall("call proc_insert_detallecomprobanteboleta(?,?,?,?) ");
                    cstm.setInt(1, res);
                    cstm.setInt(2, comprobanteBoleta.getLstDetalle().get(i).getProducto().getId());
                    cstm.setInt(3, comprobanteBoleta.getLstDetalle().get(i).getCantidad());
                    cstm.setDouble(4, comprobanteBoleta.getLstDetalle().get(i).getPrecioVenta());
                    rst = cstm.executeQuery();
                }
            }else{
                cn.rollback();
                cn.close();
                return 0;
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
