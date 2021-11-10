package com.example.emg_measurer.activities

import android.annotation.SuppressLint
import android.graphics.drawable.AnimatedVectorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.method.ScrollingMovementMethod
import android.widget.*
import com.example.emg_measurer.Fragments.Principal
import com.example.emg_measurer.R
import com.google.android.material.snackbar.Snackbar
import ingenieria.jhr.bluetoothjhr.BluetoothJhr
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import kotlin.concurrent.thread

class MainActivity3 : AppCompatActivity() {
    lateinit var blue: BluetoothJhr
    var initConexion = false
    var offHilo = false
    var isChecked = false

    lateinit var pantallaRx: TextView
    lateinit var usuarioTxt: TextView //textView
    lateinit var modoDispoTxt: TextView //textView4
    lateinit var consolaInput: EditText //textoComando
    lateinit var buttonSend : Button
    lateinit var switchMode : Switch
    lateinit var playButton : ImageButton //imageButton

    var modeSistem : Boolean = false
    var modeButton : Boolean = false

    var nombreUsuario :String = Principal.USUARIO

    var msg1 : String = '$' + "un#"
    var msg2 : String = '$' + "GO#"
    var msgStop : String = '$' + "ST#"


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        pantallaRx = findViewById(R.id.textView6)
        pantallaRx.movementMethod = ScrollingMovementMethod()
        buttonSend = findViewById(R.id.button2)
        switchMode = findViewById(R.id.switchMode)
        playButton = findViewById(R.id.imageButton)
        consolaInput = findViewById(R.id.textoComando)
        usuarioTxt = findViewById(R.id.textView)
        modoDispoTxt = findViewById(R.id.textView4)

        blue = BluetoothJhr(this, BTActivity::class.java)
        //si se pierde conexion no sale sino que avisa con un mensaje  error
        blue.exitErrorOk(true)
        //mensaje conectado
        toast("Conexion establecida")
        blue.mensajeConexion("Conectado")

        //mensaje de error
        blue.mensajeErrorTx("No se pudo conectar con el dispositivo")

        var aux: String = usuarioTxt.text as String
        usuarioTxt.text = aux + nombreUsuario

        playButton.setOnClickListener {
            if (isChecked) {
                checkToClose()
            }
            else {
                closeToCheck()
            }
            isChecked = !isChecked
        }

        switchMode.setOnClickListener{
            modeSistem = switchMode.isChecked
            if ( switchMode.isChecked)
                modoDispoTxt.setText("Modo SingleShot")
            else{
                modoDispoTxt.setText("Modo Continuo")
            }
        }

        buttonSend.setOnClickListener {
            val comando = consolaInput.text.toString()
            writeConsole(comando,true)
            blue.mTx(comando)
            consolaInput.text.clear()
        }

        thread(start = true){
            while (!initConexion && !offHilo){
                Thread.sleep(500)
            }
            while (!offHilo){
                //Recibo el mensaje
                var mensaje = blue.mRx()

                if (mensaje!="error" && mensaje.isNotEmpty()){
                    writeConsole(mensaje,false)
                }
                Thread.sleep(100)
            }
        }

    }

    private fun closeToCheck() {
        playButton.setImageResource(R.drawable.pause_to_play)
        val avdClosetoCheck :AnimatedVectorDrawable = playButton.drawable as AnimatedVectorDrawable
        avdClosetoCheck.start()
        if (!modeSistem){
            blue.mTx(msgStop)
            writeConsole(msgStop,true)
        }
    }

    private fun checkToClose() {
        if(modeSistem) { //Modo singleshot
            blue.mTx(msg1)
            writeConsole(msg1,true)
            setTimer()
        }
        else{ //Modo continuo
            blue.mTx(msg2)
            writeConsole(msg2,true)
        }
        playButton.setImageResource(R.drawable.play_to_pause)
        val avdChecktoClose :AnimatedVectorDrawable = playButton.drawable as AnimatedVectorDrawable
        avdChecktoClose.start()
    }

    private fun setTimer() {
        object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }
            override fun onFinish() {
                closeToCheck()
            }
        }.start()
    }

    private fun writeConsole(msg: String, tx: Boolean) {
        val text : String = pantallaRx.text.toString()
        pantallaRx.post {
            if(tx)
                pantallaRx.text = text + "Comando enviado: $msg" + "\n"
            else
                pantallaRx.text = text + "Comando recibido: " + msg + "\n"
        }
    }

    override fun onResume() {
        initConexion =  blue.conectaBluetooth()
        super.onResume()
    }
    //
    override fun onPause() {
        offHilo = true
        blue.exitConexion()
        super.onPause()
    }
}