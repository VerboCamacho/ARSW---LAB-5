package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Qualifier("filtramue")
public class Filtrosubmuestreo implements Filtro{
    private Point blue;
    private Blueprint bluepri;


    public void setBluepri(Blueprint entrada){
        bluepri =entrada;
        filtrar();
    }
    public void filtrar(){
        Point[] pts= bluepri.getPoints().toArray(new Point[0]);
        ArrayList<Point> puntosF=new ArrayList<Point>();
        for(int i=1;i<pts.length+1;i++){
            //System.out.println(pts[i+1].getX()+" "+pts[i+1].getY() );
            //System.out.println(i%2);
            if(i%2==1){
                //System.out.println("entra");
                Point punto=new Point(pts[i-1].getX(),pts[i-1].getY());
                puntosF.add(punto);
            }
        }
        bluepri.setPuntos(puntosF);
    }
}
