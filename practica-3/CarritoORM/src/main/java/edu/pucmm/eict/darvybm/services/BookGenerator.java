package edu.pucmm.eict.darvybm.services;

import com.github.javafaker.Faker;
import edu.pucmm.eict.darvybm.modelos.FotoProducto;
import edu.pucmm.eict.darvybm.modelos.Producto;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.Scanner;


public class BookGenerator  {
    public BookGenerator() throws Exception {
        generateBook();
    }



    public static void generate30Books() throws Exception {
        for (int i = 0; i < 30; i++) {
            generateBook();
        }
    }

    public static Producto generateBook() throws Exception {

        Faker faker = new Faker();
        String name = faker.book().title();
        String author = faker.book().author();
        Double precio = Double.parseDouble(faker.commerce().price());
        String genre = "Un libro de " + author + " de genero: "  + faker.book().genre();
        Producto producto = new Producto(name, genre, author, precio);


        producto.getFotosBase64().add(new FotoProducto(urlToBase64("https://picsum.photos/600/400"), "jpg", producto));
        producto.getFotosBase64().add(new FotoProducto(urlToBase64("https://picsum.photos/600/400"), "jpg", producto));
        producto.getFotosBase64().add(new FotoProducto(urlToBase64("https://picsum.photos/600/400"), "jpg", producto));
        ProductoServices.getInstance().addProducto(producto);

        return producto;
    }

    public static String urlToBase64(String imageUrl) throws Exception {
        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStream inputStream = connection.getInputStream();
        byte[] bytes = inputStream.readAllBytes();
        String base64 = Base64.getEncoder().encodeToString(bytes);
        return base64;
    }






}
