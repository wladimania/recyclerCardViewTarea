package com.example.recyclercardview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.UsuarioAdapter;
import WebServices.Asynchtask;
import WebServices.WebService;
import model.Usuario;

public class MainActivity extends AppCompatActivity implements Asynchtask {
  public   RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rclista);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setItemAnimator(new DefaultItemAnimator());


    Map<String, String> datos = new HashMap<String, String>();
    WebService ws= new WebService("https://reqres.in/api/users",
            datos, MainActivity.this, MainActivity.this);
    ws.execute("GET");

    }

    @Override
    public void processFinish(String result) throws JSONException {
        ArrayList<Usuario> lstUsuarios = new ArrayList<Usuario> ();

    try {

        JSONObject JSONlista =  new JSONObject(result);
        JSONArray JSONlistaUsuarios=  JSONlista.getJSONArray("data");
        lstUsuarios = Usuario.JsonObjectsBuild(JSONlistaUsuarios);


        int resId = R.anim.layout_animacion;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getApplicationContext(),
                resId);
        recyclerView.setLayoutAnimation(animation);


            UsuarioAdapter adapatorUsuario = new UsuarioAdapter(this, lstUsuarios);
        recyclerView.setAdapter(adapatorUsuario);

    }
        catch (JSONException e)
   	 {
      	  Toast.makeText(this.getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG);
    	}	

    }
}