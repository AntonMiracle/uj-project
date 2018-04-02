package pl.edu.uj.project.core;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * FileObserver is for observe file inside.
 * Count and get symbols,words,lines or statistic.
 *
 * @author Anton Bondarenko. {@code b.anton.m.1986@gmail.com}
 * @version 1.0
 */
public class FileObserver {

    private Path path;
    private String text;
    private boolean isFile;
    private Charset charset;

    /**
     * Create FileObserver to discover file inside.
     *
     * @param path    of file to observe.
     * @param charset for file to observe.
     * @throws IllegalArgumentException if path : null, not exist, readable == false.
     *                                  if charset : null.
     *                                  if IOException while try read file.
     */
    public FileObserver(Path path, Charset charset) throws IllegalArgumentException {
        setCharset(charset);
        setPath(path);
        read();
    }

    public Charset getCharset() {
        return charset;
    }

    public Path getPath() {
        return path;
    }

    public String getText() {
        return text;
    }

    private void setCharset(Charset charset) {
        Util.throwIAEWhenNull(charset, this, "setCharset(Charset charset)");
        this.charset = charset;
    }

    private void setPath(Path path) {
        Util.throwIAEWhenNull(path, this, "setPath(Path path)");
        Util.throwIAE(!Files.exists(path), this, "setPath(Path path)", "Path not exist");
        Util.throwIAE(!Files.isReadable(path), this, "setPath(Path path)", "File not readable");
        isFile = Files.isDirectory(path) ? false : true;
        this.path = path;
    }

    private void read() {
        if (isFile) {
            try {
                text = new String(Files.readAllBytes(getPath()), getCharset());
            } catch (IOException e) {
                Util.throwIAE(true, this, "read()", " IOException when read file." + e.getMessage());
            }
        } else {
            text = "";
        }
    }

    /**
     * Get elements in file.
     *
     * @param element which need to get. {@link FileObserver.Element}.
     * @return Stream of elements.
     * @throws IllegalArgumentException when element is null.
     */
    public Stream<String> get(Element element) throws IllegalArgumentException {
        Util.throwIAEWhenNull(element, this, "get(Element element)");
        Stream<String> result = Stream.of("");
        if (element == Element.WORDS) result = Pattern.compile("\\PL+").splitAsStream(getText());
        if (element == Element.LINES) result = getLines();
        if (element == Element.SYMBOLS) result = getText().chars().mapToObj(i -> String.valueOf((char) i));
        if (element == Element.ALL) result = Stream.of(getText());
        return result;
    }

    private Stream<String> getLines() {
        List<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(getText());
        while (scanner.hasNextLine()) {
            result.add(scanner.nextLine());
        }
        scanner.close();
        return result.stream();
    }

    public static Set<Charset> charsets() {
        Set<Charset> charsets = new HashSet<>();
        charsets.add(StandardCharsets.UTF_16LE);
        charsets.add(StandardCharsets.UTF_16);
        charsets.add(StandardCharsets.UTF_8);
        charsets.add(StandardCharsets.UTF_16BE);
        charsets.add(StandardCharsets.ISO_8859_1);
        charsets.add(StandardCharsets.US_ASCII);
        return charsets;
    }

    /**
     * Statistic for element.
     *
     * @param element for statistic. {@link FileObserver.Element}.
     * @return {@code Map<String,Long>}
     * where String {@link Element#name()} and long is count of elements in file.
     * @throws IllegalArgumentException when element is null.
     */
    public Map<String, Long> statistic(Element element) throws IllegalArgumentException {
        Map<String, Long> result = new TreeMap<>();
        if (element == Element.ALL) {
            return statistic();
        } else {
            get(element).forEach(string -> {
                if (result.containsKey(string)) {
                    result.put(string, result.get(string) + 1L);
                } else {
                    result.put(string, 1L);
                }
            });
        }
        return result;
    }

    private Map<String, Long> statistic() {
        Map<String, Long> result = new TreeMap<>();
        result.put(Element.LINES.name(), get(Element.LINES).count());
        result.put(Element.WORDS.name(), get(Element.WORDS).count());
        result.put(Element.SYMBOLS.name(), get(Element.SYMBOLS).count());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileObserver that = (FileObserver) o;

        if (isFile != that.isFile) return false;
        if (path != null ? !path.equals(that.path) : that.path != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        return charset != null ? charset.equals(that.charset) : that.charset == null;
    }

    @Override
    public int hashCode() {
        int result = path != null ? path.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (isFile ? 1 : 0);
        result = 31 * result + (charset != null ? charset.hashCode() : 0);
        return result;
    }

    public enum Element {
        WORDS, LINES, ALL, SYMBOLS
    }
}
