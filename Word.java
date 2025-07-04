//Name: Maritza Ramirez
//Date: July 1, 2025


public class Word implements Comparable<Word> {
    private String word;

    public Word(String word) {
        this.word = word.toUpperCase();
    }

    public String getWord() {
        return word;
    }

    @Override
    public int compareTo(Word other) {
        return this.word.compareTo(other.word);
    }

    @Override
    public String toString() {
        return word;
    }
}
