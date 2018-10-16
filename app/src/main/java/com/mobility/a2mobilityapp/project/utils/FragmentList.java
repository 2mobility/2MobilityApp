package com.mobility.a2mobilityapp.project.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mobility.a2mobilityapp.R;
import com.mobility.a2mobilityapp.project.bean.MeioTransporte;
import com.mobility.a2mobilityapp.project.bean.Particular;
import com.mobility.a2mobilityapp.project.bean.TransportePublico;
import com.mobility.a2mobilityapp.project.bean.Uber;
import com.mobility.a2mobilityapp.project.services.CrudOperation;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static Uber[] uber;
    private static ArrayList<MeioTransporte> listaMeios = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static  DecimalFormat df = new DecimalFormat("#0.00");

    private OnFragmentInteractionListener mListener;

    public FragmentList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentList.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentList newInstance(String param1, String param2, Uber[] uber, TransportePublico transportePublico, Particular particular,String user) {
        FragmentList fragment = new FragmentList();
        //Transporte publico
        if(transportePublico.getPreco() != null || transportePublico.getTempo() != null){
            String precoTransporte = "R$" + transportePublico.getPreco();
            transportePublico.setPreco(precoTransporte);
            listaMeios.add(setTransportePublico(transportePublico,user));
        }

        //Uber
        if(uber != null){
            for(int i=0;i<uber.length-1;i++){
                MeioTransporte transporte = new MeioTransporte();
                transporte.setId_usuario(Integer.parseInt(user));
                transporte.setDistancia(Float.toString(uber[i].getDistance()) + " km");
                transporte.setNome(uber[i].getDisplay_name());
                String[] precoUber = uber[i].getEstimate().split("-");
                transporte.setPreco(precoUber[0].trim());
                int tempoUber = uber[i].getDuration() / 60;
                transporte.setTempo(tempoUber + " mins");
                CrudOperation crudOperation = new CrudOperation();
                crudOperation.cadastrarViagem(transporte);
                listaMeios.add(transporte);
            }
        }

        //Carro Particular
        if(particular.getPreco() != null || particular.getTempo() != null){
            MeioTransporte transporte = new MeioTransporte();
            transporte.setId_usuario(Integer.parseInt(user));
            //transporte.setDistancia(particular.getDistancia() + " km");
            transporte.setDistancia(Float.toString(uber[1].getDistance()) + " km");
            transporte.setNome(particular.getApelido());
            //transporte.setPreco("R$" + particular.getPreco());
            double kmLitro   = Double.parseDouble(particular.getKmLitro());
            double precoCombustivel = Double.parseDouble(particular.getPreco());
            double precoFinal = (uber[1].getDistance() / kmLitro) * precoCombustivel;
            transporte.setPreco("R$" + df.format(precoFinal));
            //transporte.setTempo(particular.getTempo() + " mins");
            int tempoParticular = uber[1].getDuration() / 60;
            transporte.setTempo(tempoParticular + " mins");
            CrudOperation crudOperation = new CrudOperation();
            crudOperation.cadastrarViagem(transporte);
            listaMeios.add(transporte);
        }

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    public static MeioTransporte setTransportePublico(TransportePublico transportePublico,String user){
        MeioTransporte transporte = new MeioTransporte();
        transporte.setId_usuario(Integer.parseInt(user));
        transporte.setNome("Transporte Público");
        transporte.setDistancia(transportePublico.getDistancia());
        transporte.setPreco(transportePublico.getPreco().replace("R$",""));
        transporte.setTempo(transportePublico.getTempo());
        //transporte.setImagem(R.drawable.ic_bus);

        CrudOperation crudOperation = new CrudOperation();
        crudOperation.cadastrarViagem(transporte);
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

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}

