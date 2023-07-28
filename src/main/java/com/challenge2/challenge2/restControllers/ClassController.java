package com.challenge2.challenge2.restControllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge2.challenge2.entities.Classes;
import com.challenge2.challenge2.exceptions.BadRequestException;
import com.challenge2.challenge2.exceptions.InternalServerErrorException;
import com.challenge2.challenge2.exceptions.NotFoundException;
import com.challenge2.challenge2.services.impl.ClassServiceImpl;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    private ClassServiceImpl classService;

    public ClassController(ClassServiceImpl classService){
        this.classService = classService;
    }

    @GetMapping
    public List<Classes> getAllClasses(){
        return classService.getAllClasses();
    }

    @GetMapping("/{id}")
    public Classes getClassById(@PathVariable Long id){
        return classService.getClassById(id)
                .orElseThrow( () -> 
                    new NotFoundException("Class not found!"));
    }
    
    @PostMapping
    public ResponseEntity<Classes> addClass(@RequestBody Classes classes){    
        Classes savedClass = classService.saveClass(classes);
        return new ResponseEntity<Classes>(savedClass, HttpStatus.CREATED);    
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClass(@PathVariable Long id) {
        return classService.getClassById(id).map(entidade -> {
            classService.deleteClass(entidade.getId());
            return new ResponseEntity<String>("Class removed successfully.", HttpStatus.OK);
        }).orElseThrow(() -> new NotFoundException("Class not found!"));      
    }

    @PutMapping
    public ResponseEntity<String> updateClass(@RequestBody Classes classes) {
        try{
            return classService.getClassById(classes.getId())
                .map(entidade -> {
                    classService.saveClass(entidade);
                    return new ResponseEntity<String>("Class updated successfully.", HttpStatus.OK);
                })
                .orElseThrow(() -> new NotFoundException("Class not found!"));      
        }
        catch(Exception e){
            throw new BadRequestException("Something went wrong!");
        }       
    }
}