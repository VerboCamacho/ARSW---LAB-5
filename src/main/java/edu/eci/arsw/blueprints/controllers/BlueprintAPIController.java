/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        try{
            return new ResponseEntity<>(nombre.getAllBlueprints(), HttpStatus.OK);
        }catch (Exception ex){
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE,null,ex);
            return new ResponseEntity<>("No hay planos", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/blueprints/{author}")
    public ResponseEntity<?> getAuthorBlueprints(@PathVariable(value="author") String author)throws ResourceNotFoundException{

        try {

            return new ResponseEntity<>(nombre.getBluePrintsByAuthor(author), HttpStatus.OK);
        } catch (BlueprintNotFoundException e) {

            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE,null,e);
            return new ResponseEntity<>("No existe algún plano del autor", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/blueprints/{author}/{name}")
    public ResponseEntity<?> getBlueprint(@PathVariable(value="author") String author,@PathVariable(value="name") String name)throws ResourceNotFoundException{

        try {
            return new ResponseEntity<>(nombre.getBlueprint(author, name), HttpStatus.OK);
        } catch (BlueprintNotFoundException e) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE,null,e);
            return new ResponseEntity<>("No existe el plano", HttpStatus.NOT_FOUND);

        }
    }
    //POST
    @PostMapping("/planos")
    public ResponseEntity<?> addBlueprint (@RequestBody Blueprint bp) throws ResourceNotFoundException {
        try{
            nombre.saveBlueprint(bp);
            return new ResponseEntity<>("Se registro el plano exitosamente",HttpStatus.CREATED);
        }
        catch (BlueprintPersistenceException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE,null,ex);
            return new ResponseEntity<>("No se ha podido registrar el plano", HttpStatus.FORBIDDEN);
        }
    }
    //PUT
    @PutMapping("/blueprints/{author}/{name}")
    public ResponseEntity<?>  putBlueprint (@PathVariable(value="author") String author,@PathVariable(value="name") String name, @RequestBody Blueprint bp) throws ResourceNotFoundException, BlueprintPersistenceException, BlueprintNotFoundException {

        if(nombre.putBlueprint(author, name, bp)){
            return new ResponseEntity<>("Se ha actualizado el plano exitosamente",HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>("No se ha podido actualizar el plano",HttpStatus.FORBIDDEN);
        }
    }



}

