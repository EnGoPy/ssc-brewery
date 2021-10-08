package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class IndexControllerIT extends BaseIT{

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
