package eu.dm2e.validation.validator;

import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;

import eu.dm2e.NS;
import eu.dm2e.validation.Dm2eValidationReport;
import eu.dm2e.validation.ValidationLevel;
import eu.dm2e.validation.ValidationProblemCategory;

/**
 * Static methods for validating RDF data against the DM2E Data Model
 * 
 * <p>
 * Based on v1.1_Rev1.2-DRAFT of the requirements
 * </p>
 * 
 * @author Konstantin Baierer
 */
public class Dm2eValidator_1_1_Rev_1_2 extends BaseValidator {

	private static final String	modelVersion = "1.1_Rev1.2";

	@Override
	public InputStream getOwlInputStream() {
		return getClass().getResourceAsStream("/dm2e-model/DM2Ev1.1_Rev1.2.owl");
	}

	@Override public String getVersion() {
		return modelVersion;
	}
	
	@Override public Set<Property> build_DateLike_Properties(Model m) {
		Set<Property> ret = new HashSet<>();
		ret.add(prop(m, NS.DCTERMS.PROP_CREATED));
		ret.add(prop(m, NS.DCTERMS.PROP_MODIFIED));
		ret.add(prop(m, NS.DCTERMS.PROP_ISSUED));
		ret.add(prop(m, NS.DCTERMS.PROP_TEMPORAL));
		ret.add(prop(m, NS.CRM.PROP_P79F_BEGINNING_IS_QUALIFIED_BY));
		ret.add(prop(m, NS.CRM.PROP_P80F_END_IS_QUALIFIED_BY));
		ret.add(prop(m, NS.EDM.PROP_BEGIN));
		ret.add(prop(m, NS.EDM.PROP_END));
		ret.add(prop(m, NS.DC.PROP_SUBJECT));
		ret.add(prop(m, NS.EDM.PROP_HAS_MET));
		ret.add(prop(m, NS.DC.PROP_DATE));
		return ret;
	}

	@Override public Set<Property> build_ore_Aggregation_Mandatory_Properties(Model m) {
		Set<Property> req = new HashSet<>();
		req.add(prop(m, NS.EDM.PROP_AGGREGATED_CHO));
		req.add(prop(m, NS.EDM.PROP_PROVIDER));
		req.add(prop(m, NS.EDM.PROP_DATA_PROVIDER));
		req.add(prop(m, NS.EDM.PROP_RIGHTS));
		return req;
	}

	@Override public Set<Property> build_ore_Aggregation_Recommended_Properties(Model m) {
		Set<Property> ret = new HashSet<>();
		ret.add(prop(m, NS.EDM.PROP_OBJECT));
		ret.add(prop(m, NS.DM2E.PROP_HAS_ANNOTABLE_VERSION_AT));
		return ret;
	}

	@Override public Set<Property> build_ore_Aggregation_AnnotatableWebResource_Properties(Model m) {
		Set<Property> req = new HashSet<>();
		req.add(prop(m, NS.DM2E.PROP_HAS_ANNOTABLE_VERSION_AT));
		return req;
	}

	@Override public Set<Property> build_ore_Aggregation_FunctionalProperties(Model m) {
		Set<Property> ret = new HashSet<>();
		ret.add(prop(m, NS.EDM.PROP_AGGREGATED_CHO));
		ret.add(prop(m, NS.EDM.PROP_PROVIDER));
		ret.add(prop(m, NS.EDM.PROP_DATA_PROVIDER));
		ret.add(prop(m, NS.EDM.PROP_RIGHTS));
		ret.add(prop(m, NS.EDM.PROP_IS_SHOWN_BY));
		ret.add(prop(m, NS.EDM.PROP_IS_SHOWN_AT));
		ret.add(prop(m, NS.EDM.PROP_OBJECT));
		ret.add(prop(m, NS.DM2E.PROP_HAS_ANNOTABLE_VERSION_AT));
		return ret;
	}

	@Override public Map<Property, Set<Resource>> build_ore_Aggregation_ObjectPropertyRanges(Model m) {
		Map<Property, Set<Resource>> ret = new HashMap<>();
		// TODO Auto-generated method stub
		return ret;
	}

	@Override public Map<Property, Set<Resource>> build_ore_Aggregation_LiteralPropertyRanges(Model m) {
		Map<Property, Set<Resource>> ret = new HashMap<>();
		// TODO Auto-generated method stub
		return ret;
	}

	@Override public Set<Property> build_edm_ProvidedCHO_Mandatory_Properties(Model m) {
		Set<Property> req = new HashSet<>();
		req.add(prop(m, NS.DM2E.PROP_DISPLAY_LEVEL));
		req.add(prop(m, NS.EDM.PROP_TYPE));
		req.add(prop(m, NS.DC.PROP_TYPE));
		req.add(prop(m, NS.DC.PROP_LANGUAGE));
		req.add(prop(m, NS.DC.PROP_SUBJECT));
		return req;
	}

	@Override public Set<Property> build_edm_ProvidedCHO_Recommended_Properties(Model m) {
		Set<Property> ret = new HashSet<>();
		return ret;
	}

	@Override public Set<Property> build_edm_ProvidedCHO_FunctionalProperties(Model m) {
		Set<Property> ret = new HashSet<>();
		ret.add(prop(m, NS.EDM.PROP_TYPE));
		ret.add(prop(m, NS.DM2E.PROP_INCIPIT));
		ret.add(prop(m, NS.DM2E.PROP_EXPLICIT));
		ret.add(prop(m, NS.DM2E.PROP_PUBLISHED_AT));
		ret.add(prop(m, NS.DM2E.PROP_PRINTED_AT));
		ret.add(prop(m, NS.BIBO.PROP_ISBN));
		ret.add(prop(m, NS.BIBO.PROP_ISSN));
		ret.add(prop(m, NS.DM2E.PROP_CALL_NUMBER));
		ret.add(prop(m, NS.EDM.PROP_CURRENT_LOCATION));
		ret.add(prop(m, NS.DM2E.PROP_SHELFMARK_LOCATION));
		ret.add(prop(m, NS.DC.PROP_SUBJECT));
		ret.add(prop(m, NS.DM2E.PROP_DISPLAY_LEVEL));
		ret.add(prop(m, NS.DM2E.PROP_LEVEL_OF_GENESIS));
		ret.add(prop(m, NS.BIBO.PROP_PAGES));
		ret.add(prop(m, NS.BIBO.PROP_NUMBER));
		ret.add(prop(m, NS.BIBO.PROP_NUM_VOLUMES));
		ret.add(prop(m, NS.BIBO.PROP_VOLUME));
		ret.add(prop(m, NS.DM2E.PROP_CONDITION));
		ret.add(prop(m, NS.DM2E.PROP_WATERMARK));
		ret.add(prop(m, NS.DM2E.PROP_LEVEL_OF_HIERARCHY));
		return ret;
	}

	@Override public Map<Property, Set<Resource>> build_edm_ProvidedCHO_LiteralPropertyRanges(Model m) {
		Map<Property, Set<Resource>> ret = new HashMap<>();

		ret.put(prop(m, NS.BIBO.PROP_NUMBER), new HashSet<Resource>());
		ret.get(prop(m, NS.BIBO.PROP_NUMBER)).add(res(m, NS.XSD.INTEGER));
		return ret;
	}

	@Override public Map<Property, Set<Resource>> build_edm_ProvidedCHO_ObjectPropertyRanges(Model m) {
		Map<Property, Set<Resource>> ret = new HashMap<>();
		ret.put(prop(m, NS.DC.PROP_SUBJECT), new HashSet<Resource>());
		ret.get(prop(m, NS.DC.PROP_SUBJECT)).add(res(m, NS.SKOS.CLASS_CONCEPT));
		ret.get(prop(m, NS.DC.PROP_SUBJECT)).add(res(m, NS.DM2E.CLASS_WORK));
		ret.get(prop(m, NS.DC.PROP_SUBJECT)).add(res(m, NS.FABIO.CLASS_ARTICLE));
		ret.get(prop(m, NS.DC.PROP_SUBJECT)).add(res(m, NS.FABIO.CLASS_CHAPTER));
		ret.get(prop(m, NS.DC.PROP_SUBJECT)).add(res(m, NS.DM2E.CLASS_PARAGRAPH));
		ret.get(prop(m, NS.DC.PROP_SUBJECT)).add(res(m, NS.DM2E.CLASS_PUBLICATION));
		ret.get(prop(m, NS.DC.PROP_SUBJECT)).add(res(m, NS.BIBO.CLASS_SERIES));
		ret.get(prop(m, NS.DC.PROP_SUBJECT)).add(res(m, NS.EDM.CLASS_AGENT));
		ret.get(prop(m, NS.DC.PROP_SUBJECT)).add(res(m, NS.FOAF.CLASS_PERSON));
		ret.get(prop(m, NS.DC.PROP_SUBJECT)).add(res(m, NS.FOAF.CLASS_ORGANIZATION));
		ret.get(prop(m, NS.DC.PROP_SUBJECT)).add(res(m, NS.DM2E.CLASS_ARCHIVE));
		ret.get(prop(m, NS.DC.PROP_SUBJECT)).add(res(m, NS.VIVO.CLASS_LIBRARY));
		ret.get(prop(m, NS.DC.PROP_SUBJECT)).add(res(m, NS.VIVO.CLASS_MUSEUM));
		ret.get(prop(m, NS.DC.PROP_SUBJECT)).add(res(m, NS.VIVO.CLASS_UNIVERSITY));
		ret.get(prop(m, NS.DC.PROP_SUBJECT)).add(res(m, NS.EDM.CLASS_TIMESPAN));

		ret.put(prop(m, NS.EDM.PROP_HAS_MET), new HashSet<Resource>());
		ret.get(prop(m, NS.EDM.PROP_HAS_MET)).add(res(m, NS.SKOS.CLASS_CONCEPT));
		ret.get(prop(m, NS.EDM.PROP_HAS_MET)).add(res(m, NS.EDM.CLASS_EVENT));
		ret.get(prop(m, NS.EDM.PROP_HAS_MET)).add(res(m, NS.EDM.CLASS_PLACE));
		ret.get(prop(m, NS.EDM.PROP_HAS_MET)).add(res(m, NS.EDM.CLASS_AGENT));
		ret.get(prop(m, NS.EDM.PROP_HAS_MET)).add(res(m, NS.EDM.CLASS_TIMESPAN));
		ret.get(prop(m, NS.EDM.PROP_HAS_MET)).add(res(m, NS.FOAF.CLASS_PERSON));
		ret.get(prop(m, NS.EDM.PROP_HAS_MET)).add(res(m, NS.FOAF.CLASS_ORGANIZATION));

		return ret;
	}

	@Override public Set<Property> build_edm_TimeSpan_Mandatory_Properties(Model m) {
		return new HashSet<>();
	}

	@Override public Set<Property> build_edm_WebResource_Recommended_Properties(Model m) {
		Set<Property> ret = new HashSet<>();
		ret.add(prop(m, NS.EDM.PROP_RIGHTS));
		return ret;
	}

	//
	// Validation Overrides
	//

	/**
	 * All the checks that depend on DM2E's versioned namespace must be duplicated across versions argh :(
	 * @see eu.dm2e.validation.validator.BaseValidator#validate_edm_ProvidedCHO(com.hp.hpl.jena.rdf.model.Model, com.hp.hpl.jena.rdf.model.Resource, java.lang.Object, eu.dm2e.validation.Dm2eValidationReport)
	 */
	@Override
	public void validate_edm_ProvidedCHO(Model m, Resource cho, Dm2eValidationReport report) {

		super.validate_edm_ProvidedCHO(m, cho, report);

		//
		// NS.BIBO.PROP_NUMBER:
		// "mandatory not repeatable for CHOs of dc:type type dm2e:Page" (p.32)
		//
		if (cho.hasProperty(prop(m, NS.DC.PROP_TYPE), res(m, NS.DM2E.CLASS_PAGE))
				&&
			! cho.hasProperty(prop(m, NS.BIBO.PROP_NUMBER))) {	
			report.add(ValidationLevel.ERROR, ValidationProblemCategory.MISSING_CONDITIONALLY_REQUIRED_PROPERTY, cho, NS.BIBO.PROP_NUMBER, "One bibo:Number for every dm2e:Page");
		}
	}
	
	@Override
	public void validate_ore_Aggregation(Model m, Resource agg, Dm2eValidationReport report) {
		super.validate_ore_Aggregation(m, agg, report);
		
		Statement displayLevelStmt = agg.getProperty(prop(m, NS.DM2E.PROP_DISPLAY_LEVEL));
		if (null != displayLevelStmt && displayLevelStmt.getObject().isLiteral()) {
			String trueFalse = displayLevelStmt.getObject().asLiteral().getLexicalForm();
			if (! trueFalse.equals("true") && ! trueFalse.equals("false")) {
				report.add(ValidationLevel.ERROR, ValidationProblemCategory.INVALID_LITERAL, agg, NS.DM2E.PROP_DISPLAY_LEVEL, trueFalse, "dm2e:displayLevel must be 'true' or 'false'");
			}
		}
	}

	@Override
	public Set<Resource> build_edm_ProvidedcHO_AllowedDcTypes(Model m) {
		Set<Resource> ret = new HashSet<>();
		// edm:PhysicalThing
		ret.add(res(m, NS.BIBO.CLASS_BOOK));
		ret.add(res(m, NS.FABIO.CLASS_COVER));
		ret.add(res(m, NS.DM2E.CLASS_DOCUMENT));
//		ret.add(res(m, NS.BIBO.CLASS_ISSUE));
		ret.add(res(m, NS.DM2E.CLASS_FILE));
		ret.add(res(m, NS.BIBO.CLASS_JOURNAL));
		ret.add(res(m, NS.BIBO.CLASS_LETTER));
		ret.add(res(m, NS.DM2E.CLASS_MANUSCRIPT));
		ret.add(res(m, NS.DM2E.CLASS_PAGE));
		ret.add(res(m, NS.DM2E.CLASS_PHOTO));
		// skos:Concept
		ret.add(res(m, NS.DM2E.CLASS_WORK));
		ret.add(res(m, NS.FABIO.CLASS_ARTICLE));
		ret.add(res(m, NS.FABIO.CLASS_CHAPTER));
		ret.add(res(m, NS.DM2E.CLASS_PARAGRAPH));
		ret.add(res(m, NS.DM2E.CLASS_PUBLICATION));
		ret.add(res(m, NS.BIBO.CLASS_SERIES));
		return ret;
	}

}
