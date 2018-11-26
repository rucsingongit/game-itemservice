package com.mygame.itemservice.itemservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mygame.itemservice.itemservice.entity.BattleItem;
import com.mygame.itemservice.itemservice.exceptions.EntityNotFoundException;
import com.mygame.itemservice.itemservice.service.BattleItemService;
import com.mygame.itemservice.itemservice.service.BattleItemServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(value = BattleItemsController.class, secure = false)
public class BattleItemsControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BattleItemsControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BattleItemService service;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /*Acceptance Criteria

    Scenario: Create where object not in data store.
    Given An object of type  "item" with the format { "id": 1, "name": "sword" }, that is not in the data store.
    When a POST call is made to /object/create/{className}
    Then The object is registered in the data store and a 200 is returned.
    All objects have an "id" field that holds their key value.

    Scenario: Create where object is in data store.
    Given An object of type  "item" with the format { "id": 1, "name": "sword" }, that is in the data store.
    When a POST call is made to /object/create/{className}
    Then The object that has type=="item" and "id"==1 will be overwritten in the data store and a 200 is returned.
    All objects have an "id" field that holds their key value.

    Scenario: Delete item that IS in the data store.
    Given An object of type  "item" with the format { "id": 1, "name": "sword" }
    When a POST call is made to /object/delete/{className}/{id}
    Then The object is deleted from the data store and a 200 is returned.

    Scenario: Delete item that is NOT in the data store.
    Given An object of type  "item" with the format { "id": 1, "name": "sword" }
    When a POST call is made to /object/delete/{className}/{id}
    Then The object is not found and the service returns 404.

    Scenario: Get a specific item.
    Given An object of type  "item" with the format { "id": 1, "name": "sword" } is in the data store.
    When a GET call is made to /object/get/{className}/{id}
    Then The object is returned in json format along with a 200 code.

            Scenario: Get a specific item but it is not in the data store.
    Given An object of type  "item" with the format { "id": 1, "name": "sword" } is not in the data store.
    When a GET call is made to /object/get/{className}/{id}
    Then The service returns 404.

    Scenario: Get all items of a specific type.
    Given Several objects of type  "item" are in the data store.
    When a GET call is made to /object/get/{className}
    Then The service returns a JSON array of all the items that match the className (could be an empty array) and a 200.

    Scenario: Get all items in the registry.
    Given Several objects are in the data store.
    When a GET call is made to /object/get/
    Then The service returns a JSON array of all the items in the data store(could be an empty array) and a 200.

    Notes:
    The game object registry should store any object as a name value pair. The name should hold the class name, and the value should hold the json representation of the object.*/

    @Test
    public void getAllBattleItemsTest() throws Exception {
        when(service.getAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/object")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andReturn();

        verify(service, Mockito.times(1)).getAll();
    }

    @Test
    public void getBattleItemByCorrectIdTest() throws Exception {
        BattleItem battleItem = new BattleItem();
        when(service.getById(anyLong())).thenReturn(battleItem);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/object/get/id/{id}", anyLong())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andReturn();

        verify(service, Mockito.times(1)).getById(anyLong());
    }

    @Test
    public void getMovieByWrongIdTest() throws Exception {
        when(service.getById(anyLong())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/object/get/id/{id}", anyLong())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();

        verify(service, Mockito.times(1)).getById(anyLong());
    }

    @Test
    public void createBattleItemTest() throws Exception {
        BattleItem battleItem = new BattleItem("name1");

        when(service.create(battleItem)).thenReturn(battleItem);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/object/create/name1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsBytes(battleItem))
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andReturn();

        verify(service, times(1)).create(battleItem);
    }

    @Test
    public void updateBattleItemTest() throws Exception {
        BattleItem battleItem = new BattleItem("club");
        when(service.update(battleItem)).thenReturn(battleItem);

        mockMvc.perform(MockMvcRequestBuilders
                .put("object/update/{name}", "club")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsBytes(battleItem))
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andReturn();

        verify(service, times(1)).update(battleItem);
    }

    @Test
    public void deleteByIdCorrectId() throws Exception {
        doNothing().when(service).deleteById(anyLong());

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/object/delete/id/{id}", anyLong()))
                .andExpect(status().isOk())
                .andDo(print());

        verify(service, times(1)).deleteById(anyLong());
    }

    @Test
    public void deleteMovieByWrongIdTest() throws Exception {
        doThrow(BattleItemsController.BattleItemNotFoundException.class).when(service).deleteById(anyLong());

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/object/delete/id/{id}", anyLong()))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(service, times(1)).deleteById(anyLong());
    }

}