package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class BeerRestControllerIT extends BaseIT{

    @Test
    void findBeers() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerById() throws Exception{
        UUID generatedUUid = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer/"+generatedUUid))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByUpc() throws Exception{
        UUID generatedUUid = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beerUpc/v35gfbgdtbh45h4"))
                .andExpect(status().isOk());
    }


}
