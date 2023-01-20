package com.example.lyricsapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities={Cocktail.class},version=1,exportSchema = false)
public abstract class CocktailDatabase extends RoomDatabase {
    private static CocktailDatabase databaseInstance;
    static final ExecutorService databaseWriteExecutor= Executors.newSingleThreadExecutor();
    public abstract CocktailDao cocktailDao();
    static CocktailDatabase getDatabase(final Context context){
        if(databaseInstance==null){
            databaseInstance= Room.databaseBuilder(context.getApplicationContext(), CocktailDatabase.class,"cocktail_database").addCallback(roomDatabaseCallback).build();
        }
        return databaseInstance;
    }
    private static final RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                CocktailDao dao = databaseInstance.cocktailDao();
                String receipe ="Musisz iść do pracy\n" +
                        "Musisz zrobić kwit\n" +
                        "Musisz iść do pracy\n" +
                        "Musisz dobrze żyć\n" +
                        "Musisz iść do pracy\n" +
                        "Musisz zrobić kwit\n" +
                        "Musisz iść do pracy\n" +
                        "Musisz dobrze żyć\n" +
                        "\n" +
                        "Nie obchodzi mnie ten biznes\n" +
                        "Gdy normalnie żyję, wpadam na mieliznę\n" +
                        "Budżetowe gwiazdy rocka i to przykre\n" +
                        "Chcę postać w blasku zanim w końcu zniknę\n" +
                        "Młody Sonic, żyję bardzo szybko\n" +
                        "Chcę by moi ludzie mieli wszystko\n" +
                        "Chcę by moi ludzie mieli przyszłość\n" +
                        "Chcę by moi ludzie mieli wszystko\n" +
                        "\n"+
                        "Musiałem nie hamować, by na zawsze żyć\n" +
                        "Musiałem nie wybaczać, by nie zgubić sił\n" +
                        "I wiem, że Ty tu jesteś (I wiem, że Ty tu jesteś)\n" +
                        "To co inne jest nieważne (To co inne jest nieważne)\n" +
                        "Wypełniać te kieszenie (Wypełniać te kieszenie)\n" +
                        "Dla niej to nie jest ważne\n" +
                        "Wiatr nie zwolni w stronę naszych ran\n" +
                        "By zobaczyć więcej, cierpię cały czas\n" +
                        "Chciałbym, byśmy mogli poczuć chociaż raz\n" +
                        "Pełną wolność, jakby okraść cały bank\n" +
                        "\n" +
                        "Nie obchodzi mnie ten biznes\n" +
                        "Budżetowe gwiazdy rocka i to przykre\n" +
                        "Chcę postać w blasku zanim w końcu zniknę\n" +
                        "\n" +
                        "Nie obchodzi mnie ten biznes\n" +
                        "Gdy normalnie żyję, wpadam na mieliznę\n" +
                        "Budżetowe gwiazdy rocka i to przykre\n" +
                        "Chcę postać w blasku zanim w końcu zniknę\n" +
                        "Młody Sonic, żyję bardzo szybko\n" +
                        "Chcę by moi ludzie mieli wszystko\n" +
                        "Chcę by moi ludzie mieli przyszłość\n" +
                        "Chcę by moi ludzie mieli wszystko";
                Cocktail cocktail = new Cocktail("Ludzie","Syndrom Paryski",receipe,"https://www.youtube.com/watch?v=ZYWOzw25ODc");
                dao.insert(cocktail);
                receipe ="In a world of fun and fantasy\n" +
                        "And ever changing views\n" +
                        "And computer terminology\n" +
                        "Commodore is news\n" +
                        "\n" +
                        "Are you keeping up with the Commodore?\n" +
                        "'Cause the Commodore is keepin up with you!\n" +
                        "Are you keeping up with the Commodore?\n" +
                        "'Cause the Commodore is keepin up with you!\n" +
                        "Are you keeping up?\n" +
                        "'Cause the Commodore is keepin up with you!\n" +
                        "Are you keeping up?\n" +
                        "'Cause the Commodore is keepin up with you!\n" +
                        "\n" +
                        "In a world of high technology\n" +
                        "And ever changing moods\n" +
                        "In a world that's full of make believe\n" +
                        "And changing attitudes\n" +
                        "Are you keeping up with the Commodore?\n" +
                        "'Cause the Commodore is keepin up with you!\n" +
                        "\n" +
                        "Are you keeping up with the Commodore?\n" +
                        "'Cause the Commodore is keepin up with you!\n" +
                        "Are you keeping up with the Commodore?\n" +
                        "'Cause the Commodore is keepin up with you!";
                cocktail = new Cocktail("Keeping Up With The Commodore","Woxy",receipe,"https://www.youtube.com/watch?v=7E6qR-1ASMA");
                dao.insert(cocktail);
            });
        }
    };
}
