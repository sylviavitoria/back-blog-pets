package com.sylviavitoria.blogpets.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler exceptionHandler;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;
    
    @Mock
    private BindingResult bindingResult;

    @Test
    @DisplayName("Deve tratar EntityNotFoundException e retornar status 404")
    void deveTratar_EntityNotFoundException_E_Retornar_Status404() {

        String mensagemErro = "Entidade não encontrada";
        EntityNotFoundException exception = new EntityNotFoundException(mensagemErro);
        
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleNotFound(exception);
        
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(mensagemErro, response.getBody().get("erro"));
    }

    @Test
    @DisplayName("Deve tratar EntityExistsException e retornar status 409")
    void deveTratar_EntityExistsException_E_Retornar_Status409() {

        String mensagemErro = "Entidade já existe";
        EntityExistsException exception = new EntityExistsException(mensagemErro);
        
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleEntityExists(exception);
        
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(mensagemErro, response.getBody().get("erro"));
    }

    @Test
    @DisplayName("Deve tratar IllegalArgumentException e retornar status 400")
    void deveTratar_IllegalArgumentException_E_Retornar_Status400() {

        String mensagemErro = "Argumento ilegal";
        IllegalArgumentException exception = new IllegalArgumentException(mensagemErro);
        
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleIllegalArgumentException(exception);
        
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(mensagemErro, response.getBody().get("erro"));
    }

    @Test
    @DisplayName("Deve tratar MethodArgumentNotValidException e retornar erros de validação")
    void deveTratar_MethodArgumentNotValidException_E_Retornar_ErrosValidacao() {

        FieldError fieldError1 = new FieldError("post", "titulo", "O título é obrigatório");
        FieldError fieldError2 = new FieldError("post", "descricao", "A descrição é obrigatória");
        
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));
        
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleMethodArgumentNotValid(methodArgumentNotValidException);
        
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> errors = response.getBody();
        assertEquals(2, errors.size());
        assertEquals("O título é obrigatório", errors.get("titulo"));
        assertEquals("A descrição é obrigatória", errors.get("descricao"));
        
        verify(methodArgumentNotValidException, times(1)).getBindingResult();
        verify(bindingResult, times(1)).getFieldErrors();
    }

    @Test
    @DisplayName("Deve tratar MethodArgumentNotValidException sem erros de campo")
    void deveTratar_MethodArgumentNotValidException_SemErrosCampo() {

        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of());
        
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleMethodArgumentNotValid(methodArgumentNotValidException);
        
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> errors = response.getBody();
        assertEquals(0, errors.size());
        
        verify(methodArgumentNotValidException, times(1)).getBindingResult();
        verify(bindingResult, times(1)).getFieldErrors();
    }
}