package de.nk.camel.route;

import java.util.Map;

import lombok.Setter;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.springframework.stereotype.Component;

import de.nk.camel.model.MyCsvBean;

/**
 * @author Niko Köbler, http://www.n-k.de
 */
@Setter
@Component("route1")
public class Route1 extends AbstractRoute {
    private String source;
    private String target;

    @Override
    public void configureRoute() throws Exception {

        final DataFormat bindy = new BindyCsvDataFormat("de.nk.camel.model");

        from(source)
                .log(body().toString())
                .unmarshal(bindy)
                .split(body())
                .process(new Processor() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void process(final Exchange exchange) throws Exception {
                        final Message in = exchange.getIn();
                        final Map<String, Object> modelMap = (Map<String, Object>) in.getBody();
                        in.setBody(modelMap.get(MyCsvBean.class.getCanonicalName()));
                    }
                })
                .to(target);
    }

}
