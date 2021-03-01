package com.plutus.system.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransferType {
    TYPE_1(1, "Type 1");

    private final long id;
    private final String name;
    private String description;

}
