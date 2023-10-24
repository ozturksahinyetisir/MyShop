package com.ozturksahinyetisir.myshop.presentation.home

import DatabaseManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ozturksahinyetisir.myshop.data.model.ShopModel
import com.ozturksahinyetisir.myshop.montserratFontFamily
import com.ozturksahinyetisir.myshop.presentation.home.ui.theme.MyShopTheme
import com.ozturksahinyetisir.myshop.presentation.update.UpdateActivity


class ViewProducts : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyShopTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        //AdminPanel()
                        Spacer(modifier = Modifier.height(6.dp))
                        readDataFromDatabase(LocalContext.current)
                        MyScreen()
                    }
                }
            }
        }
    }
}

lateinit var productList: List<ShopModel>
fun getList(context: Context) {
    productList = ArrayList<ShopModel>()
    val databaseManager: DatabaseManager = DatabaseManager(context);
    productList = databaseManager.readProducts()!!
}
@Composable
fun readDataFromDatabase(context: Context) {
    getList(context)
    ShowList(context = context)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowList(context: Context) {

    LazyColumn {
        itemsIndexed(productList) { index, item ->
            Card(
                onClick = {
                    val i = Intent(context, UpdateActivity::class.java)
                    i.putExtra("productName", productList[index].productName)
                    i.putExtra("productPrice", productList[index].productPrice.toString())
                    i.putExtra("productStock", productList[index].productStock.toString())
                    i.putExtra("productDesc", productList[index].productDesc)
                    context.startActivity(i)
                },
                modifier = Modifier.padding(8.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row {
                        Text(
                            text = productList[index].productName,
                            modifier = Modifier.padding(4.dp),
                            color = Color.Blue,
                            textAlign = TextAlign.Center,
                            fontFamily = montserratFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = "X",
                                color = Color.Black,
                                fontSize = 16.sp,
                                modifier = Modifier.clickable {
                                    var dbManager: DatabaseManager = DatabaseManager(context)
                                    dbManager.deleteProduct(productName = productList[index].productName)
                                    val i = Intent(context, ViewProducts::class.java)
                                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                                    context.startActivity(i)
                                }
                            )
                        }
                    }
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Fiyati : " + productList[index].productPrice,
                        modifier = Modifier.padding(4.dp),
                        color = Color(0xFF136804),
                        textAlign = TextAlign.Center,
                        fontFamily = montserratFontFamily,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Stok : " + productList[index].productStock,
                        modifier = Modifier.padding(4.dp),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontFamily = montserratFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Açıklama : " + productList[index].productDesc,
                        modifier = Modifier.padding(4.dp),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontFamily = montserratFontFamily,
                    )

                }
            }
        }
    }

@Composable
fun AdminPanel() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        var context = LocalContext.current
        Button(onClick = {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }, colors = ButtonDefaults.buttonColors(Color.Blue)) {
            Text("Admin Paneli Görüntüle")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScreen() {
    var showDialog by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Admin Paneline Git!")
        }
        if (showDialog) {
            Dialog(
                onDismissRequest = { showDialog = false },
                properties = DialogProperties(dismissOnClickOutside = true)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Admin Paneli için şifre girin.")
                        IconButton(onClick = { showDialog = false }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = null)
                        }
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            var context = LocalContext.current
                            TextField(
                                value = password,
                                onValueChange = {
                                    if (it.length <= 4) { // The maximum length of the password can be up to 4.
                                        password = it
                                        password = it.replace(" ", "") //Suppresses line breaks
                                    }
                                },
                                label = { Text("Şifre", fontFamily = montserratFontFamily,) },
                                visualTransformation = PasswordVisualTransformation(),

                                )
                            Button(onClick = {
                                if(password.equals("7777")) {
                                    Toast.makeText(context,"Giriş Başarılı!",Toast.LENGTH_SHORT).show()
                                    val intent = Intent(context, HomeActivity::class.java)
                                    context.startActivity(intent)
                                }else{
                                    Toast.makeText(context,"Şifre Hatalı!",Toast.LENGTH_SHORT).show()
                                }
                            }, colors = ButtonDefaults.buttonColors(Color.Blue)) {
                                Text("Giriş Yap")
                            }
                        }
                    }
                }
            }
        }
    }
}

