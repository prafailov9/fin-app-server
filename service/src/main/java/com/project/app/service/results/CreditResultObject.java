package com.project.app.service.results;

import com.project.app.service.adapters.JsonMapAdapter;
import com.project.app.entities.position.Position;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 *
 * @author prafailov
 */
public class CreditResultObject extends ResultObject {

    @XmlElement(name = "principalPayments")
    @XmlJavaTypeAdapter(JsonMapAdapter.class)
    private Map<LocalDateTime, Double> principalPayments;

    public CreditResultObject() {

    }

    public CreditResultObject(Map<LocalDateTime, Double> cashFlow, Position position,
            String instrumentType, Map<LocalDateTime, Double> principalPayments) {
        super(cashFlow, position, instrumentType);

        this.principalPayments = principalPayments;
    }

    public Map<LocalDateTime, Double> getPrincipalPayments() {
        return principalPayments;
    }

    public void setPrincipalPayments(Map<LocalDateTime, Double> principalPayments) {
        this.principalPayments = principalPayments;
    }

}
