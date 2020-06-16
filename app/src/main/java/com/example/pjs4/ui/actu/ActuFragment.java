package com.example.pjs4.ui.actu;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pjs4.MainActivity;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pjs4.PictureActivity;
import com.example.pjs4.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import model.DataBase;
import model.User;

public class ActuFragment extends Fragment {

    private ActuViewModel actuViewModel;

    private DataBase dataBase;
    private Button btn_logout;
    private TextView txt_name;
    private String uName;
    private String uPass;
    private User user;

    private ViewPager2 viewPager2;
    private RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        actuViewModel =
                ViewModelProviders.of(this).get(ActuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_actu, container, false);

        /*final TextView textView = root.findViewById(R.id.text_notifications);
        actuViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        btn_logout = getView().findViewById(R.id.btn_logout);
        txt_name = getView().findViewById(R.id.session_name);

        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser != null) {
            uName = fbUser.getDisplayName();
        }

        //User Information
        txt_name.append(uName);

        showAllChallenge();

        /*Button btn_camera = getView().findViewById(R.id.btn_camera);

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //startActivity(new Intent(this, PictureActivity.class));
            }
        });*/


        return root;
    }

    //voir comment utilisef cette méthode ici
    public void logout(View view){
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


    /**
     * Show all challenges of the data base from fire base
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showAllChallenge(){

        ArrayList<String> liste = new ArrayList<>();
        liste.add("Ramasse des bouchons");
        liste.add("utilise une serviette en tissu");
        liste.add("tri collectif");
        liste.add("Stop plastique");



        /*test = findViewById(R.id.tv_testChallenge);
        test.append(liste.get(0));*/

       /* LinearLayout l = findViewById(R.id.layout_challenge);

        //création d'un text view
        TextView t = new TextView(this);
        t.setText(liste.get(0));
        t.setTextSize(25);

        // Définition de la façon dont le composant va remplir le layout.
        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // Ajout du composant au layout.
        l.addView(t, layoutParam);*/

    }

}
