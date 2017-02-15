package com.dsdl.eidea.core.web.converter;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 刘大磊 on 2016/10/13 13:24.
 * spring Date 转 Json
 */
public class DateSerializer extends JsonSerializer<Date> {
    private static final String DATE_TIME_FORMAT="yyyy-MM-dd HH:mm:ss";
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        DateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        jsonGenerator.writeString(sdf.format(date));
    }
}
