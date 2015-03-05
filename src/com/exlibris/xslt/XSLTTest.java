package com.exlibris.xslt;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

public class XSLTTest
{
    public static void main(String[] args) throws FileNotFoundException
    {
    	long startTime = System.currentTimeMillis();

        String dataXML = "C:/Users/MichaelB/Desktop/New folder (3)/in_new.xml";
        String inputXSL = "E:/dps/workspace_alto/book-reader-pre-processor-alto/PLUGIN-INF/transformer_logical.xsl";

        String xml = new Scanner(new File(dataXML)).useDelimiter("\\Z").next();
        String xslFileContent = new Scanner(new File(inputXSL)).useDelimiter("\\Z").next();

        String result=runXSLTransformer(xml,xslFileContent);
        result = result.replaceAll("\"", "'");
        System.out.println(result);
        startTime  = System.currentTimeMillis() - startTime;
        System.out.println("time: " +  startTime);
    }

	public static String getIamgeName(String altoName,String metsFilePath) {
		String imageName=null;
		try {
			InputStream is = new FileInputStream(metsFilePath);

			DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = xmlFactory.newDocumentBuilder();
			Document xmlDoc = docBuilder.parse(is);

			String tt = xmlToString(xmlDoc);
			PrintWriter out = new PrintWriter("C:/Users/MichaelB/Desktop/New folder (3)/filename.txt");
			out.println(tt);

			XPathFactory xpathFact = XPathFactory.newInstance();
			XPath xpath = xpathFact.newXPath();

		        String groupId = (String) xpath.evaluate("/mets/fileSec/fileGrp[@ID='ALTOGRP']/file[@ID='"+altoName+"']/@GROUPID", xmlDoc, XPathConstants.STRING);
		        imageName = (String) xpath.evaluate("/mets/fileSec/fileGrp[@ID='IMGGRP']/file[@GROUPID='"+groupId+"']/FLocat/@href", xmlDoc, XPathConstants.STRING);
			int lastIndexOfSlash = imageName.lastIndexOf("/");
			imageName = imageName.substring(lastIndexOfSlash+1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageName;
	}

	public static String runXSLTransformer(String xml, String xslFileContent) {
		if (xml == null || xslFileContent == null) {
			return null;
		}
		xml = fixXML(xml);
		StringWriter outputXML=null;
		try {
			DOMParser parser = new DOMParser();
			InputSource inputSource = new InputSource(new StringReader(xml));
			parser.parse(inputSource);
			org.w3c.dom.Document doc = parser.getDocument();

			Source xmlSource = new DOMSource(doc);

			outputXML = new StringWriter();
			Result result = new StreamResult(outputXML);
			Source xslSource = new StreamSource(new StringReader(xslFileContent));

			Transformer  transformer = TransformerFactory.newInstance().newTransformer(xslSource);
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
//			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
//			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        transformer.transform(xmlSource, result);
		} catch (Exception e) {
			return null;
		}

		return outputXML.toString();
	}


	private static String fixXML(String xml) {
		xml = xml.replaceAll("mets:fptr", "fptr");
		xml = xml.replaceAll("mets:div", "div");
		xml = xml.replaceAll("mets:structMap", "structMap");
		return xml;
	}


	  public static String xmlToString(Document doc) {
		    String xmlString = null;
		    try {
		        Source source = new DOMSource(doc);
		        StringWriter stringWriter = new StringWriter();
		        Result result = new StreamResult(stringWriter);
		        TransformerFactory factory = TransformerFactory.newInstance();
		        Transformer transformer = factory.newTransformer();
		        transformer.transform(source, result);
		        xmlString = stringWriter.getBuffer().toString();
		    } catch (TransformerConfigurationException e) {
		        e.printStackTrace();
		    } catch (TransformerException e) {
		        e.printStackTrace();
		    }
		    return xmlString;
		}




}