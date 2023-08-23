package com.pokemonrewiev.api.exceptions;


import lombok.Data;

import java.util.Date;

@Data
public class ErrorObject {
    private int statusCode;
    private String message;
    private Date timeStamp;
}
