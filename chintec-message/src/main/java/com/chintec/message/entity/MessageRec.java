package com.chintec.message.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/20 10:53
 */
@Data
public class MessageRec implements Serializable {
    /**
     * 消息类型0 是消息推送 1 为短信发送
     */
    private Integer type;
    /**
     * 消息内容
     */
    private String message;
    /**
     * 用户id集合
     */
    private List<Integer> userIds;
}
