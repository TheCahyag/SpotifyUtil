package io.github.TheCahyag.SpotifyUtil.spotify;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * File: Connector.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class Connector {

    private static Connector instance;

    private static String CLIENT_ID = "client_id";
    private static String CLIENT_SECRET = "client_secret";
    private static String USER_ID = "user_id";
    private Map<String, String> information;

    private final SpotifyApi spotifyApi;
    private final ClientCredentialsRequest clientCredentialsRequest;
    private final AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest;

    private Connector(){
        this.information = new HashMap<>();
        String fileName = "spotify_secrets.dat";
        try (Scanner in = new Scanner(new File(fileName))){
            // Parse the information from the data file to get information needed to connect to the spotify api
            while (in.hasNext()){
                String line = in.nextLine();
                String[] beforeAndAfterEquals = line.split("=");
                this.information.put(beforeAndAfterEquals[0], beforeAndAfterEquals[1]);
            }
        } catch (FileNotFoundException fnfe){
            System.err.println("File: " + fileName + " not found.");
        }
        this.spotifyApi = new SpotifyApi.Builder()
                .setClientId(this.information.get(CLIENT_ID))
                .setClientSecret(this.information.get(CLIENT_SECRET))
                .build();

        this.clientCredentialsRequest = spotifyApi.clientCredentials()
                .build();

        this.authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh()
                .build();
    }

    public static Connector getConnector(){
        if (instance == null){
            instance = new Connector();
            instance.clientCredentials_Sync();
            instance.authorizationCodeRefresh_Sync();
        }
        return instance;
    }

    public SpotifyApi getSpotifyApi(){
        return this.spotifyApi;
    }


    public void clientCredentials_Sync() {
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

            System.out.println("Expires in: " + clientCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void clientCredentials_Async() {
        try {
            final Future<ClientCredentials> clientCredentialsFuture = clientCredentialsRequest.executeAsync();

            // ...

            final ClientCredentials clientCredentials = clientCredentialsFuture.get();

            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

            System.out.println("Expires in: " + clientCredentials.getExpiresIn());
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        }
    }



    public void authorizationCodeRefresh_Sync() {
        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRefreshRequest.execute();

            // Set access and refresh token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void authorizationCodeRefresh_Async() {
        try {
            final Future<AuthorizationCodeCredentials> authorizationCodeCredentialsFuture = authorizationCodeRefreshRequest.executeAsync();

            // ...

            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeCredentialsFuture.get();

            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());

            System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        }
    }


}
