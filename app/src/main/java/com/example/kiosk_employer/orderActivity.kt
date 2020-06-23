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
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
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

            val docRef = db.collection("order").document("신수동_중국집")
            docRef.addSnapshotListener(EventListener<DocumentSnapshot> { snapshot, e ->
                if(e != null){
                    Log.w(TAG, "Listen failed.", e)
                    return@EventListener
                }
                if(snapshot != null && snapshot.exists()){
                    var order = snapshot.toObject(OrderList::class.java)
                //Log.d(TAG,order!!.toString())
                var myLinearLayout = findViewById<LinearLayout>(R.id.ll)
                myLinearLayout.removeAllViews()

                for(i in 0..order!!.orderList!!.size-1) {
                    //if(i>order!!.orderList!!.size-1) break
                    var flag = order!!.orderList!![i].flag

                    if (flag == false) {
                        var name = order!!.orderList!![i].name!!.toString()
                        var table = order!!.orderList!![i].table!!.toString()
                        var timestamp = order!!.orderList!![i].timestamp

                        //val date = timestamp.toDate()
                        var menu = order!!.orderList!![i].menu!!
                        var text: String = "------------------------------------------------------------------------\n"
                        text = text + "식당이름 : " + name + "\n"
                        text = text + "테이블 번호 : " + table + "\n"
                        text = text + "시간 : " + timestamp!!.toDate().toString() + "\n"
                        for (x in 0..menu.size - 1) {
                            var tmp = menu[x].split(",")
                            if (tmp[1].toInt() >= 1) {
                                text = text + tmp[0] + " : " + tmp[1] + "개\n"
                            }
                        }
                        text =
                            text + "------------------------------------------------------------------------"

                        var textView = TextView(this)
                        //content.setText(text)
                        textView.setText(text)
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20F)
                        var button_yes = Button(this)
                        var button_no = Button(this)

                        button_yes.setText("complete")
                        button_yes.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)

                        myLinearLayout.addView(textView)
                        myLinearLayout.addView(button_yes)


                        button_yes.setOnClickListener {
                            Toast.makeText(this, "${table}번 테이블 요리 완료 알림을 보냈습니다.", Toast.LENGTH_SHORT)
                                .show()

                            order!!.orderList!![i].flag = true
                            db.collection("order").document("신수동_중국집")
                                .set(order, SetOptions.merge())

                            db.collection("order").document("신수동_중국집")
                                .update("orderList", FieldValue.arrayRemove(order!!.orderList!![i]))



                        }
                    }
                }
            } else {
                Log.d(TAG, "Current data: null")
            }
        })

    }
}
