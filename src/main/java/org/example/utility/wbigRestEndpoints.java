package org.example.utility;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class wbigRestEndpoints {
    private static String Localhost = "http://localhost:8080/";
    private static String wbigURL = "http://wfm-group-1.uni-muenster.de:80/";

    // adjust here
    private static String current_URL = wbigURL;

    public static String getCurrent_URL() {
        return current_URL;
    }
}
