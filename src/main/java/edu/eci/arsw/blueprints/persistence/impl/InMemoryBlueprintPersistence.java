/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author hcadavid
 */
@Component
@Qualifier("inMemory")
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final ConcurrentHashMap<Tuple<String,String>,Blueprint> blueprints=new ConcurrentHashMap<>();
    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp=new Blueprint("Bigjose", "plano1",pts);
        Point[] pts2=new Point[]{new Point(25, 78),new Point(156, 478)};
        Blueprint bp2=new Blueprint("Bigjose", "plano2",pts2);
        Point[] pts3=new Point[]{new Point(84, 65),new Point(658, 365)};
        Blueprint bp3=new Blueprint("suescaguy", "planosu",pts3);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3);

    }


    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        //System.out.println("llega");
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{

            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {

        Blueprint bp = blueprints.get(new Tuple<>(author, bprintname));
        if (bp == null){
            throw new BlueprintNotFoundException("No existe este Blueprint");
        }
        return  blueprints.get(new Tuple<>(author, bprintname));
    }


    @Override
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
        Set<Blueprint> authorBlueprints = new HashSet<>(blueprints.values());
        if (authorBlueprints.isEmpty()) {
            throw new BlueprintNotFoundException("No existe ningún plano ");
        }
        return authorBlueprints;
    }

    @Override
    public boolean putBlueprint(String author, String name, Blueprint bp) throws BlueprintPersistenceException  {
        boolean retorna;
        if (blueprints.containsKey(new Tuple<>(author,name))){
            blueprints.remove(new Tuple<>(author,name));
            saveBlueprint(bp);
            retorna=true;
        }
       else{retorna=false;}
        return retorna;
    }


    @Override
    public Set<Blueprint> getBluePrintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> bluePrints = new HashSet<>();

        for(Map.Entry<Tuple<String, String>, Blueprint> key: blueprints.entrySet()){

            if(key.getKey().o1.equals(author) ){
                bluePrints.add(key.getValue());
            }
        }
        if (bluePrints.isEmpty()) {
            throw new BlueprintNotFoundException("No existe ningún plano ");
        }
        return bluePrints;
    }





}
