package sistemafarmacia.datos;

import com.mysql.jdbc.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import sistemafarmacia.entidades.Laboratorio;

public class LaboratorioDAT {
    
    public ArrayList<Laboratorio> getLista(String buscar, int all, int limit)throws Exception {
        ArrayList<Laboratorio> lst = null;
        Connection cn= null;
        CallableStatement cstm =null;
        ResultSet rst = null;
        try {
            cn = Conexion.getConexion();
            cstm = cn.prepareCall("call proc_select_laboratorio(?,?,?) ");
            cstm.setString(1, buscar);
            cstm.setInt(2, all);
            cstm.setInt(3, limit);
            rst = cstm.executeQuery();
            Laboratorio laboratorio = null;
            lst = new ArrayList<>();
            while (rst.next()) {
                laboratorio = new Laboratorio(rst.getInt(1),rst.getString(2),rst.getString(3),rst.getInt(4));
                lst.add(laboratorio);
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
    
    public int insert(Laboratorio laboratorio) throws Exception{
        Connection cn= null;
        CallableStatement cstm =null;
        ResultSet rst = null;
        int res = 0;
        try {
            cn = Conexion.getConexion();
            cstm = cn.prepareCall("call proc_insert_laboratorio(?,?) ");
            cstm.setString(1, laboratorio.getNombre());
            cstm.setString(2, laboratorio.getDireccion());
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
    
    public int update(Laboratorio laboratorio) throws Exception{
        Connection cn= null;
        CallableStatement cstm =null;
        ResultSet rst = null;
        int res = 0;
        try {
            cn = Conexion.getConexion();
            cstm = cn.prepareCall("call proc_update_laboratorio(?,?,?,?) ");
            cstm.setInt(1, laboratorio.getId());
            cstm.setString(2, laboratorio.getNombre());
            cstm.setString(3, laboratorio.getDireccion());
            cstm.setInt(4, laboratorio.getEstado());
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
