
package sistemafarmacia.controladores;

import java.util.ArrayList;
import sistemafarmacia.datos.PersonaDAT;
import sistemafarmacia.entidades.Persona;

public class PersonaCON {
    PersonaDAT datos = new PersonaDAT();
    public ArrayList<Persona> getLista(String buscar, int all, int limit)throws Exception {
        return datos.getLista(buscar, all, limit);
    }
}
