package com.ozturksahinyetisir.myshop.presentation.login

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.ozturksahinyetisir.myshop.R
import com.ozturksahinyetisir.myshop.local.SharedPref
import com.ozturksahinyetisir.myshop.montserratFontFamily
import com.ozturksahinyetisir.myshop.presentation.home.ViewProducts

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    state: LoginInState,
    onSignInClick: () -> Unit
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val sharedPreferences = SharedPref(context)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(25.dp))
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = null,
            modifier = Modifier.size(150.dp),
            contentScale = ContentScale.Fit,
        )
        TextField(
            value = username,
            onValueChange = {
                if (it.length <= 12) { // The maximum length of the ID can be up to 12.
                    username = it
                    username = it.replace(" ", "") //Suppresses line breaks

                }
            },
            label = { Text("Kullanıcı Adı", fontFamily = montserratFontFamily) },
        )
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = "Maksimum karakter uzunluğu 12'dir.\nSadece kullanıcı adı ile giriş yapılabilir. ",
            color = Color.Gray,
            fontSize = 12.sp,
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            onValueChange = {
                if (it.length <= 16) { // The maximum length of the password can be up to 16.
                    password = it
                    password = it.replace(" ", "") //Suppresses line breaks
                }
            },
            label = { Text("Şifre", fontFamily = montserratFontFamily) },
            visualTransformation = PasswordVisualTransformation(),

            )
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = "Maksimum karakter uzunluğu 16'dır.\nŞifreniz en az 6 karakterden oluşmalıdır",
            color = Color.Gray,
            fontSize = 12.sp,
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                sharedPreferences.loadData("MyPrefs") // load SharedPreferences
                val savedUsername = sharedPreferences.loadData("username")
                val savedPass = sharedPreferences.loadData("pass")
                if (username == "ozturk" && password == "123123") {  // login with simple if else
                    Toast.makeText(context, "Giriş Başarılı", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, ViewProducts::class.java)
                    context.startActivity(intent)
                } else if (username == savedUsername && password == savedPass) { // login with shared Preferences
                    Toast.makeText(
                        context,
                        "Başarıyla Giriş Yaptınız!",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(context, ViewProducts::class.java)
                    context.startActivity(intent)
                } else if (username == "" || password == "") {
                    Toast.makeText(
                        context,
                        "Kullanıcı Adı veya Şifreyi Boş Bıraktınız!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (password.length < 6) {
                    Toast.makeText(
                        context,
                        "Şifreyi uzunluğu 6'dan küçük olamaz.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Kullanıcı Adı veya Şifre Hatalı",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF192c97)),
        )
        {
            Text(
                "Giriş",
                fontFamily = montserratFontFamily,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (username.length > 1 && password.length > 5) { // Basic login information control.
                    sharedPreferences.saveData("username", username)
                    sharedPreferences.saveData("pass", password)
                    Toast.makeText(context, "Başarıyla Kayıt Oldunuz!", Toast.LENGTH_SHORT).show()
                } else if (username == "" || password == "") {
                    Toast.makeText(
                        context,
                        "Kullanıcı Adı veya Şifreyi Boş Bıraktınız!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (password.length < 6) {
                    Toast.makeText(
                        context,
                        "Şifreyi uzunluğu 6'dan küçük olamaz.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Kullanıcı adı çok kısa",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF192c97)),
        ) {
            Text(
                "Kayıt Ol",
                fontFamily = montserratFontFamily,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onSignInClick,
            colors = ButtonDefaults.buttonColors(Color(0xFFF40f88))
        ) {
            Text(text = "Google ile Giriş Yap", color = Color.White, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(135.dp))
    }

}
