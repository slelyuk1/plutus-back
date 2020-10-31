package com.plutus.system.controller;

import com.plutus.system.model.request.CreateAutomaticTransferRequest;
import com.plutus.system.model.response.AutomaticTransferInfo;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/secured/automaticTransfer")
public interface AutomaticTransferController {

    @PostMapping("/create")
    AutomaticTransferInfo create(@Valid @RequestBody CreateAutomaticTransferRequest request);

    @PostMapping("/{transferId}/disable")
    void disable(@PathVariable long transferId);

    @DeleteMapping("/{transferId}/delete")
    void delete(@PathVariable long transferId);

    @GetMapping("/all")
    Collection<AutomaticTransferInfo> getAll(@RequestParam Optional<Long> accountId);
}
