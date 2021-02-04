/**
 * Project Name:medication;<br/>
 * File Name:MyJsonFactoryDecorator;<br/>
 * Package Name:com.tasly.medication.common.component.log;<br/>
 * Date: 2019-05-15 13:40;<br/>
 * Copyright (c) 2019, www.sq580.com All Rights Reserved.;<br/>
 */
package demo.common.component.log;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import net.logstash.logback.decorate.JsonFactoryDecorator;

public class MyJsonFactoryDecorator implements JsonFactoryDecorator {

    @Override
    public MappingJsonFactory decorate(MappingJsonFactory factory) {
        // 禁用对非ascii码进行escape编码的特性
        factory.disable(JsonGenerator.Feature.ESCAPE_NON_ASCII);
        return factory;
    }
}