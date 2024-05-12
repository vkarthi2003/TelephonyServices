package com.example.telephonyservics

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private lateinit var nwop:EditText
    private lateinit var phntype:EditText
    private lateinit var nwiso:EditText
    private lateinit var simiso:EditText
    private lateinit var swversion:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        nwop = findViewById(R.id.nwop)
        phntype = findViewById(R.id.phntype)
        nwiso = findViewById(R.id.nwiso)
        simiso = findViewById(R.id.simiso)
        swversion = findViewById(R.id.swversion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btn:Button=findViewById(R.id.btn)
        btn.setOnClickListener(){
            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_PHONE_STATE),
                    111
                )
            }
            else{
                getinfo()
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getinfo()
        }
    }

    private fun getinfo(){
        val tm:TelephonyManager=getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        nwop.setText(tm.networkOperatorName)
        var phonetype:Int=tm.phoneType
        when(phonetype){
            TelephonyManager.PHONE_TYPE_CDMA->phntype.setText("CDMA")
            TelephonyManager.PHONE_TYPE_GSM->phntype.setText("GSM")
            TelephonyManager.PHONE_TYPE_NONE->phntype.setText("NONE")
        }
        nwiso.setText(tm.networkCountryIso)
        simiso.setText(tm.simCountryIso)
        swversion.setText(tm.deviceSoftwareVersion)
    }

}