package com.ifactory.oddjobs;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


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
    Jobject jo;
    ListView lv;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.skill_list, container, false);
lv = (ListView)v.findViewById(R.id.listView);
         jo = new Jobject("");

         skill = SkillModel.getData(jo.obj());
        mAdapt = new pro_adapter(getActivity().getBaseContext(), skill);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             // SkillModel md = (SkillModel) (getListAdapter()).getItem(position);
             //   int id = md.returnId();
            }
        });
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

}
