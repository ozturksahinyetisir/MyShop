package com.ozturksahinyetisir.myshop.presentation.update

import DatabaseManager
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ozturksahinyetisir.myshop.presentation.home.ViewProducts
import com.ozturksahinyetisir.myshop.presentation.update.ui.theme.MyShopTheme

class UpdateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyShopTheme {
                Column() {
                    updateDataToDatabase(
                        context = LocalContext.current,
                        pName = intent.getStringExtra("productName"),
                        pPrice = intent.getStringExtra("productPrice"),
                        pStock = intent.getStringExtra("productStock"),
                        pDesc = intent.getStringExtra("productDesc")
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun updateDataToDatabase(
    context: Context, pName: String?, pStock: String?, pPrice: String?, pDesc: String?
) {

    val activity = context as Activity
    var productName = remember {
        mutableStateOf(pName)
    }
    val productPrice = remember {
        mutableStateOf(pPrice)
    }
    val productStock = remember {
        mutableStateOf(pStock)
    }
    val productDesc = remember {
        mutableStateOf(pDesc)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        var dbManager: DatabaseManager = DatabaseManager(context)

        Text(
            text = "Ozturk Shop",
            color = Color.Magenta, fontSize = 20.sp, fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))


        TextField(

            value = productName.value!!,
            onValueChange = { productName.value = it },
            placeholder = { Text(text = "Ürün adı giriniz.") },

            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = productPrice.value.toString()!!,
            onValueChange = { productPrice.value = it },
            placeholder = { Text(text = "Ürün fiyatı giriniz.") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = productStock.value.toString()!!,
            onValueChange = { productStock.value = it },
            placeholder = { Text(text = "Ürün stoğu giriniz.") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = productDesc.value!!,
            onValueChange = { productDesc.value = it },
            placeholder = { Text(text = "Ürün açıklaması giriniz.") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(15.dp))

        Button(onClick = {
            dbManager.updateProduct(
                originalProductName = pName!!,
                productName = productName.value,
                productDesc = productDesc.value,
                productStock = productStock.value,
                productPrice = productPrice.value
            )
            Toast.makeText(context, "Ürün güncellendi..", Toast.LENGTH_SHORT).show()
            val i = Intent(context, ViewProducts::class.java)
            context.startActivity(i)
        }) {
            Text(text = "Ürünü Güncelle", color = Color.White)
        }
    }
}