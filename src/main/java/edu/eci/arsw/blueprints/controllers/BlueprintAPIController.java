/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.LinkedHashSet;
import java.util.Set;

import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
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

    @GetMapping("/blueprints")
    public ResponseEntity<?> getAllBluePrints() throws BlueprintNotFoundException {

        return new ResponseEntity<>(nombre.getAllBlueprints(), HttpStatus.OK);
    }

    @GetMapping("/blueprints/{author}")
    public ResponseEntity<?> getAuthorBlueprints(@PathVariable(value="author") String author) throws ResourceNotFoundException, BlueprintNotFoundException {

        return new ResponseEntity<>(nombre.getBlueprintsByAuthor(author), HttpStatus.OK);
    }

    @GetMapping("/blueprints/{author}/{name}")
    public ResponseEntity<?> getBlueprint(@PathVariable(value="author") String author,@PathVariable(value="name") String name)throws ResourceNotFoundException{

        try {
            return new ResponseEntity<>(nombre.getBlueprint(author, name), HttpStatus.OK);
        } catch (BlueprintNotFoundException e) {
            throw new ResourceNotFoundException();

        }
    }


}


