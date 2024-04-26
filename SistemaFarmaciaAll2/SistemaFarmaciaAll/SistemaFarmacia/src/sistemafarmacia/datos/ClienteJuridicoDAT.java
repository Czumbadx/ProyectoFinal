package sistemafarmacia.datos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import sistemafarmacia.entidades.ClienteJuridico;

public class ClienteJuridicoDAT {
    public ArrayList<ClienteJuridico> getLista(String buscar, int all, int limit)throws Exception {
        ArrayList<ClienteJuridico> lst = null;
        Connection cn= null;
        CallableStatement cstm =null;
        ResultSet rst = null;
        try {
            cn = Conexion.getConexion();
            cstm = cn.prepareCall("call proc_select_clientejuridico(?,?,?) ");
            cstm.setString(1, buscar);
            cstm.setInt(2, all);
            cstm.setInt(3, limit);
            rst = cstm.executeQuery();
            lst = new ArrayList<>();
            while (rst.next()) {
                lst.add(new ClienteJuridico(rst.getInt(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getInt(5)));
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
    
    public int insert(ClienteJuridico clienteJuridico) throws Exception{
        Connection cn= null;
        CallableStatement cstm =null;
        ResultSet rst = null;
        int res = 0;
        try {
            cn = Conexion.getConexion();
            cstm = cn.prepareCall("call proc_insert_clientejuridico(?,?,?) ");
            cstm.setString(1, clienteJuridico.getRazonSocial());
            cstm.setString(2, clienteJuridico.getDireccion());
            cstm.setString(3, clienteJuridico.getRuc());         
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
    
    public int update(ClienteJuridico clienteJuridico) throws Exception{
        Connection cn= null;
        CallableStatement cstm =null;
        ResultSet rst = null;
        int res = 0;
        try {
            cn = Conexion.getConexion();
            cstm = cn.prepareCall("call proc_update_clientejuridico(?,?,?,?,?) ");
            cstm.setInt(1, clienteJuridico.getId());
            cstm.setString(2, clienteJuridico.getRazonSocial());
            cstm.setString(3, clienteJuridico.getDireccion());
            cstm.setString(4, clienteJuridico.getRuc());
            cstm.setInt(5, clienteJuridico.getEstado());
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
