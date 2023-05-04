//Darvy Betances
//1014-2918

//Cliente HTTP usando la librería JSOUP y httpComponents y haciendo uso del gestor de proyectos gradle

package edu.pucmm.eict.darvybm;

import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.text.html.parser.Entity;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //Solicitando la URL al usuario

        System.out.println("Bienvenido a este cliente HTTP, ingrese la URL para su consulta: ");
        String URL = new Scanner(System.in).nextLine();

        //Creando un nuevo cliente y procesando la solicitud del mismo
        //Notese la implementación de HttpComponents de Apache.

        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpGet solicitud = new HttpGet(URL);
            HttpResponse respuesta = httpClient.execute(solicitud);

            //Verificando que tipo de archivo es la URL

            Header[] headerTipo = respuesta.getHeaders("Content-Type");

            if (!headerTipo[0].getValue().contains("text/html")){
                System.out.println();
                System.out.println("---------------------------------------");
                System.out.println("      El Archivo es " + headerTipo[0].getValue() + "    ");
                System.out.println("---------------------------------------");
            }
            else{
                System.out.println();
                System.out.println("---------------------------------------");
                System.out.println("-           El Archivo es HTML        -");
                System.out.println("---------------------------------------");

                String documento = EntityUtils.toString(respuesta.getEntity());
                //Para algunos de los siguientes puntos voy a utilizar Jsoup para aprovechar su formateador HTML
                Document doc = Jsoup.parse(documento);

                //1. Indicar la cantidad de lineas del recurso retornado
                
                System.out.println("  > Cant. Lineas: " + cantLineas(documento));
                System.out.println();

                //2. Indicar la cantidad de párrafos (p) que contiene el documento HTML

                Elements p = doc.select("p");
                System.out.println("  > Cant. Parrafos: " + p.size());
                System.out.println();

                //3. Indicar la cantidad de imágenes (img) dentro de los párrafos que contiene el archivo HTML

                Elements imgParrafos = p.select("img");
                System.out.println("  > Cant. Imagenes dentro de párrafos: " + imgParrafos.size());
                System.out.println();

                //4. indicar la cantidad de formularios (form) que contiene el HTML categorizando por el método implementado POST o GET

                Elements forms = doc.select("form");
                System.out.println("  > Cant. Forms: " + forms.size());
                System.out.println("    - (POST) Forms: " + cantFormulariosByMetodo(forms, "POST"));
                System.out.println("    - (GET) Forms: " + cantFormulariosByMetodo(forms, "GET"));
                System.out.println();

                //5. Para cada formulario mostrar los campos del tipo input y su respectivo tipo que contiene en el documento HTML

                System.out.println("  > Forms - campo Input y su valor");
                for (int i = 0; i < forms.size(); i++) {
                    Element form = forms.get(i);
                    System.out.println("    > Form: " + String.format("%0" + 3 + "d", i));
                    Elements inputs = form.select("input");
                    for (Element input : inputs) {
                        System.out.println("        - type: " + input.attr("type"));
                        //System.out.println("Nombre: " + input.attr("name") + ", Tipo: " + input.attr("type"));
                    }
                }
                System.out.println();

                //6. Para cada formulario parseado, identificar que el método de envío del formulario sea POST y enviar una petición al servidor
                //   con el parámetro llamado asignatura y valor practica1 y un header llamado matricula-id con el valor correspondiente a
                //   matrícula o id asignado.

                //7. Las informaciones serán mostradas por la salida estándar.

                System.out.println("  > Forms - obteniendo respuesta a la petición realizada que es el header y el cuerpo del mensaje (con statusCode)");
                for (int i = 0; i < forms.size(); i++) {
                    Element form = forms.get(i);
                    if (form.attr("method").equalsIgnoreCase("POST")){
                        System.out.println("    > Form: " + String.format("%0" + 3 + "d", i));
                        //Obteniendo la url del form action
                        HttpPost httpPost = new HttpPost(form.absUrl("action"));

                        //Creando el header mediante la función setHeader()
                        httpPost.addHeader("matricula-id", "1014-2918");

                        //Creando la petición mediante la función setEntity()
                        ArrayList<BasicNameValuePair> parametros = new ArrayList<BasicNameValuePair>();
                        parametros.add(new BasicNameValuePair("asignatura", "practica1"));
                        httpPost.setEntity(new UrlEncodedFormEntity(parametros));
                        CloseableHttpResponse response = httpClient.execute(httpPost);
                        System.out.println("        - Status code: " + response.getStatusLine().getStatusCode());
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static int cantLineas(String documento){

        //Esta función recorre caracter a caracter el documento buscando salto de líneas

        int cantLineas = 0;
        for (int i = 0; i < documento.length(); i++){
            if (String.valueOf(documento.charAt(i)).equals("\n"))
                cantLineas++;
        }
        return cantLineas;
    }

    private static int cantFormulariosByMetodo(Elements forms, String metodo){

        //Esta función recorre los formularios e ingresa a sus atributos viendo de que tipo es el método.

        int cantForms = 0;
        for (Element form: forms) {
            if (form.attr("method").equalsIgnoreCase(metodo))
                cantForms++;
        }
        return cantForms;
    }
}