package org.mitre.cpe.matching;

import java.text.ParseException;
import java.util.Hashtable;
import org.mitre.cpe.naming.CPENameUnbinder;

import org.mitre.cpe.common.*;

/**
 * The CPENameMatcher is an implementation of the CPE Matching algorithm, 
 * as specified in the CPE Matching Standard version 2.3.  
 * 
 * @see <a href="http://cpe.mitre.org">cpe.mitre.org</a> for more information. 
 * @author Joshua Kraunelis
 * @email jkraunelis@mitre.org
 */
public class CPENameMatcher {

    /**
     * Tests two Well Formed Names for disjointness.  
     * @param source Source WFN
     * @param target Target WFN
     * @return true if the names are disjoint, false otherwise
     */
    public boolean isDisjoint(WellFormedName source, WellFormedName target) {
        // if any pairwise comparison is disjoint, the names are disjoint.
        Hashtable result_list = compareWFNs(source, target);
        for (Object result : result_list.values()) {
            if (result.equals(Relation.DISJOINT)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tests two Well Formed Names for equality. 
     * @param source Source WFN
     * @param target Target WFN
     * @return true if the names are equal, false otherwise
     */
    public boolean isEqual(WellFormedName source, WellFormedName target) {
        // if every pairwise comparison is equal, the names are equal.
        Hashtable result_list = compareWFNs(source, target);
        for (Object result : result_list.values()) {
            if (!(result.equals(Relation.EQUAL))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if the target Well Formed Name is a subset of the source Well Formed
     * Name.  
     * @param source Source WFN
     * @param target Target WFN
     * @return true if the target is a subset of the source, false otherwise
     */
    public boolean isSubset(WellFormedName source, WellFormedName target) {
        // if any comparison is anything other than subset or equal, then target is
        // not a subset of source.
        Hashtable result_list = compareWFNs(source, target);
        for (Object result : result_list.values()) {
            if (!(result.equals(Relation.SUBSET)) && !(result.equals(Relation.EQUAL))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests if the target Well Formed name is a superset of the source Well Formed
     * Name.
     * @param source Source WFN
     * @param target Target WFN
     * @return true if the target is a superset of the source, false otherwise
     */
    public boolean isSuperset(WellFormedName source, WellFormedName target) {
        // if any comparison is anything other than superset or equal, then target is not
        // a superset of source.
        Hashtable result_list = compareWFNs(source, target);
        for (Object result : result_list.values()) {
            if ((!result.equals(Relation.SUPERSET)) && (!result.equals(Relation.EQUAL))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compares each attribute value pair in two Well Formed Names.
     * @param source Source WFN
     * @param target Target WFN
     * @return A Hashtable mapping attribute string to attribute value Relation
     */
    public Hashtable compareWFNs(WellFormedName source, WellFormedName target) {
        Hashtable result = new Hashtable();
        result.put("part", compare(source.get("part"), target.get("part")));
        result.put("vendor", compare(source.get("vendor"), target.get("vendor")));
        result.put("product", compare(source.get("product"), target.get("product")));
        result.put("version", compare(source.get("version"), target.get("version")));
        result.put("update", compare(source.get("update"), target.get("update")));
        result.put("edition", compare(source.get("edition"), target.get("edition")));
        result.put("language", compare(source.get("language"), target.get("language")));
        result.put("sw_edition", compare(source.get("sw_edition"), target.get("sw_edition")));
        result.put("target_sw", compare(source.get("target_sw"), target.get("target_sw")));
        result.put("target_hw", compare(source.get("target_hw"), target.get("target_hw")));
        result.put("other", compare(source.get("other"), target.get("other")));
        return result;
    }

    /**
     * Compares an attribute value pair.
     * @param source Source attribute value.
     * @param target Target attribute value.
     * @return The relation between the two attribute values.
     */
    private Object compare(Object source, Object target) {
        // matching is case insensitive, convert strings to lowercase.
        if (isString(source)) {
            source = Utilities.toLowercase((String) source);
        }
        if (isString(target)) {
            target = Utilities.toLowercase((String) target);
        }

        // Unquoted wildcard characters yield an undefined result.
        if (isString(target) && Utilities.containsWildcards((String) target)) {
            return Relation.UNDEFINED;
        }
        // If source and target values are equal, then result is equal.
        if (source.equals(target)) {
            return Relation.EQUAL;
        }

        // Check to see if source or target are Logical Values.
        LogicalValue lvSource = null;
        LogicalValue lvTarget = null;
        if (source instanceof LogicalValue) {
            lvSource = (LogicalValue) source;
        }
        if (target instanceof LogicalValue) {
            lvTarget = (LogicalValue) target;
        }
        if (lvSource != null && lvTarget != null) {
            // If Logical Values are equal, result is equal.
            if (lvSource.isANY() == lvTarget.isANY() || lvSource.isNA() == lvTarget.isNA()) {
                return Relation.EQUAL;
            }
        }
        // If source value is ANY, result is a superset.
        if (lvSource != null) {
            if (lvSource.isANY()) {
                return Relation.SUPERSET;
            }
        }
        // If target value is ANY, result is a subset.
        if (lvTarget != null) {
            if (lvTarget.isANY()) {
                return Relation.SUBSET;
            }
        }
        // If source or target is NA, result is disjoint.
        if (lvSource != null) {
            if (lvSource.isNA()) {
                return Relation.DISJOINT;
            }
        }
        if (lvTarget != null) {
            if (lvTarget.isNA()) {
                return Relation.DISJOINT;
            }
        }
        // only Strings will get to this point, not LogicalValues
        return compareStrings((String) source, (String) target);
    }

    /**
     * Compares a source string to a target string, and addresses the condition 
     * in which the source string includes unquoted special characters. It 
     * performs a simple regular expression  match, with the assumption that 
     * (as required) unquoted special characters appear only at the beginning 
     * and/or the end of the source string. It also properly differentiates 
     * between unquoted and quoted special characters.
     * 
     * @return Relation between source and target Strings.
     */
    private Object compareStrings(String source, String target) {
        int start = 0;
        int end = Utilities.strlen(source);
        int begins = 0;
        int ends = 0;
        int index, leftover, escapes;

        if (Utilities.substr(source, 0, 1).equals("*")) {
            start = 1;
            begins = -1;
        } else {
            while ((start < Utilities.strlen(source)) && (Utilities.substr(source, start, start + 1).equals("?"))) {
                start = start + 1;
                begins = begins + 1;
            }
        }
        if ((Utilities.substr(source, end - 1, end).equals("*")) && (isEvenWildcards(source, end - 1))) {
            end = end - 1;
            ends = -1;
        } else {
            while ((end > 0) && Utilities.substr(source, end - 1, end).equals("?") && (isEvenWildcards(source, end - 1))) {
                end = end - 1;
                ends = ends + 1;
            }
        }

        source = Utilities.substr(source, start, end);
        index = -1;
        leftover = Utilities.strlen(target);
        while (leftover > 0) {
            index = Utilities.indexOf(target, source, index + 1);
            if (index == -1) {
                break;
            }
            escapes = Utilities.countEscapeCharacters(target, 0, index);
            if ((index > 0) && (begins != -1) && (begins < (index - escapes))) {
                break;
            }
            escapes = Utilities.countEscapeCharacters(target, index + 1, Utilities.strlen(target));
            leftover = Utilities.strlen(target) - index - escapes - Utilities.strlen(source);
            if ((leftover > 0) && ((ends != -1) && (leftover > ends))) {
                continue;
            }
            return Relation.SUPERSET;
        }
        return Relation.DISJOINT;
    }

    /**
     * Searches a string for the backslash character
     * @param str string to search in
     * @param idx end index
     * @return true if the number of backslash characters is even, false if odd
     */
    private boolean isEvenWildcards(String str, int idx) {
        int result = 0;
        while ((idx > 0) && (Utilities.strchr(str, "\\", idx - 1)) != -1) {
            idx = idx - 1;
            result = result + 1;
        }
        return Utilities.isEvenNumber(result);
    }

    /**
     * Tests if an Object is an instance of the String class
     * @param arg the Object to test
     * @return true if arg is a String, false if not
     */
    private boolean isString(Object arg) {
        if (arg instanceof String) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws ParseException {
        // Examples.
        WellFormedName wfn = new WellFormedName("a", "microsoft", "internet_explorer", "8\\.0\\.6001", "beta", new LogicalValue("ANY"), "sp2", null, null, null, null);
        WellFormedName wfn2 = new WellFormedName("a", "microsoft", "internet_explorer", new LogicalValue("ANY"), new LogicalValue("ANY"), new LogicalValue("ANY"), new LogicalValue("ANY"), new LogicalValue("ANY"), new LogicalValue("ANY"), new LogicalValue("ANY"), new LogicalValue("ANY"));
        CPENameMatcher cpenm = new CPENameMatcher();
        System.out.println(cpenm.isDisjoint(wfn, wfn2)); // false
        System.out.println(cpenm.isEqual(wfn, wfn2)); // false
        System.out.println(cpenm.isSubset(wfn, wfn2)); // true, wfn2 is a subset of wfn
        System.out.println(cpenm.isSuperset(wfn, wfn2)); // false
        CPENameUnbinder cpenu = new CPENameUnbinder();
        wfn = cpenu.unbindFS("cpe:2.3:a:adobe:*:9.*:*:PalmOS:*:*:*:*:*");
        wfn2 = cpenu.unbindURI("cpe:/a::Reader:9.3.2:-:-");
        System.out.println(cpenm.isDisjoint(wfn, wfn2)); // true, wfn2 and wfn are disjoint
        System.out.println(cpenm.isEqual(wfn, wfn2)); // false 
        System.out.println(cpenm.isSubset(wfn, wfn2)); // false
        System.out.println(cpenm.isSuperset(wfn, wfn2)); // false
    }
}
