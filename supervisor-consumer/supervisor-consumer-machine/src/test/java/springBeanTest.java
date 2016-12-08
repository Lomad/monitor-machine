import com.winning.monitor.supervisor.consumer.machine.CollectDataMessageHandler;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * @Author Lemod
 * @Version 2016/12/6
 */
public class springBeanTest {
    
    @Test
    public void getBean(){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:META-INF/spring/*.xml");

        Map<String,CollectDataMessageHandler> messageHandler = context.getBeansOfType(CollectDataMessageHandler.class);

        System.out.println(messageHandler);
    }


}
