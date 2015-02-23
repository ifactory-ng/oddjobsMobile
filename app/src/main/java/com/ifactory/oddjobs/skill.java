package com.ifactory.oddjobs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link skill.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link skill#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class skill extends ListFragment{

 //   private OnFragmentInteractionListener mListener;
    private pro_adapter mAdapt;
    ArrayList<SkillModel> skill;
    //Jobject jo;
    ListView lv;
    String id;
    Communicator com;
    JSONArray ja = null;
    public interface Communicator {
        public void itemSelected(JSONObject Jobject);

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Context c = getActivity();
       // SharedPreferences editor = c.getSharedPreferences(c.getString(R.string.preference_file_name), c.MODE_PRIVATE);
      //id = editor.getString(c.getString(R.string.preference_file_name), "id");
        //jo = new Jobject(routes.GET_USER_PRODUCTS + id);
       id="963176113698271";

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.skill_list, container, false);
lv = (ListView)v.findViewById(android.R.id.list);

        FutureTask<JSONArray> Jarray = new FutureTask<JSONArray>(new Jobject(routes.GET_USER_PRODUCTS + id));
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(Jarray);
        try{
            ja = Jarray.get();
        }
        catch (InterruptedException i){
            i.printStackTrace();
        }
        catch (ExecutionException e){
            e.printStackTrace();
        }
        es.shutdown();


//        Log.d("test", ja.toString());
/*            if(ja.isNull(0)){



            }*/
         skill = SkillModel.getData(ja);

        mAdapt = new pro_adapter(getActivity().getBaseContext(), skill);
/*        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              SkillModel md = (SkillModel) (getListAdapter()).getItem(position);
             com.itemSelected(md.passJson());
                String pos = Integer.toString(position);
                Log.d("click", pos);
            }
        });*/
        lv.setAdapter(mAdapt);
        // Inflate the layout for this fragment
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
   /* public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
   // mAdapt = new pro_adapter(getActivity());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            com = (Communicator) activity;
        }
        catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement Communicator interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //SkillModel md = (SkillModel) l.getAdapter().getItem(position);
        SkillModel md = (SkillModel)l.getItemAtPosition(position);
        String pos = Integer.toString(position);
        Log.d("click", pos);
        com.itemSelected(md.passJson());



    }
}
