package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.repositories.BeerInventoryRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.repositories.CustomerRepository;
import guru.sfg.brewery.services.BeerService;
import guru.sfg.brewery.services.BreweryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class IndexControllerIT extends BaseIT{

    @MockBean
    BeerRepository beerRepository;
    @MockBean
    BeerInventoryRepository beerInventoryRepository;
    @MockBean
    BreweryService breweryService;
    @MockBean
    CustomerRepository customerRepository;
    @MockBean
    BeerService beerService;

    @Test
    public void testGetIndexSlash() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetLoginPage() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get( "/login"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetFindBeers() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get( "/beers/find"))
                .andExpect(view().name("beers/findBeers"))
                .andExpect(model().attributeExists("beer"))
                .andExpect(status().isOk());
    }

}
