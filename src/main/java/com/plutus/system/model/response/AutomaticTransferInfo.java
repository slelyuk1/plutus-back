package com.plutus.system.model.response;

import com.plutus.system.model.AutomaticTransferStatus;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class AutomaticTransferInfo {
    Long id;
    String name;
    Long fromId;
    Long toId;
    AutomaticTransferStatus status;
    Long period;
    LocalDate nextPaymentTime;
    BigDecimal transferAmount;
    String description;
}
