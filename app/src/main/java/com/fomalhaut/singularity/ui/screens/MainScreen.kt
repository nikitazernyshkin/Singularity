package com.fomalhaut.singularity.ui.screens
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fomalhaut.singularity.dev.viewModel.ViewModel
import com.fomalhaut.singularity.dev.data.entity.PasswordEntity

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainScreen(viewModel: ViewModel) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    var showAbout by remember { mutableStateOf(false) }
    val services by viewModel.allPasswords.collectAsState(initial = emptyList())
    var variable by remember { mutableStateOf<PasswordEntity?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var nameInput by remember { mutableStateOf("") }
    var loginInput by remember { mutableStateOf("") }
    var passInput by remember { mutableStateOf("") }
    if (variable != null){
        LaunchedEffect(variable){
            nameInput = variable!!.name
            loginInput = variable!!.login
            passInput = variable!!.password
        }
        AlertDialog(
            onDismissRequest = {variable = null; nameInput = ""; loginInput = ""; passInput = "" },
            text = {
                Column {
                    Text(" Изменение профиля \n", fontSize = 20.sp)
                    TextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        label = { Text("Название") })
                    Text("")
                    Row {
                        TextField(
                            modifier = Modifier.weight(1f),
                            value = loginInput,
                            onValueChange = { loginInput = it },
                            label = { Text("Логин") })
                        IconButton(onClick = {
                            clipboardManager.setText(AnnotatedString(loginInput))
                            Toast.makeText(context, "Логин скопирован", Toast.LENGTH_SHORT).show()
                        }
                        ){
                            Icon(
                                imageVector = Icons.Default.ContentCopy, // Нужно импортировать androidx.compose.material.icons.Icons
                                contentDescription = "Copy",
                                tint = Color(0xFFFF5C00) // Можно покрасить её в твой оранжевый!
                            )
                        }

                    }
                    Text("")
                    Row {
                        TextField(
                            modifier = Modifier.weight(1f),
                            value = passInput,
                            onValueChange = { passInput = it },
                            label = { Text("Пароль") })
                        IconButton(onClick = {
                            clipboardManager.setText(AnnotatedString(passInput))
                            Toast.makeText(context, "Пароль скопирован", Toast.LENGTH_SHORT).show()
                        }
                        ){
                            Icon(
                                imageVector = Icons.Default.ContentCopy, // Нужно импортировать androidx.compose.material.icons.Icons
                                contentDescription = "Copy",
                                tint = Color(0xFFFF5C00) // Можно покрасить её в твой оранжевый!
                            )
                        }
                        Text("")
                    }
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            viewModel.deletePassword(variable!!)
                            variable = null
                            nameInput = ""
                            loginInput = ""
                            passInput = ""
                        }) {
                        Text("Удалить")
                    }
                    Text("            ")
                    Button(
                        onClick = {
                            viewModel.updatePassword(
                                variable!!.copy(
                                    name = nameInput,
                                    login = loginInput,
                                    password = passInput
                                )
                            )
                            variable = null
                            nameInput = ""
                            loginInput = ""
                            passInput = ""
                        }) {
                        Text("Сохранить")
                    }
                }
            }
        )
    }
    if (showDialog){
        AlertDialog(
            onDismissRequest = { showDialog = false },
            text = { Column {
                Text("Создание профиля \n", fontSize = 20.sp)
                TextField(value = nameInput, onValueChange = { nameInput = it }, label = { Text("Название") })
                Text("")
                TextField(value = loginInput,onValueChange =  { loginInput = it }, label = {Text("Логин")})
                Text("")
                TextField(value = passInput, onValueChange = { passInput = it }, label = {Text("Пароль")})
            }
            },
            confirmButton = {
                Button(onClick = {
                    val newEntry = PasswordEntity(
                        name = nameInput,
                        login = loginInput,
                        password = passInput
                    )
                    viewModel.addPassword(newEntry)
                    nameInput = ""
                    loginInput = ""
                    passInput = ""
                    showDialog = false
                }
                ){
                    Text("Добавить")
                }
            }
        )
    }
    if (showAbout){
        AlertDialog(
            onDismissRequest = {showAbout = false},
            text = {
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = " Singularity Dev", color = Color(0xFFFF5C00), fontSize = 25.sp)
                    Text(text = " v1.0", color = Color(0xFFFF5C00), fontSize = 25.sp)
                    Text(text = " Разработано в рамках курса \n KotlinDroid \n Никитой Зернышкиным. \n MIT LICENCE. \n Разработка ведется  \n в проекте Singularity", fontSize = 16.sp)
                }
            },
            confirmButton = {
                Button(onClick = { showAbout = false }) {
                    Text("Ок")
                }
            }
        )
    }

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "\n", color = Color(0xFFFF5C00), fontSize = 16.sp)
        Text(text = "Singularity", color = Color(0xFFFF5C00), fontSize = 32.sp)
        Text(text = "", color = Color(0xFFFFFFFF), fontSize = 16.sp)
        if(services.isEmpty()){
            Text(text = "\n Менеджер паролей пуст", fontSize = 16.sp, color = Color.LightGray)
        }else{
            Text(text = "Ваши пароли:                                                             ", color = Color(0xFFFFFFFF), fontSize = 16.sp)
        }
        Scaffold(
            modifier = Modifier.weight(1f),
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    showDialog = true
                }) { Text("+", fontSize = 35.sp) }
            }
        ) { padding ->
            LazyColumn(
                Modifier.padding(top = padding.calculateTopPadding() / 10)
            ) {
                items(services) { service ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { variable = service }
                    ) {
                        Text(service.name, Modifier.padding(15.dp))
                    }

                }
                item{
                    Text("")
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable { showAbout = true }
                    ) {
                        Text("О программе", Modifier.padding(15.dp), color = Color(0xFFFF5C00))
                    }
                }
            }
        }
    }
}
