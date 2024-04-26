package sistemafarmacia.datos;

import com.mysql.jdbc.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import sistemafarmacia.entidades.Laboratorio;
import sistemafarmacia.entidades.Producto;
import sistemafarmacia.entidades.ProductoTipo;

public class ProductoDAT {
    public ArrayList<Producto> getLista(String buscar, int all, int limit)throws Exception {
        ArrayList<Producto> lst = null;
        Connection cn= null;
        CallableStatement cstm =null;
        ResultSet rst = null;
        try {
            cn = Conexion.getConexion();
            cstm = cn.prepareCall("call proc_select_producto(?,?,?) ");
            cstm.setString(1, buscar);
            cstm.setInt(2, all);
            cstm.setInt(3, limit);
            rst = cstm.executeQuery();
            Producto Producto = null;
            lst = new ArrayList<>();
            while (rst.next()) {
                Producto = new Producto(
                        rst.getInt(1),
                        rst.getString(2),
                        rst.getDouble(3),
                        rst.getInt(4),
                        new ProductoTipo(rst.getInt(5), rst.getString(9), "", 0),
                        new Laboratorio(rst.getInt(6), rst.getString(10), "", 0),
                        rst.getInt(7),
                        rst.getDate(8));
                lst.add(Producto);
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
    
    public int insert(Producto producto) throws Exception{
        Connection cn= null;
        CallableStatement cstm =null;
        ResultSet rst = null;
        int res = 0;
        try {
            cn = Conexion.getConexion();
            cstm = cn.prepareCall("call proc_insert_producto(?,?,?,?,?,?) ");
            cstm.setString(1, producto.getNombre());
            cstm.setDouble(2, producto.getPrecio());
            cstm.setInt(3, producto.getStock());
            cstm.setInt(4, producto.getTipo().getId());
            cstm.setInt(5, producto.getLaboratorio().getId());
            cstm.setDate(6, producto.getFechaVencimiento());
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
    
    public int update(Producto producto) throws Exception{
        Connection cn= null;
        CallableStatement cstm =null;
        ResultSet rst = null;
        int res = 0;
        try {
            cn = Conexion.getConexion();
            cstm = cn.prepareCall("call proc_update_producto(?,?,?,?,?,?,?,?) ");
            cstm.setInt(1, producto.getId());
            cstm.setString(2, producto.getNombre());
            cstm.setDouble(3, producto.getPrecio());
            cstm.setInt(4, producto.getStock());
            cstm.setInt(5, producto.getTipo().getId());
            cstm.setInt(6, producto.getLaboratorio().getId());
            cstm.setInt(7, producto.getEstado());
            cstm.setDate(8, producto.getFechaVencimiento());
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
