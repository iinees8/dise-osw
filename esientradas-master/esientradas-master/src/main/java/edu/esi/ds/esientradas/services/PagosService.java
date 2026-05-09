package edu.esi.ds.esientradas.services;

import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;


import org.json.*;


@Service
public class PagosService {
    static {
        Stripe.apiKey = "sk_test_51T92jARWJutHSGUGTj0Sv0Kx9r3sCL7xdb9vZTnnXEf1NHhhwFtRkfKg3HxMVRTbmVNIOmcSMHCg00d50oRx9Gdj00lNH3itUo";
    }

    public String prepararPago(Long centimos)  throws StripeException {
            PaymentIntentCreateParams params = new PaymentIntentCreateParams.Builder()
                    .setCurrency("eur")
                    .setAmount(centimos)
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);
            JSONObject jso = new JSONObject(intent.toJson());
            String clientSecret = jso.getString("client_secret");
            return clientSecret;

    }

    public boolean pagar(Long precio) {
        return precio > 0;
    }


}
