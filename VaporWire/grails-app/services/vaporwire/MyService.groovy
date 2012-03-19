package vaporwire

import javax.servlet.ServletException
import javax.servlet.ServletConfig

class MyService {
    String apiBaseUrl;
    String fitbitSiteBaseUrl;
    String exampleBaseUrl;
    String clientConsumerKey;
    String clientSecret;
    def grailsApplication;

    public void init() throws ServletException {
        try {
            clientConsumerKey = grailsApplication.config.Consumer_key;
            clientSecret = grailsApplication.config.Consumer_secret;
            fitbitSiteBaseUrl = grailsApplication.config.fitbitSiteBaseUrl;
            apiBaseUrl = grailsApplication.config.apiBaseUrl;
            exampleBaseUrl = grailsApplication.config.exampleBaseUrl;
        } catch (IOException e) {
            throw new ServletException("Exception during loading properties", e);
        }
    }


}
