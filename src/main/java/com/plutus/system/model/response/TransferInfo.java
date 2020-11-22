package com.plutus.system.model.response;

import com.plutus.system.model.entity.Transfer;
import lombok.Generated;
import lombok.Value;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
public class TransferInfo {
    Long id;
    Long fromId;
    Long toId;
    String transferStatus;
    BigDecimal amount;
    LocalDateTime createdWhen;
    String description;

    public static TransferInfo fromTransfer(Transfer t){
       return new TransferInfo(t.getId(),t.getFromId(),t.getToId(),t.getTransferStatus(),t.getAmount(),t.getCreatedWhen(),t.getDescription());
    }
}
