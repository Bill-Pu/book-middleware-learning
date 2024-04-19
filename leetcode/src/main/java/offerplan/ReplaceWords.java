package offerplan;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class ReplaceWords {
    public static String replaceWords(List<String> dictionary, String sentence) {
        List<String> collect = dictionary.stream().sorted(Comparator.comparing(String::length)).collect(Collectors.toList());
        ArrayList<String> result = new ArrayList<String>(sentence.split(" ").length);
        for (String s : sentence.split(" ")) {
            int flag = 0;
            for (String j : collect) {
                if (j.length() <= s.length()) {
                    int length = j.length();
                    String substring = s.substring(0, length);
                    if (substring.equals(j)){
                        result.add(j);
                        flag = 1;
                        break;
                    }
                }
            }
            if (flag == 0) {

                result.add(s);
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : result
             ) {
            stringBuffer.append(s).append(" ");
        }
        String substring = stringBuffer.toString().substring(0, stringBuffer.toString().length() - 1);
        return substring;
    }

    public static void main(String[] args) {
        String[] dictionary = {"cat","bat","rat"};
        List<String> collect = Arrays.stream(dictionary).collect(Collectors.toList());

        String s = "the cattle was rattled by the battery";
        String s1 = replaceWords2(collect, s);
        System.out.println(s1);

    }
    public static String replaceWords2(List<String> dictionary, String sentence) {
        return Arrays
                .stream(sentence.split(" "))
                .map(word -> dictionary
                        .stream()
                        .filter(word::startsWith)
                        .min(Comparator.comparingInt(String::length))
                        .orElse(word))
                .peek(System.out::println)
                .reduce((s1,s2)->s1+" "+s2)
                .get()
                .trim();
    }
}