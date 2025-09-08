package com.simuladorcredito.entrypoint.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simuladorcredito.entrypoint.data.request.ClienteRequest;
import com.simuladorcredito.entrypoint.data.request.PedidoSimulacaoRequest;
import com.simuladorcredito.mapper.SimulacaoMapper;
import com.simuladorcredito.mapper.SimulacaoMapperImpl;
import com.simuladorcredito.service.SimulacaoServiceImpl;
import com.simuladorcredito.service.data.ClienteBusiness;
import com.simuladorcredito.service.data.PedidoSimulacaoBusiness;
import com.simuladorcredito.service.data.SolicitacaoSimulacaoBusiness;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SimulacaoCreditoController.class)
class SimulacaoCreditoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SimulacaoServiceImpl simulacaoService;

    @MockBean
    private SimulacaoMapper simulacaoMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve simular propostas em lote e retornar status 201 Created")
    void deveSimularCreditoEmLoteERetornarStatusCreated() throws Exception {
        PedidoSimulacaoRequest request = buildPedidoSimulacaoRequest();
        List<PedidoSimulacaoRequest> requestList = Collections.singletonList(request);

        PedidoSimulacaoBusiness business = buildPedidoSimulacaoBusiness();
        when(simulacaoMapper.simulacaoRequestToBusiness(anyList())).thenReturn(Collections.singletonList(business));
        doNothing().when(simulacaoService).simularEmLote(anyList());

        mockMvc.perform(post("/v1/simulacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestList)))
                .andExpect(status().isCreated());

        verify(simulacaoService).simularEmLote(anyList());
    }

    private PedidoSimulacaoRequest buildPedidoSimulacaoRequest() {
        return new PedidoSimulacaoRequest(
                new ClienteRequest("12345678900",LocalDate.of(1995, 5, 10), "teste@exemplo.com"),
                BigDecimal.valueOf(10000),
                24);
    }

    private PedidoSimulacaoBusiness buildPedidoSimulacaoBusiness() {
        return new PedidoSimulacaoBusiness(null,
                new ClienteBusiness("12345678900",LocalDate.of(1995, 5, 10), "teste@exemplo.com"),
                new SolicitacaoSimulacaoBusiness(BigDecimal.valueOf(10000), 24, BigDecimal.valueOf(0.05), null),
                LocalDateTime.now());
    }
}