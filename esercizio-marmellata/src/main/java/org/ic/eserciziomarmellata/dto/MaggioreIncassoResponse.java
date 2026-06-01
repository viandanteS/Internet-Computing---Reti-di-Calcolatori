package org.ic.eserciziomarmellata.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MaggioreIncassoResponse {
    private final String tipoMarmellata;
    private final double importo;
}
