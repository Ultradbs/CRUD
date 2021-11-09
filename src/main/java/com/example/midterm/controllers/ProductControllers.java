package com.example.midterm.controllers;


import com.example.midterm.dao.ShopDAO;
import com.example.midterm.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/shop")
public class ProductControllers {

    @Autowired
    private final ShopDAO shopDAO;
    @Autowired
    public ProductControllers(ShopDAO shopDAO) {
        this.shopDAO = shopDAO;
    }

    public void refreshTotal(Model model){

    }

    @GetMapping("/cashbox")
    public String cashbox(String keyword, Model model ){
        showAllOrder(model);
        if (keyword !=null){
            model.addAttribute("products", shopDAO.findByKeyword(keyword));
            return "products/cashbox";
        } else{
        model.addAttribute("products", shopDAO.index());
            return "products/cashbox";}
    }

    public void showAllOrder(Model model){
        model.addAttribute("order", shopDAO.indexOrders());
        model.addAttribute("total", shopDAO.refreshTotal());
    }

    @PostMapping("add/{id}")
    public String addOrder(@PathVariable("id") int id,  Model model){
        shopDAO.checkOrderAndChange(id);
        model.addAttribute("products", shopDAO.index());

        showAllOrder(model);

        return "products/cashbox";
    }

    @DeleteMapping("delete/{id}")
    public String deleteOrder(@PathVariable("id") int id,  Model model) {
        shopDAO.deleteOrder(id);
        model.addAttribute("products", shopDAO.index());
        showAllOrder(model);
        return "products/cashbox";
    }

    @GetMapping("productList")
    public String index(String keyword, Model model){
        if (keyword !=null){
            model.addAttribute("products", shopDAO.findByKeyword(keyword));
            return "products/index";
        } else{
            model.addAttribute("products", shopDAO.index());
            return "products/index";}
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
