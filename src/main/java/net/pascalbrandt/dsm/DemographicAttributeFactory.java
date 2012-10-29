package net.pascalbrandt.dsm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.regadb.db.Patient;
import weka.core.Attribute;
import weka.core.Instance;

public class DemographicAttributeFactory {
    private static final Logger logger = LoggerFactory.getLogger(DemographicAttributeFactory.class);

    // Pretty Numeric Attribute Names
    public static final String DEMOGRAPHIC_PRETTY_ATTRIBUTE_AGE = "Age";

    // Pretty Categorical Attribute Names
    public static final String DEMOGRAPHIC_PRETTY_ATTRIBUTE_GENDER = "Gender";
    public static final String DEMOGRAPHIC_PRETTY_ATTRIBUTE_ETHNICITY = "Ethnicity";
    public static final String DEMOGRAPHIC_PRETTY_ATTRIBUTE_PROVINCE = "Province";
    public static final String DEMOGRAPHIC_PRETTY_ATTRIBUTE_GEOGRAPHIC_ORIGIN = "Geographic Origin";
    public static final String DEMOGRAPHIC_PRETTY_ATTRIBUTE_COUNTRY_OF_ORIGIN = "Country of Origin";

    // Rega Attribute Names
    public static final String DEMOGRAPHIC_REGA_ATTRIBUTE_AGE = "Age";
    public static final String DEMOGRAPHIC_REGA_ATTRIBUTE_COUNTRY_OF_ORIGIN = "Country of origin";
    public static final String DEMOGRAPHIC_REGA_ATTRIBUTE_GEOGRAPHIC_ORIGIN = "Geographic origin";
    public static final String DEMOGRAPHIC_REGA_ATTRIBUTE_ETHNICITY = "Ethnicity";
    public static final String DEMOGRAPHIC_REGA_ATTRIBUTE_GENDER = "Gender";
    public static final String DEMOGRAPHIC_REGA_ATTRIBUTE_PROVINCE = "ZAProvinces";

    // Numeric Attributes
    public static final Set<String> DEMOGRAPHIC_NUMERIC_ATTRIBUTES = new HashSet<String>(
            Arrays.asList(new String[] { DEMOGRAPHIC_PRETTY_ATTRIBUTE_AGE }));

    // Categorical Attributes
    public static Map<String, String[]> DEMOGRAPHIC_CATEGORITCAL_ATTRIBUTES = new HashMap<String, String[]>();

    // Pretty to Rega name map
    public static Map<String, String> DEMOGRAPHIC_ATTRIBUTES_REGA_MAP = new HashMap<String, String>();

    static {
        DEMOGRAPHIC_CATEGORITCAL_ATTRIBUTES.put(DEMOGRAPHIC_PRETTY_ATTRIBUTE_GENDER, new String[] {
                "male", "female" });
        DEMOGRAPHIC_CATEGORITCAL_ATTRIBUTES.put(DEMOGRAPHIC_PRETTY_ATTRIBUTE_ETHNICITY,
                new String[] { "african", "asian", "caucasian" });
        DEMOGRAPHIC_CATEGORITCAL_ATTRIBUTES.put(DEMOGRAPHIC_PRETTY_ATTRIBUTE_PROVINCE,
                new String[] { "Western Cape", "Gauteng", "North West", "Limpopo", "KwaZulu-Natal",
                        "Northern Cape", "Free State", "Eastern Cape", "Mapumalanga" });
        DEMOGRAPHIC_CATEGORITCAL_ATTRIBUTES.put(DEMOGRAPHIC_PRETTY_ATTRIBUTE_GEOGRAPHIC_ORIGIN,
                new String[] {

                "Caribbean", "Middle East", "Africa", "Polynesia", "Subsaharan Africa",
                        "Eastern Europe", "North Africa", "South America", "Asia",
                        "Central Africa", "North America", "Europe" });

        DEMOGRAPHIC_CATEGORITCAL_ATTRIBUTES.put(DEMOGRAPHIC_PRETTY_ATTRIBUTE_COUNTRY_OF_ORIGIN,
        // Used list
                new String[] { "Botswana", "India", "Namibia", "South Africa", "Tanzania",
                        "Zimbabwe" });

        /**
         * Full list new String[] { "Kuwait", "Saint Kitts and Nevis",
         * "Swaziland", "Belize", "Morocco", "Namibia", "Romania", "Botswana",
         * "Guinea", "Vatican City", "Lesotho", "New Zealand", "Costa Rica",
         * "Belgium", "Lebanon", "Germany", "Mongolia", "Vietnam", "Montenegro",
         * "Gambia, The", "Papua New Guinea", "Slovenia", "Iraq", "Ethiopia",
         * "Maldives", "Monaco", "Peru", "Sierra Leone", "Bahrain", "Reunion",
         * "Comoros", "Panama", "Zambia", "Nauru", "San Marino",
         * "United Arab Emirates", "Sudan", "United States", "Azerbaijan",
         * "Guatemala", "Senegal", "Dominica", "Cuba",
         * "Congo, Republic of the (Congo - Brazzaville)", "Jordan", "Chad",
         * "Malaysia", "Algeria", "Sweden", "El Salvador", "Austria", "Israel",
         * "Greenland", "Syria", "Niger", "Burundi",
         * "Korea, Republic of (South Korea)", "Nigeria", "Equatorial Guinea",
         * "Faroe Islands", "French Guiana", "Mauritania", "Kazakhstan",
         * "Netherlands", "Netherlands Antilles", "Marshall Islands",
         * "Antigua and Barbuda", "Moldova", "Mali", "Pakistan", "Macau",
         * "Benin", "Korea, Democratic People's Republic of (North Korea)",
         * "Cape Verde", "Congo, Democratic Republic of the (Congo - Kinshasa)",
         * "Bulgaria", "Russia", "Svalbard", "Uruguay", "Ireland", "Macedonia",
         * "Jamaica", "Colombia", "Burkina Faso", "Trinidad and Tobago",
         * "Gabon", "Canada", "Tuvalu", "Kenya", "Martinique",
         * "Central African Republic", "Haiti", "Barbados", "Indonesia",
         * "Nepal", "Nicaragua", "Yemen", "Tristan da Cunha",
         * "Cote d'Ivoire (Ivory Coast)", "Portugal", "South Africa", "Serbia",
         * "Bosnia and Herzegovina", "United Kingdom", "France", "Chile",
         * "Slovakia", "Togo", "Tunisia", "Samoa", "Paraguay", "Zimbabwe",
         * "Oman", "Honduras", "Denmark", "Guadeloupe", "Venezuela", "Malta",
         * "Luxembourg", "Saudi Arabia", "Finland", "Turkey", "Greece", "Fiji",
         * "Hungary", "Madagascar", "Tajikistan", "Dominican Republic",
         * "Myanmar (Burma)", "Rwanda", "Guyana", "Belarus", "Seychelles",
         * "Qatar", "Norway", "Djibouti", "Liechtenstein", "Mozambique",
         * "Sri Lanka", "Turkmenistan", "Australia", "Timor-Leste (East Timor)",
         * "Ukraine", "Somalia", "Czech Republic", "Angola", "Libya", "Mexico",
         * "Micronesia", "Ghana", "Laos", "Tanzania", "Bolivia", "Georgia",
         * "Lithuania", "Afghanistan", "Iceland", "Uganda", "Latvia",
         * "Argentina", "Grenada", "India", "Andorra", "Tonga", "Vanuatu",
         * "Uzbekistan", "Bhutan", "Spain", "Saint Lucia", "Kiribati", "Italy",
         * "Albania", "Aruba", "Malawi", "Cambodia", "Kosovo", "Singapore",
         * "Bahamas, The", "Saint Vincent and the Grenadines", "Estonia",
         * "Aland", "Iran", "Mauritius", "China, People's Republic of",
         * "Philippines", "Hong Kong", "Cyprus", "Cameroon", "Armenia",
         * "Suriname", "Brunei", "Japan", "Sao Tome and Principe", "Egypt",
         * "Poland", "Solomon Islands", "Bangladesh", "Liberia", "Eritrea",
         * "Palau", "Switzerland", "Kyrgyzstan", "Thailand", "Croatia",
         * "Brazil", "Ascension", "Guinea-Bissau", "Ecuador" });
         */

        DEMOGRAPHIC_ATTRIBUTES_REGA_MAP.put(DEMOGRAPHIC_PRETTY_ATTRIBUTE_AGE,
                DEMOGRAPHIC_REGA_ATTRIBUTE_AGE);
        DEMOGRAPHIC_ATTRIBUTES_REGA_MAP.put(DEMOGRAPHIC_PRETTY_ATTRIBUTE_GENDER,
                DEMOGRAPHIC_REGA_ATTRIBUTE_GENDER);
        DEMOGRAPHIC_ATTRIBUTES_REGA_MAP.put(DEMOGRAPHIC_PRETTY_ATTRIBUTE_COUNTRY_OF_ORIGIN,
                DEMOGRAPHIC_REGA_ATTRIBUTE_COUNTRY_OF_ORIGIN);
        DEMOGRAPHIC_ATTRIBUTES_REGA_MAP.put(DEMOGRAPHIC_PRETTY_ATTRIBUTE_ETHNICITY,
                DEMOGRAPHIC_REGA_ATTRIBUTE_ETHNICITY);
        DEMOGRAPHIC_ATTRIBUTES_REGA_MAP.put(DEMOGRAPHIC_PRETTY_ATTRIBUTE_GEOGRAPHIC_ORIGIN,
                DEMOGRAPHIC_REGA_ATTRIBUTE_GEOGRAPHIC_ORIGIN);
        DEMOGRAPHIC_ATTRIBUTES_REGA_MAP.put(DEMOGRAPHIC_PRETTY_ATTRIBUTE_PROVINCE,
                DEMOGRAPHIC_REGA_ATTRIBUTE_PROVINCE);
    }

    // Get a list of the demographic attributes
    public static List<Attribute> getDemographicAttributes() {
        ArrayList<Attribute> attributes = new ArrayList<Attribute>();

        // Numeric Attributes
        for (String name : DEMOGRAPHIC_NUMERIC_ATTRIBUTES) {
            attributes.add(new Attribute(name));
        }

        // Categorical Attributes
        for (String name : DEMOGRAPHIC_CATEGORITCAL_ATTRIBUTES.keySet()) {
            attributes.add(new Attribute(name, Arrays.asList(DEMOGRAPHIC_CATEGORITCAL_ATTRIBUTES
                    .get(name))));
        }

        return attributes;
    }

    // Get a list of the demographic attribute names
    public static List<String> getDemographicAttributeNames() {
        ArrayList<String> attributes = new ArrayList<String>();

        // Numeric Attributes
        for (String name : DEMOGRAPHIC_NUMERIC_ATTRIBUTES) {
            attributes.add(name);
        }

        // Categorical Attributes
        for (String name : DEMOGRAPHIC_CATEGORITCAL_ATTRIBUTES.keySet()) {
            attributes.add(name);
        }

        return attributes;
    }

    // Create attributes for the given list of attribute names
    public static List<Attribute> createAttributes(String[] selectedDemographicAttributes) {
        ArrayList<Attribute> attributes = new ArrayList<Attribute>();

        for (String attributeName : selectedDemographicAttributes) {
            // Numeric Attributes
            if (DEMOGRAPHIC_NUMERIC_ATTRIBUTES.contains(attributeName)) {
                attributes.add(new Attribute(attributeName));
            }

            // Categorical Attributes
            if (DEMOGRAPHIC_CATEGORITCAL_ATTRIBUTES.containsKey(attributeName)) {
                attributes.add(new Attribute(attributeName, Arrays
                        .asList(DEMOGRAPHIC_CATEGORITCAL_ATTRIBUTES.get(attributeName))));
            }
        }

        return attributes;
    }

    // Add the value to the instance for the given attribute and patient
    public static Instance addAttributeValue(Attribute attribute, Instance instance,
            Patient patient, RegaService rs) {

        if (DEMOGRAPHIC_NUMERIC_ATTRIBUTES.contains(attribute.name())) {
            // Numeric Attributes

            // Simple Numeric Attributes
            if (DEMOGRAPHIC_ATTRIBUTES_REGA_MAP.containsKey(attribute.name())) {
                AttributeFactory.setSimpleNumericAttributeValue(attribute,
                        DEMOGRAPHIC_ATTRIBUTES_REGA_MAP.get(attribute.name()), instance, patient,
                        rs);
            } else {
                // Calculated Numeric Attributes

                // There are no calculated demographic numeric attributes
            }

            return instance;

        } else if (DEMOGRAPHIC_CATEGORITCAL_ATTRIBUTES.containsKey(attribute.name())) {
            // Categorical Attributes

            // Simple Categorical Attributes
            if (DEMOGRAPHIC_ATTRIBUTES_REGA_MAP.containsKey(attribute.name())) {
                AttributeFactory.setSimpleCategoricalAttributeValue(attribute,
                        DEMOGRAPHIC_ATTRIBUTES_REGA_MAP.get(attribute.name()), instance, patient,
                        rs);
            } else {
                // Calculated Categorical Attributes

                // There are no calculated demographic categorical attributes
            }

            return instance;
        }

        return null;
    }
}
