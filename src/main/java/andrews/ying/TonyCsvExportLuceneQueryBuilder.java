package andrews.ying;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class TonyCsvExportLuceneQueryBuilder {
    public static void main(String[] args) {
//        List<String> uuids = Arrays.asList("55178ced-5f83-4594-b1b7-ad5328c891a3", "96d47965-a7cd-40a0-95aa-0d58a949ca3a");
        List<String> uuids = Collections.emptyList();
//        List<String> uuids = new ArrayList<>();
//        uuids.add("uuid_1");
//        uuids.add("uuid_2");
//        uuids.add("uuid_3");

        System.out.println(buildQuery2(uuids));
    }

    /*
     *
     * Tony's version
     * Build a query that returns all given objectives based on UUID
     */
    private static String buildQuery(List<String> uuids) {
        StringBuilder str = new StringBuilder();

        str.append("tags: objective; uuid:(");

        if (uuids.size() <= 1) {
            str.append(uuids.get(0));
        } else {
            // if more than 1 objective, keep appending with OR
            str.append(uuids.remove(0));

            do {
                str.append(" OR ").append(uuids.remove(0));
            } while (uuids.size() > 0);

            str.append(")");
        }

        return str.toString();
    }

    private static String buildQuery2(List<String> uuids) {
        return uuids.isEmpty() ? "classesV2:\"C/OBJECTIVE/GXP\"" : "classesV2:\"C/OBJECTIVE/GXP\" AND uuid:(" + uuids.stream().collect(Collectors.joining(" OR ")) + ")";
    }
}