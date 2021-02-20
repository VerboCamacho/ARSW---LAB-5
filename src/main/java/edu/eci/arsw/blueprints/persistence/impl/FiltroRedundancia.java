package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Qualifier("filtraredu")
public class FiltroRedundancia implements Filtro {
    private Point blue;
    private Blueprint bluepri;

    public void setBluepri(Blueprint entrada){
        bluepri =entrada;
        filtrar();
    }
    public void filtrar(){
            Point[] pts= bluepri.getPoints().toArray(new Point[0]);
            ArrayList<Point> puntosF=new ArrayList<Point>();
            blue=pts[0];
            puntosF.add(blue);
            for(int i=0;i<pts.length-1;i++){
                //System.out.println(pts[i+1].getX()+" "+pts[i+1].getY() );
                //System.out.println(blue.getX()+" "+blue.getY());
                if(pts[i+1].getX()== blue.getX()&&pts[i+1].getY()== blue.getY()){
                    //System.out.println("iguales");
                    //no los añade por que estan consecutivos
                }else{
                    Point punto=new Point(pts[i+1].getX(),pts[i+1].getY());
                    //System.out.println("diferentes");
                    puntosF.add(punto);
                }
                blue=pts[i+1];

            }
//            for (Point p:puntosF){
//                System.out.println(p.getX()+" "+p.getY());
//            }
            bluepri.setPuntos(puntosF);

    }
}
