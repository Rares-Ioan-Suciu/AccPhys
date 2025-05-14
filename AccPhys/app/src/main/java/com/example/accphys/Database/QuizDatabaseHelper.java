package com.example.accphys.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class QuizDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "quiz.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_QUESTIONS = "questions";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_QUESTION = "question";
    public static final String COLUMN_IMAGE_PATH = "imagePath";
    public static final String COLUMN_OPTION_A = "optionA";
    public static final String COLUMN_OPTION_B = "optionB";
    public static final String COLUMN_OPTION_C = "optionC";
    public static final String COLUMN_OPTION_D = "optionD";
    public static final String COLUMN_CORRECT = "correctAnswer";

    public QuizDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public boolean isTableExists() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?",
                new String[]{TABLE_QUESTIONS});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_QUESTIONS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_QUESTION + " TEXT, " +
                COLUMN_OPTION_A + " TEXT, " +
                COLUMN_OPTION_B + " TEXT, " +
                COLUMN_OPTION_C + " TEXT, " +
                COLUMN_OPTION_D + " TEXT, " +
                COLUMN_CORRECT + " TEXT, " +
                COLUMN_IMAGE_PATH + " TEXT)";

        db.execSQL(CREATE_TABLE);
        insertSampleQuestions(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        onCreate(db);
    }

    private void insertSampleQuestions(SQLiteDatabase db) {
        insertQuestion(db, "Mechanics",
                "The unit of measure of force may be written, depending on other I.S. units, in the form:",
                "J/m", "J/m*kg", "J/kg", "J*kg/m", "A", null);

        insertQuestion(db, "Mechanics",
                "A body A acts on a body B with force FAB. Body A has mass m, and body B has mass 2m. The force FBA with which body B acts on body A is:",
                "FBA = FAB", "FBA = -FAB", "FBA = 2", "FBA = -2", "B", null);

        insertQuestion(db, "Mechanics",
                "According to Hooke's law, the elongation of an elastically deformed wire is:",
                "directly proportional to the deforming force and inversely proportional to the cross-sectional area of the wire",
                "inversely proportional to the deforming force and directly proportional to the cross-sectional area of the wire",
                "directly proportional to both the deforming force and the cross-sectional area of the wire",
                "inversely proportional to both the deforming force and the cross-sectional area of the wire",
                "A", null);

        insertQuestion(db, "Mechanics",
                "The graph opposite shows the dependence of the mechanical momentum of a body on its velocity. The mass of the body is equal to:",
                "128 kg", "8 kg", "4 kg", "2 kg", "D", "momentum_graph");

        insertQuestion(db, "Mechanics",
                "A mobile phone travels a distance d. Half of this distance is travelled at constant speed v1 = 70 cm/s and the other half with constant speed v2 = 30 cm/s. The average speed of the mobile over the whole distance is:",
                "100 cm/s", "50 cm/s", "42 cm/s", "37 cm/s", "C", null);

        insertQuestion(db, "Mechanics",
                "A rock of negligible size is thrown horizontally from a tower into the Earth's gravitational field. The interaction with air is assumed to be negligible. From the moment of throwing to the moment of touching the ground:",
                "the velocity of the stone is always in the horizontal direction",
                "the speed of the stone is always vertical",
                "the acceleration of the stone is horizontal",
                "the acceleration of the stone is vertical",
                "D", null);

        insertQuestion(db, "Mechanics",
                "Since the symbols for physical quantities are those used in physics textbooks, the relative deformation of an elongated elastic thread under the action of the deforming force F is given by the relation:",
                "Δ·F = S·E",
                "Δ = F / (E·S)",
                "Δ = F / (S·E)",
                "Δ = S / (E·F)",
                "B", null);

        insertQuestion(db, "Mechanics",
                "Since the symbols for physical quantities are those used in physics textbooks, the unit of measurement of the physical quantity expressed by the product F·v is:",
                "J", "W", "W·s⁻¹", "J·s", "B", null);

        insertQuestion(db, "Mechanics",
                "A body with mass m = 300g, considered as a point, is dropped from a height H = 24m above ground level. The interaction with air is assumed to be negligible. The mechanical work done by the weight of the body from the moment of release to the moment the body touches the ground is:",
                "27 J", "48 J", "72 J", "90 J", "C", null);

        insertQuestion(db, "Mechanics",
                "A crate of mass m = 12kg is moved on a horizontal surface under the action of a constant horizontal force. The time-dependence of the acceleration of the crate is plotted in the adjacent graph. The modulus of the resistive force acting on the crate is:",
                "24 N", "15 N", "12 N", "2 N", "A", "crate_acceleration_graph");

        insertQuestion(db, "Mechanics",
                "The SI unit of mechanical power can be written in the form:",
                "N/m", "N·m", "J/s", "J·s", "C", null);

        insertQuestion(db, "Mechanics",
                "The instantaneous velocity vector is always oriented:",
                "perpendicular to the acceleration vector",
                "parallel to the acceleration vector",
                "perpendicular to the trajectory",
                "tangent to the trajectory", "D", null);

        insertQuestion(db, "Mechanics",
                "The gravitational potential energy of a body of mass m, at height h above ground level (considering ground level as zero potential energy) is expressed by the relation:",
                "Ep = ½mgh", "Ep = -½mgh", "Ep = mgh", "Ep = -mgh", "C", null);

        insertQuestion(db, "Mechanics",
                "Two mobiles move in the same direction. The graph in the adjacent figure shows the time dependence of the co-ordinates of the two movers. The ratio between the velocity of mobile M1 and the velocity of mobile M2 is equal to:",
                "1", "2", "3", "4", "C", "velocity_ratio_graph");

        insertQuestion(db, "Mechanics",
                "A spring has elastic constant k = 50 N/m and is initially undeformed. The mechanical work done by the elastic force in elongating the spring by Δ = 10 cm is:",
                "-0.25 J", "-2.5 J", "-250 J", "-2500 J", "A", null);

        insertQuestion(db, "Mechanics",
                "A body is travelling rectilinearly under the action of a constant resultant force, and the velocity of the body increases in time. In this case the acceleration vector:",
                "has the same direction as the momentary velocity vector and opposite its direction",
                "has the same direction and sense as the momentary velocity vector",
                "is perpendicular to the momentary velocity vector",
                "is perpendicular to the mean velocity vector", "B", null);

        insertQuestion(db, "Mechanics",
                "An elastic thread having elastic constant k and length l₀ in the undeformed state is elongated by Δ. The modulus of the elastic force is expressed by the relation:",
                "F = k·Δ", "F = (1-k)·Δ", "F = (1-k)·l₀", "F = k·l₀", "A", null);

        insertQuestion(db, "Mechanics",
                "The SI unit of measurement of the physical quantity expressed by the product of force and velocity is:",
                "m", "N", "W", "J", "C", null);

        insertQuestion(db, "Mechanics",
                "The time dependence of the mechanical power developed by a force acting on a body is illustrated in the adjacent figure. The mechanical work done by this force on the body in the time interval t ∈ [4s, 8s] has value:",
                "10 J", "20 J", "40 J", "80 J", "D", "power_vs_time_graph");

        insertQuestion(db, "Mechanics",
                "A trolley of mass m = 2 kg is travelling rectilinearly, with negligible friction, in a horizontal plane, with constant speed v = 2 m/s. At some point the trolley strikes the free end of a horizontal, undeformed spring. The other end of the spring is fixed to a vertical, immovable wall. The maximum compression of the spring is x = 10 cm. The spring constant of the spring is:",
                "200 N/m", "400 N/m", "800 N/m", "1000 N/m", "C", null);

    }

    private void insertQuestion(SQLiteDatabase db, String category, String question,
                                String a, String b, String c, String d, String correct, String imagePath) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_QUESTION, question);
        values.put(COLUMN_OPTION_A, a);
        values.put(COLUMN_OPTION_B, b);
        values.put(COLUMN_OPTION_C, c);
        values.put(COLUMN_OPTION_D, d);
        values.put(COLUMN_CORRECT, correct);
        values.put(COLUMN_IMAGE_PATH, imagePath);
        db.insert(TABLE_QUESTIONS, null, values);
    }


    public List<Question> getRandomQuestions(int limit) {
        List<Question> questions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_QUESTIONS + " ORDER BY RANDOM() LIMIT ?", new String[]{String.valueOf(limit)});

        if (cursor.moveToFirst()) {
            do {
                Question q = new Question();
                q.id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                q.category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY));
                q.questionText = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUESTION));
                q.optionA = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OPTION_A));
                q.optionB = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OPTION_B));
                q.optionC = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OPTION_C));
                q.optionD = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OPTION_D));
                q.correctAnswer = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CORRECT));
                q.imagePath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_PATH));

                questions.add(q);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return questions;
    }


}
