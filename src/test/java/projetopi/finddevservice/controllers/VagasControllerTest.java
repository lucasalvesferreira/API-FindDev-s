package projetopi.finddevservice.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import projetopi.finddevservice.dtos.v1.request.VagaRequestDto;
import projetopi.finddevservice.dtos.v1.response.VagaResponseDto;
import projetopi.finddevservice.enums.FuncaoDev;
import projetopi.finddevservice.enums.SenioridadeDev;
import projetopi.finddevservice.exceptions.ResourceNotFoundException;
import projetopi.finddevservice.mapper.DozerMapper;
import projetopi.finddevservice.models.Vaga;
import projetopi.finddevservice.repositories.EmpresaRepository;
import projetopi.finddevservice.repositories.VagasRepository;
import projetopi.finddevservice.services.VagasService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = VagasController.class)
class VagasControllerTest {

    @Autowired
    VagasController controller;

    @MockBean
    VagasService service;

    @Test
    @DisplayName("Dada uma requisicao para salvar uma Vaga, quando o corpo for válido, deve retornar 201")
    void deveCriarVagaComSucesso() {
        VagaRequestDto request = new VagaRequestDto();
        request.setTitulo("Desenvolvedor Pleno Java");
        request.setDescricao("Desenvolver aplicacoes no backend em Spring");
        request.setFuncao(FuncaoDev.BACKEND);
        request.setSenioridade(SenioridadeDev.PLENO);

        Vaga entity = DozerMapper.parseObject(request, Vaga.class);
        VagaResponseDto expectedBody = DozerMapper.parseObject(entity, VagaResponseDto.class);

        when(
            service.create(request)
        ).thenReturn(expectedBody);

        ResponseEntity<VagaResponseDto> response = controller.save(request);

        assertNotNull(response.getBody());
        assertEquals(expectedBody, response.getBody());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    @DisplayName(
        "Dada uma requisicao para encontrar uma vaga, quando o id for valido, deve retornar 200 com a Vaga no body"
    )
    void deveEncontrarVagaComSucesso() {
        VagaResponseDto vaga = new VagaResponseDto();
        vaga.setKey(1);
        vaga.setTitulo("Desenvolvedor React Junior");
        vaga.setDescricao("Desenvolver front end em React");
        vaga.setFuncao(FuncaoDev.FRONTEND);
        vaga.setSenioridade(SenioridadeDev.JUNIOR);

        when(
            service.findById(1)
        ).thenReturn(vaga);

        ResponseEntity<VagaResponseDto> response = controller.findById(1);

        assertNotNull(response.getBody());
        assertEquals(vaga, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Dada uma requisicao para encontrar uma vaga, quando o id nao for valido, deve retornar 404")
    void deveRetornar404QuandoNaoEncontrarVaga() {
        when(
            service.findById(1)
        ).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> controller.findById(1));
    }

    @Test
    @DisplayName(
        "Dada uma requisicao para encontrar vagas de uma empresa, " +
            "quando o id da empresa nao for encontrado, deve retornar 404"
    )
    void deveRetornar404QuandoIdEmpresaNaoForEncontrado() {
        UUID idEmpresa = UUID.randomUUID();

        when(
            service.findAllByIdEmpresa(idEmpresa)
        ).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> controller.findAllByIdEmpresa(idEmpresa));
    }

    @Test
    @DisplayName(
        "Dada uma requisição para encontrar vagas de uma empresa, quando a lista vier vazia, deve retornar 204"
    )
    void deveRetornar204QuandoListaVagasVazia() {
        UUID idEmpresa = UUID.randomUUID();

        when(
            service.findAllByIdEmpresa(idEmpresa)
        ).thenReturn(emptyList());

        ResponseEntity<List<VagaResponseDto>> response = controller.findAllByIdEmpresa(idEmpresa);

        assertNull(response.getBody());
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Dada uma requisição para encontrar vagas de uma empresa, deve retornar 200 com lista no body")
    void deveRetornarVagasDaEmpresaComSucesso() {
        VagaResponseDto vaga1 = new VagaResponseDto();
        VagaResponseDto vaga2 = new VagaResponseDto();
        List<VagaResponseDto> vagas = List.of(vaga1, vaga2);
        UUID idEmpresa = UUID.randomUUID();

        when(
            service.findAllByIdEmpresa(idEmpresa)
        ).thenReturn(vagas);

        ResponseEntity<List<VagaResponseDto>> response = controller.findAllByIdEmpresa(idEmpresa);

        assertNotNull(response.getBody());
        assertEquals(vagas, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }
}