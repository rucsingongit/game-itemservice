package com.mygame.itemservice.itemservice.service;

import com.mygame.itemservice.itemservice.entity.BattleItem;
import com.mygame.itemservice.itemservice.exceptions.EntityNotFoundException;
import com.mygame.itemservice.itemservice.repository.BattleItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BattleItemServiceImpl implements BattleItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BattleItemService.class);

    @Autowired
    private BattleItemRepository battleItemRepository;

    public Iterable<BattleItem> getAll() {

        return this.battleItemRepository.findAll();
    }

    public BattleItem getById(Long id)
    {
        Optional<BattleItem> optBattleItem = this.battleItemRepository.findById(id);
        if(!optBattleItem.isPresent()){
            throw new EntityNotFoundException();
        }
        return optBattleItem.get();
    }

    public BattleItem getByName(String name)
    {
        Optional<BattleItem> optBattleItem = this.battleItemRepository.findByItemName(name);
        if(!optBattleItem.isPresent()) {
            throw new EntityNotFoundException();
        }
        return optBattleItem.get();
    }


    public BattleItem create(BattleItem item) {

        return this.battleItemRepository.save(item);
    }

    public BattleItem update(BattleItem item) {
        Optional<BattleItem> optBattleItem = battleItemRepository.findById(item.getId());
        if (!optBattleItem.isPresent())
            throw new EntityNotFoundException();
        BattleItem battleItem = optBattleItem.get();
        battleItem.setName(item.getName());
        BattleItem updatedBattleItem = battleItemRepository.save(battleItem);
        return updatedBattleItem;
    }

    public void deleteById(Long id)
    {

        if (!this.battleItemRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        this.battleItemRepository.deleteById(id);
    }

    public void deleteByName(String name)
    {
        Optional<BattleItem> optBattleItem = this.battleItemRepository.findByItemName(name);
        if(!optBattleItem.isPresent()) {
            throw new EntityNotFoundException();
        }
        this.battleItemRepository.deleteById(optBattleItem.get().getId());
    }
}
