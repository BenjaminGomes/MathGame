package com.bgomes.mathgame;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class ScoreDB {
	// database constants
	public static final String DB_NAME = "score.db";
	public static final int	   DB_VERSION = 1;
	
	// score table constants
	public static final String SCORE_TABLE = "score";
	
	public static final String SCORE_ID = "_id";
	public static final int	   SCORE_ID_COL = 0;
	
	public static final String SCORE_USERNAME = "user_name";
	public static final int    SCORE_USERNAME_COL = 1;
	
	public static final String SCORE_FINALSCORE = "final_score";
	public static final int    SCORE_FINALSCORE_COL = 2;
	
	public static final String SCORE_CORRECTCOUNT = "correct_count";
	public static final int    SCORE_CORRECTCOUNT_COL = 3;
	
	public static final String SCORE_WRONGCOUNT = "wrong_count";
	public static final int    SCORE_WRONGCOUNT_COL = 4;
	
	// CREATE and DROP TABLE statements
	public static final String CREATE_SCORE_TABLE =
			"CREATE TABLE "    	   + SCORE_TABLE + " (" +
			SCORE_ID	   	   + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			SCORE_USERNAME	   + " TEXT	   NOT NULL, " +	
			SCORE_FINALSCORE   + " INTEGER NOT NULL, " +
			SCORE_CORRECTCOUNT + " INTEGER NOT NULL, " +
			SCORE_WRONGCOUNT   + " INTEGER NOT NULL);";
	
	public static final String DROP_SCORE_TABLE = 
			"DROP TABLE IF EXISTS " + SCORE_TABLE;
	
	private static class DBHelper extends SQLiteOpenHelper {
		public DBHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_SCORE_TABLE);
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db,
				int oldVersion, int newVersion) {
			Log.d("Task list", "Upgrading db from version " + oldVersion +
				  " to " + newVersion);
			db.execSQL(ScoreDB.DROP_SCORE_TABLE);
			onCreate(db);
		}
	}
	
	// database and database helper objects
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    
    // constructor
    public ScoreDB(Context context) {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }
    
    // private methods
    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }
    
    private void openWriteableDB() {
        db = dbHelper.getWritableDatabase();
    }
    
    private void closeDB() {
        if (db != null)
            db.close();
    }
    
    public ArrayList<Score> getScores() {
    	ArrayList<Score> scores = new ArrayList<Score>();
    	openReadableDB();
    	Cursor c = db.query(SCORE_TABLE, null, null, null, null, null, SCORE_FINALSCORE + " DESC", "5");
		
    	while (c.moveToNext()) {
    		Score score = new Score();
    		// score.setId(c.getInt(SCORE_ID_COL));
    		score.setUserName(c.getString(SCORE_USERNAME_COL));
    		score.setFinalScore(c.getInt(SCORE_FINALSCORE_COL));
    		score.setCorrectCount(c.getInt(SCORE_CORRECTCOUNT_COL));
    		score.setWrongCount(c.getInt(SCORE_WRONGCOUNT_COL));
    		scores.add(score);
    	}
    	if (c != null)
    		c.close();
    	closeDB();
    	
    	return scores;
    }
    
    public ArrayList<Score> getScores(String limitValue) {
    	ArrayList<Score> scores = new ArrayList<Score>();
    	openReadableDB();
    	Cursor c = db.query(SCORE_TABLE, null, null, null, null, null, SCORE_FINALSCORE + " DESC", limitValue);
		
    	while (c.moveToNext()) {
    		Score score = new Score();
    		// score.setId(c.getInt(SCORE_ID_COL));
    		score.setUserName(c.getString(SCORE_USERNAME_COL));
    		score.setFinalScore(c.getInt(SCORE_FINALSCORE_COL));
    		score.setCorrectCount(c.getInt(SCORE_CORRECTCOUNT_COL));
    		score.setWrongCount(c.getInt(SCORE_WRONGCOUNT_COL));
    		scores.add(score);
    	}
    	if (c != null)
    		c.close();
    	closeDB();
    	
    	return scores;
    }
    
    /*
     * see page 447 public Task getTask(int id) to be able to retrieve one row from DB
     * see page 448-449 to see how to get data from a cursor
     */
    public long insertScore(Score score) {
    	ContentValues cv = new ContentValues();
    	cv.put(SCORE_USERNAME, score.getUserName());
    	cv.put(SCORE_FINALSCORE, score.getFinalScore());
    	cv.put(SCORE_CORRECTCOUNT, score.getCorrectCount());
    	cv.put(SCORE_WRONGCOUNT, score.getWrongCount());
    	
    	this.openWriteableDB();
    	long rowID = db.insert(SCORE_TABLE, null, cv);
    	return rowID;
    }
    
    /*
     * There shouldn't be a reason to modify a row, but see page 451 updateTask if needed
     */
    /*
     * The following will delete a row from the database by accepting the ID of the high score you wish
     * to delete and returns an int value for the count of deleted rows. (success = 1, otherwise 0)
     */
    
    public int deleteScore(long id) {
    	String where = SCORE_ID + "= ?";
    	String[] whereArgs = { String.valueOf(id) };
    	
    	this.openWriteableDB();
    	int rowCount = db.delete(SCORE_TABLE, where, whereArgs);
    	this.closeDB();
    	
    	return rowCount;
    }
    
    public int getLastId() {
    	/*
    	 * By creating lastId at MAX_VALUE it allows user to play game + 2 billion before
    	 * there would be a problem finding the lastId
    	 */
    	int lastId = Integer.MAX_VALUE;
    	
    	ArrayList<Score> scores = new ArrayList<Score>();
    	openReadableDB();
    	Cursor c = db.rawQuery("SELECT last_insert_rowid()", null);
		c.moveToFirst();
    	lastId = c.getInt(0);
    	if (c != null)
    		c.close();
    	closeDB();
    	
    	return lastId;
    }
    
    public int getHighScore() {
    	int highScoreId;
    	openReadableDB();
    	Cursor c = db.rawQuery("SELECT " + SCORE_ID + " FROM " + SCORE_TABLE +
    			" WHERE " + SCORE_FINALSCORE + " = (SELECT MAX(" + SCORE_FINALSCORE +
    			") FROM " + SCORE_TABLE + ")", null);
    	c.moveToFirst();
    	highScoreId = c.getInt(0);
    	if (c != null)
    		c.close();
    	closeDB();
  
    	return highScoreId;
    }
    
    public void deleteTable() {
    	openReadableDB();
    	db.execSQL("DELETE FROM " + SCORE_TABLE);
    	// TRUNCATE table may be more efficient than DELETE FROM
    	closeDB();
    }
}
