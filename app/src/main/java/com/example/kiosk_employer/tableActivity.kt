package com.example.kiosk_employer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.lang.reflect.Type

class tableActivity : AppCompatActivity() {
    val db=FirebaseFirestore.getInstance()
    val TAG="Firestore"
    var beforeStore: Store? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)

        val docRef = db.collection("store").document("신수동_중국집")
        docRef.addSnapshotListener(EventListener<DocumentSnapshot> { snapshot, e ->
            if(e != null){
                Log.w(TAG, "Listen failed.", e)
                return@EventListener
            }
            if(snapshot != null && snapshot.exists()){
                //Log.d(TAG, "Current data: " + snapshot.data)
                val store = snapshot.toObject(Store::class.java)

                var mData = snapshot?.data!!["table"].toString()
                mData=mData.replace(" ","")
                mData=mData.replace("[","")
                mData=mData.replace("{","")
                mData=mData.replace("]","")
                mData=mData.replace("}","")
                val arr=mData.split(",")
                var myLinearLayout=findViewById<LinearLayout>(R.id.ll)
                myLinearLayout.removeAllViews()
                for(x in 0..arr.size-2 step 2) {
                    var table_state:String=""

                    if(arr[x+1].contains("true")){
                        table_state=table_state+"table #"+x/2+": □ (available)"
                    }
                    else{
                        table_state=table_state+"table #"+x/2+": ■ (not available)"
                    }
                    var textView=TextView(this)
                    var button=Button(this)

                    button.setId(x/2)
                    button.setText("reset")
                    button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)

                    textView.setText(table_state)
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25F)

                    button.setOnClickListener {
                        Toast.makeText(this,"${button.id}번 테이블을 초기화시켰습니다.",Toast.LENGTH_SHORT).show()

                        store!!.table!![button.id].availability = true
                        // TODO asdasfasffsaasf
                        db.collection("store").document("신수동_중국집").set(store!!, SetOptions.merge())

                    }
                    myLinearLayout.addView(textView)
                    myLinearLayout.addView(button)

                }

            } else {
                Log.d(TAG, "Current data: null")
            }
        })
    }
}
