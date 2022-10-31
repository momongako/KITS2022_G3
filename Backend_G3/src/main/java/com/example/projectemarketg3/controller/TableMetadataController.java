package com.example.projectemarketg3.controller;

import com.example.projectemarketg3.entity.Category;
import com.example.projectemarketg3.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/metadata/{table}")
public class TableMetadataController{

    @GetMapping("/columns")
    public List<String> getColumnNames(@PathVariable String table){

        Class<?> entity = null;
        try {
            entity = Class.forName("com.example.projectemarketg3.entity."+table);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        List<String> Columns = new ArrayList<String>();
        assert entity != null;
        Field[] fields = entity.getDeclaredFields();

        for (Field field : fields) {
            Column col = field.getAnnotation(Column.class);
            if (col != null) {
                Columns.add(col.name());
            }
        }
        System.out.println(Columns);
        return Columns;
    }

    @GetMapping("/variables")
    public List<String> getVariableNames(@PathVariable String table){

        Class<?> entity = null;
        try {
            entity = Class.forName("com.example.projectemarketg3.entity."+table);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        List<String> Columns = new ArrayList<String>();
        assert entity != null;
        Field[] fields = entity.getDeclaredFields();

        for (Field field : fields) {
            Columns.add(field.getName());
        }
        return Columns;
    }


}
