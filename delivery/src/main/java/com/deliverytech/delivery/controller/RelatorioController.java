package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.ApiResponseWrapper;
import com.deliverytech.delivery.dto.VendasPorRestauranteDTO;
import com.deliverytech.delivery.service.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
@CrossOrigin(origins = "*")
@Tag(name = "Relatórios", description = "Operações relacionadas a relatórios de vendas")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/vendas-por-restaurante")
    @Operation(summary = "Relatório de vendas por restaurante",
               description = "Gera um relatório de vendas de todos os restaurantes no período especificado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    public ResponseEntity<ApiResponseWrapper<List<VendasPorRestauranteDTO>>> vendasPorRestaurante(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        List<VendasPorRestauranteDTO> relatorio =
                relatorioService.vendasPorRestaurante(dataInicio, dataFim);

        ApiResponseWrapper<List<VendasPorRestauranteDTO>> response =
                new ApiResponseWrapper<>(true, relatorio, "Relatório gerado com sucesso");

        return ResponseEntity.ok(response);
    }
}
