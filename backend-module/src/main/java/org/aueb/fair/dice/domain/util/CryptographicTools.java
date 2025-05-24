package org.aueb.fair.dice.domain.util;

import java.util.List;

public class CryptographicTools {

    public static String generateSha256Hash(final String input) {
        // do hash

        // return
        return input;
    }


    public static List<String> generateSha256Hash(final List<String> input) {
        // do hash
        input.forEach(CryptographicTools::generateSha256Hash);

        // return
        return input;
    }


}
