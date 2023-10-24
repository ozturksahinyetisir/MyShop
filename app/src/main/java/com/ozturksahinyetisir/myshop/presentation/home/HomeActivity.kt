package com.ozturksahinyetisir.myshop.presentation.home

import DatabaseManager
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.twotone.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ozturksahinyetisir.myshop.R
import com.ozturksahinyetisir.myshop.montserratFontFamily
import com.ozturksahinyetisir.myshop.presentation.ui.theme.MyShopTheme

class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyShopTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                        addDataToDatabase(LocalContext.current)

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addDataToDatabase(context: Context) {
    val activity = context as Activity

    val productName = remember { mutableStateOf(TextFieldValue()) }
    val productPrice = remember { mutableStateOf(TextFieldValue()) }
    val productStock = remember { mutableStateOf(TextFieldValue()) }
    val productDesc = remember { mutableStateOf(TextFieldValue()) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        var dbManager: DatabaseManager = DatabaseManager(context)

        Image(
            painter = painterResource(id = R.drawable.market),
            contentDescription = "Resim Açıklaması",
            modifier = Modifier.size(100.dp))
        Text(
            text = "Ozturk Shop",
            color = Color.Magenta, fontFamily = montserratFontFamily, fontSize = 20.sp, fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = productName.value,
            onValueChange = { productName.value = it },
            placeholder = { Text(text = "Ürün Adı Giriniz", fontFamily = montserratFontFamily) },
            modifier = Modifier
                .fillMaxWidth(),trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Name",
                    tint = Color.Gray
                )
            },
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = productPrice.value,
            onValueChange = { productPrice.value = it },
            placeholder = { Text(text = "Ürün Fiyatı Giriniz.", fontFamily = montserratFontFamily) },
            modifier = Modifier
                .fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Fiyat",
                    tint = Color.Gray
                )
            },
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(20.dp))


        TextField(
            value = productStock.value,
            onValueChange = { productStock.value = it },
            placeholder = { Text(text = "Ürün Stok Adeti Giriniz", fontFamily = montserratFontFamily) },
            modifier = Modifier
                .fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = Icons.TwoTone.Menu,
                    contentDescription = "Stock",
                    tint = Color.Gray
                )
            },
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = productDesc.value,
            onValueChange = { productDesc.value = it },
            placeholder = { Text(text = "Ürün Açıklaması Giriniz.", fontFamily = montserratFontFamily) },
            modifier = Modifier
                .fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Açıklama",
                    tint = Color.Gray
                )
            },
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(15.dp))

        Button(onClick = {
            val priceText = productPrice.value.text
            val stockText = productStock.value.text

            val price: Long = try {
                priceText.toLong()
            } catch (e: NumberFormatException) {
                Toast.makeText(context, "Geçersiz fiyat formatı", Toast.LENGTH_SHORT).show()
                return@Button
            }

            val stock: Int = try {
                stockText.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(context, "Geçersiz stok miktarı formatı", Toast.LENGTH_SHORT).show()
                return@Button
            }
            dbManager.addNewProducts(
                productName.value.text,
                price,
                productDesc.value.text,
                stock
            )
            Toast.makeText(context, "Ürün Veri Tabanına Eklendi.", Toast.LENGTH_SHORT).show()
        }) {
            Text(text = "Ürünü Kaydet", color = Color.White)
        }

        Spacer(modifier = Modifier.height(15.dp))

        Button(onClick = {
            val intent = Intent(context,ViewProducts::class.java)
            context.startActivity(intent)
        }) {
            Text(text = "Ürünleri İncele", color = Color.White)
        }
    }
}








