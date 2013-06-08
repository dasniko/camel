package de.nk.camel.route;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.apache.camel.spring.javaconfig.test.JavaConfigContextLoader;
import org.junit.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import de.nk.camel.model.MyCsvBean;

@ContextConfiguration(locations = "de.nk.camel.route.RouteOneTest$ContextConfig", loader = JavaConfigContextLoader.class)
public class RouteOneTest extends AbstractJUnit4SpringContextTests {

    @EndpointInject(uri = "mock:result")
    protected MockEndpoint resultEndpoint;

    @Produce(uri = "direct:start")
    protected ProducerTemplate template;

    @DirtiesContext
    @Test
    public void testSendCsvDataZeroLines() throws Exception {
        resultEndpoint.expectedMessageCount(0);
        template.sendBody("only one line");
        resultEndpoint.assertIsSatisfied();
    }

    @DirtiesContext
    @Test
    public void testSendCsvDataOneLine() throws Exception {
        resultEndpoint.expectedBodiesReceived(new MyCsvBean(1, "message", "db"));
        template.sendBody("CSV\n1,message,db");
        resultEndpoint.assertIsSatisfied();
    }

    @DirtiesContext
    @Test
    public void testSendCsvDataTwoLines() throws Exception {
        resultEndpoint.expectedBodiesReceived(new MyCsvBean(1, "message", "db"), new MyCsvBean(2, "second", "never mind"));
        template.sendBody("CSV\n1,message,db\n2,second,never mind");
        resultEndpoint.assertIsSatisfied();
    }

    @DirtiesContext
    @Test
    public void testSendInvalidData() throws Exception {
        resultEndpoint.expectedMessageCount(0);
        template.sendBody("CSV\nNullpointerException():100");
        resultEndpoint.assertIsSatisfied();
    }

    @Configuration
    public static class ContextConfig extends SingleRouteCamelConfiguration {
        @Override
        @Bean
        public RouteBuilder route() {
            return new Route1("direct:start", "mock:result");
        }
    }
}