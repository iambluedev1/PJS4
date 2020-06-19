package model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FireBase {
    private FirebaseFirestore db;
    private FirebaseStorage strg;

    private Map<String, Challenge> challenges; //id_challenge, object challenge

    private static FireBase instance;

    static {
        instance = new FireBase();
    }

    private FireBase() {
        db = FirebaseFirestore.getInstance();
        strg = FirebaseStorage.getInstance();

        getAllChallenges(challenges -> {
            this.challenges = challenges;
        });
    }

    public static FireBase getInstance() {
        return instance;
    }

    public void addNewUser(String login, String mail) {
        Calendar calendar = Calendar.getInstance();

        Map<String, Object> user = new HashMap<>();
        user.put("Login", login);
        user.put("Mail", mail);
        user.put("Register Date", calendar.getTime());

        db.collection("Users").document(login)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Wrinting firestore db", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Wrinting firestore db", "Error writing document", e);
                    }
                });
    }

    public void getAllChallenges(FirestoreCallback<Map<String, Challenge>> callback) {
        db.collection("Challenge")
                .get()
                .addOnCompleteListener(task -> {
                    Map<String, Challenge> challenges = new HashMap<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            long difficulty = document.getLong("Difficulté");
                            String difficulty_type;
                            if (difficulty == 1) {
                                difficulty_type = "Facile";
                            } else if (difficulty == 2) {
                                difficulty_type = "Moyen";
                            } else {
                                difficulty_type = "Difficile";
                            }
                            Challenge challenge = new Challenge(Integer.parseInt(document.getId()), document.getString("Titre"),
                                    document.getString("Label"), document.getString("Type"), difficulty_type
                                    , difficulty, document.getString("Lien"));

                            challenges.put(document.getId(), challenge);
                        }
                    }
                    callback.onCallback(challenges);
                });
    }

    /**
     * get the current User by calling it on the database
     *
     * @return User
     */
    public void getUser(FirestoreCallback<User> callback) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.d("TEEEEEEEEEEEST", "TESSSST");
        final String mail = currentUser.getEmail();

        db.collection("Users").whereEqualTo("Mail", mail).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot document = task.getResult();
                String login = document.getDocuments().get(0).getString("Login");
                User user = new User(login, mail);
                Log.d("TEEEEEEEEEEEST", "TESSSST");
                getUserChallenge(login, map_challengePivot -> {
                    for (Map.Entry mapentry : map_challengePivot.entrySet()) { // For each challenge pivot
                        String status = mapentry.getValue().toString();
                        ChallengeStatus challengeStatus;
                        if (status.equals("enCours")) {
                            challengeStatus = new UnDone();
                        } else if (status.equals("enAttente")) {
                            challengeStatus = new InProgress();
                        } else {
                            challengeStatus = new Done();
                        }
                        user.addChallenge(challenges.get(mapentry.getKey()), challengeStatus);
                    }
                    Log.d("User", user.getLogin());
                    callback.onCallback(user);
                });
            }
        });
    }

    public void getImage(String imageName, final FirestoreCallback<Bitmap> callback) {
        final Bitmap[] b = new Bitmap[1];
        StorageReference storageRef = strg.getReference();
        StorageReference imagesRef = storageRef.child("Challenges/" + imageName);
        final long ONE_MEGABYTE = 1024 * 1024;
        imagesRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(bytes -> {
            b[0] = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            callback.onCallback(b[0]);
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });
    }

    public void getUserChallenge(String login, final FirestoreCallback<HashMap<String, String>> firestoreCallback) {
        final HashMap<String, String> map = new HashMap<>();
        Log.d("TEEEEEEEEEEEST", "TESSSST");
        db.collection("Users").document(login).collection("Challenge")
                .get()
                .addOnCompleteListener(task -> {
                    Log.d("TEEEEEEEEEEEST", "TESSSSTeuuuhh");
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            map.put(document.getId(), document.getString("Etat"));
                        }
                        Log.d("Uncomplet challenge", map.toString());
                    }
                    System.out.println(task.getResult().size());
                    firestoreCallback.onCallback(map);
                });
    }

}
