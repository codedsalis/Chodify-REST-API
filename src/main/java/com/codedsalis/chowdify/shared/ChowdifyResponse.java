package com.codedsalis.chowdify.shared;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChowdifyResponse {
    private String status;

    private Object data;
}
