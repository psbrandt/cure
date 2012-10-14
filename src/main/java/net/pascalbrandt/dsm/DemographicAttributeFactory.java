package net.pascalbrandt.dsm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import weka.core.Attribute;

public class DemographicAttributeFactory {

	// Numeric Attributes
	public static String[] DEMOGRAPHIC_NUMERIC_ATTRIBUTES = {"Age", "Weight"};
	
	// Categortical Attributes
	public static Map<String, String[]> DEMOGRAPHIC_CATEGORITCAL_ATTRIBUTES = new HashMap<String, String[]>();
	
	static {
		DEMOGRAPHIC_CATEGORITCAL_ATTRIBUTES.put("Gender", new String[] {"Male", "Female"});
		DEMOGRAPHIC_CATEGORITCAL_ATTRIBUTES.put("Ethnicity", new String[] {"African", "Asian", "Caucasian"});
		DEMOGRAPHIC_CATEGORITCAL_ATTRIBUTES.put("Province", new String[] {"Western Cape", "Gauteng", "North West", "Limpopo", "KwaZulu-Natal", "Northern Cape", "Free State", "Eastern Cape", "Mapumalanga"});
		DEMOGRAPHIC_CATEGORITCAL_ATTRIBUTES.put("Geographic Origin", new String[] {"Kuwait",
			"Saint Kitts and Nevis",
			"Swaziland",
			"Belize",
			"Morocco",
			"Namibia",
			"Romania",
			"Botswana",
			"Guinea",
			"Vatican City",
			"Lesotho",
			"New Zealand",
			"Costa Rica",
			"Belgium",
			"Lebanon",
			"Germany",
			"Mongolia",
			"Vietnam",
			"Montenegro",
			"Gambia, The",
			"Papua New Guinea",
			"Slovenia",
			"Iraq",
			"Ethiopia",
			"Maldives",
			"Monaco",
			"Peru",
			"Sierra Leone",
			"Bahrain",
			"Reunion",
			"Comoros",
			"Panama",
			"Zambia",
			"Nauru",
			"San Marino",
			"United Arab Emirates",
			"Sudan",
			"United States",
			"Azerbaijan",
			"Guatemala",
			"Senegal",
			"Dominica",
			"Cuba",
			"Congo, Republic of the (Congo - Brazzaville)",
			"Jordan",
			"Chad",
			"Malaysia",
			"Algeria",
			"Sweden",
			"El Salvador",
			"Austria",
			"Israel",
			"Greenland",
			"Syria",
			"Niger",
			"Burundi",
			"Korea, Republic of (South Korea)",
			"Nigeria",
			"Equatorial Guinea",
			"Faroe Islands",
			"French Guiana",
			"Mauritania",
			"Kazakhstan",
			"Netherlands",
			"Netherlands Antilles",
			"Marshall Islands",
			"Antigua and Barbuda",
			"Moldova",
			"Mali",
			"Pakistan",
			"Macau",
			"Benin",
			"Korea, Democratic People's Republic of (North Korea)",
			"Cape Verde",
			"Congo, Democratic Republic of the (Congo - Kinshasa)",
			"Bulgaria",
			"Russia",
			"Svalbard",
			"Uruguay",
			"Ireland",
			"Macedonia",
			"Jamaica",
			"Colombia",
			"Burkina Faso",
			"Trinidad and Tobago",
			"Gabon",
			"Canada",
			"Tuvalu",
			"Kenya",
			"Martinique",
			"Central African Republic",
			"Haiti",
			"Barbados",
			"Indonesia",
			"Nepal",
			"Nicaragua",
			"Yemen",
			"Tristan da Cunha",
			"Cote d'Ivoire (Ivory Coast)",
			"Portugal",
			"South Africa",
			"Serbia",
			"Bosnia and Herzegovina",
			"United Kingdom",
			"France",
			"Chile",
			"Slovakia",
			"Togo",
			"Tunisia",
			"Samoa",
			"Paraguay",
			"Zimbabwe",
			"Oman",
			"Honduras",
			"Denmark",
			"Guadeloupe",
			"Venezuela",
			"Malta",
			"Luxembourg",
			"Saudi Arabia",
			"Finland",
			"Turkey",
			"Greece",
			"Fiji",
			"Hungary",
			"Madagascar",
			"Tajikistan",
			"Dominican Republic",
			"Myanmar (Burma)",
			"Rwanda",
			"Guyana",
			"Belarus",
			"Seychelles",
			"Qatar",
			"Norway",
			"Djibouti",
			"Liechtenstein",
			"Mozambique",
			"Sri Lanka",
			"Turkmenistan",
			"Australia",
			"Timor-Leste (East Timor)",
			"Ukraine",
			"Somalia",
			"Czech Republic",
			"Angola",
			"Libya",
			"Mexico",
			"Micronesia",
			"Ghana",
			"Laos",
			"Tanzania",
			"Bolivia",
			"Georgia",
			"Lithuania",
			"Afghanistan",
			"Iceland",
			"Uganda",
			"Latvia",
			"Argentina",
			"Grenada",
			"India",
			"Andorra",
			"Tonga",
			"Vanuatu",
			"Uzbekistan",
			"Bhutan",
			"Spain",
			"Saint Lucia",
			"Kiribati",
			"Italy",
			"Albania",
			"Aruba",
			"Malawi",
			"Cambodia",
			"Kosovo",
			"Singapore",
			"Bahamas, The",
			"Saint Vincent and the Grenadines",
			"Estonia",
			"Aland",
			"Iran",
			"Mauritius",
			"China, People's Republic of",
			"Philippines",
			"Hong Kong",
			"Cyprus",
			"Cameroon",
			"Armenia",
			"Suriname",
			"Brunei",
			"Japan",
			"Sao Tome and Principe",
			"Egypt",
			"Poland",
			"Solomon Islands",
			"Bangladesh",
			"Liberia",
			"Eritrea",
			"Palau",
			"Switzerland",
			"Kyrgyzstan",
			"Thailand",
			"Croatia",
			"Brazil",
			"Ascension",
			"Guinea-Bissau",
			"Ecuador"});
	}
	
	public static List<Attribute> getDemographicAttributes() {
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		
		// Numeric Attributes
		for(String name : DEMOGRAPHIC_NUMERIC_ATTRIBUTES) {
			attributes.add(new Attribute(name));
		}
		
		// Categorical Attributes
		for(String name : DEMOGRAPHIC_CATEGORITCAL_ATTRIBUTES.keySet()) {
			attributes.add(new Attribute(name, Arrays.asList(DEMOGRAPHIC_CATEGORITCAL_ATTRIBUTES.get(name))));
		}
		
		return attributes;
	}
	
	public static List<String> getDemographicAttributeNames() {
		ArrayList<String> attributes = new ArrayList<String>();
		
		// Numeric Attributes
		for(String name : DEMOGRAPHIC_NUMERIC_ATTRIBUTES) {
			attributes.add(name);
		}
		
		// Categorical Attributes
		for(String name : DEMOGRAPHIC_CATEGORITCAL_ATTRIBUTES.keySet()) {
			attributes.add(name);
		}
		
		return attributes;	
	}

	public static List<Attribute> createAttributes(String[] selectedDemographicAttributes) {
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
			
		
		return null;
	}
			
}
