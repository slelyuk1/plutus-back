package com.plutus.system.model.response;

import com.plutus.system.model.entity.Transfer;
import lombok.Value;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Value
public class TransferInfo {
    BigInteger id;
    BigInteger fromId;
    BigInteger toId;
    BigDecimal amount;
    LocalDateTime createdWhen;
    String description;

    public static TransferInfo fromTransfer(Transfer t) {
        BigInteger fromId = null;
        BigInteger toId = null;
        if (t.getCreator() != null) {
            fromId = t.getCreator().getId();
        }
        if (t.getReceiver() != null) {
            toId = t.getReceiver().getId();
        }
        return new TransferInfo(t.getId(), fromId, toId,
                t.getAmount(), t.getCreatedWhen(), t.getDescription());
    }
}
