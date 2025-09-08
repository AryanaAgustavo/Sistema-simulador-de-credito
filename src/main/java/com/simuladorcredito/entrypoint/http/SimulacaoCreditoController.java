package com.simuladorcredito.entrypoint.http;

import com.simuladorcredito.constants.UrlMapping;
import com.simuladorcredito.entrypoint.data.request.PedidoSimulacaoRequest;
import com.simuladorcredito.mapper.SimulacaoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.simuladorcredito.service.SimulacaoServiceImpl;

import java.util.List;

@Tag(name = "Simulação de Crédito", description = "Endpoints para simulação de propostas de crédito.")
@RestController
@RequestMapping()
public class SimulacaoCreditoController {

    private final SimulacaoServiceImpl simulacaoService;
    private final SimulacaoMapper simulacaoMapper;

    @Autowired
    public SimulacaoCreditoController(SimulacaoServiceImpl simulacaoService, SimulacaoMapper simulacaoMapper) {
        this.simulacaoService = simulacaoService;
        this.simulacaoMapper = simulacaoMapper;
    }

    @Operation(summary = "Simular propostas de crédito em lote", description = "Recebe uma lista de pedidos e inicia a simulação assíncrona para cada um.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Requisição de simulação recebida e processamento iniciado."),
    })
    @PostMapping(UrlMapping.SIMULACAO_CREDITO)
    @ResponseStatus(HttpStatus.CREATED)
    public void simularCreditoEmLote(@RequestBody List<PedidoSimulacaoRequest> pedidoSimulacaoList) {
        simulacaoService.simularEmLote(simulacaoMapper.simulacaoRequestToBusiness(pedidoSimulacaoList));
    }
}