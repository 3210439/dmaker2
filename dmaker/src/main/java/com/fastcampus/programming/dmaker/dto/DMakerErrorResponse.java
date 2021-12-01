package com.fastcampus.programming.dmaker.dto;

import com.fastcampus.programming.dmaker.exception.DMakerErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// 공통 실패 dto
public class DMakerErrorResponse {
    private DMakerErrorCode errorCode;
    private String errorMessage;
}
