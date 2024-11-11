package com.example.App_museo_papalote.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

@Composable
fun RegisterScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF5F5E9)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Crea tu cuenta", style = MaterialTheme.typography.headlineLarge.copy(fontSize = 32.sp))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Ingresa tus datos", fontSize = 16.sp)

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Edad") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = gender,
                onValueChange = { gender = it },
                label = { Text("Sexo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (email.isNotBlank() && password.isNotBlank() && name.isNotBlank() && age.isNotBlank() && gender.isNotBlank()) {
                        Log.d("RegisterDebug", "Intentando registrar con email: $email")
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Usuario registrado exitosamente en Firebase Auth
                                    Log.d("RegisterDebug", "Registro en Firebase Auth exitoso")
                                    val userId = task.result?.user?.uid
                                    if (userId != null) {
                                        // Guardar datos adicionales en Firestore
                                        val userData = mapOf(
                                            "name" to name,
                                            "age" to age,
                                            "gender" to gender,
                                            "email" to email
                                        )
                                        db.collection("users").document(userId).set(userData)
                                            .addOnSuccessListener {
                                                // Mostrar mensaje y navegar a login
                                                Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                                                navController.navigate("login") {
                                                    popUpTo("register") { inclusive = true }
                                                }
                                            }
                                            .addOnFailureListener { e ->
                                                Toast.makeText(context, "Error al guardar en Firestore: ${e.message}", Toast.LENGTH_LONG).show()
                                                Log.e("FirestoreError", "Error al guardar en Firestore", e)
                                            }
                                    } else {
                                        Log.e("RegisterError", "El UID del usuario es nulo después de crear la cuenta")
                                        Toast.makeText(context, "Error: No se pudo obtener el ID del usuario.", Toast.LENGTH_LONG).show()
                                    }
                                } else {
                                    // Manejo de errores específicos de Firebase Auth
                                    val exception = task.exception
                                    when (exception) {
                                        is FirebaseAuthWeakPasswordException ->
                                            Toast.makeText(context, "Contraseña demasiado débil", Toast.LENGTH_LONG).show()
                                        is FirebaseAuthInvalidCredentialsException ->
                                            Toast.makeText(context, "Correo inválido", Toast.LENGTH_LONG).show()
                                        is FirebaseAuthUserCollisionException ->
                                            Toast.makeText(context, "El usuario ya existe", Toast.LENGTH_LONG).show()
                                        else -> {
                                            Toast.makeText(context, "Error: ${exception?.message}", Toast.LENGTH_LONG).show()
                                            Log.e("RegisterError", "Error al registrar usuario", exception)
                                        }
                                    }
                                }
                            }
                    } else {
                        Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8BC34A)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Registrarse", color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = { navController.navigate("login") }) {
                Text("Ya tengo una cuenta", color = Color(0xFF8BC34A))
            }
        }
    }
}