package space.normand.regextrieoptimizer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A class that can take a list of terms and create an optimized regex/pattern based on a trie of the terms
 * Heavily based on <a href="https://gist.github.com/EricDuminil/8faabc2f3de82b24e5a371b6dc0fd1e0">this gist</a>
 * With references from <a href="https://stackoverflow.com/questions/42742810/speed-up-millions-of-regex-replacements-in-python-3">this SO post</a>
 */
public class RegexTrieOptimizer {
    public static String forTerms(final List<String> terms) {
        return forTerms(terms, false);
    }

    public static String forTermsCaseInsensitive(final List<String> terms) {
        return forTerms(terms, true);
    }

    private static String forTerms(final List<String> terms, final boolean caseInsensitive) {
        final Trie trie = new Trie();

        for (final String term : terms) {
            trie.insert(caseInsensitive ? term.toLowerCase() : term);
        }

        return recurse("", trie.getRoot());
    }

    private static String recurse(final String character, final TrieNode trieNode) {
        if (trieNode.getChildren().isEmpty()) {
            return character;
        }

        final String optional = trieNode.isEndOfWord() ? "?" : "";

        if (trieNode.getChildren().size() == 1 && (!trieNode.isEndOfWord() || trieNode.getChildren().values().iterator().next().getChildren().isEmpty())) {
            // Only 1 child and it is *not* an end word, add character and recurse
            // or
            // Only 1 child and it has no children, add character and recurse
            final Map.Entry<String, TrieNode> child = trieNode.getChildren().entrySet().iterator().next();
            return character + recurse(child.getKey(), child.getValue()) + optional;
        }

        if (trieNode.getChildren().values().stream().allMatch(node -> node.isEndOfWord() && node.getChildren().isEmpty())) {
            // All children are word ends *and* have no children, we can return a more efficient/compact [xyz] pattern
            return character + String.format("[%s]", String.join("", trieNode.getChildren().keySet())) + optional;
        }

        return character + String.format("(?:%s)", trieNode.getChildren().entrySet().stream()
                .map(entry -> recurse(entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("|"))) + optional;
    }
}
