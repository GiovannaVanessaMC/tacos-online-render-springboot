package com.lm2a.tacosonline.api;


import com.lm2a.tacosonline.data.TacoRepository;
import com.lm2a.tacosonline.model.Taco;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping(path = "/rest", produces="application/json")
@RestController
public class DesignTacoControllerAPI {

    TacoRepository tacoRepository;

    public DesignTacoControllerAPI(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

//    @GetMapping("/{id}")
//    public Taco tacoById(@PathVariable Long id){
//        Optional<Taco> taco = tacoRepository.findById(id);
//        return taco.orElse(null);
//    }

    //traer solo un id
    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable Long id){
        Optional<Taco> tacoOptional = tacoRepository.findById(id);
        if(tacoOptional.isPresent()){
            return ResponseEntity.ok(tacoOptional.get());
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    //traer todos
    @GetMapping()
    public Iterable<Taco> allTacos(){
        return tacoRepository.findAll();
    }

    //crear
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Taco postTaco(@RequestBody Taco taco){
        return tacoRepository.save(taco);
    }

//   @DeleteMapping("/{id}")
//    public void deleteTaco(@PathVariable Long id){
//        tacoRepository.deleteById(id);
//    }

    //Borrado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaco(@PathVariable Long id){
    if(tacoRepository.existsById(id)){
        tacoRepository.deleteById(id);
        //devuelve 204
        return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //Actualizar
//    @PutMapping("/{id}")
//    public ResponseEntity<Taco> updateTaco(@PathVariable Long id, @RequestBody Taco taco){
//        Optional<Taco> tacoOptional = tacoRepository.findById(id);
//        if(tacoOptional.isPresent()){
//            Taco existingTaco = tacoOptional.get();
//            existingTaco.setName(taco.getName());
//            Taco updatedTaco = tacoRepository.save(existingTaco);
//            return ResponseEntity.ok(updatedTaco);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @PutMapping
    public ResponseEntity<Taco> updateTaco(@RequestBody Taco taco){

        Optional<Taco> tacoOptional = tacoRepository.findById(taco.getId());

        if(tacoOptional.isPresent()){
            Taco existingTaco = tacoOptional.get();
            existingTaco.setName(taco.getName());

            Taco updatedTaco = tacoRepository.save(existingTaco);
            return ResponseEntity.ok(updatedTaco);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/recent/page/{id}")
    public Iterable<Taco> recentTacos(@PathVariable int id) {
        PageRequest page = PageRequest.of(0, 5, Sort.by("createdAt").descending());
       return tacoRepository.findAll(page);

    }

}
