package com.kursova.kep.rest;

import javafx.scene.control.TextArea;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.logging.Logger;

/**
 * by Mr Skip on 26.03.2016.
 */

public class Client {
    private static TextArea textArea;
    public static boolean status = true;

    public static void setTextArea(TextArea textArea){
        Client.textArea = textArea;
    }
//    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);
    private static Logger logger = Logger.getLogger("Client.class");
    private static final String BEGINNER_URI = "http://localhost:8080/";

    private static String uri;

    public static <C extends Class> Get get(String uri, C c1) {
        Client.uri = BEGINNER_URI + uri;
        return new Get(c1);
    }

    public static <T, C extends Class> T delete(String uri, C c) {
        Client.uri = BEGINNER_URI + uri;
        return new Rend(RequestMethod.DELETE).execute(c);
    }

    public static <C extends Class> Post post(String uri, C c) {
        Client.uri = BEGINNER_URI + uri;
        return new Post(c);
    }

    public static class Get{
        private HashMap<String, String> variable = new HashMap<>();

        private Class c;

        Get(Class c) {
            this.c = c;
        }

        public Get setVariable(HashMap<String, String> variable) {
            if (variable != null)
                this.variable = variable;
            return this;
        }

        public <T> T build() {
            return (T) new Rend(RequestMethod.GET, variable).execute(c);
        }
    }

    public static class Post{
        private HashMap<String, String> variable = new HashMap<>();
        private Object request;
        private Class c;

        Post(Class c) {
            this.c = c;
        }

        public Post setVariable(HashMap<String, String> variable) {
            if (variable != null)
                this.variable = variable;
            return this;
        }

        public Post setRequest(Object request) {
            this.request = request;
            return this;
        }

        public <T> T build() {
            return (T) new Rend(RequestMethod.POST, variable, request).execute(c);
        }
    }

    private static class Rend {
        private RestTemplate restTemplate = new RestTemplate();
        private RequestMethod requestMethod;

        private HashMap<String, String> variable = new HashMap<>();
        private Object request;

        private Rend() {
            requestMethod = RequestMethod.GET;
        }

        Rend(RequestMethod requestMethod) {
            this.requestMethod = requestMethod;
        }

        Rend(RequestMethod requestMethod, HashMap<String, String> variable){
            this.requestMethod = requestMethod;
            this.variable = variable;
        }

        Rend(RequestMethod requestMethod, HashMap<String, String> variable, Object request){
            this.requestMethod = requestMethod;
            this.request = request;
            this.variable = variable;
        }

        public <T, C extends Class> T execute(C c) {
            try {
                
                UriComponentsBuilder builderUri =
                        UriComponentsBuilder.fromHttpUrl(Client.uri);
                variable.forEach(builderUri::queryParam);
                ResponseEntity response;
                switch (requestMethod){
                    case DELETE: response = restTemplate.exchange(new RequestEntity<>(HttpMethod.DELETE, new URI(Client.uri)), c);
                        break;
                    case GET:
                        System.out.println(builderUri.build().encode("UTF-8").toUri() + " - " + c);
                        response = restTemplate.getForEntity(builderUri.build().encode().toUri(), c);
                        break;
                    case POST:
                        response = restTemplate.postForEntity(Client.uri, request, c, variable);
                        break;
                    default: response = restTemplate.getForEntity(Client.uri, c, variable);
                }

                if (!c.isArray())
                    return (T) response.getBody();
                else {
                    return (T) Arrays.asList((T[]) response.getBody());
                }

            } catch (HttpClientErrorException e){
                System.out.println("Client exception (HttpClientErrorException)");
                if (e.getResponseHeaders().get("MySQL_Exception") != null)
                    textArea.setText(e.getResponseHeaders().get("MySQL_Exception").get(0));
                status = false;
                return null;
            } catch (ResourceAccessException e){
                System.out.println("Resource Access Exception: " + e.getCause().getMessage());
                status = false;
                return null;
            } catch (Exception e){
                System.out.println("Body is empty: \n" + e + "");
                status = false;
                return null;
            }
        }
    }

}
