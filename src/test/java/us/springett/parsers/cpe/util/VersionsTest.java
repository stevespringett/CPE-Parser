package us.springett.parsers.cpe.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static us.springett.parsers.cpe.util.Versions.VersionPart.intPart;
import static us.springett.parsers.cpe.util.Versions.VersionPart.strPart;
import static us.springett.parsers.cpe.util.Versions.splitVersion;

public class VersionsTest {

    @Test
    public void testSplitVersions() {
        assertThat(splitVersion(null)).containsExactly();
        assertThat(splitVersion("")).containsExactly();
        assertThat(splitVersion("0.1")).containsExactly(strPart("0"), intPart("1"));
        assertThat(splitVersion("0.134alpha")).containsExactly(strPart("0"), intPart("134"), strPart("alpha"));
        assertThat(splitVersion("0.1+alpha")).containsExactly(strPart("0"), intPart("1"), strPart("+alpha"));
        assertThat(splitVersion("1.2.3")).containsExactly(intPart("1"), intPart("2"), intPart("3"));
        assertThat(splitVersion("1.2.3b")).containsExactly(intPart("1"), intPart("2"), intPart("3"), strPart("b"));
        assertThat(splitVersion("1.2.3-SNAPSHOT")).containsExactly(intPart("1"), intPart("2"), intPart("3"), strPart("SNAPSHOT"));
        assertThat(splitVersion("1.2.3:|")).containsExactly(intPart("1"), intPart("2"), intPart("3"));
        assertThat(splitVersion("1-2|3|")).containsExactly(intPart("1"), intPart("2"), intPart("3"));
        assertThat(splitVersion("5.1.32-bzr")).containsExactly(intPart("5"), intPart("1"), intPart("32"), strPart("bzr"));
        assertThat(splitVersion("5.1.9223372036854775807152")).containsExactly(intPart("5"), intPart("1"), intPart("9223372036854775807152"));

        // Test letter-to-digit transitions (rc10 should split into rc and 10)
        assertThat(splitVersion("rc10")).containsExactly(strPart("rc"), intPart("10"));
        assertThat(splitVersion("alpha2")).containsExactly(strPart("alpha"), intPart("2"));
        assertThat(splitVersion("rc01")).containsExactly(strPart("rc"), strPart("01"));
    }

    @Test
    public void testZeroOneA() {
        // Test consistency: letter suffix always splits, even with leading zeros
        assertThat(splitVersion("1.1a")).containsExactly(intPart("1"), intPart("1"), strPart("a"));
        assertThat(splitVersion("1.01a")).containsExactly(intPart("1"), strPart("01"), strPart("a"));
    }


    /**
     * Test splitVersion with additional leading zero cases
     */
    @Test
    public void testSplitVersionLeadingZeros() {
        // Leading zeros in first position
        assertThat(splitVersion("0.1")).containsExactly(strPart("0"), intPart("1"));

        // Leading zeros create string tokens (numeric part stays together, no leading zeros stripped)
        // "01" is a string (leading zero indicates not a number, like a date or build ID)
        // "02" is a string (leading zero indicates not a number)
        assertThat(splitVersion("01.02"))
                .as("Leading zeros indicate string tokens")
                .containsExactly(strPart("01"), strPart("02"));

        // Leading-zero numeric followed by letter: splits at digit->letter boundary for consistency
        // "01a" splits into "01" (string) + "a" (string) for consistency with "1a" -> [1, "a"]
        assertThat(splitVersion("1.01a"))
                .as("1.01a splits: 1 (numeric), then '01' (string), then 'a' (string)")
                .containsExactly(intPart("1"), strPart("01"), strPart("a"));

        // Pure leading-zero number with no suffix stays together
        assertThat(splitVersion("04121975"))
                .as("Date-like numbers with all digits stay together")
                .containsExactly(strPart("04121975"));
    }

    @Test
    public void testVersionPartComparisonWithStringSemantics() {
        assertThat(strPart("1")).usingDefaultComparator().isEqualByComparingTo(strPart("1"));
        assertThat(strPart("11")).usingDefaultComparator().isLessThan(strPart("9"));
        assertThat(strPart("9")).usingDefaultComparator().isGreaterThan(strPart("11"));
        assertThat(strPart("alpha")).usingDefaultComparator().isLessThan(strPart("beta"));
    }

    @Test
    public void testVersionPartComparisonWithIntegerSemantics() {
        assertThat(intPart("1")).usingDefaultComparator().isEqualByComparingTo(intPart("1"));
        assertThat(intPart("11")).usingDefaultComparator().isGreaterThan(intPart("9"));
        assertThat(intPart("9")).usingDefaultComparator().isLessThan(intPart("11"));
    }

    @Test
    public void testVersionPartComparisonWithBigIntegers() {
        assertThat(intPart("122337203685477580715234")).usingDefaultComparator().isEqualByComparingTo(intPart("122337203685477580715234"));
        assertThat(intPart("9")).usingDefaultComparator().isLessThan(intPart("122337203685477580715234"));
        assertThat(intPart("122337203685477580715234")).usingDefaultComparator().isGreaterThan(intPart("9"));
    }

    @Test
    public void testVersionPartComparisonFallsBackToStringSemanticsIfNecessary() {
        assertThat(strPart("alpha")).usingDefaultComparator().isGreaterThan(intPart("12"));
        assertThat(strPart("12")).usingDefaultComparator().isLessThan(intPart("alpha"));

        assertThat(strPart("alpha")).usingDefaultComparator().isGreaterThan(intPart("122337203685477580715234"));
        assertThat(strPart("122337203685477580715234")).usingDefaultComparator().isLessThan(intPart("alpha"));
    }
}