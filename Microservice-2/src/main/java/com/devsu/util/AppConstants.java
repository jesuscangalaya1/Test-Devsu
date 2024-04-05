package com.devsu.util;

import lombok.experimental.UtilityClass;

/**
 * la clase AppConstants es una clase de utilidad que contiene constantes de la aplicación.
 * @UtilityClass: Anotación de Lombok que permite crear una clase con métodos estáticos, sin necesidad de instanciar la clase.
 */
@UtilityClass
public class AppConstants {


    // CLIENTS ERRORS
    public static final String BAD_REQUEST = "P-404";
    public static final String BAD_REQUEST_ACOUNT = "Cuenta no encontrado con el ID: ";
    public static final String BAD_REQUEST_MOTION = "Movimiento no encontrado con el ID: ";

    public static final String BAD_REQUEST_CLIENT_NOT_FOUND = "El cliente no existe";
    public static final String BAD_REQUEST_INVALID_DATE_FORMAT = "Formato de fecha inválido";
    public static final String BAD_REQUEST_DATE_EMPTY = "No se encontraron fechas" ;

    //MESSAGE CONTROLLER
    public static final String SUCCESS = "SUCCESS";
    public static final String MESSAGE_ID_CLIENT = "CLIENT ID: ";


    // =============================================================================================
    // CONSTANTES DE PAGINATION
    // =============================================================================================
    public static final String NUMERO_DE_PAGINA_POR_DEFECTO = "1";
    public static final String MEDIDA_DE_PAGINA_POR_DEFECTO = "10";
    public static final String ORDENAR_POR_DEFECTO = "id";
    public static final String ORDENAR_DIRECCION_POR_DEFECTO = "asc";

}
