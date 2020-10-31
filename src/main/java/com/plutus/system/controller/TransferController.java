package com.plutus.system.controller;

import com.plutus.system.configuration.security.SecurityConfiguration;
import com.plutus.system.model.request.ChangeBalanceRequest;
import com.plutus.system.model.request.MakeTransferRequest;
import com.plutus.system.model.response.TransferInfo;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(SecurityConfiguration.SECURED_API_ENDPOINT + "/transfer")
public interface TransferController {

    @GetMapping("/all")
    Collection<TransferInfo> getAll(@RequestParam Optional<Long> accountId);

    @PostMapping("/changeBalance")
    void changeBalance(@Valid @RequestBody ChangeBalanceRequest request);

    @PostMapping("/makeTransfer")
    TransferInfo makeTransfer(@Valid @RequestBody MakeTransferRequest request);

}
