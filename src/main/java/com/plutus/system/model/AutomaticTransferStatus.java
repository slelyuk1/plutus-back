package com.plutus.system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public enum AutomaticTransferStatus {
    STATUS_1(1, "Status 1");

    private final long id;
    private final String name;
    private String description;
}
