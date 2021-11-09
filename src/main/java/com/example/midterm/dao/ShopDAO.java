package com.example.midterm.dao;

import com.example.midterm.models.Order;
import com.example.midterm.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@Component
public class ShopDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ShopDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> index() {
        return jdbcTemplate.query("SELECT * FROM products order by id", new BeanPropertyRowMapper<>(Product.class));
    }

    public List<Order> indexOrders() {
        return jdbcTemplate.query("SELECT products.id, products.name," +
                "products.category, products.manufacturer, products.volume, products.price," +
                "cart.quantity FROM cart inner join products on products.id=cart.product_id order by products.name", new BeanPropertyRowMapper<>(Order.class));
    }

    public void checkOrderAndChange(int id){
        Order isExist = jdbcTemplate.query("SELECT products.id, products.name," +
                "products.category, products.manufacturer, products.volume, products.price," +
                "cart.quantity FROM cart inner join products on products.id=cart.product_id where cart.product_id=?",new Object[]{id}, new BeanPropertyRowMapper<>(Order.class))
                .stream().findAny().orElse(null);
        if(isExist!=null){
            updateQuant(isExist);
        }else{
            saveOrder(id);
        }
        indexOrders();
    }

    public int refreshTotal(){
        return jdbcTemplate.queryForObject("select sum(products.price * cart.quantity) from cart\n" +
                "inner join products on cart.product_id = products.id",Integer.class);
    }

    public void updateQuant(Order order) {
        jdbcTemplate.update("UPDATE cart SET quantity=? WHERE product_id=?",
                order.getQuantity()+1, order.getId());
    }

    public void deleteOrder(int id) {
        jdbcTemplate.update("DELETE FROM cart WHERE product_id=?", id);

    }

    public void saveOrder(int id) {
        jdbcTemplate.update(
                "INSERT INTO cart (product_id, quantity) VALUES(?, ?)",
                id,1);
    }

    public List<Product> findByKeyword(String keyword){
       String queryKeyword = "%"+keyword+"%";
       String sql = "select * from products where name like ?";
        if (keyword != null) {
                return  jdbcTemplate.query(sql,
                        new Object[]{queryKeyword},new BeanPropertyRowMapper<>(Product.class));
            } else{
            return index();}
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
