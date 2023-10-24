import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ozturksahinyetisir.myshop.data.model.ShopModel

class DatabaseManager (context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        //Creating the database table.
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + PRICE_COL + " LONG,"
                + DESCRIPTION_COL + " TEXT,"
                + STOCK_COL + " INTEGER)")
        db.execSQL(query)
    }

    // Function to add a new product
    fun addNewProducts(
        productName: String?,
        productPrice: Long?,
        productDesc: String?,
        productStock: Int?
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME_COL, productName)
        values.put(PRICE_COL, productPrice)
        values.put(DESCRIPTION_COL, productDesc)
        values.put(STOCK_COL, productStock)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "myshopdb"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "myproducts"
        private const val ID_COL = "id"
        private const val NAME_COL = "name"
        private const val PRICE_COL = "price"
        private const val DESCRIPTION_COL = "description"
        private const val STOCK_COL = "stocks"
    }

    // Function to read products and return them in a list
    fun readProducts(): ArrayList<ShopModel>? {
        val db = this.readableDatabase

        val cur: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        val productModelArrayLit: ArrayList<ShopModel> = ArrayList()

        if (cur.moveToFirst()) {
            do {

                productModelArrayLit.add(
                    ShopModel(
                        cur.getString(1),
                        cur.getLong(2),
                        cur.getInt(4),
                        cur.getString(3)
                    )
                )
            } while (cur.moveToNext())
        }

        cur.close()
        return productModelArrayLit
    }

    fun deleteProduct(productName: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "name=?", arrayOf(productName))
        db.close()
    }

    fun updateProduct(
        originalProductName: String, productName: String?, productPrice: String?,
        productStock: String?, productDesc: String?
    ) {
        val db = this.writableDatabase
        val values = ContentValues()

         values.put(NAME_COL, productName)
        values.put(PRICE_COL, productPrice)
        values.put(DESCRIPTION_COL, productDesc)
        values.put(STOCK_COL, productStock)

       db.update(TABLE_NAME, values, "name=?", arrayOf(originalProductName))
        db.close()
    }
}
