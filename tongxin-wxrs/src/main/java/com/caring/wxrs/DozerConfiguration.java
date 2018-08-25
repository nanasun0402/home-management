package com.caring.wxrs;

import java.util.Arrays;
import java.util.List;
import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author james
 */
@Configuration
public class DozerConfiguration {

    @Bean(name = "dozerBeanMapper")
    public DozerBeanMapper dozerBean() {
        List<String> mappingFiels = Arrays.asList("vo-mapping.xml");
        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(mappingFiels);
        return dozerBeanMapper;
    }
}
