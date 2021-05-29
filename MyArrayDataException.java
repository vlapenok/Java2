public class MyArrayDataException extends NumberFormatException {
    private String txt;

    public String getTxt() {
        return txt;
    }

    public MyArrayDataException(String txt) {
        super(txt);
        this.txt = txt;
    }
}
