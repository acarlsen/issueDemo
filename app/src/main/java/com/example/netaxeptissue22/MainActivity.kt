package com.example.netaxeptissue22

import android.net.Uri
import android.os.Bundle
import android.util.Pair
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.example.netaceptissue22.databinding.ActivityMainBinding
import eu.nets.pia.PiaSDK
import eu.nets.pia.ProcessResult
import eu.nets.pia.card.*
import eu.nets.pia.wallets.PaymentProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val cardPaymentActivityLauncher: ActivityResultLauncher<CardProcessActivityLauncherInput> =
        registerForActivityResult(
            CardProcessActivityResultContract.invoke(), // why is invoke necessary?
            this::transactionCompleteResult
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener {
            startNetaxeptCardPayment()
        }
    }

    private fun transactionCompleteResult(result: ProcessResult<eu.nets.pia.CardProcessError>) {
        // TODO: I want to handle result here, but cannot compile with eu.nets.pia.CardProcessError
    }

    private fun startNetaxeptCardPayment() {
           val cardProcess = PaymentProcess.cardPayment(
            Pair("merchantId", PiaSDK.Environment.TEST),
            emptySet(),
            Pair(1000, "DKK"),
            object : CardPaymentRegistration {
                override fun registerPayment(shouldStoreCard: Boolean, callbackWithTransaction: TransactionCallback) {
                    callbackWithTransaction.successWithTransactionIDAndRedirectURL(
                        "transactionID",
                        Uri.parse("https://payment.url.com")
                    )
                }
            }
        )

        PiaSDK.startCardProcessActivity(
            cardPaymentActivityLauncher,
            cardProcess,
            false
        )
    }
}
