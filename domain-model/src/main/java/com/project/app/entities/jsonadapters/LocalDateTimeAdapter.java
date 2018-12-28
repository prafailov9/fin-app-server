package com.project.app.entities.jsonadapters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author p.rafailov
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LocalDateTime unmarshal(String str) throws Exception {
        return LocalDateTime.parse(str, DTF);
    }

    @Override
    public String marshal(LocalDateTime ldt) throws Exception {
        return ldt.format(DTF);
    }

}
