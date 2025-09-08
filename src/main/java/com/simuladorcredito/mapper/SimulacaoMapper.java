package com.simuladorcredito.mapper;

import com.simuladorcredito.entrypoint.data.request.PedidoSimulacaoRequest;
import com.simuladorcredito.gateway.repository.data.SimulacaoEntity;
import org.mapstruct.*;
import com.simuladorcredito.service.data.PedidoSimulacaoBusiness;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface SimulacaoMapper {

    List<PedidoSimulacaoBusiness> simulacaoRequestToBusiness(List<PedidoSimulacaoRequest> pedidoSimulacaoRequest);

    @Mapping( source = "valorTotalEmprestimo", target = "solicitacaoSimulacao.valorTotalEmprestimo")
    @Mapping( source = "prazo", target = "solicitacaoSimulacao.prazo")
    PedidoSimulacaoBusiness requestToBusiness(PedidoSimulacaoRequest pedidoSimulacaoRequest);

    SimulacaoEntity businessToEntity(PedidoSimulacaoBusiness pedidoSimulacaoBusiness);

    PedidoSimulacaoBusiness entityToBusiness(SimulacaoEntity simulacaoEntity);

}
