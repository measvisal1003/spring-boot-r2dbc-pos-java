package backend.Utils;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StringUtil {

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface TrimString {}

    @TrimString
    public String trimString(String s) {
        return s == null ? null :s.replaceAll("\\s+([\\p{P}])","$1").replaceAll("\\s+"," ").trim() ;
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface AddSpaceAllPunctuation {}

    @AddSpaceAllPunctuation
    public String addSpaceAllPunctuation(String st) {
        Pattern pattern = Pattern.compile("([.,:;!?])");
        //create a matcher object to match the pattern again the sting
        Matcher matcher = pattern.matcher(st);
        //use String Builder
        StringBuilder stringBuilder = new StringBuilder();

        int lastIndex = 0;
        while (matcher.find()) {
            stringBuilder.append(st, lastIndex, matcher.start());
            stringBuilder.append(" ").append(matcher.group()).append(" ");
            lastIndex = matcher.end();
        }
        stringBuilder.append(st.substring(lastIndex));

        return stringBuilder.toString();
    }
}
