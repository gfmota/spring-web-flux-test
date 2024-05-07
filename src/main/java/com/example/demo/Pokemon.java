package com.example.demo;

import lombok.Data;

@Data
public class Pokemon {

    private long id;

    private String name;

    public String toString() {
        return name + ' ' + String.valueOf(id);
    }
}
