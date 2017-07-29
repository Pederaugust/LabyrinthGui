import org.jetbrains.annotations.NotNull;

/**
 * Created by PederAugust on 22.04.2017.
 */
public class StringComparable implements Comparable<StringComparable> {
    private String innhold;
    public StringComparable(String innhold){
        this.innhold = innhold;
    }
    @Override
    public int compareTo(@NotNull StringComparable o) {
        if (o.innhold.length() > this.innhold.length()) {
            return -1;
        } else if (o.innhold.length() < this.innhold.length()){
            return 1;
        } else {
            return 0;
        }
    }


    @Override
    public String toString() {
        return innhold;
    }
}
