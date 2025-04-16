package com.springbatch.parkapi.service;

import com.springbatch.parkapi.entity.Usuario;
import com.springbatch.parkapi.exception.EntityNotFoundException;
import com.springbatch.parkapi.exception.PasswordInvalidException;
import com.springbatch.parkapi.exception.UsernameUniqueViolationException;
import com.springbatch.parkapi.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void salvarUsuario_ComDadosValidos_RetornaUsuarioSalvo() {
        // Arrange
        Usuario usuario = getUsuario("teste", "123456", Usuario.Role.ROLE_CLIENTE);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // Act
        Usuario usuarioSalvo = usuarioService.salvar(usuario);

        // Assert
        assertNotNull(usuarioSalvo);
        assertEquals(usuario.getUsername(), usuarioSalvo.getUsername());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void salvarUsuario_ComUsernameExistente_LancaExcecao() {
        // Arrange
        Usuario usuario = getUsuario("existente", "123456", Usuario.Role.ROLE_CLIENTE);
        when(usuarioRepository.save(usuario)).thenThrow(DataIntegrityViolationException.class);

        // Act & Assert
        assertThrows(UsernameUniqueViolationException.class, () -> usuarioService.salvar(usuario));
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void buscarPorId_ComIdExistente_RetornaUsuario() {
        // Arrange
        Long id = 1L;
        Usuario usuario = getUsuario("teste", "123456", Usuario.Role.ROLE_CLIENTE);
        usuario.setId(id);
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        // Act
        Usuario usuarioEncontrado = usuarioService.buscarPorId(id);

        // Assert
        assertNotNull(usuarioEncontrado);
        assertEquals(id, usuarioEncontrado.getId());
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    void buscarPorId_ComIdInexistente_LancaExcecao() {
        // Arrange
        Long id = 99L;
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> usuarioService.buscarPorId(id));
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    void editarSenha_ComDadosValidos_AtualizaSenha() {
        // Arrange
        Long id = 1L;
        String senhaAtual = "123456";
        String novaSenha = "654321";
        String confirmaSenha = "654321";

        Usuario usuario = getUsuario("teste", senhaAtual, Usuario.Role.ROLE_CLIENTE);
        usuario.setId(id);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        // Act
        Usuario usuarioAtualizado = usuarioService.editarSenha(id, senhaAtual, novaSenha, confirmaSenha);

        // Assert
        assertNotNull(usuarioAtualizado);
        assertEquals(novaSenha, usuarioAtualizado.getPassword());
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    void editarSenha_ComSenhaAtualIncorreta_LancaExcecao() {
        // Arrange
        Long id = 1L;
        String senhaAtual = "errada";
        String novaSenha = "654321";
        String confirmaSenha = "654321";

        Usuario usuario = getUsuario("teste", "123456", Usuario.Role.ROLE_CLIENTE);
        usuario.setId(id);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        // Act & Assert
        assertThrows(PasswordInvalidException.class,
                () -> usuarioService.editarSenha(id, senhaAtual, novaSenha, confirmaSenha));
        verify(usuarioRepository, times(1)).findById(id);
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void editarSenha_ComNovaSenhaDiferente_LancaExcecao() {
        // Arrange
        Long id = 1L;
        String senhaAtual = "123456";
        String novaSenha = "654321";
        String confirmaSenha = "diferente";

        // Act & Assert
        assertThrows(PasswordInvalidException.class,
                () -> usuarioService.editarSenha(id, senhaAtual, novaSenha, confirmaSenha));
        verify(usuarioRepository, never()).findById(any());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void buscarTodos_QuandoChamado_RetornaListaDeUsuarios() {
        // Arrange
        Usuario usuario1 = getUsuario("user1", "123", Usuario.Role.ROLE_CLIENTE);
        Usuario usuario2 = getUsuario("user2", "456", Usuario.Role.ROLE_ADMIN);
        List<Usuario> usuarios = List.of(usuario1, usuario2);

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        // Act
        List<Usuario> resultado = usuarioService.buscarTodos();

        // Assert
        assertEquals(2, resultado.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    private Usuario getUsuario(String username, String senha, Usuario.Role role) {
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(senha);
        usuario.setRole(role);

        return usuario;
    }
}