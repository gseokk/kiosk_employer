package com.example.kiosk_employer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 테이블 관리로 넘어감
        table_mn_btn.setOnClickListener{
            val intent= Intent(this, tableActivity::class.java)
            startActivity(intent)
        }
        // 주문 관리로 넘어감
        order_mn_btn.setOnClickListener{
            val intent= Intent(this, orderActivity::class.java)
            startActivity(intent)
        }
    }
}
