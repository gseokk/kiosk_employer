package com.example.kiosk_employer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.type.Date
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class orderActivity : AppCompatActivity() {
    val db=FirebaseFirestore.getInstance()
    val TAG="FireStore"
    //var BeforeOrder: Order? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        //var content=findViewById<TextView>(R.id.order)

        val docRef = db.collection("order").document("중국집")
        docRef.addSnapshotListener(EventListener<DocumentSnapshot> { snapshot, e ->
            if(e != null){
                Log.w(TAG, "Listen failed.", e)
                return@EventListener
            }
            if(snapshot != null && snapshot.exists()){
                var order = snapshot.toObject(Order::class.java)

                var name=order!!.name!!.toString()
                var table= order!!.table!!.toString()
                var timestamp=order!!.timestamp

                //val date = timestamp.toDate()
                var menu = order!!.menu!!
                var text:String =""
                text=text+"식당이름 : " + name + "\n"
                text=text+"테이블 번호 : " + table + "\n"
                text=text+"시간 : " + timestamp!!.toDate().toString() + "\n"
                for(x in 0..menu.size-1){
                    var tmp=menu[x].split(",")
                    text= text+ tmp[0] +" : "+ tmp[1]+"개\n"
                }
                text=text+"------------------------------------------------------------------------"

                var textView=TextView(this)
                //content.setText(text)
                textView.setText(text)
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20F)
                var button_yes= Button(this)
                var button_no= Button(this)

                var myLinearLayout_1=findViewById<LinearLayout>(R.id.for_text)
                var myLinearLayout_2=findViewById<LinearLayout>(R.id.for_button)
                myLinearLayout_1.removeAllViews()
                myLinearLayout_2.removeAllViews()

                button_yes.setText("yes")
                button_yes.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)

                button_no.setText("no")
                button_no.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)

                myLinearLayout_1.addView(textView)
                myLinearLayout_2.addView(button_yes)
                myLinearLayout_2.addView(button_no)


                button_yes.setOnClickListener {
                    Toast.makeText(this,"${table}번 테이블 주문을 승인했습니다.", Toast.LENGTH_SHORT).show()

                    order!!.flag= true
                    db.collection("order").document("중국집").set(order, SetOptions.merge())

                }
                button_no.setOnClickListener {
                    Toast.makeText(this,"${table}번 테이블 주문을 거절했습니다.", Toast.LENGTH_SHORT).show()

                    order!!.flag= false
                    db.collection("order").document("중국집").set(order, SetOptions.merge())

                }

            } else {
                Log.d(TAG, "Current data: null")
            }
        })

    }
}
