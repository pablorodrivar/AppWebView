package org.izv.aad.appwebview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

/*RECOGER DATOS DE USUARIO Y ENTRAR, SI YA LOS HA REGISTRADO ANTES ENTRA DIRECTAMENTE*/
//USUARIO: aad CONTRASEÑA: 1234

public class ActividadPrincipal extends AppCompatActivity {
    private WebView webView;
    private static final String TAG = "MITAG";
    private String url = "http://www.juntadeandalucia.es/averroes/centros-tic/18700098/moodle2/login/index.php";
    private String URL = url;   //URL CONSTANTE
    private String javaScript = "";
    private static String user = "",password = "";
    private boolean verified = false, visitada = false, visitada2 = false;
    private SharedPreferences shPref;
    private WebAppInterface webAppInterface;

    /*Añade los valores de la clase 'user' y 'password' a las ShPref, si comprobados=true, las guarda en las checkeadas
    * si no, las guarda en unas 'temporales' (esperando a que se verifiquen)*/
    public void addPref(boolean comprobados){
        SharedPreferences.Editor editor = shPref.edit();

        if(comprobados){
            editor.putString("userCh", shPref.getString("user",null));
            editor.putString("passCh", shPref.getString("password",null));
            editor.apply();
        }else{
            editor.putString("user", user);
            editor.putString("password", password);
            editor.apply();
        }
    }

    //INICIALIZA LOS ELEMENTOS
    public void init(){
        webView = findViewById(R.id.wvMoodle);  //init()
        webView.getSettings().setJavaScriptEnabled(true);   //enableJava()
        webView.loadUrl(url);
        webAppInterface = new WebAppInterface();
        webView.addJavascriptInterface(webAppInterface, "android");
        shPref = getPreferences(Context.MODE_PRIVATE);
        user = shPref.getString("user",null);
        password = shPref.getString("password",null);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_principal);

        init();
        //CLIENTE WEB
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                //PAGINA PRINCIPAL + NO VERIFICADO
                if(paginaPrincipal(url) && !verified) {//CREAMOS UNA ALERTA
                    if(!visitada){
                        LayoutInflater inflater = LayoutInflater.from(ActividadPrincipal.this);
                        //Inflamos el layout del alertdialog
                        final View customView = inflater.inflate(R.layout.dialog_signin, null);
                        final TextView username = (EditText) customView.findViewById(R.id.username);
                        final TextView pass = (EditText) customView.findViewById(R.id.password);
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ActividadPrincipal.this)
                                .setTitle(R.string.datos)
                                .setView(customView)
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    //Capturamos los valores de los campos de texto en las variables de la clase
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        user = username.getText().toString();
                                        password = pass.getText().toString();
                                        addPref(false); //NO COMPROBADO
                                    }
                                })
                                .setNegativeButton(R.string.cancel, null);

                        dialog.show();
                        //CREAMOS SENTENCIA JAVASCRIPT QUE ESCRIBA EN LOS CAMPOS DEL LOGIN
                        javaScript = putFields(shPref.getString("user", null),
                                shPref.getString("password", null));
                        Log.v(TAG, javaScript + "");
                    }
                    visitada = true;
                }

                //PAGINA PRINCIPAL + VERIFICADO
                if(paginaPrincipal(url) && verified) {
                    //CREAMOS UN ALERT DIALOG
                    if(!visitada2){
                    LayoutInflater inflater = LayoutInflater.from(ActividadPrincipal.this);
                    final View customView = inflater.inflate(R.layout.dialog_saved, null);
                    final TextView saved_user = (TextView) customView.findViewById(R.id.saved_user);
                    final TextView nombre_usuario = (TextView) customView.findViewById(R.id.nombre_usuario);
                    nombre_usuario.setText(shPref.getString("userSh",null));
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ActividadPrincipal.this)
                            .setTitle(R.string.datos)
                            .setView(customView)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    addPref(true);
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    user = "";
                                    password = "";
                                }
                            });

                    dialog.show();
                    //ESCRIBIMOS LOS SHARED PREFERENCES VALIDADOS
                    javaScript = putFields(shPref.getString("userSh", shPref.getString("user",null)),
                            shPref.getString("passSh", shPref.getString("password",null)));
                    Log.v(TAG, javaScript + "");
                }
                visitada2 = true;
                }

                super.onPageFinished(view, url);
                webView.loadUrl("javascript: " + javaScript);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                url = webView.getUrl(); //ACTUALIZAMOS URL
                if(!paginaPrincipal(url) && !verified) {
                    verified = true;    //flag de verificado
                    addPref(true);  //guardamos las shpref validadas
                    Log.v(TAG, "VERIFICADO");
                }else{
                    Log.v(TAG, "iniciada");
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

        });
    }

    //INDICA SI NOS ENCONTRAMOS EN LA PAGINA PRINCIPAL
    public boolean paginaPrincipal(String url){
        boolean esta = false;

        if(URL.compareTo(url) == 0)
            esta = true;

        return esta;
    }

    //DEVUELVE CODIGO JAVASCRIPT: INSERTA VALORES EN LOS CAMPOS
    public String putFields(String nombre, String pass){
        return "document.getElementById('username').value = "+nombre+";"+
                "document.getElementById('password').value = "+pass+";"+
                "document.getElementById('loginbtn').click()";
    }

    //DEVUELVE CODIGO JAVASCRIPT: RECUPERA VALORES DEL HTML MEDIANTE JAVASCRIPTINTERFACE
    public String saveFromJavaScript(){
        return "var boton = document.getElementById('loginbtn')" +
                "boton.addEventListener('click', function(){" +
                "var usuario = document.getElementById('usuario').value" +
                "var password = document.getElementById('clave').value" +
                "android.sendData(usuario,password,true)" +
                "})";
    }
}
