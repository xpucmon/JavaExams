package alararestaurant.domain.dtos;

import alararestaurant.common.OrderType;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderImportDto {

    @XmlElement(name = "customer")
    private String name;
    @XmlElement
    private String employee;
    @XmlElement(name = "date-time")
    private String dateTime;
    @XmlElement
    private String type;
    @XmlElement(name = "items")
    private ItemImportRootXmlDto itemImportRootXmlDto;

    public OrderImportDto() {
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    @NotNull
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Enumerated(value = EnumType.STRING)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ItemImportRootXmlDto getItemImportRootXmlDto() {
        return itemImportRootXmlDto;
    }

    public void setItemImportRootXmlDto(ItemImportRootXmlDto itemImportRootXmlDto) {
        this.itemImportRootXmlDto = itemImportRootXmlDto;
    }
}
