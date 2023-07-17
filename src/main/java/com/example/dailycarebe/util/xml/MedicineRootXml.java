package com.example.dailycarebe.util.xml;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "api.health.kr")
@Getter
@Setter
@ToString
public class MedicineRootXml {
  @XmlElement(name = "result")
  private String result;

  @XmlElement(name = "create")
  private String create;

  @XmlElement(name = "count")
  private String count;

  @XmlElement(name = "itemList")
  private MedicineListXml medicineList;


  @XmlAccessorType(XmlAccessType.FIELD)
  @Getter
  @Setter
  @ToString
  public static class MedicineListXml {
    @XmlElement(name = "item")
    private Set<MedicineXml> medicines;
  }
}
