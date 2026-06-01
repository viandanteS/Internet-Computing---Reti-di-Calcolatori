package org.ic.eserciziomarmellata.controller;


import org.ic.eserciziomarmellata.dto.MaggioreIncassoResponse;
import org.ic.eserciziomarmellata.dto.VenditaRequest;
import org.ic.eserciziomarmellata.dto.VenditaResponse;
import org.ic.eserciziomarmellata.service.AziendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class MarmellataController {

    @Autowired
    private AziendaService aziendaService;

    @PostMapping("/aziende/{idAzienda}/vendite")
    public ResponseEntity<VenditaResponse> aggiungiVendita(@PathVariable long idAzienda, @RequestBody VenditaRequest request) {
        aziendaService.registraIncasso(idAzienda,request);
        return ResponseEntity.ok(new VenditaResponse());
    }

    @GetMapping("/aziende/{idAzienda}/migliore")
    public ResponseEntity<MaggioreIncassoResponse> getVendita(@PathVariable long idAzienda) {
        MaggioreIncassoResponse response = aziendaService.maggioreIncasso(idAzienda);
        return ResponseEntity.ok(response);
    }

}
