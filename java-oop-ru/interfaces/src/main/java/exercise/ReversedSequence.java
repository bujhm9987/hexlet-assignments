package exercise;

// BEGIN
public class ReversedSequence implements CharSequence{

    private String str;

    public ReversedSequence(String string) {
        String result = "";
        for (int i = 0; i < string.length(); i++) {
            result = string.charAt(i) + result;
        }
        this.str = result;
    }

    public String toString() {
        return str;
    }

    @Override
    public int length() {
        return str.length();
    }

    @Override
    public char charAt(int index) {
        return str.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return str.substring(start, end);
    }

}
// END
