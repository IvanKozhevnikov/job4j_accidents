package ru.job4j.accidents.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.data.AccidentService;
import ru.job4j.accidents.service.data.AccidentTypeService;
import ru.job4j.accidents.service.data.RuleService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@Transactional
class AccidentControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccidentService accidentService;

    @MockBean
    private RuleService ruleService;

    @MockBean
    private AccidentTypeService accidentTypeService;

    private static List<AccidentType> accidentTypes;

    private static Accident expectedAccident;

    private static List<Rule> rules;

    @BeforeAll
    public static void init() {

        accidentTypes = List.of(
                new AccidentType(1, "Две машины"),
                new AccidentType(2, "Машина и человек"),
                new AccidentType(3, "Машина и велосипед"));

        expectedAccident = new Accident();
        expectedAccident.setId(1);
        expectedAccident.setName("test1");
        expectedAccident.setType(accidentTypes.get(1));

        rules = List.of(
                new Rule(1, "Статья. 1"),
                new Rule(2, "Статья. 2"),
                new Rule(3, "Статья. 3"));
        expectedAccident.setRules(Set.of(rules.get(1)));
    }

    @Test
    @WithMockUser
    @Transactional
    public void whenGetByIdForUpdateViewThenReturn() throws Exception {
          Mockito.when(this.accidentService.findById(1)).thenReturn(Optional.of(expectedAccident));
          Mockito.when(this.accidentTypeService.findAll()).thenReturn(accidentTypes);

        this.mockMvc.perform(get("/accidents/formUpdateAccident?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/updateAccident"))
                .andExpect(model().attribute("accident", expectedAccident))
                .andExpect(model().attribute("types", accidentTypes));
    }

    @Test
    @WithMockUser
    @Transactional
    public void whenGetCreateAccidentViewThenReturnCreateView() throws Exception {
        Mockito.when(this.accidentTypeService.findAll()).thenReturn(accidentTypes);
        Mockito.when(this.ruleService.findAll()).thenReturn(rules);

        this.mockMvc.perform(get("/accidents/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/createAccident"))
                .andExpect(model().attribute("types", accidentTypes))
                .andExpect(model().attribute("rules", rules));
    }

    @Test
    @WithMockUser
    @Transactional
    void whenGetByIdForUpdateThenReturn404() throws Exception {
        Mockito.when(this.accidentService.findById(2)).thenReturn(Optional.empty());

        mockMvc.perform(get("/accidents/formUpdateAccident?id=2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("errors/404"));
    }

    @Test
    @WithMockUser
    @Transactional
    void saveAccident() throws Exception {
        this.mockMvc.perform(post("/accidents/saveAccident")
                .param("name", "name")
                .param("text", "text")
                .param("address", "address")
                .param("type.id", "2")
                .param("rIds", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Accident> argumentCaptor = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).create(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getName()).isEqualTo("name");
        assertThat(argumentCaptor.getValue().getText()).isEqualTo("text");
    }



    @Test
    @WithMockUser
    @Transactional
    void updateAccident() throws Exception {
        this.mockMvc.perform(post("/accidents/updateAccident")
                .param("name", "name")
                .param("text", "text")
                .param("address", "address")
                .param("type.id", "2")
                .param("rIds", "1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
        ArgumentCaptor<Accident> argumentCaptor = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).update(argumentCaptor.capture());
        Accident capturedAccident = argumentCaptor.getValue();
        assertThat(capturedAccident.getName()).isEqualTo("name");
        assertThat(capturedAccident.getText()).isEqualTo("text");
        assertThat(capturedAccident.getAddress()).isEqualTo("address");
        assertThat(capturedAccident.getType().getId()).isEqualTo(2);
    }
}