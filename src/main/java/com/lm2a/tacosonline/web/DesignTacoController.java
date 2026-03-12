package com.lm2a.tacosonline.web;


import com.lm2a.tacosonline.data.IngredientRepository;
import com.lm2a.tacosonline.data.TacoRepository;
import com.lm2a.tacosonline.model.Ingredient;
import com.lm2a.tacosonline.model.Ingredient.Type;
import com.lm2a.tacosonline.model.Order;
import com.lm2a.tacosonline.model.Taco;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    IngredientRepository ingredientRepository;
    TacoRepository tacoRepository;

    public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepository) {
        this.ingredientRepository = ingredientRepository;
        this.tacoRepository = tacoRepository;
    }

    @GetMapping
    public String showDesignForm(Model model){
        fillModel(model);

        model.addAttribute("tacos", tacoRepository.findAll());
        return "design";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(ingredient -> ingredient.getType() == type).toList();
    }

    @ModelAttribute("order")
    public Order order(){
        return new Order();
    }

    @ModelAttribute("tktn")
    public Taco taco(){
        return new Taco();
    }

    @PostMapping
    public String processDesign(@Valid @ModelAttribute("tktn") Taco tacoDesign, Errors errors, Model model, @ModelAttribute Order order){

        if(errors.hasErrors()){
            fillModel(model);
            return "design";
        }
        //TacoRepository.save(taco);
        Taco saved = tacoRepository.save(tacoDesign);
        order.addDesign(saved);

        log.info("Processing design: {}", saved);
        return "redirect:/orders/current";
    }
    //borrado en la vista con boton de eliminar
    @PostMapping("/delete/{id}")
    public String deleteTaco(@PathVariable Long id){
        tacoRepository.deleteById(id);
        return "redirect:/design";
    }


    private void fillModel(Model model){

        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);
        Type[] values = Type.values();
        for(Type type : values){
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }
}
