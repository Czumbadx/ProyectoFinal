
package sistemafarmacia.datos;

import com.mysql.jdbc.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import sistemafarmacia.entidades.ProductoTipo;


public class ProductoTipoDAT {
    public ArrayList<ProductoTipo> getLista(String buscar, int all, int limit)throws Exception {
        ArrayList<ProductoTipo> lst = null;
        Connection cn= null;
        CallableStatement cstm =null;
        ResultSet rst = null;
        try {
            cn = Conexion.getConexion();
            cstm = cn.prepareCall("call proc_select_tipo(?,?,?) ");
            cstm.setString(1, buscar);
            cstm.setInt(2, all);
            cstm.setInt(3, limit);
            rst = cstm.executeQuery();
            ProductoTipo ProductoTipo = null;
            lst = new ArrayList<>();
            while (rst.next()) {
                ProductoTipo = new ProductoTipo(rst.getInt(1),rst.getString(2),rst.getString(3),rst.getInt(4));
                lst.add(ProductoTipo);
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
    
    public int insert(ProductoTipo tipo) throws Exception{
        Connection cn= null;
        CallableStatement cstm =null;
        ResultSet rst = null;
        int res = 0;
        try {
            cn = Conexion.getConexion();
            cstm = cn.prepareCall("call proc_insert_tipo(?,?) ");
            cstm.setString(1, tipo.getNombre());
            cstm.setString(2, tipo.getDescripcion());
            rst = cstm.executeQuery();
            while (rst.next()) {
                res = rst.getInt(1);
            }
            
        } catch (Exception e) {
            throw e;
        } finally{
            rst.close();
            cstm.close();
            cn.close();
        }
        return res;
    }
    
    public int update(ProductoTipo tipo) throws Exception{
        Connection cn= null;
        CallableStatement cstm =null;
        ResultSet rst = null;
        int res = 0;
        try {
            cn = Conexion.getConexion();
            cstm = cn.prepareCall("call proc_update_tipo(?,?,?,?) ");
            cstm.setInt(1, tipo.getId());
            cstm.setString(2, tipo.getNombre());
            cstm.setString(3, tipo.getDescripcion());
            cstm.setInt(4, tipo.getEstado());
            rst = cstm.executeQuery();
            while (rst.next()) {
                res = rst.getInt(1);
            }
            
        } catch (Exception e) {
            throw e;
        } finally{
            rst.close();
            cstm.close();
            cn.close();
        }
        return res;
    }
}
