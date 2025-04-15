package com.springbatch.parkapi.web.controller;

import com.springbatch.parkapi.entity.Usuario;
import com.springbatch.parkapi.service.UsuarioService;
import com.springbatch.parkapi.web.dto.UsuarioCreateDto;
import com.springbatch.parkapi.web.dto.UsuarioResponseDto;
import com.springbatch.parkapi.web.dto.UsuarioSenhaDto;
import com.springbatch.parkapi.web.dto.mapper.UsuarioMapper;
import com.springbatch.parkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuarios", description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de um usuário.")
@RequiredArgsConstructor
@RestController
@RequestMapping("")
public class DefaultController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Listar todos os usuários", description = "Listar todos os usuários cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista com todos os usuários cadastrados",
                            content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDto.class))))
            })
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAll() {
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDto(users));
    }
}
