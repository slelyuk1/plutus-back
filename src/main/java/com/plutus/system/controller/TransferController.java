package com.plutus.system.controller;

import com.plutus.system.configuration.security.SecurityConfiguration;
import com.plutus.system.model.request.transfer.ChangeBalanceRequest;
import com.plutus.system.model.request.transfer.MakeTransferRequest;
import com.plutus.system.model.response.TransferInfo;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(SecurityConfiguration.SECURED_API_ENDPOINT + "/transfer")
public interface TransferController {

    @GetMapping("/")
    Collection<TransferInfo> getAccountTransfers();

    @GetMapping("/all")
    Collection<TransferInfo> getAll();

    @PostMapping("/changeBalance")
    TransferInfo changeBalance(@Valid @RequestBody ChangeBalanceRequest request);

    @PostMapping("/makeTransfer")
    TransferInfo makeTransfer(@Valid @RequestBody MakeTransferRequest request);

}
