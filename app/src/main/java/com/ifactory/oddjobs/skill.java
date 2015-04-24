package com.ifactory.oddjobs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.gc.materialdesign.views.Button;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonFloat;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class skill extends ListFragment{

 //   private OnFragmentInteractionListener mListener;
    private pro_adapter mAdapt;
    ArrayList<SkillModel> skill;
//    Jobject jo;
    ListView lv;
    TextView empty;
    String id;
    SharedPreferences editor;
    Communicator com;
    //JSONArray ja = null;
ButtonFloat floatadd;
    public interface Communicator {
        public void itemSelected(String Jobject);

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Context c = getActivity();
   editor = c.getSharedPreferences(c.getString(R.string.preference_file_name), c.MODE_PRIVATE);

        //id="5516c392ea37b5030f4158d2";
      //  jo = new Jobject(routes.GET_USER_PRODUCTS + id);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.skill_list, container, false);
        lv = (ListView)v.findViewById(android.R.id.list);
        empty = (TextView) v.findViewById(R.id.emptyview);
    floatadd = (ButtonFloat) v.findViewById(R.id.floatadd);
        floatadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_product add = new add_product();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.mainContent, add).commit();
            }
        });
        new GetSkill().execute();
      /*  FutureTask<JSONArray> Jarray = new FutureTask<JSONArray>(new Jobject(routes.GET_SKILLs+ id));
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
        es.shutdown();*/

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
        SkillModel md = (SkillModel) l.getAdapter().getItem(position);
        //SkillModel md = ((pro_adapter)getListAdapter()).getItem(position);
        String pos = Integer.toString(position);
        Log.d("click", pos);
        com.itemSelected(md.id);



    }
    private class GetSkill extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            String result = " ";
            try {
                id = editor.getString("_id", "id");
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet get = new HttpGet(routes.GET_SKILLs + id);

                HttpResponse response = httpclient.execute(get);
                result = EntityUtils.toString(response.getEntity());

//            JSONObject rss = new JSONObject(result);

            }
            catch(ClientProtocolException cpe){
                cpe.getMessage();
            }
            catch(IOException i){
                i.getMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray ja = new JSONArray(s);
                Log.d("resultdata", ja.toString());
//        Log.d("test", ja.toString());
                empty.setVisibility(View.GONE);
                skill = SkillModel.getData(ja);
                Log.d("skill",skill.toString());
                mAdapt = new pro_adapter(getActivity().getBaseContext(), skill);
                lv.setAdapter(mAdapt);
            }
            catch(NullPointerException e){
                e.getMessage();
                lv.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
            }catch (JSONException j){
                j.getMessage();
            }

        }
    }
}
