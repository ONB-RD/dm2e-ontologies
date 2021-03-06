package eu.dm2e.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.Ontology;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import eu.dm2e.NS;
import eu.dm2e.NS.OWLAnnotation;

/**
 * Utility class for exposing class constants and enum values to JSON.
 *
 * @see eu.dm2e.ws.NS
 *
 * @author Konstantin Baierer
 */
public class NSExporter {
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(NSExporter.class);
	
	public static String exportInnerClassConstantsToJSON(Class clazz) {
		
		JsonObject nsObj = new JsonObject();
		
		Class[] listOfInnerClasses = clazz.getDeclaredClasses();
		
		for (Class innerClass : listOfInnerClasses) {
			JsonObject innerClassObj = new JsonObject();
			nsObj.add(innerClass.getSimpleName(), innerClassObj);
			Field[] listOfFields = innerClass.getFields();
			for (Field field : listOfFields) {
				if (! Modifier.isStatic(field.getModifiers())
//						||
//					! Modifier.isStatic(field.getModifiers())
////						||
//					field.getName().equals("BASE")
//						||
//					! (
//						field.getName().startsWith("PROP_")
//							||
//						field.getName().startsWith("CLASS_")
//					  )
					)
					continue;
				try {
					innerClassObj.addProperty(field.getName(), (String)field.get(null));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		return nsObj.toString();
	}
	
	public static String exportStaticClassToOWL(Class cls) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Field baseField;
		baseField = cls.getField("BASE");
		String base = (String) baseField.get(null);
		return exportStaticClassToOWL(base, cls);
	}
		
	public static String exportStaticClassToOWL(String base, Class cls) throws IllegalArgumentException, IllegalAccessException {
		Logger log = LoggerFactory.getLogger(NSExporter.class);
		OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		Ontology ont = m.createOntology(base);
		m.setNsPrefix("omnom-types", NS.OMNOM_TYPES.BASE);
		m.setNsPrefix("omnom", NS.OMNOM.BASE);
		ont.setComment("Generated by " + NSExporter.class.getName() + " on " + DateTime.now(), "en");
		
		Field[] listOfFields = cls.getFields();
		for (Field field : listOfFields) {
			if (field.getAnnotations().length == 0) { 
				log.debug("No annotations, skipping " + field);
				continue;
			} else if (!field.isAnnotationPresent(NS.OWLAnnotation.class)) {
				log.debug("No OWLAnnotation, skipping " + field);
				continue;
			}
			log.debug("Current field: " + field);
			OWLAnnotation owlAnno = field.getAnnotation(NS.OWLAnnotation.class);
			final String resUri = (String) field.get(null);
			if (owlAnno.owlType().equals(NS.OWL.CLASS)) {
				OntClass res = m.createClass(resUri);
				res.addComment(owlAnno.description(), "en");
				if (! "".equals(owlAnno.label())) res.setLabel(owlAnno.label(), "en");
//				if (owlAnno.deprecated()) res.setRDFType(com.hp.hpl.jena.vocabulary.OWL.DeprecatedClass);
			} else if (owlAnno.owlType().equals(NS.OWL.INDIVIDUAL)) {
				Individual res = m.createIndividual(resUri, m.createResource(owlAnno.rdfType()));
				res.addComment(owlAnno.description(), "en");
				if (! "".equals(owlAnno.label())) res.setLabel(owlAnno.label(), "en");
//				if (owlAnno.deprecated()) m.createLiteralStatement(res, m.createProperty(NS.OWL.DEPRECATED), true);
			} else if (owlAnno.owlType().equals(NS.OWL.OBJECT_PROPERTY)) {
				ObjectProperty res = m.createObjectProperty(resUri);
				res.addComment(owlAnno.description(), "en");
				if (! "".equals(owlAnno.label())) res.setLabel(owlAnno.label(), "en");
				if (! "".equals(owlAnno.domain())) res.setDomain(m.createResource(owlAnno.domain()));
				for (String thisRange : owlAnno.range())
					res.addRange(m.createResource(thisRange));
//				if (owlAnno.deprecated()) res.setRDFType(com.hp.hpl.jena.vocabulary.OWL.DeprecatedProperty);
			} else if (owlAnno.owlType().equals(NS.OWL.DATATYPE_PROPERTY)) {
				DatatypeProperty res = m.createDatatypeProperty(resUri);
				res.addComment(owlAnno.description(), "en");
				if (! "".equals(owlAnno.label())) res.setLabel(owlAnno.label(), "en");
				if (! "".equals(owlAnno.domain())) res.setDomain(m.createResource(owlAnno.domain()));
				for (String thisRange : owlAnno.range())
					res.addRange(m.createResource(thisRange));
//				if (owlAnno.deprecated()) res.setRDFType(com.hp.hpl.jena.vocabulary.OWL.DeprecatedProperty);
			}
		}

		StringWriter sw = new StringWriter();
		m.write(sw, "RDF/XML-ABBREV", null);
		return sw.toString();
	}
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, IOException {
		if (args.length == 1 && args[0].equals("-h")) {
			System.out.println("Usage: NSExporter [<outputPath>] [<baseUri>]");
			System.exit(1);
		}
		Logger log = LoggerFactory.getLogger("MAIN");
		Map<String,Class> nameToClass = new HashMap<>();
		nameToClass.put("omnom.owl", NS.OMNOM.class);
		nameToClass.put("omnom-types.owl", NS.OMNOM_TYPES.class);
		for (Entry<String, Class> entry: nameToClass.entrySet()) {
			try {
				String owlStr = exportStaticClassToOWL(entry.getValue());
				FileUtils.writeStringToFile(new File(entry.getKey()), owlStr);
				log.info("Written {} to {}", entry.getValue(), entry.getKey());
			} catch (NoSuchFieldException | SecurityException e) {
				log.error("Failed to export " + entry.getValue() +" to OWL: {}", e);
			}
		}
//		String base = NS.OMNOM.BASE;
//		String outputPath = "export.owl";
//		if (args.length > 0) outputPath = args[0];
//		if (args.length == 2) base = args[1];
//		String owlStr = exportStaticClassToOWL(base, NS.OMNOM.class);
//		FileUtils.writeStringToFile(new File(outputPath), owlStr);
//		System.out.println("Written ontology " + base + " to " + outputPath);
	}

	public static String exportEnumToJSON(Class clazz) {
		if (! clazz.isEnum()) {
			return "{}";
		}
		JsonObject enumObj = new JsonObject();
		for (Field field : clazz.getDeclaredFields()) {
			if (! field.isEnumConstant()) {
				continue;
			}
			enumObj.addProperty(field.getName(), field.getName());
		}
		return enumObj.toString();
	}

}
