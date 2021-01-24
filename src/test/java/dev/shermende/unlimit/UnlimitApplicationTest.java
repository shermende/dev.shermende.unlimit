package dev.shermende.unlimit;

import dev.shermende.unlimit.handler.event.PayloadEventHandler;
import dev.shermende.unlimit.handler.event.ShutdownEventHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;

@SpringBootTest(args = {"resource:source/data.json", "resource:source/data.csv"})
class UnlimitApplicationTest {

    @SpyBean
    private PayloadEventHandler payloadEventHandler;

    @MockBean
    private ShutdownEventHandler shutdownEventHandler;

    @Test
    void main() {
        then(shutdownEventHandler).should(timeout(10000L)).handleMessage(any());
        then(payloadEventHandler).should(times(2000)).handleMessage(any());
    }

}