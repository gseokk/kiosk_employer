package com.example.kiosk_employer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class orderActivity : AppCompatActivity() {
    val db=FirebaseFirestore.getInstance()
    val TAG="FireStore"
    var BeforeOrder: Order? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        var content=findViewById<TextView>(R.id.order)

        val docRef = db.collection("order").document("중국집")
        docRef.addSnapshotListener(EventListener<DocumentSnapshot> { snapshot, e ->
            if(e != null){
                Log.w(TAG, "Listen failed.", e)
                return@EventListener
            }
            if(snapshot != null && snapshot.exists()){
                //var order = snapshot.toObject(Order::class.java)
                //val temp =order!!.table!!
                //var mData = snapshot?.data!!["table"].toString()
                //var mData=snapshot?.data!!.toString()
                //content.setText(temp)
                content.setText("a")

            } else {
                Log.d(TAG, "Current data: null")
            }
        })

    }
}
