package com.example.pjs4.ui.actu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.pjs4.R;
import com.example.pjs4.ShowChallenge;

import java.util.ArrayList;

import model.Challenge;

public class ActuFragment extends Fragment {

    private ActuViewModel actuViewModel;

    private ConstraintLayout lay_parentActu;

    private ArrayList<ArrayList<String>> l = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        actuViewModel =
                ViewModelProviders.of(this).get(ActuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_actu, container, false);

        lay_parentActu = root.findViewById(R.id.lay_parentActu);

        return root;
    }


    /**
     * Récupérer collection FilActualité depuis FireBase
     */


    /**
     * Challenges juste pour tester...
     */
    public void initTestChallenge() {


        ArrayList<String> actu1 = new ArrayList<>();
        ArrayList<String> actu2 = new ArrayList<>();
        ArrayList<String> actu3 = new ArrayList<>();

        //correspond à l'ID Challenge en attente
        actu1.add(0, "0");
        actu2.add(0, "1");
        actu3.add(0, "2");

        //correspond à l'ID du user qui l'a fait
        actu1.add(1, "0");
        actu2.add(1, "1");
        actu3.add(1, "2");

        //correspond au Login du User
        actu1.add(2, "dede");
        actu2.add(2, "alexis");
        actu3.add(2, "antoine");

        //correspond à la photo qu'il a posté pour relever le Challenge
        actu1.add(3, "img.jbdjzb");
        actu2.add(3, "img.hfkee");
        actu3.add(3, "img.jebfe");

        //Correspond au titre du challenge qui vient d'être relevé
        actu1.add(4, "Ramasse des bouchons");
        actu2.add(4, "jette la poubelle");
        actu3.add(4, "lave-toi à l'eau froide");

        l.add(actu1);
        l.add(actu2);
        l.add(actu3);
    }

    private void setOnClickShow(final TextView btn, final Challenge c) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Do whatever you want(str can be used here)
                SendChallenge(c);
            }
        });
    }

    /**
     * Reste à modifier
     */
    public void SendChallenge(Challenge c) {

        Intent i = new Intent(getActivity(), ShowChallenge.class);
        Bundle bundle = new Bundle();
        bundle.putString("tv_challengeName", c.getName_challenge());
        bundle.putString("tv_challengeDesc", c.getDescription_challenge());
        bundle.putString("tv_challengeLevel", c.getDifficulty_challenge());
        bundle.putString("tv_challengeType", c.getType_challenge());

        i.putExtras(bundle);
        startActivity(i);

    }

    /**
     * à modif aussi
     */
    public void showChallengeActu(View v) {

        //à la place il y aura :
        /**
         * appel de la fonction getFilActualité de FireBase
         * récup des des éléments dans une liste
         */

        initTestChallenge();

        LinearLayout lay_parent = v.findViewById(R.id.lay_parentDone);

        /*for (Challenge c : l ){
            LinearLayout lay_child = new LinearLayout(v.getContext());
            lay_child.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    500, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(100,0,0,0);
            lay_child.setBackgroundColor(Color.BLUE);
            lay_child.setLayoutParams(layoutParams);
            TextView tv_title = new TextView(v.getContext());
            tv_title.setTextSize(20);
            tv_title.setText(c.getName_challenge());

            TextView tv_show = new TextView(v.getContext());
            tv_show.setTextSize(15);
            tv_show.setText("Voir le défi");

            lay_child.addView(tv_title);
            lay_child.addView(tv_show);

            lay_parent.addView(lay_child);

            setOnClickShow(tv_show, c);
            }*/

    }

}
