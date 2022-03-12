/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mm.rest.exceptions;

/**
 *
 * @author david
 */
public class ServiceException extends Exception{
    private String code;
    
    public ServiceException(String errorMessage, String code) {
        super(errorMessage);
        this.code = code;
    }
    
    public ServiceException(String errorMessage, String code, Throwable cause) {
        super(errorMessage, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
}
