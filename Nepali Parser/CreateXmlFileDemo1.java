//package com.tutorialspoint.xml;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;

public class CreateXmlFileDemo1 {
	
   public static void main(String argv[]) {

      try {
         DocumentBuilderFactory dbFactory =
         DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = 
            dbFactory.newDocumentBuilder();
         Document doc = dBuilder.newDocument();
         // root element
         Element rootElement = doc.createElement("दिन्छ");
         doc.appendChild(rootElement);

         //  supercars element
         Element supercar = doc.createElement("supercars");
         rootElement.appendChild(supercar);

         // setting attribute to element
         Attr attr = doc.createAttribute("Verb");
         attr.setValue("दिन्छ");
         supercar.setAttributeNode(attr);
	 
/*         Element vibhakti = doc.createElement("vibhakti");
         Attr attrType = doc.createAttribute("presence");
         attrType.setValue("medatory");
         vibhakti.setAttributeNode(attrType);
         vibhakti.appendChild(
         doc.createTextNode("ko"));
         supercar.appendChild(vibhakti);*/

// FRAME OF DINCHHA
  	 Element vibhakti = doc.createElement("vibhakti");
         Attr attrType = doc.createAttribute("presence");
         attrType.setValue("Mendatory");
         vibhakti.setAttributeNode(attrType);
         vibhakti.appendChild(
         doc.createTextNode("null"));
         supercar.appendChild(vibhakti);
         Attr attrTypekk = doc.createAttribute("karaka");
         attrTypekk.setValue("karta(K1)");
         vibhakti.setAttributeNode(attrTypekk);
         	
         
         Element carname1 = doc.createElement("vibhakti");
         Attr attrType1 = doc.createAttribute("presence");
         attrType1.setValue("Mendatory");
         carname1.setAttributeNode(attrType1);
         carname1.appendChild(
         doc.createTextNode("कोor null or लाई"));
         supercar.appendChild(carname1);
         Attr attrTypekk1 = doc.createAttribute("karaka");
         attrTypekk1.setValue("karma(K2)");
         carname1.setAttributeNode(attrTypekk1);


         Element carname2 = doc.createElement("vibhakti");
         Attr attrType2 = doc.createAttribute("presence");
         attrType2.setValue("Optional");
         carname2.setAttributeNode(attrType2);
         carname2.appendChild(
         doc.createTextNode("Dwara or द्वारा"));
         supercar.appendChild(carname2);
         Attr attrTypekk2 = doc.createAttribute("karaka");
         attrTypekk2.setValue("karana(K3)");
         carname2.setAttributeNode(attrTypekk2);


         Element carname3 = doc.createElement("vibhakti");
         Attr attrType3 = doc.createAttribute("presence");
         attrType3.setValue("Optional");
         carname3.setAttributeNode(attrType3);
         carname3.appendChild(
         doc.createTextNode("लाई"));
         supercar.appendChild(carname3);
         Attr attrTypekk3 = doc.createAttribute("karaka");
         attrTypekk3.setValue("Sampradan(K4)");
         carname3.setAttributeNode(attrTypekk3);

	 Element carname12dinchh = doc.createElement("vibhakti");
         Attr attrType12dinchh = doc.createAttribute("presence");
         attrType12dinchh.setValue("Optional");
         carname12dinchh.setAttributeNode(attrType12dinchh);
         carname12dinchh.appendChild(
         doc.createTextNode("बाट or हरुबाट or देखि"));
         supercar.appendChild(carname12dinchh);
         Attr attrTypekk12dinchh = doc.createAttribute("karaka");
         attrTypekk12dinchh.setValue("Apadan(K5)");
         carname12dinchh.setAttributeNode(attrTypekk12dinchh);
         Attr attrTypeform4dinchh = doc.createAttribute("Form");
         attrTypeform4dinchh.setValue("Singular and Plural");
         carname12dinchh.setAttributeNode(attrTypeform4dinchh);
         

         Element carname13dinchh = doc.createElement("vibhakti");
         Attr attrType13dinchh = doc.createAttribute("presence");
         attrType13dinchh.setValue("Optional");
         carname13dinchh.setAttributeNode(attrType13dinchh);
         carname13dinchh.appendChild(
         doc.createTextNode("को or करुको or रो or नो"));
         supercar.appendChild(carname13dinchh);
         Attr attrTypekk13dinchh = doc.createAttribute("karaka");
         attrTypekk13dinchh.setValue("Sambandh(K6)");
         carname13dinchh.setAttributeNode(attrTypekk13dinchh);
         Attr attrTypeform5dinchh = doc.createAttribute("Form");
         attrTypeform5dinchh.setValue("Singular and Plural");
         carname13dinchh.setAttributeNode(attrTypeform5dinchh);


         Element carname14dinchh = doc.createElement("vibhakti");
         Attr attrType14dinchh = doc.createAttribute("presence");
         attrType14dinchh.setValue("Optional");
         carname14dinchh.setAttributeNode(attrType14dinchh);
         carname14dinchh.appendChild(
         doc.createTextNode("मा or हरुमा "));
         supercar.appendChild(carname14dinchh);
         Attr attrTypekk14dinchh = doc.createAttribute("karaka");
         attrTypekk14dinchh.setValue("Adhikaran(K7)");
         carname14dinchh.setAttributeNode(attrTypekk14dinchh);
         Attr attrTypeform6dinchh = doc.createAttribute("Form");
         attrTypeform6dinchh.setValue("Singular and Plural");
         carname14dinchh.setAttributeNode(attrTypeform6dinchh);
         
         Element carnamepc20dinchh = doc.createElement("vibhakti");
         Attr attrTypepc22dinchh = doc.createAttribute("presence");
         attrTypepc22dinchh.setValue("Optional");
         carnamepc20dinchh.setAttributeNode(attrTypepc22dinchh);
         carnamepc20dinchh.appendChild(
         doc.createTextNode("null"));
         supercar.appendChild(carnamepc20dinchh);
         Attr attrTypekkpc23dinchh = doc.createAttribute("karaka");
         attrTypekkpc23dinchh.setValue("Locative(K2p)");
         carnamepc20dinchh.setAttributeNode(attrTypekkpc23dinchh);

	 Element carname100 = doc.createElement("Tense");
         Attr attrType100 = doc.createAttribute("Vibhakti");
         attrType100.setValue("Simple Present Tense");
         carname100.setAttributeNode(attrType100);
         supercar.appendChild(carname100);


// FRAME OF DIYO

        // Element rootElement2 = doc.createElement("दियो");
         //doc.appendChild(rootElement2);

         //  supercars element
         Element supercar1 = doc.createElement("supercars");
         rootElement.appendChild(supercar1);

         // setting attribute to element
         Attr attr1 = doc.createAttribute("Verb");
         attr1.setValue("दियो");
         supercar1.setAttributeNode(attr1);
	 
/*         Element vibhakti = doc.createElement("vibhakti");
         Attr attrType = doc.createAttribute("presence");
         attrType.setValue("medatory");
         vibhakti.setAttributeNode(attrType);
         vibhakti.appendChild(
         doc.createTextNode("ko"));
         supercar.appendChild(vibhakti);*/


  	 Element vibhakti7 = doc.createElement("vibhakti");
         Attr attrType7 = doc.createAttribute("presence");
         attrType7.setValue("Mendatory");
         vibhakti7.setAttributeNode(attrType7);
         vibhakti7.appendChild(
         doc.createTextNode("ले  or को or null or लाई"));
         supercar1.appendChild(vibhakti7);
         Attr attrTypekk7 = doc.createAttribute("karaka");
         attrTypekk7.setValue("karta(K1)");
         vibhakti7.setAttributeNode(attrTypekk7);
         	
         
         Element carname6 = doc.createElement("vibhakti");
         Attr attrType6 = doc.createAttribute("presence");
         attrType6.setValue("Mendatory");
         carname6.setAttributeNode(attrType6);
         carname6.appendChild(
         doc.createTextNode("को or null or लाई"));
         supercar1.appendChild(carname6);
         Attr attrTypekk6 = doc.createAttribute("karaka");
         attrTypekk6.setValue("karma(K2)");
         carname6.setAttributeNode(attrTypekk6);


         Element carname5 = doc.createElement("vibhakti");
         Attr attrType5 = doc.createAttribute("presence");
         attrType5.setValue("Optional");
         carname5.setAttributeNode(attrType5);
         carname5.appendChild(
         doc.createTextNode("Dwara or द्वारा"));
         supercar1.appendChild(carname5);
         Attr attrTypekk5 = doc.createAttribute("karaka");
         attrTypekk5.setValue("karana(K3)");
         carname5.setAttributeNode(attrTypekk5);


         Element carname4 = doc.createElement("vibhakti");
         Attr attrType4 = doc.createAttribute("presence");
         attrType4.setValue("Optional");
         carname4.setAttributeNode(attrType4);
         carname4.appendChild(
         doc.createTextNode("लाई"));
         supercar1.appendChild(carname4);
         Attr attrTypekk4 = doc.createAttribute("karaka");
         attrTypekk4.setValue("Sampradan(K4)");
         carname4.setAttributeNode(attrTypekk4);
         
         Element carname12diyo = doc.createElement("vibhakti");
         Attr attrType12diyo = doc.createAttribute("presence");
         attrType12diyo.setValue("Optional");
         carname12diyo.setAttributeNode(attrType12diyo);
         carname12diyo.appendChild(
         doc.createTextNode("बाट or हरुबाट or देखि"));
         supercar1.appendChild(carname12diyo);
         Attr attrTypekk12diyo = doc.createAttribute("karaka");
         attrTypekk12diyo.setValue("Apadan(K5)");
         carname12diyo.setAttributeNode(attrTypekk12diyo);
         Attr attrTypeform4diyo = doc.createAttribute("Form");
         attrTypeform4diyo.setValue("Singular and Plural");
         carname12diyo.setAttributeNode(attrTypeform4diyo);
         

         Element carname13diyo = doc.createElement("vibhakti");
         Attr attrType13diyo = doc.createAttribute("presence");
         attrType13diyo.setValue("Optional");
         carname13diyo.setAttributeNode(attrType13diyo);
         carname13diyo.appendChild(
         doc.createTextNode("को or करुको or रो or नो"));
         supercar1.appendChild(carname13diyo);
         Attr attrTypekk13diyo = doc.createAttribute("karaka");
         attrTypekk13diyo.setValue("Sambandh(K6)");
         carname13diyo.setAttributeNode(attrTypekk13diyo);
         Attr attrTypeform5diyo = doc.createAttribute("Form");
         attrTypeform5diyo.setValue("Singular and Plural");
         carname13diyo.setAttributeNode(attrTypeform5diyo);


         Element carname14diyo = doc.createElement("vibhakti");
         Attr attrType14diyo = doc.createAttribute("presence");
         attrType14diyo.setValue("Optional");
         carname14diyo.setAttributeNode(attrType14diyo);
         carname14diyo.appendChild(
         doc.createTextNode("मा or हरुमा "));
         supercar1.appendChild(carname14diyo);
         Attr attrTypekk14diyo = doc.createAttribute("karaka");
         attrTypekk14diyo.setValue("Adhikaran(K7)");
         carname14diyo.setAttributeNode(attrTypekk14diyo);
         Attr attrTypeform6diyo = doc.createAttribute("Form");
         attrTypeform6diyo.setValue("Singular and Plural");
         carname14diyo.setAttributeNode(attrTypeform6diyo);
         
         Element carnamepc20diyo = doc.createElement("vibhakti");
         Attr attrTypepc22diyo = doc.createAttribute("presence");
         attrTypepc22diyo.setValue("Optional");
         carnamepc20diyo.setAttributeNode(attrTypepc22diyo);
         carnamepc20diyo.appendChild(
         doc.createTextNode("null"));
         supercar1.appendChild(carnamepc20diyo);
         Attr attrTypekkpc23diyo = doc.createAttribute("karaka");
         attrTypekkpc23diyo.setValue("Locative(K2p)");
         carnamepc20diyo.setAttributeNode(attrTypekkpc23diyo);

  	 Element carname101 = doc.createElement("Tense");
         Attr attrType101 = doc.createAttribute("Vibhakti");
         attrType101.setValue("Simple Past Tense");
         carname101.setAttributeNode(attrType101);
         supercar1.appendChild(carname101);


// FRAME OF DIE

        // Element rootElement2 = doc.createElement("दियो");
         //doc.appendChild(rootElement2);

         //  supercars element
         Element supercar4 = doc.createElement("supercars");
         rootElement.appendChild(supercar4);

         // setting attribute to element
         Attr attr4 = doc.createAttribute("Verb");
         attr4.setValue("दिए");
         supercar4.setAttributeNode(attr4);
	 
/*         Element vibhakti = doc.createElement("vibhakti");
         Attr attrType = doc.createAttribute("presence");
         attrType.setValue("medatory");
         vibhakti.setAttributeNode(attrType);
         vibhakti.appendChild(
         doc.createTextNode("ko"));
         supercar.appendChild(vibhakti);*/


  	 Element vibhakti15 = doc.createElement("vibhakti");
         Attr attrType15 = doc.createAttribute("presence");
         attrType15.setValue("Mendatory");
         vibhakti15.setAttributeNode(attrType15);
         vibhakti15.appendChild(
         doc.createTextNode("ले"));
         supercar4.appendChild(vibhakti15);
         Attr attrTypekk15 = doc.createAttribute("karaka");
         attrTypekk15.setValue("karta(K1)");
         vibhakti15.setAttributeNode(attrTypekk15);
         	
         
         Element carname16 = doc.createElement("vibhakti");
         Attr attrType16 = doc.createAttribute("presence");
         attrType16.setValue("Optional");
         carname16.setAttributeNode(attrType16);
         carname16.appendChild(
         doc.createTextNode("को or null"));
         supercar4.appendChild(carname16);
         Attr attrTypekk16 = doc.createAttribute("karaka");
         attrTypekk16.setValue("karma(K2)");
         carname16.setAttributeNode(attrTypekk16);


         Element carname17 = doc.createElement("vibhakti");
         Attr attrType17 = doc.createAttribute("presence");
         attrType17.setValue("Optional");
         carname17.setAttributeNode(attrType17);
         carname17.appendChild(
         doc.createTextNode("Dwara or द्वारा"));
         supercar4.appendChild(carname17);
         Attr attrTypekk17 = doc.createAttribute("karaka");
         attrTypekk17.setValue("karana(K3)");
         carname17.setAttributeNode(attrTypekk17);


         Element carname18 = doc.createElement("vibhakti");
         Attr attrType18 = doc.createAttribute("presence");
         attrType18.setValue("Mendatory");
         carname18.setAttributeNode(attrType18);
         carname18.appendChild(
         doc.createTextNode("लाई"));
         supercar4.appendChild(carname18);
         Attr attrTypekk18 = doc.createAttribute("karaka");
         attrTypekk18.setValue("Sampradan(K4)");
         carname18.setAttributeNode(attrTypekk18);

	 Element carname12die = doc.createElement("vibhakti");
         Attr attrType12die = doc.createAttribute("presence");
         attrType12die.setValue("Optional");
         carname12die.setAttributeNode(attrType12die);
         carname12die.appendChild(
         doc.createTextNode("बाट or हरुबाट or देखि"));
         supercar4.appendChild(carname12die);
         Attr attrTypekk12die = doc.createAttribute("karaka");
         attrTypekk12die.setValue("Apadan(K5)");
         carname12die.setAttributeNode(attrTypekk12die);
         Attr attrTypeform4die = doc.createAttribute("Form");
         attrTypeform4die.setValue("Singular and Plural");
         carname12die.setAttributeNode(attrTypeform4die);
         

         Element carname13die = doc.createElement("vibhakti");
         Attr attrType13die = doc.createAttribute("presence");
         attrType13die.setValue("Optional");
         carname13die.setAttributeNode(attrType13die);
         carname13die.appendChild(
         doc.createTextNode("को or करुको or रो or नो"));
         supercar4.appendChild(carname13die);
         Attr attrTypekk13die = doc.createAttribute("karaka");
         attrTypekk13die.setValue("Sambandh(K6)");
         carname13die.setAttributeNode(attrTypekk13die);
         Attr attrTypeform5die = doc.createAttribute("Form");
         attrTypeform5die.setValue("Singular and Plural");
         carname13die.setAttributeNode(attrTypeform5die);


         Element carname14die = doc.createElement("vibhakti");
         Attr attrType14die = doc.createAttribute("presence");
         attrType14die.setValue("Optional");
         carname14die.setAttributeNode(attrType14die);
         carname14die.appendChild(
         doc.createTextNode("मा or हरुमा "));
         supercar4.appendChild(carname14die);
         Attr attrTypekk14die = doc.createAttribute("karaka");
         attrTypekk14die.setValue("Adhikaran(K7)");
         carname14die.setAttributeNode(attrTypekk14die);
         Attr attrTypeform6die = doc.createAttribute("Form");
         attrTypeform6die.setValue("Singular and Plural");
         carname14die.setAttributeNode(attrTypeform6die);
         
         Element carnamepc20die = doc.createElement("vibhakti");
         Attr attrTypepc22die = doc.createAttribute("presence");
         attrTypepc22die.setValue("Optional");
         carnamepc20die.setAttributeNode(attrTypepc22die);
         carnamepc20die.appendChild(
         doc.createTextNode("null"));
         supercar4.appendChild(carnamepc20die);
         Attr attrTypekkpc23die = doc.createAttribute("karaka");
         attrTypekkpc23die.setValue("Locative(K2p)");
         carnamepc20die.setAttributeNode(attrTypekkpc23die);
	
  	 Element carname102 = doc.createElement("Tense");
         Attr attrType102 = doc.createAttribute("Vibhakti");
         attrType102.setValue("Simple Past Tense");
         carname102.setAttributeNode(attrType102);
         supercar4.appendChild(carname102);



// FRAME OF JAMMA

        // Element rootElement2 = doc.createElement("दियो");
         //doc.appendChild(rootElement2);

         //  supercars element
         Element supercar5 = doc.createElement("supercars");
         rootElement.appendChild(supercar5);

         // setting attribute to element
         Attr attr5 = doc.createAttribute("Verb");
         attr5.setValue("जम्मा");
         supercar5.setAttributeNode(attr5);
	 
/*         Element vibhakti = doc.createElement("vibhakti");
         Attr attrType = doc.createAttribute("presence");
         attrType.setValue("medatory");
         vibhakti.setAttributeNode(attrType);
         vibhakti.appendChild(
         doc.createTextNode("ko"));
         supercar.appendChild(vibhakti);*/


  	 Element vibhakti16 = doc.createElement("vibhakti");
         Attr attrType19 = doc.createAttribute("presence");
         attrType19.setValue("Optional");
         vibhakti16.setAttributeNode(attrType19);
         vibhakti16.appendChild(
         doc.createTextNode("ले"));
         supercar5.appendChild(vibhakti16);
         Attr attrTypekk19 = doc.createAttribute("karaka");
         attrTypekk19.setValue("karta(K1)");
         vibhakti16.setAttributeNode(attrTypekk19);
         	
         
         Element carname19 = doc.createElement("vibhakti");
         Attr attrType20 = doc.createAttribute("presence");
         attrType20.setValue("Optional");
         carname19.setAttributeNode(attrType20);
         carname19.appendChild(
         doc.createTextNode("null"));
         supercar5.appendChild(carname19);
         Attr attrTypekk21 = doc.createAttribute("karaka");
         attrTypekk21.setValue("karma(K2)");
         carname19.setAttributeNode(attrTypekk21);


         Element carname20 = doc.createElement("vibhakti");
         Attr attrType22 = doc.createAttribute("presence");
         attrType22.setValue("Optional");
         carname20.setAttributeNode(attrType22);
         carname20.appendChild(
         doc.createTextNode("Dwara or द्वारा"));
         supercar5.appendChild(carname20);
         Attr attrTypekk23 = doc.createAttribute("karaka");
         attrTypekk23.setValue("karana(K3)");
         carname20.setAttributeNode(attrTypekk23);


         Element carname21 = doc.createElement("vibhakti");
         Attr attrType24 = doc.createAttribute("presence");
         attrType24.setValue("Optional");
         carname21.setAttributeNode(attrType24);
         carname21.appendChild(
         doc.createTextNode("लाई"));
         supercar5.appendChild(carname21);
         Attr attrTypekk25 = doc.createAttribute("karaka");
         attrTypekk25.setValue("Sampradan(K4)");
         carname21.setAttributeNode(attrTypekk25);
         
         Element carname12jamma = doc.createElement("vibhakti");
         Attr attrType12jamma = doc.createAttribute("presence");
         attrType12jamma.setValue("Optional");
         carname12jamma.setAttributeNode(attrType12jamma);
         carname12jamma.appendChild(
         doc.createTextNode("बाट or हरुबाट or देखि"));
         supercar5.appendChild(carname12jamma);
         Attr attrTypekk12jamma = doc.createAttribute("karaka");
         attrTypekk12jamma.setValue("Apadan(K5)");
         carname12jamma.setAttributeNode(attrTypekk12jamma);
         Attr attrTypeform4jamma = doc.createAttribute("Form");
         attrTypeform4jamma.setValue("Singular and Plural");
         carname12jamma.setAttributeNode(attrTypeform4jamma);
         

         Element carname13jamma = doc.createElement("vibhakti");
         Attr attrType13jamma = doc.createAttribute("presence");
         attrType13jamma.setValue("Optional");
         carname13jamma.setAttributeNode(attrType13jamma);
         carname13jamma.appendChild(
         doc.createTextNode("को or करुको or रो or नो"));
         supercar5.appendChild(carname13jamma);
         Attr attrTypekk13jamma = doc.createAttribute("karaka");
         attrTypekk13jamma.setValue("Sambandh(K6)");
         carname13jamma.setAttributeNode(attrTypekk13jamma);
         Attr attrTypeform5jamma = doc.createAttribute("Form");
         attrTypeform5jamma.setValue("Singular and Plural");
         carname13jamma.setAttributeNode(attrTypeform5jamma);


         Element carname14jamma = doc.createElement("vibhakti");
         Attr attrType14jamma = doc.createAttribute("presence");
         attrType14jamma.setValue("Optional");
         carname14jamma.setAttributeNode(attrType14jamma);
         carname14jamma.appendChild(
         doc.createTextNode("मा or हरुमा "));
         supercar5.appendChild(carname14jamma);
         Attr attrTypekk14jamma = doc.createAttribute("karaka");
         attrTypekk14jamma.setValue("Adhikaran(K7)");
         carname14jamma.setAttributeNode(attrTypekk14jamma);
         Attr attrTypeform6jamma = doc.createAttribute("Form");
         attrTypeform6jamma.setValue("Singular and Plural");
         carname14jamma.setAttributeNode(attrTypeform6jamma);
         
         Element carnamepc20jamma = doc.createElement("vibhakti");
         Attr attrTypepc22jamma = doc.createAttribute("presence");
         attrTypepc22jamma.setValue("Optional");
         carnamepc20jamma.setAttributeNode(attrTypepc22jamma);
         carnamepc20jamma.appendChild(
         doc.createTextNode("null"));
         supercar5.appendChild(carnamepc20jamma);
         Attr attrTypekkpc23jamma = doc.createAttribute("karaka");
         attrTypekkpc23jamma.setValue("Locative(K2p)");
         carnamepc20jamma.setAttributeNode(attrTypekkpc23jamma);

  	 Element carname103 = doc.createElement("Tense");
         Attr attrType103 = doc.createAttribute("Vibhakti");
         attrType103.setValue("Simple Past Tense");
         carname103.setAttributeNode(attrType103);
         supercar5.appendChild(carname103);



// FRAME OF DEKHYO

        // Element rootElement2 = doc.createElement("दियो");
         //doc.appendChild(rootElement2);

         //  supercars element
         Element supercar6 = doc.createElement("supercars");
         rootElement.appendChild(supercar6);

         // setting attribute to element
         Attr attr6 = doc.createAttribute("Verb");
         attr6.setValue("देख्यो");
         supercar6.setAttributeNode(attr6);
	 
/*         Element vibhakti = doc.createElement("vibhakti");
         Attr attrType = doc.createAttribute("presence");
         attrType.setValue("medatory");
         vibhakti.setAttributeNode(attrType);
         vibhakti.appendChild(
         doc.createTextNode("ko"));
         supercar.appendChild(vibhakti);*/


  	 Element vibhakti17 = doc.createElement("vibhakti");
         Attr attrType25 = doc.createAttribute("presence");
         attrType25.setValue("Mendatory");
         vibhakti17.setAttributeNode(attrType25);
         vibhakti17.appendChild(
         doc.createTextNode("ले  or को or null or लाई"));
         supercar6.appendChild(vibhakti17);
         Attr attrTypekk26 = doc.createAttribute("karaka");
         attrTypekk26.setValue("karta(K1)");
         vibhakti17.setAttributeNode(attrTypekk26);
         	
         
         Element carname22 = doc.createElement("vibhakti");
         Attr attrType26 = doc.createAttribute("presence");
         attrType26.setValue("Mendatory");
         carname22.setAttributeNode(attrType26);
         carname22.appendChild(
         doc.createTextNode("को or null or लाई"));
         supercar6.appendChild(carname22);
         Attr attrTypekk27 = doc.createAttribute("karaka");
         attrTypekk27.setValue("karma(K2)");
         carname22.setAttributeNode(attrTypekk27);


         Element carname23 = doc.createElement("vibhakti");
         Attr attrType27 = doc.createAttribute("presence");
         attrType27.setValue("Optional");
         carname23.setAttributeNode(attrType27);
         carname23.appendChild(
         doc.createTextNode("Dwara or द्वारा"));
         supercar6.appendChild(carname23);
         Attr attrTypekk28 = doc.createAttribute("karaka");
         attrTypekk28.setValue("karana(K3)");
         carname23.setAttributeNode(attrTypekk28);


         Element carname24 = doc.createElement("vibhakti");
         Attr attrType28 = doc.createAttribute("presence");
         attrType28.setValue("Optional");
         carname24.setAttributeNode(attrType28);
         carname24.appendChild(
         doc.createTextNode("लाई"));
         supercar6.appendChild(carname24);
         Attr attrTypekk29 = doc.createAttribute("karaka");
         attrTypekk29.setValue("Sampradan(K4)");
         carname24.setAttributeNode(attrTypekk29);
         
         Element carname12dekhyo = doc.createElement("vibhakti");
         Attr attrType12dekhyo = doc.createAttribute("presence");
         attrType12dekhyo.setValue("Optional");
         carname12dekhyo.setAttributeNode(attrType12dekhyo);
         carname12dekhyo.appendChild(
         doc.createTextNode("बाट or हरुबाट or देखि"));
         supercar6.appendChild(carname12dekhyo);
         Attr attrTypekk12dekhyo = doc.createAttribute("karaka");
         attrTypekk12dekhyo.setValue("Apadan(K5)");
         carname12dekhyo.setAttributeNode(attrTypekk12dekhyo);
         Attr attrTypeform4dekhyo = doc.createAttribute("Form");
         attrTypeform4dekhyo.setValue("Singular and Plural");
         carname12dekhyo.setAttributeNode(attrTypeform4dekhyo);
         

         Element carname13dekhyo = doc.createElement("vibhakti");
         Attr attrType13dekhyo = doc.createAttribute("presence");
         attrType13dekhyo.setValue("Optional");
         carname13dekhyo.setAttributeNode(attrType13dekhyo);
         carname13dekhyo.appendChild(
         doc.createTextNode("को or करुको or रो or नो"));
         supercar6.appendChild(carname13dekhyo);
         Attr attrTypekk13dekhyo = doc.createAttribute("karaka");
         attrTypekk13dekhyo.setValue("Sambandh(K6)");
         carname13dekhyo.setAttributeNode(attrTypekk13dekhyo);
         Attr attrTypeform5dekhyo = doc.createAttribute("Form");
         attrTypeform5dekhyo.setValue("Singular and Plural");
         carname13dekhyo.setAttributeNode(attrTypeform5dekhyo);


         Element carname14dekhyo = doc.createElement("vibhakti");
         Attr attrType14dekhyo = doc.createAttribute("presence");
         attrType14dekhyo.setValue("Optional");
         carname14dekhyo.setAttributeNode(attrType14dekhyo);
         carname14dekhyo.appendChild(
         doc.createTextNode("मा or हरुमा "));
         supercar6.appendChild(carname14dekhyo);
         Attr attrTypekk14dekhyo = doc.createAttribute("karaka");
         attrTypekk14dekhyo.setValue("Adhikaran(K7)");
         carname14dekhyo.setAttributeNode(attrTypekk14dekhyo);
         Attr attrTypeform6dekhyo = doc.createAttribute("Form");
         attrTypeform6dekhyo.setValue("Singular and Plural");
         carname14dekhyo.setAttributeNode(attrTypeform6dekhyo);
         
         Element carnamepc20dekhyo = doc.createElement("vibhakti");
         Attr attrTypepc22dekhyo = doc.createAttribute("presence");
         attrTypepc22dekhyo.setValue("Optional");
         carnamepc20dekhyo.setAttributeNode(attrTypepc22dekhyo);
         carnamepc20dekhyo.appendChild(
         doc.createTextNode("null"));
         supercar6.appendChild(carnamepc20dekhyo);
         Attr attrTypekkpc23dekhyo = doc.createAttribute("karaka");
         attrTypekkpc23dekhyo.setValue("Locative(K2p)");
         carnamepc20dekhyo.setAttributeNode(attrTypekkpc23dekhyo);

  	 Element carname104 = doc.createElement("Tense");
         Attr attrType104 = doc.createAttribute("Vibhakti");
         attrType104.setValue("Simple Past Tense");
         carname104.setAttributeNode(attrType104);
         supercar6.appendChild(carname104);

// FRAME OF पठाउँछ

        // Element rootElement2 = doc.createElement("दियो");
         //doc.appendChild(rootElement2);

         //  supercars element
         Element supercarpc5 = doc.createElement("supercars");
         rootElement.appendChild(supercarpc5);

         // setting attribute to element
         Attr attrpc5 = doc.createAttribute("Verb");
         attrpc5.setValue("पठाउँछ");
         supercarpc5.setAttributeNode(attrpc5);
	 
/*         Element vibhakti = doc.createElement("vibhakti");
         Attr attrType = doc.createAttribute("presence");
         attrType.setValue("medatory");
         vibhakti.setAttributeNode(attrType);
         vibhakti.appendChild(
         doc.createTextNode("ko"));
         supercar.appendChild(vibhakti);*/


  	 Element vibhaktipc16 = doc.createElement("vibhakti");
         Attr attrTypepc19 = doc.createAttribute("presence");
         attrTypepc19.setValue("Mendatory");
         vibhaktipc16.setAttributeNode(attrTypepc19);
         vibhaktipc16.appendChild(
         doc.createTextNode("ले"));
         supercarpc5.appendChild(vibhaktipc16);
         Attr attrTypekkpc19 = doc.createAttribute("karaka");
         attrTypekkpc19.setValue("karta(K1)");                     // Noun is Transitive or Ergative i.e. after karta immediately karma appears
         vibhaktipc16.setAttributeNode(attrTypekkpc19);
         	
         
         Element carnamepc19 = doc.createElement("vibhakti");
         Attr attrTypepc20 = doc.createAttribute("presence");
         attrTypepc20.setValue("Mendatory");
         carnamepc19.setAttributeNode(attrTypepc20);
         carnamepc19.appendChild(
         doc.createTextNode("लाई or null"));
         supercarpc5.appendChild(carnamepc19);
         Attr attrTypekkpc21 = doc.createAttribute("karaka");
         attrTypekkpc21.setValue("karma(K2)");
         carnamepc19.setAttributeNode(attrTypekkpc21);

 
         Element carnamepath = doc.createElement("vibhakti");
         Attr attrTypepath = doc.createAttribute("presence");
         attrTypepath.setValue("Optional");
         carnamepath.setAttributeNode(attrTypepath);
         carnamepath.appendChild(
         doc.createTextNode("Dwara or द्वारा"));
         supercarpc5.appendChild(carnamepath);
         Attr attrTypekkpath = doc.createAttribute("karaka");
         attrTypekkpath.setValue("karana(K3)");
         carnamepath.setAttributeNode(attrTypekkpath);
         
         Element carname11pathaunchh = doc.createElement("vibhakti");
         Attr attrType11pathaunchh = doc.createAttribute("presence");
         attrType11pathaunchh.setValue("Optional");
         carname11pathaunchh.setAttributeNode(attrType11pathaunchh);
         carname11pathaunchh.appendChild(
         doc.createTextNode("लाई or हरुलाई"));
         supercarpc5.appendChild(carname11pathaunchh);
         Attr attrTypekk11pathaunchh = doc.createAttribute("karaka");
         attrTypekk11pathaunchh.setValue("Sampradan(K4)");
         carname11pathaunchh.setAttributeNode(attrTypekk11pathaunchh);
         Attr attrTypeform3pathaunchh = doc.createAttribute("Form");
         attrTypeform3pathaunchh.setValue("Singular and Plural");
         carname11pathaunchh.setAttributeNode(attrTypeform3pathaunchh);
         
         Element carname12pathaunchh = doc.createElement("vibhakti");
         Attr attrType12pathaunchh = doc.createAttribute("presence");
         attrType12pathaunchh.setValue("Optional");
         carname12pathaunchh.setAttributeNode(attrType12pathaunchh);
         carname12pathaunchh.appendChild(
         doc.createTextNode("बाट or हरुबाट or देखि"));
         supercarpc5.appendChild(carname12pathaunchh);
         Attr attrTypekk12pathaunchh = doc.createAttribute("karaka");
         attrTypekk12pathaunchh.setValue("Apadan(K5)");
         carname12pathaunchh.setAttributeNode(attrTypekk12pathaunchh);
         Attr attrTypeform4pathaunchh = doc.createAttribute("Form");
         attrTypeform4pathaunchh.setValue("Singular and Plural");
         carname12pathaunchh.setAttributeNode(attrTypeform4pathaunchh);
         

         Element carname13pathaunchh = doc.createElement("vibhakti");
         Attr attrType13pathaunchh = doc.createAttribute("presence");
         attrType13pathaunchh.setValue("Optional");
         carname13pathaunchh.setAttributeNode(attrType13pathaunchh);
         carname13pathaunchh.appendChild(
         doc.createTextNode("को or करुको or रो or नो"));
         supercarpc5.appendChild(carname13pathaunchh);
         Attr attrTypekk13pathaunchh = doc.createAttribute("karaka");
         attrTypekk13pathaunchh.setValue("Sambandh(K6)");
         carname13pathaunchh.setAttributeNode(attrTypekk13pathaunchh);
         Attr attrTypeform5pathaunchh = doc.createAttribute("Form");
         attrTypeform5pathaunchh.setValue("Singular and Plural");
         carname13pathaunchh.setAttributeNode(attrTypeform5pathaunchh);


         Element carname14pathaunchh = doc.createElement("vibhakti");
         Attr attrType14pathaunchh = doc.createAttribute("presence");
         attrType14pathaunchh.setValue("Optional");
         carname14pathaunchh.setAttributeNode(attrType14pathaunchh);
         carname14pathaunchh.appendChild(
         doc.createTextNode("मा or हरुमा "));
         supercarpc5.appendChild(carname14pathaunchh);
         Attr attrTypekk14pathaunchh = doc.createAttribute("karaka");
         attrTypekk14pathaunchh.setValue("Adhikaran(K7)");
         carname14pathaunchh.setAttributeNode(attrTypekk14pathaunchh);
         Attr attrTypeform6pathaunchh = doc.createAttribute("Form");
         attrTypeform6pathaunchh.setValue("Singular and Plural");
         carname14pathaunchh.setAttributeNode(attrTypeform6pathaunchh);
         
         Element carnamepc20 = doc.createElement("vibhakti");
         Attr attrTypepc22 = doc.createAttribute("presence");
         attrTypepc22.setValue("Mendatory");
         carnamepc20.setAttributeNode(attrTypepc22);
         carnamepc20.appendChild(
         doc.createTextNode("null"));
         supercarpc5.appendChild(carnamepc20);
         Attr attrTypekkpc23 = doc.createAttribute("karaka");
         attrTypekkpc23.setValue("Locative(K2p)");
         carnamepc20.setAttributeNode(attrTypekkpc23);

  	 Element carnamepc103 = doc.createElement("Tense");
         Attr attrTypepc103 = doc.createAttribute("Vibhakti");
         attrTypepc103.setValue("Simple Present Tense");
         carnamepc103.setAttributeNode(attrTypepc103);
         supercarpc5.appendChild(carnamepc103);



// FRAME OF पठाउँछ for sampradan (Dative or K4)

        // Element rootElement2 = doc.createElement("दियो");
         //doc.appendChild(rootElement2);

         //  supercars element
         Element supercarpcn5 = doc.createElement("supercars");
         rootElement.appendChild(supercarpcn5);

         // setting attribute to element
         Attr attrpcn5 = doc.createAttribute("Verb");
         attrpcn5.setValue("पठाउँछ");
         supercarpcn5.setAttributeNode(attrpcn5);
	 
/*         Element vibhakti = doc.createElement("vibhakti");
         Attr attrType = doc.createAttribute("presence");
         attrType.setValue("medatory");
         vibhakti.setAttributeNode(attrType);
         vibhakti.appendChild(
         doc.createTextNode("ko"));
         supercar.appendChild(vibhakti);*/


  	 Element vibhaktipcn16 = doc.createElement("vibhakti");
         Attr attrTypepcn19 = doc.createAttribute("presence");
         attrTypepcn19.setValue("Mendatory");
         vibhaktipcn16.setAttributeNode(attrTypepcn19);
         vibhaktipcn16.appendChild(
         doc.createTextNode("null"));
         supercarpcn5.appendChild(vibhaktipcn16);
         Attr attrTypekkpcn19 = doc.createAttribute("karaka");
         attrTypekkpcn19.setValue("karta(K1)");               // Noun is Transitive or Ergative i.e. after karta immediately karma does not appear
         vibhaktipcn16.setAttributeNode(attrTypekkpcn19);
         	
        Element carnamepcn20 = doc.createElement("vibhakti");
         Attr attrTypepcn22 = doc.createAttribute("presence");
         attrTypepcn22.setValue("Mendatory");
         carnamepcn20.setAttributeNode(attrTypepcn22);
         carnamepcn20.appendChild(
         doc.createTextNode("null"));
         supercarpcn5.appendChild(carnamepcn20);
         Attr attrTypekkpcn23 = doc.createAttribute("karaka");
         attrTypekkpcn23.setValue("karma(K2)");
         carnamepcn20.setAttributeNode(attrTypekkpcn23);

	 Element carnamepath1 = doc.createElement("vibhakti");
         Attr attrTypepath1 = doc.createAttribute("presence");
         attrTypepath1.setValue("Optional");
         carnamepath1.setAttributeNode(attrTypepath1);
         carnamepath1.appendChild(
         doc.createTextNode("Dwara or द्वारा"));
         supercarpcn5.appendChild(carnamepath1);
         Attr attrTypekkpath1 = doc.createAttribute("karaka");
         attrTypekkpath1.setValue("karana(K3)");
         carnamepath1.setAttributeNode(attrTypekkpath1);
         
         Element carnamepcn19 = doc.createElement("vibhakti");
         Attr attrTypepcn20 = doc.createAttribute("presence");
         attrTypepcn20.setValue("Mendatory");
         carnamepcn19.setAttributeNode(attrTypepcn20);
         carnamepcn19.appendChild(
         doc.createTextNode("लाई or null"));
         supercarpcn5.appendChild(carnamepcn19);
         Attr attrTypekkpcn21 = doc.createAttribute("karaka");
         attrTypekkpcn21.setValue("Sampradan(K4)");
         carnamepcn19.setAttributeNode(attrTypekkpcn21);

	Element carname12pathaunchh1 = doc.createElement("vibhakti");
         Attr attrType12pathaunchh1 = doc.createAttribute("presence");
         attrType12pathaunchh1.setValue("Optional");
         carname12pathaunchh1.setAttributeNode(attrType12pathaunchh1);
         carname12pathaunchh1.appendChild(
         doc.createTextNode("बाट or हरुबाट or देखि"));
         supercarpcn5.appendChild(carname12pathaunchh1);
         Attr attrTypekk12pathaunchh1 = doc.createAttribute("karaka");
         attrTypekk12pathaunchh1.setValue("Apadan(K5)");
         carname12pathaunchh1.setAttributeNode(attrTypekk12pathaunchh1);
         Attr attrTypeform4pathaunchh1 = doc.createAttribute("Form");
         attrTypeform4pathaunchh1.setValue("Singular and Plural");
         carname12pathaunchh1.setAttributeNode(attrTypeform4pathaunchh1);
         

         Element carname13pathaunchh1 = doc.createElement("vibhakti");
         Attr attrType13pathaunchh1 = doc.createAttribute("presence");
         attrType13pathaunchh1.setValue("Optional");
         carname13pathaunchh1.setAttributeNode(attrType13pathaunchh1);
         carname13pathaunchh1.appendChild(
         doc.createTextNode("को or करुको or रो or नो"));
         supercarpcn5.appendChild(carname13pathaunchh1);
         Attr attrTypekk13pathaunchh1 = doc.createAttribute("karaka");
         attrTypekk13pathaunchh1.setValue("Sambandh(K6)");
         carname13pathaunchh1.setAttributeNode(attrTypekk13pathaunchh1);
         Attr attrTypeform5pathaunchh1 = doc.createAttribute("Form");
         attrTypeform5pathaunchh1.setValue("Singular and Plural");
         carname13pathaunchh1.setAttributeNode(attrTypeform5pathaunchh1);


         Element carname14pathaunchh1 = doc.createElement("vibhakti");
         Attr attrType14pathaunchh1 = doc.createAttribute("presence");
         attrType14pathaunchh1.setValue("Optional");
         carname14pathaunchh1.setAttributeNode(attrType14pathaunchh1);
         carname14pathaunchh1.appendChild(
         doc.createTextNode("मा or हरुमा "));
         supercarpcn5.appendChild(carname14pathaunchh1);
         Attr attrTypekk14pathaunchh1 = doc.createAttribute("karaka");
         attrTypekk14pathaunchh1.setValue("Adhikaran(K7)");
         carname14pathaunchh1.setAttributeNode(attrTypekk14pathaunchh1);
         Attr attrTypeform6pathaunchh1 = doc.createAttribute("Form");
         attrTypeform6pathaunchh1.setValue("Singular and Plural");
         carname14pathaunchh1.setAttributeNode(attrTypeform6pathaunchh1);
         
         Element carnamepc20pathaunchh1 = doc.createElement("vibhakti");
         Attr attrTypepc22pathaunchh1 = doc.createAttribute("presence");
         attrTypepc22pathaunchh1.setValue("Optional");
         carnamepc20pathaunchh1.setAttributeNode(attrTypepc22pathaunchh1);
         carnamepc20pathaunchh1.appendChild(
         doc.createTextNode("null"));
         supercarpcn5.appendChild(carnamepc20pathaunchh1);
         Attr attrTypekkpc23pathaunchh1 = doc.createAttribute("karaka");
         attrTypekkpc23pathaunchh1.setValue("Locative(K2p)");
         carnamepc20pathaunchh1.setAttributeNode(attrTypekkpc23pathaunchh1);
        
 

  	 Element carnamepcn103 = doc.createElement("Tense");
         Attr attrTypepcn103 = doc.createAttribute("Vibhakti");
         attrTypepcn103.setValue("Simple Present Tense");
         carnamepcn103.setAttributeNode(attrTypepcn103);
         supercarpcn5.appendChild(carnamepcn103);


// DEFAULT KARAKA CHART
         Element supercar2 = doc.createElement("supercars");
         rootElement.appendChild(supercar2);

         // setting attribute to element
         Attr attr3 = doc.createAttribute("DEFAULT");
         attr3.setValue("DEFAULT KARAKA CHART");
         supercar2.setAttributeNode(attr3);

 	 Element vibhakti8 = doc.createElement("vibhakti");
         Attr attrType8 = doc.createAttribute("presence");
         attrType8.setValue("Mendatory");
         vibhakti8.setAttributeNode(attrType8);
         vibhakti8.appendChild(
         doc.createTextNode("    ले  or null  or हरु "));
         supercar2.appendChild(vibhakti8);
         Attr attrTypekk8 = doc.createAttribute("karaka");
         attrTypekk8.setValue("karta(K1)");
         vibhakti8.setAttributeNode(attrTypekk8);
         Attr attrTypeform = doc.createAttribute("Form");
         attrTypeform.setValue("Singular and Plural");
         vibhakti8.setAttributeNode(attrTypeform);
//         Attr attrTypeformp = doc.createAttribute("Form");
//	 attrTypeformp.setValue("Plural");
  //       vibhakti8.setAttributeNode(attrTypeformp);         	
         
         Element carname9 = doc.createElement("vibhakti");
         Attr attrType9 = doc.createAttribute("presence");
         attrType9.setValue("Mendatory");
         carname9.setAttributeNode(attrType9);
         carname9.appendChild(
         doc.createTextNode("लाई or null or हरुलाई  "));
         supercar2.appendChild(carname9);
         Attr attrTypekk9 = doc.createAttribute("karaka");
         attrTypekk9.setValue("karma(K2)");
         carname9.setAttributeNode(attrTypekk9);
         Attr attrTypeform1 = doc.createAttribute("Form");
         attrTypeform1.setValue("Singular and Plural");
         carname9.setAttributeNode(attrTypeform1);



         Element carname10 = doc.createElement("vibhakti");
         Attr attrType10 = doc.createAttribute("presence");
         attrType10.setValue("Optional");
         carname10.setAttributeNode(attrType10);
         carname10.appendChild(
         doc.createTextNode("ले or हरुले "));
         supercar2.appendChild(carname10);
         Attr attrTypekk10 = doc.createAttribute("karaka");
         attrTypekk10.setValue("karana(K3)");
         carname10.setAttributeNode(attrTypekk10);
         Attr attrTypeform2 = doc.createAttribute("Form");
         attrTypeform2.setValue("Singular and Plural");
         carname10.setAttributeNode(attrTypeform2);
         
         Element carname11 = doc.createElement("vibhakti");
         Attr attrType11 = doc.createAttribute("presence");
         attrType11.setValue("Optional");
         carname11.setAttributeNode(attrType11);
         carname11.appendChild(
         doc.createTextNode("लाई or हरुलाई"));
         supercar2.appendChild(carname11);
         Attr attrTypekk11 = doc.createAttribute("karaka");
         attrTypekk11.setValue("Sampradan(K4)");
         carname11.setAttributeNode(attrTypekk11);
         Attr attrTypeform3 = doc.createAttribute("Form");
         attrTypeform3.setValue("Singular and Plural");
         carname11.setAttributeNode(attrTypeform3);
         
         Element carname12 = doc.createElement("vibhakti");
         Attr attrType12 = doc.createAttribute("presence");
         attrType12.setValue("Optional");
         carname12.setAttributeNode(attrType12);
         carname12.appendChild(
         doc.createTextNode("बाट or हरुबाट or देखि"));
         supercar2.appendChild(carname12);
         Attr attrTypekk12 = doc.createAttribute("karaka");
         attrTypekk12.setValue("Apadan(K5)");
         carname12.setAttributeNode(attrTypekk12);
         Attr attrTypeform4 = doc.createAttribute("Form");
         attrTypeform4.setValue("Singular and Plural");
         carname12.setAttributeNode(attrTypeform4);
         

         Element carname13 = doc.createElement("vibhakti");
         Attr attrType13 = doc.createAttribute("presence");
         attrType13.setValue("Optional");
         carname13.setAttributeNode(attrType13);
         carname13.appendChild(
         doc.createTextNode("को or करुको or रो or नो"));
         supercar2.appendChild(carname13);
         Attr attrTypekk13 = doc.createAttribute("karaka");
         attrTypekk13.setValue("Sambandh(K6)");
         carname13.setAttributeNode(attrTypekk13);
         Attr attrTypeform5 = doc.createAttribute("Form");
         attrTypeform5.setValue("Singular and Plural");
         carname13.setAttributeNode(attrTypeform5);


         Element carname14 = doc.createElement("vibhakti");
         Attr attrType14 = doc.createAttribute("presence");
         attrType14.setValue("Optional");
         carname14.setAttributeNode(attrType14);
         carname14.appendChild(
         doc.createTextNode("मा or हरुमा "));
         supercar2.appendChild(carname14);
         Attr attrTypekk14 = doc.createAttribute("karaka");
         attrTypekk14.setValue("Adhikaran(K7)");
         carname14.setAttributeNode(attrTypekk14);
         Attr attrTypeform6 = doc.createAttribute("Form");
         attrTypeform6.setValue("Singular and Plural");
         carname14.setAttributeNode(attrTypeform6);
         
         // write the content into xml file
         TransformerFactory transformerFactory =
         TransformerFactory.newInstance();
         Transformer transformer =
         transformerFactory.newTransformer();
         DOMSource source = new DOMSource(doc);
         StreamResult result =
         new StreamResult(new File("D:\\cars.xml"));
         transformer.transform(source, result);
         // Output to console for testing
         StreamResult consoleResult =
         new StreamResult(System.out);
         transformer.transform(source, consoleResult);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}