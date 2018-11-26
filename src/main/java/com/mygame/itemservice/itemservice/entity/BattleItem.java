package com.mygame.itemservice.itemservice.entity;


import javax.persistence.*;

@Entity
public class BattleItem {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String name;

    public BattleItem(){
    }

    public BattleItem(String name) {
        this.name = name;
    }

    public BattleItem(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
