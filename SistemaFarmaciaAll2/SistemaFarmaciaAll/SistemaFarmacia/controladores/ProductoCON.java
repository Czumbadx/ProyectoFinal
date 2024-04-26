
package sistemafarmacia.controladores;

import java.util.ArrayList;
import sistemafarmacia.datos.ProductoDAT;
import sistemafarmacia.entidades.Producto;

public class ProductoCON {
    ProductoDAT datos = new ProductoDAT();
    public ArrayList<Producto> getLista(String buscar, int all)throws Exception {
        return datos.getLista(buscar, all);
    }
    
    public int insert(Producto producto) throws Exception{
        return datos.insert(producto);
    }
    
   public int update(Producto producto) throws Exception{
       return datos.update(producto);
   }
            
}
