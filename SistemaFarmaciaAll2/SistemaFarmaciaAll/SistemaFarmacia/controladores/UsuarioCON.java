/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemafarmacia.controladores;

import sistemafarmacia.datos.UsuarioDAT;
import sistemafarmacia.entidades.Usuario;

/**
 *
 * @author Miler
 */
public class UsuarioCON {
    public static Usuario login(Usuario usuario) throws Exception{
        return UsuarioDAT.login(usuario);
    }
}
