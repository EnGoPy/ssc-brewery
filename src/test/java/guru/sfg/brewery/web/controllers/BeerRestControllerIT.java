package guru.sfg.brewery.web.controllers;

import guru.sfg.brewery.domain.Beer;
import guru.sfg.brewery.repositories.BeerOrderRepository;
import guru.sfg.brewery.repositories.BeerRepository;
import guru.sfg.brewery.web.model.BeerStyleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Random;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BeerRestControllerIT extends BaseIT{

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerOrderRepository beerOrderRepository;

    private static final String URL_USERNAME = "spring";
    private static final String URL_PASSWORD = "guru";


    public Beer existingBeer(){
        Random rand = new Random();
        return beerRepository.saveAndFlush(Beer.builder()
                .beerName("Delete Me Beer")
                .beerStyle(BeerStyleEnum.IPA)
                .minOnHand(12)
                .quantityToBrew(200)
                .upc(String.valueOf(rand.nextInt(999999999)))
                .build());
    }

    @DisplayName("Searching test")
    @Nested
    class SearchTest{

        @Test
        void findByUpcADMIN() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beerUpc/"+existingBeer().getUpc())
                            .with(httpBasic("spring", "guru")))
                    .andExpect(status().isOk());
        }

        @Test
        void findByUpcUSER() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beerUpc/"+existingBeer().getUpc())
                            .with(httpBasic("user", "password")))
                    .andExpect(status().isOk());
        }

        @Test
        void findByUpcCUSTOMER() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beerUpc/"+existingBeer().getUpc())
                            .with(httpBasic("scott", "tiger")))
                    .andExpect(status().isOk());
        }

        @Test
        void findByUpcNOAUTH() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beerUpc/"+existingBeer().getUpc()))
                    .andExpect(status().isUnauthorized());
        }


    }

    @DisplayName("Delete Tests")
    @Nested
    class DeleteTests{

        @Test
        void findBeerFormADMIN() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders.get("/beers")
                            .param("beerName", "")
                            .with(httpBasic("spring", "guru")))
                    .andExpect(status().isOk());
        }

        @Test
        void listBreweriesHttpBasicCustomerRole() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders.get("/brewery/api/v1/breweries")
                            .with(httpBasic("scott", "tiger")))
                    .andExpect(status().is2xxSuccessful());
        }

        @Test
        void listBreweriesHttpBasicUserRole() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders.get("/brewery/api/v1/breweries")
                            .with(httpBasic("user", "password")))
                    .andExpect(status().isForbidden());
        }

        @Test
        void listBreweriesHttpBasicUnauthenticated() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/breweries/"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void deleteBeerHttpBasicCustomerRole() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/"+ existingBeer().getId())
                            .with(httpBasic("scott", "tiger")))
                    .andExpect(status().isForbidden());
        }

        @Test
        void deleteBeerHttpBasicUserRole() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/"+ existingBeer().getId())
                            .with(httpBasic("user", "password")))
                    .andExpect(status().isForbidden());
        }

        @Test
        void deleteBeerUrlCredentials () throws Exception{
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/"+ existingBeer().getId())
                            .param("username", URL_USERNAME).param("password", URL_PASSWORD))
                    .andExpect(status().isOk());
        }

        @Test
        void deleteBeerBadCreds() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/"+ existingBeer().getId())
                            .header("Api-Key", "spring").header("Api-Secret", "guruXXXX"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void deleteBeer() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/"+ existingBeer().getId())
                            .header("Api-Key", "spring").header("Api-Secret", "guru"))
                    .andExpect(status().isOk());
        }

        @Test
        void deleteBeerHttpBasic() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/"+ existingBeer().getId())
                            .with(httpBasic("spring", "guru")))
                    .andExpect(status().is2xxSuccessful());
        }

        @Test
        void deleteBeerNoAuth() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beer/"+ existingBeer().getId()))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void findBeers() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void findBeerById() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer/"+ existingBeer().getId()))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        void findBeerByUpc() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beerUpc/"+ existingBeer().getUpc()))
                    .andExpect(status().isUnauthorized());
        }
    }
}
