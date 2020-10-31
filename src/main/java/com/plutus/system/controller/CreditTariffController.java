package com.plutus.system.controller;

import com.plutus.system.model.request.AssignCreditTariffToAccountRequest;
import com.plutus.system.model.request.ModifyOrCreateCreditTariffRequest;
import com.plutus.system.model.response.CreditTariffInfo;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/secured/creditTariff")
public interface CreditTariffController {
    @PostMapping("/createOrModify")
    CreditTariffInfo createOrModify(@Valid @RequestBody ModifyOrCreateCreditTariffRequest request);

    @DeleteMapping("/{creditTariffId}")
    void delete(@PathVariable long creditTariffId);

    @PostMapping("/assignToAccount")
    void assignToAccount(@Valid @RequestBody AssignCreditTariffToAccountRequest request);
}
