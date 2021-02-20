package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.impl.Filtro;
import edu.eci.arsw.blueprints.persistence.impl.FiltroRedundancia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class FiltrosServices {
    @Autowired
    @Qualifier("filtraredu")
    Filtro redund;
    @Autowired
    @Qualifier("filtramue")
    Filtro muestre;

    public void filtroredu(Blueprint ob){
        redund.setBluepri(ob);
    }
    public void filtromue(Blueprint ob){

        muestre.setBluepri(ob);
    }
}
