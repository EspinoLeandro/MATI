package Firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class db {

    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference myRef = database.getReference(Referencias.MATI_REFERENCE);

    public db() {
        database.setPersistenceEnabled(true);
    }
}
