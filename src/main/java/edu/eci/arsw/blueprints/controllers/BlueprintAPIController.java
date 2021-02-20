/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.LinkedHashSet;
import java.util.Set;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/")
public class BlueprintAPIController {

    @Autowired
    @Qualifier("inMemory")
    BlueprintsPersistence nombre;

    //GETS
    @GetMapping("/blueprints")
    public ResponseEntity<?> getAllBluePrints() throws BlueprintNotFoundException {
        return new ResponseEntity<>(nombre.getAllBlueprints(), HttpStatus.OK);
    }

    @GetMapping("/blueprints/{author}")
    public ResponseEntity<?> getAuthorBlueprints(@PathVariable(value="author") String author)throws ResourceNotFoundException{

        try {
            return new ResponseEntity<>(nombre.getBluePrintsByAuthor(author), HttpStatus.OK);
        } catch (BlueprintNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping("/blueprints/{author}/{name}")
    public ResponseEntity<?> getBlueprint(@PathVariable(value="author") String author,@PathVariable(value="name") String name)throws ResourceNotFoundException{

        try {
            //System.out.println(nombre.getBlueprint(author, name));
            return new ResponseEntity<>(nombre.getBlueprint(author, name), HttpStatus.OK);
        } catch (BlueprintNotFoundException e) {
            throw new ResourceNotFoundException();

        }
    }
    //POST
    @PostMapping("/planos")
    public ResponseEntity<?> addBlueprint (@RequestBody Blueprint bp) throws ResourceNotFoundException {
        try{
            //System.out.println(bp.getAuthor());
            //System.out.println(bp.getName());
            //System.out.println(bp.getPoints());
            nombre.saveBlueprint(bp);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (BlueprintPersistenceException e) {
            throw new ResourceNotFoundException();
        }
    }
    //PUT
    @PutMapping("/blueprints/{author}/{name}")
    public ResponseEntity<?>  putBlueprint (@PathVariable(value="author") String author,@PathVariable(value="name") String name, @RequestBody Blueprint bp) throws ResourceNotFoundException, BlueprintPersistenceException, BlueprintNotFoundException {
        if(nombre.putBlueprint(author, name, bp)){
            return new ResponseEntity<>(nombre.getBlueprint(author, name),HttpStatus.OK);
        }else{
            throw new ResourceNotFoundException();
        }
    }



}

