package com.example.pjs4.ui.profil;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.pjs4.R;
import com.example.pjs4.ShowChallenge;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

import model.Challenge;
import model.ChallengePivot;
import model.DataBase;
import model.FireBase;
import model.User;

public class ProfilFragment extends Fragment {

    private ProfilViewModel profilViewModel;

    private DataBase dataBase;
    private Button btn_logout;
    private TextView txt_name;
    private String uName;
    private String uPass;
    private User user;

    private TextView testChallenge;
    private TextView tv_chal1; //pour tester
    private ViewPager2 viewPager2;
    private RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FireBase fb;
    private ArrayList<Challenge> l = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        profilViewModel =
                ViewModelProviders.of(this).get(ProfilViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profil, container, false);
        fb = FireBase.getInstance();

        btn_logout = root.findViewById(R.id.btn_logout);
        txt_name = root.findViewById(R.id.session_name);
        uName = "patate";
        fb.getUser(user -> {
            HashMap<Integer, ChallengePivot> backpack = user.getBackpack();
            Log.d("User backpack", backpack.toString());
        });

        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser != null) {
            uName = fbUser.getDisplayName();
        }
       /*fb.getUser(new Callback<User>() {
            @Override
            public void Call(User user) {
                Log.d("User", user.getLogin());
            }
        });*/

        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logout();
            }
        });

        //User Information
        txt_name.append(uName);


        showChallengeDone(root);
        showChallengeInProgress(root);

        return root;
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        this.getActivity().finish();
    }

    /*
    public String getInfoUser() {
        DocumentReference docRef = db.collection("cities").document("SF");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }
*/

    public void initTestChallenge() {
        Challenge c = new Challenge(1, "Ramasse des bouchons", "La collecte de bouchons consiste à ramsser 100 bouchons!", "geste ecolo", "moyen", 30, "");
        Challenge c2 = new Challenge(2, "jester poubelles", "jette bien tes poubelles!", "geste ecolo", "facile", 10, "");
        Challenge c3 = new Challenge(3, "eau froide", "lave toi à l'eau froide", "geste ecolo", "difficile", 50, "");

        l.add(c);
        l.add(c2);
        l.add(c3);
    }

    private void setOnClickShow(final TextView btn, final Challenge c, final String verif) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Do whatever you want(str can be used here)
                SendChallenge(c, verif);
            }
        });
    }

    public void SendChallenge(Challenge c, final String verif) {

        Intent i = new Intent(getActivity(), ShowChallenge.class);
        Bundle bundle = new Bundle();
        bundle.putString("tv_challengeName", c.getName_challenge());
        bundle.putString("tv_challengeDesc", c.getDescription_challenge());
        bundle.putString("tv_challengeLevel", c.getDifficulty_challenge());
        bundle.putString("tv_challengeType", c.getType_challenge());
        bundle.putString("verif", verif);
        i.putExtras(bundle);
        startActivity(i);

    }

    public void showChallengeDone(View v) {
        initTestChallenge();

        LinearLayout lay_parent = v.findViewById(R.id.lay_parentDone);

        for (Challenge c : l) {
            LinearLayout lay_child = new LinearLayout(v.getContext());
            lay_child.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    500, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(100, 0, 0, 0);
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

            setOnClickShow(tv_show, c, "true");

        }

    }

    public void showChallengeInProgress(View v) {
        initTestChallenge();

        LinearLayout lay_parent = v.findViewById(R.id.lay_parentInProgress);

        for (Challenge c : l) {
            LinearLayout lay_child = new LinearLayout(v.getContext());
            lay_child.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    500, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(100, 0, 0, 0);
            lay_child.setBackgroundColor(Color.BLUE);
            lay_child.setLayoutParams(layoutParams);
            TextView tv_title = new TextView(v.getContext());
            tv_title.setTextSize(20);
            tv_title.setText(c.getName_challenge()); //changer pour le test

            TextView tv_show = new TextView(v.getContext());
            tv_show.setTextSize(15);
            tv_show.setText("Voir le défi");

            lay_child.addView(tv_title);
            lay_child.addView(tv_show);

            lay_parent.addView(lay_child);

            setOnClickShow(tv_show, c, "false");
        }


    }


}
