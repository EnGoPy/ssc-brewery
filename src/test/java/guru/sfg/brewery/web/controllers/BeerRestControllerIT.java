package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class BeerRestControllerIT extends BaseIT{

    private static final String URL_USERNAME = "spring";
    private static final String URL_PASSWORD = "guru";


    @Test
    void deleteBeerUrlCredentials () throws Exception{
        UUID generatedUUid = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/"+generatedUUid)
                        .param("username", URL_USERNAME).param("password", URL_PASSWORD))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeerBadCreds() throws Exception{
        UUID generatedUUid = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/"+generatedUUid)
                .header("Api-Key", "spring").header("Api-Secret", "guruXXXX"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteBeer() throws Exception{
        UUID generatedUUid = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/"+generatedUUid)
                .header("Api-Key", "spring").header("Api-Secret", "guru"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeerHttpBasic() throws Exception{
        UUID generatedUUid = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/"+generatedUUid)
                        .with(httpBasic("spring", "guru")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteBeerNoAuth() throws Exception{
        UUID generatedUUid = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/"+generatedUUid))
                .andExpect(status().isUnauthorized());
    }

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
