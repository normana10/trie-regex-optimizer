package regextrieoptimizer;

import org.junit.jupiter.api.Test;
import space.normand.regextrieoptimizer.RegexTrieOptimizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegexTrieOptimizerTest {
    @Test
    public void jumping() {
        final String optimizedPattern = RegexTrieOptimizer.forTerms(listOf("jump", "jumping"));
        assertEquals(optimizedPattern, "jump(?:ing)?");

        final String searchText = "The thief was jumping over the wall";
        assertEquals(
                getMatches(Pattern.compile("jumping|jump"), searchText),
                getMatches(Pattern.compile(optimizedPattern), searchText)
        );
    }

    @Test
    public void foobah() {
        // stack_overflow_example
        final String optimizedPattern = RegexTrieOptimizer.forTerms(listOf("foobar", "foobah", "fooxar", "foozap", "fooza"));
        assertEquals("foo(?:ba[hr]|xar|zap?)", optimizedPattern);
    }

    @Test
    public void albert() {
        // apache_trie_example
        final String optimizedPattern = RegexTrieOptimizer.forTerms(listOf("Albert", "Xavier", "XyZ", "Anna", "Alien", "Alberto", "Alberts", "Allie", "Alliese", "Alabama", "Banane", "Blabla", "Amber", "Ammun", "Akka", "Akko", "Albertoo", "Amma"));
        assertEquals("(?:A(?:kk[ao]|l(?:abama|bert(?:oo?|s)?|ien|lie(?:se)?)|m(?:ber|m(?:a|un))|nna)|B(?:anane|labla)|X(?:avier|yZ))", optimizedPattern);
    }

    @Test
    public void foo() {
        final String optimizedPattern = RegexTrieOptimizer.forTerms(listOf("foo", "bar"));
        assertEquals("(?:bar|foo)", optimizedPattern);
    }

    @Test
    public void valiantly() {
        final String optimizedPattern = RegexTrieOptimizer.forTerms(listOf("valiant", "valiantly"));
        assertEquals("valiant(?:ly)?", optimizedPattern);
    }

    private List<String> getMatches(final Pattern pattern, final String content) {
        final Matcher matcher = pattern.matcher(content);
        final List<String> toReturn = new ArrayList<>();
        while (matcher.find()) {
            toReturn.add(matcher.toMatchResult().group());
        }
        return toReturn;
    }

    private List<String> listOf(final String... terms) {
        return Arrays.asList(terms);
    }
}
