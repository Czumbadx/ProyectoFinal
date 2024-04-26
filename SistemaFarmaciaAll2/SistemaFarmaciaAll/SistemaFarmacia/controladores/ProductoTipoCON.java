
package sistemafarmacia.controladores;

import java.util.ArrayList;
import sistemafarmacia.datos.ProductoTipoDAT;
import sistemafarmacia.entidades.ProductoTipo;


public class ProductoTipoCON {
    ProductoTipoDAT datos = new ProductoTipoDAT();
    public ArrayList<ProductoTipo> getLista(String buscar, int all)throws Exception {
        return datos.getLista(buscar, all);
    }
    
    public int insert(ProductoTipo tipo) throws Exception{
        return datos.insert(tipo);
    }
    
    public int update(ProductoTipo tipo) throws Exception{
       return datos.update(tipo);
    }
}
