package com.chintec.backofiice.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/20 10:53
 */
@Data
public class MessageRec implements Serializable {
    private Integer type;
    private String message;
    private Integer userId;
}
