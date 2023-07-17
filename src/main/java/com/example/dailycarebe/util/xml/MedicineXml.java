package com.example.dailycarebe.util.xml;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"drugCode"})
public class MedicineXml {

  @XmlElement(name = "drug_code")
  private String drugCode;
  @XmlElement(name = "drug_name")
  private String drugName;
  @XmlElement(name = "firm_name")
  private String companyName;
  @XmlElement(name = "drug_class")
  private String drugClass;
  @XmlElement(name = "ingr_type")
  private String drugIngredientType;
  @XmlElement(name = "form_code")
  private String drugForm;
  @XmlElement(name = "dosage_route_code1")
  private String dosageRouteCode;
  @XmlElement(name = "charact")
  private String description;
  @XmlElement(name = "item_permit_date")
  private String drugPermitDate;
  @XmlElement(name = "bioeq_prodt_yn")
  private String isBioEquivalence;
  @XmlElement(name = "comp_drug_yn")
  private String isComparatorDrug;
  @XmlElement(name = "edi_code")
  private String ediCode;
  @XmlElement(name = "list_boh")
  private String pricePer;
  @XmlElement(name = "cls_name")
  private String className;
  @XmlElement(name = "drug_box")
  private String packagingUnitPer;
  @XmlElement(name = "stmt")
  private String storageMethod;
  @XmlElement(name = "effect")
  private String effect;
  @XmlElement(name = "dosage")
  private String dosage;
  @XmlElement(name = "caution")
  private String caution;
  @XmlElement(name = "picto_img")
  private String pictogramImg;
  @XmlElement(name = "indic_medi")
  private String medicinalEffect;
  @XmlElement(name = "sim_effect")
  private String medSummary;
  @XmlElement(name = "gen_medi")
  private String medGuide;
  @XmlElement(name = "abbrev_medi")
  private String medGuideSummary;
  @XmlElement(name = "produce")
  private String produce;
  @XmlElement(name = "sunb")
  private String ingredients;
  @XmlElement(name = "sunb_code")
  private String ingredientsCode;
  @XmlElement(name = "additives")
  private String additives;
  @XmlElement(name = "atc_cd")
  private String atcCode;
  @XmlElement(name = "kpic_atc")
  private String atcClassKPIC;
  @XmlElement(name = "idfy_pic")
  private String identifyPicture;
  @XmlElement(name = "pack_pic")
  private String packagePicture;
  @XmlElement(name = "insert_paper")
  private String insertPaper;
}
