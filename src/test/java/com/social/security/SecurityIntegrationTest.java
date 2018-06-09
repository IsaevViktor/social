package com.social.security;

import com.social.SocialApplication;
import com.social.conf.SecurityConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@SpringBootTest(classes = {SocialApplication.class, /*MvcConfig.class, SecurityConfig.class, PersistenceConfig.class, SecurityIntegrationTest.config.class*/})
@AutoConfigureMockMvc(secure = false)
public class SecurityIntegrationTest {
    /*@Configuration
    static class config {
        @Bean
        @Primary
        public UserDetailsService detailsService() {
            User basicUser = new User("basicUser",
                    "password",
                    asList(new SimpleGrantedAuthority("USER")));
            User adminUser = new User("admin", "password",
                    asList(new SimpleGrantedAuthority("USER"), new SimpleGrantedAuthority("ADMIN")));

            return new InMemoryUserDetailsManager(asList(adminUser, basicUser));
        }
    }*/

    @TestConfiguration
    @EnableWebSecurity
    @Order(0)
    static class Config extends SecurityConfig {
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            super.configure(auth);
            auth.inMemoryAuthentication()
                    .withUser("user")
                    .password(super.passwordEncoder.encode("password"))
                    .roles("USER");
        }
    }

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .<DefaultMockMvcBuilder>webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void accessPublicLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    public void accessPublicHome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void accessProtectedPage() throws Exception{
        mockMvc.perform(get("/protected"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void loginCorrectUserWithRedirect() throws Exception {
        MvcResult mvcResult = mockMvc.perform(formLogin().user("user").password("password"))
                .andExpect(authenticated())
                .andReturn();
        MockHttpSession httpSession = (MockHttpSession) mvcResult.getRequest().getSession(false);

        mockMvc.perform(get("/protected").session(httpSession))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "basicUser", roles = "USER")
    public void testCorrectUserWithMockUser() throws Exception {
        mockMvc.perform(get("/protected"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void loginIncorrectUser() throws Exception{
        mockMvc.perform(get("/protected"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void logout() throws Exception {
        MvcResult mvcResult = mockMvc.perform(formLogin().user("user").password("password"))
                .andExpect(authenticated())
                .andReturn();
        MockHttpSession httpSession = (MockHttpSession) mvcResult.getRequest().getSession(false);

        mockMvc.perform(post("/logout").session(httpSession))
                .andExpect(unauthenticated());
        mockMvc.perform(get("/protected").session(httpSession))
                .andExpect(status().is3xxRedirection())
                .andExpect(unauthenticated());
    }



}
