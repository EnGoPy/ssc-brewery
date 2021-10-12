package guru.sfg.brewery.web.controllers;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BreweryControllerTestIT extends BaseIT {

    //    /brewery/breweries/**
    @Test
    void getBreweriesCUSTOMER() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/brewery/breweries")
                .with(httpBasic("scott", "tiger")))
                .andExpect(status().isOk());
    }

    @Test
    void getBreweriesUSER() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/brewery/breweries")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void getBreweriesADMIN() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/brewery/breweries")
                        .with(httpBasic("spring", "guru")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void getBreweriesNOAUTH() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/brewery/breweries"))
                .andExpect(status().isUnauthorized());
    }
}
