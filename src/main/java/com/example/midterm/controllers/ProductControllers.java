package com.example.midterm.controllers;


import com.example.midterm.dao.ShopDAO;
import com.example.midterm.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/shop")
public class ProductControllers {

    private final ShopDAO shopDAO;

    @Autowired
    public ProductControllers(ShopDAO shopDAO) {
        this.shopDAO = shopDAO;
    }


    @GetMapping("productList")
    public String index(Model model){
        model.addAttribute("products", shopDAO.index());
        return "products/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("product", shopDAO.show(id));
        return "products/show";
    }

    @GetMapping("/new")
    public String newProduct(Model model){
        model.addAttribute("product", new Product());
        return "products/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("product") @Valid Product product,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "products/new";

        shopDAO.save(product);
        return "redirect:/shop/productList";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("product", shopDAO.show(id));
        return "products/edit";
    }


    @PatchMapping("/{id}")
    public String update(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "products/edit";

        shopDAO.update(id, product);
        return "redirect:/shop/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        shopDAO.delete(id);
        return "redirect:/shop/productList";
    }



}
