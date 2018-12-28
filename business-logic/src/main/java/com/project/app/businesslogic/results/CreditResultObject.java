package com.project.app.businesslogic.results;

import com.project.app.businesslogic.adapters.JsonMapAdapter;
import com.project.app.entities.position.Position;
import java.time.LocalDateTime;
import java.util.Map;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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
