package com.swarag.laplock

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

// Utility class to handle preferences
class PreferenceManager(context: Context) {
    private val PREFS_NAME = "LaplockPrefs"
    private val KEY_SERVER_IP = "server_ip"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    fun saveServerIp(ip: String) {
        prefs.edit().putString(KEY_SERVER_IP, ip).apply()
        Log.d("PreferenceManager", "Saved server IP: $ip")
    }
    
    fun getSavedServerIp(): String? {
        return prefs.getString(KEY_SERVER_IP, null)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onLockClick: () -> Unit,
    onRefreshIpClick: () -> Unit,
    serverIp: String,
    apiServer: String,
    onManualIpChanged: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val prefManager = remember { PreferenceManager(context) }
    val keyboardController = LocalSoftwareKeyboardController.current
    
    // State for the dialog
    var showIpDialog by remember { mutableStateOf(false) }
    var manualIpInput by remember { mutableStateOf(serverIp) }
    
    // State to show the custom IP badge
    val isUsingCustomIp = remember(serverIp) { 
        prefManager.getSavedServerIp() != null && prefManager.getSavedServerIp() == serverIp 
    }
    
    // Effect to load saved IP on first launch
    LaunchedEffect(Unit) {
        val savedIp = prefManager.getSavedServerIp()
        if (savedIp != null && savedIp.isNotEmpty()) {
            Log.d("MainScreen", "Loading saved IP: $savedIp")
            onManualIpChanged(savedIp)
        }
    }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Laptop Controller", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Laptop IP Address", style = MaterialTheme.typography.titleMedium)
                    
                    if (isUsingCustomIp) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = MaterialTheme.shapes.small
                        ) {
                            Text(
                                "Custom",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = serverIp,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "API Server: $apiServer",
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = onRefreshIpClick) {
                        Text("Refresh IP")
                    }
                    
                    Button(
                        onClick = { showIpDialog = true }
                    ) {
                        Text("Set IP Manually")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onLockClick,
            enabled = serverIp != "Loading..." && 
                     serverIp != "Connection failed" && 
                     serverIp != "Parse error",
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth(0.7f)
        ) {
            Text("LOCK LAPTOP", style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        // Add test connection buttons
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            OutlinedButton(
                onClick = { 
                    ConnectionTester.testApiServer(context, apiServer)
                }
            ) {
                Text("Test API", style = MaterialTheme.typography.labelMedium)
            }
            
            OutlinedButton(
                onClick = { 
                    ConnectionTester.testTcpServer(context, serverIp, 8888)
                }
            ) {
                Text("Test Lock", style = MaterialTheme.typography.labelMedium)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Add debugging information
        Text(
            "Note: Make sure your laptop and device are on the same network",
            style = MaterialTheme.typography.bodySmall
        )
    }
    
    // Dialog for manual IP input
    if (showIpDialog) {
        val focusRequester = remember { FocusRequester() }
        
        LaunchedEffect(Unit) {
            // Slight delay to ensure dialog is shown before requesting focus
            kotlinx.coroutines.delay(100)
            focusRequester.requestFocus()
        }
        
        AlertDialog(
            onDismissRequest = { showIpDialog = false },
            title = { Text("Enter Server IP Address") },
            text = {
                Column {
                    OutlinedTextField(
                        value = manualIpInput,
                        onValueChange = { manualIpInput = it },
                        label = { Text("IP Address") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Ascii,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                            }
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        "Enter the IP address of your laptop running the server.py script.",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (manualIpInput.isNotBlank() && isValidIpAddress(manualIpInput)) {
                            prefManager.saveServerIp(manualIpInput)
                            onManualIpChanged(manualIpInput)
                            showIpDialog = false
                            Toast.makeText(context, "IP address saved", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Please enter a valid IP address", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Icon(Icons.Default.Save, contentDescription = "Save")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Save")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showIpDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

// Helper function to validate IP address
fun isValidIpAddress(ip: String): Boolean {
    val pattern = """^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$""".toRegex()
    return pattern.matches(ip)
}