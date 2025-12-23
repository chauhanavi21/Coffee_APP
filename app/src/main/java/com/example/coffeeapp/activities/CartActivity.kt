package com.uilover.project262.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.uilover.project262.Helper.ChangeNumberItemsListener
import com.uilover.project262.Helper.ManagmentCart
import com.uilover.project262.adapters.CartAdapter
import com.uilover.project262.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    lateinit var binding: ActivityCartBinding
    lateinit var managmentCart: ManagmentCart
    private var tax: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)

        calculateCart()
        setVariable()
        initCartList()
    }

    private fun initCartList() {
        binding.apply {
            listView.layoutManager =
                LinearLayoutManager(
                    this@CartActivity, LinearLayoutManager.VERTICAL,
                    false
                )
            listView.adapter = CartAdapter(
                managmentCart.getListCart(),
                this@CartActivity,
                object : ChangeNumberItemsListener {
                    override fun onChanged() {
                        calculateCart()
                    }

                }
            )
        }
    }

    private fun setVariable() {
        binding.backBtn.setOnClickListener { finish() }
    }

    private fun calculateCart() {
        val percentTax = 0.02
        val delivery = 10.0
        tax = ((managmentCart.getTotalFee() * percentTax) * 100) / 100
        val total = ((managmentCart.getTotalFee() + tax + delivery) * 100) / 100
        val itemTotal = (managmentCart.getTotalFee() * 100) / 100
        binding.apply {
            totalFeeTxt.text = "$$itemTotal"
            totalTaxTxt.text = "$$tax"
            deliveryTxt.text = "$$delivery"
            totalTxt.text = "$$total"
        }
    }
}