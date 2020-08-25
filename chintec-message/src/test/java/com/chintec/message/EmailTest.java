package com.chintec.message;

import com.chintec.common.util.ResultResponse;
import com.chintec.message.entity.dto.MailSendDTO;
import com.chintec.message.service.IEmailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Jeff·Tang
 * @version 1.0
 * @date 2020/8/25 10:35
 */
@SpringBootTest
@Slf4j
public class EmailTest {
    @Autowired
    private IEmailService iEmailService;

    @Test
    void sendSimpleMailMessageTest() {
        MailSendDTO mailSendDTO = new MailSendDTO();
        mailSendDTO.setContent("这是一封测试邮件");
        mailSendDTO.setSubject("测试邮件的成功与否");
        mailSendDTO.setTo("793816287@qq.com");
        ResultResponse resultResponse = iEmailService.sendSimpleMailMessage(mailSendDTO);
        log.info(resultResponse.getMessage());
    }

    @Test
    void sendMimeMessageTest() {
        ResultResponse resultResponse = iEmailService.sendMimeMessage("793816287@qq.com", "这是一封测试邮寄", "内容为测试内容");
        log.info(resultResponse.getMessage());
    }
    @Test
    void sendMimeMessageFileTest() {
        ResultResponse resultResponse = iEmailService.sendMimeMessage("793816287@qq.com", "这是一封测试邮寄", "内容为测试内容","C:\\Users\\tangxinli\\Desktop\\timg1.jpg");
        log.info(resultResponse.getMessage());
    }
}
