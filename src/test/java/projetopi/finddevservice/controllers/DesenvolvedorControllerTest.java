package projetopi.finddevservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import projetopi.finddevservice.repositories.DesenvolvedorRepository;
import projetopi.finddevservice.services.DesenvolvedorService;

@SpringBootTest(classes = DesenvolvedorController.class)
class DesenvolvedorControllerTest {

    @Autowired
    DesenvolvedorController desenvolvedorController;
    @MockBean
    DesenvolvedorRepository repository;
    @MockBean
    DesenvolvedorService service;

//    @Test
//    void create() throws Exception {
//        DevelopRequestDto dto = Mockito.mock(DevelopRequestDto.class);
//        Mockito.when(service.create(dto)).thenReturn(dto);
//        ResponseEntity<DevelopRequestDto> response = desenvolvedorController.create(dto);
//        Mockito.verify(service,Mockito.times(1)).create(dto);
//        assertEquals(400,response.getStatusCode());
//
//    }
}