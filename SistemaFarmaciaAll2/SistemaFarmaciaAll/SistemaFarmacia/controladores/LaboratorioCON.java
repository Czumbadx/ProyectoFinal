package sistemafarmacia.controladores;

import java.util.ArrayList;
import sistemafarmacia.datos.LaboratorioDAT;
import sistemafarmacia.entidades.Laboratorio;

public class LaboratorioCON {
    LaboratorioDAT datos = new LaboratorioDAT();
    public ArrayList<Laboratorio> getLista(String buscar, int all)throws Exception {
        return  datos.getLista(buscar,all);
    }
    
    public int insert(Laboratorio laboratorio) throws Exception{
        return datos.insert(laboratorio);
    }
    
    public int update(Laboratorio laboratorio) throws Exception{
        return datos.update(laboratorio);
    }
    
    
}
