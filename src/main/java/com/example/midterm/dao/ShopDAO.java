package com.example.midterm.dao;

import com.example.midterm.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Component
public class ShopDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ShopDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> index() {
        return jdbcTemplate.query("SELECT * FROM products", new BeanPropertyRowMapper<>(Product.class));
    }

    public Product show(int id ){
        return jdbcTemplate.query("Select * from Products where id =?", new Object[]{id}, new BeanPropertyRowMapper<>(Product.class))
                .stream().findAny().orElse(null);
    }

    public void save(Product product) {
        jdbcTemplate.update(
                "INSERT INTO Products (name, category, manufacturer, volume, price) VALUES(?, ?, ?,?,?)",
                product.getName(), product.getCategory(), product.getManufacturer(), product.getVolume(), product.getPrice());
    }

    public void update(int id, Product updatedProduct) {
        jdbcTemplate.update("UPDATE products SET name=?, category=?, manufacturer=?, volume=?, price=? WHERE id=?",
                updatedProduct.getName(), updatedProduct.getCategory(), updatedProduct.getManufacturer(),updatedProduct.getVolume(),updatedProduct.getPrice(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM products WHERE id=?", id);
    }
}
