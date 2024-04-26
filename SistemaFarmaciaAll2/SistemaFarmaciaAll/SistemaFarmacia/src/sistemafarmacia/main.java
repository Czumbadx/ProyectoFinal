
package sistemafarmacia;

import sistemafarmacia.entidades.Usuario;
import sistemafarmacia.presentacion.FrmLogin;
import sistemafarmacia.presentacion.FrmPrincipal;

public class main {

    public static void main(String[] args) {
        new FrmLogin().setVisible(true);
        //new FrmPrincipal(new Usuario()).setVisible(true);
    }
    
}
