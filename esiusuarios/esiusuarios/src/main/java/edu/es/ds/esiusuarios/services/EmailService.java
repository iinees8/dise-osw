package edu.es.ds.esiusuarios.services;

import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import edu.es.ds.esiusuarios.auxiliares.HttpClient;
import edu.es.ds.esiusuarios.model.Token2;
import edu.es.ds.esiusuarios.model.User;

@Service
public class EmailService {

    public void sendValidationEmail(User user, Token2 token) throws IOException {

        System.out.println("ENTRA EMAIL");

        InputStream htmlStream =
            getClass().getClassLoader()
            .getResourceAsStream("welcome.html.txt");

        String body =
            new String(htmlStream.readAllBytes());

        body =
            body.replace(
                "#TOKEN#",
                token.getId()
            );

        InputStream paramsStream =
            getClass().getClassLoader()
            .getResourceAsStream("brevo.parameters.txt");

        String paramsText =
            new String(paramsStream.readAllBytes());

        JSONObject params =
            new JSONObject(paramsText);

        String endpoint =
            params.getString("endpoint");

        JSONArray headers =
            params.getJSONArray("headers");

        JSONObject payload =
            new JSONObject();

        payload.put(
            "sender",
            params.getJSONObject("sender")
        );

        JSONArray to =
            new JSONArray();

        to.put(
            new JSONObject()
            .put("email", user.getEmail())
            .put("name", user.getName())
        );

        payload.put("to", to);

        payload.put(
            "subject",
            params.getString("subject")
        );

        payload.put(
            "htmlContent",
            body
        );

        HttpClient client =
            new HttpClient();

        String response =
            client.sendPost(
                endpoint,
                headers,
                payload
            );

        System.out.println(response);
    }
}