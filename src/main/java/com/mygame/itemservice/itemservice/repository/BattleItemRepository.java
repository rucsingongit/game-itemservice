package com.mygame.itemservice.itemservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.mygame.itemservice.itemservice.entity.BattleItem;

import java.util.Optional;

public interface BattleItemRepository extends CrudRepository<BattleItem, Long> {

    @Query("select bi from BattleItem bi where bi.name=?1")
    Optional<BattleItem> findByItemName(String name);

}
