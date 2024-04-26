package sistemafarmacia.datos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import sistemafarmacia.entidades.Persona;

public class PersonaDAT {
    public ArrayList<Persona> getLista(String buscar, int all, int limit)throws Exception {
        ArrayList<Persona> lst = null;
        Connection cn= null;
        CallableStatement cstm =null;
        ResultSet rst = null;
        try {
            cn = Conexion.getConexion();
            cstm = cn.prepareCall("call proc_select_persona(?,?,?) ");
            cstm.setString(1, buscar);
            cstm.setInt(2, all);
            cstm.setInt(3, limit);
            rst = cstm.executeQuery();
            Persona persona = null;
            lst = new ArrayList<>();
            while (rst.next()) {
                persona = new Persona(rst.getInt(1),rst.getString(2),rst.getString(3),rst.getInt(4),rst.getString(5),rst.getInt(6));
                lst.add(persona);
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
    
    public int insert(Persona persona) throws Exception{
        Connection cn= null;
        CallableStatement cstm =null;
        ResultSet rst = null;
        int res = 0;
        try {
            cn = Conexion.getConexion();
            cstm = cn.prepareCall("call proc_insert_persona(?,?,?,?) ");
            cstm.setString(1, persona.getNombres());
            cstm.setString(2, persona.getApellidos());
            cstm.setInt(3, persona.getDni());            
            cstm.setString(4, persona.getDireccion());            
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
    
    public int update(Persona persona) throws Exception{
        Connection cn= null;
        CallableStatement cstm =null;
        ResultSet rst = null;
        int res = 0;
        try {
            cn = Conexion.getConexion();
            cstm = cn.prepareCall("call proc_update_persona(?,?,?,?,?,?) ");
            cstm.setInt(1, persona.getId());
            cstm.setString(2, persona.getNombres());
            cstm.setString(3, persona.getApellidos());
            cstm.setInt(4, persona.getDni());
            cstm.setString(5, persona.getDireccion());
            cstm.setInt(6, persona.getEstado());
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
