package com.plutus.system.controller;

import com.plutus.system.model.request.AssignCreditTariffToAccountRequest;
import com.plutus.system.model.request.ModifyOrCreateCreditTariffRequest;
import com.plutus.system.model.response.CreditTariffInfo;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Collection;

@RestController
@RequestMapping("/api/secured/creditTariff")
public interface CreditTariffController {
    @PostMapping("/createOrModify")
    CreditTariffInfo createOrModify(@Valid @RequestBody ModifyOrCreateCreditTariffRequest request);

    @DeleteMapping("/{creditTariffId}/delete")
    void delete(@PathVariable("creditTariffId") BigInteger creditTariffId);

    @PostMapping("/assignToAccount")
    void assignToAccount(@Valid @RequestBody AssignCreditTariffToAccountRequest request);

    @GetMapping("/all")
    Collection<CreditTariffInfo> getAll();
}
