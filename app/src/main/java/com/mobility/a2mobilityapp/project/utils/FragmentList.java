package com.mobility.a2mobilityapp.project.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mobility.a2mobilityapp.R;
import com.mobility.a2mobilityapp.project.bean.MeioTransporte;
import com.mobility.a2mobilityapp.project.bean.Particular;
import com.mobility.a2mobilityapp.project.bean.TransportePublico;
import com.mobility.a2mobilityapp.project.bean.Uber;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragmentList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static Uber[] uber;
    private static ArrayList<MeioTransporte> listaMeios = new ArrayList<>();


    private static  DecimalFormat df = new DecimalFormat("#0.00");

    private static String enderecoOrigem;
    private static String enderecoDestino;
    private static boolean possuiTransportePublico2;

    public FragmentList() {
        // Required empty public constructor
    }


    public static FragmentList newInstance(String enderecoInicial, String enderecoFinal, Uber[] uber,
                                           TransportePublico transportePublico, Particular particular, boolean possuiTransportePublico) {
        FragmentList fragment = new FragmentList();

        //Transporte publico
        if(transportePublico.getPreco() != null || transportePublico.getTempo() != null){
            String precoTransporte = "R$" + transportePublico.getPreco();
            transportePublico.setPreco(precoTransporte );
            listaMeios.add(setTransportePublico(transportePublico));
        }

        //Uber
        if(uber != null){
            for(int i=0;i<uber.length-1;i++){
                MeioTransporte transporte = new MeioTransporte();
                transporte.setDistancia(Float.toString(uber[i].getDistance()) + " km");
                transporte.setNome(uber[i].getDisplay_name());
                transporte.setPreco(uber[i].getEstimate());
                int tempoUber = uber[i].getDuration() / 60;
                transporte.setTempo(tempoUber + " mins");
                listaMeios.add(transporte);
            }
        }

        //Carro Particular
        if(particular.getPreco() != null || particular.getTempo() != null){
            MeioTransporte transporte = new MeioTransporte();
            //transporte.setDistancia(particular.getDistancia() + " km");
            transporte.setDistancia(Float.toString(uber[1].getDistance()) + " km");
            transporte.setNome("Carro Particular");
            //transporte.setPreco("R$" + particular.getPreco());
            double kmLitro = 8;
            double precoCombustivel = 3.80;
            double precoFinal = (uber[1].getDistance() / kmLitro) * precoCombustivel;
            transporte.setPreco("R$" + df.format(precoFinal));
            //transporte.setTempo(particular.getTempo() + " mins");
            int tempoParticular = uber[1].getDuration() / 60;
            transporte.setTempo(tempoParticular + " mins");
            listaMeios.add(transporte);
        }

        enderecoOrigem = enderecoInicial.toString();
        enderecoDestino = enderecoFinal.toString();
        possuiTransportePublico2 = possuiTransportePublico;

        return fragment;
    }

    public static MeioTransporte setTransportePublico(TransportePublico transportePublico){
        MeioTransporte transporte = new MeioTransporte();
        transporte.setNome("Transporte Público");
        transporte.setDistancia(transportePublico.getDistancia());
        transporte.setPreco(transportePublico.getPreco());
        transporte.setTempo(transportePublico.getTempo());
        //transporte.setImagem(R.drawable.ic_bus);
        return transporte;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
        //Associando dados do Array ao hashmap
        for(MeioTransporte transporte : listaMeios){
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("listview_nome", transporte.getNome());
            hm.put("listview_distancia", transporte.getDistancia());
            hm.put("listview_preco", transporte.getPreco());
            hm.put("listview_tempo", transporte.getTempo());
            //hm.put("listview_imagem", Integer.toString(transporte.getImagem()));
            if(transporte.getNome().equals("Transporte Público")){
                hm.put("listview_imagem", Integer.toString((R.drawable.ic_bus)));
            }
            else if(transporte.getNome().equals("Carro Particular")){
                hm.put("listview_imagem", Integer.toString((R.drawable.ic_automovel_list)));
            }
            else {
                hm.put("listview_imagem", Integer.toString((R.drawable.ic_uber)));
            }

            aList.add(hm);
        }

        String[] from = {"listview_imagem", "listview_nome", "listview_distancia", "listview_preco", "listview_tempo"};
        int[] to = {R.id.listview_imagem, R.id.listview_item_nome, R.id.listview_item_distancia, R.id.listview_item_preco, R.id.listview_item_tempo};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), aList, R.layout.activity_list, from, to);
        ListView androidListView = (ListView) view.findViewById(R.id.list_view);
        androidListView.setAdapter(simpleAdapter);

        listaMeios.clear();

        //quando clicar no item da lista transporte público
        androidListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if((position == 0) && (possuiTransportePublico2 == true)){
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?saddr=" + enderecoOrigem + "&daddr=" + enderecoDestino));
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    public static Uber[] getUber() {
        return uber;
    }

    public static void setUber(Uber[] uber) {
        FragmentList.uber = uber;
    }

    public static ArrayList<MeioTransporte> getListaMeios() {
        return listaMeios;
    }

    public static void setListaMeios(ArrayList<MeioTransporte> listaMeios) {
        FragmentList.listaMeios = listaMeios;
    }

}

