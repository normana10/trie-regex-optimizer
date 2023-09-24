package space.normand.regextrieoptimizer;

import java.util.TreeMap;

// Intentionally package-private to not pollute dependents
class TrieNode {
    private final TreeMap<String, TrieNode> children;
    private boolean endOfWord;

    public TrieNode() {
        // Using a TreeMap so the keys/characters are sorted (improves performance)
        this.children = new TreeMap<>();
    }

    public TreeMap<String, TrieNode> getChildren() {
        return children;
    }

    public boolean isEndOfWord() {
        return endOfWord;
    }

    public void setEndOfWord(final boolean endOfWord) {
        this.endOfWord = endOfWord;
    }
}
