package space.normand.regextrieoptimizer;

/**
 * Heavily based on <a href="https://www.baeldung.com/trie-java">this Baeldung article</a>
 */
// Intentionally package-private to not pollute dependents
class Trie {
    private final TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    public void insert(final String word) {
        TrieNode curr = root;
        for (final char c : word.toCharArray()) {
            curr = curr.getChildren().computeIfAbsent(String.valueOf(c), (ignored) -> new TrieNode());
        }
        curr.setEndOfWord(true);
    }

    public TrieNode getRoot() {
        return root;
    }
}
