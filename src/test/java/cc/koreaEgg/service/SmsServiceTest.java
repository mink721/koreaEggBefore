package cc.koreaEgg.service;

import cc.koreaEgg.entity.User;
import cc.koreaEgg.entity.sms.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class SmsServiceTest {
    @Autowired
    SmsService service;

    @Test
    public void send() throws Exception {
        SmsSendRequest req = new SmsSendRequest();
        req.setMsg("%고객명%님. 안녕하세요. API TEST SEND");
        req.setReceiver("01111111111,01111111112");
        req.setDestination("01111111111|담당자,01111111112|홍길동");
        req.setTitle("제목입력");
        service.sendSms(req);
    }

    @Test
    public void cancel() throws Exception {
        SmsCancelRequest req = new SmsCancelRequest();
        req.setMid(8697445);
        service.sendSmsInfo(req);
    }

    @Test
    public void state() throws Exception {
        SmsStateRequest req = new SmsStateRequest();
        req.setMid(39003);
        service.sendSmsInfo(req);
    }

    @Test
    public void list() throws Exception {
        SmsListRequest req = new SmsListRequest();
        service.sendSmsInfo(req);
    }

    @Test
    public void remain() throws Exception {
        SmsRemainRequest req = new SmsRemainRequest();
        service.sendSmsInfo(req);
    }


}