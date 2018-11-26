package com.mygame.itemservice.itemservice.controller;


import com.mygame.itemservice.itemservice.entity.BattleItem;
import com.mygame.itemservice.itemservice.exceptions.EntityNotFoundException;
import com.mygame.itemservice.itemservice.service.BattleItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/object")
public class BattleItemsController {

    @Autowired
    private BattleItemService battleItemservice;

    @GetMapping
    public Iterable<BattleItem> all() {
        return this.battleItemservice.getAll();
    }

    @GetMapping("/get/id/{id}")
    public BattleItem getItemById(@PathVariable Long id) {
        try {
            return this.battleItemservice.getById(id);
        } catch (EntityNotFoundException e) {
            throw new BattleItemNotFoundException();
        }
    }

    @GetMapping("/get/name/{name}")
    public BattleItem getItemByName(@PathVariable String name) {
        try {
            return this.battleItemservice.getByName(name);
        } catch (EntityNotFoundException e) {
            throw new BattleItemNotFoundException();
        }

    }

    @PostMapping(value="/create/{name}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public BattleItem create(@RequestBody BattleItem item,
                             @PathVariable String name) {
        //Validate item name in Url path and JSON data are consistent
        if(!name.equals(item.getName())){
            throw new BattleItemBadRequestException();
        }
        return this.battleItemservice.create(item);
    }

    @PutMapping(value="/update/{name}")
    public BattleItem update(@RequestBody BattleItem item,
                             @PathVariable String name) {
        //Validate item name in Url path and JSON data are consistent
        /*if(!name.equals(item.getName())){
            throw new BattleItemBadRequestException();
        }*/
        return this.battleItemservice.update(item);
    }



    @DeleteMapping(value="/delete/id/{id}")
    public void deleteById(@PathVariable Long id) {
        try {
            this.battleItemservice.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new BattleItemNotFoundException();
        }
    }

    @DeleteMapping(value="/delete/name/{name}")
    public void deleteByName(@PathVariable String name){
        try {
            this.battleItemservice.deleteByName(name);
        } catch (EntityNotFoundException e) {
            throw new BattleItemNotFoundException();
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Battle Item by ID not found")
    class BattleItemNotFoundException extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Item name invalid/inconsistent")
    class BattleItemBadRequestException extends RuntimeException {
    }

}
