package com.zzk.fastanswer.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Question Output DTO
 *
 * @author zzk
 * @create 2020-11-06 8:57
 */
@Data
public class QuestionDTO extends QuestionBaseDTO {
    /**
     * 问题发起人
     */
    private UserDTO userDTO = new UserDTO();
}
