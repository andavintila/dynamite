package nl.vu.cs.dynamite.storage;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class StandardTerms {

	/***** Standard URIs IDs *****/
	public static final long RDF_TYPE = 0;
	public static final long RDF_PROPERTY = 1;
	public static final long RDF_NIL = 28;
	public static final long RDF_LIST = 27;
	public static final long RDF_FIRST = 26;
	public static final long RDF_REST = 25;
	public static final long RDFS_RANGE = 2;
	public static final long RDFS_DOMAIN = 3;
	public static final long RDFS_SUBPROPERTY = 4;
	public static final long RDFS_SUBCLASS = 5;
	public static final long RDFS_MEMBER = 19;
	public static final long RDFS_LITERAL = 20;
	public static final long RDFS_CONTAINER_MEMBERSHIP_PROPERTY = 21;
	public static final long RDFS_DATATYPE = 22;
	public static final long RDFS_CLASS = 23;
	public static final long RDFS_RESOURCE = 24;
	public static final long OWL_CLASS = 6;
	public static final long OWL_FUNCTIONAL_PROPERTY = 7;
	public static final long OWL_INVERSE_FUNCTIONAL_PROPERTY = 8;
	public static final long OWL_SYMMETRIC_PROPERTY = 9;
	public static final long OWL_TRANSITIVE_PROPERTY = 10;
	public static final long OWL_SAME_AS = 11;
	public static final long OWL_INVERSE_OF = 12;
	public static final long OWL_EQUIVALENT_CLASS = 13;
	public static final long OWL_EQUIVALENT_PROPERTY = 14;
	public static final long OWL_HAS_VALUE = 15;
	public static final long OWL_ON_PROPERTY = 16;
	public static final long OWL_SOME_VALUES_FROM = 17;
	public static final long OWL_ALL_VALUES_FROM = 18;
	public static final long OWL2_PROPERTY_CHAIN_AXIOM = 29;
	public static final long OWL2_HAS_KEY = 30;
	public static final long OWL2_INTERSECTION_OF = 31;
	public static final long OWL2_UNION_OF = 32;
	public static final long OWL2_ONE_OF = 33;
	public static final long OWL2_THING = 34;
	public static final long OWL2_1 = 35;
	public static final long OWL2_MAX_CARD = 36;
	public static final long OWL2_MAX_Q_CARD = 37;
	public static final long OWL2_ON_CLASS = 38;
	public static final long OWL2_NOTHING = 39;
	public static final long OWL2_DATATYPE_PROP = 40;
	public static final long OWL2_OBJECT_PROP = 41;

	public static final String S_RDF_NIL = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#nil>";
	public static final String S_RDF_LIST = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#List>";
	public static final String S_RDF_FIRST = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#first>";
	public static final String S_RDF_REST = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#rest>";
	public static final String S_RDF_TYPE = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>";
	public static final String S_RDF_PROPERTY = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#Property>";
	public static final String S_RDFS_RANGE = "<http://www.w3.org/2000/01/rdf-schema#range>";
	public static final String S_RDFS_DOMAIN = "<http://www.w3.org/2000/01/rdf-schema#domain>";
	public static final String S_RDFS_SUBPROPERTY = "<http://www.w3.org/2000/01/rdf-schema#subPropertyOf>";
	public static final String S_RDFS_SUBCLASS = "<http://www.w3.org/2000/01/rdf-schema#subClassOf>";
	public static final String S_RDFS_MEMBER = "<http://www.w3.org/2000/01/rdf-schema#member>";
	public static final String S_RDFS_LITERAL = "<http://www.w3.org/2000/01/rdf-schema#Literal>";
	public static final String S_RDFS_CONTAINER_MEMBERSHIP_PROPERTY = "<http://www.w3.org/2000/01/rdf-schema#ContainerMembershipProperty>";
	public static final String S_RDFS_DATATYPE = "<http://www.w3.org/2000/01/rdf-schema#Datatype>";
	public static final String S_RDFS_CLASS = "<http://www.w3.org/2000/01/rdf-schema#Class>";
	public static final String S_RDFS_RESOURCE = "<http://www.w3.org/2000/01/rdf-schema#Resource>";
	public static final String S_OWL_CLASS = "<http://www.w3.org/2002/07/owl#Class>";
	public static final String S_OWL_FUNCTIONAL_PROPERTY = "<http://www.w3.org/2002/07/owl#FunctionalProperty>";
	public static final String S_OWL_INVERSE_FUNCTIONAL_PROPERTY = "<http://www.w3.org/2002/07/owl#InverseFunctionalProperty>";
	public static final String S_OWL_SYMMETRIC_PROPERTY = "<http://www.w3.org/2002/07/owl#SymmetricProperty>";
	public static final String S_OWL_TRANSITIVE_PROPERTY = "<http://www.w3.org/2002/07/owl#TransitiveProperty>";
	public static final String S_OWL_SAME_AS = "<http://www.w3.org/2002/07/owl#sameAs>";
	public static final String S_OWL_INVERSE_OF = "<http://www.w3.org/2002/07/owl#inverseOf>";
	public static final String S_OWL_EQUIVALENT_CLASS = "<http://www.w3.org/2002/07/owl#equivalentClass>";
	public static final String S_OWL_EQUIVALENT_PROPERTY = "<http://www.w3.org/2002/07/owl#equivalentProperty>";
	public static final String S_OWL_HAS_VALUE = "<http://www.w3.org/2002/07/owl#hasValue>";
	public static final String S_OWL_ON_PROPERTY = "<http://www.w3.org/2002/07/owl#onProperty>";
	public static final String S_OWL_SOME_VALUES_FROM = "<http://www.w3.org/2002/07/owl#someValuesFrom>";
	public static final String S_OWL_ALL_VALUES_FROM = "<http://www.w3.org/2002/07/owl#allValuesFrom>";
	public static final String S_OWL2_PROPERTY_CHAIN_AXIOM = "<http://www.w3.org/2002/07/owl#propertyChainAxiom>";
	public static final String S_OWL2_HAS_KEY = "<http://www.w3.org/2002/07/owl#hasKey>";
	public static final String S_OWL2_INTERSECTION_OF = "<http://www.w3.org/2002/07/owl#intersectionOf>";
	public static final String S_OWL2_UNION_OF = "<http://www.w3.org/2002/07/owl#unionOf>";
	public static final String S_OWL2_ONE_OF = "<http://www.w3.org/2002/07/owl#oneOf>";
	public static final String S_OWL2_THING = "<http://www.w3.org/2002/07/owl#Thing>";
	public static final String S_OWL2_1 = "\"1\"^^http://www.w3.org/2001/XMLSchema#nonNegativeInteger";
	public static final String S_OWL2_MAX_CARD = "<http://www.w3.org/2002/07/owl#maxCardinality>";
	public static final String S_OWL2_MAX_Q_CARD = "<http://www.w3.org/2002/07/owl#maxQualifiedCardinality>";
	public static final String S_OWL2_ON_CLASS = "<http://www.w3.org/2002/07/owl#onClass>";
	public static final String S_OWL2_NOTHING = "<http://www.w3.org/2002/07/owl#Nothing>";
	public static final String S_OWL2_DATATYPE_PROP = "<http://www.w3.org/2002/07/owl#DatatypeProperty>";
	public static final String S_OWL2_OBJECT_PROP = "<http://www.w3.org/2002/07/owl#ObjectProperty>";

	private static final Map<String, Long> map1 = new HashMap<String, Long>();
	private static final Map<Long, String> map2 = new HashMap<Long, String>();

	static {
		Field[] fields = StandardTerms.class.getFields();
		for (Field field : fields) {
			if (field.getName().startsWith("S_")) {
				try {
					String sValue = (String) field.get(null);
					String n = field.getName().substring(2);
					Field numericCounterpart = StandardTerms.class.getField(n);
					long nValue = numericCounterpart.getLong(null);
					map1.put(sValue, nValue);
					map2.put(nValue, sValue);
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
					System.exit(1);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

			}
		}
	}

	public static Map<String, Long> getTextToNumber() {
		return map1;
	}

	public static Map<Long, String> getNumberToText() {
		return map2;
	}
}
