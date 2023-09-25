A zero-dependency utility that will optimize term lists into a trie to be used for regex-ing

# Installation

To install via Maven, add the following to the `<dependencies>` section of your `pom.xml` (currently targeting Java 8)

```xml
<dependency>
    <groupId>space.normand</groupId>
    <artifactId>trie-regex-optimizer</artifactId>
    <version>0.0.2</version>
</dependency>
```

# Usage

```java
import space.normand.regextrieoptimizer.RegexTrieOptimizer;

final String optimizedPattern = RegexTrieOptimizer.forTerms(List.of("the","list","of","terms","to","optimize"));
// (?:list|o(?:f|ptimize)|t(?:erms|he|o))
```

If you're going to do a case-insensitive regex, you can do:

```java
final String optimizedPattern = RegexTrieOptimizer.forTermsCaseInsensitive(List.of("the","list","of","terms","to","optimize"));
```

(The case-insensitive variant is more compact and performant _depending_ on how mixed your upper/lower casing is)
