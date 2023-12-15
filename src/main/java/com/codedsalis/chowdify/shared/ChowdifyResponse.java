package com.codedsalis.chowdify.shared;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@Builder
public class ChowdifyResponse {
    private String status;

    private HashMap<String, ?> data;
}
