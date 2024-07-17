package andrews.ying;

public class PowerSliceMapping {
    public static void main(String[] args) {
//        String s = "\\\\GXPDBAE495916\\fmv360\\CSU_FtCollins_short.mpeg";
        String s = "file:////GXPDBAE495916/fmv360/CSU_FtCollins_short.mpeg";
        System.out.println(s);
        String p = s.replaceAll("/", "\\\\").replaceFirst("^file:[\\\\]*", "\\\\\\\\");
        System.out.println(p);
    }
}
