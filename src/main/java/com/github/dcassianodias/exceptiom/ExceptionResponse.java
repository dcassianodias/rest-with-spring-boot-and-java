package com.github.dcassianodias.exceptiom;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;

public record ExceptionResponse(

        @JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd'T'HH:mm:ss",
                timezone = "America/Sao_Paulo"
        )
        Instant timestamp,
        String message,
        String details
) {}