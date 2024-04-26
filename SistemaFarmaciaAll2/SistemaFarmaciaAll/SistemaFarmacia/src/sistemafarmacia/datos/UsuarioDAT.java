

package sistemafarmacia.datos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import sistemafarmacia.entidades.Persona;
import sistemafarmacia.entidades.Usuario;


public class UsuarioDAT {
    public static Usuario login(Usuario user)throws Exception {
        Usuario usuario = null;
        Connection cn= null;
        CallableStatement cstm =null;
        ResultSet rst = null;
        try {
            cn = Conexion.getConexion();
            cstm = cn.prepareCall("call proc_login(?,?) ");
            cstm.setString(1, user.getUsername());
            cstm.setString(2, user.getPassword());
            rst = cstm.executeQuery();
            while (rst.next()) {
                usuario = user;
                usuario.setPassword("01010101");
                usuario.setId(rst.getInt(1));
                usuario.setPerfilId(rst.getInt(2));
                usuario.setPerfil(rst.getString(3));
                usuario.setPersona(new Persona(rst.getInt(4), rst.getString(5), rst.getString(6),rst.getInt(7),rst.getString(8), 1));
            }
            
        } catch (Exception e) {
            throw e;
        }
        return usuario;
    }
            
}
