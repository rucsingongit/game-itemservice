package com.mygame.itemservice.itemservice.service;

import com.mygame.itemservice.itemservice.entity.BattleItem;
import org.springframework.stereotype.Service;

@Service
public interface BattleItemService {

    public Iterable<BattleItem> getAll();

    public BattleItem getById(Long id);

    public BattleItem getByName(String name);

    public BattleItem  create(BattleItem battleItem);

    public BattleItem update(BattleItem item);

    public void deleteByName(String name);

    public void deleteById(Long id);

}
